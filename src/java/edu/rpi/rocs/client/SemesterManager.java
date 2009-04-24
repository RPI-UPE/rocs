package edu.rpi.rocs.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

import edu.rpi.rocs.client.objectmodel.CourseDB;
import edu.rpi.rocs.client.services.coursedb.CourseDBService;


/**
 * This class handles keeping semester data up to date on the
 * client side.
 * @author jon
 *
 */
public class SemesterManager {
	
	private static SemesterManager instance = null;
	
	private CourseDB currentSemester = null;
	
	public static SemesterManager getInstance() {
		if (instance == null) {
			instance = new SemesterManager();
		}
		return instance;
	}
	
	private SemesterManager() {
	
	}
	
	private AsyncCallback<CourseDB> retrieveCallback = new AsyncCallback<CourseDB>(){

		public void onFailure(Throwable caught) {
			
		}

		public void onSuccess(CourseDB result) {
			currentSemester = result;
		}
		
	};
	
	public void retrieveCourseDB(Integer semesterId) {
		CourseDBService.Singleton.getInstance().getSemesterData(semesterId, retrieveCallback);
	}
	
	public CourseDB getCurrentSemester() {
		return currentSemester;
	}

}
