package org.openlca.sd.xmile.view;

import java.util.ArrayList;
import java.util.List;

import org.openlca.sd.xmile.Xmile;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class XmiView {

	@XmlAttribute(name = "page_width")
	Integer pageWidth;

	@XmlAttribute(name = "page_height")
	Integer pageHeight;

	@XmlAttribute(name = "zoom")
	Integer zoom;

	@XmlElement(name = "stock", namespace = Xmile.NS)
	List<XmiStockView> stocks;

	@XmlElement(name = "aux", namespace = Xmile.NS)
	List<XmiAuxView> auxiliaries;

	@XmlElement(name = "text_box", namespace = Xmile.NS)
	List<XmiTextBoxView> textBoxes;

	@XmlElement(name = "flow", namespace = Xmile.NS)
	List<XmiFlowView> flows;

	@XmlElement(name = "connector", namespace = Xmile.NS)
	List<XmiConnectorView> connectors;

	@XmlElement(name = "style", namespace = Xmile.NS)
	XmiViewStyle style;

	public List<XmiStockView> stocks() {
		return stocks == null ? List.of() : stocks;
	}

	public List<XmiAuxView> auxiliaries() {
		return auxiliaries == null ? List.of() : auxiliaries;
	}

	public List<XmiTextBoxView> textBoxes() {
		return textBoxes == null ? List.of() : textBoxes;
	}

	public List<XmiFlowView> flows() {
		return flows == null ? List.of() : flows;
	}

	public List<XmiConnectorView> connectors() {
		return connectors == null ? List.of() : connectors;
	}

	public Integer pageWidth() {
		return pageWidth;
	}

	public Integer pageHeight() {
		return pageHeight;
	}

	public Integer zoom() {
		return zoom;
	}

	public XmiViewStyle style() {
		return style;
	}

	public XmiView withPageWidth(Integer pageWidth) {
		this.pageWidth = pageWidth;
		return this;
	}

	public XmiView withPageHeight(Integer pageHeight) {
		this.pageHeight = pageHeight;
		return this;
	}

	public XmiView withZoom(Integer zoom) {
		this.zoom = zoom;
		return this;
	}

	public XmiView withStocks(List<XmiStockView> stocks) {
		this.stocks = stocks;
		return this;
	}

	public List<XmiStockView> withStocks() {
		if (stocks == null) {
			stocks = new ArrayList<>();
		}
		return stocks;
	}

	public XmiView withAuxiliaries(List<XmiAuxView> auxiliaries) {
		this.auxiliaries = auxiliaries;
		return this;
	}

	public List<XmiAuxView> withAuxiliaries() {
		if (auxiliaries == null) {
			auxiliaries = new ArrayList<>();
		}
		return auxiliaries;
	}

	public XmiView withTextBoxes(List<XmiTextBoxView> textBoxes) {
		this.textBoxes = textBoxes;
		return this;
	}

	public List<XmiTextBoxView> withTextBoxes() {
		if (textBoxes == null) {
			textBoxes = new ArrayList<>();
		}
		return textBoxes;
	}

	public XmiView withFlows(List<XmiFlowView> flows) {
		this.flows = flows;
		return this;
	}

	public List<XmiFlowView> withFlows() {
		if (flows == null) {
			flows = new ArrayList<>();
		}
		return flows;
	}

	public XmiView withConnectors(List<XmiConnectorView> connectors) {
		this.connectors = connectors;
		return this;
	}

	public List<XmiConnectorView> withConnectors() {
		if (connectors == null) {
			connectors = new ArrayList<>();
		}
		return connectors;
	}

	public XmiView withStyle(XmiViewStyle style) {
		this.style = style;
		return this;
	}

	public XmiViewStyle withStyle() {
		if (style == null) {
			style = new XmiViewStyle();
		}
		return style;
	}

}
