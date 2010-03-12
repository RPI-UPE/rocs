package edu.rpi.rocs.client.ui;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.InlineHyperlink;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;

import edu.rpi.rocs.client.ImageManager;

public class WelcomeScreen extends VerticalPanel {
	InlineHTML title;
	InlineHTML subtitle;
	HTML body;
	HTML prompt;
	HTML warning;
	FlexTable buttonTable;
	Hyperlink tutorialButton;
	Hyperlink continueButton;
	static WelcomeScreen theInstance;
	
	public static WelcomeScreen getInstance() {
		if(theInstance==null) theInstance = new WelcomeScreen();
		return theInstance;
	}
	
	private WelcomeScreen() {
		title = new InlineHTML("<h1 align=\"center\">Welcome to the Rensselaer Open Course Scheduler Beta</h1>");
		subtitle = new InlineHTML("<h4 align=\"center\">The Rensselaer Open Course Scheduler [BETA]</h4>");
		body = new HTML("<p>The Rensselaer Open Course Scheduler was developed by <a href=\"http://upe.cs.rpi.edu/\" target=\"_blank\">Upsilon Pi Epsilon</a> as a replacement to the RPI Scheduler, is partially funded by the <a href=\"http://rcos.cs.rpi.edu/\">Rensselaer Center for Open Source</a> directed by Dr. Krishnamoorthy, and is endorsed by the <a href=\"http://srfs.rpi.edu/setup.do\">Office of the Registrar</a>. The new scheduler is tied to your RCS account through the RPI Portal and uses information provided by the Registrar as part of an initiative to change how you plan your studies and schedule courses. Eventually, ROCS will be tied into the Banner system underlying SIS, providing a direct mechanism for registering your courses for the coming semester.</p>");
		warning = new HTML("<p align=\"center\">ROCS works best with:"+
			    "<br/><img title=\"Mozilla Firefox\" src=\""+ImageManager.getPathForImage("firefox.png")+
				"\"/><img title=\"Google Chrome\" src=\""+ImageManager.getPathForImage("chrome.png")+
				"\"/><img title=\"Apple Safari\" src=\""+ImageManager.getPathForImage("safari.png")+
				"\"/><img title=\"Opera\" src=\""+ImageManager.getPathForImage("opera.png")+
				"\"/></p>");
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
		this.add(warning);
		this.add(prompt);
		this.add(buttonTable);
		/*
		HTMLTableList t = new HTMLTableList();
		HTMLTableListRow r = t.new HTMLTableListRow();
		HTMLTableListCell c=null;
		c = t.new HTMLTableListCell(true); c.setText("Dept"); r.add(c);
		c = t.new HTMLTableListCell(true); c.setText("Num"); r.add(c);
		c = t.new HTMLTableListCell(true); c.setText("Seats"); r.add(c);
		c = t.new HTMLTableListCell(true); c.setText("Title"); r.add(c);
		c = t.new HTMLTableListCell(true); c.setText("Instructor"); r.add(c);
		t.add(r);
		r = t.new HTMLTableListRow();
		c = t.new HTMLTableListCell(); c.setStyleName("dept"); c.setText("CSCI"); r.add(c);
		c = t.new HTMLTableListCell(); c.setStyleName("num"); c.setText("4020"); r.add(c);
		c = t.new HTMLTableListCell(); c.setStyleName("seats"); c.setText("37 of 50"); r.add(c);
		c = t.new HTMLTableListCell(); c.setStyleName("title"); c.setText("Computer Algorithms"); r.add(c);
		c = t.new HTMLTableListCell(); c.setText("Anshelevich"); r.add(c);
		t.add(r);
		r.setStyleName("course-selected");
		r = t.new HTMLTableListRow();
		c = t.new HTMLTableListCell(); c.setStyleName("dept"); c.setText("CSCI"); r.add(c);
		c = t.new HTMLTableListCell(); c.setStyleName("num"); c.setText("1100"); r.add(c);
		c = t.new HTMLTableListCell(); c.setStyleName("seats"); c.setText("129 of 200"); r.add(c);
		c = t.new HTMLTableListCell(); c.setStyleName("title"); c.setText("Computer Science I"); r.add(c);
		c = t.new HTMLTableListCell(); c.setText("Hardwick"); r.add(c);
		t.add(r);
		r.setStyleName("course-conflict");
		r = t.new HTMLTableListRow();
		c = t.new HTMLTableListCell(); c.setStyleName("dept"); c.setText("PSYC"); r.add(c);
		c = t.new HTMLTableListCell(); c.setStyleName("num"); c.setText("4310"); r.add(c);
		c = t.new HTMLTableListCell(); c.setStyleName("seats"); c.setText("62 of 60"); r.add(c);
		c = t.new HTMLTableListCell(); c.setStyleName("title"); c.setText("Adv. Methods and Stats"); r.add(c);
		c = t.new HTMLTableListCell(); c.setText("Kalsher"); r.add(c);
		r.setStyleName("course-closed");
		t.add(r);
		r = t.new HTMLTableListRow();
		c = t.new HTMLTableListCell(); c.setStyleName("dept"); c.setText("CSCI"); r.add(c);
		c = t.new HTMLTableListCell(); c.setStyleName("num"); c.setText("6990"); r.add(c);
		c = t.new HTMLTableListCell(); c.setStyleName("seats"); c.setText("9 of 20"); r.add(c);
		c = t.new HTMLTableListCell(); c.setStyleName("title"); c.setText("Masters Thesis"); r.add(c);
		c = t.new HTMLTableListCell(); c.setText("Adali, Anshelevich, Stewart, Krishnamoorthy, Bennett, Hendler, Das, Yener, Bringsjord, Milanova, Cutler, Zaki, McGuinness, Shephard, Carothers, Szymanski, Akella, Fox, Varela, Bystroff, Goldberg, Magdon-Ismail, Isler"); r.add(c);
		r.setStyleName("course-selectable");
		t.add(r);
		t.setStyleName("course-search-list");
		this.add(t);
		*/
	}
}
