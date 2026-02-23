package org.openlca.sd.xmile;

import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;

public sealed abstract class XmiEvaluatable<T extends XmiEvaluatable<T>>
	extends XmiVariable
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

	@XmlAccessorType(XmlAccessType.FIELD)
	public static class Dim {

		@XmlAttribute(name = "name")
		String name;

		public String name() {
			return name;
		}
	}
}
