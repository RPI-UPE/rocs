package edu.rpi.rocs;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.PortletConfig;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.log4j.helpers.Loader;
import org.apache.log4j.xml.DOMConfigurator;
import org.mortbay.log.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.rpi.rocs.server.hibernate.util.HibernateUtil;
import edu.rpi.rocs.server.objectmodel.SemesterDB;
import edu.rpi.rocs.server.objectmodel.SemesterParser;

/**
 * The main Scheduler interface, an instance of JSR-168 Porlet.
 * 
 * @author ewpatton
 *
 */
public class Scheduler extends GenericPortlet {
	
	/**
	 * Represents an XML Document stored on remote server.
	 * @author ewpatton
	 *
	 */
	public static class Document {
		/**
		 * URL path of the document.
		 */
		public String path;
		/**
		 * Date modified as reported by Apache server
		 */
		public String changeTime;
	}
	
	final Logger log = LoggerFactory.getLogger(Scheduler.class);
		
	/**
	 * Base path to where CourseDB XML files are stored
	 */
	public static String xmlPath = null;
	/**
	 * List of XML documents to be loaded by the scheduler
	 */
	public static List<Document> documents=new ArrayList<Document>();
	/**
	 * The current instance of the Scheduler
	 */
	private static Scheduler theInstance;
	/**
	 * A map from a UID to a UserInfo dictionary
	 */
	private Map<Integer, Map<?, ?>> userInfoMap=new TreeMap<Integer, Map<?, ?>>();
	/**
	 * The timer which reads the databases at an interval specified by edu.rpi.rocs.Scheduler.refreshInterval
	 */
	private Timer semesterRefreshTimer;
	
	/**
	 * A helper class which executes at each timer interval specified by Scheduler.semesterRefreshTimer
	 * 
	 * @author ewpatton
	 *
	 */
	private class ParseXMLFilesTask extends TimerTask {
		private final Logger log = LoggerFactory.getLogger(ParseXMLFilesTask.class);
		
		protected String lastModifiedDate(final URL path) throws IOException {
			log.info("Checking existence of "+path);
			final SimpleDateFormat format = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z");

			final HttpURLConnection conn = (HttpURLConnection)path.openConnection();
			conn.setRequestMethod("HEAD");
			conn.connect();
			if(conn.getResponseCode() >= 400) {
				log.info("File '"+path+"' does not exist");
				return null;
			}
			final long lastModifiedMS = conn.getLastModified();
			
			final Date date = new Date();
			date.setTime(lastModifiedMS);
			log.info("File exists and was last modified "+format.format(date));
			return format.format(date);
		}
		
		protected Document generateDocument(final URL baseDir, final Calendar date) {
			final DateFormat formatter = new SimpleDateFormat("yyyyMM'.xml'");
			String lastModified = null;
			URL targetUrl = null;
			try {
				targetUrl = new URL(baseDir, formatter.format(date.getTime()));
				lastModified = lastModifiedDate(targetUrl);
				if(lastModified == null) {
					return null;
				}
				Document doc = new Document();
				doc.path = targetUrl.toString();
				doc.changeTime = lastModified;
				return doc;
			}
			catch(IOException e) {
				log.warn("Unable to load XML file "+targetUrl+" from server", e);
			}
			return null;
		}
		
		/**
		 * Gets a list of available semester documents by polling SIS
		 * @return
		 */
		protected List<Document> getAvailableSemesters(final URL baseDir) {
			List<Document> result = new ArrayList<Document>();
			Calendar now = GregorianCalendar.getInstance();
			int month = (int)(now.get(Calendar.MONTH)/4);
			now.set(Calendar.DAY_OF_MONTH, 1);
			now.set(Calendar.MONTH, month*4);
			
			Document d;
			d = generateDocument(baseDir, now);
			if(d != null) {
				result.add(d);
			}
			
			now.add(Calendar.MONTH, 4);
			d = generateDocument(baseDir, now);
			if(d != null) {
				result.add(d);
			}
			
			now.add(Calendar.MONTH, 4);
			d = generateDocument(baseDir, now);
			if(d != null) {
				result.add(d);
			}
			return result;
		}
		
		/**
		 * Requests the contents of a URL and passes it to ParseHTML to obtain a
		 * set of XML files that are passed to the SemesterParser.
		 * 
		 */
		public void run() {
			Scheduler.getInstance().getLogger().info("Looking for XML files...");
			try {
				final URL url = new URL(xmlPath);
				Scheduler.documents = getAvailableSemesters(url);
				for(Document file : Scheduler.documents) {
					long start = System.currentTimeMillis();
					SemesterParser.parse(file.path, file.changeTime);
					log.info("Parsed "+file.path+" in "+(System.currentTimeMillis()-start)+" ms");
				}
				System.gc();
			}
			catch(MalformedURLException e) {
				Log.warn("Unable to process path to XML documents: "+xmlPath, e);
			}
		}
	}
	
	/**
	 * Stores initialization state of the porlet (mostly for deployment purposes).
	 */
	private boolean initialized=false;
	
	/**
	 * Returns whether or not the portlet has been initialized
	 * @return boolean, true if initialized, false otherwise
	 */
	public boolean isInitialized() {
		return initialized;
	}
	
	/**
	 * Stored portlet configuration information in the event first initialization fails.
	 */
	PortletConfig theConfig=null;
	
	public Logger getLogger() {
		return log;
	}
	
	/**
	 * Initializes the portlet. Loads the hibernate configuration files and restores any data stored in the database.
	 */
	public void initialize() {
		URL temp=null;
		temp = Loader.getResource("log4j.xml");
		DOMConfigurator.configure(temp);
		try {
			temp = theConfig.getPortletContext().getResource("/xml/hibernate.cfg.xml");
		}
		catch (MalformedURLException e) {
			log.error("Malformed URL attempting to locate hibernate configuration", e);
		}
		if(temp == null) {
			log.error("Could not find hibernate configuration");
		}
		else {
			log.debug("Path to config: "+temp);
		
			HibernateUtil.init(temp);
			theInstance = this;
			SemesterDB.restoreSemesters();
			xmlPath = theConfig.getInitParameter("edu.rpi.rocs.Scheduler.xmlPath");
			long interval = 1000*Long.parseLong(theConfig.getInitParameter("edu.rpi.rocs.Scheduler.refreshInterval"));
			TimerTask taskPerformer = new ParseXMLFilesTask();
			semesterRefreshTimer = new Timer();
			semesterRefreshTimer.scheduleAtFixedRate(taskPerformer, 0, interval);
			initialized=true;
		}
	}
	
	/**
	 * Initialization function called by the Portal when it deploys an instance of ROCS
	 * 
	 * @param config The portlet configuration constructed from options in portlet.xml and web.xml 
	 * @throws PortletException
	 */
	public void init(PortletConfig config) throws PortletException {
		super.init(config);
		theConfig = config;
		initialize();
	}
		
	/**
	 * Processes an action generated by the user.
	 * 
	 * @param aRequest The ActionRequest object from JSR-168 Portal
	 * @param aResponse The ActionResponse object describing render parameters
	 * @throws PortletException
	 */
	public void processAction(ActionRequest aRequest, ActionResponse aResponse)
		throws PortletException {
		/*
		try {
			CASPortletUtils.establishSession(aRequest, this.ptvFactory);
		}
		catch(CASAuthenticationException e) {
			throw new PortletException(e);
		}
		*/
	}
	
	/**
	 * Processes a view render request.
	 * 
	 * @param aRequest The RenderRequest object from JSR-168 Portal
	 * @param aResponse The RenderResponse object describing the rendered content
	 * @throws PortletException
	 * @throws IOException
	 */
	public void doView(RenderRequest aRequest, RenderResponse aResponse)
		throws PortletException, IOException {
		if(!initialized) initialize();
		aResponse.setContentType("text/html");
		PrintWriter out = aResponse.getWriter();
		Map<?, ?> userinfo = (Map<?, ?>)aRequest.getAttribute(PortletRequest.USER_INFO);
		if(userinfo == null) {
			out.println("<p>You must be logged in to the portal to use ROCS.</p>");
			return;
		}
		String userName = (String)userinfo.get("user.login.id");
		userInfoMap.put(new Integer(userName.hashCode()), userinfo);
		if(theConfig.getInitParameter("edu.rpi.rocs.Scheduler.includejQuery").equals("true")) {
			out.println("<script language=\"javascript\" src=\"" + aRequest.getContextPath() + "/js/jquery-1.4.4.min.js\"></script>");
			out.println("<script language=\"javascript\" src=\"" + aRequest.getContextPath() + "/js/jquery-ui-1.8.6.custom.min.js\"></script>");
		}
		out.println("<script language=\"javascript\">var rocsUserName=\""+userName.hashCode()+"\";\nvar rocsContext=\""+aRequest.getContextPath()+"\";\nwindow.rocsContext=rocsContext;</script>");
		out.println("<script language=\"javascript\" src=\"" + aRequest.getContextPath() + "/rocs.gwt/rocs.gwt.nocache.js\"></script>");
		out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"" + aRequest.getContextPath() + "/css/rocs.css\"/>");
		out.println("<div id=\"rocs_PORTLET_rocs_root_view\" class=\"rocs-style\"><a name=\"rocs-main\"></a>");
		out.println("<div style=\"background-color: red; width:100%; text-align: center;\">" + 
				"This is beta software. If you experience problems please contact " +
				"<a href=\"mailto:UPE-ROCS-USER-L@lists.rpi.edu\">the ROCS dev team</a> with details.</div>");
		out.println("<div style=\"background-color: yellow; width:100%; text-align: center;\">" +
				"If you have problems with course content (e.g. two required courses conflict),<br/> please contact " +
				"<a href=\"mailto:registrar@rpi.edu\">the Registrar's office</a> with details.</div>");
		out.println("</div>");
	}
	
	/**
	 * Processes an edit render request.
	 * 
	 * @param aRequest The RenderRequest object from JSR-168 Portal
	 * @param aResponse The RenderResponse object describing the rendered content
	 * @throws PortletException
	 * @throws IOException
	 */
	public void doEdit(RenderRequest aRequest, RenderResponse aResponse)
		throws PortletException, IOException {
		
	}
	
	/**
	 * Processes a help render request.
	 * 
	 * @param aRequest The RenderRequest object from JSR-168 Portal
	 * @param aResponse The RenderResponse object describing the rendered content
	 * @throws PortletException
	 * @throws IOException
	 */
	public void doHelp(RenderRequest aRequest, RenderResponse aResponse)
		throws PortletException, IOException {
		
	}
	
	/**
	 * Gets the singleton instance of the Scheduler generated by the portal.
	 * 
	 * @return The Scheduler instance
	 */
	public static Scheduler getInstance() {
		return theInstance;
	}

	/**
	 * Gets a UserInfo dictionary for a given user ID
	 * 
	 * @param userid A user id passed to the client
	 * @return A map containing the user info as defined by JSR-168
	 */
	public Map<?, ?> getUserInfo(String userid) {
		return userInfoMap.get(Integer.valueOf(userid));
	}
	
	/**
	 * Destroys the Scheduler and stops the scheduler refresh timer for when the portlet is undeployed.
	 */
	public void destroy() {
		log.warn("Destroying Scheduler");
		semesterRefreshTimer.cancel();
		semesterRefreshTimer = null;
		super.destroy();
	}
}
