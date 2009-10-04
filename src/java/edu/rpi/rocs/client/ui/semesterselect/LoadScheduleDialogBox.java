package edu.rpi.rocs.client.ui.semesterselect;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class LoadScheduleDialogBox extends DialogBox {
	private VerticalPanel layout;
	private HorizontalPanel buttons;
	private ListBox fileList;
	private Anchor cancelButton;
	private Anchor okButton;
	private List<String> scheduleNames;
	
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
