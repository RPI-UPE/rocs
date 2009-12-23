package edu.rpi.rocs.client.ui.scheduler;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;

import edu.rpi.rocs.client.objectmodel.Schedule;
import edu.rpi.rocs.client.objectmodel.ScheduleFilterManager;
import edu.rpi.rocs.client.objectmodel.SchedulerManager;

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
	
	public static SchedulerPanel getInstance() {
		if(theInstance==null) theInstance = new SchedulerPanel();
		return theInstance;
	}
	
	public void switchTo(SchedulerPage page) {
		switch(page) {
		case FilterPage:
			this.remove(currentChild);
			currentChild = SchedulerFilterDisplayPanel.getInstance();
			this.add(currentChild);
			break;
		case SchedulePage:
			if(ScheduleFilterManager.getInstance().filtersChanged()) {
				SchedulerManager.getInstance().generateSchedules();
				ArrayList<Schedule> schedules = SchedulerManager.getInstance().getAllSchedules();
				if(schedules==null || schedules.size()==0) {
					return;
				}
				this.remove(currentChild);
				currentChild = SchedulerDisplayPanel.getInstance();
				SchedulerDisplayPanel.getInstance().setSchedules(schedules);
				this.add(currentChild);
				break;
			}
			else {
				this.remove(currentChild);
				currentChild = SchedulerDisplayPanel.getInstance();
				this.add(currentChild);
			}
		}
	}
}
