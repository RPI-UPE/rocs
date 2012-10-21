package edu.rpi.rocs.client.services.updatemanager;

import java.util.Set;
import java.util.TreeMap;

import com.google.gwt.user.client.rpc.RemoteService;

import edu.rpi.rocs.client.objectmodel.Course;
import edu.rpi.rocs.client.objectmodel.CrossListing;

public interface UpdateManagerService extends RemoteService {
	public Set<UpdateType> checkForUpdates(Integer semesterID, long last);
	public Set<Course> getAddedCourses(long last);
	public Set<Course> getUpdatedCourses(long last);
	public Set<Course> getRemovedCourses(long last);
	public Set<CrossListing> getAddedCrossListings(long last);
	public Set<CrossListing> getUpdatedCrossListings(long last);
	public Set<CrossListing> getRemovedCrossListings(long last);
	public TreeMap<Integer, Integer> getUpdatedSeats(long last);
	public TreeMap<Integer, Integer> getUpdatedStudents(long last);
}
