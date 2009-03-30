package edu.rpi.rocs.objectmodel;

public class CourseDB {
//coursedb is an object and return an instance of it
    
    //class variables
    private String timestamp;
    private String semesternumber;
    private String semesterdesc;
    
    //accessor functions
    public CourseDB(String gts, String gsn, String gsd){
        timestamp = gts;
        semesternumber = gsn;
        semesterdesc = gsd;
    }
    
    public void setTimeStamp(String newValue){
        timestamp = newValue;
    }
    
    public String getTimeStamp(){
        return timestamp;
    }
    
    public void setSemesterNum(String newValue){
        semesternumber = newValue;
    }
    
    public String getSemesterNum(){
        return semesternumber;
    }
    
    public void setSemesterDesc(String newValue){
        semesterdesc = newValue;
    }
    
    public String getSemesterDesc(){
        return semesterdesc;
    }
}
