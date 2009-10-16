package edu.rpi.rocs.client.objectmodel;

import java.io.Serializable;

/**
 * Pair containing semester ID and semester name for users to choose from.
 * 
 * @author JonZolla
 * @author ewpatton
 *
 */
public class SemesterDescription implements Serializable, Comparable<SemesterDescription> {
	/**
	 * UID needed for Serializable interface
	 */
	private static final long serialVersionUID = -8278743455161603450L;
	
	/** Human-readable description and internal semester id number */
	private String description;
	private Integer semesterId;
	
	/**
	 * Default constructor needed for Serializable interface
	 */
	public SemesterDescription() {
		description = "";
		semesterId = new Integer(-1);
	}
	
	/**
	 * Custom constructor to populate private members
	 * 
	 * @param semesterId Semester identifier
	 * @param description Human-readable description
	 */
	public SemesterDescription(Integer semesterId, String description) {
		setSemesterId(semesterId);
		setDescription(description);
	}
	
	/**
	 * Gets the written description of this semester
	 * 
	 * @return Human-readable text
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Sets the written description of this semester
	 * 
	 * @param description Human-readable text
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * Gets the internal semester identifier
	 * 
	 * @return Semester ID
	 */
	public Integer getSemesterId() {
		return semesterId;
	}
	
	/**
	 * Sets the internal semester identifier
	 * 
	 * @param semesterId Value for semester id
	 */
	public void setSemesterId(Integer semesterId) {
		this.semesterId = semesterId;
	}

	public int compareTo(SemesterDescription o) {
		// TODO Auto-generated method stub
		return o.getSemesterId().intValue() - semesterId.intValue();
	}
	
}
