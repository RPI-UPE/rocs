package edu.rpi.rocs.client.ui.semesterselect;

import java.util.ArrayList;
import java.util.List;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.rpi.rocs.client.objectmodel.SchedulerManager;
import edu.rpi.rocs.client.objectmodel.SemesterDescription;
import edu.rpi.rocs.client.objectmodel.SemesterManager;
import edu.rpi.rocs.client.objectmodel.SchedulerManager.RestorationEventHandler;
import edu.rpi.rocs.client.services.coursedb.CourseDBService;
import edu.rpi.rocs.client.services.updatemanager.UpdateItem;


public class SemesterSelectionPanel extends VerticalPanel implements RestorationEventHandler {
	private FlexTable layout = new FlexTable();
	private Label title = new Label("Semester: Loading...");
	private ListBox semesterList = new ListBox();
	private List<SemesterDescription> allSemesters = null;
	private Label file = new Label("File: Untitled");
	private FlexTable buttons = new FlexTable();
	private Anchor newFile = new Anchor("New");
	private Anchor loadFile = new Anchor("Load");
	private Anchor saveFile = new Anchor("Save");

	private SemesterDescription selectedSemester = null;

	private static SemesterSelectionPanel instance = null;
	public static SemesterSelectionPanel getInstance() {
		if (instance == null) {
			instance = new SemesterSelectionPanel();
		}
		return instance;
	}

	private SemesterSelectionPanel() {
		layout.setWidget(0, 0, title);
		semesterList.addChangeHandler(handleSemesterChange);

		add(layout);
		add(file);

		newFile.addStyleName("greybutton");
		newFile.addStyleName("linkbutton");
		loadFile.addStyleName("greybutton");
		loadFile.addStyleName("linkbutton");
		loadFile.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				SchedulerManager.getInstance().getScheduleList(getScheduleListCallbackLoad);
			}

		});

		saveFile.addStyleName("greybutton");
		saveFile.addStyleName("linkbutton");
		saveFile.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if(SchedulerManager.getInstance().getCurrentSchedule()==null) {
					Window.alert("You must generate a schedule before you can save your session");
				}
				else SchedulerManager.getInstance().getScheduleList(getScheduleListCallbackSave);
			}
		});

		buttons.setWidget(0, 0, newFile);
		buttons.setWidget(0, 1, loadFile);
		buttons.setWidget(0, 2, saveFile);

		add(buttons);
		
		CourseDBService.Singleton.getInstance().getSemesterList(semesterListCallback);
	}

	private AsyncCallback<List<SemesterDescription>> semesterListCallback =
		new AsyncCallback<List<SemesterDescription>>() {

			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				title.setText("Semester: unavailable");
			}

			public void onSuccess(List<SemesterDescription> result) {
				// TODO Auto-generated method stub
				title.setText("Semester: Loading...");
				allSemesters = result;
				semesterList.clear();
				for(SemesterDescription desc : result) {
					semesterList.addItem(desc.getDescription(), desc.getSemesterId().toString());
				}
				Log.debug("Requesting the current semester description...");
				CourseDBService.Singleton.getInstance().getCurrentSemester(currentSemesterCallback);
			}
	};

	private AsyncCallback<SemesterDescription> currentSemesterCallback =
		new AsyncCallback<SemesterDescription>() {

			public void onFailure(Throwable caught) {
				Log.debug("Request failed.");
				title.setText("Semester: unavailable");
			}

			public void onSuccess(SemesterDescription result) {
				Log.debug("Semester description retrieved.");
				title.setText("Semester: ");
				layout.setWidget(0, 1, semesterList);
				selectedSemester = result;
				if(result == null) {
					semesterList.setSelectedIndex(0);
					selectedSemester = allSemesters.get(0);
				}
				else {
					int i=0;
					for(SemesterDescription desc : allSemesters) {
						if(desc.getSemesterId() == result.getSemesterId()) {
							semesterList.setSelectedIndex(i);
							break;
						}
						i++;
					}
				}
				selectedSemesterDidChange();
			}

	};

	private AsyncCallback<List<String>> getScheduleListCallbackLoad =
		new AsyncCallback<List<String>>() {

			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				Log.error("Failed to retrieve schedule list.", caught);
			}

			public void onSuccess(List<String> result) {
				// TODO Auto-generated method stub
				LoadScheduleDialogBox.get().setScheduleList(result);
				LoadScheduleDialogBox.get().center();
			}

	};
	
	private AsyncCallback<List<String>> getScheduleListCallbackSave =
		new AsyncCallback<List<String>>() {

			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				Log.error("Failed to retrieve schedule list.", caught);
			}

			public void onSuccess(List<String> result) {
				// TODO Auto-generated method stub
				SaveScheduleDialogBox.get().setScheduleList(result);
				SaveScheduleDialogBox.get().center();
			}

	};

	private ChangeHandler handleSemesterChange =
		new ChangeHandler() {

			public void onChange(ChangeEvent event) {
				// TODO Auto-generated method stub
				int i = semesterList.getSelectedIndex();
				Integer id = Integer.valueOf(semesterList.getValue(i));
				for(SemesterDescription desc : allSemesters) {
					if(desc.getSemesterId().intValue() == id.intValue()) {
						selectedSemester = desc;
						selectedSemesterDidChange();
						break;
					}
				}
			}
	};

	public void selectedSemesterDidChange() {
		Log.debug("Semester changed. Retrieving contents of new semester...");
		SemesterManager.getInstance().retrieveCourseDB(selectedSemester.getSemesterId());
		Log.debug("Sleeping...");
		javascriptThread(selectedSemester);
	}

	private void javaThread(SemesterDescription SD)
	{
		Log.debug("Waking...");
		if (selectedSemester.getSemesterId() == SD.getSemesterId())
		{
			Log.debug("Updating semester from server...");
			CourseDBService.Singleton.getInstance().getUpdateList(SD.getSemesterId(),
																 SemesterManager.getInstance().getCurrentSemester().getTimeStamp(),
																 getUpdatedItems);
			Log.debug("Sleeping...");
			javascriptThread(SD);
		}
	}
	private native void javascriptThread(SemesterDescription SD)/*-{
		$wnd.globalSemesterDesc = SD;
		$wnd.rocs_ssp = this;
		updateFunc = function() {
			$wnd.rocs_ssp.@edu.rpi.rocs.client.ui.semesterselect.SemesterSelectionPanel::javaThread(Ledu/rpi/rocs/client/objectmodel/SemesterDescription;)($wnd.globalSemesterDesc);
		}
		$wnd.setTimeout(updateFunc, 305000);
	}-*/;

	private AsyncCallback<ArrayList<UpdateItem>> getUpdatedItems =
		new AsyncCallback<ArrayList<UpdateItem>>()
		{
			public void onFailure(Throwable caught) {
				Log.error("Failed to get a response from the Update server.", caught);
			}
			public void onSuccess(ArrayList<UpdateItem> result) {
				SemesterManager.getInstance().manageUpdates(result);
			}
		};


	public SemesterDescription getSelectedSemester() {
		return selectedSemester;
	}

	public void restore() {
		
	}
}
