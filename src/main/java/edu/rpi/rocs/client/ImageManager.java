package edu.rpi.rocs.client;

/** 
 * The ImageManager class acts as a facade by hiding the complexity of the 
 * JSR-168 portlet specification from GWT widgets.
 * 
 * @author ewpatton
 *
 */
public class ImageManager {
	/**
	 * The Image class acts like an enumeration of images present
	 * in the WAR file.
	 * @author ewpatton
	 *
	 */
	public class Image {
		public static final String CollapsedArrow="collapsed.png";
		public static final String ExpandedArrow="expanded.png";
	}
	
	/**
	 * A JSNI method that retrieves the value of the path
	 * that represents the ROCS context in the JSR-168 portlet.
	 * @return a String relative to the server URI.
	 */
	private static native String getBasePath()/*-{
		return $wnd.rocsContext;
	}-*/;
	/**
	 * Computes the path of an image in the WAR file using the basepath
	 * of the ROCS portlet.
	 * @param image the image for which to computer the path.
	 * @return the absolute URL to the image.
	 */
	public static String getPathForImage(String image) {
		return getBasePath()+"/images/"+image;
	}
}
