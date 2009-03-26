package edu.rpi.rocs;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import com.liferay.portal.util.PortalUtil;

/**
 * The main Scheduler interface, an instance of JSR-168 Porlet.
 * 
 * @author ewpatton
 *
 */
public class Scheduler extends GenericPortlet {
	
	/**
	 * Processes an action generated by the user.
	 * 
	 * @param aRequest The ActionRequest object from JSR-168 Portal
	 * @param aResponse The ActionResponse object describing render parameters 
	 */
	public void processAction(ActionRequest aRequest, ActionResponse aResponse) {
		
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
		System.out.println("Entering Scheduler.doView");
		aResponse.setContentType("text/html");
		PrintWriter out = aResponse.getWriter();
		out.println("<h1>Rensselaer Open Course Scheduler</h1>");
		out.println("<p>This portlet demonstrates how to delegate to an" + 
				" existing JSR-168 portlet via a HandlerAdapter</p>");
		out.println("<p>Portlet name: " + this.getPortletName() + "</p>");
		out.println("<p>Init Parameters: </p><ul>");
		for(Enumeration e = this.getInitParameterNames();e.hasMoreElements();) {
			String name = (String)e.nextElement();
			out.println("<li>" + name + " = " + this.getInitParameter(name) + "</li>");
		}
		out.println("</ul>");
		out.print("<p>Your username: ");
		String name=PortalUtil.getUserName(PortalUtil.getUserId(aRequest), "");
		if(name.equalsIgnoreCase("") || Character.getNumericValue(name.charAt(0)) == -1)
			name = "&lt;anonymous&gt;";
		out.print(name);
		
		out.println("<script language='javascript' src='" + aRequest.getContextPath() + "/edu.rpi.rocs.Scheduler.nocache.js'></script>");
		out.println("GWT test:");
		out.println("<div id='uid'></div>");
		out.print("</p>");
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
}
