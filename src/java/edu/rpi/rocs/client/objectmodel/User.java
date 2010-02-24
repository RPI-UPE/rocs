package edu.rpi.rocs.client.objectmodel;


public class User {
	private native static int readUserName()/*-{
		return $wnd.rocsUserName;
	}-*/;
	public static String getUserID() {
		return new Integer("pattoe".hashCode()).toString();
		//return new Integer(readUserName()).toString();
	}
}
