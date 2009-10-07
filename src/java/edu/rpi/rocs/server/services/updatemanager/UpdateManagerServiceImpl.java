package edu.rpi.rocs.server.services.updatemanager;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import edu.rpi.rocs.client.objectmodel.Course;
import edu.rpi.rocs.client.objectmodel.CrossListing;
import edu.rpi.rocs.client.objectmodel.Semester;
import edu.rpi.rocs.client.services.updatemanager.UpdateType;
import edu.rpi.rocs.server.objectmodel.SemesterDB;

public class UpdateManagerServiceImpl extends RemoteServiceServlet
	implements edu.rpi.rocs.client.services.updatemanager.UpdateManagerService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1758535463021522411L;

	private static HashMap<Thread, Set<UpdateType>> connections=new HashMap<Thread, Set<UpdateType>>();
	
	public static void updatedSemester(Set<UpdateType> updateSet) {
		Set<Thread> threads = connections.keySet();
		for(Thread t : threads) {
			Set<UpdateType> state = connections.get(t);
			state.addAll(updateSet);
			t.notify();
		}
	}
	
	public Set<UpdateType> checkForUpdates(Integer semesterID, long last) {
		// TODO Auto-generated method stub
		Semester s = SemesterDB.getInstance(semesterID);
		HashSet<UpdateType> retVal = new HashSet<UpdateType>();
		if(s.getTimeStamp()>last) {
			retVal.add(UpdateType.UpdateAll);
			return retVal;
		}
		synchronized (Thread.currentThread()) {
			connections.put(Thread.currentThread(), retVal);
			try {
				Thread.currentThread().wait();
			} catch(InterruptedException e) {
			
			}
		}
		Set<UpdateType> updates = connections.get(Thread.currentThread());
		connections.remove(Thread.currentThread());
		return updates;
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
