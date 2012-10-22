package edu.rpi.rocs.client.ui.svg;

import java.util.ArrayList;

public interface IsColorable {
	public void setFillColor(String color);
	public void setStrokeColor(String color);
	public void setStrokeWidth(String width);
	public void setStrokeDashArray(ArrayList<String> dashes);
	public String getFillColor();
	public String getStrokeColor();
	public String getStrokeWidth();
	public ArrayList<String> getStrokeDashArray();
}
