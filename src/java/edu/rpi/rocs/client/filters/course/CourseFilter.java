package edu.rpi.rocs.client.filters.course;

import com.google.gwt.user.client.ui.Widget;

import edu.rpi.rocs.client.filters.course.reason.InvalidReason;
import edu.rpi.rocs.client.objectmodel.Course;
import edu.rpi.rocs.client.objectmodel.Section;

public abstract class CourseFilter {	
	public abstract Widget getFilterWidget();
	public abstract InvalidReason getReason();
	
	//Return true if course passes, false if not
	//Return true if section passes, false if not
	protected abstract boolean filter(Course course);
	protected abstract boolean filter(Section section);
		

	
}
