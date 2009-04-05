package edu.rpi.rocs.objectmodel;

/**
 * Superclass for objects which have major and minor revisions.
 * 
 * @author ewpatton
 *
 */
public class MajorMinorRevisionObject {
	/** The major and minor revisions for this object */
	private Integer majRevision;
	private Integer minRevision;
	
	/** Global current revision */
	static private Integer currentRevision=0;
	
	/**
	 * Get the current global revision number
	 * 
	 * @return Revision number
	 */
	static public Integer getCurrentRevision() {
		return currentRevision;
	}
	
	/**
	 * Sets the current revision
	 * 
	 * @param rev The new revision number
	 */
	static public void setCurrentRevision(int rev) {
		currentRevision = new Integer(rev);
	}
	
	/**
	 * Sets the current revision
	 * 
	 * @param rev The new revision number
	 */
	static public void setCurrentRevision(Integer rev) {
		currentRevision = rev;
	}
	
	/**
	 * Determines if this object is out of date given the current global 
	 * revision on a "major" parameter
	 * 
	 * @return true if out of date, false if not
	 */
	public boolean outOfDateMajor() {
		return majRevision != MajorMinorRevisionObject.currentRevision;
	}
	
	/**
	 * Determines if this object is out of date given the current global
	 * revision on any parameter
	 * 
	 * @return true if out of date, false if not
	 */
	public boolean outOfDateMinor() {
		return minRevision != MajorMinorRevisionObject.currentRevision;
	}
	
	/**
	 * Updates the major and minor revisions of this object
	 * 
	 */
	public void updateMajorRevision() {
		majRevision = currentRevision;
		minRevision = currentRevision;
	}
	
	/**
	 * Updates the minor revision of this object
	 * 
	 */
	public void updateMinorRevision() {
		minRevision = currentRevision;
	}
	
	/**
	 * Sets the major revision. ONLY use this if you need to override the update methods.
	 * 
	 * @see updateMajorRevision
	 * @param rev The revision number to use as the major revision.
	 */
	public void setMajorRevision(Integer rev) {
		majRevision = rev;
	}
	
	/**
	 * Sets the minor revision. ONLY use this if you need to override the update methods.
	 * 
	 * @see updateMinorRevision
	 * @param rev The revision number to use as the minor revision.
	 */
	public void setMinorRevision(Integer rev) {
		minRevision = rev;
	}
	
	/**
	 * Gets the major revision of this object.
	 * 
	 * @return The major revision
	 */
	public Integer getMajorRevision() {
		return majRevision;
	}
	
	/**
	 * Gets the minor revision of this object.
	 * 
	 * @return The minor revision
	 */
	public Integer getMinorRevision() {
		return minRevision;
	}
}
