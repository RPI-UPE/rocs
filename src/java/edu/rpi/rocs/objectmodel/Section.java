package edu.rpi.rocs.objectmodel;

import java.util.ArrayList;

public class Section extends MajorMinorRevisionObject {
    
    //class variables
    private int crn;
    private int number;
    private int students;
    private int seats;
    private boolean closed;
    private ArrayList<Period> periods;
    
    public Section() {
    	periods = new ArrayList<Period>();
    }
    
    //accessor functions
    public void setCRN(int newValue){
        crn = newValue;
    }
    
    public int getCRN(){
        return crn;
    }
    
    public void setNum(int newValue){
        number = newValue;
    }
    
    public int getNum(){
        return number;
    }
    
    public void setStudents(int newValue){
        students = newValue;
    }
    
    public int getStudents(){
        return students;
    }
    
    public void setSeats(int newValue){
        seats = newValue;
    }
    
    public int getSeats(){
        return seats;
    }
    
    public void setClosed(boolean newValue){
        closed = newValue;
    }
    
    public boolean getClosed(){
        return closed;
    }
    
    public void addPeriod(Period p) {
    	periods.add(p);
    }
    
    public ArrayList<Period> getPeriods() {
    	return periods;
    }
    
    public void removePeriod(Period p) {
    	periods.remove(p);
    }
}
