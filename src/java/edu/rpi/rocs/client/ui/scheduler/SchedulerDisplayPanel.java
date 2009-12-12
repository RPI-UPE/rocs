package edu.rpi.rocs.client.ui.scheduler;

import java.util.ArrayList;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;

import edu.rpi.rocs.client.objectmodel.Schedule;
import edu.rpi.rocs.client.objectmodel.Section;

public class SchedulerDisplayPanel extends HorizontalPanel {
	ArrayList<Schedule> m_schedules=null;
	static SchedulerDisplayPanel theInstance=null;
	Schedule m_current;
	ScheduleViewWidget m_view;
	FlowPanel m_summary;
	RandomColorGenerator m_genMap = new RandomColorGenerator();

	private void setActiveSchedule(Schedule s) {
		m_current = s;
		updateSchedulePanel();
	}

	class ScheduleClickHandler implements ClickHandler {
		Schedule m_schedule;

		ScheduleClickHandler(Schedule s) {
			m_schedule = s;
		}

		public void onClick(ClickEvent arg0) {
			for(Section s : m_schedule.getSections()) {
				Log.debug("Section " + s.getNumber() + " of course " + s.getParent().getDept() + "-" + s.getParent().getNum());
			}
			setActiveSchedule(m_schedule);
		}
	}

	private SchedulerDisplayPanel() {
		m_current = null;
		m_view = null;
		m_summary = new FlowPanel();
		m_summary.addStyleName("rocs-style");
		m_summary.addStyleName("schedule-list");
		add(m_summary);
	}

	public static SchedulerDisplayPanel get() {
		if(theInstance==null) theInstance = new SchedulerDisplayPanel();
		return theInstance;
	}

	public void setSchedules(ArrayList<Schedule> schedules) {
		m_schedules = schedules;
		if(m_schedules.size()>0) {
			m_current = m_schedules.get(0);
			for(Schedule s : schedules) {
				ScheduleMiniViewWidget w = new ScheduleMiniViewWidget(s, m_genMap);
				m_summary.add(w);
				w.addClickHandler(new ScheduleClickHandler(s));
			}
		}
		else {
			m_current = null;
		}
		updateSchedulePanel();
	}

	private void updateSchedulePanel() {
		if(m_view!=null) {
			remove(m_view);
		}
		if(m_current==null) {
			Window.alert("Unable to compute at least one valid schedule.");
			return;
		}
		m_view = new ScheduleViewWidget(m_current, m_genMap);
		this.insert(m_view, 0);
	}
}
