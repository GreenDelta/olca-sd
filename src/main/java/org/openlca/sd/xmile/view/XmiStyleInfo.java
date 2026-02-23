package org.openlca.sd.xmile.view;

import jakarta.xml.bind.annotation.XmlAttribute;

public abstract class XmiStyleInfo<T extends XmiStyleInfo<T>> {

	@XmlAttribute(name = "color")
	String color;

	@XmlAttribute(name = "background")
	String background;

	@XmlAttribute(name = "font_family")
	String fontFamily;

	@XmlAttribute(name = "font_size")
	String fontSize;

	@XmlAttribute(name = "font_color")
	String fontColor;

	@XmlAttribute(name = "padding")
	Integer padding;

	@XmlAttribute(name = "label_side")
	String labelSide;

	public String color() {
		return color;
	}

	public String background() {
		return background;
	}

	public String fontSize() {
		return fontSize;
	}

	public String fontFamily() {
		return fontFamily;
	}

	public String fontColor() {
		return fontColor;
	}

	public Integer padding() {
		return padding;
	}

	public String labelSide() {
		return labelSide;
	}

	@SuppressWarnings("unchecked")
	public T withColor(String color) {
		this.color = color;
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public T withBackground(String background) {
		this.background = background;
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public T withFontFamily(String fontFamily) {
		this.fontFamily = fontFamily;
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public T withFontSize(String fontSize) {
		this.fontSize = fontSize;
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public T withFontColor(String fontColor) {
		this.fontColor = fontColor;
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public T withPadding(Integer padding) {
		this.padding = padding;
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public T withLabelSide(String labelSide) {
		this.labelSide = labelSide;
		return (T) this;
	}

}
