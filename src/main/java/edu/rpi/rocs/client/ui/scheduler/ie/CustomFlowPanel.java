package edu.rpi.rocs.client.ui.scheduler.ie;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.FlowPanel;

public class CustomFlowPanel extends FlowPanel {
	public void setLeft(String x) {
		setLeftPos(getElement(), x);
	}
	
	public void setTop(String y) {
		setTopPos(getElement(), y);
	}
	
	@Override
	public void setHeight(String height) {
		super.setHeight(height);
		setHeightSize(getElement(), height);
	}
	
	@Override
	public void setSize(String width, String height) {
		super.setSize(width, height);
		setHeightSize(getElement(), height);
	}
	
	private native void setLeftPos(Element e, String left)/*-{
		e.style.position = 'absolute';
		e.style.left = left;
	}-*/;
	
	private native void setTopPos(Element e, String top)/*-{
		e.style.position = 'absolute';
		e.style.top = top;
	}-*/;
	
	private native void setHeightSize(Element e, String height)/*-{
		e.style.height = height;
		e.style.lineHeight = height;
	}-*/;

	public void setPosition(String left, String top) {
		setLeft(left); setTop(top);
	}
	
	public void setBackgroundColor(String color) {
		setBGColor(getElement(), color);
	}
	
	private native void setBGColor(Element e, String color)/*-{
		e.style.backgroundColor = color;
	}-*/;
}
