package org.openlca.sd.xmile;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;

public sealed abstract class XmiEvaluatable<T extends XmiEvaluatable<T>>
	extends XmiVariable<T>
	permits XmiAux, XmiFlow, XmiStock {

	@XmlElement(name = "eqn", namespace = Xmile.NS)
	String eqn;

	@XmlElement(name = "units", namespace = Xmile.NS)
	String units;

	@XmlElement(name = "doc", namespace = Xmile.NS)
	String doc;

	@XmlElement(name ="gf", namespace = Xmile.NS)
	XmiGf gf;

	@XmlElementWrapper(name = "dimensions", namespace = Xmile.NS)
	@XmlElement(name="dim", namespace = Xmile.NS)
	List<Dim> dimensions;

	@XmlElement(name = "element", namespace = Xmile.NS)
	List<XmiElement> elements;

	@XmlElement(name = "non_negative", namespace = Xmile.NS)
	XmiNonNegative nonNegative;

	public String eqn() {
		return eqn;
	}

	public String units() {
		return units;
	}

	public String doc() {
		return doc;
	}

	public XmiGf gf() {
		return gf;
	}

	public List<Dim> dimensions() {
		return dimensions == null ? List.of() : dimensions;
	}

	public List<XmiElement> elements() {
		return elements != null ? elements : List.of();
	}

	public boolean isNonNegative() {
		return nonNegative != null;
	}

	@SuppressWarnings("unchecked")
	public T withEqn(String eqn) {
		this.eqn = eqn;
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public T withUnits(String units) {
		this.units = units;
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public T withDoc(String doc) {
		this.doc = doc;
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public T withGf(XmiGf gf) {
		this.gf = gf;
		return (T) this;
	}

	public XmiGf withGf() {
		if (gf == null) {
			gf = new XmiGf();
		}
		return gf;
	}

	@SuppressWarnings("unchecked")
	public T withNonNegative(XmiNonNegative nonNegative) {
		this.nonNegative = nonNegative;
		return (T) this;
	}

	public XmiNonNegative withNonNegative() {
		if (nonNegative == null) {
			nonNegative = new XmiNonNegative();
		}
		return nonNegative;
	}

	@SuppressWarnings("unchecked")
	public T withDimensions(List<Dim> dimensions) {
		this.dimensions = dimensions;
		return (T) this;
	}

	public List<Dim> withDimensions() {
		if (dimensions == null) {
			dimensions = new ArrayList<>();
		}
		return dimensions;
	}

	@SuppressWarnings("unchecked")
	public T withElements(List<XmiElement> elements) {
		this.elements = elements;
		return (T) this;
	}

	public List<XmiElement> withElements() {
		if (elements == null) {
			elements = new ArrayList<>();
		}
		return elements;
	}

	@XmlAccessorType(XmlAccessType.FIELD)
	public static class Dim {

		@XmlAttribute(name = "name")
		String name;

		public String name() {
			return name;
		}

		public Dim withName(String name) {
			this.name = name;
			return this;
		}
	}
}
