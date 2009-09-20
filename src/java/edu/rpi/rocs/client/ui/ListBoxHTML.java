package edu.rpi.rocs.client.ui;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.OptionElement;
import com.google.gwt.dom.client.SelectElement;
import com.google.gwt.user.client.ui.ListBox;

public class ListBoxHTML extends ListBox {
	private static final int INSERT_AT_END = -1;
	
	public ListBoxHTML() {
		super();
	}
	
	public ListBoxHTML(boolean multiple) {
		super(multiple);
	}
	
	public void addHTML(String html, String value) {
		insertHTML(html, value, INSERT_AT_END);
	}
	
	public void insertHTML(String html, String value, int index) {
		SelectElement select = getSelectElement2();
		OptionElement option = Document.get().createOptionElement();
		option.setInnerHTML(html);
		option.setValue(value);
		
		if((index == -1) || (index == select.getLength())) {
			select.add(option, null);
		}
		else {
			OptionElement before = select.getOptions().getItem(index);
			select.add(option, before);
		}
	}
	
	protected SelectElement getSelectElement2() {
		return getElement().cast();
	}
}
