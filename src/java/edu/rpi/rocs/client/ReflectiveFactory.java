package edu.rpi.rocs.client;

import com.google.gwt.core.client.GWT;

public class ReflectiveFactory implements FactoryWrapper {
	static Factory theInstance=null;
	
	private ReflectiveFactory() {
		
	}
	
	public static Factory get() {
		if(theInstance == null) theInstance = (Factory)GWT.create(ReflectiveFactory.class);
		return theInstance;
	}
}
