package org.openlca.sd.xmile;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
public class XmiPoints {

	@XmlAttribute(name = "sep")
	String sep;

	@XmlValue
	String values;

	public String sep() {
		return sep;
	}

	public String values() {
		return values;
	}

	public XmiPoints withSep(String sep) {
		this.sep = sep;
		return this;
	}

	public XmiPoints withValues(String values) {
		this.values = values;
		return this;
	}

	public double[] parse() {
		if (values == null || values.isBlank())
			return new double[0];
		var sp = sep != null && !sep.isBlank() ? sep : ",";
		var parts = values.split(sp);
		var num = new double[parts.length];
		for (int i = 0; i < parts.length; i++) {
			try {
				num[i] = Double.parseDouble(parts[i]);
			} catch (Exception e) {
				num[i] = Double.NaN;
			}
		}
		return num;
	}
}
