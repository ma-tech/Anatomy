/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayer
*
* Title:        ExtraTimedNode.java
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
* Description:  This class represents a SQL Database Transfer Object for the 
*                Timed Node Table - ANA_TIMED_NODE
*                 With Extra Joined Fields from:
*                  ANA_NODE
*                  ANA_STAGE
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

import utility.ObjectConverter;

public class ExtraTimedNode {
    // Properties ---------------------------------------------------------------------------------
	/*
     *   1. ATN_OID               - int(10) unsigned 
     *   2. ATN_NODE_FK           - int(10) unsigned 
     *   3. ATN_STAGE_FK          - int(10) unsigned 
     *   4. ATN_STAGE_MODIFIER_FK - varchar(20)      
     *   5. ATN_PUBLIC_ID         - varchar(20)
     *   6. ANO_PUBLIC_ID         - varchar(20)
     *   
     *   1. STG_NAME              - varchar(20)
     *   2. STG_SEQUENCE          - int(10) unsigned 
     *   3. STAGE_MIN             - varchar(20)
     *   4. STAGE_MAX             - varchar(20)
	 */
    private Long oid; 
    private long nodeFK; 
    private long stageFK; 
    private String stageModifierFK;
    private String publicEmapId;
    private String publicEmapaId;
    private String stageName;
    private long stageSeq;
    private String stageMinName;
    private String stageMaxName;

    // Constructors -------------------------------------------------------------------------------
    /*
     * Default constructor.
     */
    public ExtraTimedNode() {
        
    }

    /*
     * Minimal constructor. Contains required fields.
     */
    public ExtraTimedNode(long oid, 
    		long nodeFK, 
    		long stageFK,
    		String stageModifierFK,
    		String publicEmapId,
    		String publicEmapaId,
    		String stageName,
    		long stageSeq,
    		String stageMinName,
    		String stageMaxName
    		) {
    	
        this.oid = oid;
        this.nodeFK = nodeFK; 
        this.stageFK = stageFK; 
        this.stageModifierFK = stageModifierFK;
        this.publicEmapId = publicEmapId;
        this.publicEmapaId = publicEmapaId;
        this.stageName = stageName;
        this.stageSeq = stageSeq;
        this.stageMinName = stageMinName;
        this.stageMaxName = stageMaxName;
    }

    /*
     * Minimal constructor. Contains required fields.
     */
    public ExtraTimedNode(long oid, 
    		String nodeFK, 
    		String stageFK,
    		String stageModifierFK,
    		String publicEmapId,
    		String publicEmapaId,
    		String stageName,
    		String stageSeq,
    		String stageMinName,
    		String stageMaxName
    		) {
    	
        this.oid = oid;
        this.nodeFK = ObjectConverter.convert(nodeFK, Long.class);
        this.stageFK = ObjectConverter.convert(stageFK, Long.class);
        this.stageModifierFK = stageModifierFK;
        this.publicEmapId = publicEmapId;
        this.publicEmapaId = publicEmapaId;
        this.stageName = stageName;
        this.stageSeq = ObjectConverter.convert(stageSeq, Long.class);
        this.stageMinName = stageMinName;
        this.stageMaxName = stageMaxName;
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
    public String getPublicEmapId() {
        return publicEmapId;
    }
    public String getPublicEmapaId() {
        return publicEmapaId;
    }
    public String getStageName() {
        return stageName;
    }
    public long getStageSeq() {
        return stageSeq;
    }
    public String getStageMinName() {
        return stageMinName;
    }
    public String getStageMaxName() {
        return stageMaxName;
    }

    // Setters ------------------------------------------------------------------------------------
    public void setOid(Long oid) {
        this.oid = oid;
    }
    public void setNodeFK(long nodeFK) {
        this.nodeFK = nodeFK;
    }
    public void setNodeFK(String nodeFK) {
        this.nodeFK = ObjectConverter.convert(nodeFK, Long.class);
    }
    public void setStageFK(long stageFK) {
        this.stageFK = stageFK;
    }
    public void setStageFK(String stageFK) {
        this.stageFK = ObjectConverter.convert(stageFK, Long.class);
    }
    public void setStageModifierFK(String stageModifierFK) {
        this.stageModifierFK = stageModifierFK;
    }
    public void setPublicEmapId(String publicEmapId) {
        this.publicEmapId = publicEmapId;
    }
    public void setPublicEmapaId(String publicEmapaId) {
        this.publicEmapaId = publicEmapaId;
    }
    public void setStageName(String stageName) {
        this.stageName = stageName;
    }
    public void setStageSeq(long stageSeq) {
        this.stageSeq = stageSeq;
    }
    public void setStageSeq(String stageSeq) {
        this.stageSeq = ObjectConverter.convert(stageSeq, Long.class);
    }
    public void setStageMinName(String stageMinName) {
        this.stageMinName = stageMinName;
    }
    public void setStageMaxName(String stageMaxName) {
        this.stageMaxName = stageMaxName;
    }

    // Override -----------------------------------------------------------------------------------
    /*
     * Is this ExtraTimedNode the same as the Supplied ExtraTimedNode?
     */
    public boolean isSameAs(ExtraTimedNode daoextratimednode){

    	if ( this.getNodeFK() == daoextratimednode.getNodeFK() &&
    		this.getStageFK() == daoextratimednode.getStageFK() &&
    		this.getStageModifierFK().equals(daoextratimednode.getStageModifierFK()) &&
    		this.getPublicEmapId().equals(daoextratimednode.getPublicEmapId()) &&
    		this.getPublicEmapaId().equals(daoextratimednode.getPublicEmapaId()) &&
    		this.getStageName().equals(daoextratimednode.getStageName()) &&
    		this.getStageSeq() == daoextratimednode.getStageSeq() &&
    		this.getStageMinName().equals(daoextratimednode.getStageMinName()) &&
    		this.getStageMaxName().equals(daoextratimednode.getStageMaxName()) ) {

        	return true;
        }
        else {

        	return false;
        }
    }

    /*
     * The relation ID is unique for each Relation. So this should compare Relation by ID only.
     */
    public boolean equals(Object other) {
    	
        return (other instanceof ExtraTimedNode) && (oid != null) 
        		? oid.equals(((ExtraTimedNode) other).oid) 
        		: (other == this);
    }

    /*
     * Returns the String representation of this User. Not required, it just makes reading logs easier.
     */
    public String toString() {
    	
        return String.format("ExtraTimedNode [ oid=%d, nodeFK=%d, stageFK=%d, stageModifierFK=%s, publicEmapId=%s, publicEmapaId=%s, stageName=%s, stageSeq=%d, stageMaxName=%s, stageMaxName=%s ]", 
            oid, nodeFK, stageFK, stageModifierFK, publicEmapId, publicEmapaId, stageName, stageSeq, stageMinName, stageMaxName);
    }
}
