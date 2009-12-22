package edu.rpi.rocs.client.ui;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.ui.HTMLPanel;

import edu.rpi.rocs.client.ImageManager;
import edu.rpi.rocs.client.objectmodel.Semester;
import edu.rpi.rocs.client.objectmodel.SemesterManager;
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
	
	private static String createHeaderText(String text, boolean visible) {
		return "<img src=\"" +
				ImageManager.getPathForImage((visible? 
			    ImageManager.Image.ExpandedArrow : 
			    ImageManager.Image.CollapsedArrow)) + 
				"\" width=\"12\" height=\"12\" />"+text;
	}
	
	private class UpdateImageHelper implements MultiStackPanel.MultiStackPanelVisibilityChangeHandler {
		private String m_text="";
		private int m_i;
		
		public UpdateImageHelper(int i, String text) {
			m_text = text;
			m_i = i;
		}

		public void onChange(int index, boolean visible) {
			if(index==m_i) thePanel.setStackText(index, createHeaderText(m_text, visible), true);
		}
	}
	
	private class SemesterImageHelper extends UpdateImageHelper {

		public SemesterImageHelper() {
			super(0, "");
		}
		
		public void onChange(int index, boolean visible) {
			if(index==0 && SemesterManager.getInstance().getCurrentSemester()!=null)
				thePanel.setStackText(index, createHeaderText("<span>Semester - "+
					SemesterManager.getInstance().getCurrentSemester().getSemesterDesc()+"</span><span style=\"float:right;font-weight:normal;\"><i>Last updated:</i> "+SemesterManager.getInstance().getCurrentSemester().getLastChangeTime()+"</span>", visible), true);
		}
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
				thePanel.setStackText(0, createHeaderText("<span>Semester - "+semester.getSemesterDesc()+"</span><span style=\"font-weight: normal; float:right;\"><i>Last updated:</i> "+SemesterManager.getInstance().getCurrentSemester().getLastChangeTime()+"&nbsp;&nbsp;</span>",thePanel.isVisible(0)),true);
				Log.debug("Semester - "+semester.getSemesterDesc());
			}
		});
		
		HTMLPanel temp = new HTMLPanel("<div id=\"rocs_PORTLET_rocs_semester_pane\"></div>");
		semesterPanel = SemesterSelectionPanel.getInstance();
		temp.add(semesterPanel, "rocs_PORTLET_rocs_semester_pane");
		thePanel.add(temp,createHeaderText("<span>Semester - Loading...</span>",false),true);
		
		searchPanel = CourseSearchPanel.getInstance();
		temp = new HTMLPanel("<div id=\"rocs_PORTLET_rocs_search_pane\"></div>");
		temp.add(searchPanel, "rocs_PORTLET_rocs_search_pane");
		thePanel.add(temp,createHeaderText("Course Search",true),true);
		viewPanel = ClassViewPanel.getInstance();
		temp = new HTMLPanel("<div id=\"rocs_PORTLET_rocs_courses_pane\"></div>");
		temp.add(viewPanel, "rocs_PORTLET_rocs_courses_pane");
		thePanel.add(temp,createHeaderText("Selected Courses",true),true);
		Log.debug("Adding filter panel");
		filterPanel = SchedulerFilterDisplayPanel.get();
		temp = new HTMLPanel("<div id=\"rocs_PORTLET_rocs_filters_pane\"></div>");
		temp.add(filterPanel, "rocs_PORTLET_rocs_filters_pane");
		thePanel.add(temp,createHeaderText("Schedule Filters",false),true);
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
		thePanel.add(temp,createHeaderText("Schedules",false),true);
		thePanel.showStack(0);   // Shown by default, this hides it
		thePanel.showStack(1);   // Shows the search pane
		thePanel.showStack(2);   // Shows the selected course pane
		thePanel.addChangeHandler(new SemesterImageHelper());
		thePanel.addChangeHandler(new UpdateImageHelper(1, "Course Search"));
		thePanel.addChangeHandler(new UpdateImageHelper(2, "Selected Courses"));
		thePanel.addChangeHandler(new UpdateImageHelper(3, "Schedule Filters"));
		thePanel.addChangeHandler(new UpdateImageHelper(4, "Schedules"));
		this.add(thePanel, "rocs_PORTLET_rocs_stackbody");
	}
}
