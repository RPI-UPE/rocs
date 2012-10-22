package edu.rpi.rocs.client.filters.course;

import com.google.gwt.user.client.ui.Widget;

import edu.rpi.rocs.client.filters.course.reason.InvalidReason;
import edu.rpi.rocs.client.objectmodel.Course;
import edu.rpi.rocs.client.objectmodel.Section;

/**
 * Course filters are classes which test a class against
 * a particular restriction set by the user. The user should
 * be able to add as many restrictions as he or she needs to
 * find the courses he or she desires.
 * 
 * @author jon, ewpatton
 *
 */
public abstract class CourseFilter {
	/**
	 * Returns the widget which the user manipulates to change the
	 * options in the course filter.
	 * @return A GWT Widget to be displayed
	 */
	public abstract Widget getFilterWidget();
	/**
	 * The reason a particular course was rejected by the filter.
	 * @return An InvalidReason object to explain course rejection
	 */
	public abstract InvalidReason getReason();
	
	//Return true if course passes, false if not
	//Return true if section passes, false if not
	/**
	 * Tests a course against the filter and returns whether it is valid or not.
	 * @param course A Course to test against the filter
	 * @return The course's validity
	 * @see #filter(Section)
	 */
	protected abstract boolean filter(Course course);
	/**
	 * Tests a section against the filter and returns whether it is valid or not.
	 * @param section A Section to test against the filter
	 * @return The section's validity
	 * @see #filter(Course)
	 */
	protected abstract boolean filter(Section section);
		

	
}
