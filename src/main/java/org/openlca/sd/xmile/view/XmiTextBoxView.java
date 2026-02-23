package org.openlca.sd.xmile.view;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
public class XmiTextBoxView implements XmiViewPoint {

	@XmlAttribute(name = "uid")
	String uid;

	@XmlAttribute(name = "x")
	double x;

	@XmlAttribute(name = "y")
	double y;

	@XmlAttribute(name = "width")
	double width;

	@XmlAttribute(name = "height")
	double height;

	@XmlValue
	String text;

	public String uid() {
		return uid;
	}

	public double x() {
		return x;
	}

	public double y() {
		return y;
	}

	public double width() {
		return width;
	}

	public double height() {
		return height;
	}

	public String text() {
		return text;
	}

	public XmiTextBoxView withUid(String uid) {
		this.uid = uid;
		return this;
	}

	public XmiTextBoxView withX(double x) {
		this.x = x;
		return this;
	}

	public XmiTextBoxView withY(double y) {
		this.y = y;
		return this;
	}

	public XmiTextBoxView withWidth(double width) {
		this.width = width;
		return this;
	}

	public XmiTextBoxView withHeight(double height) {
		this.height = height;
		return this;
	}

	public XmiTextBoxView withText(String text) {
		this.text = text;
		return this;
	}
}
