package edu.rpi.rocs.server.objectmodel;

import java.util.ArrayList;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.rpi.rocs.client.objectmodel.Period;
import edu.rpi.rocs.client.objectmodel.Time;
import edu.rpi.rocs.exceptions.InvalidCourseDatabaseException;

public class PeriodImpl extends Period {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6624571166889630370L;
	
	public PeriodImpl(String type, String instructor,
			ArrayList<Integer> someDays, Time start, Time end, String location) {
		super(type, instructor, someDays, start, end, location);
		// TODO Auto-generated constructor stub
	}
	
	public PeriodImpl(Node src) throws InvalidCourseDatabaseException {
		super();
		this.type = "TBA";
		this.instructor = "TBA";
		this.location = "TBA";
		for(int i=0;i<src.getAttributes().getLength();i++) {
			Node n = src.getAttributes().item(i);
			String name = n.getNodeName();
			String value = n.getNodeValue();
			if(name == "type") {
				this.type = value;
			}
			else if(name == "instructor") {
				this.instructor = value;
			}
			else if(name == "start") {
				this.start = new Time(value);
			}
			else if(name == "end") {
				this.end = new Time(value);
			}
			else if(name == "location") {
				this.location = value;
			}
		}
		NodeList l = src.getChildNodes();
		for(int i=0;i<l.getLength();i++) {
			Node n = l.item(i);
			if(n.getNodeName().equalsIgnoreCase("Day")) {
				this.days.add(Integer.valueOf(n.getFirstChild().getNodeValue()));
			}
			else if(n.getNodeName() == "#text") {
				
			}
			else {
				throw new InvalidCourseDatabaseException("Invalid child node for Period");
			}
		}
	}
	
	public void setType(String newValue){
        type = newValue;
    }
    
    public void setInstructor(String newValue){
        instructor = newValue;
    }
    
    public void addDay(int day) {
    	days.add(new Integer(day));
    }
    
    public void addDay(Integer day) {
    	days.add(day);
    }
    
    public void removeDay(int day) {
    	days.remove(new Integer(day));
    }
    
    public void removeDay(Integer day) {
    	days.remove(day);
    }
    
    public void setStart(Time newValue){
        start = newValue;
    }
    
    public void setEnd(Time newValue){
        end = newValue;
    }
    
    public void setLocation(String newValue){
        location = newValue;
    }
}
