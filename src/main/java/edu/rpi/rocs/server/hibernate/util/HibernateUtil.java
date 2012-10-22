package edu.rpi.rocs.server.hibernate.util;

import java.net.MalformedURLException;
import java.net.URL;

import net.sf.ehcache.CacheManager;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HibernateUtil {
	private static SessionFactory sessionFactory = null;
	private static final Logger log = LoggerFactory.getLogger(HibernateUtil.class);
	
	public static void init(final URL confpath) {
		if(sessionFactory != null) {
			clearSessionFactory();
		}
		sessionFactory = buildSessionFactory(confpath);
	}
	
	private static SessionFactory buildSessionFactory(final URL confpath) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
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
		catch(ClassNotFoundException e) {
			log.error("Unable to find MySQL JDBC driver", e);
			throw new ExceptionInInitializerError(e);
		}
		catch(MalformedURLException e) {
			log.error("Malformed URL attempting to locate partial hibernate mapping", e);
			throw new ExceptionInInitializerError(e);
		}
	}
	
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	public static void clearSessionFactory() {
		sessionFactory.close();
		CacheManager manager = CacheManager.getCacheManager("default");
		if(manager != null) {
			manager.shutdown();
		}
		sessionFactory = null;
	}
}
