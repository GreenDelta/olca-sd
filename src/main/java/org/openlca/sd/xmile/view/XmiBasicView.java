package org.openlca.sd.xmile.view;

import jakarta.xml.bind.annotation.XmlAttribute;

abstract class XmiBasicView<T extends XmiBasicView<T>> extends XmiStyleInfo<T> implements XmiViewPoint {

	@XmlAttribute(name = "x")
	double x;

	@XmlAttribute(name = "y")
	double y;

	public double x() {
		return x;
	}

	public double y() {
		return y;
	}

	@SuppressWarnings("unchecked")
	public T withX(double x) {
		this.x = x;
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public T withY(double y) {
		this.y = y;
		return (T) this;
	}
}
