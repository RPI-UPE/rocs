package edu.rpi.rocs.client.ui.classview;

import java.util.List;

import com.allen_sauer.gwt.log.client.Log;
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
import edu.rpi.rocs.client.objectmodel.Course;

public class ClassViewPanel extends HorizontalPanel implements CourseAddedHandler, CourseRemovedHandler, CourseRequiredHandler, CourseOptionalHandler 
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
	private List<CourseStatusObject> curCourses;

	private static ClassViewPanel instance = null;
	public static ClassViewPanel getInstance() {
		if (instance == null) {
			instance = new ClassViewPanel();
		}
		return instance;
	}
	
	private ClassViewPanel() {
		SchedulerManager.get().addCourseAddedEventHandler( this );
		SchedulerManager.get().addCourseOptionalEventHandler(this);
		SchedulerManager.get().addCourseRequiredEventHandler(this);
		SchedulerManager.get().addCourseRemovedEventHandler(this);
		
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
	
	public void printMsg( String str )
	{
		classList.addHTML( str, "MESSAGE" );
	}
	
	private void updateList()
	{
		curCourses = SchedulerManager.get().getSelectedCourses();
		classList.clear();
		for(int x = 0; x < curCourses.size(); x++)
		{
			Course course = curCourses.get(x).getCourse();
			String data = "";
			if( SchedulerManager.get().isCourseRequired( course ) )
			{
				data += "R&nbsp;&nbsp;";
			}
			else
			{
				data += "&nbsp;&nbsp;&nbsp;";
			}
			data += course.getListDescription();
			classList.addHTML(data, course.getDept()+course.getNum());
		}
	}
	
	public void handleEvent(CourseStatusObject status)
	{
		Log.debug("Class " + status.getCourse().getName() + " has status " + (status.getRequired() ? "required" : "optional"));
		this.updateList();
	}
	
	public void markRequired()
	{
		for(int i=0; i<classList.getItemCount(); i++) 
		{
			if( classList.isItemSelected(i) )
			{
				Course c = curCourses.get(i).getCourse();
				SchedulerManager.get().setCourseRequired(c);
			}
		}
	}
	
	public void markOptional()
	{
		for(int i=0; i<classList.getItemCount(); i++) 
		{
			if( classList.isItemSelected(i) )
			{
				Course c = curCourses.get(i).getCourse();
				SchedulerManager.get().setCourseOptional(c);
			}
		}
	}
	
	public void remove()
	{
		for(int i=0; i<classList.getItemCount(); i++) 
		{
			if( classList.isItemSelected(i) )
			{
				Course c = curCourses.get(i).getCourse();
				SchedulerManager.get().removeCourse(c);
			}
		}
	}
}
