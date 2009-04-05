package edu.rpi.rocs.server.services.coursedb;

import java.util.List;

import edu.rpi.rocs.client.objectmodel.CourseDB;
import edu.rpi.rocs.client.objectmodel.SemesterDescription;
import edu.rpi.rocs.server.objectmodel.CourseDBImpl;

public class CourseDBServiceImpl implements
		edu.rpi.rocs.client.services.coursedb.CourseDBService {

	public CourseDB getSemesterData(Integer semesterId) {
		return CourseDBImpl.getInstance(semesterId);
	}

	public List<SemesterDescription> getSemesterList() {
		return CourseDBImpl.getSemesterList();
	}
	
	public SemesterDescription getCurrentSemester() {
		CourseDB semester = CourseDBImpl.getCurrentSemester();
		return new SemesterDescription(semester.getSemesterId(),
				semester.getSemesterDesc());
	}

}
