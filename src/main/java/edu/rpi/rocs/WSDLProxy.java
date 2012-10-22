package edu.rpi.rocs;

import java.io.IOException;
import java.net.URL;

import edu.rpi.rocs.client.objectmodel.Schedule;

/**
 * Hides the complexity of WSDL by providing functions which wrap the
 * functionality of WSDLQuery, WSDLFunction, and WSDLQueryEngine into a single
 * class.
 * 
 * @author ewpatton
 *
 */
public class WSDLProxy {
	/**
	 * Connects to a service and caches the resulting WSDLQueryEngine
	 * 
	 * @param aService Service to connect to
	 * @return true if successful
	 * @throws WSDLException
	 * @throws IOException
	 */
	public boolean connectToService(String aService) throws WSDLException, IOException {
		throw new WSDLNotImplementedException();
	}
	
	/**
	 * Connects to a service and caches the resulting WSDLQueryEngine
	 * 
	 * @param aService URL to connect to
	 * @return true if successful
	 * @throws WSDLException
	 * @throws IOException
	 */
	public boolean connectToService(URL aService) throws WSDLException, IOException {
		throw new WSDLNotImplementedException();
	}
	
	/**
	 * 
	 * @param aSchedule Custom Schedule for user
	 * @return true if successful
	 * @throws WSDLException
	 * @throws IOException
	 */
	public boolean saveSchedule(Schedule aSchedule) throws WSDLException, IOException {
		throw new WSDLNotImplementedException();
	}
	
	public boolean loadSchedule(Schedule aSchedule) throws WSDLException, IOException {
		throw new WSDLNotImplementedException();
	}
	
	public boolean registerClasses(Schedule aSchedule) throws WSDLException, IOException {
		throw new WSDLNotImplementedException();
	}
}
