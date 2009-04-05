package edu.rpi.rocs.client.objectmodel;

public class SemesterDescription {
	private String description;
	private Integer semesterId;
	
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
