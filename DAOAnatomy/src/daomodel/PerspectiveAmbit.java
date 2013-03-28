/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomy
*
* Title:        PerspectiveAmbit.java
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
* Version: 1
*
* Description:  This class represents a SQL Database Transfer Object for the PerspectiveAmbit Table.
*                ANA_PERSPECTIVE_AMBIT
*                 A List of all the Start and Stop nodes for Each Perspective 
*
* Link:         http://balusc.blogspot.com/2008/07/dao-tutorial-data-layer.html
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

public class PerspectiveAmbit {
    // Properties ---------------------------------------------------------------------------------
	/*
     *   1. PAM_OID            - boolean(10) unsigned
     *   2. PAM_PERSPECTIVE_FK - varchar(25)
     *   3. PAM_NODE_FK        - boolean(10) unsigned
     *   4. PAM_IS_START       - tinyboolean(1)
     *   5. PAM_IS_STOP        - tinyboolean(1)
     *   6. PAM_COMMENTS       - varchar(255)
 	 */
    private Long oid; 
    private String perspectiveFK; 
    private long nodeFK; 
    private boolean start;
    private boolean stop;
    private String comments; 

    // Constructors -------------------------------------------------------------------------------
    /*
     * Default constructor.
     */
    public PerspectiveAmbit() {
        // Always keep the default constructor alive in a Javabean class.
    }

    /*
     * Minimal constructor. Contains required fields.
     */
    public PerspectiveAmbit(Long oid, 
    	    String perspectiveFK,  
    	    long nodeFK, 
    	    boolean start, 
    	    boolean stop, 
    	    String comments) {
    	
        this.oid = oid;
	    this.perspectiveFK = perspectiveFK;
	    this.nodeFK = nodeFK;
	    this.start = start;
	    this.stop = stop;
	    this.comments = comments;
    }

    // Getters ------------------------------------------------------------------------------------
    public Long getOid() {
        return oid;
    }
    public String getPerspectiveFK() {
        return perspectiveFK;
    }
    public long getNodeFK() {
        return nodeFK;
    }
    public boolean isStart() {
        return start;
    }
    public boolean isStop() {
        return stop;
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
    public void setNodeFK(long nodeFK) {
        this.nodeFK = nodeFK;
    }
    public void setStart(boolean start) {
        this.start = start;
    }
    public void setStop(boolean stop) {
        this.stop = stop;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }

    // Override -----------------------------------------------------------------------------------
    /*
     * Is this PerspectiveAmbit the same as the Supplied PerspectiveAmbit?
     */
    public boolean isSameAs(PerspectiveAmbit daoperspectiveambit){

    	if (this.getPerspectiveFK().equals(daoperspectiveambit.getPerspectiveFK()) &&
    		this.getNodeFK() == daoperspectiveambit.getNodeFK() &&
    		this.isStart() == daoperspectiveambit.isStart() &&
    		this.isStop() == daoperspectiveambit.isStop() &&
    		this.getComments().equals(daoperspectiveambit.getComments()) ) {

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
      
    	return (other instanceof PerspectiveAmbit) && (oid != null) 
        		? oid.equals(((PerspectiveAmbit) other).oid) 
        		: (other == this);
    }

    /*
     * Returns the String representation of this Node. Not required, it just pleases reading logs.
     */
    public String toString() {
        
    	return String.format("PerspectiveAmbit [ oid=%d, perspectiveFK=%s, nodeFK=%s, start=%b, stop=%b, comments=%s ]", 
            oid, perspectiveFK, nodeFK, start, stop, comments); 
    }
}
