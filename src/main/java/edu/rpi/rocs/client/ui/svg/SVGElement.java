package edu.rpi.rocs.client.ui.svg;

import com.google.gwt.dom.client.Element;

public interface SVGElement {
	public Element getElement();
	public void setID(String id);
	public String getID(String id);
	public void setX(String x);
	public String getX();
	public void setY(String y);
	public String getY();
	public void setWidth(String w);
	public String getWidth();
	public void setHeight(String h);
	public String getHeight();
	public void attach();
	public void detach();
	public void setSVGParent(SVGCanvas parent);
	public SVGCanvas getSVGParent();
}
