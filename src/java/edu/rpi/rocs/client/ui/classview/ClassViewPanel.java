package edu.rpi.rocs.client.ui.classview;

import java.util.List;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import edu.rpi.rocs.client.ui.coursesearch.CourseSearchPanel;
import edu.rpi.rocs.client.ui.scheduler.SchedulerFilterDisplayPanel;
import edu.rpi.rocs.client.ui.ListBoxHTML;
import edu.rpi.rocs.client.ui.ROCSInterface;
import edu.rpi.rocs.client.objectmodel.SchedulerManager;
import edu.rpi.rocs.client.objectmodel.CourseStatusObject;
import edu.rpi.rocs.client.objectmodel.Course;
import edu.rpi.rocs.client.objectmodel.SchedulerManager.*;

public class ClassViewPanel extends VerticalPanel implements CourseAddedHandler, CourseRemovedHandler, CourseRequiredHandler, CourseOptionalHandler
{
	//UI Elements:
	private FlexTable layout=null; //Table: One row, Two columns
	private VerticalPanel listHolder=null; //Holds the classList
	private VerticalPanel buttonHolder=null; //Holds the three buttons
	private ListBoxHTML classList=null; //List of selected classes
	private Label selectedTitle=null; //Label for the list of classes
	private Anchor requiredButton=null; //Required Button
	private Anchor optionalButton=null; //Optional Button
	private Anchor removeButton=null; //Remove Button
	private Anchor continueButton=null;

	//Data Members:
	private List<CourseStatusObject> curCourses=null;

	private static ClassViewPanel instance = null;
	public static ClassViewPanel getInstance() {
		if (instance == null) {
			instance = new ClassViewPanel();
		}
		return instance;
	}

	private ClassViewPanel() {
		SchedulerManager.getInstance().addCourseAddedEventHandler( this );
		SchedulerManager.getInstance().addCourseOptionalEventHandler(this);
		SchedulerManager.getInstance().addCourseRequiredEventHandler(this);
		SchedulerManager.getInstance().addCourseRemovedEventHandler(this);
		
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
		continueButton = new Anchor("Continue");

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
		
		continueButton.addStyleName("greenbutton");
		continueButton.addStyleName("linkbutton");
		continueButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent arg0) {
				if(!ROCSInterface.getInstance().isDisplaying(SchedulerFilterDisplayPanel.getInstance())) {
					ROCSInterface.getInstance().show(SchedulerFilterDisplayPanel.getInstance(), true);
				}
			}
			
		});

		this.add(layout);
		this.add(continueButton);
	}

	public void printMsg( String str )
	{
		classList.addHTML( str, "MESSAGE" );
	}

	private void updateList()
	{
		curCourses = SchedulerManager.getInstance().getSelectedCourses();
		classList.clear();
		for(int x = 0; x < curCourses.size(); x++)
		{
			Course course = curCourses.get(x).getCourse();
			String data = "";
			if( SchedulerManager.getInstance().isCourseRequired( course ) )
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
		boolean mod = false;
		for(int i=0; i<classList.getItemCount(); i++)
		{
			if( classList.isItemSelected(i) )
			{
				mod = true;
				Course c = curCourses.get(i).getCourse();
				SchedulerManager.getInstance().setCourseRequired(c);
			}
		}
		if (mod) CourseSearchPanel.getInstance().redosearch();
	}

	public void markOptional()
	{
		boolean mod = false;
		for(int i=0; i<classList.getItemCount(); i++)
		{
			if( classList.isItemSelected(i) )
			{
				mod = true;
				Course c = curCourses.get(i).getCourse();
				SchedulerManager.getInstance().setCourseOptional(c);
			}
		}
		if (mod)
		{
			CourseSearchPanel.getInstance().rebuildFS();
			CourseSearchPanel.getInstance().redosearch();
		}
	}

	public void remove()
	{
		boolean mod = false;
		for(int i=0; i<classList.getItemCount(); i++)
		{
			if( classList.isItemSelected(i) )
			{
				mod = true;
				Course c = curCourses.get(i).getCourse();
				SchedulerManager.getInstance().removeCourse(c);
			}
		}
		if (mod)
		{
			CourseSearchPanel.getInstance().rebuildFS();
			CourseSearchPanel.getInstance().redosearch();
		}
	}
}
