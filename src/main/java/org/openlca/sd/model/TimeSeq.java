package org.openlca.sd.model;

import org.openlca.commons.Res;
import org.openlca.sd.xmile.Xmile;

public class TimeSeq {

	private double start;
	private double end;
	private double dt;
	private String unit;

	public TimeSeq() {
	}

	static Res<TimeSeq> of(Xmile xmile) {
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
	}

	public TimeSeq(double start, double end, String unit) {
		this(start, end, 1, unit);
	}

	public double timeAt(int iteration) {
		return start + iteration * dt;
	}

	public double start() {
		return start;
	}

	public void setStart(double start) {
		this.start = start;
	}

	public double end() {
		return end;
	}

	public void setEnd(double end) {
		this.end = end;
	}

	public double dt() {
		return dt;
	}

	public void setDt(double dt) {
		this.dt = dt;
	}

	public String unit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public int iterationCount() {
		if (dt == 0)
			return 0;
		double count = 1 + (end - start) / dt;
		return (int) count;
	}
}
