package edu.rpi.rocs.client;

public interface Factory {
	Instantiable newInstance(String className);
}
