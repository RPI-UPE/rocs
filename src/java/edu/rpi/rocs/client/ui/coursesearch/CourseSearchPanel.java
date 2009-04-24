package edu.rpi.rocs.client.ui.coursesearch;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.rpi.rocs.client.SemesterManager;
import edu.rpi.rocs.client.filters.course.CourseFilterEnum;
import edu.rpi.rocs.client.filters.course.reason.InvalidReason;
import edu.rpi.rocs.client.filters.course.reason.ReasonSeverity;
import edu.rpi.rocs.client.objectmodel.Course;
import edu.rpi.rocs.client.objectmodel.Semester;
import edu.rpi.rocs.client.ui.semesterselect.SemesterSelectionPanel;

public class CourseSearchPanel extends VerticalPanel implements ClickHandler {

	private Label title = new Label("Search Courses:");
	
	private Button searchButton = new Button("Search");
	
	private static CourseSearchPanel instance = null;
	public static CourseSearchPanel getInstance() {
		if (instance == null) {
			instance = new CourseSearchPanel();
		}
		return instance;
	}
	
	private CourseSearchPanel() {
		this.add(title);
		CourseFilterEnum filters[] = CourseFilterEnum.values();
		for(int i = 0; i < filters.length; i++) {
			this.add(filters[i].getFilter().getFilterWidget());
		}
		searchButton.addClickHandler(this);
		this.add(searchButton);
	}

	public void onClick(ClickEvent event) {
		if (event.getSource() == searchButton) {
			search();
		}
	}
	
	public void search() {
		Semester semester = SemesterManager.getInstance().getCurrentSemester();
		List<Course> courses = semester.getCourses();
		CourseResultList.getInstance().clear();
		for(int i = 0; i < courses.size(); i++) {
			Course course = courses.get(i);
			InvalidReason reason = CourseFilterEnum.filterCourseLevel(course);
			if ((reason == null) || (reason.getSeverity() != ReasonSeverity.FILTERED)) {
				CourseResultList.getInstance().addCourse(course, reason);
			}
		}
	}
}
