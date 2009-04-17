package edu.rpi.rocs.server.objectmodel;

import java.util.ArrayList;
import java.util.Date;

import org.w3c.dom.Node;

import edu.rpi.rocs.client.objectmodel.Period;

public class PeriodImpl extends Period {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6624571166889630370L;

	public PeriodImpl(String type, String instructor,
			ArrayList<Integer> someDays, Date start, Date end, String location) {
		super(type, instructor, someDays, start, end, location);
		// TODO Auto-generated constructor stub
	}
	
	public PeriodImpl(Node src) {
		super();
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
    
    public void setStart(Date newValue){
        start = newValue;
    }
    
    public void setEnd(Date newValue){
        end = newValue;
    }
    
    public void setLocation(String newValue){
        location = newValue;
    }
}
