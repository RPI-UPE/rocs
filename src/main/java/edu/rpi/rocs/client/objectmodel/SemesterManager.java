package edu.rpi.rocs.client.objectmodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.rpc.AsyncCallback;

import edu.rpi.rocs.client.objectmodel.Course;
import edu.rpi.rocs.client.objectmodel.CrossListing;
import edu.rpi.rocs.client.objectmodel.Semester;
import edu.rpi.rocs.client.services.coursedb.CourseDBService;
import edu.rpi.rocs.client.services.updatemanager.UpdateItem;

/**
 * This class handles keeping semester data up to date on the
 * client side.
 * @author jon, ewpatton
 *
 */
public class SemesterManager {
	/**
	 * A set of callbacks which should be called when the Semester Manager completes an operation.
	 */
	Set<SemesterManagerCallback> callbacks = new HashSet<SemesterManagerCallback>();

	/**
	 * Adds a callback for when the Semester Manager loads a semester.
	 *
	 * @param callback
	 */
	public void addSemesterChangeListener(SemesterManagerCallback callback) {
		callbacks.add(callback);
	}

	/**
	 * Removes a callback from the callback set.
	 *
	 * @param callback
	 */
	public void removeSemesterChangeListener(SemesterManagerCallback callback) {
		callbacks.remove(callback);
	}

	/**
	 * An interface which describes a callback class for the Semester Manager.
	 *
	 * @author ewpatton
	 *
	 */
	public interface SemesterManagerCallback {
		public void semesterLoaded(Semester semester);
		public void semesterUpdated(Semester semester);
		public void didChangeCrosslisting(CrossListing cl);
		public void didChangeCourse(Course c);
		public void didChangeSection(Section s);
	}

	/**
	 * The current instance of the SemesterManager.
	 */
	private static SemesterManager instance = null;

	/**
	 * The current semester loaded from the server.
	 */
	private Semester currentSemester = null;

	/**
	 * Accesses the singleton instance of the SemesterManager.
	 *
	 * @return The current SemesterManager
	 */
	public static SemesterManager getInstance() {
		if (instance == null) {
			instance = new SemesterManager();
		}
		return instance;
	}

	private SemesterManager() {

	}

	/**
	 * Callback for when the client completes the loading of a semester from the server.
	 */
	
	private transient AsyncCallback<Semester> retrieveCallback = new AsyncCallback<Semester>(){

		public void onFailure(Throwable caught) {

		}

		/**
		 * Sets the local current semester to the object returned by the server and calls
		 * any callbacks registered for the change in semesters.
		 * @param result The semester which was loaded from the server.
		 */
		public void onSuccess(Semester result) {
			currentSemester = result;
			if(result != null) {
				for(SemesterManagerCallback caller : callbacks) {
					caller.semesterLoaded(currentSemester);
				}
			}
		}

	};
	/**
	 * Retrieves a course database given a particular semester ID.
	 *
	 * @param semesterId Identifier for the semester to retrieve
	 */
	public void retrieveCourseDB(Integer semesterId) {
		
		CourseDBService.Singleton.getInstance().getSemesterData(semesterId, retrieveCallback);
		
	}

	/**
	 * Gets the currently loaded semester.
	 *
	 * @return The loaded semester
	 */
	public Semester getCurrentSemester() {
		return currentSemester;
	}

	/**
	 *	Takes a list of Updates and incorporates them.
	 */
	public void manageUpdates(ArrayList<UpdateItem> updates)
	{
		Log.debug("Parsing updates...");
		ArrayList<Course> modifiedCRS = new ArrayList<Course>();
		ArrayList<CrossListing> modifiedCL = new ArrayList<CrossListing>();

		for (UpdateItem UI : updates)
		{
			if (UI.isCourse()) modifiedCRS.add((Course)UI.getObject());
			else modifiedCL.add((CrossListing)UI.getObject());
		}

		List<Course> getCourses = currentSemester.getCourses();
		for (Course C : modifiedCRS) for (Course C2 : getCourses) if (C.getId().equals(C2.getId()))
		{
			// TODO: notifications for major changes
			currentSemester.removeCourse(C2);
			if (C.getLastRevision().longValue() != Long.MAX_VALUE) currentSemester.addCourse(C);
			HashSet<SemesterManagerCallback> handlers = courseHandlers.get(C.getId());
			if(handlers != null) {
				handlers = new HashSet<SemesterManagerCallback>(handlers);
				for(SemesterManagerCallback h : handlers) {
					h.didChangeCourse(C);
				}
				for(Section s : C.getSections()) {
					handlers = sectionHandlers.get(s.getCrn());
					if(handlers != null) {
						handlers = new HashSet<SemesterManagerCallback>(handlers);
						for(SemesterManagerCallback h : handlers) {
							h.didChangeSection(s);
						}
					}
				}
			}
			break;
		}

		List<CrossListing> getCrossLists = currentSemester.getCrossListings();
		for (CrossListing CL : modifiedCL) for (CrossListing CL2 : getCrossLists) if (CL.getUID() == CL2.getUID())
		{
			// TODO: notifications for major changes
			currentSemester.removeCrosslisting(CL2.getUID());
			if (CL.getLastRevision().longValue() != Long.MAX_VALUE) currentSemester.addCrosslisting(CL);
			HashSet<SemesterManagerCallback> handlers = crosslistHandlers.get(CL.getUID());
			if(handlers != null) {
				handlers = new HashSet<SemesterManagerCallback>(handlers);
				for(SemesterManagerCallback h : handlers) {
					h.didChangeCrosslisting(CL);
				}
				for(Section s : CL.getSections()) {
					handlers = sectionHandlers.get(s.getCrn());
					if(handlers != null) {
						handlers = new HashSet<SemesterManagerCallback>(handlers);
						for(SemesterManagerCallback h : handlers) {
							h.didChangeSection(s);
						}
					}
				}
			}
			break;
		}

		for(SemesterManagerCallback caller : callbacks) {
			caller.semesterUpdated(currentSemester);
		}
	}
	
	private transient HashMap<Integer, HashSet<SemesterManagerCallback>> crosslistHandlers = new HashMap<Integer, HashSet<SemesterManagerCallback>>();
	private transient HashMap<String, HashSet<SemesterManagerCallback>> courseHandlers = new HashMap<String, HashSet<SemesterManagerCallback>>();
	private transient HashMap<Integer, HashSet<SemesterManagerCallback>> sectionHandlers = new HashMap<Integer, HashSet<SemesterManagerCallback>>();
	
	public void addCrossListingChangeHandler(CrossListing cl, SemesterManagerCallback h) {
		HashSet<SemesterManagerCallback> handlers = crosslistHandlers.get(cl.getUID());
		if(handlers==null) {
			handlers = new HashSet<SemesterManagerCallback>();
			crosslistHandlers.put(cl.getUID(), handlers);
		}
		handlers.add(h);
	}
	
	public void addCourseChangeHandler(Course c, SemesterManagerCallback h) {
		HashSet<SemesterManagerCallback> handlers = courseHandlers.get(c.getId());
		if(handlers==null) {
			handlers = new HashSet<SemesterManagerCallback>();
			courseHandlers.put(c.getId(), handlers);
		}
		handlers.add(h);
	}
	
	public void addSectionChangeHandler(Section s, SemesterManagerCallback h) {
		HashSet<SemesterManagerCallback> handlers = sectionHandlers.get(s.getCrn());
		if(handlers==null) {
			handlers = new HashSet<SemesterManagerCallback>();
			sectionHandlers.put(s.getCrn(), handlers);
		}
		handlers.add(h);
	}
	
	public void removeCrossListChangeHandler(CrossListing cl, SemesterManagerCallback h) {
		HashSet<SemesterManagerCallback> handlers = crosslistHandlers.get(cl.getUID());
		if(handlers==null) return;
		handlers.remove(h);
	}
	
	public void removeCourseChangeHandler(Course c, SemesterManagerCallback h) {
		HashSet<SemesterManagerCallback> handlers = courseHandlers.get(c.getId());
		if(handlers==null) return;
		handlers.remove(h);
	}
	
	public void removeSectionChangeHandler(Section s, SemesterManagerCallback h) {
		HashSet<SemesterManagerCallback> handlers = sectionHandlers.get(s.getCrn());
		if(handlers==null) return;
		handlers.remove(h);
	}
}
