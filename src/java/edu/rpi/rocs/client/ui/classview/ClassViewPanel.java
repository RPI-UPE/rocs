package edu.rpi.rocs.client.ui.classview;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import edu.rpi.rocs.client.ui.ListBoxHTML;

public class ClassViewPanel extends HorizontalPanel 
{
	private ListBoxHTML classList;

	private static ClassViewPanel instance = null;
	public static ClassViewPanel getInstance() {
		if (instance == null) {
			instance = new ClassViewPanel();
		}
		return instance;
	}
	
	private ClassViewPanel() {
		classList = new ListBoxHTML(true);
		this.add(classList);
		
		classList.addStyleName("class_view");
		
		classList.addStyleName("search_results");
		classList.addHTML("Awesome Course", "Awesome-101");
		classList.addHTML("Cool Course", "Awesome-201");
		classList.addHTML("Terrible Course", "Awesome-301");
		classList.addHTML("Who-Takes-This Course", "Awesome-401");
		classList.addHTML("Meh Course", "Awesome-501");
	}
	
	
}
