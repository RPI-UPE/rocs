package edu.rpi.rocs.client;

public class ImageManager {
	public class Image {
		public static final String CollapsedArrow="collapsed.png";
		public static final String ExpandedArrow="expanded.png";
	}
	
	private static native String getBasePath()/*-{
		return $wnd.rocsContext;
	}-*/;
	
	public static String getPathForImage(String image) {
		return getBasePath()+"/images/"+image;
	}
}
