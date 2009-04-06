package edu.rpi.rocs.exceptions;

/**
 * This exception is thrown if an error is encountered while loading the course database from an XML file.
 * 
 * @author ewpatton
 *
 */
public class InvalidCourseDatabaseException extends Exception {

	/**
	 * UID needed for Serializable interface
	 */
	private static final long serialVersionUID = 2516051457225831028L;

	/** Extra data to be passed along */
	private String data;
	
	/**
	 * Constructor for exception
	 * 
	 * @param someData Human-readable string to be rendered
	 */
	public InvalidCourseDatabaseException(String someData) {
		data = someData;
	}
	
	/**
	 * Returns the human-readable text for this exception
	 */
	public String toString() {
		return data;
	}
}
