package edu.rpi.rocs.client.ui;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.ui.HTMLPanel;

import edu.rpi.rocs.client.SemesterManager;
import edu.rpi.rocs.client.objectmodel.Semester;
import edu.rpi.rocs.client.ui.coursesearch.CourseSearchPanel;
import edu.rpi.rocs.client.ui.scheduler.SchedulerDisplayPanel;
import edu.rpi.rocs.client.ui.scheduler.SchedulerFilterDisplayPanel;
import edu.rpi.rocs.client.ui.scheduler.SchedulerPanel;
import edu.rpi.rocs.client.ui.semesterselect.SemesterSelectionPanel;
import edu.rpi.rocs.client.ui.classview.ClassViewPanel;

public class ROCSInterface extends HTMLPanel {
	SemesterSelectionPanel semesterPanel;
	CourseSearchPanel searchPanel;
	SchedulerPanel schedulerPanel;
	ClassViewPanel viewPanel;
	SchedulerFilterDisplayPanel filterPanel;
	SchedulerDisplayPanel schedulerDisplayPanel;
	MultiStackPanel thePanel;

	static ROCSInterface theInstance;
	
	public static ROCSInterface getInstance() {
		if(theInstance==null) theInstance = new ROCSInterface();
		return theInstance;
	}
	
	private ROCSInterface() {
		/*
		super("<div id=\"rocs_PORTLET_rocs_semester_pane\"></div>"+
				"<div id=\"rocs_PORTLET_rocs_courses_pane\"></div>" +
				"<div id=\"rocs_PORTLET_rocs_search_pane\"></div>" +
				"<div id=\"rocs_PORTLET_rocs_scheduler_pane\"></div>");
		
		this.setHeight("600px");
		
		semesterPanel = SemesterSelectionPanel.getInstance();
		searchPanel = CourseSearchPanel.getInstance();
		schedulerPanel = SchedulerPanel.get();
		viewPanel = ClassViewPanel.getInstance();
		
		this.add(semesterPanel, "rocs_PORTLET_rocs_semester_pane");
		this.add(searchPanel, "rocs_PORTLET_rocs_search_pane");
		this.add(schedulerPanel, "rocs_PORTLET_rocs_scheduler_pane");
		this.add(viewPanel, "rocs_PORTLET_rocs_courses_pane");
		*/
		super("<div id=\"rocs_PORTLET_rocs_stackbody\"></div>");
		thePanel = new MultiStackPanel();
		
		SemesterManager.getInstance().addSemesterChangeListener(new SemesterManager.SemesterManagerCallback() {
			
			public void semesterLoaded(Semester semester) {
				// TODO Auto-generated method stub
				thePanel.setStackText(0, "Semester - "+semester.getSemesterDesc());
				Log.debug("Semester - "+semester.getSemesterDesc());
			}
		});
		
		HTMLPanel temp = new HTMLPanel("<div id=\"rocs_PORTLET_rocs_semester_pane\"></div>");
		semesterPanel = SemesterSelectionPanel.getInstance();
		temp.add(semesterPanel, "rocs_PORTLET_rocs_semester_pane");
		thePanel.add(temp,"Semester - Loading...",false);
		
		searchPanel = CourseSearchPanel.getInstance();
		temp = new HTMLPanel("<div id=\"rocs_PORTLET_rocs_search_pane\"></div>");
		temp.add(searchPanel, "rocs_PORTLET_rocs_search_pane");
		thePanel.add(temp,"Course Search",false);
		viewPanel = ClassViewPanel.getInstance();
		temp = new HTMLPanel("<div id=\"rocs_PORTLET_rocs_courses_pane\"></div>");
		temp.add(viewPanel, "rocs_PORTLET_rocs_courses_pane");
		thePanel.add(temp,"Selected Courses",false);
		Log.debug("Adding filter panel");
		filterPanel = SchedulerFilterDisplayPanel.get();
		temp = new HTMLPanel("<div id=\"rocs_PORTLET_rocs_filters_pane\"></div>");
		temp.add(filterPanel, "rocs_PORTLET_rocs_filters_pane");
		thePanel.add(temp,"Schedule Filters",false);
		/*
		schedulerPanel = SchedulerPanel.get();
		temp = new HTMLPanel("<div id=\"rocs_PORTLET_rocs_scheduler_pane\"></div>");
		temp.add(schedulerPanel, "rocs_PORTLET_rocs_scheduler_pane");
		thePanel.add(temp,"Scheduler",false);
		*/
		Log.debug("Adding scheduler panel");
		schedulerDisplayPanel = SchedulerDisplayPanel.get();
		temp = new HTMLPanel("<div id=\"rocs_PORTLET_rocs_scheduler_pane\"></div>");
		temp.add(schedulerDisplayPanel, "rocs_PORTLET_rocs_scheduler_pane");
		thePanel.add(temp,"Schedules",false);
		thePanel.showStack(0);   // Shown by default, this hides it
		thePanel.showStack(1);   // Shows the search pane
		thePanel.showStack(2);   // Shows the selected course pane
		this.add(thePanel, "rocs_PORTLET_rocs_stackbody");
	}
}
