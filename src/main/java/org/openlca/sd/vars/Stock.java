package org.openlca.sd.vars;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.openlca.sd.eqn.Id;
import org.openlca.sd.eqn.cells.Cell;

public record Stock(
	Id name,
	Cell def,
	String unit,
	List<Id> inFlows,
	List<Id> outFlows,
	List<Cell> values) implements Var {

	public Stock {
		Objects.requireNonNull(name);
		Objects.requireNonNull(def);
		Objects.requireNonNull(inFlows);
		Objects.requireNonNull(outFlows);
		Objects.requireNonNull(values);
	}

	public Stock(
		Id name, Cell def, String unit, List<Id> inFlows, List<Id> outFlows) {
		this(name, def, unit, inFlows, outFlows, new ArrayList<>());
	}

	@Override
	public Var freshCopy() {
		return new org.openlca.sd.vars.Stock(
			name, def, unit, new ArrayList<>(inFlows), new ArrayList<>(outFlows));
	}
}
