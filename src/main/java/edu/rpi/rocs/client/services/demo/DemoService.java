package edu.rpi.rocs.client.services.demo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public interface DemoService extends RemoteService {
	public Long getRandomNumber();
	
	public static class Singleton {
		private static DemoServiceAsync instance = null;
		public static DemoServiceAsync getInstance() {
			if (instance == null) {
				instance = (DemoServiceAsync)GWT.create(DemoService.class);
				((ServiceDefTarget)instance).setServiceEntryPoint(GWT.getModuleBaseURL() + "DemoService");
			}
			return instance;
		}
	}
}
