package edu.rpi.rocs.client.objectmodel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeMap;

import com.google.gwt.event.shared.EventHandler;

import edu.rpi.rocs.client.filters.schedule.MaxCreditFilter;
import edu.rpi.rocs.client.filters.schedule.MinCreditFilter;
import edu.rpi.rocs.client.filters.schedule.NoMoreThanNumAtLevelFilter;
import edu.rpi.rocs.client.filters.schedule.ScheduleFilter;
import edu.rpi.rocs.client.filters.schedule.TimeSchedulerFilter;

public class ScheduleFilterManager {
	private static ScheduleFilterManager theInstance=null;
	private boolean changed=false;
	private HashSet<ScheduleFilter> filters = new HashSet<ScheduleFilter>();
	private HashSet<FilterAddedHandler> addedHandlers = new HashSet<FilterAddedHandler>();
	private HashSet<FilterRemovedHandler> removedHandlers = new HashSet<FilterRemovedHandler>();
	private TreeMap<String, String> registeredFilters = new TreeMap<String, String>();
	
	public ArrayList<String> getRegisteredFilterNames() {
		return new ArrayList<String>(registeredFilters.keySet());
	}
	
	public String getFilterByName(String filterName) {
		return registeredFilters.get(filterName);
	}
	
	public interface FilterAddedHandler extends EventHandler {
		public void filterAdded(ScheduleFilter filter);
	}
	
	public interface FilterRemovedHandler extends EventHandler {
		public void filterRemoved(ScheduleFilter filter);
	}
	
	public void addFilterAddedHandler(FilterAddedHandler handler) {
		addedHandlers.add(handler);
	}
	
	public void addFilterRemovedHandler(FilterRemovedHandler handler) {
		removedHandlers.add(handler);
	}
	
	public void removeFilterAddedHandler(FilterAddedHandler handler) {
		addedHandlers.remove(handler);
	}
	
	public void removeFilterRemovedHandler(FilterRemovedHandler handler) {
		removedHandlers.remove(handler);
	}
	
	private ScheduleFilterManager() {
		/*
		ScheduleFilter temp;
		temp = new MaxCreditFilter();
		registerFilter(temp.getDisplayTitle(), temp.getClass());
		temp = new MinCreditFilter();
		registerFilter(temp.getDisplayTitle(), temp.getClass());
		temp = new NoMoreThanNumAtLevelFilter();
		registerFilter(temp.getDisplayTitle(), temp.getClass());
		temp = new TimeSchedulerFilter();
		registerFilter(temp.getDisplayTitle(), temp.getClass());
		*/
	}
	
	public void registerFilter(String displayTitle, String qName) {
		// TODO Auto-generated method stub
		registeredFilters.put(displayTitle, qName);
	}

	public static ScheduleFilterManager get() {
		if(theInstance==null) {
			theInstance = new ScheduleFilterManager();
			TimeSchedulerFilter.register();
			MaxCreditFilter.register();
			MinCreditFilter.register();
			NoMoreThanNumAtLevelFilter.register();
		}
		return theInstance;
	}
	
	public void addFilter(ScheduleFilter filter) {
		filters.add(filter);
		
		changed=true;
	}
	
	public void clearChangedFlag() {
		changed=false;
	}
	
	public HashSet<ScheduleFilter> getFilters() {
		return new HashSet<ScheduleFilter>(filters);
	}
	
	public void removeFilter(ScheduleFilter filter) {
		filters.remove(filter);
		changed=true;
	}

	public boolean filtersChanged() {
		// TODO Auto-generated method stub
		return changed;
	}
}
