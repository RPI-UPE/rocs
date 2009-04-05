package edu.rpi.rocs.client.objectmodel;

import java.util.ArrayList;
import java.util.Date;

public class Period extends MajorMinorRevisionObject {

    //class variables
    private String type;
    private String instructor;
    private ArrayList<Integer> days;
    private Date start;
    private Date end;
    private String location;
    
    /**
     * Default constructor
     * 
     * @param newValue
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
    
    //accessor functions
    public void setType(String newValue){
        type = newValue;
    }
    
    public String getType(){
        return type;
    }
    
    public void setInstructor(String newValue){
        instructor = newValue;
    }
    
    public String getInstructor(){
        return instructor;
    }
    
    public void addDay(int day) {
    	days.add(new Integer(day));
    }
    
    public void addDay(Integer day) {
    	days.add(day);
    }
    
    public ArrayList<Integer> getDays() {
    	return days;
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
    
    public Date getStart(){
        return start;
    }
    
    public void setEnd(Date newValue){
        end = newValue;
    }
    
    public Date getEnd(){
        return end;
    }
    
    public void setLocation(String newValue){
        location = newValue;
    }
    
    public String getLocation(){
        return location;
    }
}
