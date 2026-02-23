package org.openlca.sd.xmile.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openlca.sd.xmile.Xmile;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;

@XmlAccessorType(XmlAccessType.FIELD)
public class XmiFlowView extends XmiVariableView<XmiFlowView> {

	@XmlElementWrapper(name = "pts", namespace = Xmile.NS)
	@XmlElement(name = "pt", namespace = Xmile.NS)
	List<Pt> pts;

	public List<Pt> pts() {
		return pts != null ? pts : Collections.emptyList();
	}

	public XmiFlowView withPts(List<Pt> pts) {
		this.pts = pts;
		return this;
	}

	public List<Pt> withPts() {
		if (pts == null) {
			pts = new ArrayList<>();
		}
		return pts;
	}

	@XmlAccessorType(XmlAccessType.FIELD)
	public static class Pt implements XmiViewPoint {

		@XmlAttribute(name = "x")
		double x;

		@XmlAttribute(name = "y")
		double y;

		public double x() {
			return x;
		}

		public double y() {
			return y;
		}

		public Pt withX(double x) {
			this.x = x;
			return this;
		}

		public Pt withY(double y) {
			this.y = y;
			return this;
		}
	}
}
