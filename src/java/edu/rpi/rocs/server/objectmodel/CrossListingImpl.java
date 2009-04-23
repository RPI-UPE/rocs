package edu.rpi.rocs.server.objectmodel;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.rpi.rocs.client.objectmodel.CrossListing;
import edu.rpi.rocs.exceptions.InvalidCourseDatabaseException;

public class CrossListingImpl extends CrossListing {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1548320920265726231L;

	public CrossListingImpl(Node src) throws InvalidCourseDatabaseException {
		super();
		numberOfSeats = new Integer(src.getAttributes().getNamedItem("seats").getNodeValue());
		NodeList children = src.getChildNodes();
		for(int i=0;i<children.getLength();i++) {
			Node n = children.item(i);
			if(n.getNodeName().equalsIgnoreCase("CRN")) {
				if(n.getFirstChild().getNodeName()!="#text") {
					throw new InvalidCourseDatabaseException("Invalid child node for CRN.");
				}
				else {
					crns.add(new Integer(n.getFirstChild().getNodeValue()));
				}
			}
			else if(n.getNodeName()=="#text") {
				
			}
			else {
				throw new InvalidCourseDatabaseException("Invalid child node for CrossListing.");
			}
		}
		updateMajorRevision();
	}
	
	/**
	 * Adds a CRN to a cross listing.
	 * 
	 * @param crn The CRN to be added.
	 */
	public void addCRNToCrossListing(int crn) {
		crns.add(new Integer(crn));
		updateMajorRevision();
	}
	
	/**
	 * Remove a CRN from a cross listing.
	 * 
	 * @param crn The CRN to be removed.
	 */
	public void removeCRNFromCrossListing(int crn) {
		crns.remove(new Integer(crn));
		updateMajorRevision();
	}
	
	/**
	 * Sets the number of seats in the crosslisting
	 * 
	 * @param seats Total number of seats
	 */
	public void setNumberOfSeats(int seats) {
		numberOfSeats = seats;
		updateMinorRevision();
	}
	
	/**
	 * Sets the number of seats in the crosslisting
	 * 
	 * @param seats Total number of seats
	 */
	public void setNumberOfSeats(Integer seats) {
		numberOfSeats = seats;
		updateMinorRevision();
	}
	
	/**
	 * Sets the unique identifier of this cross listing.
	 * 
	 * @param id New Unique Identifier
	 */
	public void setUID(int id) {
		uid = id;
	}
}
