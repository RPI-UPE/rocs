package edu.rpi.rocs.server.objectmodel;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.rpi.rocs.client.objectmodel.Course;
import edu.rpi.rocs.client.objectmodel.Section;
import edu.rpi.rocs.exceptions.InvalidCourseDatabaseException;

public class CourseParser {

    /**
	 * 
	 */
	private static final long serialVersionUID = 7964624938393972286L;


	public static Course parse(Node src) throws InvalidCourseDatabaseException {
    	Course course = new Course();
    	for(int i=0;i<src.getAttributes().getLength();i++) {
    		Node n = src.getAttributes().item(i);
    		String name = n.getNodeName();
    		String val = n.getNodeValue();
    		if(name.equals("name")) {
    			course.setName(val);
    		}
    		else if(name.equals("dept")) {
    			course.setDept(val);
    		}
    		else if(name.equals("num")) {
    			course.setNum(Integer.parseInt(val));
    		}
    		else if(name.equals("credmin")) {
    			course.setCredmin(Integer.parseInt(val));
    		}
    		else if(name.equals("credmax")) {
    			course.setCredmax(Integer.parseInt(val));
    		}
    		else if(name.equals("gradetype")) {
    			course.setGradetype(val);
    		}
    		else {
    			throw new InvalidCourseDatabaseException("Course has an invalid attribute \"" + name + "\".");
    		}
    	}
    	NodeList children = src.getChildNodes();
    	for(int i=0;i<children.getLength();i++) {
    		Node n = children.item(i);
    		if(n.getNodeName().equalsIgnoreCase("Section")) {
    			Section s = SectionParser.parse(n);
    			if(s.getPeriods().size()>0) {
	    			s.setParent(course);
	    			course.addSection(s);
    			}
    		}
    		else if(n.getNodeName().equalsIgnoreCase("Note")) {
    			course.addNote(n.getFirstChild().getNodeValue());
    		}
    		else if(n.getNodeName()=="#text") {
    		}
    		else {
    			throw new InvalidCourseDatabaseException("Course has an invalid child <" + n.getNodeName() + ">.");
    		}
    	}
    	course.updateMajorRevision();
    	return course;
    }
    
 
}
