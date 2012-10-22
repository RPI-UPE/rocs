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
	
	public void addStyleNameOfOption(int index, String name) {
		SelectElement select = getSelectElement2();
		OptionElement option = select.getOptions().getItem(index);
		String clsname = option.getClassName();
		if(clsname.startsWith(name+" ") || clsname.endsWith(" "+name)) return;
		if(clsname.contains((" "+name+" ").subSequence(0, name.length()+2))) return;
		clsname += " name";
		option.setClassName(clsname);
	}
	
	public void removeStyleNameOfOption(int index, String name) {
		SelectElement select = getSelectElement2();
		OptionElement option = select.getOptions().getItem(index);
		String clsname = option.getClassName();
		if(clsname.startsWith(name+" ")) {
			clsname.replaceFirst(name+" ", "");
		}
		else if(clsname.endsWith(" "+name)) {
			clsname.replaceFirst(" "+name+"$", "");
		}
		else if(clsname.contains((" "+name+" ").subSequence(0, name.length()+2))) {
			clsname.replaceFirst(" "+name+" ", "");
		}
		option.setClassName(clsname);
	}
	
	public void insertHTML(String html, String value, int index) {
		SelectElement select = getSelectElement2();
		OptionElement option = Document.get().createOptionElement();
		option.setInnerHTML(html);
		option.setValue(value);
		
		if((index == -1) || (index == select.getLength())) {
			//select.add(option, null);
			select.appendChild(option);
		}
		else {
			OptionElement before = select.getOptions().getItem(index);
			select.insertBefore(option, before);
		}
	}
	
	protected SelectElement getSelectElement2() {
		return getElement().cast();
	}
}
