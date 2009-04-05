package edu.rpi.rocs.server.services.demo;

import java.util.List;

import edu.rpi.rocs.client.objectmodel.CourseDB;
import edu.rpi.rocs.client.objectmodel.SemesterDescription;

public class CourseDBService implements
		edu.rpi.rocs.client.services.coursedb.CourseDBService {

	public CourseDB getSemesterData(Integer semesterId) {
		return CourseDB.getInstance(semesterId);
	}

	public List<SemesterDescription> getSemesterList() {
		return CourseDB.getSemesterList();
	}
	
	public SemesterDescription getCurrentSemester() {
		CourseDB semester = CourseDB.getCurrentSemester();
		return new SemesterDescription(semester.getSemesterId(),
				semester.getSemesterDesc());
	}

}
