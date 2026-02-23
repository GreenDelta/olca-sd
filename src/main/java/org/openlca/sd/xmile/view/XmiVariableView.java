package org.openlca.sd.xmile.view;

import jakarta.xml.bind.annotation.XmlAttribute;

abstract class XmiVariableView<T extends XmiVariableView<T>> extends XmiBasicView<T> {

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
