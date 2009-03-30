package edu.rpi.rocs.objectmodel;

public class Section {
    
    //class variables
    private int crn;
    private String num;
    private String students;
    private String seats;
    
    //accessor functions
    public void setCRN(int newValue){
        crn = newValue;
    }
    
    public int getCRN(){
        return crn;
    }
    
    public void setNum(String newValue){
        num = newValue;
    }
    
    public String getNum(){
        return num;
    }
    
    public void setStudents(String newValue){
        students = newValue;
    }
    
    public String getStudents(){
        return students;
    }
    
    public void setSeats(String newValue){
        seats = newValue;
    }
    
    public String getSeats(){
        return seats;
    }
}
