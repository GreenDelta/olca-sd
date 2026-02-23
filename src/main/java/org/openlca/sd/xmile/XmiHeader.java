package org.openlca.sd.xmile;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class XmiHeader {

	@XmlElement(name = "name", namespace = Xmile.NS)
	String name;

	@XmlElement(name = "uuid", namespace = Xmile.NS)
	String uuid;

	@XmlElement(name = "vendor", namespace = Xmile.NS)
	String vendor;

	public String name() {
		return name;
	}

	public String uuid() {
		return uuid;
	}

	public String vendor() {
		return vendor;
	}

	public XmiHeader withName(String name) {
		this.name = name;
		return this;
	}

	public XmiHeader withUuid(String uuid) {
		this.uuid = uuid;
		return this;
	}

	public XmiHeader withVendor(String vendor) {
		this.vendor = vendor;
		return this;
	}
}
