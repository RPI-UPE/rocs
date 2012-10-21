package edu.rpi.rocs.client.ui.filters;

import java.util.HashSet;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;

public class MaxCreditFilterWidget extends SimplePanel implements ChangeHandler {
	private HorizontalPanel layout = new HorizontalPanel();
	private InlineLabel label = new InlineLabel("Maximum number of credits: ");
	private TextBox entryPane = new TextBox();
	private HashSet<MaxCreditValueChanged> changeHandlers = new HashSet<MaxCreditValueChanged>();
	
	public interface MaxCreditValueChanged {
		void setThreshold(int newValue);
	}
	
	public void addChangeHandler(MaxCreditValueChanged e) {
		changeHandlers.add(e);
	}
	
	public void removeChangeHandler(MaxCreditValueChanged e) {
		changeHandlers.remove(e);
	}
	
	public MaxCreditFilterWidget() {
		this(21);
	}
	
	public MaxCreditFilterWidget(int start) {
		entryPane.setText(Integer.toString(start));
		entryPane.addChangeHandler(this);
		
		layout.add(label);
		layout.add(entryPane);
		
		this.add(layout);
	}

	public void onChange(ChangeEvent arg0) {
		// TODO Auto-generated method stub
		for(MaxCreditValueChanged handler : changeHandlers) {
			handler.setThreshold(Integer.parseInt(entryPane.getValue()));
		}
	}
}
