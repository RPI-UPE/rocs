package edu.rpi.rocs.client.ui.entry;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.RootPanel;

import edu.rpi.rocs.client.ui.ROCSInterface;
import edu.rpi.rocs.client.ui.WelcomeScreen;
import edu.rpi.rocs.client.ui.coursesearch.CourseSearchPanel;

public class MainEntryPoint implements EntryPoint {

	private static MainEntryPoint entryPoint;
	private static String lastView;
	
	public static MainEntryPoint getInstance() {
		return entryPoint;
	}
	
	public void onModuleLoad() {
		entryPoint = this;
		Log.trace("Module loading");
		
		String initToken = History.getToken();
		if(initToken.length()==0) {
			History.newItem("rocs-welcome");
			initToken = "rocs-welcome";
		}
		History.addValueChangeHandler(historyChangedHandler);
		Log.trace("Init Token = " + initToken);
		if(initToken=="rocs-main") {
			RootPanel.get("rocs_PORTLET_rocs_root_view").add(ROCSInterface.getInstance());
		}
		else if(initToken=="rocs-welcome") {
			RootPanel.get("rocs_PORTLET_rocs_root_view").add(WelcomeScreen.getInstance());
		}
		else {
			initToken = "rocs-welcome";
			History.newItem("rocs-welcome");
			RootPanel.get("rocs_PORTLET_rocs_root_view").add(WelcomeScreen.getInstance());
		}
		lastView = initToken;
	}
	
	private ValueChangeHandler<String> historyChangedHandler =
		new ValueChangeHandler<String>() {

			public void onValueChange(ValueChangeEvent<String> event) {
				// TODO Auto-generated method stub
				String val = event.getValue();
				if(val == "rocs-main") {
					if(lastView=="rocs-welcome") {
						RootPanel.get("rocs_PORTLET_rocs_root_view").remove(WelcomeScreen.getInstance());
						RootPanel.get("rocs_PORTLET_rocs_root_view").add(ROCSInterface.getInstance());
					}
				}
				else if(val == "rocs-welcome") {
					RootPanel.get("rocs_PORTLET_rocs_root_view").clear();
					RootPanel.get("rocs_PORTLET_rocs_root_view").add(WelcomeScreen.getInstance());
				}
				else if(val == "rocs-search") {
					CourseSearchPanel.getInstance().search();
				}
			}
		
	};
}
