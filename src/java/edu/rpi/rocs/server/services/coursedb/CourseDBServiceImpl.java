package edu.rpi.rocs.server.services.coursedb;

import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import edu.rpi.rocs.client.objectmodel.CourseDB;
import edu.rpi.rocs.client.objectmodel.SemesterDescription;
import edu.rpi.rocs.server.objectmodel.CourseDBImpl;

public class CourseDBServiceImpl extends RemoteServiceServlet implements
		edu.rpi.rocs.client.services.coursedb.CourseDBService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1198710711253036931L;

	public CourseDB getSemesterData(Integer semesterId) {
		return (CourseDB)CourseDBImpl.getInstance(semesterId);
	}

	public List<SemesterDescription> getSemesterList() {
		return CourseDBImpl.getSemesterList();
	}
	
	public SemesterDescription getCurrentSemester() {
		CourseDB semester = CourseDBImpl.getCurrentSemester();
		if(semester == null) {
			return new SemesterDescription(-1,"[Warning] No Course Database Loaded");
		}
		return new SemesterDescription(semester.getSemesterId(),
				semester.getSemesterDesc());
	}

}
