package org.openlca.sd.xmile;

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
}
