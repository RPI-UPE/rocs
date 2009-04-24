package edu.rpi.rocs.server.objectmodel;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.rpi.rocs.client.objectmodel.CrossListing;
import edu.rpi.rocs.exceptions.InvalidCourseDatabaseException;

public class CrossListingParser {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1548320920265726231L;

	public static CrossListing parse(Node src) throws InvalidCourseDatabaseException {
		CrossListing crossList = new CrossListing();
		crossList.setNumberOfSeats(new Integer(src.getAttributes().getNamedItem("seats").getNodeValue()));
		NodeList children = src.getChildNodes();
		for(int i=0;i<children.getLength();i++) {
			Node n = children.item(i);
			if(n.getNodeName().equalsIgnoreCase("CRN")) {
				if(n.getFirstChild().getNodeName()!="#text") {
					throw new InvalidCourseDatabaseException("Invalid child node for CRN.");
				}
				else {
					crossList.addCrn(new Integer(n.getFirstChild().getNodeValue()));
				}
			}
			else if(n.getNodeName()=="#text") {
				
			}
			else {
				throw new InvalidCourseDatabaseException("Invalid child node for CrossListing.");
			}
		}
		crossList.updateMajorRevision();
		return crossList;
	}
	

}
