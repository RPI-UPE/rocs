package edu.rpi.rocs.client.ui.semesterselect;

import java.util.List;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.rpi.rocs.client.objectmodel.SchedulerManager;
import edu.rpi.rocs.client.services.schedulemanager.ScheduleManagerService;

public class SaveScheduleDialogBox extends DialogBox {
	private VerticalPanel layout;
	private HorizontalPanel buttons;
	private ListBox fileList;
	private Anchor cancelButton;
	private Anchor okButton;
	private List<String> scheduleNames;
	private TextBox theName;
	
	private static SaveScheduleDialogBox theInstance=null;
	
	public static SaveScheduleDialogBox get() {
		if(theInstance==null) theInstance = new SaveScheduleDialogBox();
		return theInstance;
	}

	private AsyncCallback<Void> result = new AsyncCallback<Void>() {

		public void onFailure(Throwable arg0) {
			Window.alert("Service failure when attempting to save scheduler state to server. \nPlease check your internet connection or report this as a bug.");
			Log.debug(arg0.toString());
			arg0.printStackTrace();
		}

		public void onSuccess(Void arg0) {
			Window.alert("Your schedule was saved successfully.");
			SaveScheduleDialogBox.get().hide();
		}
		
	};

	public SaveScheduleDialogBox() {
		setText("Save Schedule");
		//setWidth("600px");
		//setHeight("600px");
		
		layout=new VerticalPanel();
		fileList=new ListBox(true);
		fileList.setWidth("400px");
		fileList.setHeight("300px");
		fileList.addChangeHandler(new ChangeHandler() {

			public void onChange(ChangeEvent arg0) {
				int i = fileList.getSelectedIndex();
				theName.setText(scheduleNames.get(i));
			}
			
		});
		
		cancelButton=new Anchor("Cancel");
		cancelButton.addStyleName("linkbutton");
		cancelButton.addStyleName("greybutton");
		cancelButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				SaveScheduleDialogBox.get().hide();
			}
			
		});
		
		this.addStyleName("rocs-dialog");
		this.addStyleName("rocs-style");
		
		okButton=new Anchor("Save");
		okButton.addStyleName("linkbutton");
		okButton.addStyleName("greenbutton");
		okButton.addClickHandler(new ClickHandler() {


			public void onClick(ClickEvent arg0) {
				ScheduleManagerService.Singleton.getInstance().saveSchedule(theName.getText(), SchedulerManager.getInstance(), result);
			}
			
		});
		
		buttons=new HorizontalPanel();
		buttons.add(cancelButton);
		buttons.add(okButton);
		
		theName = new TextBox();
		
		layout.add(fileList);
		layout.add(theName);
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
