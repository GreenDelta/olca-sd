package org.openlca.sd.xmile;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openlca.commons.Res;

import jakarta.xml.bind.JAXB;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "xmile", namespace = Xmile.NS)
@XmlAccessorType(XmlAccessType.FIELD)
public class Xmile {

	public static final String NS = "http://docs.oasis-open.org/xmile/ns/XMILE/v1.0";

	@XmlElement(name = "header", namespace = NS)
	XmiHeader header;

	@XmlElement(name = "sim_specs", namespace = NS)
	XmiSimSpecs simSpecs;

	@XmlElement(name = "model", namespace = NS)
	XmiModel model;

	@XmlElementWrapper(name = "dimensions", namespace = NS)
	@XmlElement(name = "dim", namespace = Xmile.NS)
	List<XmiDim> dims;

	public static Res<Xmile> readFrom(File file) {
		try (var stream = new FileInputStream(file);
				 var buffer = new BufferedInputStream(stream)) {
			return readFrom(buffer);
		} catch (Exception e) {
			return Res.error("Error reading XMILE file: " + file, e);
		}
	}

	public static Res<Xmile> readFrom(InputStream stream) {
		try {
			var xmile = JAXB.unmarshal(stream, Xmile.class);
			return Res.ok(xmile);
		} catch (Exception e) {
			return Res.error("Error reading XMILE stream", e);
		}
	}

	public XmiHeader header() {
		return header;
	}

	public XmiSimSpecs simSpecs() {
		return simSpecs;
	}

	public List<XmiDim> dims() {
		return dims != null ? dims : Collections.emptyList();
	}

	public XmiModel model() {
		return model;
	}

	public Xmile withHeader(XmiHeader header) {
		this.header = header;
		return this;
	}

	public Xmile withSimSpecs(XmiSimSpecs simSpecs) {
		this.simSpecs = simSpecs;
		return this;
	}

	public Xmile withModel(XmiModel model) {
		this.model = model;
		return this;
	}

	public List<XmiDim> withDims() {
		if (dims == null || dims.isEmpty()) {
			dims = new ArrayList<>();
		}
		return dims;
	}
}
