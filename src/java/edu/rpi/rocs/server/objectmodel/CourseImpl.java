package edu.rpi.rocs.server.objectmodel;

import java.util.ArrayList;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.rpi.rocs.client.objectmodel.Course;
import edu.rpi.rocs.client.objectmodel.Section;
import edu.rpi.rocs.exceptions.InvalidCourseDatabaseException;

public class CourseImpl extends Course {

    /**
	 * 
	 */
	private static final long serialVersionUID = 7964624938393972286L;

	public CourseImpl(String name, String dept, int number, int credMin,
			int credMax, String gradeType, ArrayList<String> someNotes) {
		super(name, dept, number, credMin, credMax, gradeType, someNotes);
		// TODO Auto-generated constructor stub
	}

	public CourseImpl(Node src) throws InvalidCourseDatabaseException {
    	super();
    	for(int i=0;i<src.getAttributes().getLength();i++) {
    		Node n = src.getAttributes().item(i);
    		String name = n.getNodeName();
    		String val = n.getNodeValue();
    		if(name == "name") {
    			this.name = val;
    		}
    		else if(name == "dept") {
    			dept = val;
    		}
    		else if(name == "num") {
    			num = Integer.parseInt(val);
    		}
    		else if(name == "credmin") {
    			credmin = Integer.parseInt(val);
    		}
    		else if(name == "credmax") {
    			credmax = Integer.parseInt(val);
    		}
    		else if(name == "gradetype") {
    			gradetype = val;
    		}
    		else {
    			throw new InvalidCourseDatabaseException("Course has an invalid attribute.");
    		}
    	}
    	NodeList children = src.getChildNodes();
    	for(int i=0;i<children.getLength();i++) {
    		Node n = children.item(i);
    		if(n.getNodeName()=="Section") {
    			SectionImpl s = new SectionImpl(n);
    			sections.add(s);
    		}
    		else if(n.getNodeName()=="#text") {
    		}
    		else {
    			throw new InvalidCourseDatabaseException("Course has an invalid child.");
    		}
    	}
    	updateMajorRevision();
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
