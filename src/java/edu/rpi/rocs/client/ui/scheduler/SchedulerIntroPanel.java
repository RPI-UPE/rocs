package edu.rpi.rocs.client.ui.scheduler;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.rpi.rocs.client.ui.scheduler.SchedulerPanel.SchedulerPage;

public class SchedulerIntroPanel extends SimplePanel {
	private FlexTable table;
	private HTML text;
	private Anchor advance;
	private VerticalPanel arrangement;
	
	public SchedulerIntroPanel() {
		this.setHeight("100%");
		table = new FlexTable();
		text = new HTML("To create a schedule, you must define a set of filters to control which schedules you want.<br/>");
		advance = new Anchor("Continue");
		advance.addStyleName("greybutton");
		advance.addStyleName("linkbutton");
		advance.addClickHandler(handleContinue);
		arrangement = new VerticalPanel();
		arrangement.add(text);
		arrangement.add(advance);
		table.setWidget(0, 0, arrangement);
		table.setWidth("100%");
		table.setHeight("100%");
		table.getFlexCellFormatter().setAlignment(0, 0, HasHorizontalAlignment.ALIGN_CENTER, HasVerticalAlignment.ALIGN_MIDDLE);
		this.add(table);
	}
	
	private ClickHandler handleContinue = new ClickHandler() {

		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			SchedulerPanel.get().switchTo(SchedulerPage.FilterPage);
		}
		
	};
}
