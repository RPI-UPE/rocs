package edu.rpi.rocs.server.hibernate.util;

import java.io.File;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	private static final SessionFactory sessionFactory = buildSessionFactory();
	
	private static SessionFactory buildSessionFactory() {
		try {
			File f = new File("src/xml/hibernate.cfg.xml");
			if(!f.exists()) {
				throw new ExceptionInInitializerError(new Exception("Unable to locate hibernate configuration file for ROCS."));
			}
			return new Configuration().configure(f).buildSessionFactory();
		}
		catch(Throwable ex) {
			System.err.println("Initial SessionFactory creation failed. "+ex);
			throw new ExceptionInInitializerError(ex);
		}
	}
	
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}
