package edu.rpi.rocs.server.services.demo;

import java.util.Random;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import edu.rpi.rocs.client.services.demo.DemoService;

public class DemoServiceImpl extends RemoteServiceServlet implements
		DemoService {
	

	private static final long serialVersionUID = 1L;
	
	private static final Random generator = new Random();

	public Long getRandomNumber() {
		return generator.nextLong();
	}

}
