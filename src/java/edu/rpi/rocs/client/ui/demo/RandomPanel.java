package edu.rpi.rocs.client.ui.demo;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;

import edu.rpi.rocs.client.services.demo.DemoService;

public class RandomPanel extends HorizontalPanel implements ClickHandler {

	Label random = new Label("Waiting...");
	Button getNumber = new Button("Take a number!");
	
	public RandomPanel() {
		getNumber.addClickHandler(this);
		
		this.add(random);
		this.add(getNumber);
	}

	
	private AsyncCallback<Long> randomNumberCallback = new AsyncCallback<Long>() {
		public void onFailure(Throwable caught) {
			random.setText("Error: " + caught.getMessage());
		}

		public void onSuccess(Long result) {
			try {
				random.setText(result.toString());
			} catch (NullPointerException e) {
				random.setText("Oops, result was null!");
			}
		}
	};
	
	public void onClick(ClickEvent event) {
		if (event.getSource() == getNumber) {
			random.setText("Retrieving...");
			DemoService.Singleton.getInstance().getRandomNumber(randomNumberCallback);
		}
	}
}
