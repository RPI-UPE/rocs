package edu.rpi.rocs.client.filters.schedule;

import java.util.HashSet;
import java.util.Iterator;

import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.Widget;

import edu.rpi.rocs.client.objectmodel.Schedule;
import edu.rpi.rocs.client.objectmodel.ScheduleFilterManager;
import edu.rpi.rocs.client.objectmodel.Section;
import edu.rpi.rocs.client.ui.filters.NoMoreThanNumAtLevelFilterWidget;
import edu.rpi.rocs.client.ui.filters.NoMoreThanNumAtLevelFilterWidget.NoMoreThanNumAtLevelChangeHandler;

public class NoMoreThanNumAtLevelFilter implements ScheduleFilter, NoMoreThanNumAtLevelChangeHandler {

	private static String DISPLAY_NAME="Maximum Course Count at Level Filter";
	private static String QUALIFIED_NAME="edu.rpi.rocs.client.filters.schedule.NoMoreThanNumAtLevelFilter";
	
	public static boolean register() {
		ScheduleFilterManager.get().registerFilter(DISPLAY_NAME, QUALIFIED_NAME);
		return true;
	}

	/**
	 * The unique identifier for serialization
	 */
	private static final long serialVersionUID = -8071519178249185461L;
	private static NoMoreThanNumAtLevelFilterWidget widget = null;
	
	/**
	 * The level to check against
	 */
	protected int level=1000;
	/**
	 * The maximum number of courses allowed at this level
	 */
	protected int max=3;

	/**
	 * Creates a new filter to restrict the number of courses at a particular level
	 * @param level The level to filter
	 * @param max The maximum number of courses at level
	 */
	public NoMoreThanNumAtLevelFilter(int level, int max) {
		this.level = level;
		this.max = max;
	}
	
	public NoMoreThanNumAtLevelFilter() {
	
	}
	
	/**
	 * Checks a schedule against this filter.
	 * @return true if the schedule has max or less courses at level
	 */
	public boolean doesScheduleSatisfyFilter(Schedule schedule) {
		// TODO Auto-generated method stub
		Iterator<Section> i = schedule.getSections().iterator();
		int counter=0;
		while(i.hasNext()) {
			Section s = i.next();
			if(s.getParent().getLevel() == level) {
				counter++;
				if(counter==max) return false;
			}
		}
		return true;
	}

	/**
	 * Any schedule generated with this schedule as a base will also fail this filter, so prune the tree.
	 */
	public boolean shouldPruneTreeOnFailure() {
		// TODO Auto-generated method stub
		return true;
	}

	public Widget getWidget() {
		// TODO Auto-generated method stub
		if(widget == null) {
			widget = new NoMoreThanNumAtLevelFilterWidget();
			widget.addChangeHandler(this);
		}
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

	public void setLevel(int level) {
		// TODO Auto-generated method stub
		this.level = level;
		for(ChangeHandler handler : changeHandlers) {
			handler.onChange(null);
		}
	}

	public void setNumber(int num) {
		// TODO Auto-generated method stub
		this.max = num;
		for(ChangeHandler handler : changeHandlers) {
			handler.onChange(null);
		}
	}
	
	public int getLevel() {
		return level;
	}
	
	public int getNumber() {
		return max;
	}

}
