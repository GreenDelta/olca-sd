package org.openlca.sd.model;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.openlca.commons.Res;
import org.openlca.sd.model.cells.Cell;
import org.openlca.sd.model.cells.EqnCell;
import org.openlca.sd.model.cells.LookupCell;
import org.openlca.sd.model.cells.LookupEqnCell;
import org.openlca.sd.model.cells.NonNegativeCell;
import org.openlca.sd.model.cells.TensorCell;
import org.openlca.sd.model.cells.TensorEqnCell;
import org.openlca.sd.xmile.XmiAux;
import org.openlca.sd.xmile.XmiDim;
import org.openlca.sd.xmile.XmiElement;
import org.openlca.sd.xmile.XmiEvaluatable;
import org.openlca.sd.xmile.XmiFlow;
import org.openlca.sd.xmile.XmiGf;
import org.openlca.sd.xmile.XmiMinMax;
import org.openlca.sd.xmile.XmiModel;
import org.openlca.sd.xmile.XmiNonNegative;
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
		var xmileRes = write();
		if (xmileRes.isError())
			return xmileRes.castError();

		try (var stream = new FileOutputStream(file);
				 var buffer = new BufferedOutputStream(stream)) {
			JAXB.marshal(xmileRes.value(), buffer);
			return Res.ok();
		} catch (Exception e) {
			return Res.error("Error writing XMILE file: " + file, e);
		}
	}

	public Res<Void> writeTo(OutputStream stream) {
		var xmileRes = write();
		if (xmileRes.isError())
			return xmileRes.castError();

		try {
			JAXB.marshal(xmileRes.value(), stream);
			return Res.ok();
		} catch (Exception e) {
			return Res.error("Error writing XMILE stream", e);
		}
	}

	private XmiSimSpecs writeSimSpecs() {
		var specs = model.time();
		if (specs == null)
			return null;

		var xmiSpecs = new XmiSimSpecs();
		xmiSpecs.setStart(specs.start());
		xmiSpecs.setStop(specs.end());
		xmiSpecs.setTimeUnits(specs.unit());

		var dt = new org.openlca.sd.xmile.XmiSimSpecs.DeltaT();
		dt.setValue(specs.dt());
		xmiSpecs.setDt(dt);

		return xmiSpecs;
	}

	private List<XmiDim> writeDims() {
		var dims = new ArrayList<XmiDim>();
		for (var d : model.dimensions()) {
			var xmiDim = new XmiDim();
			xmiDim.setName(d.name().value());
			var elements = new ArrayList<XmiDim.Elem>();
			for (var e : d.elements()) {
				var xmiElem = new XmiDim.Elem();
				xmiElem.setName(e.value());
				elements.add(xmiElem);
			}
			xmiDim.setElems(elements);
			dims.add(xmiDim);
		}
		return dims;
	}

	private List<XmiVariable> writeVariables() {
		var vars = new ArrayList<XmiVariable>();
		for (var v : model.vars()) {
			XmiVariable xmiVar = switch (v) {
				case Auxil a -> writeAux(a);
				case Rate r -> writeFlow(r);
				case Stock s -> writeStock(s);
				default -> null;
			};
			if (xmiVar != null) {
				vars.add(xmiVar);
			}
		}
		return vars;
	}

	private XmiAux writeAux(Auxil a) {
		var xmiAux = new XmiAux();
		xmiAux.setName(a.name().value());
		xmiAux.setUnits(a.unit());
		fillEvaluatable(xmiAux, a.def());
		return xmiAux;
	}

	private XmiFlow writeFlow(Rate r) {
		var xmiFlow = new XmiFlow();
		xmiFlow.setName(r.name().value());
		xmiFlow.setUnits(r.unit());
		fillEvaluatable(xmiFlow, r.def());
		return xmiFlow;
	}

	private XmiStock writeStock(Stock s) {
		var xmiStock = new XmiStock();
		xmiStock.setName(s.name().value());
		xmiStock.setUnits(s.unit());
		fillEvaluatable(xmiStock, s.def());
		xmiStock.setInflows(s.inFlows().stream().map(Id::value).toList());
		xmiStock.setOutflows(s.outFlows().stream().map(Id::value).toList());
		return xmiStock;
	}

	private void fillEvaluatable(XmiEvaluatable x, Cell cell) {
		if (cell instanceof NonNegativeCell nn) {
			x.setNonNegative(new XmiNonNegative());
			cell = nn.value();
		}

		if (cell instanceof TensorEqnCell te) {
			if (te.eqn() instanceof EqnCell ec) {
				x.setEqn(ec.value());
			}
			fillTensor(x, te.tensor());
		} else if (cell instanceof EqnCell ec) {
			x.setEqn(ec.value());
		} else if (cell instanceof LookupEqnCell lec) {
			x.setEqn(lec.eqn());
			x.setGf(xmiLookupOf(lec.func()));
		} else if (cell instanceof LookupCell lc) {
			x.setGf(xmiLookupOf(lc.func()));
		} else if (cell instanceof TensorCell tc) {
			fillTensor(x, tc.value());
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

		// XMILE elements are for sparse or explicit values in arrays
		// We'll only write elements for non-empty cells
		var elements = new ArrayList<XmiElement>();
		for (int i = 0; i < t.size(); i++) {
			Cell c = t.get(i);
			if (c instanceof org.openlca.sd.model.cells.EmptyCell)
				continue;

			var xmiElem = new XmiElement();
			// We need a way to get the subscript for an index if we want to write it back
			// For now, we skip subscripted elements or handle them if possible
			if (c instanceof NonNegativeCell nn) {
				xmiElem.setNonNegative(new XmiNonNegative());
				c = nn.value();
			}
			if (c instanceof EqnCell ec) {
				xmiElem.setEqn(ec.value());
			} else if (c instanceof LookupEqnCell lec) {
				xmiElem.setEqn(lec.eqn());
				xmiElem.setGf(xmiLookupOf(lec.func()));
			} else if (c instanceof LookupCell lc) {
				xmiElem.setGf(xmiLookupOf(lc.func()));
			}
			elements.add(xmiElem);
		}
		x.setElements(elements);
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
