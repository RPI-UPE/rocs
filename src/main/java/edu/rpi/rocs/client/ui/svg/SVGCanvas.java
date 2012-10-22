package edu.rpi.rocs.client.ui.svg;

public interface SVGCanvas extends SVGElement {
	public void addSVGElement(SVGElement e);
	public void removeSVGElement(SVGElement e);
	public int count();
	public SVGElement getChildElement(int i);
}
