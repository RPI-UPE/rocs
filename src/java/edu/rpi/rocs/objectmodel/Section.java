package edu.rpi.rocs.objectmodel;

public class Section extends MajorMinorRevisionObject {
    
    //class variables
    private int crn;
    private int number;
    private int students;
    private int seats;
    private boolean closed;
    
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
}
