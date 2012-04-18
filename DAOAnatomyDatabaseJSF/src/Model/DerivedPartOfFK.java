package Model;

import java.io.Serializable;

/**
 * This class represents a Data Transfer Object for the DerivedPartOfFK. 
 */
public class DerivedPartOfFK implements Serializable {

    // Properties ---------------------------------------------------------------------------------
	/*
	 *  ANA_DerivedPartOfFK - All DerivedPartOfFKs between ABSTRACT Nodes in the Anatomy DAG
     *  
     *  Columns:
     *   1.  APO_OID             => int(10)
     *   2.  APO_SPECIES_FK      => varchar(20)
     *   3.  NODE_START_STAGE    => varchar(255)
     *   4.  NODE_END_STAGE      => varchar(255)
     *   5.  PATH_START_STAGE    => varchar(255)
     *   6.  PATH_END_STAGE      => varchar(255)
     *   7.  NODE_ID             => int(10)
     *   8.  APO_SEQUENCE        => int(10)
     *   9.  APO_DEPTH           => int(10)
     *   10. APO_FULL_PATH           => varchar(500)
     *   11. APO_FULL_PATH_OIDS      => varchar(500)
     *   12. APO_FULL_PATH_JSON_HEAD => varchar(3000)
     *   13. APO_FULL_PATH_JSON_TAIL => varchar(500)
     *   14. APO_IS_PRIMARY          => tinyint(1)
     *   15. APO_IS_PRIMARY_PATH     => tinyint(1)
     *   16. APO_PARENT_APO_FK       => int(10)
	 */
    private Long oid; 
    private String speciesFK; 
    private String nodeStart; 
    private String nodeStop;
    private String pathStart; 
    private String pathStop;
    private String node; 
    private Long sequence; 
    private Long depth; 
    private String fullPath; 
    private String fullPathOids; 
    private String fullPathJsonHead; 
    private String fullPathJsonTail; 
    private int primary; 
    private int primaryPath; 
    private Long parentFK;

    // Constructors -------------------------------------------------------------------------------
    /**
     * Default constructor.
     */
    public DerivedPartOfFK() {
        // Always keep the default constructor alive in a Javabean class.
    }

    /**
     * Minimal constructor. Contains required fields.
     * Full constructor. Contains required and optional fields.
     *  The Full Constructor is the Minimal Constructor
     * 
     */
    public DerivedPartOfFK(Long oid,
    		String speciesFK, 
    		String nodeStart, 
    		String nodeStop,
    		String pathStart, 
    		String pathStop,
    		String node, 
    		Long sequence, 
    		Long depth, 
    		String fullPath, 
    		String fullPathOids, 
    		String fullPathJsonHead, 
    		String fullPathJsonTail, 
    		int primary, 
    		int primaryPath, 
    		Long parentFK) {
    	
    	this.oid = oid;
    	this.speciesFK   = speciesFK;
    	this.nodeStart   = nodeStart;
    	this.nodeStop    = nodeStop;
    	this.pathStart   = pathStart;
    	this.pathStop    = pathStop;
    	this.node        = node;
    	this.sequence    = sequence;
    	this.depth       = depth;
    	this.fullPath = fullPath;
    	this.fullPathOids = fullPathOids;
    	this.fullPathJsonHead = fullPathJsonHead;
    	this.fullPathJsonTail = fullPathJsonTail;
    	this.primary     = primary;
    	this.primaryPath = primaryPath;
    	this.parentFK    = parentFK;
    }

    // SPECIAL Getters ------------------------------------------------------------------------------------
    public String getFullPathJson() {
        return fullPathJsonHead + fullPathJsonTail;
    } 

    // Getters ------------------------------------------------------------------------------------
    public Long getOid() {
        return oid;
    }
    public String getSpeciesFK() {
        return speciesFK;
    } 
    public String getNodeStart() {
        return nodeStart;
    } 
    public String getNodeStop() {
        return nodeStop;
    }
    public String getPathStart() {
        return pathStart;
    } 
    public String getPathStop() {
        return pathStop;
    }
    public String getNode() {
        return node;
    } 
    public Long getSequence() {
        return sequence;
    } 
    public Long getDepth() {
        return depth;
    } 
    public String getFullPath() {
        return fullPath;
    } 
    public String getFullPathOids() {
        return fullPathOids;
    } 
    public String getFullPathJsonHead() {
        return fullPathJsonHead;
    } 
    public String getFullPathJsonTail() {
        return fullPathJsonTail;
    } 
    public Integer getPrimary() {
        return primary;
    } 
    public Integer getPrimaryPath() {
        return primaryPath;
    } 
    public Long getParentFK() {
        return parentFK;
    } 

    // Setters ------------------------------------------------------------------------------------
    public void setOid(Long oid) {
        this.oid = oid;
    }
    public void setSpeciesFK(String speciesFK) {
        this.speciesFK = speciesFK;
    } 
    public void setNodeStart(String nodeStart) {
        this.nodeStart = nodeStart;
    } 
    public void setNodeStop(String nodeStop) {
        this.nodeStop = nodeStop;
    }
    public void setPathStart(String pathStart) {
        this.pathStart = pathStart;
    } 
    public void setPathStop(String pathStop) {
        this.pathStop = pathStop;
    }
    public void setNode(String node) {
        this.node = node;
    } 
    public void setSequence(Long sequence) {
        this.sequence = sequence;
    } 
    public void setDepth(Long depth) {
        this.depth = depth;
    } 
    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    } 
    public void setFullPathOids(String fullPathOids) {
        this.fullPathOids = fullPathOids;
    } 
    public void setFullPathJsonHead(String fullPathJsonHead) {
        this.fullPathJsonHead = fullPathJsonHead;
    } 
    public void setFullPathJsonTail(String fullPathJsonTail) {
        this.fullPathJsonTail = fullPathJsonTail;
    } 
    public void setPrimary(int primary) {
        this.primary = primary;
    } 
    public void setPrimaryPath(int primaryPath) {
        this.primaryPath = primaryPath;
    } 
    public void setParentFK(Long parentFK) {
        this.parentFK = parentFK;
    } 

    // Override -----------------------------------------------------------------------------------
    /**
     * The relation ID is unique for each DerivedPartOfFK. 
     *  So this should compare DerivedPartOfFK by ID only.
     */
    public boolean equals(Object other) {
        return (other instanceof DerivedPartOfFK) && (oid != null) 
        		? oid.equals(((DerivedPartOfFK) other).oid) 
        		: (other == this);
    }

    /**
     * Returns the String representation of this DerivedPartOfFK.
     *  Not required, it just aids log reading.
     */
    public String toString() {
        return String.format("DerivedPartOfFK [ oid=%d, speciesFK=%s, nodeStart=%s, nodeStop=%s, pathStart=%s, pathStop=%s, node=%s, sequence=%d, depth=%d, fullPath=%s, fullPathOids=%s, fullPathJsonHead=%s, fullPathJsonTail=%s, primary=%d, primaryPath=%d, parentFK=%d ]", 
        		oid, speciesFK, nodeStart, nodeStop, pathStart, pathStop, node, sequence, depth, fullPath, fullPathOids, fullPathJsonHead, fullPathJsonTail, primary, primaryPath, parentFK);

    }
}
