package org.openlca.sd.vars;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.openlca.sd.eqn.Id;
import org.openlca.sd.eqn.cells.Cell;

public record Auxil(
	Id name, Cell def, String unit, List<Cell> values) implements Var {

	public Auxil {
		Objects.requireNonNull(name);
		Objects.requireNonNull(def);
		Objects.requireNonNull(values);
	}

	public Auxil(Id name, Cell def, String unit) {
		this(name, def, unit, new ArrayList<>());
	}

	@Override
	public org.openlca.sd.vars.Auxil freshCopy() {
		return new org.openlca.sd.vars.Auxil(name, def, unit);
	}
}
