package edu.rpi.rocs.server;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import edu.rpi.rocs.client.objectmodel.Course;
import edu.rpi.rocs.client.objectmodel.CrossListing;
import edu.rpi.rocs.client.objectmodel.Section;

public class Simulator {
	
	public static final double P_NEW_COURSE=0.05;
	public static final double P_DELETE_COURSE=0.05;
	public static final double P_NEW_SECTION=0.05;
	public static final double P_DELETE_SECTION=0.05;
	public static final double P_ADD_STUDENTS=0.20;
	public static final double P_REMOVE_STUDENTS=0.20;
	public static final double P_CLOSE_SECTION=0.10;
	public static final double P_CHANGE_PERIOD_TIME=0.05;
	public static final double P_CHANGE_PERIOD_DAY=0.05;
	public static final double P_PROFESSOR_CHANGE=0.05;
	public static final double P_CROSSLISTING=0.10;
	
	public static int COURSE_COUNTER=0;
	public static int PROFESSOR_COUNTER=0;
	public static final int INITIAL_COURSES=400;
	public static final int AVG_SECTIONS_PER_COURSE=3;
	
	private static ArrayList<Course> courses=new ArrayList<Course>();
	private static ArrayList<CrossListing> crosslistings=new ArrayList<CrossListing>();

	public static int seat_rnd() {
		return (int)(375.0*Math.random()+25);
	}
	
	/**
	 * This simulator is designed to test the update capabilities of
	 * ROCS by making random updates to an XML file. ROCS should be
	 * configured to pull this XML file and use it to update its
	 * internal state, then push those changes to any active clients.
	 * 
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		generateInitialConfig();
		generateXMLFile();
		while(true) {
			Thread.sleep(1000*300);
			updateConfiguration();
			generateXMLFile();
		}
	}

	private static void updateConfiguration() {
		for(int i=0;i<courses.size();i++) {
			
		}
	}

	private static void generateXMLFile() throws ParserConfigurationException, TransformerConfigurationException, Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		DOMImplementation impl = builder.getDOMImplementation();
		
		Document doc = impl.createDocument(null, null, null);
		Element el = doc.createElement("CourseDB");
		el.setAttribute("timestamp", Long.toString(System.currentTimeMillis()));
		el.setAttribute("semesternumber", "209909");
		el.setAttribute("semesterdesc", "Fall 2099");
		doc.appendChild(el);
		
		DOMSource domSource = new DOMSource(doc);
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		transformer.setOutputProperty(OutputKeys.METHOD, "xml");
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		FileOutputStream fo = new FileOutputStream("/var/www/rocs/209909.xml");
		OutputStreamWriter wr = new OutputStreamWriter(fo);
		StreamResult sr = new StreamResult(wr);
		transformer.transform(domSource,sr);
		fo.close();
	}

	private static void generateInitialConfig() {
		for(int i=0;i<INITIAL_COURSES;i++) {
			Course c = new Course();
			c.setCredmax(4);
			c.setCredmin(4);
			c.setDept(dept_rand());
			c.setGradetype("MOD");
			c.setName("Course "+COURSE_COUNTER++);
			c.setNum(num_rand());
			int sections = (int)Math.round(AVG_SECTIONS_PER_COURSE*Math.random()*2.0)+1;
			for(int j=0;j<sections;j++) {
				Section s = new Section();
				s.setCrn(crn_rand());
				s.setNumber(Integer.toString(j+1));
				s.setSeats(seat_rnd());
				s.setStudents((int)Math.round(s.getSeats()*Math.random()));
				int numPeriods = (int)Math.round(3.0*Math.random())+1;
				for(int k=0;k<numPeriods;k++) {
					
				}
				c.addSection(s);
			}
			courses.add(c);
		}
		for(int i=0;i<INITIAL_COURSES;i++) {
			if(Math.random()>P_CROSSLISTING) {
				if(crosslistings.size()==0) {
					
				}
			}
		}
	}
	
	private static int crn_rand() {
		return 10000+(int)Math.round(89999*Math.random());
	}

	private static String[] depts = {
		"ADMN",
		"ARCH",
		"ARTS",
		"ASTR",
		"BCBP",
		"BIOL",
		"BMED",
		"CHEM",
		"CHME",
		"CIVL",
		"COGS",
		"COMM",
		"COOP",
		"CSCI",
		"DSES",
		"ECON",
		"ECSE",
		"EMBA",
		"ENGR",
		"ENVE",
		"EPOW",
		"ERTH",
		"ESCI",
		"IENV",
		"IHSS",
		"ISCI",
		"ITEC",
		"LGHT",
		"LITR",
		"MANE",
		"MATH",
		"MATP",
		"MGMT",
		"MTLE",
		"PHIL",
		"PHYS",
		"PSYC",
		"STSH",
		"STSS",
		"USAF",
		"USAR",
		"USNA",
		"WRIT"
	};
	
	private static String dept_rand() {
		return depts[(int)(depts.length*Math.random())];
	}

	private static int num_rand() {
		int type=(int) Math.round(4.0*Math.random());
		switch(type) {
		case 0:
			type=1000;
			break;
		case 1:
			type=2000;
			break;
		case 3:
			type=4000;
			break;
		case 4:
			type=6000;
			break;
		}
		return type+(int)(999*Math.random());
	}

}
