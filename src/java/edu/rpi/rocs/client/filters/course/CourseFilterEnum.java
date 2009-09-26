package edu.rpi.rocs.client.filters.course;

import edu.rpi.rocs.client.filters.course.reason.InvalidReason;
import edu.rpi.rocs.client.filters.course.reason.ReasonSeverity;
import edu.rpi.rocs.client.objectmodel.Course;

/**
 * CourseFilterEnum contains all of the possible filters
 * which could be applied to courses during a search.
 * 
 * @author jon, ewpatton
 *
 */
public enum CourseFilterEnum {
	/*
	 * Since courses are usually filtered through
	 * this list in order, it would be a good idea
	 * to order this list by what you think will kick
	 * out most courses first, so subsequent filters
	 * don't need to be examined so much..
	 * 
	 * Just a friendly suggestion
	 */
	/**
	 * Filter for the Department field.
	 */
	DEPARTMENT_FILTER(new DepartmentCourseFilter());
	
	/**
	 * A course filter instance
	 */
	private CourseFilter filter;
	
	/**
	 * Stores a singleton of a filter
	 * @param filter The filter to apply
	 */
	private CourseFilterEnum(CourseFilter filter) {
		this.filter = filter;
	}
	
	/**
	 * Gets a filter instance from the enum.
	 * @return The filter to apply
	 */
	public CourseFilter getFilter() {
		return filter;
	}
	
	/**
	 * Checks a course against a filter and returns a reason for rejecting it.
	 * @param course A course to test against the filter
	 * @return The reason the course was rejected
	 */
	public static InvalidReason filterCourseLevel(Course course) {
		InvalidReason maxReason = null;
		for(int i = 0; i < values().length; i++) {
			CourseFilterEnum item = values()[i];
			if (item.getFilter() == null)
				continue;
			
			//True == filter passes
			if (item.getFilter().filter(course)) continue;
			
			//Otherwise get reason
			InvalidReason fail = item.getFilter().getReason();
			if ((maxReason == null) || (fail.getSeverity().isMoreSevere(maxReason.getSeverity()))) {
				maxReason = fail;
			}
			
			if (maxReason.getSeverity() == ReasonSeverity.getMaxSeverity()) break;
		}
		return maxReason;
	}
	
	
}
