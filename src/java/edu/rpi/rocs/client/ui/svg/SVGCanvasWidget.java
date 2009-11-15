package edu.rpi.rocs.client.ui.svg;

import java.util.ArrayList;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;

public class SVGCanvasWidget extends Widget implements SVGCanvas {
	Element m_element;
	String m_x,m_y,m_w,m_h;
	ArrayList<SVGElement> m_children;
	
	public static final String NS="http://www.w3.org/2000/svg";

	public static native Element createElementNS(final String ns, final String name)/*-{
		return document.createElementNS(ns, name);
	}-*/;
	
	public SVGCanvasWidget(String id, String w, String h) {
		m_element = createElementNS(NS, "svg");
		m_element.setId(id);
		m_element.setAttribute("width", w);
		m_element.setAttribute("height", h);
		m_element.setAttribute("version", "1.1");
		setElement(m_element);
		
		m_x="";
		m_y="";
		m_w=w;
		m_h=h;
		m_children = new ArrayList<SVGElement>();
	}
	
	public void addSVGElement(SVGElement e) {
		if(!m_children.contains(e)) {
			m_children.add(e);
			m_element.appendChild(e.getElement());
			e.setSVGParent(this);
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

	public int count() {
		return m_children.size();
	}

	public SVGElement getChildElement(int i) {
		return m_children.get(i);
	}

	public void removeSVGElement(SVGElement e) {
		if(m_children.contains(e)) {
			e.setSVGParent(null);
			m_children.remove(e);
			m_element.removeChild(e.getElement());
		}
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
		m_element.setAttribute("height", h);
	}

	public void setID(String id) {
		m_element.setId(id);
	}

	public void setWidth(String w) {
		m_w = w;
		m_element.setAttribute("width", w);
	}

	public void setX(String x) {
		m_x = x;
		m_element.setAttribute("x", x);
	}

	public void setY(String y) {
		m_y = y;
		m_element.setAttribute("y", y);
	}

	public void attach() {
		// TODO Auto-generated method stub
		
	}

	public void detach() {
		// TODO Auto-generated method stub
		
	}

	public SVGCanvas getSVGParent() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setSVGParent(SVGCanvas parent) {
		// TODO Auto-generated method stub
		
	}
}
