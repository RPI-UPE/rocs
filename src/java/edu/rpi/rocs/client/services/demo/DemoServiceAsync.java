package edu.rpi.rocs.client.services.demo;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface DemoServiceAsync {
	void getRandomNumber(AsyncCallback<Long> callback);
}
