package edu.rpi.rocs.client.ui.filters;

import java.util.HashSet;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;

public class NoMoreThanNumAtLevelFilterWidget extends SimplePanel implements ChangeHandler {

	public interface NoMoreThanNumAtLevelChangeHandler {
		public void setNumber(int num);
		public void setLevel(int level);
	}
	
	private HashSet<NoMoreThanNumAtLevelChangeHandler> changeHandlers = new HashSet<NoMoreThanNumAtLevelChangeHandler>();
	
	public void addChangeHandler(
			NoMoreThanNumAtLevelChangeHandler e) {
		// TODO Auto-generated method stub
		changeHandlers.add(e);
	}
	
	public void removeChangeHandler(NoMoreThanNumAtLevelChangeHandler e) {
		changeHandlers.remove(e);
	}
	
	private FlexTable layout = new FlexTable();
	private InlineLabel lbl1 = new InlineLabel("Number of courses: ");
	private InlineLabel lbl2 = new InlineLabel("Filter level: ");
	private TextBox textBox = new TextBox();
	private ListBox listBox = new ListBox();
	
	public NoMoreThanNumAtLevelFilterWidget() {
		listBox.addItem("1000");
		listBox.addItem("2000");
		listBox.addItem("4000");
		listBox.addItem("6000");
		listBox.setSelectedIndex(0);
		listBox.addChangeHandler(this);
		
		textBox.setText("3");
		textBox.addChangeHandler(this);
				
		layout.setWidget(0, 0, lbl1);
		layout.setWidget(1, 0, lbl2);
		layout.setWidget(0, 1, textBox);
		layout.setWidget(1, 1, listBox);
		
		this.add(layout);
	}
	
	public void onChange(ChangeEvent arg0) {
		if(arg0.getSource() == textBox) {
			for(NoMoreThanNumAtLevelChangeHandler handler : changeHandlers) {
				handler.setNumber(Integer.parseInt(textBox.getText()));
			}
		}
		else if(arg0.getSource() == listBox) {
			for(NoMoreThanNumAtLevelChangeHandler handler : changeHandlers) {
				handler.setLevel(Integer.parseInt(listBox.getItemText(listBox.getSelectedIndex())));
			}
		}
	}

}
