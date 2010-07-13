package edu.rpi.rocs.client.objectmodel;

import java.io.Serializable;

public class SectionStatusObject implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7548924475872625796L;
	/**
	 * The course to store status about
	 */
	private Section theSection=null;
	/**
	 * Flag for whether a course is required or not
	 */
	private boolean included=true;
	
	/**
	 * Creates a course status given a course and whether it is required
	 * @param c The course to store
	 * @param required Whether the course is required
	 */
	public SectionStatusObject(Section s, boolean included) {
		theSection = s;
		this.included = included;
		getSection();
	}
	
	public SectionStatusObject() {
		theSection = null;
		this.included = false;
	}
	
	/**
	 * Sets the requirement flag
	 * @param required true if required, false if optional
	 */
	public void setIncluded(boolean included) {
		this.included = included;
	}
	
	/**
	 * Gets the requirement flag
	 * @return The requirement flag
	 */
	public boolean getIncluded() {
		return included;
	}
	
	/**
	 * Gets the course we're storing status of
	 * @return The course
	 */
	public Section getSection() {
		return theSection;
	}
	
	public void setSection(Section s) {
		theSection = s;
	}
	
	private Long dbid=null;
	public void setDbid(Long id) {
		dbid = id;
	}
	public Long getDbid() {
		return dbid;
	}
}
