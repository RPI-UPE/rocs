package edu.rpi.rocs.client.filters.schedule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.Widget;

import edu.rpi.rocs.client.objectmodel.Schedule;
import edu.rpi.rocs.client.objectmodel.ScheduleFilterManager;
import edu.rpi.rocs.client.objectmodel.Time;
import edu.rpi.rocs.client.ui.filters.ScheduleTimeBlockFilterWidget;

public class TimeSchedulerFilter implements ScheduleFilter {

	private static String DISPLAY_NAME="Disallowed Times Filter";
	private static String QUALIFIED_NAME="edu.rpi.rocs.client.filters.schedule.TimeSchedulerFilter";
	
	public static boolean register() {
		ScheduleFilterManager.getInstance().registerFilter(DISPLAY_NAME, QUALIFIED_NAME);
		return true;
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7735119145993404979L;
	private ScheduleTimeBlockFilterWidget widget=null;
	
	public HashMap<Integer, ArrayList<Time>> getTimes() {
		return widget.getBlockedTimes();
	}
	
	public void setTimeStatus(int day, int hour, int minute, boolean status) {
		Integer iDay = new Integer(day);
		widget.setTimeStatus(iDay, new Time(hour, minute), status);
		for(ChangeHandler handler : changeHandlers) {
			handler.onChange(null);
		}
	}
	
	public boolean getTimeStatus(int day, int hour, int minute) {
		HashMap<Integer, ArrayList<Time>> vals = widget.getBlockedTimes();
		ArrayList<Time> times = vals.get(new Integer(day));
		return times.contains(new Time(hour, minute));
	}
	
	public boolean doesScheduleSatisfyFilter(Schedule schedule) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean shouldPruneTreeOnFailure() {
		// TODO Auto-generated method stub
		return true;
	}

	public Widget getWidget() {
		// TODO Auto-generated method stub
		if(widget==null) widget = new ScheduleTimeBlockFilterWidget();
		return widget;
	}

	public String getDisplayTitle() {
		// TODO Auto-generated method stub
		return DISPLAY_NAME;
	}
	
	private HashSet<ChangeHandler> changeHandlers = new HashSet<ChangeHandler>();

	public void addChangeHandler(ChangeHandler e) {
		// TODO Auto-generated method stub
		changeHandlers.add(e);
	}

	public void removeChangeHandler(ChangeHandler e) {
		// TODO Auto-generated method stub
		changeHandlers.remove(e);
	}

}
