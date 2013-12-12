/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomy
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
* Version:      1
*
* Description:  This class represents a SQL Database Transfer Object for the Timed Node Table.
*                ANA_TIMED_NODE
*
* Link:         
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
     *   1. ATN_OID               - int(10) unsigned 
     *   2. ATN_NODE_FK           - int(10) unsigned 
     *   3. ATN_STAGE_FK          - int(10) unsigned 
     *   4. ATN_STAGE_MODIFIER_FK - varchar(20)      
     *   5. ATN_PUBLIC_ID         - varchar(20)      
     *   6. ATN_DISPLAY_ID        - varchar(20)      
	 */
    private Long oid; 
    private long nodeFK; 
    private long stageFK; 
    private String stageModifierFK;
    private String publicId;
    private String displayId;

    // Constructors -------------------------------------------------------------------------------
    /*
     * Default constructor.
     */
    public TimedNode() {
        
    }

    /*
     * Minimal constructor. Contains required fields.
     */
    public TimedNode(Long oid, 
    		long nodeFK, 
    		long stageFK,
    		String stageModifierFK,
    		String publicId,
    		String displayId) {
    	
        this.oid = oid;
        this.nodeFK = nodeFK; 
        this.stageFK = stageFK; 
        this.stageModifierFK = stageModifierFK;
        this.publicId = publicId;
        this.displayId = displayId;
    }

    // Getters ------------------------------------------------------------------------------------
    public Long getOid() {
        return oid;
    }
    public long getNodeFK() {
        return nodeFK;
    }
    public long getStageFK() {
        return stageFK;
    }
    public String getStageModifierFK() {
        return stageModifierFK;
    }
    public String getPublicId() {
        return publicId;
    }
    public String getDisplayId() {
        return displayId;
    }

    // Setters ------------------------------------------------------------------------------------
    public void setOid(Long oid) {
        this.oid = oid;
    }
    public void setNodeFK(long nodeFK) {
        this.nodeFK = nodeFK;
    }
    public void setStageFK(long stageFK) {
        this.stageFK = stageFK;
    }
    public void setStageModifierFK(String stageModifierFK) {
        this.stageModifierFK = stageModifierFK;
    }
    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }
    public void setDisplayId(String displayId) {
        this.displayId = displayId;
    }

    // Helper -------------------------------------------------------------------------------------
    /*
     * Is this TimedNode the same as the Supplied TimedNode?
     */
    public boolean isSameAs(TimedNode daotimednode){

    	if (this.getNodeFK() == daotimednode.getNodeFK() &&
    		this.getStageFK() == daotimednode.getStageFK() &&
    		this.getStageModifierFK().equals(daotimednode.getStageModifierFK()) &&
    		this.getPublicId().equals(daotimednode.getPublicId()) &&
    		this.getDisplayId().equals(daotimednode.getDisplayId()) ) {

        	return true;
        }
        else {

        	return false;
        }
    }

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
    	
        return String.format("TimedNode [ oid=%d, nodeFK=%d, stageFK=%d, stageModifierFK=%s, publicId=%s, displayId=%s ]", 
            oid, nodeFK, stageFK, stageModifierFK, publicId, displayId);
    }
}
