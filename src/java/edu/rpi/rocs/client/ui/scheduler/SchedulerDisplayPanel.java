package edu.rpi.rocs.client.ui.scheduler;

import java.util.ArrayList;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.rpi.rocs.client.objectmodel.Schedule;

public class SchedulerDisplayPanel extends HorizontalPanel {
	ArrayList<Schedule> m_schedules=null;
	static SchedulerDisplayPanel theInstance=null;
	Schedule m_current;
	ScheduleViewWidget m_view;
	VerticalPanel m_summary;
	
	private SchedulerDisplayPanel() {
		m_current = null;
		m_view = null;
		m_summary = new VerticalPanel();
		m_summary.addStyleName("rocs-style");
		m_summary.addStyleName("schedule-list");
	}
	
	public static SchedulerDisplayPanel get() {
		if(theInstance==null) theInstance = new SchedulerDisplayPanel();
		return theInstance;
	}
	
	public void setSchedules(ArrayList<Schedule> schedules) {
		m_schedules = schedules;
		if(m_schedules.size()>0) {
			m_current = m_schedules.get(0);
		}
		else {
			m_current = null;
		}
		updateSchedulePanel();
	}
	
	private void updateSchedulePanel() {
		if(m_view!=null) {
			remove(m_view);
			remove(m_summary);
		}
		if(m_current==null) {
			Window.alert("Unable to compute at least one valid schedule.");
			return;
		}
		m_view = new ScheduleViewWidget(m_current);
		add(m_view);
		add(m_summary);
	}
}
