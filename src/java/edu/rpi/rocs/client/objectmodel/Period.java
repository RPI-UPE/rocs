package edu.rpi.rocs.client.objectmodel;


import java.util.ArrayList;
import java.util.List;


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
    protected Time start;
    protected Time end;
    protected String location;
    
    /**
     * Default constructor needed for Serializable interface
     */
    public Period() {
    	type = "";
    	instructor = "";
    	days = new ArrayList<Integer>();
    	start = new Time();
    	end = new Time();
    	location = "";
    }
    
    /**
     * Custom constructor to populate protected members
     */
    public Period(String aType, String aInstructor, ArrayList<Integer> someDays, Time aStart, Time aEnd, String aLocation) {
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
    public List<Integer> getDays() {
    	return new ArrayList<Integer>(days);
    }
    
    public void setDays(List<Integer> list) {
    	days = new ArrayList<Integer>(list);
    }
    
    /**
     * Gets the starting time of this period.
     * 
     * @return The start "time" (as Date)
     */
    public Time getStart(){
        return (Time)start.clone();
    }
    
    /**
     * Gets the ending time of this period.
     * 
     * @return The end "time" (as Date)
     */
    public Time getEnd(){
        return (Time)end.clone();
    }
    
    /**
     * Gets the location where this section meets.
     * 
     * @return Location string
     */
    public String getLocation(){
        return location;
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
    
    public Integer getStartInt() {
    	return start.getAbsMinute();
    }
    
    public Integer getEndInt() {
    	return end.getAbsMinute();
    }
    
    public void setStartInt(Integer x) {
    	start = new Time(x);
    }
    
    public void setEndInt(Integer x) {
    	end = new Time(x);
    }
    
    public void setLocation(String newValue){
        location = newValue;
    }
    
    private Long dbid;
    
    public Long getDbid() {
    	return dbid;
    }
    
    public void setDbid(Long id) {
    	dbid = id;
    }
    
    public String toString() {
    	return "{days:"+days.toString()+",start:"+start.getHour()+""+start.getMinute()+",end:"+end.getHour()+""+end.getMinute()+"}";
    }
}
