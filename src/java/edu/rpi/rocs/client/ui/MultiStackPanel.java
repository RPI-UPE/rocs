package edu.rpi.rocs.client.ui;

import java.util.HashSet;

import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.StackPanel;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;

public class MultiStackPanel extends StackPanel {
	private static final String DEFAULT_STYLENAME = "gwt-MultiStackPanel";
	private static final String DEFAULT_ITEM_STYLENAME = DEFAULT_STYLENAME+"Item";
	
	private Element m_table;
	private Element m_tbody;
	
	private HashSet<Integer> m_selected=new HashSet<Integer>();
	private boolean programmatic=false;
	
	public interface MultiStackPanelVisibilityChangeHandler {
		public void onChange(int index, boolean visible);
	}
	
	private HashSet<MultiStackPanelVisibilityChangeHandler> changeHandlers
		= new HashSet<MultiStackPanelVisibilityChangeHandler>();
	
	public void addChangeHandler(MultiStackPanelVisibilityChangeHandler h) {
		changeHandlers.add(h);
	}
	
	public void removeChangeHandler(MultiStackPanelVisibilityChangeHandler h) {
		changeHandlers.remove(h);
	}
	
	public MultiStackPanel() {
		m_table = getElement();
		m_tbody = (Element)m_table.getFirstChildElement();
		setStyleName(DEFAULT_STYLENAME);
	}
	
	@Override
	public void add(Widget w) {
		insert(w, getWidgetCount());
	}
	
	@Override
	public void add(Widget w, String stackText, boolean asHTML) {
		add(w);
		setStackText(getWidgetCount() - 1, stackText, asHTML);
	}
	
	public HashSet<Integer> getSelectedIndices() {
		return new HashSet<Integer>(m_selected);
	}
	
	public void insert(Widget w, int beforeIndex) {
		// Header
		Element trh = DOM.createTR();
		Element tdh = DOM.createTD();
		DOM.appendChild(trh, tdh);
		DOM.appendChild(tdh, DOM.createDiv());
		
		// body
		Element trb = DOM.createTR();
		Element tdb = DOM.createTD();
		DOM.appendChild(trb, tdb);
		
		// DOM indices are 2x logical indices; 2 dom elements per stack item
		beforeIndex = adjustIndex(w, beforeIndex);
		int effectiveIndex = beforeIndex * 2;
		
		// This ordering puts the body below the header
		DOM.insertChild(m_tbody, trb, effectiveIndex);
		DOM.insertChild(m_tbody, trh, effectiveIndex);
		
		// header styling
		setStyleName(tdh, DEFAULT_ITEM_STYLENAME, true);
		DOM.setElementPropertyInt(tdh, "__owner", hashCode());
		DOM.setElementProperty(tdh, "height", "1px");
		
		// body styling
		setStyleName(tdb, DEFAULT_ITEM_STYLENAME+"Content", true);
		DOM.setElementProperty(tdb, "height", "100%");
		DOM.setElementProperty(tdb, "vAlign", "top");
		
		insert(w, tdb, beforeIndex, false);
		updateIndicesFrom(beforeIndex);
		
		if(m_selected.size()==0) {
			programmatic=true;
			showStack(0);
			programmatic=false;
		}
		else {
			programmatic=true;
			setStackVisible(beforeIndex, false);
			programmatic=false;
		}
	}
	
	@Override
	public boolean remove(int index) {
		return remove(getWidget(index), index);
	}
	
	@Override
	public boolean remove(Widget child) {
		return remove(child, getWidgetIndex(child));
	}
	
	public void setStackText(int index, String text) {
		setStackText(index, text, false);
	}
	
	public void setStackText(int index, String text, boolean asHTML) {
		if(index >= getWidgetCount()) {
			return;
		}
		
		Element tdWrapper = DOM.getChild(DOM.getChild(m_tbody, index*2), 0);
		Element headerElem = DOM.getFirstChild(tdWrapper);
		if(asHTML) {
			DOM.setInnerHTML(headerElem, text);
		}
		else {
			DOM.setInnerText(headerElem, text);
		}
	}
	
	public void showStack(int index) {
		if((index>=getWidgetCount()) || (index < 0)) {
			return;
		}
		if(m_selected.contains(new Integer(index))) {
			m_selected.remove(new Integer(index));
			setStackVisible(index, false);
		}
		else {
			m_selected.add(new Integer(index));
			setStackVisible(index, true);
		}
	}
		
	private boolean remove(Widget child, int index) {
		 boolean removed = super.remove(child);
		 if(removed) {
			 int rowIndex = 2 * index;
			 Element tr = DOM.getChild(m_tbody, rowIndex);
			 DOM.removeChild(m_tbody, tr);
			 tr = DOM.getChild(m_tbody, rowIndex);
			 DOM.removeChild(m_tbody, tr);
			 
			 // Update visible item indices
			 HashSet<Integer> temp = new HashSet<Integer>();
			 for(Integer i : m_selected) {
				 if(i.intValue() == index) continue;
				 if(i.intValue() > index)
					 temp.add(new Integer(i.intValue()-1));
				 else
					 temp.add(i);
			 }
			 m_selected = temp;
		 }
		 return removed;
	}
	
	private void setStackContentVisible(int index, boolean visible) {
		Element tr = DOM.getChild(m_tbody, (index * 2)+1);
		UIObject.setVisible(tr, visible);
		getWidget(index).setVisible(visible);
	}
	
	private void setStackVisible(int index, boolean visible) {
		Element tr = DOM.getChild(m_tbody, (index * 2));
		if(tr == null) {
			return;
		}
		
		// Style the stack selector item
		Element td = DOM.getFirstChild(tr);
		setStyleName(td, DEFAULT_ITEM_STYLENAME+"-selected", visible);
		
		// Show/hide the contained widget.
		setStackContentVisible(index, visible);
		
		Element trNext = DOM.getChild(m_tbody, ((index+1)*2));
		if(trNext != null) {
			Element tdNext = DOM.getFirstChild(trNext);
			setStyleName(tdNext, DEFAULT_ITEM_STYLENAME+"-below-selected", visible);
		}
		
		if(!programmatic) {
			for(MultiStackPanelVisibilityChangeHandler h : changeHandlers) {
				h.onChange(index, visible);
			}
		}
	}
	
	private void updateIndicesFrom(int beforeIndex) {
		for(int i=beforeIndex, c = getWidgetCount();i<c;++i) {
			Element childTR = DOM.getChild(m_tbody, i*2);
			Element childTD = DOM.getFirstChild(childTR);
			DOM.setElementPropertyInt(childTD, "__index", i);
			
			if(beforeIndex == 0) {
				setStyleName(childTD, DEFAULT_ITEM_STYLENAME+"-first", true);
			}
			else {
				setStyleName(childTD, DEFAULT_ITEM_STYLENAME+"-first", false);
			}
		}
	}
	
	public boolean isVisible(int index) {
		if(index < 0 || index >= getWidgetCount()) return false;
		return m_selected.contains(new Integer(index));
	}
}
