/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayer
*
* Title:        TimedNodeFK.java
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

public class TimedNodeFK {
    // Properties ---------------------------------------------------------------------------------
	/*
     *   1. ATN_OID               - int(10) unsigned 
     *   2. ANO_PUBLIC_ID         - varchar(20)     
     *   3. STG_NAME              - varchar(20)
     *   4. ATN_STAGE_MODIFIER_FK - varchar(20)      
     *   5. ATN_PUBLIC_ID         - varchar(20)      
     *   6. ATN_DISPLAY_ID        - varchar(20)      
	 */
    private Long oid; 
    private String nodeNameFK; 
    private String stageNameFK; 
    private String stageModifierFK;
    private String publicId;
    private String displayId;

    // Constructors -------------------------------------------------------------------------------
    /*
     * Default constructor.
     */
    public TimedNodeFK() {
        
    }

    /*
     * Minimal constructor. Contains required fields.
     */
    public TimedNodeFK(Long oid, 
    		String nodeNameFK, 
    		String stageNameFK,
    		String stageModifierFK,
    		String publicId,
    		String displayId) {
    	
        this.oid = oid;
        this.nodeNameFK = nodeNameFK; 
        this.stageNameFK = stageNameFK; 
        this.stageModifierFK = stageModifierFK;
        this.publicId = publicId;
        this.displayId = displayId;
    }

    // Getters ------------------------------------------------------------------------------------
    public Long getOid() {
        return oid;
    }
    public String getNodeNameFK() {
        return nodeNameFK;
    }
    public String getStageNameFK() {
        return stageNameFK;
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
    public void setNodeNameFK(String nodeNameFK) {
        this.nodeNameFK = nodeNameFK;
    }
    public void setStageNameFK(String stageNameFK) {
        this.stageNameFK = stageNameFK;
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
     * Is this TimedNodeFK the same as the Supplied TimedNodeFK?
     */
    public boolean isSameAs(TimedNodeFK daotimednode){

    	if (this.getNodeNameFK().equals(daotimednode.getNodeNameFK()) &&
    		this.getStageNameFK().equals(daotimednode.getStageNameFK()) &&
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
    	
        return (other instanceof TimedNodeFK) && (oid != null) 
        		? oid.equals(((TimedNodeFK) other).oid) 
        		: (other == this);
    }

    /*
     * Returns the String representation of this Timed Node. 
     *  Not required, it just makes reading logs easier
     */
    public String toString() {
    	
        return String.format("TimedNodeFK [ oid=%d, nodeNameFK=%s, stageNameFK=%s, stageModifierFK=%s, publicId=%s, displayId=%s ]", 
            oid, nodeNameFK, stageNameFK, stageModifierFK, publicId, displayId);
    }

    /*
     * Returns the String representation of this Timed Node. 
     *  Not required, it just makes reading logs easier
     */
    public String toStringThing() {
    	
        return String.format("oid=%d, nodeNameFK=%s, stageNameFK=%s, stageModifierFK=%s, publicId=%s, displayId=%s", 
            oid, nodeNameFK, stageNameFK, stageModifierFK, publicId, displayId);
    }
}
