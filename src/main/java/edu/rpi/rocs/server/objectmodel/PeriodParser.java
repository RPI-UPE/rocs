package edu.rpi.rocs.server.objectmodel;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.rpi.rocs.client.objectmodel.Period;
import edu.rpi.rocs.client.objectmodel.Time;
import edu.rpi.rocs.exceptions.InvalidCourseDatabaseException;

public class PeriodParser {

	/**
	 * 
	 */
	@SuppressWarnings("unused")
	private static final long serialVersionUID = -6624571166889630370L;
	
	
	public static Period parse(Node src) throws InvalidCourseDatabaseException {
		Period period = new Period();
		period.setType("TBA");
		period.setInstructor("TBA");
		period.setLocation("TBA");
		for(int i=0;i<src.getAttributes().getLength();i++) {
			Node n = src.getAttributes().item(i);
			String name = n.getNodeName();
			String value = n.getNodeValue();
			if(name == "type") {
				period.setType(value);
			}
			else if(name == "instructor") {
				period.setInstructor(value);
			}
			else if(name == "start") {
				period.setStart(new Time(value));
			}
			else if(name == "end") {
				period.setEnd(new Time(value));
			}
			else if(name == "location") {
				period.setLocation(value);
			}
		}
		NodeList l = src.getChildNodes();
		for(int i=0;i<l.getLength();i++) {
			Node n = l.item(i);
			if(n.getNodeName().equalsIgnoreCase("Day")) {
				period.addDay(Integer.valueOf(n.getFirstChild().getNodeValue()));
			}
			else if(n.getNodeName() == "#text") {
				
			}
			else {
				throw new InvalidCourseDatabaseException("Invalid child node for Period");
			}
		}
		return period;
	}

}
