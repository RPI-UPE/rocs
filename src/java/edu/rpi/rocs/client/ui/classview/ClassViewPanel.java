package edu.rpi.rocs.client.ui.classview;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import edu.rpi.rocs.client.ui.ListBoxHTML;
import edu.rpi.rocs.client.objectmodel.SchedulerManager;
import edu.rpi.rocs.client.objectmodel.SchedulerManager.*;

public class ClassViewPanel extends HorizontalPanel implements CourseAddedHandler 
{
	//UI Elements:
	private FlexTable layout; //Table: One row, Two columns
	private VerticalPanel listHolder; //Holds the classList
	private VerticalPanel buttonHolder; //Holds the three buttons
	private ListBoxHTML classList; //List of selected classes
	private Label selectedTitle; //Label for the list of classes
	private Anchor requiredButton; //Required Button
	private Anchor optionalButton; //Optional Button
	private Anchor removeButton; //Remove Button
	
	//Data Members:

	private static ClassViewPanel instance = null;
	public static ClassViewPanel getInstance() {
		if (instance == null) {
			instance = new ClassViewPanel();
		}
		return instance;
	}
	
	private ClassViewPanel() {
		SchedulerManager.get().addCourseAddedEventHandler( this );
		
		layout = new FlexTable();
		
		listHolder = new VerticalPanel();
		buttonHolder = new VerticalPanel();
		
		layout.setWidget(0, 0, listHolder);
		layout.setWidget(0, 1, buttonHolder);
		
		selectedTitle = new Label("Selected Courses:");
		
		classList = new ListBoxHTML(true);
		classList.addStyleName("class_view");
		classList.addStyleName("search_results");
		
		listHolder.add( selectedTitle );
		listHolder.add( classList );
		
		requiredButton = new Anchor("Mark Required");
		optionalButton = new Anchor("Mark Optional");
		removeButton = new Anchor("Remove");
		
		buttonHolder.add(requiredButton);
		buttonHolder.add(optionalButton);
		buttonHolder.add(removeButton);
		
		requiredButton.addStyleName("greybutton");
		requiredButton.addStyleName("linkbutton");
		requiredButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				ClassViewPanel.getInstance().markRequired();
			}
			
		});
		
		optionalButton.addStyleName("greybutton");
		optionalButton.addStyleName("linkbutton");
		optionalButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				ClassViewPanel.getInstance().markOptional();
			}
			
		});
		
		removeButton.addStyleName("redbutton");
		removeButton.addStyleName("linkbutton");
		removeButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				ClassViewPanel.getInstance().remove();
			}
			
		});
		
		this.add(layout);
	}
	
	public void handleEvent(CourseStatusObject status)
	{
		classList.addHTML("HEY THEY ADDED SOMETHING!", "Input");
	}
	
	public void markRequired()
	{
		classList.addHTML("REQUIRED", "Input");
	}
	
	public void markOptional()
	{
		classList.addHTML("OPTIONAL", "Input");
	}
	
	public void remove()
	{
		classList.addHTML("REMOVE", "Input");
	}
}
