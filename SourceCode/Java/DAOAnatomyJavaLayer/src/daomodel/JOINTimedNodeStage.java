/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayer
*
* Title:        JOINTimedNodeStage.java
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
*                JOINTimedNodeStage "Table".
*                ANA_TIMED_NODE & ANA_STAGE Joined on ATN_STAGE_FK and STG_OID
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

public class JOINTimedNodeStage {
    // Properties ---------------------------------------------------------------------------------
	/*
     *   1. ATN_OID               - int(10) unsigned 
     *   2. ATN_NODE_FK           - int(10) unsigned 
     *   3. ATN_STAGE_FK          - int(10) unsigned 
     *   4. ATN_STAGE_MODIFIER_FK - varchar(20)      
     *   5. ATN_PUBLIC_ID         - varchar(20)      
     *   6. ATN_DISPLAY_ID        - varchar(20)      
     *     
     *   1. STG_OID               - int(10) unsigned
     *   2. STG_SPECIES_FK        - varchar(20)      
     *   3. STG_NAME              - varchar(20)      
     *   4. STG_SEQUENCE          - int(10) unsigned 
     *   5. STG_DESCRIPTION       - varchar(2000)    
     *   6. STG_SHORT_EXTRA_TEXT  - varchar(25)      
     *   7. STG_PUBLIC_ID         - varchar(20)      
	 */
    private Long oidTimedNode; 
    private long nodeFK; 
    private long stageFK; 
    private String stageModifierFK;
    private String publicTimedNodeId;
    private String displayTimedNodeId;
    private long oidStage; 
    private String speciesFK; 
    private String name; 
    private long sequence;
    private String description;
    private String extraText; 
    private String publicStageId; 

    // Constructors -------------------------------------------------------------------------------
    /*
     * Default constructor.
     */
    public JOINTimedNodeStage() {
        
    }

    /*
     * Minimal constructor. Contains required fields.
     */
    public JOINTimedNodeStage(Long oidTimedNode, 
    		long nodeFK, 
    		long stageFK,
    		String stageModifierFK,
    		String publicTimedNodeId,
    		String displayTimedNodeId,
    		Long oidStage, 
    		String speciesFK, 
    		String name, 
    		long sequence, 
    		String description, 
    		String extraText, 
    		String publicStageId) {
    	
        this.oidTimedNode = oidTimedNode;
        this.nodeFK = nodeFK; 
        this.stageFK = stageFK; 
        this.stageModifierFK = stageModifierFK;
        this.publicTimedNodeId = publicTimedNodeId;
        this.displayTimedNodeId = displayTimedNodeId;
        this.oidStage = oidStage;
        this.speciesFK = speciesFK; 
        this.name = name; 
        this.sequence = sequence;
        this.description = description;
        this.extraText = extraText; 
        this.publicStageId = publicStageId; 
    }

    /*
     * Minimal constructor. Contains required fields.
     */
    public JOINTimedNodeStage(Long oidTimedNode, 
    		String nodeFK, 
    		String stageFK,
    		String stageModifierFK,
    		String publicTimedNodeId,
    		String displayTimedNodeId,
    		Long oidStage, 
    		String speciesFK, 
    		String name, 
    		String sequence, 
    		String description, 
    		String extraText, 
    		String publicStageId) {
    	
        this.oidTimedNode = oidTimedNode;
        this.nodeFK = ObjectConverter.convert(nodeFK, Long.class);
        this.stageFK = ObjectConverter.convert(stageFK, Long.class);
        this.stageModifierFK = stageModifierFK;
        this.publicTimedNodeId = publicTimedNodeId;
        this.displayTimedNodeId = displayTimedNodeId;
        this.oidStage = oidStage;
        this.speciesFK = speciesFK; 
        this.name = name; 
        this.sequence = ObjectConverter.convert(sequence, Long.class);
        this.description = description;
        this.extraText = extraText; 
        this.publicStageId = publicStageId; 
    }

    // Getters ------------------------------------------------------------------------------------
    public Long getOidTimedNode() {
        return oidTimedNode;
    }
    public long getNodeFK() {
        return nodeFK;
    }
    public String getNodeFKAsString() {
        return ObjectConverter.convert(nodeFK, String.class);
    }
    public long getStageFK() {
        return stageFK;
    }
    public String getStageFKAsString() {
        return ObjectConverter.convert(stageFK, String.class);
    }
    public String getStageModifierFK() {
        return stageModifierFK;
    }
    public String getPublicTimedNodeId() {
        return publicTimedNodeId;
    }
    public String getDisplayTimedNodeId() {
        return displayTimedNodeId;
    }

    public Long getOidStage() {
        return oidStage;
    }
    public String getSpeciesFK() {
        return speciesFK;
    }
    public String getName() {
        return name;
    }
    public long getSequence() {
        return sequence;
    }
    public String getSequenceAsString() {
        return ObjectConverter.convert(sequence, String.class);
    }
    public String getDescription() {
        return description;
    }
    public String getExtraText() {
        return extraText;
    }
    public String getPublicStageId() {
        return publicStageId;
    }

    // Setters ------------------------------------------------------------------------------------
    public void setOidTimedNode(Long oidTimedNode) {
        this.oidTimedNode = oidTimedNode;
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
    public void setPublicTimedNodeId(String publicTimedNodeId) {
        this.publicTimedNodeId = publicTimedNodeId;
    }
    public void setDisplayTimedNodeId(String displayTimedNodeId) {
        this.displayTimedNodeId = displayTimedNodeId;
    }

    public void setOidStage(Long oidStage) {
        this.oidStage = oidStage;
    }
    public void setSpeciesFK(String speciesFK) {
        this.speciesFK = speciesFK;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setSequence(long sequence) {
        this.sequence = sequence;
    }
    public void setSequence(String sequence) {
        this.sequence = ObjectConverter.convert(sequence, Long.class);
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setExtraText(String extraText) {
        this.extraText = extraText;
    }
    public void setPublicStageId(String publicStageId) {
        this.publicStageId = publicStageId;
    }

    // Override -----------------------------------------------------------------------------------
    /*
     * Is this JOINTimedNodeStage the same as the Supplied JOINTimedNodeStage?
     */
    public boolean isSameAs(JOINTimedNodeStage daojointimednodestage){


    	if ( this.getOidTimedNode().equals(daojointimednodestage.getOidTimedNode()) &&
    		this.getNodeFK() == daojointimednodestage.getNodeFK() &&
    		this.getStageFK() == daojointimednodestage.getStageFK() &&
    		this.getStageModifierFK().equals(daojointimednodestage.getStageModifierFK()) && 
    		this.getPublicTimedNodeId().equals(daojointimednodestage.getPublicTimedNodeId()) && 
    		this.getDisplayTimedNodeId().equals(daojointimednodestage.getDisplayTimedNodeId()) && 
    		this.getOidStage().equals(daojointimednodestage.getOidStage()) &&
    		this.getSpeciesFK().equals(daojointimednodestage.getSpeciesFK()) && 
    		this.getName().equals(daojointimednodestage.getName()) && 
    		this.getSequence() == daojointimednodestage.getSequence() &&
    		this.getDescription().equals(daojointimednodestage.getDescription()) && 
    		this.getExtraText().equals(daojointimednodestage.getExtraText()) && 
    		this.getPublicStageId().equals(daojointimednodestage.getPublicStageId()) ) {

        	return true;
        }
        else {

        	return false;
        }
    }

    /*
     * The JOINTimedNodeStage TimedNode OID is unique for each JOINTimedNodeStage. 
     *  So this should compare JOINTimedNodeStage by TimedNode OID only.
     */
    public boolean equals(Object other) {
    	
        return (other instanceof JOINTimedNodeStage) && (oidTimedNode != null) 
        		? oidTimedNode.equals(((JOINTimedNodeStage) other).oidTimedNode) 
        		: (other == this);
    }

    /*
     * Returns the String representation of this JOINTimedNodeStage.
     *  Not required, it just makes reading logs easier.
     */
    public String toString() {
    	
        return String.format("JOINTimedNodeStage\n" +
        		"TimedNode [ oid=%d, nodeFK=%d, stageFK=%d, stageModifierFK=%s, publicTimedNodeId=%s, displayTimedNodeId=%s ]" +
        		"Stage [ oid=%d, speciesFK=%s, name=%s, sequence=%d, description=%s, extraText=%s, publicId=%s ]", 
        		oidTimedNode, nodeFK, stageFK, stageModifierFK, publicTimedNodeId, displayTimedNodeId,  
        		oidStage, speciesFK, name, sequence, description, extraText, publicStageId);
    }
}
