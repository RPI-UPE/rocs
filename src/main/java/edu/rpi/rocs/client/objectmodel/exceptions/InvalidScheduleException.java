package edu.rpi.rocs.client.objectmodel.exceptions;

public class InvalidScheduleException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3792584110690727928L;
	String msg;
	
	public InvalidScheduleException(String msg) {
		this.msg = msg;
	}
	
	public String toString() {
		return msg;
	}
}
