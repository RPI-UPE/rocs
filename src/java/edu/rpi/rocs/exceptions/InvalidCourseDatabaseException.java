package edu.rpi.rocs.exceptions;

public class InvalidCourseDatabaseException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2516051457225831028L;

	private String data;
	
	public InvalidCourseDatabaseException(String someData) {
		data = someData;
	}
	
	public String toString() {
		return data;
	}
}
