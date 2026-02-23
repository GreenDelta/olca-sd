package org.openlca.sd.xmile;

import jakarta.xml.bind.annotation.XmlAttribute;

public sealed abstract class XmiVariable<T extends XmiVariable<T>>
	permits XmiEvaluatable, XmiGf  {

	@XmlAttribute(name = "name")
	String name;

	public String name() {
		return name;
	}

	@SuppressWarnings("unchecked")
	public T withName(String name) {
		this.name = name;
		return (T) this;
	}
}
