package edu.rpi.rocs.client.ui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.InlineHyperlink;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;

import edu.rpi.rocs.client.services.schedulemanager.ScheduleManagerService;

public class WelcomeScreen extends VerticalPanel {
	InlineHTML title;
	InlineHTML subtitle;
	HTML body;
	HTML prompt;
	HTML warning;
	HTML motd;
	FlexTable buttonTable;
	Hyperlink tutorialButton;
	Hyperlink continueButton;
	static WelcomeScreen theInstance;
	
	public static WelcomeScreen getInstance() {
		if(theInstance==null) theInstance = new WelcomeScreen();
		return theInstance;
	}
	
	@SuppressWarnings("deprecation")
	private WelcomeScreen() {
		title = new InlineHTML("<h1 align=\"center\">Welcome to the Rensselaer Open Course Scheduler Beta</h1>");
		subtitle = new InlineHTML("<h4 align=\"center\">The Rensselaer Open Course Scheduler [BETA]</h4>");
		body = new HTML("<p>The Rensselaer Open Course Scheduler was developed by <a href=\"http://upe.cs.rpi.edu/\" target=\"_blank\">Upsilon Pi Epsilon</a> as a replacement to the RPI Scheduler, is partially funded by the <a href=\"http://rcos.cs.rpi.edu/\">Rensselaer Center for Open Source</a> directed by Dr. Krishnamoorthy, and is endorsed by the <a href=\"http://srfs.rpi.edu/setup.do\">Office of the Registrar</a>. The new scheduler is tied to course information and your RCS account through DotCIO and uses information provided by the Registrar.</p>");
		/*
		warning = new HTML("<p align=\"center\">ROCS works best with:"+
			    "<br/><img title=\"Mozilla Firefox\" src=\""+ImageManager.getPathForImage("firefox.png")+
				"\"/><img title=\"Google Chrome\" src=\""+ImageManager.getPathForImage("chrome.png")+
				"\"/><img title=\"Apple Safari\" src=\""+ImageManager.getPathForImage("safari.png")+
				"\"/><img title=\"Opera\" src=\""+ImageManager.getPathForImage("opera.png")+
				"\"/></p>");
				*/
		warning = new HTML("<p align=\"center\">If you are using Internet Explorer, please be advised that ROCS will not work with any version of IE below 8.<br/>Please update if your version of Internet Explorer is out of date.</p>");
		motd = new HTML("<p></p>");
		prompt = new HTML("<p>How would you like to continue?</p>");
		tutorialButton = new InlineHyperlink("See a ROCS Tutorial","rocs-tutorial");
		tutorialButton.addStyleName("bluebutton");
		tutorialButton.addStyleName("linkbutton");
		tutorialButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent arg0) {
				Window.open("http://upe.acm.cs.rpi.edu/rocs/tutorial.mov", "_blank", "");
			}
			
		});
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
		this.add(warning);
		this.add(motd);
		this.add(prompt);
		this.add(buttonTable);
		ScheduleManagerService.Singleton.getInstance().getMOTD(new AsyncCallback<String> (){

			public void onFailure(Throwable arg0) {
			}

			public void onSuccess(String arg0) {
				motd.setHTML(arg0);
			}
			
		});
	}
}
