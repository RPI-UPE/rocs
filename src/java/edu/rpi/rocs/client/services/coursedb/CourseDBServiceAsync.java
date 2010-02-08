package edu.rpi.rocs.client.services.coursedb;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import edu.rpi.rocs.client.objectmodel.Semester;
import edu.rpi.rocs.client.objectmodel.SemesterDescription;
import edu.rpi.rocs.client.services.updatemanager.UpdateItem;

public interface CourseDBServiceAsync {
	public void getUpdateList(Integer semesterID, Long timeStamp, AsyncCallback<ArrayList<UpdateItem>> callback);
	public void getSemesterList( AsyncCallback<List<SemesterDescription>> callback);
	public void getSemesterData(Integer semesterId, AsyncCallback<Semester> callback);
	public void getCurrentSemester(AsyncCallback<SemesterDescription> callback);
	public void getUserName(String userid, AsyncCallback<String> callback);
}
