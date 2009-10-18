package edu.rpi.rocs.client.ui.scheduler;

import java.util.ArrayList;
import java.util.HashSet;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.rpi.rocs.client.objectmodel.ScheduleFilterManager;

public class SchedulerFilterDialogBox extends DialogBox {
	public interface SchedulerFilterDialogBoxCompleted {
		public void completedWithFilter(String qName);
		public void cancelled();
	}
	
	private HashSet<SchedulerFilterDialogBoxCompleted> completeCallbacks=new HashSet<SchedulerFilterDialogBoxCompleted>();
	private VerticalPanel layout = new VerticalPanel();
	private ListBox filters = new ListBox(true);
	private Anchor cancelButton = new Anchor("Cancel");
	private Anchor acceptButton = new Anchor("Select");
	private HorizontalPanel buttonLayout = new HorizontalPanel();
	private ArrayList<String> filterNames = ScheduleFilterManager.get().getRegisteredFilterNames();
	
	public SchedulerFilterDialogBox() {
		this.setText("ROCS Filter List");
		this.addStyleName("rocs-dialog");
		this.addStyleName("rocs-style");
		
		cancelButton.addClickHandler(cancelHandler);
		cancelButton.addStyleName("linkbutton");
		cancelButton.addStyleName("greybutton");
		
		acceptButton.addClickHandler(acceptHandler);
		acceptButton.addStyleName("linkbutton");
		acceptButton.addStyleName("greenbutton");
		
		buttonLayout.add(cancelButton);
		buttonLayout.add(acceptButton);

		filters.setWidth("300px");
		filters.setHeight("300px");
		for(String name : filterNames) {
			filters.addItem(name);
		}
		filters.setSelectedIndex(0);

		layout.add(filters);
		layout.add(buttonLayout);
		this.add(layout);
	}
	
	public void addCompletionHandler(SchedulerFilterDialogBoxCompleted e) {
		completeCallbacks.add(e);
	}
	
	public void removeCompletionHandler(SchedulerFilterDialogBoxCompleted e) {
		completeCallbacks.remove(e);
	}
	
	private abstract class ButtonClickHandler implements ClickHandler {
		SchedulerFilterDialogBox owner;
		ButtonClickHandler(SchedulerFilterDialogBox db) {
			owner = db;
		}
	}
	
	public void hideWithResult(boolean accepted) {
		super.hide();
		if(accepted) {
			for(SchedulerFilterDialogBoxCompleted callback : completeCallbacks) {
				String filterName = filterNames.get(filters.getSelectedIndex());
				String qName = ScheduleFilterManager.get().getFilterByName(filterName);
				callback.completedWithFilter(qName);
			}
		}
		else {
			for(SchedulerFilterDialogBoxCompleted callback : completeCallbacks) {
				callback.cancelled();
			}
		}
	}
	
	private ButtonClickHandler acceptHandler = new ButtonClickHandler(this) {
		public void onClick(ClickEvent arg0) {
			owner.hideWithResult(true);
		}
	};
	
	private ButtonClickHandler cancelHandler = new ButtonClickHandler(this) {
		public void onClick(ClickEvent arg0) {
			owner.hideWithResult(false);
		}
	};
}
