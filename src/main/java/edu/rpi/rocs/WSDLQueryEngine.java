package edu.rpi.rocs;

import java.io.IOException;
import java.net.URL;

/**
 * WSDLQueryEngine describes the interface for a service based on the Web 
 * Service Description Language (WSDL). The QueryEngine allows the system
 * to query a remote web service, determine its functionality, and execute
 * queries against the service.
 * 
 * @author ewpatton
 * @version %I%
 * 
 */
public interface WSDLQueryEngine {
	
	/**
	 * Reads a WSDL decription at src and stores its representation.
	 * 
	 * @param aSrc The URL pointing to a WSDL description
	 * @return true on success, false if src does not contain a WSDL description
	 * @throws IOException
	 */
	boolean readWSDLDescription(URL aSrc) throws IOException;
	
	/**
	 * Determines whether a named function is implemented by this web service.
	 * 
	 * @param aName The name of the function to locate
	 * @return true on success, false if unsuccessful
	 * @throws WSDLException
	 */
	boolean providesFunction(String aName) throws WSDLException;
	
	/**
	 * Returns a WSDLFunction which describes the named function and its parameters.
	 * 
	 * @param aName The name of the function to describe
	 * @return A WSDLFunction which describes the function named by name
	 * @throws WSDLException
	 */
	WSDLFunction getFunctionDescription(String aName) throws WSDLException;
	
	/**
	 * Executes a query against this web service.
	 * 
	 * @param aQuery
	 * @return An object representing the response of the service to aQuery
	 * @throws WSDLException
	 * @throws IOException
	 */
	Object executeQuery(WSDLQuery aQuery) throws WSDLException, IOException;
}
