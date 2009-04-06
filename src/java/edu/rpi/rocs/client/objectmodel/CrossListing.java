package edu.rpi.rocs.client.objectmodel;

import java.util.ArrayList;

/**
 * CrossListing class which stores information about multiple classes if they 
 * are crosslisted in SIS.
 * 
 * @author ewpatton
 *
 */
public class CrossListing extends MajorMinorRevisionObject {
	/**
	 * UID for Serializable interface
	 */
	private static final long serialVersionUID = 3406898058852760862L;
	
	/** Protected members set by @see edu.rpi.rocs.server.objectmodel.CrossListing */
	protected ArrayList<Integer> crns;
	protected int numberOfSeats;
	protected int uid;
	
	/**
	 * Default constructor
	 */
	public CrossListing() {
		crns = new ArrayList<Integer>();
	}
	
	/**
	 * Gets the list of CRNs for this cross listing.
	 * 
	 * @return An array of CRNs
	 */
	public ArrayList<Integer> getCRNs() {
		return new ArrayList<Integer>(crns);
	}
	
	/**
	 * Gets the number of seats in this crosslisting
	 * 
	 * @return Total number of seats
	 */
	public int getNumberOfSeats() {
		return numberOfSeats;
	}
	
	/**
	 * Gets the unique identifier of this cross listing
	 * 
	 * @return Unique Identifier
	 */
	public int getUID() {
		return uid;
	}
}
