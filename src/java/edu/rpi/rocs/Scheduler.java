package edu.rpi.rocs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.PortletConfig;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

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
	
	public static class Document {
		public String path;
		public String changeTime;
	}
	
	/**
	 * Base path to where CourseDB XML files are stored
	 */
	public static String xmlPath;
	/**
	 * List of XML documents to be loaded by the scheduler
	 */
	public static ArrayList<Document> documents=new ArrayList<Document>();
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
		/**
		 * Parses an Apache directory listing for XML files to be parsed for Course databases.
		 * 
		 * @param html Contents of an HTML file as a String
		 * @return A list of XML filenames which may contain CourseDBs.
		 */
		public ArrayList<Document> parseHTML(String html) {
			ArrayList<Document> results=new ArrayList<Document>();
			Pattern p = Pattern.compile("[^h]*(href=\"([^\"]*)\")>[^<]*</a>\\s*([^\\s]*)\\s([^\\s]*)|h[^r]", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
			Matcher m = p.matcher(html);
			int found=0;
			while(m.find()) {
				found++;
				String path = m.group(1);
				String date = m.group(3)+" "+m.group(4);
				//System.out.println("path = "+path);
				//System.out.println("date = "+date);
				if(path!=null) {
					path = path.replaceAll("\"", "");
					path = path.replace("href=", "");
					path = path.replace("HREF=", "");
					if(path.endsWith(".xml")) {
						Document d = new Document();
						d.path = path;
						d.changeTime = date;
						results.add(d);
					}
				}
			}
			//System.out.println("Found "+found+" semester files.");
			return results;
		}
		
		/**
		 * Requests the contents of a URL and passes it to ParseHTML to obtain a
		 * set of XML files that are passed to the SemesterParser.
		 * 
		 */
		public void run() {
			System.out.println("Looking for XML files...");
			try {
				URL url = new URL(xmlPath);
				BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
				String data="";
				String inputLine;
				while((inputLine = in.readLine())!= null) {
					data += inputLine;
				}
				in.close();
				
				Scheduler.documents = parseHTML(data);
				for(Document file : Scheduler.documents) {
					long start = System.currentTimeMillis();
					String newPath = Scheduler.xmlPath + file.path;
					SemesterParser.parse(newPath, file.changeTime);
					System.out.println("Parsed "+file.path+" in "+(System.currentTimeMillis()-start)+" ms");
				}
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private boolean initialized=false;
	
	public boolean isInitialized() {
		return initialized;
	}
	
	PortletConfig theConfig=null;
	
	public void initialize() {
		URL temp=null;
		try {
			temp = theConfig.getPortletContext().getResource("/xml/hibernate.cfg.xml");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(temp==null) System.out.println("Unable to find hibernate.cfg.xml");
		else {
			System.out.println("Path to config: "+temp);
		
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
		out.println("<script language=\"javascript\">var rocsUserName=\""+userName.hashCode()+"\";\nvar rocsContext=\""+aRequest.getContextPath()+"\";\nwindow.rocsContext=rocsContext;</script>");
		out.println("<script language=\"javascript\" src=\"" + aRequest.getContextPath() + "/rocs.gwt/rocs.gwt.nocache.js\"></script>");
		out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"" + aRequest.getContextPath() + "/css/rocs.css\"/>");
		out.println("<div id=\"rocs_PORTLET_rocs_root_view\" class=\"rocs-style\">");
		out.println("<div style=\"background-color: red; width:100%; text-align: center;\">This is beta software. If you experience problems please contact <a href=\"mailto:pattoe@rpi.edu\">Evan Patton</a> with details.</div>");
		out.println("<div style=\"background-color: yellow; width:100%; text-align: center;\">If you have problems with course content (e.g. two required courses conflict),<br/> please contact <a href=\"conrom@rpi.edu\">Michael Conroy</a> in the Registrar's Office with details.</div>");
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
		// TODO Auto-generated method stub
		return userInfoMap.get(Integer.valueOf(userid));
	}
	
	/**
	 * Removes the refresh timer when the Scheduler is destroyed.
	 */
	protected void finalize() throws Throwable {
		semesterRefreshTimer.cancel();
		super.finalize();
	}
	
	/**
	 * Destroys the Scheduler and stops the scheduler refresh timer for when the portlet is undeployed.
	 */
	public void destroy() {
		semesterRefreshTimer.cancel();
		super.destroy();
	}
}
