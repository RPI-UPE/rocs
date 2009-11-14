package edu.rpi.rocs.client.ui.filters;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.HTMLTable.Cell;

import edu.rpi.rocs.client.objectmodel.Time;
import edu.rpi.rocs.client.ui.filters.helpers.SelectableTableWidget;

public class ScheduleTimeBlockFilterWidget extends SimplePanel {
	private SelectableTableWidget theTable = new SelectableTableWidget(2*(22-8), 7);
	private FlexTable table = new FlexTable();
	private final int start=8*2; // 8 am with 30 min blocks
	private final int end=22*2; // 10 pm with 30 min blocks
	private boolean[][] disabled=new boolean[7][end-start];
	
	private native void registerMouseEvents()/*-{
		window.mouseDown = 0;
		document.body.onmousedown = function() {
			++window.mouseDown;
		}
		document.body.onmouseup = function() {
			--window.mouseDown;
		}
	}-*/;
	
	private String dayOfWeek(int i) {
		switch(i) {
		case 0:
			return "Mon";
		case 1:
			return "Tues";
		case 2:
			return "Wed";
		case 3:
			return "Thurs";
		case 4:
			return "Fri";
		case 5:
			return "Sat";
		case 6:
			return "Sun";
		}
		return "Unknown";
	}
	
	public ScheduleTimeBlockFilterWidget() {
		/*
		registerMouseEvents();
		table.setWidth("100%");
		table.setHeight("100%");
		table.setHTML(0, 0, "Time");
		for(int i=start;i<end;i++) {
			if(i%2==0)
				table.setHTML(i-start+1, 0, Integer.toString(i/2)+":00");
			else
				table.setHTML(i-start+1, 0, "");
		}
		for(int i=1;i<8;i++) {
			table.setHTML(0, i, dayOfWeek(i-1));
			table.getFlexCellFormatter().setHorizontalAlignment(0, i, HasHorizontalAlignment.ALIGN_CENTER);
			for(int j=start;j<end;j++) {
				table.setHTML(j-start+1, i, "");
				table.getFlexCellFormatter().setStyleName(j-start+1, i, "timeAvailable");
				Element e = table.getFlexCellFormatter().getElement(j-start+1, i);
				CellMouseOverHandler h = new CellMouseOverHandler(j-start, i);
				h.registerHandler(e);
			}
		}
		table.addClickHandler(tableClickHandler);
		this.add(table);
		*/
		for(int i=0;i<7;i++) {
			theTable.setColumnHeader(i, dayOfWeek(i));
		}
		for(int i=0;i<2*(22-8);i++) {
			if(i%2==0) {
				theTable.setRowHeader(i, Integer.toString(8+i/2)+":00");
			}
			else {
				theTable.setRowHeader(i, "&nbsp;");
			}
		}
		this.add(theTable);
	}
	
	private ClickHandler tableClickHandler = new ClickHandler() {

		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			Cell c = table.getCellForEvent(event);
			if(c.getRowIndex()==0) return;
			if(c.getCellIndex()==0) return;
			String oldstyle, newstyle;
			int row, col;
			row = c.getRowIndex()-1;
			col = c.getCellIndex()-1;
			if(disabled[col][row]) {
				oldstyle = "timeBlocked";
				newstyle = "timeAvailable";
			}
			else {
				oldstyle = "timeAvailable";
				newstyle = "timeBlocked";
			}
			
			disabled[col][row] = !disabled[col][row];
			table.getFlexCellFormatter().removeStyleName(row+1, col+1, oldstyle);
			table.getFlexCellFormatter().addStyleName(row+1, col+1, newstyle);
		}
		
	};
	
	private class CellMouseOverHandler implements MouseOverHandler {
		int row,col;
		
		public CellMouseOverHandler(int cx, int cy) {
			row = cx;
			col = cy;
		}
		
		private native void registerHandler(Element e, CellMouseOverHandler x)/*-{
			e.addEventListener('mouseover', function (evt) {
				if(window.mouseDown>0) {
					x.@edu.rpi.rocs.client.ui.filters.ScheduleTimeBlockFilterWidget.CellMouseOverHandler::onMouseOver()();
				}
			},true);
		}-*/;
		
		public void registerHandler(Element e) {
			registerHandler(e, this);
		}
		
		@SuppressWarnings("unused")
		public void onMouseOver() {
			onMouseOver(null);
		}
		
		public void onMouseOver(MouseOverEvent arg0) {
			// TODO Auto-generated method stub
			String oldstyle, newstyle;
			if(disabled[col][row]) {
				oldstyle = "timeBlocked";
				newstyle = "timeAvailable";
			}
			else {
				oldstyle = "timeAvailable";
				newstyle = "timeBlocked";
			}
			disabled[col][row] = !disabled[col][row];
			table.getFlexCellFormatter().removeStyleName(row+1, col+1, oldstyle);
			table.getFlexCellFormatter().addStyleName(row+1, col+1, newstyle);
		}
		
	}
	
	public HashMap<Integer, ArrayList<Time>> getBlockedTimes() {
		HashMap<Integer, ArrayList<Time>> result = new HashMap<Integer, ArrayList<Time>>();
		for(int i=0;i<7;i++) {
			Integer day = new Integer(i);
			ArrayList<Time> times = new ArrayList<Time>();
			for(int j=start;j<end;j++) {
				if(disabled[i][j]) {
					times.add(new Time(j/2,j%2*30));
				}
			}
			result.put(day, times);
		}
		return result;
	}

	public void setTimeStatus(Integer iDay, Time time, boolean status) {
		// TODO Auto-generated method stub
		disabled[iDay.intValue()][time.getHour()] = status;
	}
}
