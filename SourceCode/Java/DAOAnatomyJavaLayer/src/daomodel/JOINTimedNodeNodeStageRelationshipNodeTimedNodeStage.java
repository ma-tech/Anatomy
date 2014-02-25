/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayer
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
* Version:      1
*
* Description:  This class represents a SQL Database Transfer Object for the 
*                JOINTimedNodeNodeStageRelationshipNodeTimedNodeStage "Table".
*                
*                ANA_TIMED_NODE   - I
*                ANA_NODE         - I
*                ANA_STAGE        - I
*                ANA_RELATIONSHIP 
*                ANA_NODE         - II
*                ANA_TIMED_NODE   - II 
*                ANA_STAGE        - II
*                
*                Joined on 
*                
*                 FROM ANA_TIMED_NODE   a 
*        		  JOIN ANA_NODE         b  ON b.ANO_OID       = a.ATN_NODE_FK
*                 JOIN ANA_STAGE        c  ON c.STG_OID       = a.ATN_STAGE_FK
*                 JOIN ANA_RELATIONSHIP d  ON d.REL_CHILD_FK  = b.ANO_OID
*                 JOIN ANA_NODE         f  ON d.REL_PARENT_FK = f.ANO_OID
*                 JOIN ANA_TIMED_NODE   e  ON f.ANO_OID       = e.ATN_NODE_FK
*                 JOIN ANA_STAGE        g  ON g.STG_OID       = e.ATN_STAGE_FK
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

public class JOINTimedNodeNodeStageRelationshipNodeTimedNodeStage {
    // Properties ---------------------------------------------------------------------------------
	/*
     *   1. ATN_OID                  - int(10) unsigned 
     *   2. ATN_NODE_FK              - int(10) unsigned 
     *   3. ATN_STAGE_FK             - int(10) unsigned 
     *   4. ATN_STAGE_MODIFIER_FK    - varchar(20)      
     *   5. ATN_PUBLIC_ID            - varchar(20)
     *   6. ATN_DISPLAY_ID           - varchar(20)      
     *         
     *   1. ANO_OID                  - int(10) unsigned 
     *   2. ANO_SPECIES_FK           - varchar(20)      
     *   3. ANO_COMPONENT_NAME       - varchar(255)     
     *   4. ANO_IS_PRIMARY           - tinyint(1)       
     *   5. ANO_IS_GROUP             - tinyint(1)       
     *   6. ANO_PUBLIC_ID            - varchar(20)      
     *   7. ANO_DESCRIPTION          - varchar(2000)    
     *   
     *   1. STG_OID                  - int(10) unsigned
     *   2. STG_SPECIES_FK           - varchar(20)      
     *   3. STG_NAME                 - varchar(20)      
     *   4. STG_SEQUENCE             - int(10) unsigned 
     *   5. STG_DESCRIPTION          - varchar(2000)    
     *   6. STG_SHORT_EXTRA_TEXT     - varchar(25)      
     *   7. STG_PUBLIC_ID            - varchar(20)
     *         
     *   1. REL_OID                  - int(10) unsigned 
     *   2. REL_RELATIONSHIP_TYPE_FK - varchar(20)      
     *   3. REL_CHILD_FK             - int(10) unsigned 
     *   4. REL_PARENT_FK            - int(10) unsigned
     *    
     *   1. ATN_OID                  - int(10) unsigned 
     *   2. ATN_NODE_FK              - int(10) unsigned 
     *   3. ATN_STAGE_FK             - int(10) unsigned 
     *   4. ATN_STAGE_MODIFIER_FK    - varchar(20)      
     *   5. ATN_PUBLIC_ID            - varchar(20)
     *   6. ATN_DISPLAY_ID           - varchar(20)      
     *         
     *   1. ANO_OID                  - int(10) unsigned 
     *   2. ANO_SPECIES_FK           - varchar(20)      
     *   3. ANO_COMPONENT_NAME       - varchar(255)     
     *   4. ANO_IS_PRIMARY           - tinyint(1)       
     *   5. ANO_IS_GROUP             - tinyint(1)       
     *   6. ANO_PUBLIC_ID            - varchar(20)      
     *   7. ANO_DESCRIPTION          - varchar(2000)
     *
     *   1. STG_OID                  - int(10) unsigned
     *   2. STG_SPECIES_FK           - varchar(20)      
     *   3. STG_NAME                 - varchar(20)      
     *   4. STG_SEQUENCE             - int(10) unsigned 
     *   5. STG_DESCRIPTION          - varchar(2000)    
     *   6. STG_SHORT_EXTRA_TEXT     - varchar(25)      
     *   7. STG_PUBLIC_ID            - varchar(20)
     */
    private Long oidTimedNodeI; 
    private long nodeFKI; 
    private long stageFKI; 
    private String stageModifierFKI;
    private String publicTimedNodeIdI;
    private String displayTimedNodeIdI;
    private Long oidNodeI; 
    private String speciesFKNodeI; 
    private String componentNameI; 
    private boolean primaryI;
    private boolean groupI;
    private String publicNodeIdI; 
    private String descriptionNodeI; 
    private Long oidStageI; 
    private String speciesFKStageI; 
    private String nameI; 
    private long sequenceI;
    private String descriptionStageI;
    private String extraTextI; 
    private String publicStageIdI; 
    private Long oidRel; 
    private String typeFK; 
    private long childFK; 
    private long parentFK;
    private Long oidTimedNodeII; 
    private long nodeFKII; 
    private long stageFKII; 
    private String stageModifierFKII;
    private String publicTimedNodeIdII;
    private String displayTimedNodeIdII;
    private Long oidNodeII; 
    private String speciesFKNodeII; 
    private String componentNameII; 
    private boolean primaryII;
    private boolean groupII;
    private String publicNodeIdII; 
    private String descriptionNodeII; 
    private Long oidStageII; 
    private String speciesFKStageII; 
    private String nameII; 
    private long sequenceII;
    private String descriptionStageII;
    private String extraTextII; 
    private String publicStageIdII; 

    // Constructors -------------------------------------------------------------------------------
    /*
     * Default constructor.
     */
    public JOINTimedNodeNodeStageRelationshipNodeTimedNodeStage() {
        
    }

    /*
     * Minimal constructor. Contains required fields.
     */
    public JOINTimedNodeNodeStageRelationshipNodeTimedNodeStage(Long oidTimedNodeI, 
    		long nodeFKI, 
    		long stageFKI, 
    		String stageModifierFKI,
    		String publicTimedNodeIdI,
    		String displayTimedNodeIdI,
    		Long oidNodeI, 
    		String speciesFKNodeI, 
    		String componentNameI, 
    		boolean primaryI,
    		boolean groupI,
    		String publicNodeIdI, 
    		String descriptionNodeI, 
    		Long oidStageI, 
    		String speciesFKStageI, 
    		String nameI, 
    		long sequenceI,
    		String descriptionStageI,
    		String extraTextI, 
    		String publicStageIdI, 
    		Long oidRel, 
    		String typeFK, 
    		long childFK, 
    		long parentFK,
    		Long oidTimedNodeII, 
    		long nodeFKII, 
    		long stageFKII, 
    		String stageModifierFKII,
    		String publicTimedNodeIdII,
    		String displayTimedNodeIdII,
    		Long oidNodeII, 
    		String speciesFKNodeII, 
    		String componentNameII, 
    		boolean primaryII,
    		boolean groupII,
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
    	this.displayTimedNodeIdI = displayTimedNodeIdI;
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
    	this.displayTimedNodeIdII = displayTimedNodeIdII;
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
     * Minimal constructor. Contains required fields.
     */
    public JOINTimedNodeNodeStageRelationshipNodeTimedNodeStage(Long oidTimedNodeI, 
    		String nodeFKI, 
    		String stageFKI, 
    		String stageModifierFKI,
    		String publicTimedNodeIdI,
    		String displayTimedNodeIdI,
    		Long oidNodeI, 
    		String speciesFKNodeI, 
    		String componentNameI, 
    		String primaryI,
    		String groupI,
    		String publicNodeIdI, 
    		String descriptionNodeI, 
    		Long oidStageI, 
    		String speciesFKStageI, 
    		String nameI, 
    		String sequenceI,
    		String descriptionStageI,
    		String extraTextI, 
    		String publicStageIdI, 
    		Long oidRel, 
    		String typeFK, 
    		String childFK, 
    		String parentFK,
    		Long oidTimedNodeII, 
    		String nodeFKII, 
    		String stageFKII, 
    		String stageModifierFKII,
    		String publicTimedNodeIdII,
    		String displayTimedNodeIdII,
    		Long oidNodeII, 
    		String speciesFKNodeII, 
    		String componentNameII, 
    		String primaryII,
    		String groupII,
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
    	this.nodeFKI = ObjectConverter.convert(nodeFKI, Long.class);
    	this.stageFKI = ObjectConverter.convert(stageFKI, Long.class);
    	this.stageModifierFKI = stageModifierFKI;
    	this.publicTimedNodeIdI = publicTimedNodeIdI;
    	this.displayTimedNodeIdI = displayTimedNodeIdI;
    	this.oidNodeI = oidNodeI;
    	this.speciesFKNodeI = speciesFKNodeI;
    	this.componentNameI = componentNameI;
    	this.primaryI = ObjectConverter.convert(primaryI, Boolean.class);
    	this.groupI = ObjectConverter.convert(groupI, Boolean.class);
    	this.publicNodeIdI = publicNodeIdI;
    	this.descriptionNodeI = descriptionNodeI;
    	this.oidStageI = oidStageI;
    	this.speciesFKStageI = speciesFKStageI;
    	this.nameI = nameI;
    	this.sequenceI = ObjectConverter.convert(sequenceI, Long.class);
    	this.descriptionStageI = descriptionStageI;
    	this.extraTextI = extraTextI;
    	this.publicStageIdI = publicStageIdI;
    	this.oidRel = oidRel;
    	this.typeFK = typeFK;
    	this.childFK = ObjectConverter.convert(childFK, Long.class);
    	this.parentFK = ObjectConverter.convert(parentFK, Long.class);
    	this.oidTimedNodeII = oidTimedNodeII;
    	this.nodeFKII = ObjectConverter.convert(nodeFKII, Long.class);
    	this.stageFKII = ObjectConverter.convert(stageFKII, Long.class);
    	this.stageModifierFKII = stageModifierFKII;
    	this.publicTimedNodeIdII = publicTimedNodeIdII;
    	this.displayTimedNodeIdII = displayTimedNodeIdII;
    	this.oidNodeII = oidNodeII;
    	this.speciesFKNodeII = speciesFKNodeII;
    	this.componentNameII = componentNameII;
    	this.primaryII = ObjectConverter.convert(primaryII, Boolean.class);
    	this.groupII = ObjectConverter.convert(groupII, Boolean.class);
    	this.publicNodeIdII = publicNodeIdII;
    	this.descriptionNodeII = descriptionNodeII;
    	this.speciesFKStageII = speciesFKStageII;
    	this.nameII = nameII;
    	this.sequenceII = sequenceII;
    	this.descriptionStageII = descriptionStageII;
    	this.extraTextII = extraTextII;
    	this.publicStageIdII = publicStageIdII;
    }

    // Getters ------------------------------------------------------------------------------------
    public Long getOidTimedNodeI() {
        return oidTimedNodeI;
    }
    public long getNodeFKI() {
        return nodeFKI;
    }
    public String getNodeFKIAsString() {
        return ObjectConverter.convert(nodeFKI, String.class);
    }
    public long getStageFKI() {
        return stageFKI;
    }
    public String getStageFKIAsString() {
        return ObjectConverter.convert(stageFKI, String.class);
    }
    public String getStageModifierFKI() {
        return stageModifierFKI;
    }
    public String getPublicTimedNodeIdI() {
        return publicTimedNodeIdI;
    }
    public String getDisplayTimedNodeIdI() {
        return displayTimedNodeIdI;
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
    public boolean isPrimaryI() {
        return primaryI;
    }
    public String getPrimaryI() {
        return ObjectConverter.convert(primaryI, String.class);
    }
    public boolean isGroupI() {
        return groupI;
    }
    public String getGroupI() {
        return ObjectConverter.convert(groupI, String.class);
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
    public long getSequenceI() {
        return sequenceI;
    }
    public String getSequenceIAsString() {
        return ObjectConverter.convert(sequenceI, String.class);
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
    public long getChildFK() {
        return childFK;
    }
    public String getChildFKAsString() {
        return ObjectConverter.convert(childFK, String.class);
    }
    public long getParentFK() {
        return parentFK;
    }
    public String getParentFKAsString() {
        return ObjectConverter.convert(parentFK, String.class);
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
    public boolean isPrimaryII() {
        return primaryII;
    }
    public String getPrimaryII() {
        return ObjectConverter.convert(primaryII, String.class);
    }
    public boolean isGroupII() {
        return groupII;
    }
    public String getGroupII() {
        return ObjectConverter.convert(groupII, String.class);
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
    public long getNodeFKII() {
        return nodeFKII;
    }
    public String getNodeFKIIAsString() {
        return ObjectConverter.convert(nodeFKII, String.class);
    }
    public long getStageFKII() {
        return stageFKII;
    }
    public String getStageFKIIAsString() {
        return ObjectConverter.convert(stageFKII, String.class);
    }
    public String getStageModifierFKII() {
        return stageModifierFKII;
    }
    public String getPublicTimedNodeIdII() {
        return publicTimedNodeIdII;
    }
    public String getDisplayTimedNodeIdII() {
        return displayTimedNodeIdII;
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
    public long getSequenceII() {
        return sequenceII;
    }
    public String getSequenceIIAsString() {
        return ObjectConverter.convert(sequenceII, String.class);
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
    public void setNodeFKI(long nodeFKI) {
        this.nodeFKI = nodeFKI;
    }
    public void setNodeFKI(String nodeFKI) {
        this.nodeFKI = ObjectConverter.convert(nodeFKI, Long.class);
    }
    public void setStageFKI(long stageFKI) {
        this.stageFKI = stageFKI;
    }
    public void setStageFKI(String stageFKI) {
        this.stageFKI = ObjectConverter.convert(stageFKI, Long.class);
    }
    public void setStageModifierFKI(String stageModifierFKI) {
        this.stageModifierFKI = stageModifierFKI;
    }
    public void setPublicTimedNodeIdI(String publicTimedNodeIdI) {
        this.publicTimedNodeIdI = publicTimedNodeIdI;
    }
    public void setDisplayTimedNodeIdI(String displayTimedNodeIdI) {
        this.displayTimedNodeIdI = displayTimedNodeIdI;
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
    public void setPrimaryI(boolean primaryI) {
        this.primaryI = primaryI;
    }
    public void setPrimaryI(String primaryI) {
        this.primaryI = ObjectConverter.convert(primaryI, Boolean.class);
    }
    public void setGroupI(boolean groupI) {
        this.groupI = groupI;
    }
    public void setGroupI(String groupI) {
        this.groupI = ObjectConverter.convert(groupI, Boolean.class);
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
    public void setSequenceI(long sequenceI) {
        this.sequenceI = sequenceI;
    }
    public void setSequenceI(String sequenceI) {
        this.sequenceI = ObjectConverter.convert(sequenceI, Long.class);
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
    public void setChildFK(long childFK) {
        this.childFK = childFK;
    }
    public void setChildFK(String childFK) {
        this.childFK = ObjectConverter.convert(childFK, Long.class);
    }
    public void getParentFK(long parentFK) {
        this.parentFK = parentFK;
    }
    public void getParentFK(String parentFK) {
        this.parentFK = ObjectConverter.convert(parentFK, Long.class);
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
    public void setPrimaryII(boolean primaryII) {
        this.primaryII = primaryII;
    }
    public void setPrimaryII(String primaryII) {
        this.primaryII = ObjectConverter.convert(primaryII, Boolean.class);
    }
    public void setGroupII(boolean groupII) {
        this.groupII = groupII;
    }
    public void setGroupII(String groupII) {
        this.groupII = ObjectConverter.convert(groupII, Boolean.class);
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
    public void setNodeFKII(long nodeFKII) {
        this.nodeFKII = nodeFKII;
    }
    public void setNodeFKII(String nodeFKII) {
        this.nodeFKII = ObjectConverter.convert(nodeFKII, Long.class);
    }
    public void setStageFKII(long stageFKII) {
        this.stageFKII = stageFKII;
    }
    public void setStageFKII(String stageFKII) {
        this.stageFKII = ObjectConverter.convert(stageFKII, Long.class);
    }
    public void setStageModifierFKII(String stageModifierFKII) {
        this.stageModifierFKII = stageModifierFKII;
    }
    public void setPublicTimedNodeIdII(String publicTimedNodeIdII) {
        this.publicTimedNodeIdII = publicTimedNodeIdII;
    }
    public void setDisplayTimedNodeIdII(String displayTimedNodeIdII) {
        this.displayTimedNodeIdII = displayTimedNodeIdII;
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
    public void setSequenceII(long sequenceII) {
        this.sequenceII = sequenceII;
    }
    public void setSequenceII(String sequenceII) {
        this.sequenceII = ObjectConverter.convert(sequenceII, Long.class);
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
    
    // Helper -------------------------------------------------------------------------------------
    /*
     * Is this JOINTimedNodeNodeStageRelationshipNodeTimedNodeStage the same as the 
     *  Supplied JOINTimedNodeNodeStageRelationshipNodeTimedNodeStage?
     */
    public boolean isSameAs(JOINTimedNodeNodeStageRelationshipNodeTimedNodeStage daojointimednodenodestagerelationshipnodetimednodestage){

    	if (this.getOidTimedNodeI() == daojointimednodenodestagerelationshipnodetimednodestage.getOidTimedNodeI() &&
    		this.getNodeFKI() == daojointimednodenodestagerelationshipnodetimednodestage.getNodeFKI() &&
    		this.getStageFKI() == daojointimednodenodestagerelationshipnodetimednodestage.getStageFKI() &&
    		this.getStageModifierFKI().equals(daojointimednodenodestagerelationshipnodetimednodestage.getStageModifierFKI()) && 
    		this.getPublicTimedNodeIdI().equals(daojointimednodenodestagerelationshipnodetimednodestage.getPublicTimedNodeIdI()) && 
    		this.getDisplayTimedNodeIdI().equals(daojointimednodenodestagerelationshipnodetimednodestage.getDisplayTimedNodeIdI()) && 
    		this.getOidNodeI().equals(daojointimednodenodestagerelationshipnodetimednodestage.getOidNodeI()) &&
    		this.getSpeciesFKNodeI().equals(daojointimednodenodestagerelationshipnodetimednodestage.getSpeciesFKNodeI()) && 
    		this.getComponentNameI().equals(daojointimednodenodestagerelationshipnodetimednodestage.getComponentNameI()) && 
    		this.isPrimaryI() == daojointimednodenodestagerelationshipnodetimednodestage.isPrimaryI() &&
    		this.isGroupI() == daojointimednodenodestagerelationshipnodetimednodestage.isGroupI() &&
    		this.getPublicNodeIdI().equals(daojointimednodenodestagerelationshipnodetimednodestage.getPublicNodeIdI()) && 
    		this.getDescriptionNodeI().equals(daojointimednodenodestagerelationshipnodetimednodestage.getDescriptionNodeI()) && 
    		this.getOidStageI().equals(daojointimednodenodestagerelationshipnodetimednodestage.getOidStageI()) &&
    		this.getSpeciesFKStageI().equals(daojointimednodenodestagerelationshipnodetimednodestage.getSpeciesFKStageI()) && 
    		this.getNameI().equals(daojointimednodenodestagerelationshipnodetimednodestage.getNameI()) && 
    		this.getSequenceI() == daojointimednodenodestagerelationshipnodetimednodestage.getSequenceI() &&
    		this.getDescriptionStageI().equals(daojointimednodenodestagerelationshipnodetimednodestage.getDescriptionStageI()) && 
    		this.getExtraTextI().equals(daojointimednodenodestagerelationshipnodetimednodestage.getExtraTextI()) && 
    		this.getPublicStageIdI().equals(daojointimednodenodestagerelationshipnodetimednodestage.getPublicStageIdI()) && 
    		this.getOidRel().equals(daojointimednodenodestagerelationshipnodetimednodestage.getOidRel()) &&
    		this.getTypeFK().equals(daojointimednodenodestagerelationshipnodetimednodestage.getTypeFK()) && 
    		this.getChildFK() == daojointimednodenodestagerelationshipnodetimednodestage.getChildFK() &&
    		this.getParentFK() == daojointimednodenodestagerelationshipnodetimednodestage.getParentFK() &&
    		this.getOidNodeII().equals(daojointimednodenodestagerelationshipnodetimednodestage.getOidNodeII()) &&
    		this.getSpeciesFKNodeII().equals(daojointimednodenodestagerelationshipnodetimednodestage.getSpeciesFKNodeII()) && 
    		this.getComponentNameII().equals(daojointimednodenodestagerelationshipnodetimednodestage.getComponentNameII()) && 
    		this.isPrimaryII() == daojointimednodenodestagerelationshipnodetimednodestage.isPrimaryII() &&
    		this.isGroupII() == daojointimednodenodestagerelationshipnodetimednodestage.isGroupII() &&
    		this.getPublicNodeIdII().equals(daojointimednodenodestagerelationshipnodetimednodestage.getPublicNodeIdII()) && 
    		this.getDescriptionNodeII().equals(daojointimednodenodestagerelationshipnodetimednodestage.getDescriptionNodeII()) && 
    		this.getOidTimedNodeII().equals(daojointimednodenodestagerelationshipnodetimednodestage.getOidTimedNodeII()) &&
    		this.getNodeFKII() == daojointimednodenodestagerelationshipnodetimednodestage.getNodeFKII() &&
    		this.getStageFKII() == daojointimednodenodestagerelationshipnodetimednodestage.getStageFKII() &&
    		this.getStageModifierFKII().equals(daojointimednodenodestagerelationshipnodetimednodestage.getStageModifierFKII()) && 
    		this.getPublicTimedNodeIdII().equals(daojointimednodenodestagerelationshipnodetimednodestage.getPublicTimedNodeIdII()) && 
    		this.getDisplayTimedNodeIdII().equals(daojointimednodenodestagerelationshipnodetimednodestage.getDisplayTimedNodeIdII()) && 
    		this.getOidStageII().equals(daojointimednodenodestagerelationshipnodetimednodestage.getOidStageII()) &&
    		this.getSpeciesFKStageII().equals(daojointimednodenodestagerelationshipnodetimednodestage.getSpeciesFKStageII()) && 
    		this.getNameII().equals(daojointimednodenodestagerelationshipnodetimednodestage.getNameII()) && 
    		this.getSequenceII() == daojointimednodenodestagerelationshipnodetimednodestage.getSequenceII() &&
    		this.getDescriptionStageII().equals(daojointimednodenodestagerelationshipnodetimednodestage.getDescriptionStageII()) && 
    		this.getExtraTextII().equals(daojointimednodenodestagerelationshipnodetimednodestage.getExtraTextII()) && 
    		this.getPublicStageIdII().equals(daojointimednodenodestagerelationshipnodetimednodestage.getPublicStageIdII()) ) {
    		
        	return true;
        }
        else {

        	return false;
        }
    }

    /*
     * Returns the String representation of this JOINTimedNodeNodeStageRelationshipNodeTimedNodeStage.
     *  Not required, it just makes reading logs easier.
     */
    public String toString() {
    	
        return String.format("JOINTimedNodeNodeStageRelationshipNodeTimedNodeStage\n" +
        		"TimedNodeI [ oidTimedNodeI=%d, nodeFKI=%d, stageFKI=%d, stageModifierFKI=%s, publicTimedNodeIdI=%s, displayTimedNodeIdI=%s ]\n" +
        		"NodeI [ oidNodeI=%d, speciesFKI=%s, componentNameI=%s, primaryI=%b, groupI=%b, publicIdI=%s, descriptionNodeI=%s ]\n" +
        		"StageI [ oidStageI=%d, speciesFKI=%s, nameI=%s, sequenceI=%d, descriptionStageI=%s, extraTextI=%s, publicStageIdI=%s  ]\n" + 
                "Relationship [ oidRel=%d, typeFK=%s, childFK=%d, parentFK=%d ]\n" + 
        		"TimedNodeII [ oidTimedNodeII=%d, nodeFKII=%d, stageFKII=%d, stageModifierFKII=%s, publicTimedNodeIdII=%s, displayTimedNodeIdII=%s, oldPublicTimedNodeIdII=%s, oldDisplayTimedNodeIdII=%s ]\n" +
        		"NodeII [ oidNodeII=%d, speciesFKII=%s, componentNameII=%s, primaryII=%b, groupII=%b, publicIdII=%s, descriptionNodeII=%s ]\n" +
        		"StageII [ oidStageII=%d, speciesFKII=%s, nameII=%s, sequenceII=%d, descriptionStageII=%s, extraTextII=%s, publicStageIdII=%s ]", 
        		oidTimedNodeI, nodeFKI, stageFKI, stageModifierFKI, publicTimedNodeIdI, displayTimedNodeIdI,  
        		oidNodeI, speciesFKNodeI, componentNameI, primaryI, groupI, publicNodeIdI, descriptionNodeI,
        		oidStageI, speciesFKStageI, nameI, sequenceI, descriptionStageI, extraTextI, publicStageIdI,
                oidRel, typeFK, childFK, parentFK,
        		oidTimedNodeII, nodeFKII, stageFKII, stageModifierFKII, publicTimedNodeIdII, displayTimedNodeIdII, 
        		oidNodeII, speciesFKNodeII, componentNameII, primaryII, groupII, publicNodeIdII, descriptionNodeII,
        		oidStageII, speciesFKStageII, nameII, sequenceII, descriptionStageII, extraTextII, publicStageIdII);
    }
}
