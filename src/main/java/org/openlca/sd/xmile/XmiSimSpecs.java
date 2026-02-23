package org.openlca.sd.xmile;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
public class XmiSimSpecs {

	@XmlAttribute(name = "method")
	String method;

	@XmlAttribute(name = "time_units")
	String timeUnits;

	@XmlElement(name = "start", namespace = Xmile.NS)
	Double start;

	@XmlElement(name = "stop", namespace = Xmile.NS)
	Double stop;

	@XmlElement(name = "dt", namespace = Xmile.NS)
	DeltaT dt;

	public String method() {
		return method;
	}

	public String timeUnits() {
		return timeUnits;
	}

	public Double start() {
		return start;
	}

	public Double stop() {
		return stop;
	}

	public DeltaT dt() {
		return dt;
	}

	public XmiSimSpecs withMethod(String method) {
		this.method = method;
		return this;
	}

	public XmiSimSpecs withTimeUnits(String timeUnits) {
		this.timeUnits = timeUnits;
		return this;
	}

	public XmiSimSpecs withStart(Double start) {
		this.start = start;
		return this;
	}

	public XmiSimSpecs withStop(Double stop) {
		this.stop = stop;
		return this;
	}

	public XmiSimSpecs withDt(DeltaT dt) {
		this.dt = dt;
		return this;
	}

	public DeltaT withDt() {
		if (dt == null) {
			dt = new DeltaT();
		}
		return dt;
	}

	@XmlAccessorType(XmlAccessType.FIELD)
	public static class DeltaT {

		@XmlAttribute(name = "reciprocal")
		Boolean reciprocal;

		@XmlValue
		Double value;

		public Boolean reciprocal() {
			return reciprocal;
		}

		public Double value() {
			return value;
		}

		public boolean isReciprocal() {
			return reciprocal != null && reciprocal;
		}

		public DeltaT withReciprocal(Boolean reciprocal) {
			this.reciprocal = reciprocal;
			return this;
		}

		public DeltaT withValue(Double value) {
			this.value = value;
			return this;
		}
	}
}
