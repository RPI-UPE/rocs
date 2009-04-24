package edu.rpi.rocs.client.filters.course.reason;

public enum ReasonSeverity {
	//Do nothing, class was accepted
	ACCEPTED(0),
	
	//Warn the user about something, don't
	//prevent from addding to course list
	WARNING(1),
	
	//Do not add to course list, but show it
	//in the search results
	RESTRICTED(2),
	
	//Do not show the course in the search
	//results
	FILTERED(3);
	
	private final Integer level;
	
	private ReasonSeverity(Integer level) {
		this.level = level;
	}
	
	public boolean isMoreSevere(ReasonSeverity other) {
		return (other == null) || (this.level > other.level);
	}
	
	/*public boolean equals(Object o) {
		if (!(o instanceof ReasonSeverity)) 
			return false;
		else
			return this.level.equals(((ReasonSeverity)o).level);
	}
	
	public int compareTo(ReasonSeverity)*/
	
	public static ReasonSeverity getMaxSeverity() {
		return FILTERED;
	}
}
