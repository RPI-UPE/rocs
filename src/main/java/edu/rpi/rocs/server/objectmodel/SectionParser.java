package edu.rpi.rocs.server.objectmodel;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.rpi.rocs.client.objectmodel.Period;
import edu.rpi.rocs.client.objectmodel.Section;
import edu.rpi.rocs.exceptions.InvalidCourseDatabaseException;

/**
 * 
 * @author elsajg
 * @author ewpatton
 *
 */
public class SectionParser{

	/**
	 * 
	 */
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 7946967848665436746L;

	public static Section parse(Node src) throws InvalidCourseDatabaseException {
		Section section = new Section();
		for(int i=0;i<src.getAttributes().getLength();i++) {
			Node n = src.getAttributes().item(i);
			String name = n.getNodeName();
			String val = n.getNodeValue();
			if(name == "crn") {
				section.setCrn(Integer.parseInt(val));
			}
			else if(name == "num") {
				section.setNumber(val);
			}
			else if(name == "students") {
				section.setStudents(Integer.parseInt(val));
			}
			else if(name == "seats") {
				section.setSeats(Integer.parseInt(val));
			}
			else {
				throw new InvalidCourseDatabaseException("Section node has invalid attribute '"+name+"'.");
			}
		}
		NodeList children = src.getChildNodes();
		for(int i=0;i<children.getLength();i++) {
			Node n = children.item(i);
			if(n.getNodeName().equalsIgnoreCase("Period")) {
				Period p = PeriodParser.parse(n);
				section.addPeriod(p);
			}
			else if(n.getNodeName().equalsIgnoreCase("Note")) {
				section.addNote(n.getFirstChild().getNodeValue());
			}
			else if(n.getNodeName() == "#text") {
				
			}
			else {
				throw new InvalidCourseDatabaseException("Section node has invalid child.");
			}
		}
		return section;
	}

}
