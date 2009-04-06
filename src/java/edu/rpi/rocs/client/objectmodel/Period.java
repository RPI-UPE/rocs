package edu.rpi.rocs.client.objectmodel;

import java.util.ArrayList;
import java.util.Date;

/**
 * Stores time information about a particular section of a course.
 * 
 * @author elsajg
 * @author ewpatton
 *
 */
public class Period extends MajorMinorRevisionObject {

    /**
	 * UID for Serializable interface
	 */
	private static final long serialVersionUID = 9074143218077988582L;
	
	/** Protected data members set by @see edu.rpi.rocs.server.objectmodel.PeriodImpl */
    protected String type;
    protected String instructor;
    protected ArrayList<Integer> days;
    protected Date start;
    protected Date end;
    protected String location;
    
    /**
     * Default constructor needed for Serializable interface
     */
    public Period() {
    	type = "";
    	instructor = "";
    	days = new ArrayList<Integer>();
    	start = new Date();
    	end = new Date();
    	location = "";
    }
    
    /**
     * Custom constructor to populate protected members
     */
    public Period(String aType, String aInstructor, ArrayList<Integer> someDays, Date aStart, Date aEnd, String aLocation) {
    	type = aType;
    	instructor = aInstructor;
    	days = someDays;
    	if(someDays == null)
    		days = new ArrayList<Integer>();
    	start = aStart;
    	end = aEnd;
    	location = aLocation;
    }
    
    /**
     * Gets the type of period this is. Examples: "LEC", "LAB"
     * 
     * @return Period type
     */
    public String getType(){
        return type;
    }
    
    /**
     * Gets the name of the instructor for this section.
     * 
     * @return The instructor's name (if applicable)
     */
    public String getInstructor(){
        return instructor;
    }
    
    /**
     * Gets the days this period occurs on.
     * 
     * @return A list of days
     */
    public ArrayList<Integer> getDays() {
    	return new ArrayList<Integer>(days);
    }
    
    /**
     * Gets the starting time of this period.
     * 
     * @return The start "time" (as Date)
     */
    public Date getStart(){
        return (Date)start.clone();
    }
    
    /**
     * Gets the ending time of this period.
     * 
     * @return The end "time" (as Date)
     */
    public Date getEnd(){
        return (Date)end.clone();
    }
    
    /**
     * Gets the location where this section meets.
     * 
     * @return Location string
     */
    public String getLocation(){
        return location;
    }
}
