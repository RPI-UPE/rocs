package edu.rpi.rocs.client.ui.svg;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;

public class SVGTextWidget extends Widget implements SVGPrimitive, IsColorable, IsSVGText {

	Element m_element;
	String m_x,m_y,m_w,m_h;
	String m_text, m_fill, m_stroke, m_stroke_width;
	TextAnchor m_anchor;
	
	public SVGTextWidget() {
		this("", TextAnchor.Inherit);
	}
	
	public SVGTextWidget(String text) {
		this(text, TextAnchor.Inherit);
	}
	
	public SVGTextWidget(String text, TextAnchor anchor) {
		if(text==null) text = "";
		if(anchor==null) anchor = TextAnchor.Inherit;
		m_text = text;
		m_anchor = anchor;
		Log.trace("Creating text element");
		m_element = SVGCanvasWidget.createElementNS(SVGCanvasWidget.NS, "text");
		setElement(m_element);
		Log.trace("Setting text");
		m_element.setInnerText(text);
		Log.trace("Setting text-anchor attribute");
		m_element.setAttribute("text-anchor", anchor.toString());
	}
	
	public void attach() {
		onAttach();
	}

	public void detach() {
		onDetach();
	}

	public String getHeight() {
		return m_h;
	}

	public String getID(String id) {
		return m_element.getId();
	}

	public String getWidth() {
		return m_w;
	}

	public String getX() {
		return m_x;
	}

	public String getY() {
		return m_y;
	}

	public void setHeight(String h) {
		m_element.setAttribute("height", h);
		m_h = h;
	}

	public void setID(String id) {
		m_element.setId(id);
	}

	public void setWidth(String w) {
		m_element.setAttribute("width", w);
		m_w = w;
	}

	public void setX(String x) {
		m_element.setAttribute("x", x);
		m_x = x;
	}

	public void setY(String y) {
		m_element.setAttribute("y", y);
		m_y = y;
	}

	public String getFillColor() {
		return m_fill;
	}

	public String getStrokeColor() {
		return m_stroke;
	}

	public String getStrokeWidth() {
		return m_stroke_width;
	}

	public void setFillColor(String color) {
		m_element.setAttribute("fill", color);
		m_fill = color;
	}

	public void setStrokeColor(String color) {
		m_element.setAttribute("stroke", color);
		m_stroke = color;
	}

	public void setStrokeWidth(String width) {
		m_element.setAttribute("stroke-width", width);
		m_stroke_width = width;
	}

	public String getText() {
		return m_text;
	}

	public TextAnchor getTextAnchor() {
		return m_anchor;
	}

	public void setText(String text) {
		m_element.setInnerText(text);
		m_text = text;
	}

	public void setTextAnchor(TextAnchor a) {
		m_element.setAttribute("text-anchor", a.toString());
		m_anchor = a;
	}

}
