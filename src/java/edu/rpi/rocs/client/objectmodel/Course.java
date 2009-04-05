package edu.rpi.rocs.client.objectmodel;

import java.util.ArrayList;

public class Course extends MajorMinorRevisionObject {
	  
    //class variables
    protected String name;
    protected String dept;
    protected int num;
    protected int credmin;
    protected int credmax;
    protected String gradetype;
    protected ArrayList<String> notes;
    protected ArrayList<Section> sections;
    
    public Course() {
    	
    }
    
    public Course(String aName, String aDept, int aNumber, int aCredMin, int aCredMax, String aGradeType, ArrayList<String> someNotes){
        name = aName;
        dept = aDept;
        num = aNumber;
        credmin = aCredMin;
        credmax = aCredMax;
        gradetype = aGradeType;
        notes = someNotes;
        if(someNotes == null)
        	notes = new ArrayList<String>();
    }
    
    public String getName(){
        return name;
    }
    
    public String getDept(){
        return dept;
    }
    
    public int getNum(){
        return num;
    }
    
    public int getCredmin(){
        return credmin;
    }
    
    public int getCredmax(){
        return credmax;
    }
    
    public String getGradetype(){
        return gradetype;
    }
    
    public ArrayList<String> getNotes() {
    	return notes;
    }
    
    public ArrayList<Section> getSections() {
    	return sections;
    }
}
