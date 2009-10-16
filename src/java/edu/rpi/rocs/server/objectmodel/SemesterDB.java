package edu.rpi.rocs.server.objectmodel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.rpi.rocs.client.objectmodel.Semester;
import edu.rpi.rocs.client.objectmodel.SemesterDescription;

public class SemesterDB {
	/** The latest course database by semester */
	static private Semester latest=null;
    
	/** All loaded semesters */
    private static final Map<Integer, Semester> semesters =
    	new HashMap<Integer, Semester>();
    
    public static Semester getInstance(Integer semesterId) {
    	return semesters.get(semesterId);
    }
    
    protected static void putInstance(Integer semesterId, Semester semester) {
    	if ((latest == null) || (semester.getSemesterId() > latest.getSemesterId()))
    		latest = semester;
    	semesters.put(semesterId, semester);
    }
    
    public static List<SemesterDescription> getSemesterList() {
    	List<SemesterDescription> semesterList = new ArrayList<SemesterDescription>();
    	for(Semester current : semesters.values()) {
    		semesterList.add(
    				new SemesterDescription(current.getSemesterId(),
    						current.getSemesterDesc()));
    	}
    	Collections.sort(semesterList);
    	return semesterList;
    }
    
    public static Semester getCurrentSemester() {
    	Integer max = -1;
    	for( Integer curr : semesters.keySet()) {
    		if (curr > max)
    			max = curr;
    	}
    	if (max != -1)
    		return semesters.get(max);
    	else
    		return null;
    }

}
