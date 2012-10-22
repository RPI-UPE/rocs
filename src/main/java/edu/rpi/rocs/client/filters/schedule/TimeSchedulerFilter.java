package edu.rpi.rocs.client.filters.schedule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.Widget;

import edu.rpi.rocs.client.objectmodel.Schedule;
import edu.rpi.rocs.client.objectmodel.ScheduleFilterManager;
import edu.rpi.rocs.client.objectmodel.SchedulerManager;
import edu.rpi.rocs.client.objectmodel.Time;
import edu.rpi.rocs.client.ui.filters.ScheduleTimeBlockFilterWidget;

public class TimeSchedulerFilter implements ScheduleFilter {

	public static interface TimeSchedulerFilterChangeHandler {
		public void onChange(TimeSchedulerFilter filter);
	}
	
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
	private transient ScheduleTimeBlockFilterWidget widget=null;
	
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
		return false;
	}

	public boolean shouldPruneTreeOnFailure() {
		return true;
	}

	public Widget getWidget() {
		if(widget==null) {
			widget = new ScheduleTimeBlockFilterWidget();
			widget.setOwner(this);
		}
		return widget;
	}

	public String getDisplayTitle() {
		return DISPLAY_NAME;
	}
	
	private HashSet<ChangeHandler> changeHandlers = new HashSet<ChangeHandler>();

	public void addChangeHandler(ChangeHandler e) {
		changeHandlers.add(e);
	}

	public void removeChangeHandler(ChangeHandler e) {
		changeHandlers.remove(e);
	}

	public void onChange() {
		SchedulerManager.getInstance().fireTimeFilterChangeHandlers(this);
	}

}
