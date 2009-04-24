package edu.rpi.rocs.client.filters.course.reason;

public enum InvalidReason {
	NONE(ReasonSeverity.ACCEPTED, null),
	WRONG_DEPARTMENT(ReasonSeverity.FILTERED, null);
	
	private final String styleName;
	private final ReasonSeverity severity;
	
	private InvalidReason(ReasonSeverity severity, String styleName) {
		this.severity = severity;
		this.styleName = styleName;
	}
	public ReasonSeverity getSeverity() {
		return severity;
	}
	public String getStyleName() {
		return styleName;
	}
}
