package edu.rpi.rocs.client.services.schedulemanager;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import edu.rpi.rocs.client.objectmodel.SchedulerManager;

public interface ScheduleManagerServiceAsync {
	public void getScheduleList(String user, AsyncCallback<List<String>> callback);
	public void saveSchedule(String name, SchedulerManager schedule, AsyncCallback<Void> callback);
	public void loadSchedule(String user, String name, int semesterid, AsyncCallback<SchedulerManager> callback);
}
