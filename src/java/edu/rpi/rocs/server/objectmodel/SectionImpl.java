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
public class SectionImpl extends Section {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7946967848665436746L;

	public SectionImpl(Node src) throws InvalidCourseDatabaseException {
		super();
		for(int i=0;i<src.getAttributes().getLength();i++) {
			Node n = src.getAttributes().item(i);
			String name = n.getNodeName();
			String val = n.getNodeValue();
			if(name == "crn") {
				crn = Integer.parseInt(val);
			}
			else if(name == "num") {
				number = Integer.parseInt(val);
			}
			else if(name == "students") {
				students = Integer.parseInt(val);
			}
			else if(name == "seats") {
				seats = Integer.parseInt(val);
			}
			else if(name == "closed") {
				closed = Boolean.parseBoolean(val);
			}
			else {
				throw new InvalidCourseDatabaseException("Section node has invalid attribute.");
			}
		}
		NodeList children = src.getChildNodes();
		for(int i=0;i<children.getLength();i++) {
			Node n = children.item(i);
			if(n.getNodeName() == "Period") {
				PeriodImpl p = new PeriodImpl(n);
				periods.add(p);
			}
			else if(n.getNodeName() == "#text") {
				
			}
			else {
				throw new InvalidCourseDatabaseException("Section node has invalid child.");
			}
		}
	}
	
    public void setCRN(int newValue){
        crn = newValue;
    }
    
    public void setNumber(int newValue){
        number = newValue;
    }
    
    public void setStudents(int newValue){
        students = newValue;
    }
    
    public void setSeats(int newValue){
        seats = newValue;
    }
    
    public void setClosed(boolean newValue){
        closed = newValue;
    }
    
    public void addPeriod(Period p) {
    	periods.add(p);
    }
    
    public void removePeriod(Period p) {
    	periods.remove(p);
    }
}
