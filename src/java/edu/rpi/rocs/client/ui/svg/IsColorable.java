package edu.rpi.rocs.client.ui.svg;

public interface IsColorable {
	public void setFillColor(String color);
	public void setStrokeColor(String color);
	public void setStrokeWidth(String width);
	public String getFillColor();
	public String getStrokeColor();
	public String getStrokeWidth();
}
