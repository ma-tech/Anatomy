package daomodel;

/**
 * This class represents a Data Transfer Object for the Timed Node (Staged - EMAP)
 */
public class ExtraTimedNode {

    // Properties ---------------------------------------------------------------------------------
	/*
	 *  ANA_TIMED_NODE - 
	 *  
     *  Columns:
     *   1.  ATN_OID               - int(10) unsigned 
     *   2.  ATN_NODE_FK           - int(10) unsigned 
     *   3.  ATN_STAGE_FK          - int(10) unsigned 
     *   4.  ATN_STAGE_MODIFIER_FK - varchar(20)      
     *   5.  ATN_PUBLIC_ID         - varchar(20)
     *   6.  ANO_PUBLIC_ID         - varchar(20)
     *   7.  STG_NAME              - varchar(20)
     *   8.  STG_SEQUENCE          - int(10) unsigned 
     *   9.  STAGE_MIN             - varchar(20)
     *   10. STAGE_MAX             - varchar(20)
	 */
    private Long oid; 
    private Long nodeFK; 
    private Long stageFK; 
    private String stageModifierFK;
    private String publicEmapId;
    private String publicEmapaId;
    private String stageName;
    private Long stageSeq;
    private String stageMinName;
    private String stageMaxName;

    // Constructors -------------------------------------------------------------------------------
    /**
     * Default constructor.
     */
    public ExtraTimedNode() {
        // Always keep the default constructor alive in a Javabean class.
    }

    /**
     * Minimal constructor. Contains required fields.
     */
    public ExtraTimedNode(Long oid, 
    		Long nodeFK, 
    		Long stageFK,
    		String stageModifierFK,
    		String publicEmapId,
    		String publicEmapaId,
    		String stageName,
    		Long stageSeq,
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

    /**
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
    public String getPublicEmapId() {
        return publicEmapId;
    }
    public String getPublicEmapaId() {
        return publicEmapaId;
    }
    public String getStageName() {
        return stageName;
    }
    public Long getStageSeq() {
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
    public void setNodeFK(Long nodeFK) {
        this.nodeFK = nodeFK;
    }
    public void setStageFK(Long stageFK) {
        this.stageFK = stageFK;
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
    public void setStageSeq(Long stageSeq) {
        this.stageSeq = stageSeq;
    }
    public void setStageMinName(String stageMinName) {
        this.stageMinName = stageMinName;
    }
    public void setStageMaxName(String stageMaxName) {
        this.stageMaxName = stageMaxName;
    }

    // Override -----------------------------------------------------------------------------------

    /**
     * The relation ID is unique for each Relation. So this should compare Relation by ID only.
     */
    public boolean equals(Object other) {
        return (other instanceof ExtraTimedNode) && (oid != null) 
        		? oid.equals(((ExtraTimedNode) other).oid) 
        		: (other == this);
    }

    /**
     * Returns the String representation of this User. Not required, it just pleases reading logs.
     */
    public String toString() {
        return String.format("ExtraTimedNode [ oid=%d, nodeFK=%d, stageFK=%d, stageModifierFK=%s, publicEmapId=%s, publicEmapaId=%s, stageName=%s, stageSeq=%d, stageMaxName=%s, stageMaxName=%s ]", 
            oid, nodeFK, stageFK, stageModifierFK, publicEmapId, publicEmapaId, stageName, stageSeq, stageMinName, stageMaxName);
    }

}
