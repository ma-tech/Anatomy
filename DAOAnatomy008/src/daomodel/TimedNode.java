/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomy008
*
* Title:        TimedNode.java
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
* Description:  This class represents a SQL Database Transfer Object for the Timed Node Table.
*                ANA_TIMED_NODE
*
* Link:         http://balusc.blogspot.com/2008/07/dao-tutorial-data-layer.html
* 
* Maintenance:  Log changes below, with most recent at top of list.
*
* Who; When; What;
*
* Mike Wicks; 21st March 2012; Create Class
*
*----------------------------------------------------------------------------------------------
*/

package daomodel;

public class TimedNode {
    // Properties ---------------------------------------------------------------------------------
	/*
     *  Columns:
     *   1. ATN_OID               - int(10) unsigned 
     *   2. ATN_NODE_FK           - int(10) unsigned 
     *   3. ATN_STAGE_FK          - int(10) unsigned 
     *   4. ATN_STAGE_MODIFIER_FK - varchar(20)      
     *   5. ATN_PUBLIC_ID         - varchar(20)      
	 */
    private Long oid; 
    private Long nodeFK; 
    private Long stageFK; 
    private String stageModifierFK;
    private String publicId;

    // Constructors -------------------------------------------------------------------------------
    /*
     * Default constructor.
     */
    public TimedNode() {
        // Always keep the default constructor alive in a Javabean class.
    }

    /*
     * Minimal constructor. Contains required fields.
     */
    public TimedNode(Long oid, 
    		Long nodeFK, 
    		Long stageFK,
    		String stageModifierFK,
    		String publicId) {
    	
        this.oid = oid;
        this.nodeFK = nodeFK; 
        this.stageFK = stageFK; 
        this.stageModifierFK = stageModifierFK;
        this.publicId = publicId;
    }

    /*
     * Full constructor. Contains required and optional fields.
     * 
     * The Full Constructor is the Minimal Constructor
     * 
     */

    // Getters ------------------------------------------------------------------------------------
    public Long getOid() {
        return oid;
    }
    public Long getNodeFK() {
        return nodeFK;
    }
    public Long getStageFK() {
        return stageFK;
    }
    public String getStageModifierFK() {
        return stageModifierFK;
    }
    public String getPublicId() {
        return publicId;
    }

    // Setters ------------------------------------------------------------------------------------
    public void setOid(Long oid) {
        this.oid = oid;
    }
    public void setNodeFK(Long nodeFK) {
        this.nodeFK = nodeFK;
    }
    public void setStageFK(Long stageFK) {
        this.stageFK = stageFK;
    }
    public void setStageModifierFK(String stageModifierFK) {
        this.stageModifierFK = stageModifierFK;
    }
    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }

    // Override -----------------------------------------------------------------------------------
    /*
     * The Timed Node OID is unique for each Timed Node.
     *  So this should compare Timed Node by OID only.
     */
    public boolean equals(Object other) {
        return (other instanceof TimedNode) && (oid != null) 
        		? oid.equals(((TimedNode) other).oid) 
        		: (other == this);
    }

    /*
     * Returns the String representation of this Timed Node. 
     *  Not required, it just makes reading logs easier
     */
    public String toString() {
        return String.format("TimedNode [ oid=%d, nodeFK=%d, stageFK=%d, stageModifierFK=%s, publicId=%s ]", 
            oid, nodeFK, stageFK, stageModifierFK, publicId);
    }
}
