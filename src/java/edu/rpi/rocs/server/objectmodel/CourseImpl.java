package edu.rpi.rocs.server.objectmodel;

import java.util.ArrayList;

import org.w3c.dom.Node;

import edu.rpi.rocs.client.objectmodel.Course;
import edu.rpi.rocs.client.objectmodel.Section;

public class CourseImpl extends Course {

    public CourseImpl(String name, String dept, int number, int credMin,
			int credMax, String gradeType, ArrayList<String> someNotes) {
		super(name, dept, number, credMin, credMax, gradeType, someNotes);
		// TODO Auto-generated constructor stub
	}

	public CourseImpl(Node src) {
    	super();
    }
    
    //accesssor functions
    public void setName(String newValue){
        name = newValue;
    }
    
    public void setDept(String newValue){
        dept = newValue;
    }
    
    public void setNum(int newValue){
        num = newValue;
    }
    
    public void setCredmin(int newValue){
        credmin = newValue;
    }
    
    public void setCredmax(int newValue){
        credmax = newValue;
    }
    
    public void setGradetype(String newValue){
        gradetype = newValue;
    }
    
    public void addNote(String newValue) {
    	notes.add(newValue);
    }
    
    public void removeNote(String note) {
    	notes.remove(note);
    }
    
    public void addSection(Section s) {
    	sections.add(s);
    }
    
    public void removeSection(Section s) {
    	sections.remove(s);
    }
}
