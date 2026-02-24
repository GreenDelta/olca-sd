package org.openlca.sd.vars;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.openlca.sd.eqn.Id;
import org.openlca.sd.eqn.cells.Cell;

public sealed interface Var {

	Id name();

	Cell def();

	String unit();

	List<Cell> values();

	/// Creates a fresh copy of the variable. This will not copy
	/// the values from the evaluation history.
	Var freshCopy();

	default void pushValue(Cell cell) {
		values().add(cell);
	}

	default Cell value() {
		return values().isEmpty()
				? def()
				: values().getLast();
	}

	record Auxil(
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
		public Auxil freshCopy() {
			return new Auxil(name, def, unit);
		}
	}

	record Rate(
			Id name, Cell def, String unit, List<Cell> values) implements Var {

		public Rate {
			Objects.requireNonNull(name);
			Objects.requireNonNull(def);
			Objects.requireNonNull(values);
		}

		public Rate(Id name, Cell def, String unit) {
			this(name, def, unit, new ArrayList<>());
		}

		@Override
		public Rate freshCopy() {
			return new Rate(name, def, unit);
		}
	}

	record Stock(
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
			return new Stock(
					name, def, unit, new ArrayList<>(inFlows), new ArrayList<>(outFlows));
		}
	}
}
