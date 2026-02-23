package org.openlca.sd.xmile.view;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
public class XmiViewShape {

	@XmlAttribute(name = "type")
	String type;

	@XmlAttribute(name = "width")
	Double width;

	@XmlAttribute(name = "height")
	Double height;

	@XmlAttribute(name = "radius")
	Double radius;

	public String type() {
		return type;
	}

	public Double width() {
		return width;
	}

	public Double height() {
		return height;
	}

	public Double radius() {
		return radius;
	}

	public XmiViewShape withType(String type) {
		this.type = type;
		return this;
	}

	public XmiViewShape withWidth(Double width) {
		this.width = width;
		return this;
	}

	public XmiViewShape withHeight(Double height) {
		this.height = height;
		return this;
	}

	public XmiViewShape withRadius(Double radius) {
		this.radius = radius;
		return this;
	}
}
