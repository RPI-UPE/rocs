package edu.rpi.rocs.objectmodel;

import java.util.ArrayList;

public class Course extends MajorMinorRevisionObject {
	  
    //class variables
    private String name;
    private String dept;
    private int num;
    private int credmin;
    private int credmax;
    private String gradetype;
    private ArrayList<String> notes;
    private ArrayList<Section> sections;
    
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
    
    //accesssor functions
    public void setName(String newValue){
        name = newValue;
    }
    
    public String getName(){
        return name;
    }
    
    public void setDept(String newValue){
        dept = newValue;
    }
    
    public String getDept(){
        return dept;
    }
    
    public void setNum(int newValue){
        num = newValue;
    }
    
    public int getNum(){
        return num;
    }
    
    public void setCredmin(int newValue){
        credmin = newValue;
    }
    
    public int getCredmin(){
        return credmin;
    }
    
    public void setCredmax(int newValue){
        credmax = newValue;
    }
    
    public int getCredmax(){
        return credmax;
    }
    
    public void setGradetype(String newValue){
        gradetype = newValue;
    }
    
    public String getGradetype(){
        return gradetype;
    }
    
    public void addNote(String newValue) {
    	notes.add(newValue);
    }
    
    public ArrayList<String> getNotes() {
    	return notes;
    }
    
    public void removeNote(String note) {
    	notes.remove(note);
    }
    
    public void addSection(Section s) {
    	sections.add(s);
    }
    
    public ArrayList<Section> getSections() {
    	return sections;
    }
    
    public void removeSection(Section s) {
    	sections.remove(s);
    }
}
