package edu.rpi.rocs.client.objectmodel;

import java.io.Serializable;

/**
 * This is a helper class which stores whether a course is required or optional for inclusion
 * in generated schedules.
 * 
 * @author ewpatton
 *
 */
public class CourseStatusObject implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7548924475872625796L;
	/**
	 * The course to store status about
	 */
	private Course theCourse=null;
	/**
	 * Flag for whether a course is required or not
	 */
	private boolean isRequired=true;
	
	/**
	 * Creates a course status given a course and whether it is required
	 * @param c The course to store
	 * @param required Whether the course is required
	 */
	public CourseStatusObject(Course c, boolean required) {
		theCourse = c;
		isRequired = required;
		getCourse();
	}
	
	public CourseStatusObject() {
		theCourse = null;
		isRequired = false;
	}
	
	/**
	 * Sets the requirement flag
	 * @param required true if required, false if optional
	 */
	public void setRequired(boolean required) {
		isRequired = required;
	}
	
	/**
	 * Gets the requirement flag
	 * @return The requirement flag
	 */
	public boolean getRequired() {
		return isRequired;
	}
	
	/**
	 * Gets the course we're storing status of
	 * @return The course
	 */
	public Course getCourse() {
		return theCourse;
	}
	
	public void setCourse(Course c) {
		theCourse = c;
	}
	
	private Long dbid=null;
	public void setDbid(Long id) {
		dbid = id;
	}
	public Long getDbid() {
		return dbid;
	}
}
