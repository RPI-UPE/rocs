package edu.rpi.rocs;

import java.io.IOException;
import java.net.URL;

/**
 * Implements the WSDLQueryEngine for communicating with a WSDL endpoint.
 * 
 * @author ewpatton
 *
 */
public class WSDLQueryEngineImpl implements WSDLQueryEngine {
	public boolean readWSDLDescription(URL src) throws IOException {
		return false;
	}
	
	public boolean providesFunction(String name) throws WSDLException {
		return false;
	}
	
	public WSDLFunction getFunctionDescription(String aName) throws WSDLException {
		return null;
	}
	
	public Object executeQuery(WSDLQuery query) throws WSDLException, IOException {
		return null;
	}
}
