package edu.rpi.rocs.client.ui.coursesearch;

import java.util.ArrayList;
import java.util.Set;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;

import edu.rpi.rocs.client.objectmodel.Course;
import edu.rpi.rocs.client.objectmodel.CourseStatusObject;
import edu.rpi.rocs.client.objectmodel.SchedulerManager;
import edu.rpi.rocs.client.objectmodel.SchedulerManager.CourseAddedHandler;
import edu.rpi.rocs.client.objectmodel.SchedulerManager.CourseRemovedHandler;
import edu.rpi.rocs.client.ui.HTMLTableList;
import edu.rpi.rocs.client.ui.ROCSInterface;
import edu.rpi.rocs.client.ui.HTMLTableList.HTMLTableListCell;
import edu.rpi.rocs.client.ui.HTMLTableList.HTMLTableListRow;
import edu.rpi.rocs.client.ui.classview.ClassViewPanel;
import edu.rpi.rocs.client.ui.coursesearch.CourseSearchPanel.State;


public class CourseResultList {
	HTMLTableList m_table = new HTMLTableList();
	HTMLTableListRow m_header = m_table.new HTMLTableListRow();
	ArrayList<Course> results = new ArrayList<Course>();
	static CourseResultList theInstance = null;

	CourseAddedHandler addedHandler = new CourseAddedHandler() {
		public void handleEvent(CourseStatusObject status) {
			CourseSearchPanel.getInstance().redosearch();
		}
	};

	CourseRemovedHandler removedHandler = new CourseRemovedHandler() {
		public void handleEvent(CourseStatusObject status) {
			CourseSearchPanel.getInstance().redosearch();
		}
	};

	CourseResultList() {
		m_table.setStyleName("course-search-list");
		HTMLTableListCell c;
		c = m_table.new HTMLTableListCell(true); c.setText("Dept"); m_header.add(c);
		c = m_table.new HTMLTableListCell(true); c.setText("Num"); m_header.add(c);
		c = m_table.new HTMLTableListCell(true); c.setText("Seats"); m_header.add(c);
		c = m_table.new HTMLTableListCell(true); c.setText("Title"); m_header.add(c);
		c = m_table.new HTMLTableListCell(true); c.setText("Instructor"); m_header.add(c);
		m_table.add(m_header);

		/*
		SchedulerManager.getInstance().addCourseAddedEventHandler(addedHandler);
		SchedulerManager.getInstance().addCourseRemovedEventHandler(removedHandler);
		*/
	}

	public static CourseResultList getInstance() {
		if(theInstance == null) theInstance = new CourseResultList();
		return theInstance;
	}

	public void clear() {
		results.clear();
		m_table.clearEverythingButHeader();
	}

	ClickHandler clickHandler = new ClickHandler() {

		public void onClick(ClickEvent arg0) {
			Element e = arg0.getRelativeElement();
			int x = m_table.indexOfElement(e)-1;
			if(x<0) return;
			Course c = results.get(x);
			// Adding 1 here accounts for the header row.
			HTMLTableListRow r = m_table.get(x+1);
			String bits = r.getProperty("bits", null);
			if(bits==null) {
				Window.alert("bits field was null, contact the developers. CourseResultList.$ClickHandler");
			}
			else {
				int val = Integer.parseInt(bits);
				if(val == State.CONFLICT) {
					Window.alert("This course conflicts with another you've already selected.");
					return;
				}
				else if(val == State.CLOSED && !SchedulerManager.getInstance().didWarnUser(State.CLOSED)) {
					Window.alert("Warning: this course is closed.");
					SchedulerManager.getInstance().warnedUser(State.CLOSED);
				}
				else if(val == State.CHOSEN) {
					//Window.alert("You have already selected this course for your schedule.");
					SchedulerManager.getInstance().removeCourse(c);
					return;
				}
			}
			Log.debug("onclick");
			SchedulerManager.getInstance().addCourse(c);
			if(!ROCSInterface.getInstance().isDisplaying(ClassViewPanel.getInstance()))
				ROCSInterface.getInstance().show(ClassViewPanel.getInstance(), true);
		}

	};

	public void add(Course a, int status, Set<Course> conflicts) {
		results.add(a);
		HTMLTableListRow r=m_table.new HTMLTableListRow();
		HTMLTableListCell c;
		c = m_table.new HTMLTableListCell(); c.setStyleName("dept"); c.setText(a.getDept()); r.add(c);
		c = m_table.new HTMLTableListCell(); c.setStyleName("num"); c.setText(Integer.toString(a.getNum())); r.add(c);
		c = m_table.new HTMLTableListCell(); c.setStyleName("seats"); c.setText(a.getFilledSeats() + " of " +a.getTotalSeats()); r.add(c);
		c = m_table.new HTMLTableListCell(); c.setStyleName("title"); c.setText(a.getName()); r.add(c);
		c = m_table.new HTMLTableListCell(); c.setText(a.getInstructorString()); r.add(c);
		if((status & State.CONFLICT) == State.CONFLICT) {
			r.setProperty("bits", Integer.toString(State.CONFLICT));
			r.setStyleName("course-conflict");
			if(conflicts.size()>0) {
				String title = "Conflicts with: ";
				boolean first=true;
				for(Course course : conflicts) {
					if(!first) title += ", ";
					title += course.getName();
					first = false;
				}
				r.setTitle(title);
			}
			else {
				r.setTitle("Conflicts with another course");
			}
		}
		else if((status & State.CHOSEN) == State.CHOSEN) {
			r.setProperty("bits", Integer.toString(State.CHOSEN));
			r.setStyleName("course-selected");
			r.setTitle("Course selected for scheduling");
		}
		else if((status & State.CLOSED) == State.CLOSED) {
			r.setProperty("bits", Integer.toString(State.CLOSED));
			r.setStyleName("course-closed");
			r.setTitle("Course closed to registration");
		}
		else {
			r.setProperty("bits", "0");
			r.setStyleName("course-selectable");
			r.setTitle("Click to add this course");
		}
		r.addClickHandler(clickHandler);
		m_table.add(r);
	}

	public void modifyBits(int index, int status, Set<Course> conflicts)
	{
		HTMLTableListRow r = m_table.get(index+1);

		if((status & State.CONFLICT) == State.CONFLICT) {
			r.setProperty("bits", Integer.toString(State.CONFLICT));
			r.setStyleName("course-conflict");
			if(conflicts.size()>0) {
				String title = "Conflicts with: ";
				boolean first=true;
				for(Course course : conflicts) {
					if(!first) title += ", ";
					title += course.getName();
					first = false;
				}
				r.setTitle(title);
			}
			else {
				r.setTitle("Conflicts with another course");
			}
		}
		else if((status & State.CHOSEN) == State.CHOSEN) {
			r.setProperty("bits", Integer.toString(State.CHOSEN));
			r.setStyleName("course-selected");
			r.setTitle("Course selected for scheduling");
		}
		else if((status & State.CLOSED) == State.CLOSED) {
			r.setProperty("bits", Integer.toString(State.CLOSED));
			r.setStyleName("course-closed");
			r.setTitle("Course closed to registration");
		}
		else {
			r.setProperty("bits", "0");
			r.setStyleName("course-selectable");
			//r.addClickHandler(clickHandler);
			r.setTitle("Click to add this course");
		}
	}

	public HTMLTableList getTable() {
		return m_table;
	}
}
