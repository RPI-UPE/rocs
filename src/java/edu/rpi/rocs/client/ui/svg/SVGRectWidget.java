package edu.rpi.rocs.client.ui.svg;

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
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Widget;

public class SVGRectWidget extends Widget implements SVGPrimitive, IsColorable,
		HasClickHandlers, HasMouseOutHandlers, HasMouseOverHandlers {

	Element m_element;
	String m_x,m_y,m_w,m_h;
	String m_fill,m_stroke,m_stroke_width;
	HandlerManager m_handlerManager;
	
	public SVGRectWidget() {
		m_element = SVGCanvasWidget.createElementNS(SVGCanvasWidget.NS, "rect");
		setElement(m_element);
		//sinkEvents(Event.ONCLICK | Event.ONMOUSEOUT | Event.ONMOUSEOVER);
	}
	
	public SVGRectWidget(String id) {
		this();
		m_element.setId(id);
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

	public HandlerRegistration addClickHandler(ClickHandler arg0) {
		return addDomHandler(arg0, ClickEvent.getType());
	}

	public HandlerRegistration addMouseOutHandler(MouseOutHandler arg0) {
		return addDomHandler(arg0, MouseOutEvent.getType());
	}

	public HandlerRegistration addMouseOverHandler(MouseOverHandler arg0) {
		return addDomHandler(arg0, MouseOverEvent.getType());
	}

	public void attach() {
		// TODO Auto-generated method stub
		onAttach();
	}

	public void detach() {
		// TODO Auto-generated method stub
		onDetach();
	}
}
