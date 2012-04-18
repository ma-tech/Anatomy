/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyRebuild
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
* Version: 1
*
* Description:  This class represents a SQL Database Transfer Object for the 
*                JOINTimedNodeStage "Table".
*
* Link:         http://balusc.blogspot.com/2008/07/dao-tutorial-data-layer.html
* 
* Maintenance:  Log changes below, with most recent at top of list.
*
* Who; When; What;
*
* Mike Wicks; February 2012; Create Class
*
*----------------------------------------------------------------------------------------------
*/

package DAOModel;

public class JOINTimedNodeStage {
    // Properties ---------------------------------------------------------------------------------
	/*
	 *  ANA_TIMED_NODE & ANA_STAGE
	 *  
     *  Columns:
     *   1. ATN_OID               - int(10) unsigned 
     *   2. ATN_NODE_FK           - int(10) unsigned 
     *   3. ATN_STAGE_FK          - int(10) unsigned 
     *   4. ATN_STAGE_MODIFIER_FK - varchar(20)      
     *   5. ATN_PUBLIC_ID         - varchar(20)      
     *   1. STG_OID              - int(10) unsigned
     *   2. STG_SPECIES_FK       - varchar(20)      
     *   3. STG_NAME             - varchar(20)      
     *   4. STG_SEQUENCE         - int(10) unsigned 
     *   5. STG_DESCRIPTION      - varchar(2000)    
     *   6. STG_SHORT_EXTRA_TEXT - varchar(25)      
     *   7. STG_PUBLIC_ID        - varchar(20)      
	 */
    private Long oidTimedNode; 
    private Long nodeFK; 
    private Long stageFK; 
    private String stageModifierFK;
    private String publicTimedNodeId;
    private Long oidStage; 
    private String speciesFK; 
    private String name; 
    private Long sequence;
    private String description;
    private String extraText; 
    private String publicStageId; 

    // Constructors -------------------------------------------------------------------------------
    /*
     * Default constructor.
     */
    public JOINTimedNodeStage() {
        // Always keep the default constructor alive in a Javabean class.
    }

    /*
     * Minimal constructor. Contains required fields.
     */
    public JOINTimedNodeStage(Long oidTimedNode, 
    		Long nodeFK, 
    		Long stageFK,
    		String stageModifierFK,
    		String publicTimedNodeId,
    		Long oidStage, 
    		String speciesFK, 
    		String name, 
    		Long sequence, 
    		String description, 
    		String extraText, 
    		String publicStageId) {
    	
        this.oidTimedNode = oidTimedNode;
        this.nodeFK = nodeFK; 
        this.stageFK = stageFK; 
        this.stageModifierFK = stageModifierFK;
        this.publicTimedNodeId = publicTimedNodeId;
        this.oidStage = oidStage;
        this.speciesFK = speciesFK; 
        this.name = name; 
        this.sequence = sequence;
        this.description = description;
        this.extraText = extraText; 
        this.publicStageId = publicStageId; 
    }

    /*
     * Full constructor. Contains required and optional fields.
     * 
     * The Full Constructor is the Minimal Constructor
     * 
     */

    // Getters ------------------------------------------------------------------------------------
    public Long getOidTimedNode() {
        return oidTimedNode;
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
    public String getPublicTimedNodeId() {
        return publicTimedNodeId;
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
    public Long getSequence() {
        return sequence;
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
    public void setNodeFK(Long nodeFK) {
        this.nodeFK = nodeFK;
    }
    public void setStageFK(Long stageFK) {
        this.stageFK = stageFK;
    }
    public void setStageModifierFK(String stageModifierFK) {
        this.stageModifierFK = stageModifierFK;
    }
    public void setPublicTimedNodeId(String publicTimedNodeId) {
        this.publicTimedNodeId = publicTimedNodeId;
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
    public void setSequence(Long sequence) {
        this.sequence = sequence;
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
        		"TimedNode [ oid=%d, nodeFK=%d, stageFK=%d, stageModifierFK=%s, publicTimedNodeId=%s ]" +
        		"Stage [ oidTimedNode=%d, nodeFK=%d, stageFK=%d, stageModifierFK=%s, publicStageId=%s ]", 
        		oidTimedNode, nodeFK, stageFK, stageModifierFK, publicTimedNodeId, oidTimedNode, speciesFK, name, sequence, description, extraText, publicStageId);
    }
}
