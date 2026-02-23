package org.openlca.sd.xmile;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class XmiElement {

	@XmlAttribute(name = "subscript")
	String subscript;

	@XmlElement(name = "eqn", namespace = Xmile.NS)
	String eqn;

	@XmlElement(name ="gf", namespace = Xmile.NS)
	XmiGf gf;

	@XmlElement(name="non_negative", namespace = Xmile.NS)
	XmiNonNegative nonNegative;

	public String subscript() {
		return subscript;
	}

	public String eqn() {
		return eqn;
	}

	public XmiGf gf() {
		return gf;
	}

	public boolean isNonNegative() {
		return nonNegative != null;
	}

	public XmiElement withSubscript(String subscript) {
		this.subscript = subscript;
		return this;
	}

	public XmiElement withEqn(String eqn) {
		this.eqn = eqn;
		return this;
	}

	public XmiElement withGf(XmiGf gf) {
		this.gf = gf;
		return this;
	}

	public XmiElement withNonNegative() {
		this.nonNegative = new XmiNonNegative();
		return this;
	}
}
