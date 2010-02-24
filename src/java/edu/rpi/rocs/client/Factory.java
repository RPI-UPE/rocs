package edu.rpi.rocs.client;

/**
 * The Factory interface is used to identify a Factory object that
 * can be used by the GWT Compiler to perform runtime reflection in
 * the client javascript file.
 * 
 * @author ewpatton
 *
 */
public interface Factory {
	Instantiable newInstance(String className);
}
