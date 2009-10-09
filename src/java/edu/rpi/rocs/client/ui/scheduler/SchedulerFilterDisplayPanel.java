package edu.rpi.rocs.client.ui.scheduler;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.rpi.rocs.client.filters.schedule.ScheduleFilter;
import edu.rpi.rocs.client.filters.schedule.TimeSchedulerFilter;

public class SchedulerFilterDisplayPanel extends VerticalPanel {
	private FlexTable layout = new FlexTable();
	private VerticalPanel subLayout = new VerticalPanel();
	private HorizontalPanel subSubLayout = new HorizontalPanel();
	private Anchor addButton = new Anchor("Add Filter");
	private Anchor removeButton = new Anchor("Remove Filter");
	private Anchor continueButton = new Anchor("Schedule");
	private ListBox filterList = new ListBox(true);
	private static SchedulerFilterDisplayPanel theInstance = null;
	private ArrayList<ScheduleFilter> currentFilters = new ArrayList<ScheduleFilter>();
	private SimplePanel wrapperPanel = new SimplePanel();
	private Anchor generateButton = new Anchor("Generate Schedules");
	
	public static SchedulerFilterDisplayPanel get() {
		if(theInstance==null) theInstance = new SchedulerFilterDisplayPanel();
		return theInstance;
	}
	
	private SchedulerFilterDisplayPanel() {
		currentFilters.add(new TimeSchedulerFilter());
		filterList.addItem(currentFilters.get(0).getDisplayTitle(), "0");
		addButton.addStyleName("linkbutton");
		addButton.addStyleName("greenbutton");
		removeButton.addStyleName("linkbutton");
		removeButton.addStyleName("redbutton");
		generateButton.addStyleName("linkbutton");
		generateButton.addStyleName("greybutton");
		subSubLayout.add(addButton);
		subSubLayout.add(removeButton);
		subSubLayout.add(generateButton);
		subLayout.add(filterList);
		subLayout.add(subSubLayout);
		subLayout.setWidth("100%");
		filterList.setHeight("370px");
		filterList.setWidth("100%");
		filterList.setItemSelected(0, true);
		wrapperPanel.addStyleName("filter-wrapper");
		wrapperPanel.add(currentFilters.get(0).getWidget());
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
		this.add(continueButton);
		this.setWidth("100%");
		this.setHeight("100%");
	}
}
