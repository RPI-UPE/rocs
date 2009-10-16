package edu.rpi.rocs.client.ui.classview;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.HorizontalPanel;
import edu.rpi.rocs.client.ui.ListBoxHTML;
import edu.rpi.rocs.client.objectmodel.SchedulerManager;
import edu.rpi.rocs.client.objectmodel.SchedulerManager.*;

public class ClassViewPanel extends HorizontalPanel implements CourseAddedHandler 
{
	private ListBoxHTML classList;
	private FlexTable layout;
	private Label selectedTitle;
	
	private Anchor requiredButton;
	private Anchor optionalButton;
	private Anchor removeButton;

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
		
		selectedTitle = new Label("Selected Courses:");
		
		layout.setWidget(0, 0, selectedTitle);
		
		classList = new ListBoxHTML(true);
		classList.addStyleName("class_view");
		classList.addStyleName("search_results");
		classList.addHTML("Awesome Course", "Awesome-101");
		classList.addHTML("Cool Course", "Awesome-201");
		classList.addHTML("Terrible Course", "Awesome-301");
		classList.addHTML("Who-Takes-This Course", "Awesome-401");
		classList.addHTML("Meh Course", "Awesome-501");
		
		layout.setWidget(1, 0, classList);
		layout.getFlexCellFormatter().setColSpan(1, 0, 2);
		layout.getFlexCellFormatter().setRowSpan(1, 0, 3);
		
		requiredButton = new Anchor("Mark Required");
		optionalButton = new Anchor("Mark Optional");
		removeButton = new Anchor("Remove");
		
		layout.setWidget(1, 2, requiredButton);
		layout.setWidget(2, 2, optionalButton);
		layout.setWidget(3, 2, removeButton);
		
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
	
	public void handleEvent()
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
