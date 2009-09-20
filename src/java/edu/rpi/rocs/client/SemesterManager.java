package edu.rpi.rocs.client;

import java.util.HashSet;
import java.util.Set;

import com.google.gwt.user.client.rpc.AsyncCallback;

import edu.rpi.rocs.client.objectmodel.Semester;
import edu.rpi.rocs.client.services.coursedb.CourseDBService;


/**
 * This class handles keeping semester data up to date on the
 * client side.
 * @author jon, ewpatton
 *
 */
public class SemesterManager {
	Set<SemesterManagerCallback> callbacks = new HashSet<SemesterManagerCallback>();
	
	public void addSemesterChangeListener(SemesterManagerCallback callback) {
		callbacks.add(callback);
	}
	
	public void removeSemesterChangeListener(SemesterManagerCallback callback) {
		callbacks.remove(callback);
	}
	
	public interface SemesterManagerCallback {
		public void semesterLoaded(Semester semester);
	}
	
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
			//Log.trace("Retrieved semester "+result.getSemesterDesc());
			for(SemesterManagerCallback caller : callbacks) {
				//Log.trace("Caling SemesterManagerCallback");
				caller.semesterLoaded(currentSemester);
			}
		}
		
	};
	
	public void retrieveCourseDB(Integer semesterId) {
		CourseDBService.Singleton.getInstance().getSemesterData(semesterId, retrieveCallback);
	}
	
	public Semester getCurrentSemester() {
		return currentSemester;
	}

}
