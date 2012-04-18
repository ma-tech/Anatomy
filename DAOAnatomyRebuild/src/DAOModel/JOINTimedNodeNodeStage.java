/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyRebuild
*
* Title:        JOINTimedNodeNodeStage.java
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
*                JOINTimedNodeNodeStage "Table".
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

public class JOINTimedNodeNodeStage {
    // Properties ---------------------------------------------------------------------------------
	/*
	 *  ANA_TIMED_NODE & ANA_STAGE & ANA_NODE
	 *  
     *  Columns:
     *   1. ATN_OID               - int(10) unsigned 
     *   2. ATN_NODE_FK           - int(10) unsigned 
     *   3. ATN_STAGE_FK          - int(10) unsigned 
     *   4. ATN_STAGE_MODIFIER_FK - varchar(20)      
     *   5. ATN_PUBLIC_ID         - varchar(20)      
     *   1. ANO_OID            - int(10) unsigned 
     *   2. ANO_SPECIES_FK     - varchar(20)      
     *   3. ANO_COMPONENT_NAME - varchar(255)     
     *   4. ANO_IS_PRIMARY     - tinyint(1)       
     *   5. ANO_IS_GROUP       - tinyint(1)       
     *   6. ANO_PUBLIC_ID      - varchar(20)      
     *   7. ANO_DESCRIPTION    - varchar(2000)    
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
    private Long oidNode; 
    private String speciesFKNode; 
    private String componentName; 
    private Boolean primary;
    private Boolean group;
    private String publicNodeId; 
    private String descriptionNode; 
    private Long oidStage; 
    private String speciesFKStage; 
    private String name; 
    private Long sequence;
    private String descriptionStage;
    private String extraText; 
    private String publicStageId; 

    // Constructors -------------------------------------------------------------------------------
    /*
     * Default constructor.
     */
    public JOINTimedNodeNodeStage() {
        // Always keep the default constructor alive in a Javabean class.
    }

    /*
     * Minimal constructor. Contains required fields.
     */
    public JOINTimedNodeNodeStage(Long oidTimedNode, 
    		Long nodeFK, 
    		Long stageFK,
    		String stageModifierFK,
    		String publicTimedNodeId,
    		Long oidNode, 
    	    String speciesFKNode,
    	    String componentName, 
    	    Boolean primary,
    	    Boolean group,
    	    String publicNodeId,
    	    String descriptionNode,
    		Long oidStage, 
    		String speciesFKStage, 
    		String name, 
    		Long sequence, 
    		String descriptionStage, 
    		String extraText, 
    		String publicStageId) {
    	
        this.oidTimedNode = oidTimedNode;
        this.nodeFK = nodeFK; 
        this.stageFK = stageFK; 
        this.stageModifierFK = stageModifierFK;
        this.publicTimedNodeId = publicTimedNodeId;
        this.oidNode = oidNode;
	    this.speciesFKNode = speciesFKNode;
	    this.componentName = componentName;
	    this.primary = primary;
	    this.group = group;
	    this.publicNodeId = publicNodeId;
	    this.descriptionNode = descriptionNode;
        this.oidStage = oidStage;
        this.speciesFKStage = speciesFKStage; 
        this.name = name; 
        this.sequence = sequence;
        this.descriptionStage = descriptionStage;
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
    public String getPpublicTimedNodeId() {
        return publicTimedNodeId;
    }

    public Long getOidNode() {
        return oidNode;
    }
    public String getSpeciesFKNode() {
        return speciesFKNode;
    }
    public String getComponentName() {
        return componentName;
    }
    public Boolean isPrimary() {
        return primary;
    }
    public Boolean isGroup() {
        return group;
    }
    public String getPublicNodeId() {
        return publicNodeId;
    }
    public String getDescriptionNode() {
        return descriptionNode;
    }

    public Long getOidStage() {
        return oidStage;
    }
    public String getSpeciesFKStage() {
        return speciesFKStage;
    }
    public String getName() {
        return name;
    }
    public Long getSequence() {
        return sequence;
    }
    public String getDescriptionStage() {
        return descriptionStage;
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

    public void setOid(Long oidNode) {
        this.oidNode = oidNode;
    }
    public void getSpeciesFKNode(String speciesFKNode) {
        this.speciesFKNode = speciesFKNode;
    }
    public void getComponentName(String componentName) {
        this.componentName = componentName;
    }
    public void setPrimary(Boolean primary) {
        this.primary = primary;
    }
    public void setGroup(Boolean group) {
        this.group = group;
    }
    public void setPublicNodeId(String publicNodeId) {
        this.publicNodeId = publicNodeId;
    }
    public void setDescriptionNode(String descriptionNode) {
        this.descriptionNode = descriptionNode;
    }

    public void setOidStage(Long oidStage) {
        this.oidStage = oidStage;
    }
    public void speciesFKStage(String speciesFKStage) {
        this.speciesFKStage = speciesFKStage;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setSequence(Long sequence) {
        this.sequence = sequence;
    }
    public void setDescriptionStage(String descriptionStage) {
        this.descriptionStage = descriptionStage;
    }
    public void setExtraText(String extraText) {
        this.extraText = extraText;
    }
    public void setPublicStageId(String publicStageId) {
        this.publicStageId = publicStageId;
    }

    // Override -----------------------------------------------------------------------------------
    /*
     * The TimedNode OID is unique for each JOINTimedNodeNodeStage.
     *  So this should compare JOINTimedNodeNodeStage by TimedNode OID only.
     */
    public boolean equals(Object other) {
        return (other instanceof JOINTimedNodeNodeStage) && (oidTimedNode != null) 
        		? oidTimedNode.equals(((JOINTimedNodeNodeStage) other).oidTimedNode) 
        		: (other == this);
    }

    /*
     * Returns the String representation of this JOINTimedNodeNodeStage.
     *  Not required, it just makes reading logs easier.
     */
    public String toString() {
        return String.format("JOINTimedNodeNodeStage\n" +
        		"TimedNode [ oidTimedNode=%d, nodeFK=%d, stageFK=%d, stageModifierFK=%s, publicTimedNodeId=%s ]" +
        		"Node [ oidNode=%d, speciesFK=%s, componentName=%s, primary=%b, group=%b, publicId=%s, descriptionNode=%s ]" +
        		"Stage [ oidStage=%d, speciesFK=%s, name=%s, sequence=%d, descriptionStage=%s, extraText=%s, publicStageId=%s  ]", 
        		oidTimedNode, nodeFK, stageFK, stageModifierFK, publicTimedNodeId, 
        		oidNode, speciesFKNode, componentName, primary, group, publicNodeId, descriptionNode,
        		oidStage, speciesFKStage, name, sequence, descriptionStage, extraText, publicStageId);
    }
}
