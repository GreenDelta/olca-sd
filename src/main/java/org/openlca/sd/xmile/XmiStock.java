package org.openlca.sd.xmile;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public final class XmiStock extends XmiEvaluatable<XmiStock> {

	@XmlElement(name = "inflow", namespace = Xmile.NS)
	List<String> inflows;

	@XmlElement(name = "outflow", namespace = Xmile.NS)
	List<String> outflows;

	public List<String> inflows() {
		return inflows != null ? inflows : List.of();
	}

	public List<String> outflows() {
		return outflows != null ? outflows : List.of();
	}

	public XmiStock withInflows(List<String> inflows) {
		this.inflows = inflows;
		return this;
	}

	public List<String> withInflows() {
		if (inflows == null) {
			inflows = new ArrayList<>();
		}
		return inflows;
	}

	public XmiStock withOutflows(List<String> outflows) {
		this.outflows = outflows;
		return this;
	}

	public List<String> withOutflows() {
		if (outflows == null) {
			outflows = new ArrayList<>();
		}
		return outflows;
	}
}
