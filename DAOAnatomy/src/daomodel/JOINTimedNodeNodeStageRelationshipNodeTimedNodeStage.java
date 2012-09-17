/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomy008
*
* Title:        JOINTimedNodeNodeStageRelationshipNodeTimedNodeStage.java
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
*                JOINTimedNodeNodeStageRelationshipNodeTimedNodeStage "Table".
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

public class JOINTimedNodeNodeStageRelationshipNodeTimedNodeStage {
    // Properties ---------------------------------------------------------------------------------
	/*
	 *  ANA_TIMED_NODE & ANA_NODE & ANA_STAGE & ANA_RELATIONSHIP & ANA_NODE & ANA_TIMED_NODE & ANA_STAGE 
	 *  
     *  Columns:
     *   1. ATN_OID               - int(10) unsigned 
     *   2. ATN_NODE_FK           - int(10) unsigned 
     *   3. ATN_STAGE_FK          - int(10) unsigned 
     *   4. ATN_STAGE_MODIFIER_FK - varchar(20)      
     *   5. ATN_PUBLIC_ID         - varchar(20)
     *         
     *   1. ANO_OID            - int(10) unsigned 
     *   2. ANO_SPECIES_FK     - varchar(20)      
     *   3. ANO_COMPONENT_NAME - varchar(255)     
     *   4. ANO_IS_PRIMARY     - tinyint(1)       
     *   5. ANO_IS_GROUP       - tinyint(1)       
     *   6. ANO_PUBLIC_ID      - varchar(20)      
     *   7. ANO_DESCRIPTION    - varchar(2000)    
     *   
     *   1. STG_OID              - int(10) unsigned
     *   2. STG_SPECIES_FK       - varchar(20)      
     *   3. STG_NAME             - varchar(20)      
     *   4. STG_SEQUENCE         - int(10) unsigned 
     *   5. STG_DESCRIPTION      - varchar(2000)    
     *   6. STG_SHORT_EXTRA_TEXT - varchar(25)      
     *   7. STG_PUBLIC_ID        - varchar(20)
     *         
     *   1. REL_OID                  - int(10) unsigned 
     *   2. REL_RELATIONSHIP_TYPE_FK - varchar(20)      
     *   3. REL_CHILD_FK             - int(10) unsigned 
     *   4. REL_PARENT_FK            - int(10) unsigned
     *    
     *   1. ATN_OID               - int(10) unsigned 
     *   2. ATN_NODE_FK           - int(10) unsigned 
     *   3. ATN_STAGE_FK          - int(10) unsigned 
     *   4. ATN_STAGE_MODIFIER_FK - varchar(20)      
     *   5. ATN_PUBLIC_ID         - varchar(20)
     *         
     *   1. ANO_OID            - int(10) unsigned 
     *   2. ANO_SPECIES_FK     - varchar(20)      
     *   3. ANO_COMPONENT_NAME - varchar(255)     
     *   4. ANO_IS_PRIMARY     - tinyint(1)       
     *   5. ANO_IS_GROUP       - tinyint(1)       
     *   6. ANO_PUBLIC_ID      - varchar(20)      
     *   7. ANO_DESCRIPTION    - varchar(2000)
     *
     *   1. STG_OID              - int(10) unsigned
     *   2. STG_SPECIES_FK       - varchar(20)      
     *   3. STG_NAME             - varchar(20)      
     *   4. STG_SEQUENCE         - int(10) unsigned 
     *   5. STG_DESCRIPTION      - varchar(2000)    
     *   6. STG_SHORT_EXTRA_TEXT - varchar(25)      
     *   7. STG_PUBLIC_ID        - varchar(20)
     *         
     */
    private Long oidTimedNodeI; 
    private Long nodeFKI; 
    private Long stageFKI; 
    private String stageModifierFKI;
    private String publicTimedNodeIdI;
    private Long oidNodeI; 
    private String speciesFKNodeI; 
    private String componentNameI; 
    private Boolean primaryI;
    private Boolean groupI;
    private String publicNodeIdI; 
    private String descriptionNodeI; 
    private Long oidStageI; 
    private String speciesFKStageI; 
    private String nameI; 
    private Long sequenceI;
    private String descriptionStageI;
    private String extraTextI; 
    private String publicStageIdI; 
    private Long oidRel; 
    private String typeFK; 
    private Long childFK; 
    private Long parentFK;
    private Long oidTimedNodeII; 
    private Long nodeFKII; 
    private Long stageFKII; 
    private String stageModifierFKII;
    private String publicTimedNodeIdII;
    private Long oidNodeII; 
    private String speciesFKNodeII; 
    private String componentNameII; 
    private Boolean primaryII;
    private Boolean groupII;
    private String publicNodeIdII; 
    private String descriptionNodeII; 
    private Long oidStageII; 
    private String speciesFKStageII; 
    private String nameII; 
    private Long sequenceII;
    private String descriptionStageII;
    private String extraTextII; 
    private String publicStageIdII; 

    // Constructors -------------------------------------------------------------------------------
    /*
     * Default constructor.
     */
    public JOINTimedNodeNodeStageRelationshipNodeTimedNodeStage() {
        // Always keep the default constructor alive in a Javabean class.
    }

    /*
     * Minimal constructor. Contains required fields.
     */
    public JOINTimedNodeNodeStageRelationshipNodeTimedNodeStage(Long oidTimedNodeI, 
    		Long nodeFKI, 
    		Long stageFKI, 
    		String stageModifierFKI,
    		String publicTimedNodeIdI,
    		Long oidNodeI, 
    		String speciesFKNodeI, 
    		String componentNameI, 
    		Boolean primaryI,
    		Boolean groupI,
    		String publicNodeIdI, 
    		String descriptionNodeI, 
    		Long oidStageI, 
    		String speciesFKStageI, 
    		String nameI, 
    		Long sequenceI,
    		String descriptionStageI,
    		String extraTextI, 
    		String publicStageIdI, 
    		Long oidRel, 
    		String typeFK, 
    		Long childFK, 
    		Long parentFK,
    		Long oidTimedNodeII, 
    		Long nodeFKII, 
    		Long stageFKII, 
    		String stageModifierFKII,
    		String publicTimedNodeIdII,
    		Long oidNodeII, 
    		String speciesFKNodeII, 
    		String componentNameII, 
    		Boolean primaryII,
    		Boolean groupII,
    		String publicNodeIdII, 
    		String descriptionNodeII, 
    		Long oidStageII, 
    		String speciesFKStageII, 
    		String nameII, 
    		Long sequenceII,
    		String descriptionStageII,
    		String extraTextII, 
    		String publicStageIdII) {
    	
    	this.oidTimedNodeI = oidTimedNodeI;
    	this.nodeFKI = nodeFKI;
    	this.stageFKI = stageFKI;
    	this.stageModifierFKI = stageModifierFKI;
    	this.publicTimedNodeIdI = publicTimedNodeIdI;
    	this.oidNodeI = oidNodeI;
    	this.speciesFKNodeI = speciesFKNodeI;
    	this.componentNameI = componentNameI;
    	this.primaryI = primaryI;
    	this.groupI = groupI;
    	this.publicNodeIdI = publicNodeIdI;
    	this.descriptionNodeI = descriptionNodeI;
    	this.oidStageI = oidStageI;
    	this.speciesFKStageI = speciesFKStageI;
    	this.nameI = nameI;
    	this.sequenceI = sequenceI;
    	this.descriptionStageI = descriptionStageI;
    	this.extraTextI = extraTextI;
    	this.publicStageIdI = publicStageIdI;
    	this.oidRel = oidRel;
    	this.typeFK = typeFK;
    	this.childFK = childFK;
    	this.parentFK = parentFK;
    	this.oidTimedNodeII = oidTimedNodeII;
    	this.nodeFKII = nodeFKII;
    	this.stageFKII = stageFKII;
    	this.stageModifierFKII = stageModifierFKII;
    	this.publicTimedNodeIdII = publicTimedNodeIdII;
    	this.oidNodeII = oidNodeII;
    	this.speciesFKNodeII = speciesFKNodeII;
    	this.componentNameII = componentNameII;
    	this.primaryII = primaryII;
    	this.groupII = groupII;
    	this.publicNodeIdII = publicNodeIdII;
    	this.descriptionNodeII = descriptionNodeII;
    	this.speciesFKStageII = speciesFKStageII;
    	this.nameII = nameII;
    	this.sequenceII = sequenceII;
    	this.descriptionStageII = descriptionStageII;
    	this.extraTextII = extraTextII;
    	this.publicStageIdII = publicStageIdII;
    }

    /*
     * Full constructor. Contains required and optional fields.
     * 
     * The Full Constructor is the Minimal Constructor
     * 
     */

    // Getters ------------------------------------------------------------------------------------
    public Long getOidTimedNodeI() {
        return oidTimedNodeI;
    }
    public Long getNodeFKI() {
        return nodeFKI;
    }
    public Long getStageFKI() {
        return stageFKI;
    }
    public String getStageModifierFKI() {
        return stageModifierFKI;
    }
    public String getPublicTimedNodeIdI() {
        return publicTimedNodeIdI;
    }

    public Long getOidNodeI() {
        return oidNodeI;
    }
    public String getSpeciesFKNodeI() {
        return speciesFKNodeI;
    }
    public String getComponentNameI() {
        return componentNameI;
    }
    public Boolean isPrimaryI() {
        return primaryI;
    }
    public Boolean isGroupI() {
        return groupI;
    }
    public String getPublicNodeIdI() {
        return publicNodeIdI;
    }
    public String getDescriptionNodeI() {
        return descriptionNodeI;
    }

    public Long getOidStageI() {
        return oidStageI;
    }
    public String getSpeciesFKStageI() {
        return speciesFKStageI;
    }
    public String getNameI() {
        return nameI;
    }
    public Long getSequenceI() {
        return sequenceI;
    }
    public String getDescriptionStageI() {
        return descriptionStageI;
    }
    public String getExtraTextI() {
        return extraTextI;
    }
    public String getPublicStageIdI() {
        return publicStageIdI;
    }

    public Long getOidRel() {
        return oidRel;
    }
    public String getTypeFK() {
        return typeFK;
    }
    public Long getChildFK() {
        return childFK;
    }
    public Long getParentFK() {
        return parentFK;
    }

    public Long getOidNodeII() {
        return oidNodeII;
    }
    public String getSpeciesFKNodeII() {
        return speciesFKNodeII;
    }
    public String getComponentNameII() {
        return componentNameII;
    }
    public Boolean isPrimaryII() {
        return primaryII;
    }
    public Boolean isGroupII() {
        return groupII;
    }
    public String getPublicNodeIdII() {
        return publicNodeIdII;
    }
    public String getDescriptionNodeII() {
        return descriptionNodeII;
    }

    public Long getOidTimedNodeII() {
        return oidTimedNodeII;
    }
    public Long getNodeFKII() {
        return nodeFKII;
    }
    public Long getStageFKII() {
        return stageFKII;
    }
    public String getStageModifierFKII() {
        return stageModifierFKII;
    }
    public String getPublicTimedNodeIdII() {
        return publicTimedNodeIdII;
    }

    public Long getOidStageII() {
        return oidStageII;
    }
    public String getSpeciesFKStageII() {
        return speciesFKStageII;
    }
    public String getNameII() {
        return nameII;
    }
    public Long getSequenceII() {
        return sequenceII;
    }
    public String getDescriptionStageII() {
        return descriptionStageII;
    }
    public String getExtraTextII() {
        return extraTextII;
    }
    public String getPublicStageIdII() {
        return publicStageIdII;
    }

    // Setters ------------------------------------------------------------------------------------
    public void setOidTimedNodeI(Long oidTimedNodeI) {
        this.oidTimedNodeI = oidTimedNodeI;
    }
    public void setNodeFKI(Long nodeFKI) {
        this.nodeFKI = nodeFKI;
    }
    public void setStageFKI(Long stageFKI) {
        this.stageFKI = stageFKI;
    }
    public void setStageModifierFKI(String stageModifierFKI) {
        this.stageModifierFKI = stageModifierFKI;
    }
    public void setPublicTimedNodeIdI(String publicTimedNodeIdI) {
        this.publicTimedNodeIdI = publicTimedNodeIdI;
    }

    public void setOidI(Long oidNodeI) {
        this.oidNodeI = oidNodeI;
    }
    public void getSpeciesFKNodeI(String speciesFKNodeI) {
        this.speciesFKNodeI = speciesFKNodeI;
    }
    public void setComponentNameI(String componentNameI) {
        this.componentNameI = componentNameI;
    }
    public void setPrimaryI(Boolean primaryI) {
        this.primaryI = primaryI;
    }
    public void setGroupI(Boolean groupI) {
        this.groupI = groupI;
    }
    public void setPublicNodeIdI(String publicNodeIdI) {
        this.publicNodeIdI = publicNodeIdI;
    }
    public void setDescriptionNodeI(String descriptionNodeI) {
        this.descriptionNodeI = descriptionNodeI;
    }

    public void setOidStageI(Long oidStageI) {
        this.oidStageI = oidStageI;
    }
    public void speciesFKStageI(String speciesFKStageI) {
        this.speciesFKStageI = speciesFKStageI;
    }
    public void setNameI(String nameI) {
        this.nameI = nameI;
    }
    public void setSequenceI(Long sequenceI) {
        this.sequenceI = sequenceI;
    }
    public void setDescriptionStageI(String descriptionStageI) {
        this.descriptionStageI = descriptionStageI;
    }
    public void setExtraTextI(String extraTextI) {
        this.extraTextI = extraTextI;
    }
    public void setPublicStageIdI(String publicStageIdI) {
        this.publicStageIdI = publicStageIdI;
    }

    public void setOidRel(Long oidRel) {
        this.oidRel = oidRel;
    }
    public void SetTypeFK(String typeFK) {
        this.typeFK = typeFK;
    }
    public void setChildFK(Long childFK) {
        this.childFK = childFK;
    }
    public void getParentFK(Long parentFK) {
        this.parentFK = parentFK;
    }

    public void setOidII(Long oidNodeII) {
        this.oidNodeII = oidNodeII;
    }
    public void getSpeciesFKNodeII(String speciesFKNodeII) {
        this.speciesFKNodeII = speciesFKNodeII;
    }
    public void setComponentNameII(String componentNameII) {
        this.componentNameII = componentNameII;
    }
    public void setPrimaryII(Boolean primaryII) {
        this.primaryII = primaryII;
    }
    public void setGroupII(Boolean groupII) {
        this.groupII = groupII;
    }
    public void setPublicNodeIdII(String publicNodeIdII) {
        this.publicNodeIdII = publicNodeIdII;
    }
    public void setDescriptionNodeII(String descriptionNodeII) {
        this.descriptionNodeII = descriptionNodeII;
    }

    public void setOidTimedNodeII(Long oidTimedNodeII) {
        this.oidTimedNodeII = oidTimedNodeII;
    }
    public void setNodeFKII(Long nodeFKII) {
        this.nodeFKII = nodeFKII;
    }
    public void setStageFKII(Long stageFKII) {
        this.stageFKII = stageFKII;
    }
    public void setStageModifierFKII(String stageModifierFKII) {
        this.stageModifierFKII = stageModifierFKII;
    }
    public void setPublicTimedNodeIdII(String publicTimedNodeIdII) {
        this.publicTimedNodeIdII = publicTimedNodeIdII;
    }

    public void setOidStageII(Long oidStageII) {
        this.oidStageII = oidStageII;
    }
    public void speciesFKStageII(String speciesFKStageII) {
        this.speciesFKStageII = speciesFKStageII;
    }
    public void setNameII(String nameII) {
        this.nameII = nameII;
    }
    public void setSequenceII(Long sequenceII) {
        this.sequenceII = sequenceII;
    }
    public void setDescriptionStageII(String descriptionStageII) {
        this.descriptionStageII = descriptionStageII;
    }
    public void setExtraTextII(String extraTextII) {
        this.extraTextII = extraTextII;
    }
    public void setPublicStageIdII(String publicStageIdII) {
        this.publicStageIdII = publicStageIdII;
    }
    
    // Override -----------------------------------------------------------------------------------

    /*
     * Returns the String representation of this JOINTimedNodeNodeStageRelationshipNodeTimedNodeStage.
     *  Not required, it just makes reading logs easier.
     */
    public String toString() {
        return String.format("JOINTimedNodeNodeStageRelationshipNodeTimedNodeStage\n" +
        		"TimedNodeI [ oidTimedNodeI=%d, nodeFKI=%d, stageFKI=%d, stageModifierFKI=%s, publicTimedNodeIdI=%s ]" +
        		"NodeI [ oidNodeI=%d, speciesFKI=%s, componentNameI=%s, primaryI=%b, groupI=%b, publicIdI=%s, descriptionNodeI=%s ]" +
        		"StageI [ oidStageI=%d, speciesFKI=%s, nameI=%s, sequenceI=%d, descriptionStageI=%s, extraTextI=%s, publicStageIdI=%s  ]" + 
                "Relationship [ oidRel=%d, typeFK=%s, childFK=%d, parentFK=%d ]" + 
        		"TimedNodeII [ oidTimedNodeII=%d, nodeFKII=%d, stageFKII=%d, stageModifierFKII=%s, publicTimedNodeIdII=%s ]" +
        		"NodeII [ oidNodeII=%d, speciesFKII=%s, componentNameII=%s, primaryII=%b, groupII=%b, publicIdII=%s, descriptionNodeII=%s ]" +
        		"StageII [ oidStageII=%d, speciesFKII=%s, nameII=%s, sequenceII=%d, descriptionStageII=%s, extraTextII=%s, publicStageIdII=%s ]", 
        		oidTimedNodeI, nodeFKI, stageFKI, stageModifierFKI, publicTimedNodeIdI, 
        		oidNodeI, speciesFKNodeI, componentNameI, primaryI, groupI, publicNodeIdI, descriptionNodeI,
        		oidStageI, speciesFKStageI, nameI, sequenceI, descriptionStageI, extraTextI, publicStageIdI,
                oidRel, typeFK, childFK, parentFK,
        		oidTimedNodeII, nodeFKII, stageFKII, stageModifierFKII, publicTimedNodeIdII, 
        		oidNodeII, speciesFKNodeII, componentNameII, primaryII, groupII, publicNodeIdII, descriptionNodeII,
        		oidStageII, speciesFKStageII, nameII, sequenceII, descriptionStageII, extraTextII, publicStageIdII);
    }

}
