package edu.rpi.rocs.client.ui.semesterselect;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;

import edu.rpi.rocs.client.SemesterManager;
import edu.rpi.rocs.client.objectmodel.SemesterDescription;
import edu.rpi.rocs.client.services.coursedb.CourseDBService;


public class SemesterSelectionPanel extends HorizontalPanel {
	private Label title = new Label("Semester:   ");
	private Label currentSemester;
	private Button changeSemesterButton = new Button("Change");
	private ListBox semesterList;
	
	private SemesterDescription selectedSemester = null;
	
	private static SemesterSelectionPanel instance = null;
	public static SemesterSelectionPanel getInstance() {
		if (instance == null) {
			instance = new SemesterSelectionPanel();
		}
		return instance;
	}
	
	private SemesterSelectionPanel() {
		add(title);
		currentSemester = new Label("Loading...");
		add(currentSemester);
		add(changeSemesterButton);
		
		CourseDBService.Singleton.getInstance().getCurrentSemester(currentSemesterCallback);
	}
	
	private AsyncCallback<SemesterDescription> currentSemesterCallback =
		new AsyncCallback<SemesterDescription>() {

			public void onFailure(Throwable caught) {
				currentSemester.setText("Error: " + caught.getMessage());
			}

			public void onSuccess(SemesterDescription result) {
				if (currentSemester == null) {
					currentSemester.setText("No semester data...");
				} else {
					selectSemester(result);
				}
			}
		
	};
	
	private void selectSemester(SemesterDescription semester) {
		currentSemester.setText(semester.getDescription());
		selectedSemester = semester;
		SemesterManager.getInstance().retrieveCourseDB(semester.getSemesterId());
	}
	
	public SemesterDescription getSelectedSemester() {
		return selectedSemester;
	}
}
