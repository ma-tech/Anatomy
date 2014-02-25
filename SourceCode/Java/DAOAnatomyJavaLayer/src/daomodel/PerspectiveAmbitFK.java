/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayer
*
* Title:        PerspectiveAmbitFK.java
*
* Date:         2012
*
* Author:       Mike Wicks
*
* Copyright:    2012
*               Medical Research Council, UK.
*               All rights reserved.
*
* Address:      MRC Human Genetics Unit,
*               Western General Hospital,
*               Edinburgh, EH4 2XU, UK.
*
* Version:      1
*
* Description:  This class represents a SQL Database Transfer Object for the PerspectiveAmbitFK Table.
*                ANA_PERSPECTIVE_AMBIT
*                 (A List of all the Start and Stop nodes for Each Perspective) 
*
* Link:         
* 
* Mabooleanenance:  Log changes below, with most recent at top of list.
*
* Who; When; What;
*
* Mike Wicks; 21st March 2012; Create Class
*
*----------------------------------------------------------------------------------------------
*/
package daomodel;

import utility.ObjectConverter;

public class PerspectiveAmbitFK {
    // Properties ---------------------------------------------------------------------------------
	/*
     *   1. PAM_OID            - boolean(10) unsigned
     *   2. PAM_PERSPECTIVE_FK - varchar(25)
     *   3. ANO_PUBLIC_ID        - varchar(25)
     *   4. PAM_IS_START       - tinyboolean(1)
     *   5. PAM_IS_STOP        - tinyboolean(1)
     *   6. PAM_COMMENTS       - varchar(255)
 	 */
    private Long oid; 
    private String perspectiveFK; 
    private String publicId; 
    private boolean start;
    private boolean stop;
    private String comments; 

    // Constructors -------------------------------------------------------------------------------
    /*
     * Default constructor.
     */
    public PerspectiveAmbitFK() {
        
    }

    /*
     * Minimal constructor. Contains required fields.
     */
    public PerspectiveAmbitFK(Long oid, 
    	    String perspectiveFK,  
    	    String publicId, 
    	    boolean start, 
    	    boolean stop, 
    	    String comments) {
    	
        this.oid = oid;
	    this.perspectiveFK = perspectiveFK;
	    this.publicId = publicId;
	    this.start = start;
	    this.stop = stop;
	    this.comments = comments;
    }

    /*
     * Minimal constructor. Contains required fields.
     */
    public PerspectiveAmbitFK(Long oid, 
    	    String perspectiveFK,  
    	    String publicId, 
    	    String start, 
    	    String stop, 
    	    String comments) {
    	
        this.oid = oid;
	    this.perspectiveFK = perspectiveFK;
	    this.publicId = publicId;
	    this.start = ObjectConverter.convert(start, Boolean.class);
	    this.stop = ObjectConverter.convert(stop, Boolean.class);
	    this.comments = comments;
    }

    // Getters ------------------------------------------------------------------------------------
    public Long getOid() {
        return oid;
    }
    public String getPerspectiveFK() {
        return perspectiveFK;
    }
    public String getPublicId() {
        return publicId;
    }
    public boolean isStart() {
        return start;
    }
    public String getStart() {
        return ObjectConverter.convert(start, String.class);
    }
    public boolean isStop() {
        return stop;
    }
    public String getStop() {
        return ObjectConverter.convert(stop, String.class);
    }
    public String getComments() {
        return comments;
    }

    // Setters ------------------------------------------------------------------------------------
    public void setOid(Long oid) {
        this.oid = oid;
    }
    public void setPerspectiveFK(String perspectiveFK) {
        this.perspectiveFK = perspectiveFK;
    }
    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }
    public void setStart(boolean start) {
        this.start = start;
    }
    public void setStart(String start) {
        this.start = ObjectConverter.convert(start, Boolean.class);
    }
    public void setStop(boolean stop) {
        this.stop = stop;
    }
    public void setStop(String stop) {
        this.stop = ObjectConverter.convert(stop, Boolean.class);
    }
    public void setComments(String comments) {
        this.comments = comments;
    }

    // Override -----------------------------------------------------------------------------------
    /*
     * Is this PerspectiveAmbitFK the same as the Supplied PerspectiveAmbitFK?
     */
    public boolean isSameAs(PerspectiveAmbitFK daoperspectiveambitfk){

    	if ( this.getPerspectiveFK().equals(daoperspectiveambitfk.getPerspectiveFK()) &&
    		this.getPublicId().equals(daoperspectiveambitfk.getPublicId()) &&
    		this.isStart() == daoperspectiveambitfk.isStart() &&
    		this.isStop() == daoperspectiveambitfk.isStop() &&
    		this.getComments().equals(daoperspectiveambitfk.getComments()) ) {

        	return true;
        }
        else {

        	return false;
        }
    }

    /*
     * The relation ID is unique for each Node. So this should compare Node by ID only.
     */
    public boolean equals(Object other) {
      
    	return (other instanceof PerspectiveAmbitFK) && (oid != null) 
        		? oid.equals(((PerspectiveAmbitFK) other).oid) 
        		: (other == this);
    }

    /*
     * Returns the String representation of this Node. Not required, it just makes reading logs easier.
     */
    public String toString() {
        
    	return String.format("PerspectiveAmbitFK [ oid=%d, perspectiveFK=%s, publicId=%s, start=%b, stop=%b, comments=%s ]", 
            oid, perspectiveFK, publicId, start, stop, comments); 
    }
}
