package edu.rpi.rocs.client.objectmodel;


import java.io.Serializable;

/**
 * Superclass for objects which have major and minor revisions.
 * 
 * @author ewpatton
 *
 */
public class MajorMinorRevisionObject implements Serializable {
	/**
	 * UID for Serializable interface
	 */
	private static final long serialVersionUID = -8119493118175868900L;
	
	private boolean deleted=false;
	
	public boolean wasDeleted() {
		return deleted;
	}
	
	public void delete() {
		deleted = true;
	}
	
	public void undelete() {
		deleted = false;
	}
	
	/** The major and minor revisions for this object */
	private Long majRevision=null;
	private Long minRevision=null;
	
	/** Global current revision */
	static private long currentRevision=0;
	
	/**
	 * Get the current global revision number
	 * 
	 * @return Revision number
	 */
	static public Long getCurrentRevision() {
		return currentRevision;
	}
	
	/**
	 * Sets the current revision
	 * 
	 * @param rev The new revision number
	 */
	static public void setCurrentRevision(long rev) {
		currentRevision = new Long(rev);
	}
	
	/**
	 * Sets the current revision
	 * 
	 * @param rev The new revision number
	 */
	static public void setCurrentRevision(Long rev) {
		currentRevision = rev;
	}
	
	/**
	 * Determines if this object is out of date given the current global 
	 * revision on a "major" parameter
	 * 
	 * @return true if out of date, false if not
	 */
	public boolean outOfDateMajor() {
		if(majRevision==null) return true;
		return majRevision.longValue() != MajorMinorRevisionObject.currentRevision;
	}
	
	/**
	 * Determines if this object is out of date given the current global
	 * revision on any parameter
	 * 
	 * @return true if out of date, false if not
	 */
	public boolean outOfDateMinor() {
		if(minRevision==null) return true;
		return minRevision.longValue() != MajorMinorRevisionObject.currentRevision;
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
	 * @see #updateMajorRevision
	 * @param rev The revision number to use as the major revision.
	 */
	public void setMajorRevision(Long rev) {
		majRevision = rev;
	}
	
	/**
	 * Sets the minor revision. ONLY use this if you need to override the update methods.
	 * 
	 * @see #updateMinorRevision
	 * @param rev The revision number to use as the minor revision.
	 */
	public void setMinorRevision(Long rev) {
		minRevision = rev;
	}
	
	/**
	 * Gets the major revision of this object.
	 * 
	 * @return The major revision
	 */
	public Long getMajorRevision() {
		return majRevision;
	}
	
	/**
	 * Gets the minor revision of this object.
	 * 
	 * @return The minor revision
	 */
	public Long getMinorRevision() {
		return minRevision;
	}
	
	public Long getLastRevision() {
		return majRevision > minRevision ? majRevision : minRevision;
	}
}
