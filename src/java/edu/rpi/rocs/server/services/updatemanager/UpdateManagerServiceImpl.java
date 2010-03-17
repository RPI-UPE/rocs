package edu.rpi.rocs.server.services.updatemanager;

import java.util.Set;
import java.util.TreeMap;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import edu.rpi.rocs.client.objectmodel.Course;
import edu.rpi.rocs.client.objectmodel.CrossListing;
import edu.rpi.rocs.client.services.updatemanager.UpdateType;

// OBSOLETE: New model implemented in CourseDBServiceImpl getUpdateData()
public class UpdateManagerServiceImpl extends RemoteServiceServlet
	implements edu.rpi.rocs.client.services.updatemanager.UpdateManagerService {

	/**
	 *
	 */
	private static final long serialVersionUID = -1758535463021522411L;

	public Set<UpdateType> checkForUpdates(Integer semesterID, long last) {
		// TODO Auto-generated method stub
		return null;
	}

	public Set<Course> getAddedCourses(long last) {
		// TODO Auto-generated method stub
		return null;
	}

	public Set<CrossListing> getAddedCrossListings(long last) {
		// TODO Auto-generated method stub
		return null;
	}

	public Set<Course> getRemovedCourses(long last) {
		// TODO Auto-generated method stub
		return null;
	}

	public Set<CrossListing> getRemovedCrossListings(long last) {
		// TODO Auto-generated method stub
		return null;
	}

	public Set<Course> getUpdatedCourses(long last) {
		// TODO Auto-generated method stub
		return null;
	}

	public Set<CrossListing> getUpdatedCrossListings(long last) {
		// TODO Auto-generated method stub
		return null;
	}

	public TreeMap<Integer, Integer> getUpdatedSeats(long last) {
		// TODO Auto-generated method stub
		return null;
	}

	public TreeMap<Integer, Integer> getUpdatedStudents(long last) {
		// TODO Auto-generated method stub
		return null;
	}

}
