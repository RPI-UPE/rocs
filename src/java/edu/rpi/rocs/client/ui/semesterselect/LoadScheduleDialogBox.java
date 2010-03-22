package edu.rpi.rocs.client.ui.semesterselect;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.rpi.rocs.client.objectmodel.SchedulerManager;
import edu.rpi.rocs.client.objectmodel.SemesterManager;
import edu.rpi.rocs.client.objectmodel.User;
import edu.rpi.rocs.client.services.schedulemanager.ScheduleManagerService;

public class LoadScheduleDialogBox extends DialogBox {
	private VerticalPanel layout;
	private HorizontalPanel buttons;
	private ListBox fileList;
	private Anchor cancelButton;
	private Anchor okButton;
	private List<String> scheduleNames;
	private AsyncCallback<SchedulerManager> result=new AsyncCallback<SchedulerManager>() {

		public void onFailure(Throwable arg0) {
			Window.alert("Unable to load schedule from the server. Reason: "+arg0.getMessage());
		}

		public void onSuccess(SchedulerManager arg0) {
			if(arg0!=null) {
				SchedulerManager.getInstance().restoreSchedule(arg0);
			}
			else {
				Window.alert("Server returned no schedule.");
			}
			LoadScheduleDialogBox.get().hide();
		}
		
	};
	
	private static LoadScheduleDialogBox theInstance=null;
	
	public static LoadScheduleDialogBox get() {
		if(theInstance==null) theInstance = new LoadScheduleDialogBox();
		return theInstance;
	}
	
	public LoadScheduleDialogBox() {
		setText("Load Schedule");
		//setWidth("600px");
		//setHeight("600px");
		
		layout=new VerticalPanel();
		fileList=new ListBox(true);
		fileList.setWidth("400px");
		fileList.setHeight("300px");
		
		cancelButton=new Anchor("Cancel");
		cancelButton.addStyleName("linkbutton");
		cancelButton.addStyleName("greybutton");
		cancelButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				LoadScheduleDialogBox.get().hide();
			}
			
		});
		
		this.addStyleName("rocs-dialog");
		this.addStyleName("rocs-style");
		
		okButton=new Anchor("Load");
		okButton.addStyleName("linkbutton");
		okButton.addStyleName("greenbutton");
		okButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent arg0) {
				int id = fileList.getSelectedIndex();
				if(-1==id) {
					Window.alert("Select a file to load.");
				}
				else {
					ScheduleManagerService.Singleton.getInstance().loadSchedule(User.getUserID(),
						fileList.getValue(id), SemesterManager.getInstance().getCurrentSemester().getSemesterId(), result);
				}
			}
			
		});
		
		buttons=new HorizontalPanel();
		buttons.add(cancelButton);
		buttons.add(okButton);
		
		layout.add(fileList);
		layout.add(buttons);
		this.add(layout);
	}
	
	public void setScheduleList(List<String> names) {
		scheduleNames = names;
		fileList.clear();
		for(String s : scheduleNames) {
			fileList.addItem(s);
		}
	}
}
