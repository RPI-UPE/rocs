package edu.rpi.rocs.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

import edu.rpi.rocs.client.objectmodel.Semester;
import edu.rpi.rocs.client.services.coursedb.CourseDBService;


/**
 * This class handles keeping semester data up to date on the
 * client side.
 * @author jon
 *
 */
public class SemesterManager {
	
	private static SemesterManager instance = null;
	
	private Semester currentSemester = null;
	
	public static SemesterManager getInstance() {
		if (instance == null) {
			instance = new SemesterManager();
		}
		return instance;
	}
	
	private SemesterManager() {
	
	}
	
	private AsyncCallback<Semester> retrieveCallback = new AsyncCallback<Semester>(){

		public void onFailure(Throwable caught) {
			
		}

		public void onSuccess(Semester result) {
			currentSemester = result;
		}
		
	};
	
	public void retrieveCourseDB(Integer semesterId) {
		CourseDBService.Singleton.getInstance().getSemesterData(semesterId, retrieveCallback);
	}
	
	public Semester getCurrentSemester() {
		return currentSemester;
	}

}
