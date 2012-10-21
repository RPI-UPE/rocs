package edu.rpi.rocs.client.ui.svg;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;

public class SVGPathWidget extends Widget implements SVGPrimitive, IsColorable, Collection<SVGPathComponent> {
	String m_x,m_y,m_w,m_h;
	String m_fill, m_stroke, m_stroke_width;
	Element m_element;
	ArrayList<String> m_stroke_dashes;
	SVGCanvas m_parent;
	ArrayList<SVGPathComponent> m_components = new ArrayList<SVGPathComponent>();
		
	public SVGPathWidget() {
		m_element = SVGCanvasWidget.createElementNS(SVGCanvasWidget.NS, "path");
		setElement(m_element);
	}
	
	public void attach() {
		onAttach();
	}

	public void detach() {
		onDetach();
	}

	public String getHeight() {
		return m_x;
	}

	public String getID(String id) {
		return m_element.getId();
	}

	public SVGCanvas getSVGParent() {
		return m_parent;
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
		m_element.setAttribute("height", m_h);
	}

	public void setID(String id) {
		m_element.setId(id);
	}

	public void setSVGParent(SVGCanvas parent) {
		m_parent = parent;
	}
	
	public void setWidth(String w) {
		m_w = w;
		m_element.setAttribute("width", m_w);
	}

	public void setX(String x) {
		m_x = x;
		m_element.setAttribute("x", m_x);
	}

	public void setY(String y) {
		m_y = y;
		m_element.setAttribute("y", m_y);
	}

	public String getFillColor() {
		return m_fill;
	}

	public String getStrokeColor() {
		return m_stroke;
	}

	public ArrayList<String> getStrokeDashArray() {
		return m_stroke_dashes;
	}

	public String getStrokeWidth() {
		return m_stroke_width;
	}

	public void setFillColor(String color) {
		m_fill = color;
		m_element.setAttribute("fill", m_fill);
	}

	public void setStrokeColor(String color) {
		m_stroke = color;
		m_element.setAttribute("stroke", m_stroke);
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

	public void setStrokeWidth(String width) {
		m_stroke_width = width;
		m_element.setAttribute("stroke-width", m_stroke_width);
	}

	public boolean add(SVGPathComponent e) {
		boolean result = m_components.add(e);
		updatePathData();
		return result;
	}

	private void updatePathData() {
		String d="";
		for(int i=0;i<m_components.size();i++) {
			d += m_components.get(i);
		}
		m_element.setAttribute("d", d);
	}

	public boolean addAll(Collection<? extends SVGPathComponent> c) {
		boolean result = m_components.addAll(c);
		updatePathData();
		return result;
	}

	public void clear() {
		m_components.clear();
		m_element.removeAttribute("d");
	}

	public boolean contains(Object o) {
		return m_components.contains(o);
	}

	public boolean containsAll(Collection<?> c) {
		return m_components.containsAll(c);
	}

	public boolean isEmpty() {
		return m_components.isEmpty();
	}

	public Iterator<SVGPathComponent> iterator() {
		return m_components.iterator();
	}

	public boolean remove(Object o) {
		boolean result = m_components.remove(o);
		updatePathData();
		return result;
	}

	public boolean removeAll(Collection<?> c) {
		boolean result = m_components.removeAll(c);
		updatePathData();
		return result;
	}

	public boolean retainAll(Collection<?> c) {
		boolean result = m_components.retainAll(c);
		updatePathData();
		return result;
	}

	public int size() {
		return m_components.size();
	}

	public Object[] toArray() {
		return m_components.toArray();
	}

	public <T> T[] toArray(T[] a) {
		return m_components.toArray(a);
	}

}
