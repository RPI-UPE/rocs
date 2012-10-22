package edu.rpi.rocs.client.ui.svg;

import java.util.ArrayList;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasMouseOutHandlers;
import com.google.gwt.event.dom.client.HasMouseOverHandlers;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Widget;

public class SVGTextWidget extends Widget implements SVGPrimitive, IsColorable, IsSVGText, 
		HasMouseOverHandlers, HasMouseOutHandlers, HasClickHandlers {

	Element m_element;
	String m_x,m_y,m_w,m_h;
	String m_text, m_fill, m_stroke, m_stroke_width;
	String m_font_family, m_font_size;
	TextAnchor m_anchor;
	SVGCanvas m_parent;
	ArrayList<String> m_stroke_dashes;
	
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
		m_element = SVGCanvasWidget.createElementNS(SVGCanvasWidget.NS, "text");
		setElement(m_element);
		m_element.setInnerText(text);
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

	public SVGCanvas getSVGParent() {
		return m_parent;
	}

	public void setSVGParent(SVGCanvas parent) {
		m_parent = parent;
	}

	public HandlerRegistration addClickHandler(ClickHandler arg0) {
		return addDomHandler(arg0, ClickEvent.getType());
	}

	public HandlerRegistration addMouseOutHandler(MouseOutHandler arg0) {
		return addDomHandler(arg0, MouseOutEvent.getType());
	}

	public HandlerRegistration addMouseOverHandler(MouseOverHandler arg0) {
		return addDomHandler(arg0, MouseOverEvent.getType());
	}

	public ArrayList<String> getStrokeDashArray() {
		return m_stroke_dashes;
	}

	public void setStrokeDashArray(ArrayList<String> dashes) {
		m_stroke_dashes = dashes;
		if(dashes!=null) {
			String result = dashes.get(0);
			for(int i=1;i<dashes.size();i++) {
				result += ","+dashes.get(i);
			}
			m_element.setAttribute("stroke-dasharray", result);
		}
		else {
			m_element.removeAttribute("stroke-dasharray");
		}
	}

	public String getFontFamily() {
		return m_font_family;
	}

	public String getFontSize() {
		return m_font_size;
	}

	public void setFontFamily(String font) {
		m_font_family = font;
		m_element.setAttribute("font-family", font);
	}

	public void setFontSize(String size) {
		m_font_size = size;
		m_element.setAttribute("font-size", size);
	}

}
