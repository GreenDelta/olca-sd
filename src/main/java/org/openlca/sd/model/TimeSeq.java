package org.openlca.sd.model;

import org.openlca.commons.Res;
import org.openlca.sd.xmile.Xmile;

public class TimeSeq {

	private final double start;
	private final double end;
	private final double dt;
	private final String unit;
	private double current;

	public static Res<TimeSeq> of (Xmile xmile) {
		if (xmile == null || xmile.simSpecs() == null)
			return Res.error("no sim-specs provided");
		var specs = xmile.simSpecs();
		if (specs.start() == null)
			return Res.error("no start time provided");
		if (specs.stop() == null)
			return Res.error("no end time provided");

		var unit = specs.timeUnits();
		if (specs.dt() == null || specs.dt().value() == null)
			return Res.ok(new TimeSeq(specs.start(), specs.stop(), unit));

		double dt = specs.dt().value();
		var seq =  specs.dt().isReciprocal()
			? new TimeSeq(specs.start(), specs.stop(), 1 / dt, unit)
			: new TimeSeq(specs.start(), specs.stop(), dt, unit);
		return Res.ok(seq);
	}

	public TimeSeq(double start, double end, double dt, String unit) {
		this.start = start;
		this.end = end;
		this.dt = dt;
		this.unit = unit;
		this.current = start;
	}

	public TimeSeq(double start, double end, String unit) {
		this(start, end, 1, unit);
	}

	public boolean isFinished() {
		return current >= end;
	}

	public double next() {
		current += dt;
		return current;
	}

	public double start() {
		return start;
	}

	public double end() {
		return end;
	}

	public double dt() {
		return dt;
	}

	public String unit() {
		return unit;
	}

	public double current() {
		return current;
	}

	public int iterationCount() {
		if (dt == 0)
			return 0;
		double count = 1 + (end - start) / dt;
		return (int) count;
	}
}
