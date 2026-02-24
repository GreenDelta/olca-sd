package org.openlca.sd.model;

import java.util.ArrayList;
import java.util.List;

import org.openlca.commons.Res;
import org.openlca.sd.xmile.Xmile;

public class SdModel {

	private SimSpecs time;
	private final List<Var> vars = new ArrayList<>();
	private final List<Dimension> dimensions = new ArrayList<>();

	public static Res<SdModel> readFrom(Xmile xmile) {
		return new XmileReader(xmile).read();
	}

	public SimSpecs time() {
		return time;
	}

	public void setTime(SimSpecs time) {
		this.time = time;
	}

	public List<Var> vars() {
		return vars;
	}

	public List<Dimension> dimensions() {
		return dimensions;
	}
}
