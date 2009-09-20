package edu.rpi.rocs.client.ui;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.InlineHyperlink;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;

public class WelcomeScreen extends VerticalPanel {
	InlineHTML title;
	InlineHTML subtitle;
	HTML body;
	HTML prompt;
	FlexTable buttonTable;
	Hyperlink tutorialButton;
	Hyperlink continueButton;
	static WelcomeScreen theInstance;
	
	public static WelcomeScreen getInstance() {
		if(theInstance==null) theInstance = new WelcomeScreen();
		return theInstance;
	}
	
	private WelcomeScreen() {
		title = new InlineHTML("<h1 align=\"center\">Welcome to ROCS</h1>");
		subtitle = new InlineHTML("<h4 align=\"center\">The Rensselaer Open Course Scheduler</h4>");
		body = new HTML("<p>The Rensselaer Open Course Scheduler was developed by Upsilon Pi Epsilon as a replacement to the RPI Scheduler. The new scheduler is tied to your RCS account through the RPI Portal and uses information provided by the Registrar as part of an initiative to change how you plan your studies and schedule courses. Eventually, ROCS will be tied into the Banner system underlying SIS, providing a direct mechanism for registering your courses for the coming semester.</p>");
		prompt = new HTML("<p>How would you like to continue?</p>");
		tutorialButton = new InlineHyperlink("See a ROCS Tutorial","rocs-tutorial");
		tutorialButton.addStyleName("bluebutton");
		tutorialButton.addStyleName("linkbutton");
		continueButton = new InlineHyperlink("Start using ROCS", "rocs-main");
		continueButton.addStyleName("bluebutton");
		continueButton.addStyleName("linkbutton");
		buttonTable = new FlexTable();
		buttonTable.setWidth("100%");
		buttonTable.setHTML(0, 0, "&nbsp;");
		buttonTable.setWidget(0, 1, tutorialButton);
		buttonTable.setHTML(0, 2, "&nbsp");
		buttonTable.setWidget(0, 3, continueButton);
		buttonTable.setHTML(0, 4, "&nbsp;");
		FlexCellFormatter f = buttonTable.getFlexCellFormatter();
		f.setWidth(0, 0, "20%");
		f.setWidth(0, 1, "25%");
		f.setWidth(0, 2, "10%");
		f.setWidth(0, 3, "25%");
		f.setWidth(0, 4, "20%");
		
		this.add(title);
		this.add(subtitle);
		this.add(body);
		this.add(prompt);
		this.add(buttonTable);
	}
}
