package test.objectModel;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.rpi.rocs.client.objectmodel.*;
import junit.framework.TestCase;
import edu.rpi.rocs.server.hibernate.util.HibernateUtil;
import edu.rpi.rocs.server.objectmodel.*;

/**
 * 
 * @author sloan.w
 * @author ewpatton
 *
 */
public class courseDBTest extends TestCase{
	
	Semester testObject;
	final String xmlLoc = "src/test/resources/sample.xml";
	
	@Before
	public void setUp(){
		final SimpleDateFormat format = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z");
		final String lastModified = format.format(new Date());
		try {
			HibernateUtil.init(URI.create("file://"+System.getProperty("user.dir")+"/src/main/webapp/xml/hibernate.cfg.xml").toURL());
			SemesterParser.parse("file://"+System.getProperty("user.dir")+"/"+xmlLoc, lastModified);
			testObject = SemesterDB.getCurrentSemester();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testExistance(){
		try{
			assertFalse(testObject == null);
		} catch(NullPointerException e){
			fail("object not created" + e.toString());
		};
	}
	
	@Test
	public void testSemesters(){
		assertTrue("getSemesterDesc should be : " + "DTD Sample" +
				" actual : " + testObject.getSemesterDesc(),(testObject.getSemesterDesc()).equals("DTD Sample"));

		//TODO test setter functions (setSemesterDesc etc
		assertTrue("getSemesterId should be : " + "0" + 
				" actual : " + testObject.getSemesterId(), testObject.getSemesterId() == 0);
		
		assertTrue("getTimeStamp should be : " + "1238421894" +
				" actual : " + testObject.getTimeStamp(), testObject.getTimeStamp() == 1238421894);

	}
	
	@Test
	public void testTimeStamp(){
		try{
			assertFalse( testObject.getTimeStamp() == 0 );
		} catch(NullPointerException e){
			fail("null pointer exception on getTimeStamp : " + e.toString());
		};
		
		assertTrue("getTimeStamp should be : " + "1238421894" +
				" actual : " + testObject.getTimeStamp(), testObject.getTimeStamp() == 1238421894);
	}
	
	@After
	public void tearDown() {
		try {
			Semester s = SemesterDB.getInstance(0);
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			Transaction tx = session.beginTransaction();
			session.delete(s);
			tx.commit();
			SemesterDB.reset();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
