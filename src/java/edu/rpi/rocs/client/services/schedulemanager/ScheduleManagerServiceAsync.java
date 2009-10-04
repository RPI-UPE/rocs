package edu.rpi.rocs.client.services.schedulemanager;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import edu.rpi.rocs.client.objectmodel.Schedule;

public interface ScheduleManagerServiceAsync {
	public void getScheduleList(AsyncCallback<List<String>> callback);
	public void saveSchedule(String name, Schedule schedule, AsyncCallback<Void> callback);
}
