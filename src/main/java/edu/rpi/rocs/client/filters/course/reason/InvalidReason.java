package edu.rpi.rocs.client.filters.course.reason;
/**
 * InvalidReason is an enumeration which contains all the reasons 
 * for which a course may be rejected by the search pipeline.
 * @author ewpatton
 *
 */
public enum InvalidReason {
	NONE(ReasonSeverity.ACCEPTED, null),
	WRONG_DEPARTMENT(ReasonSeverity.FILTERED, null);
	
	private final String styleName;
	private final ReasonSeverity severity;
	
	private InvalidReason(ReasonSeverity severity, String styleName) {
		this.severity = severity;
		this.styleName = styleName;
	}
	/**
	 * 
	 * @return the severity of the search.
	 */
	public ReasonSeverity getSeverity() {
		return severity;
	}
	/**
	 * 
	 * @return the CSS style name, if any.
	 */
	public String getStyleName() {
		return styleName;
	}
}
