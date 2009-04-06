package edu.rpi.rocs.client.objectmodel;

import java.io.Serializable;

public class SemesterDescription implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8278743455161603450L;
	private String description;
	private Integer semesterId;
	
	public SemesterDescription() {
		description = "";
		semesterId = new Integer(-1);
	}
	
	public SemesterDescription(Integer semesterId, String description) {
		setSemesterId(semesterId);
		setDescription(description);
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getSemesterId() {
		return semesterId;
	}
	public void setSemesterId(Integer semesterId) {
		this.semesterId = semesterId;
	}
	
}
