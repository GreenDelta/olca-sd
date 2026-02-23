package org.openlca.sd.xmile.view;

import org.openlca.sd.xmile.Xmile;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class XmiViewStyle extends XmiStyleInfo<XmiViewStyle> {

	@XmlElement(name = "flow", namespace = Xmile.NS)
	XmiElementStyle flowStyle;

	@XmlElement(name = "stock", namespace = Xmile.NS)
	XmiElementStyle stockStyle;

	@XmlElement(name = "aux", namespace = Xmile.NS)
	XmiElementStyle auxStyle;

	@XmlElement(name = "text_box", namespace = Xmile.NS)
	XmiElementStyle textBoxStyle;

	@XmlElement(name = "connector", namespace = Xmile.NS)
	XmiElementStyle connectorStyle;

	public XmiElementStyle flowStyle() {
		return flowStyle;
	}

	public XmiElementStyle stockStyle() {
		return stockStyle;
	}

	public XmiElementStyle auxStyle() {
		return auxStyle;
	}

	public XmiElementStyle textBoxStyle() {
		return textBoxStyle;
	}

	public XmiElementStyle connectorStyle() {
		return connectorStyle;
	}

	public XmiViewStyle withFlowStyle(XmiElementStyle flowStyle) {
		this.flowStyle = flowStyle;
		return this;
	}

	public XmiElementStyle withFlowStyle() {
		if (flowStyle == null) {
			flowStyle = new XmiElementStyle();
		}
		return flowStyle;
	}

	public XmiViewStyle withStockStyle(XmiElementStyle stockStyle) {
		this.stockStyle = stockStyle;
		return this;
	}

	public XmiElementStyle withStockStyle() {
		if (stockStyle == null) {
			stockStyle = new XmiElementStyle();
		}
		return stockStyle;
	}

	public XmiViewStyle withAuxStyle(XmiElementStyle auxStyle) {
		this.auxStyle = auxStyle;
		return this;
	}

	public XmiElementStyle withAuxStyle() {
		if (auxStyle == null) {
			auxStyle = new XmiElementStyle();
		}
		return auxStyle;
	}

	public XmiViewStyle withTextBoxStyle(XmiElementStyle textBoxStyle) {
		this.textBoxStyle = textBoxStyle;
		return this;
	}

	public XmiElementStyle withTextBoxStyle() {
		if (textBoxStyle == null) {
			textBoxStyle = new XmiElementStyle();
		}
		return textBoxStyle;
	}

	public XmiViewStyle withConnectorStyle(XmiElementStyle connectorStyle) {
		this.connectorStyle = connectorStyle;
		return this;
	}

	public XmiElementStyle withConnectorStyle() {
		if (connectorStyle == null) {
			connectorStyle = new XmiElementStyle();
		}
		return connectorStyle;
	}
}
