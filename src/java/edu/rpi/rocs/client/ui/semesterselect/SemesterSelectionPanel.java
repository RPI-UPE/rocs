package edu.rpi.rocs.client.ui.semesterselect;

import java.util.List;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.InlineHyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.rpi.rocs.client.SemesterManager;
import edu.rpi.rocs.client.objectmodel.SemesterDescription;
import edu.rpi.rocs.client.services.coursedb.CourseDBService;


public class SemesterSelectionPanel extends VerticalPanel {
	private FlexTable layout = new FlexTable();
	private Label title = new Label("Semester: Loading...");
	private ListBox semesterList = new ListBox();
	private List<SemesterDescription> allSemesters = null;
	private Label file = new Label("File: Untitled");
	private FlexTable buttons = new FlexTable();
	private InlineHyperlink newFile = new InlineHyperlink("New","rocs-new-file");
	private InlineHyperlink loadFile = new InlineHyperlink("Load", "rocs-load-file");
	private InlineHyperlink saveFile = new InlineHyperlink("Save", "rocs-save-file");
	
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
		saveFile.addStyleName("greybutton");
		saveFile.addStyleName("linkbutton");
		
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
				
				CourseDBService.Singleton.getInstance().getCurrentSemester(currentSemesterCallback);
			}
	};
	
	private AsyncCallback<SemesterDescription> currentSemesterCallback =
		new AsyncCallback<SemesterDescription>() {

			public void onFailure(Throwable caught) {
				title.setText("Semester: unavailable");
			}

			public void onSuccess(SemesterDescription result) {
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
	
	private ChangeHandler handleSemesterChange =
		new ChangeHandler() {

			public void onChange(ChangeEvent event) {
				// TODO Auto-generated method stub
				int i = semesterList.getSelectedIndex();
				Integer id = Integer.valueOf(semesterList.getValue(i));
				for(SemesterDescription desc : allSemesters) {
					if(desc.getSemesterId() == id) {
						selectedSemester = desc;
						selectedSemesterDidChange();
						break;
					}
				}
			}
	};
	
	public void selectedSemesterDidChange() {
		SemesterManager.getInstance().retrieveCourseDB(selectedSemester.getSemesterId());
	}
	
	public SemesterDescription getSelectedSemester() {
		return selectedSemester;
	}
}
