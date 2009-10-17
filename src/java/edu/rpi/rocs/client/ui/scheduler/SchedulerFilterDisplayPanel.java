package edu.rpi.rocs.client.ui.scheduler;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.rpi.rocs.client.ReflectiveFactory;
import edu.rpi.rocs.client.filters.schedule.ScheduleFilter;
import edu.rpi.rocs.client.filters.schedule.TimeSchedulerFilter;
import edu.rpi.rocs.client.ui.scheduler.SchedulerFilterDialogBox.SchedulerFilterDialogBoxCompleted;

public class SchedulerFilterDisplayPanel extends VerticalPanel implements SchedulerFilterDialogBoxCompleted, ChangeHandler {
	private FlexTable layout = new FlexTable();
	private VerticalPanel subLayout = new VerticalPanel();
	private HorizontalPanel subSubLayout = new HorizontalPanel();
	private Anchor addButton = new Anchor("Add Filter");
	private Anchor removeButton = new Anchor("Remove Filter");
	//private Anchor continueButton = new Anchor("Schedule");
	private ListBox filterList = new ListBox(true);
	private static SchedulerFilterDisplayPanel theInstance = null;
	private ArrayList<ScheduleFilter> currentFilters = new ArrayList<ScheduleFilter>();
	private SimplePanel wrapperPanel = new SimplePanel();
	private Anchor generateButton = new Anchor("Generate Schedules");
	
	public ArrayList<ScheduleFilter> getCurrentFilters() {
		return new ArrayList<ScheduleFilter>(currentFilters);
	}
	
	public static SchedulerFilterDisplayPanel get() {
		if(theInstance==null) theInstance = new SchedulerFilterDisplayPanel();
		return theInstance;
	}
	
	private SchedulerFilterDisplayPanel() {
		currentFilters.add(new TimeSchedulerFilter());
		filterList.addItem(currentFilters.get(0).getDisplayTitle(), "0");
		
		addButton.addStyleName("linkbutton");
		addButton.addStyleName("greenbutton");
		addButton.addClickHandler(addFilterHandler);
		
		removeButton.addStyleName("linkbutton");
		removeButton.addStyleName("redbutton");
		removeButton.addClickHandler(removeFilterHandler);
		
		generateButton.addStyleName("linkbutton");
		generateButton.addStyleName("greybutton");
		generateButton.addClickHandler(generateSchedulesHandler);
		
		subSubLayout.add(addButton);
		subSubLayout.add(removeButton);
		subSubLayout.add(generateButton);
		
		subLayout.add(filterList);
		subLayout.add(subSubLayout);
		subLayout.setWidth("100%");
		
		filterList.setHeight("370px");
		filterList.setWidth("100%");
		filterList.setItemSelected(0, true);
		filterList.addChangeHandler(this);
		
		wrapperPanel.addStyleName("filter-wrapper");
		wrapperPanel.add(currentFilters.get(0).getWidget());
		
		//continueButton.addStyleName("linkbutton");
		//continueButton.addStyleName("greenButton");
		//continueButton.setWidth("100%");
		
		layout.setWidget(0, 0, subLayout);
		layout.setHTML(0, 1, "&nbsp;");
		layout.setWidget(0, 2, wrapperPanel);
		layout.getFlexCellFormatter().setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_TOP);
		layout.getFlexCellFormatter().setWidth(0, 0, "30%");
		layout.getFlexCellFormatter().setWidth(0, 1, "2%");
		layout.getFlexCellFormatter().setWidth(0, 2, "68%");
		layout.setWidth("100%");
		layout.setHeight("100%");
		this.add(layout);
		//this.add(continueButton);
		this.setWidth("100%");
		this.setHeight("100%");
	}
	
	public void buttonClicked(Anchor button) {
		if(button == addButton) {
			SchedulerFilterDialogBox db = new SchedulerFilterDialogBox();
			db.addCompletionHandler(this);
			db.center();
		}
	}
	
	private class ButtonClickHandler implements ClickHandler {
		Anchor button;
		SchedulerFilterDisplayPanel owner;
		ButtonClickHandler(SchedulerFilterDisplayPanel dp, Anchor btn) {
			owner = dp;
			button = btn;
		}
		
		public void onClick(ClickEvent arg0) {
			owner.buttonClicked(button);
		}
	}
	
	private ButtonClickHandler addFilterHandler = new ButtonClickHandler(this, addButton);
	private ButtonClickHandler removeFilterHandler = new ButtonClickHandler(this, removeButton);
	private ButtonClickHandler generateSchedulesHandler = new ButtonClickHandler(this, generateButton);

	public void cancelled() {
	}

	public void completedWithFilter(String qName) {
		ScheduleFilter newFilter=null;
		newFilter = (ScheduleFilter)ReflectiveFactory.get().newInstance(qName);
		if(newFilter!=null) {
			currentFilters.add(newFilter);
			filterList.addItem(newFilter.getDisplayTitle(), Integer.toString(currentFilters.size()-1));
		}
	}

	public void onChange(ChangeEvent arg0) {
		// TODO Auto-generated method stub
		int index = filterList.getSelectedIndex();
		ScheduleFilter filter = currentFilters.get(index);
		wrapperPanel.clear();
		wrapperPanel.add(filter.getWidget());
	}
}
