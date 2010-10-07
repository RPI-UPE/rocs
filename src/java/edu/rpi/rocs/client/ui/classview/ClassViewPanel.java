package edu.rpi.rocs.client.ui.classview;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.SimpleCheckBox;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.rpi.rocs.client.ui.HTMLTableList.HTMLTableListCell;
import edu.rpi.rocs.client.ui.HTMLTableList.HTMLTableListRow;
import edu.rpi.rocs.client.ui.scheduler.SchedulerFilterDisplayPanel;
import edu.rpi.rocs.client.ui.HTMLTableList;
import edu.rpi.rocs.client.ui.ListBoxHTML;
import edu.rpi.rocs.client.ui.ROCSInterface;
import edu.rpi.rocs.client.objectmodel.SchedulerManager;
import edu.rpi.rocs.client.objectmodel.CourseStatusObject;
import edu.rpi.rocs.client.objectmodel.Course;
import edu.rpi.rocs.client.objectmodel.Section;
import edu.rpi.rocs.client.objectmodel.SectionStatusObject;
import edu.rpi.rocs.client.objectmodel.SchedulerManager.*;

public class ClassViewPanel extends VerticalPanel implements CourseAddedHandler, CourseRemovedHandler, CourseRequiredHandler, CourseOptionalHandler
{
	private CourseAddedHandler addHandler = new CourseAddedHandler() {

		public void handleEvent(CourseStatusObject status) {
			instance.addedCourse(status);
		}
		
	};
	
	private CourseRemovedHandler removeHandler = new CourseRemovedHandler() {
		
		public void handleEvent(CourseStatusObject status) {
			instance.removedCourse(status);
		}
		
	};
	
	@SuppressWarnings("unused")
	private CourseRequiredHandler requiredHandler = new CourseRequiredHandler() {
		
		public void handleEvent(CourseStatusObject status) {
			instance.requireCourse(status);
		}
		
	};
	
	@SuppressWarnings("unused")
	private CourseOptionalHandler optionalHandler = new CourseOptionalHandler() {
		
		public void handleEvent(CourseStatusObject status) {
			instance.optionalCourse(status);
		}
		
	};
	
	//UI Elements:
	private FlexTable layout=null; //Table: One row, Two columns
	private VerticalPanel listHolder=null; //Holds the classList
	private VerticalPanel buttonHolder=null; //Holds the three buttons
	private ListBoxHTML classList=null; //List of selected classes
	private HTMLTableList sectionList=null;
	private Label selectedTitle=null; //Label for the list of classes
	private Anchor requiredButton=null; //Required Button
	private Anchor optionalButton=null; //Optional Button
	private Anchor removeButton=null; //Remove Button
	private Anchor continueButton=null;
	private class Pair {
		public Course course;
		public ArrayList<HTMLTableList.HTMLTableListRow> rows = new ArrayList<HTMLTableList.HTMLTableListRow>();
		public SimpleCheckBox check;
	}
	private ArrayList<Pair> rows = new ArrayList<Pair>();

	//Data Members:
	private List<CourseStatusObject> curCourses=null;

	private static ClassViewPanel instance = null;
	public static ClassViewPanel getInstance() {
		if (instance == null) {
			instance = new ClassViewPanel();
		}
		return instance;
	}

	protected void addedCourse(CourseStatusObject status) {
		Map<Section,SectionStatusObject> stats = SchedulerManager.getInstance().getSectionsForCourse(status.getCourse());
		HTMLTableListRow r = createRowForCourse(status);
		Pair p = new Pair();
		p.course = status.getCourse();
		p.check = (SimpleCheckBox) r.get(0).getWidget();
		sectionList.add(r);
		p.rows.add(r);
		for(SectionStatusObject sso : stats.values()) {
			r = createRowForSectionStatusObject(sso);
			sectionList.add(r);
			p.rows.add(r);
		}
		rows.add(p);
	}
	
	protected void requireCourse(CourseStatusObject status) {
		for(Pair p : rows) {
			if(p.course.equals(status.getCourse())) {
				p.rows.get(0).get(4).setText("Required");
				return;
			}
		}
	}
	
	protected void optionalCourse(CourseStatusObject status) {
		for(Pair p : rows) {
			if(p.course.equals(status.getCourse())) {
				p.rows.get(0).get(4).setText("Optional");
				return;
			}
		}
	}
	
	protected void removedCourse(CourseStatusObject status) {
		Log.debug("Removing course "+status.getCourse().getName());
		Iterator<Pair> i = rows.iterator();
		while(i.hasNext()) {
			Pair p = i.next();
			if(p.course.equals(status.getCourse())) {
				Log.debug("Found course. Removing rows...");
				for(HTMLTableListRow r : p.rows) {
					sectionList.remove(r);
				}
				i.remove();
				return;
			}
		}
	}
	
	private abstract class ModeChangeHandler implements ChangeHandler {
		ListBox mode;
		Course course;
		
		public ModeChangeHandler(ListBox m, Course c) {
			mode = m;
			course = c;
		}
	}
	
	private abstract class DeleteClickHandler implements ClickHandler {
		Course course;
		
		public DeleteClickHandler(Course c) {
			course = c;
		}
	}
	
	protected HTMLTableListRow createRowForCourse(CourseStatusObject c) {
		Course course= c.getCourse();
		HTMLTableListRow row = sectionList.new HTMLTableListRow();
		row.setStyleName("course");
		HTMLTableListCell cell = sectionList.new HTMLTableListCell();
		//SimpleCheckBox check = new SimpleCheckBox();
		//check.setTitle("Select");
		//cell.setWidget(check);
		row.add(cell);
		cell = sectionList.new HTMLTableListCell();
		row.add(cell);
		cell = sectionList.new HTMLTableListCell();
		cell.setText(course.getDept()+"-"+course.getNum());
		row.add(cell);
		cell = sectionList.new HTMLTableListCell();
		cell.setText(course.getName());
		row.add(cell);
		cell = sectionList.new HTMLTableListCell();
		cell.setText("State: ");
		row.add(cell);
		cell = sectionList.new HTMLTableListCell();
		ListBox mode = new ListBox();
		mode.addItem("Required");
		mode.addItem("Optional");
		mode.setVisibleItemCount(1);
		if(c.getRequired()) {
			mode.setItemSelected(0, true);
		}
		else {
			mode.setItemSelected(1, true);
		}
		mode.addChangeHandler(new ModeChangeHandler(mode, c.getCourse()) {

			public void onChange(ChangeEvent arg0) {
				int i = mode.getSelectedIndex();
				if(i==0)
					SchedulerManager.getInstance().setCourseRequired(course);
				else
					SchedulerManager.getInstance().setCourseOptional(course);
			}
			
		});
		cell.setWidget(mode);
		row.add(cell);
		cell = sectionList.new HTMLTableListCell();
		Button delete = new Button("Remove");
		delete.addClickHandler(new DeleteClickHandler(c.getCourse()) {

			public void onClick(ClickEvent arg0) {
				SchedulerManager.getInstance().removeCourse(course);
			}
			
		});
		cell.setWidget(delete);
		row.add(cell);
		return row;
	}
	
	protected abstract class CheckBoxCallback implements ClickHandler {
		SimpleCheckBox cb = null;
		
		public CheckBoxCallback(SimpleCheckBox check) {
			cb = check;
		}
	}
	
	protected void checkClicked(boolean val, SectionStatusObject sso) {
		Log.debug("Section included: "+!sso.getIncluded());
		if(!sso.getIncluded())
			SchedulerManager.getInstance().setSectionIncluded(sso.getSection());
		else
			SchedulerManager.getInstance().setSectionExcluded(sso.getSection());
	}
	
	protected native void addEventHandler(Element e, SimpleCheckBox cb, SectionStatusObject sso)/*-{
		var self = this;
		var callback = function() {
			self.@edu.rpi.rocs.client.ui.classview.ClassViewPanel::checkClicked(ZLedu/rpi/rocs/client/objectmodel/SectionStatusObject;)(e.checked, sso);
		};
		if(e.addEventListener) {
			e.addEventListener("change",callback,false);
		}
		else {
			e.attachEvent("onclick",callback);
		}
	}-*/;
	
	protected HTMLTableListRow createRowForSectionStatusObject(final SectionStatusObject sso) {
		Section s = sso.getSection();
		HTMLTableListRow row = sectionList.new HTMLTableListRow();
		HTMLTableListCell cell = sectionList.new HTMLTableListCell();
		row.add(cell);
		cell = sectionList.new HTMLTableListCell();
		SimpleCheckBox check = new SimpleCheckBox();
		check.setTitle("Include?");
		check.setChecked(sso.getIncluded());
		addEventHandler(check.getElement(), check, sso);
		/*
		check.addClickHandler(new CheckBoxCallback(check) {

			public void onClick(ClickEvent arg0) {
				Log.debug("Section included: "+cb.isChecked());
				if(cb.isChecked())
					SchedulerManager.getInstance().setSectionIncluded(sso.getSection());
				else
					SchedulerManager.getInstance().setSectionExcluded(sso.getSection());
			}

		});
		*/
		Log.debug("Added click handler");
		cell.setWidget(check);
		row.add(cell);
		String profs = "";
		int count = 0;
		HashSet<String> professors = s.getProfessors(); 
		for(String str : professors) {
			profs = profs + str;
			if(count+1<professors.size()) profs = profs + "/";
		}
		cell = sectionList.new HTMLTableListCell();
		if(s.getNotes().size()>0) {
			String notes = "";
			for(int i=0;i<s.getNotes().size();i++) {
				notes += s.getNotes().get(i);
				if(i+1<s.getNotes().size()) notes += "<br/>";
			}
			cell.setHTML("Section "+s.getNumber()+" - "+profs+"<br/>"+notes);
		}
		else 
			cell.setHTML("Section "+s.getNumber()+" - "+profs);
		cell.setColSpan(5);
		row.add(cell);
		return row;
	}

	private ClassViewPanel() {
		SchedulerManager.getInstance().addCourseAddedEventHandler( addHandler );
		// No longer required
		/*
		SchedulerManager.getInstance().addCourseOptionalEventHandler( optionalHandler );
		SchedulerManager.getInstance().addCourseRequiredEventHandler( requiredHandler );
		*/
		SchedulerManager.getInstance().addCourseRemovedEventHandler( removeHandler );
		
		layout = new FlexTable();

		listHolder = new VerticalPanel();
		buttonHolder = new VerticalPanel();

		layout.setWidget(0, 0, listHolder);
		//layout.setWidget(0, 1, buttonHolder);

		selectedTitle = new Label("Selected Courses:");

		classList = new ListBoxHTML(true);
		classList.addStyleName("class_view");
		classList.addStyleName("search_results");

		listHolder.add( selectedTitle );
		
		sectionList = new HTMLTableList();
		sectionList.addStyleName("class_view");
		sectionList.addStyleName("search_results");
		
		listHolder.add(sectionList);
		
		//listHolder.add( classList );

		requiredButton = new Anchor("Mark Required");
		optionalButton = new Anchor("Mark Optional");
		removeButton = new Anchor("Remove");
		continueButton = new Anchor("Continue");

		buttonHolder.add(requiredButton);
		buttonHolder.add(optionalButton);
		buttonHolder.add(removeButton);

		requiredButton.addStyleName("greybutton");
		requiredButton.addStyleName("linkbutton");
		requiredButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				ClassViewPanel.getInstance().markRequired();
			}

		});

		optionalButton.addStyleName("greybutton");
		optionalButton.addStyleName("linkbutton");
		optionalButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				ClassViewPanel.getInstance().markOptional();
			}

		});

		removeButton.addStyleName("redbutton");
		removeButton.addStyleName("linkbutton");
		removeButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				ClassViewPanel.getInstance().remove();
			}

		});
		
		continueButton.addStyleName("greenbutton");
		continueButton.addStyleName("linkbutton");
		continueButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent arg0) {
				if(!ROCSInterface.getInstance().isDisplaying(SchedulerFilterDisplayPanel.getInstance())) {
					ROCSInterface.getInstance().show(SchedulerFilterDisplayPanel.getInstance(), true);
				}
			}
			
		});

		this.add(layout);
		this.add(continueButton);
	}

	public void printMsg( String str )
	{
		classList.addHTML( str, "MESSAGE" );
	}

	private void updateList()
	{
		curCourses = SchedulerManager.getInstance().getSelectedCourses();
		classList.clear();
		for(int x = 0; x < curCourses.size(); x++)
		{
			Course course = curCourses.get(x).getCourse();
			String data = "";
			if( SchedulerManager.getInstance().isCourseRequired( course ) )
			{
				data += "R&nbsp;&nbsp;";
			}
			else
			{
				data += "&nbsp;&nbsp;&nbsp;";
			}
			data += course.getListDescription();
			classList.addHTML(data, course.getDept()+course.getNum());
		}
	}

	public void handleEvent(CourseStatusObject status)
	{
		Log.debug("Class " + status.getCourse().getName() + " has status " + (status.getRequired() ? "required" : "optional"));
		this.updateList();
	}

	public void markRequired()
	{
		for(Pair p : rows) {
			if(p.check.isChecked()) {
				SchedulerManager.getInstance().setCourseRequired(p.course);
				p.check.setChecked(false);
			}
		}
	}

	public void markOptional()
	{
		for(Pair p : rows) {
			if(p.check.isChecked()) {
				SchedulerManager.getInstance().setCourseOptional(p.course);
				p.check.setChecked(false);
			}
		}
	}

	public void remove()
	{
		ArrayList<Course> temp = new ArrayList<Course>();
		for(Pair p : rows) {
			Log.debug(p.course.getName()+": "+p.check.isChecked());
			Log.debug(p.course.getName()+" (attached?): "+p.check.isAttached());
			if(p.check.isChecked()) {
				temp.add(p.course);
				p.check.setChecked(false);
			}
		}
		for(int i=0; i<temp.size(); i++)
		{
			SchedulerManager.getInstance().removeCourse(temp.get(i));
		}
	}
}
