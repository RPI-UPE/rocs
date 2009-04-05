package edu.rpi.rocs.objectmodel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CourseDB {
//coursedb is an object and return an instance of it
    
    //class variables
    private String timestamp;
    private String semesternumber;
    private String semesterdesc;
    
    private static final Map<Integer, CourseDB> semesters =
    	new HashMap<Integer, CourseDB>();
    
    public static void addCourseDB(String xmlFile) {
    	//XML File should get parsed, key should
    	//be the semesterID from the XML file
    	semesters.put(new Integer(0), new CourseDB("","",""));
    }
    
    public static CourseDB getInstance(Integer semesterId) {
    	return semesters.get(semesterId);
    }
    
    public static Collection<CourseDB> getSemesterList() {
    	//Make a copy of the semester list since returning
    	//semesters.values() would provide a list that
    	//would allow outside classes to directly modify
    	//the backing map
    	Collection<CourseDB> semesterList = new ArrayList<CourseDB>();
    	for(CourseDB current : semesters.values()) {
    		semesterList.add(current);
    	}
    	return semesterList;
    }
    
    //accessor functions
    private CourseDB(String gts, String gsn, String gsd){
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
