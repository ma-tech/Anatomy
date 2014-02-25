/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayer
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
* Version:      1
*
* Description:  This class represents a SQL Database Transfer Object for the 
*                JOINTimedNodeNodeStage "Table".
*
*                ANA_TIMED_NODE, ANA_NODE, ANA_STAGE
*                
*                Joined on 
*                
*                 FROM ANA_TIMED_NODE   
*        		  JOIN ANA_NODE         ON ANO_OID = ATN_NODE_FK
*                 JOIN ANA_STAGE        ON STG_OID = ATN_STAGE_FK
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

public class JOINTimedNodeNodeStage {
    // Properties ---------------------------------------------------------------------------------
	/*
     *   1. ATN_OID               - int(10) unsigned 
     *   2. ATN_NODE_FK           - int(10) unsigned 
     *   3. ATN_STAGE_FK          - int(10) unsigned 
     *   4. ATN_STAGE_MODIFIER_FK - varchar(20)      
     *   5. ATN_PUBLIC_ID         - varchar(20)     
     *   6. ATN_DISPLAY_ID        - varchar(20)      
     *   
     *   1. ANO_OID               - int(10) unsigned 
     *   2. ANO_SPECIES_FK        - varchar(20)      
     *   3. ANO_COMPONENT_NAME    - varchar(255)     
     *   4. ANO_IS_PRIMARY        - tinyint(1)       
     *   5. ANO_IS_GROUP          - tinyint(1)       
     *   6. ANO_PUBLIC_ID         - varchar(20)      
     *   7. ANO_DESCRIPTION       - varchar(2000)    
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
    
    private Long oidNode; 
    private String speciesFKNode; 
    private String componentName; 
    private boolean primary;
    private boolean group;
    private String publicNodeId; 
    private String descriptionNode; 
    
    private Long oidStage; 
    private String speciesFKStage; 
    private String name; 
    private long sequence;
    private String descriptionStage;
    private String extraText; 
    private String publicStageId; 

    // Constructors -------------------------------------------------------------------------------
    /*
     * Default constructor.
     */
    public JOINTimedNodeNodeStage() {
        
    }

    /*
     * Minimal constructor. Contains required fields.
     */
    public JOINTimedNodeNodeStage(Long oidTimedNode, 
    		long nodeFK, 
    		long stageFK,
    		String stageModifierFK,
    		String publicTimedNodeId,
    		String displayTimedNodeId,
    		Long oidNode, 
    	    String speciesFKNode,
    	    String componentName, 
    	    boolean primary,
    	    boolean group,
    	    String publicNodeId,
    	    String descriptionNode,
    	    Long oidStage, 
    		String speciesFKStage, 
    		String name, 
    		long sequence, 
    		String descriptionStage, 
    		String extraText, 
    		String publicStageId) {
    	
        this.oidTimedNode = oidTimedNode;
        this.nodeFK = nodeFK; 
        this.stageFK = stageFK; 
        this.stageModifierFK = stageModifierFK;
        this.publicTimedNodeId = publicTimedNodeId;
        this.displayTimedNodeId = displayTimedNodeId;
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
     * Minimal constructor. Contains required fields.
     */
    public JOINTimedNodeNodeStage(Long oidTimedNode, 
    		String nodeFK, 
    		String stageFK,
    		String stageModifierFK,
    		String publicTimedNodeId,
    		String displayTimedNodeId,
    		Long oidNode, 
    	    String speciesFKNode,
    	    String componentName, 
    	    String primary,
    	    String group,
    	    String publicNodeId,
    	    String descriptionNode,
    	    Long oidStage, 
    		String speciesFKStage, 
    		String name, 
    		String sequence, 
    		String descriptionStage, 
    		String extraText, 
    		String publicStageId) {
    	
        this.oidTimedNode = oidTimedNode;
        this.nodeFK = ObjectConverter.convert(nodeFK, Long.class);
        this.stageFK = ObjectConverter.convert(stageFK, Long.class);
        this.stageModifierFK = stageModifierFK;
        this.publicTimedNodeId = publicTimedNodeId;
        this.displayTimedNodeId = displayTimedNodeId;
        this.oidNode = oidNode;
	    this.speciesFKNode = speciesFKNode;
	    this.componentName = componentName;
	    this.primary = ObjectConverter.convert(primary, Boolean.class);
	    this.group = ObjectConverter.convert(group, Boolean.class);
	    this.publicNodeId = publicNodeId;
	    this.descriptionNode = descriptionNode;
        this.oidStage = oidStage;
        this.speciesFKStage = speciesFKStage; 
        this.name = name; 
        this.sequence = ObjectConverter.convert(sequence, Long.class);
        this.descriptionStage = descriptionStage;
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

    public Long getOidNode() {
        return oidNode;
    }
    public String getSpeciesFKNode() {
        return speciesFKNode;
    }
    public String getComponentName() {
        return componentName;
    }
    public boolean isPrimary() {
        return primary;
    }
    public String getPrimary() {
        return ObjectConverter.convert(primary, String.class);
    }
    public boolean isGroup() {
        return group;
    }
    public String getGroup() {
        return ObjectConverter.convert(group, String.class);
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
    public long getSequence() {
        return sequence;
    }
    public String getSequenceAsString() {
        return ObjectConverter.convert(sequence, String.class);
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

    public void setOid(Long oidNode) {
        this.oidNode = oidNode;
    }
    public void getSpeciesFKNode(String speciesFKNode) {
        this.speciesFKNode = speciesFKNode;
    }
    public void getComponentName(String componentName) {
        this.componentName = componentName;
    }
    public void setPrimary(boolean primary) {
        this.primary = primary;
    }
    public void setPrimary(String primary) {
        this.primary = ObjectConverter.convert(primary, Boolean.class);
    }
    public void setGroup(boolean group) {
        this.group = group;
    }
    public void setGroup(String group) {
        this.group = ObjectConverter.convert(group, Boolean.class);
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
    public void setSequence(long sequence) {
        this.sequence = sequence;
    }
    public void setSequence(String sequence) {
        this.sequence = ObjectConverter.convert(sequence, Long.class);
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
     * Is this JOINTimedNodeNodeStage the same as the Supplied JOINTimedNodeNodeStage?
     */
    public boolean isSameAs(JOINTimedNodeNodeStage daojointimednodenodestage){

    	if (this.getOidTimedNode().equals(daojointimednodenodestage.getOidTimedNode()) &&
    		this.getNodeFK() == daojointimednodenodestage.getNodeFK() &&
    		this.getStageFK() == daojointimednodenodestage.getStageFK() &&
    		this.getStageModifierFK().equals(daojointimednodenodestage.getStageModifierFK()) && 
    		this.getPublicTimedNodeId().equals(daojointimednodenodestage.getPublicTimedNodeId()) && 
    		this.getDisplayTimedNodeId().equals(daojointimednodenodestage.getDisplayTimedNodeId()) && 
    		this.getOidNode().equals(daojointimednodenodestage.getOidNode()) &&
    		this.getSpeciesFKNode().equals(daojointimednodenodestage.getSpeciesFKNode()) && 
    		this.getComponentName().equals(daojointimednodenodestage.getComponentName()) && 
    		this.isPrimary() == daojointimednodenodestage.isPrimary() &&
    		this.isGroup() == daojointimednodenodestage.isGroup() &&
    		this.getPublicNodeId().equals(daojointimednodenodestage.getPublicNodeId()) && 
    		this.getDescriptionNode().equals(daojointimednodenodestage.getDescriptionNode()) && 
    		this.getOidStage().equals(daojointimednodenodestage.getOidStage()) &&
    		this.getSpeciesFKStage().equals(daojointimednodenodestage.getSpeciesFKStage()) && 
    		this.getName().equals(daojointimednodenodestage.getName()) && 
    		this.getSequence() == daojointimednodenodestage.getSequence() &&
    		this.getDescriptionStage().equals(daojointimednodenodestage.getDescriptionStage()) && 
    		this.getExtraText().equals(daojointimednodenodestage.getExtraText()) && 
    		this.getPublicStageId().equals(daojointimednodenodestage.getPublicStageId())  ) {

        	return true;
        }
        else {

        	return false;
        }
    }

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
        		"TimedNode [ oidTimedNode=%d, nodeFK=%d, stageFK=%d, stageModifierFK=%s, publicTimedNodeId=%s, displayTimedNodeId=%s ]\n" +
        		"Node [ oidNode=%d, speciesFK=%s, componentName=%s, primary=%b, group=%b, publicId=%s, descriptionNode=%s ]\n" +
        		"Stage [ oidStage=%d, speciesFK=%s, name=%s, sequence=%d, descriptionStage=%s, extraText=%s, publicStageId=%s  ]", 
        		oidTimedNode, nodeFK, stageFK, stageModifierFK, publicTimedNodeId, displayTimedNodeId,  
        		oidNode, speciesFKNode, componentName, primary, group, publicNodeId, descriptionNode,
        		oidStage, speciesFKStage, name, sequence, descriptionStage, extraText, publicStageId);
    }
}
