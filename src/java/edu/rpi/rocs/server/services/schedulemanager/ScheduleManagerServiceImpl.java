package edu.rpi.rocs.server.services.schedulemanager;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import edu.rpi.rocs.client.objectmodel.SchedulerManager;
import edu.rpi.rocs.server.hibernate.util.HibernateUtil;
import edu.rpi.rocs.server.objectmodel.SchedulerManagerWriter;

public class ScheduleManagerServiceImpl extends RemoteServiceServlet implements
		edu.rpi.rocs.client.services.schedulemanager.ScheduleManagerService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 943641426558410281L;
	
	public void saveSchedule(String name, SchedulerManager state) {
		try {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			
			state.setName(name);
			state.setDbid(null);
			SchedulerManagerWriter smw = new SchedulerManagerWriter();
			smw.setSession(session);
			smw.visit(state);
	
			session.getTransaction().commit();
		}
		catch(Throwable ex) {
			System.out.print("Caught exception: ");
			ex.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public List<String> getScheduleList(String user) {
		ArrayList<String> temp=new ArrayList<String>();
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		List<SchedulerManager> managers = session.createQuery(" from SchedulerManager where UserId = '"+user+"'").list();
		for(SchedulerManager s : managers) {
			temp.add(s.getName());
		}
		
		session.getTransaction().commit();
		return temp;
	}

}
