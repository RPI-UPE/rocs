package edu.rpi.rocs.client.ui;

import com.google.gwt.user.client.ui.HTMLPanel;

import edu.rpi.rocs.client.ui.coursesearch.CourseSearchPanel;
import edu.rpi.rocs.client.ui.semesterselect.SemesterSelectionPanel;

public class ROCSInterface extends HTMLPanel {
	SemesterSelectionPanel semesterPanel;
	CourseSearchPanel searchPanel;
	static ROCSInterface theInstance;
	
	public static ROCSInterface getInstance() {
		if(theInstance==null) theInstance = new ROCSInterface();
		return theInstance;
	}
	
	private ROCSInterface() {
		super("<div id=\"rocs_PORTLET_rocs_semester_pane\"></div>"+
				"<div id=\"rocs_PORTLET_rocs_courses_pane\"></div>" +
				"<div id=\"rocs_PORTLET_rocs_search_pane\"></div>" +
				"<div id=\"rocs_PORTLET_rocs_scheduler_pane\"></div>");
		
		this.setHeight("600px");
		
		semesterPanel = SemesterSelectionPanel.getInstance();
		searchPanel = CourseSearchPanel.getInstance();
		
		this.add(semesterPanel, "rocs_PORTLET_rocs_semester_pane");
		this.add(searchPanel, "rocs_PORTLET_rocs_search_pane");
	}
}
