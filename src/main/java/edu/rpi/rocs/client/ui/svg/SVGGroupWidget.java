package edu.rpi.rocs.client.ui.svg;

import java.util.ArrayList;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;

public class SVGGroupWidget extends Widget implements IsColorable, SVGCanvas {
	private String m_fill;
	private String m_stroke;
	private String m_stroke_width;
	private ArrayList<SVGElement> m_children;
	private Element m_element;
	private String m_x,m_y,m_w,m_h;
	private SVGCanvas m_parent;
	private ArrayList<String> m_stroke_dashes;
	
	public SVGGroupWidget() {
		m_element = SVGCanvasWidget.createElementNS(SVGCanvasWidget.NS, "g");
		m_children = new ArrayList<SVGElement>();
		setElement(m_element);
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
		m_fill = color;
		m_element.setPropertyString("fill", m_fill);
	}

	public void setStrokeColor(String color) {
		m_stroke = color;
		m_element.setPropertyString("stroke", m_stroke);
	}

	public void setStrokeWidth(String width) {
		m_stroke_width = width;
		m_element.setPropertyString("stroke-width", m_stroke_width);
	}

	public void addSVGElement(SVGElement e) {
		if(m_children.contains(e)) return;
		m_children.add(e);
		m_element.appendChild(e.getElement());
		if(isAttached()) {
			e.attach();
		}
	}

	public int count() {
		return m_children.size();
	}

	public SVGElement getChildElement(int i) {
		return m_children.get(i);
	}

	public void removeSVGElement(SVGElement e) {
		if(m_children.contains(e)) {
			m_element.removeChild(e.getElement());
			if(isAttached())
				e.detach();
		}
	}
	
	@Override
	protected void doAttachChildren() {
		for(SVGElement e : m_children) {
			e.attach();
		}
	}
	
	@Override
	protected void doDetachChildren() {
		for(SVGElement e : m_children) {
			e.detach();
		}
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
		m_h = h;
		m_element.setPropertyString("height", m_h);
	}

	public void setID(String id) {
		m_element.setId(id);
	}
	
	public void setWidth(String w) {
		m_w = w;
		m_element.setPropertyString("width", m_w);
	}

	public void setX(String x) {
		m_x = x;
		m_element.setPropertyString("x", m_x);
	}

	public void setY(String y) {
		m_y = y;
		m_element.setPropertyString("y", m_y);
	}

	public SVGCanvas getSVGParent() {
		return m_parent;
	}

	public void setSVGParent(SVGCanvas parent) {
		m_parent = parent;
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
}
