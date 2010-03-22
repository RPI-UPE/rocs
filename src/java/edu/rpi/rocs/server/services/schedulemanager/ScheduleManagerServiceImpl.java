package edu.rpi.rocs.server.services.schedulemanager;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import edu.rpi.rocs.client.objectmodel.Schedule;
import edu.rpi.rocs.client.objectmodel.SchedulerManager;
import edu.rpi.rocs.client.objectmodel.Section;
import edu.rpi.rocs.client.objectmodel.Semester;
import edu.rpi.rocs.server.hibernate.util.HibernateUtil;
import edu.rpi.rocs.server.objectmodel.SchedulerManagerWriter;
import edu.rpi.rocs.server.objectmodel.SemesterDB;

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
	
	public SchedulerManager loadSchedule(String user, String name, int semesterid) {
		try {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			
			SchedulerManager mgr = (SchedulerManager)session.createQuery(" from SchedulerManager where UserId = '"+
					user+"' and Name = '"+name+"' and SemesterId="+semesterid).uniqueResult();
			Schedule s = mgr.getCurrentSchedule();
			ArrayList<Section> sections = s.getSections();
			ArrayList<Section> newsections = new ArrayList<Section>();
			Semester semester = SemesterDB.getInstance(semesterid);
			for(Section section : sections) {
				Section newsection = semester.getSectionByCRN(section.getCrn());
				if(newsection != null)
					newsections.add(newsection);
			}
			
			session.getTransaction().rollback();
			
			s.setSections(newsections);
			return mgr;
		}
		catch(Throwable ex) {
			System.out.print("Caught exception: ");
			ex.printStackTrace();
		}
		return null;
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
		
		session.getTransaction().rollback();
		return temp;
	}

}
