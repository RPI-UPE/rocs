package edu.rpi.rocs.client.ui.scheduler;

import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;

import edu.rpi.rocs.client.objectmodel.ScheduleFilterManager;

public class SchedulerPanel extends SimplePanel {
	private static SchedulerPanel theInstance=null;
	private Panel currentChild=null;
	
	public enum SchedulerPage {
		FilterPage,
		SchedulePage
	}
	
	private SchedulerPanel() {
		this.setHeight("100%");
		currentChild = new SchedulerIntroPanel();
		this.add(currentChild);
	}
	
	public static SchedulerPanel get() {
		if(theInstance==null) theInstance = new SchedulerPanel();
		return theInstance;
	}
	
	public void switchTo(SchedulerPage page) {
		switch(page) {
		case FilterPage:
			this.remove(currentChild);
			currentChild = SchedulerFilterDisplayPanel.get();
			this.add(currentChild);
			break;
		case SchedulePage:
			if(ScheduleFilterManager.get().filtersChanged()) {
				
			}
		}
	}
}
