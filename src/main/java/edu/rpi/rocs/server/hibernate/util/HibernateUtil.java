package edu.rpi.rocs.server.hibernate.util;

import java.net.URL;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	private static SessionFactory sessionFactory = null;
	
	public static void init(URL confpath) {
		if(sessionFactory==null) sessionFactory = buildSessionFactory(confpath);
	}
	
	private static SessionFactory buildSessionFactory(URL confpath) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			/*
			if(!f.exists()) {
				throw new ExceptionInInitializerError(new Exception("Unable to locate hibernate configuration file for ROCS at "+f.getAbsolutePath()+"."));
			}
			*/
			return new Configuration().configure(confpath)
						.addURL(new URL(confpath, "Period.hbm.xml"))
						.addURL(new URL(confpath, "Section.hbm.xml"))
						.addURL(new URL(confpath, "SectionStatusObject.hbm.xml"))
						.addURL(new URL(confpath, "Course.hbm.xml"))
						.addURL(new URL(confpath, "CourseStatusObject.hbm.xml"))
						.addURL(new URL(confpath, "SchedulerManager.hbm.xml"))
						.addURL(new URL(confpath, "Schedule.hbm.xml"))
						.addURL(new URL(confpath, "Semester.hbm.xml"))
						.addURL(new URL(confpath, "CrossListing.hbm.xml"))
						.buildSessionFactory();
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
