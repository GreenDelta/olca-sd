package org.openlca.sd.xmile;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public final class XmiGf extends XmiVariable<XmiGf> {

	@XmlAttribute(name = "type")
	XmiGfType type;

	@XmlElement(name = "xscale", namespace = Xmile.NS)
	XmiMinMax xscale;

	@XmlElement(name = "xpts", namespace = Xmile.NS)
	XmiPoints xpts;

	@XmlElement(name = "yscale", namespace = Xmile.NS)
	XmiMinMax yscale;

	@XmlElement(name = "ypts", namespace = Xmile.NS)
	XmiPoints ypts;

	public XmiGfType type() {
		return type != null ? type : XmiGfType.CONTINUOUS;
	}

	public XmiMinMax xscale() {
		return xscale;
	}

	public XmiPoints xpts() {
		return xpts;
	}

	public XmiMinMax yscale() {
		return yscale;
	}

	public XmiPoints ypts() {
		return ypts;
	}

	public XmiGf withType(XmiGfType type) {
		this.type = type;
		return this;
	}

	public XmiGf withXScale(XmiMinMax xscale) {
		this.xscale = xscale;
		return this;
	}

	public XmiMinMax withXScale() {
		if (xscale == null) {
			xscale = new XmiMinMax();
		}
		return xscale;
	}

	public XmiGf withXPts(XmiPoints xpts) {
		this.xpts = xpts;
		return this;
	}

	public XmiPoints withXPts() {
		if (xpts == null) {
			xpts = new XmiPoints();
		}
		return xpts;
	}

	public XmiGf withYScale(XmiMinMax yscale) {
		this.yscale = yscale;
		return this;
	}

	public XmiMinMax withYScale() {
		if (yscale == null) {
			yscale = new XmiMinMax();
		}
		return yscale;
	}

	public XmiGf withYPts(XmiPoints ypts) {
		this.ypts = ypts;
		return this;
	}

	public XmiPoints withYPts() {
		if (ypts == null) {
			ypts = new XmiPoints();
		}
		return ypts;
	}
}
