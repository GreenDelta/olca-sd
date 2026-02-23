package org.openlca.sd.xmile.view;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
public class XmiAuxView extends XmiVariableView<XmiAuxView> {

	@XmlAttribute(name = "width")
	double width;

	@XmlAttribute(name = "height")
	double height;

	public double width() {
		return width;
	}

	public double height() {
		return height;
	}

	public XmiAuxView withWidth(double width) {
		this.width = width;
		return this;
	}

	public XmiAuxView withHeight(double height) {
		this.height = height;
		return this;
	}

}
