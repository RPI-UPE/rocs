package edu.rpi.rocs.client.services.coursedb;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

import edu.rpi.rocs.client.objectmodel.CourseDB;
import edu.rpi.rocs.client.objectmodel.SemesterDescription;

public interface CourseDBService extends RemoteService{
	public List<SemesterDescription> getSemesterList();
	public CourseDB getSemesterData(Integer semesterId);
	public SemesterDescription getCurrentSemester();
	
	public static class Singleton {
		private static CourseDBServiceAsync instance = null;
		public static CourseDBServiceAsync getInstance() {
			if (instance == null) {
				instance = (CourseDBServiceAsync)GWT.create(CourseDBService.class);
				((ServiceDefTarget)instance).setServiceEntryPoint(GWT.getModuleBaseURL() + "DemoService");
			}
			return instance;
		}
	}
}
