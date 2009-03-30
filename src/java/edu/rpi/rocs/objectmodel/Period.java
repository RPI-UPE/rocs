package edu.rpi.rocs.objectmodel;

public class Period {

    //class variables
    private String type;
    private String instructor;
    private String day;
    private String start;
    private String end;
    private String location;
    
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
    
    public void setDay(String newValue){
        day = newValue;
    }
    
    public String getDay(){
        return day;
    }
    
    public void setStart(String newValue){
        start = newValue;
    }
    
    public String getStart(){
        return start;
    }
    
    public void setEnd(String newValue){
        end = newValue;
    }
    
    public String getEnd(){
        return end;
    }
    
    public void setLocation(String newValue){
        location = newValue;
    }
    
    public String getLocation(){
        return location;
    }
}
