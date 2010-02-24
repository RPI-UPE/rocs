package edu.rpi.rocs.client;

import com.google.gwt.core.client.GWT;
/**
 * The ReflectiveFactory class is used by the GWT compiler to generate reflecting
 * classes that should be instantiable at runtime.
 * @author ewpatton
 *
 */
public class ReflectiveFactory implements FactoryWrapper {
	static Factory theInstance=null;

	private ReflectiveFactory() {
		
	}
	/**
	 * A singleton function that generates the ReflectiveFactory if it doesn't exist.
	 * Otherwise, it returns the previous instantiation.
	 * @return an instance of the ReflectiveFactory.
	 */
	public static Factory get() {
		if(theInstance == null) theInstance = (Factory)GWT.create(ReflectiveFactory.class);
		return theInstance;
	}
}
