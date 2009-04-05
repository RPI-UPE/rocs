package edu.rpi.rocs.objectmodel;

public class Course extends MajorMinorRevisionObject {
	  
    //class variables
    private String name;
    private String dept;
    private String num;
    private String credmin;
    private String credmax;
    private String gradetype;
    private String closed;
    
    public Course(String gname, String gdept, String gnum, String gcredmin, String gcredmax, String ggradetype, String gclosed){
        name = gname;
        dept = gdept;
        num = gnum;
        credmin = gcredmin;
        credmax = gcredmax;
        gradetype = ggradetype;
        closed = gclosed;
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
    
    public void setNum(String newValue){
        num = newValue;
    }
    
    public String getNum(){
        return num;
    }
    
    public void setCredmin(String newValue){
        credmin = newValue;
    }
    
    public String getCredmin(){
        return credmin;
    }
    
    public void setCredmax(String newValue){
        credmax = newValue;
    }
    
    public String getCredmax(){
        return credmax;
    }
    
    public void setGradetype(String newValue){
        gradetype = newValue;
    }
    
    public String getGradetype(){
        return gradetype;
    }
    
    public void setClosed(String newValue){
        closed = newValue;
    }
    
    public String getClosed(){
        return closed;
    }
}
