package edu.rpi.rocs;

import java.io.IOException;
import java.net.URL;

/**
 * Abstracts communication with a MySQL database as a WSDLQueryEngine.
 * 
 * @author ewpatton
 *
 */
public class WSDLMySQLQueryEngine implements WSDLQueryEngine {
	/**
	 * Reads a WSDL endpoint description into this WSDLQueryEngine
	 * 
	 * @param aSrc URL of the WSDL endpoint
	 * @return true if successful
	 * @throws IOException
	 */
	public boolean readWSDLDescription(URL aSrc) throws IOException {
		return false;
	}
	
	/**
	 * Returns if this endpoint implements the function named by aName.
	 * 
	 * @param aName Name of the function to look up
	 * @return true if found, false if not
	 * @throws WSDLException
	 */
	public boolean providesFunction(String aName) throws WSDLException {
		return false;
	}
	
	/**
	 * Gets the WSDLFunction named by aName.
	 * 
	 * @param aName Name of the WSDL function to retrieve
	 * @return WSDLFunction named by aName
	 * @throws WSDLException
	 */
	public WSDLFunction getFunctionDescription(String aName) throws WSDLException {
		return null;
	}
	
	/**
	 * Executes a WSDLQuery on a file.
	 * 
	 * @param aQuery The WSDLQuery instance to execute
	 * @return Returns the result of the execution
	 * @throws WSDLException
	 * @throws IOException
	 */
	public Object executeQuery(WSDLQuery aQuery) throws WSDLException, IOException {
		return null;
	}
}
