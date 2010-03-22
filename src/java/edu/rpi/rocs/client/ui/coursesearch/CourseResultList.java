package edu.rpi.rocs.client.ui.coursesearch;

import java.util.ArrayList;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

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

		}
	};

	CourseRemovedHandler removedHandler = new CourseRemovedHandler() {
		public void handleEvent(CourseStatusObject status) {

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

		SchedulerManager.getInstance().addCourseAddedEventHandler(addedHandler);
		SchedulerManager.getInstance().addCourseRemovedEventHandler(removedHandler);
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
			SchedulerManager.getInstance().addCourse(c);
			if(!ROCSInterface.getInstance().isDisplaying(ClassViewPanel.getInstance()))
				ROCSInterface.getInstance().show(ClassViewPanel.getInstance(), true);
			CourseSearchPanel.getInstance().redosearch();
		}

	};

	int prevstatus;
	public void add(Course a, int status) {
		results.add(a);
		prevstatus = status;
		HTMLTableListRow r=m_table.new HTMLTableListRow();
		HTMLTableListCell c;
		c = m_table.new HTMLTableListCell(); c.setStyleName("dept"); c.setText(a.getDept()); r.add(c);
		c = m_table.new HTMLTableListCell(); c.setStyleName("num"); c.setText(Integer.toString(a.getNum())); r.add(c);
		c = m_table.new HTMLTableListCell(); c.setStyleName("seats"); c.setText(a.getFilledSeats() + " of " +a.getTotalSeats()); r.add(c);
		c = m_table.new HTMLTableListCell(); c.setStyleName("title"); c.setText(a.getName()); r.add(c);
		c = m_table.new HTMLTableListCell(); c.setText(a.getInstructorString()); r.add(c);
		if((status & State.CONFLICT) == State.CONFLICT) {
			r.setStyleName("course-conflict");
			r.setTitle("Conflicts with another course");
		}
		else if((status & State.CLOSED) == State.CLOSED) {
			r.setStyleName("course-closed");
			r.setTitle("Course closed to registration");
		}
		else if((status & State.CHOSEN) == State.CHOSEN) {
			r.setStyleName("course-selected");
			r.setTitle("Course selected for scheduling");
		}
		else {
			r.setStyleName("course-selectable");
			r.addClickHandler(clickHandler);
			r.setTitle("Click to add this course");
		}
		m_table.add(r);
	}

	public void modifyBits(int index, int status)
	{
		if (prevstatus == status) return;
		else prevstatus = status;

		HTMLTableListRow r = m_table.get(index+1);

		if((status & State.CONFLICT) == State.CONFLICT) {
			r.setStyleName("course-conflict");
			r.setTitle("Conflicts with another course");
		}
		else if((status & State.CLOSED) == State.CLOSED) {
			r.setStyleName("course-closed");
			r.setTitle("Course closed to registration");
		}
		else if((status & State.CHOSEN) == State.CHOSEN) {
			r.setStyleName("course-selected");
			r.setTitle("Course selected for scheduling");
		}
		else {
			r.setStyleName("course-selectable");
			r.addClickHandler(clickHandler);
			r.setTitle("Click to add this course");
		}
	}

	public HTMLTableList getTable() {
		return m_table;
	}
}
