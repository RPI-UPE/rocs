package edu.rpi.rocs.client.filters.course;

import edu.rpi.rocs.client.filters.course.reason.InvalidReason;
import edu.rpi.rocs.client.filters.course.reason.ReasonSeverity;
import edu.rpi.rocs.client.objectmodel.Course;

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
	DEPARTMENT_FILTER(new DepartmentCourseFilter());
	
	private CourseFilter filter;
	private CourseFilterEnum(CourseFilter filter) {
		this.filter = filter;
	}
	public CourseFilter getFilter() {
		return filter;
	}
	
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
