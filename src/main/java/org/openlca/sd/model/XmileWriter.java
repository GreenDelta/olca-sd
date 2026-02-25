package org.openlca.sd.model;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.openlca.commons.Res;
import org.openlca.commons.Strings;
import org.openlca.sd.model.cells.BoolCell;
import org.openlca.sd.model.cells.Cell;
import org.openlca.sd.model.cells.EmptyCell;
import org.openlca.sd.model.cells.EqnCell;
import org.openlca.sd.model.cells.LookupCell;
import org.openlca.sd.model.cells.LookupEqnCell;
import org.openlca.sd.model.cells.NonNegativeCell;
import org.openlca.sd.model.cells.NumCell;
import org.openlca.sd.model.cells.TensorCell;
import org.openlca.sd.model.cells.TensorEqnCell;
import org.openlca.sd.util.Tensors;
import org.openlca.sd.xmile.XmiAux;
import org.openlca.sd.xmile.XmiDim;
import org.openlca.sd.xmile.XmiElement;
import org.openlca.sd.xmile.XmiEvaluatable;
import org.openlca.sd.xmile.XmiFlow;
import org.openlca.sd.xmile.XmiGf;
import org.openlca.sd.xmile.XmiMinMax;
import org.openlca.sd.xmile.XmiModel;
import org.openlca.sd.xmile.XmiPoints;
import org.openlca.sd.xmile.XmiSimSpecs;
import org.openlca.sd.xmile.XmiStock;
import org.openlca.sd.xmile.XmiVariable;
import org.openlca.sd.xmile.Xmile;

import jakarta.xml.bind.JAXB;

public class XmileWriter {

	private final SdModel model;

	public XmileWriter(SdModel model) {
		this.model = model;
	}

	public static Res<Void> write(SdModel model, File file) {
		return new XmileWriter(model).writeTo(file);
	}

	public static Res<Void> write(SdModel model, OutputStream stream) {
		return new XmileWriter(model).writeTo(stream);
	}

	public Res<Xmile> write() {
		if (model == null)
			return Res.error("no model provided");
		var xmile = new Xmile();
		xmile.setSimSpecs(writeSimSpecs());
		xmile.setDims(writeDims());
		var xmiModel = new XmiModel();
		xmiModel.setVariables(writeVariables());
		xmile.setModel(xmiModel);
		return Res.ok(xmile);
	}

	public Res<Void> writeTo(File file) {
		var res = write();
		if (res.isError()) return res.castError();
		try (var stream = new FileOutputStream(file);
				 var buffer = new BufferedOutputStream(stream)) {
			JAXB.marshal(res.value(), buffer);
			return Res.ok();
		} catch (Exception e) {
			return Res.error("Error writing XMILE file: " + file, e);
		}
	}

	public Res<Void> writeTo(OutputStream stream) {
		var res = write();
		if (res.isError()) return res.castError();
		try {
			JAXB.marshal(res.value(), stream);
			return Res.ok();
		} catch (Exception e) {
			return Res.error("Error writing XMILE stream", e);
		}
	}

	private XmiSimSpecs writeSimSpecs() {
		var specs = model.time();
		if (specs == null)
			return null;

		var x = new XmiSimSpecs();
		x.setStart(specs.start());
		x.setStop(specs.end());
		x.setTimeUnits(specs.unit());

		var dt = new XmiSimSpecs.DeltaT();
		dt.setValue(specs.dt());
		x.setDt(dt);
		return x;
	}

	private List<XmiDim> writeDims() {
		var dims = new ArrayList<XmiDim>();
		for (var d : model.dimensions()) {
			var xd = new XmiDim();
			xd.setName(d.name().value());
			var elements = new ArrayList<XmiDim.Elem>();
			for (var e : d.elements()) {
				var xe = new XmiDim.Elem();
				xe.setName(e.value());
				elements.add(xe);
			}
			xd.setElems(elements);
			dims.add(xd);
		}
		return dims;
	}

	private List<XmiVariable> writeVariables() {
		var vars = new ArrayList<XmiVariable>();
		for (var v : model.vars()) {
			XmiVariable xmiVar = switch (v) {
				case Auxil a -> xmiAuxOf(a);
				case Rate r -> xmiFlowOf(r);
				case Stock s -> xmiStockOf(s);
			};
			vars.add(xmiVar);
		}
		return vars;
	}

	private XmiAux xmiAuxOf(Auxil a) {
		var x = new XmiAux();
		x.setName(a.name().label());
		x.setUnits(a.unit());
		fillVariable(x, a.def());
		return x;
	}

	private XmiFlow xmiFlowOf(Rate r) {
		var x = new XmiFlow();
		x.setName(r.name().label());
		x.setUnits(r.unit());
		fillVariable(x, r.def());
		return x;
	}

	private XmiStock xmiStockOf(Stock s) {
		var x = new XmiStock();
		x.setName(s.name().label());
		x.setUnits(s.unit());
		fillVariable(x, s.def());
		x.setInflows(s.inFlows().stream().map(Id::value).toList());
		x.setOutflows(s.outFlows().stream().map(Id::value).toList());
		return x;
	}

	private void fillVariable(XmiEvaluatable x, Cell cell) {
		switch (cell) {
			case BoolCell(boolean b) -> x.setEqn(Boolean.toString(b));
			case EmptyCell ignore -> {
			}
			case EqnCell(String eqn) -> x.setEqn(eqn);
			case LookupCell(LookupFunc func) -> x.setGf(xmiLookupOf(func));
			case NumCell(double num) -> x.setEqn(Double.toString(num));
			case TensorCell(Tensor tensor) -> fillTensor(x, tensor);

			case LookupEqnCell(String eqn, LookupFunc func) -> {
				x.setEqn(eqn);
				x.setGf(xmiLookupOf(func));
			}
			case NonNegativeCell(Cell value) -> {
				x.setNonNegative();
				fillVariable(x, value);
			}
			case TensorEqnCell(Cell eqn, Tensor tensor) -> {
				fillVariable(x, eqn);
				fillTensor(x, tensor);
			}
		}
	}

	private void fillTensor(XmiEvaluatable x, Tensor t) {
		var dims = new ArrayList<XmiEvaluatable.Dim>();
		for (var d : t.dimensions()) {
			var xmiDim = new XmiEvaluatable.Dim();
			xmiDim.setName(d.name().value());
			dims.add(xmiDim);
		}
		x.setDimensions(dims);

		var elements = new ArrayList<XmiElement>();
		for (var a : Tensors.addressesOf(t)) {
			var cell = t.get(a);
			if (cell.isEmpty()) continue;
			var elem = new XmiElement();
			fillElement(elem, cell);
			if (Strings.isBlank(elem.eqn()) && elem.gf() == null) {
				continue;
			}
			var subscript = a.stream()
				.map(Object::toString)
				.collect(Collectors.joining(", "));
			elem.setSubscript(subscript);
			elements.add(elem);
		}
		x.setElements(elements);
	}

	private void fillElement(XmiElement x, Cell cell) {
		switch (cell) {
			case BoolCell(boolean b) -> x.setEqn(Boolean.toString(b));
			case EmptyCell ignore -> {
			}
			case EqnCell(String eqn) -> x.setEqn(eqn);
			case LookupCell(LookupFunc func) -> x.setGf(xmiLookupOf(func));
			case NumCell(double num) -> x.setEqn(Double.toString(num));
			case LookupEqnCell(String eqn, LookupFunc func) -> {
				x.setEqn(eqn);
				x.setGf(xmiLookupOf(func));
			}
			case NonNegativeCell(Cell value) -> {
				x.setNonNegative();
				fillElement(x, value);
			}
			case TensorCell ignore -> {
			}
			case TensorEqnCell ignore -> {
			}
		}
	}

	private XmiGf xmiLookupOf(LookupFunc func) {
		if (func == null)
			return null;

		var xmiGf = new XmiGf();
		xmiGf.setType(switch (func.type()) {
			case DISCRETE -> org.openlca.sd.xmile.XmiGfType.DISCRETE;
			case EXTRAPOLATE -> org.openlca.sd.xmile.XmiGfType.EXTRAPOLATE;
			case null, default -> org.openlca.sd.xmile.XmiGfType.CONTINUOUS;
		});

		// in our runtime-model, we always translate range defined lookup functions
		// into their xy-pairs. Thus, we write this back to XMILE and these values
		// should be read first in an import. We additionally provide the x-range
		// for information and debugging.
		xmiGf.setYpts(xmiPointsOf(func.ys()));
		var xs = func.xs();
		xmiGf.setXpts(xmiPointsOf(xs));
		if (xs != null && xs.length > 0) {
			var scale = new XmiMinMax();
			scale.setMin(xs[0]);
			scale.setMax(xs[xs.length - 1]);
			xmiGf.setXscale(scale);
		}
		return xmiGf;
	}

	private XmiPoints xmiPointsOf(double[] values) {
		if (values == null)
			return null;
		var sb = new StringBuilder();
		for (int i = 0; i < values.length; i++) {
			if (i > 0) {
				sb.append(",");
			}
			sb.append(values[i]);
		}
		var points = new XmiPoints();
		points.setValues(sb.toString());
		return points;
	}
}
