package edu.rpi.rocs.client.ui.filters;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gwt.user.client.ui.SimplePanel;

import edu.rpi.rocs.client.objectmodel.Time;
import edu.rpi.rocs.client.ui.filters.helpers.SelectableTableWidget;

public class ScheduleTimeBlockFilterWidget extends SimplePanel {
	private SelectableTableWidget theTable = new SelectableTableWidget(2*(22-8), 7);
	private final int start=8*2; // 8 am with 30 min blocks
	private final int end=22*2; // 10 pm with 30 min blocks
	
	public static String dayOfWeek(int i) {
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
	
	public HashMap<Integer, ArrayList<Time>> getBlockedTimes() {
		HashMap<Integer, ArrayList<Time>> result = new HashMap<Integer, ArrayList<Time>>();
		for(int i=0;i<7;i++) {
			Integer day = new Integer(i);
			ArrayList<Time> times = new ArrayList<Time>();
			for(int j=start;j<end;j++) {
				if(theTable.isSelected(j-start, i)) {
					times.add(new Time(j/2,j%2*30));
				}
			}
			result.put(day, times);
		}
		return result;
	}

	public void setTimeStatus(Integer iDay, Time time, boolean status) {
		theTable.setSelected((time.getHour()-8)*2+time.getMinute()/30, iDay.intValue(), status);
	}
}
