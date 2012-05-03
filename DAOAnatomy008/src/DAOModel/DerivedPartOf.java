/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomy008
*
* Title:        DerivedPartOf.java
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
*                DerivedPartOf Table - ANAD_PART_OF
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

import java.io.Serializable;

public class DerivedPartOf implements Serializable {
    // Properties ---------------------------------------------------------------------------------
	/*
     *  Columns:
     *   1.  APO_OID                 => int(10)
     *   2.  APO_SPECIES_FK          => varchar(20)
     *   3.  APO_NODE_START_STAGE_FK => int(10)
     *   4.  APO_NODE_END_STAGE_FK   => int(10)
     *   5.  APO_PATH_START_STAGE_FK => int(10)
     *   6.  APO_PATH_END_STAGE_FK   => int(10)
     *   7.  APO_NODE_FK             => int(10)
     *   8.  APO_SEQUENCE            => int(10)
     *   9.  APO_DEPTH               => int(10)
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
    private Long nodeStartFK; 
    private Long nodeStopFK;
    private Long pathStartFK; 
    private Long pathStopFK;
    private Long nodeFK; 
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
    /*
     * Default constructor.
     */
    public DerivedPartOf() {
        // Always keep the default constructor alive in a Javabean class.
    }

    /*
     * Minimal constructor. Contains required fields.
     * Full constructor. Contains required and optional fields.
     *  The Full Constructor is the Minimal Constructor
     */
    public DerivedPartOf(Long oid,
    		String speciesFK, 
    		Long nodeStartFK, 
    		Long nodeStopFK,
    		Long pathStartFK, 
    		Long pathStopFK,
    		Long nodeFK, 
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
    	this.speciesFK = speciesFK;
    	this.nodeStartFK = nodeStartFK;
    	this.nodeStopFK = nodeStopFK;
    	this.pathStartFK = pathStartFK;
    	this.pathStopFK = pathStopFK;
    	this.nodeFK = nodeFK;
    	this.sequence = sequence;
    	this.depth = depth;
    	this.fullPath = fullPath;
    	this.fullPathOids = fullPathOids;
    	this.fullPathJsonHead = fullPathJsonHead;
    	this.fullPathJsonTail = fullPathJsonTail;
    	this.primary = primary;
    	this.primaryPath = primaryPath;
    	this.parentFK = parentFK;
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
    public Long getNodeStartFK() {
        return nodeStartFK;
    } 
    public Long getNodeStopFK() {
        return nodeStopFK;
    }
    public Long getPathStartFK() {
        return pathStartFK;
    } 
    public Long getPathStopFK() {
        return pathStopFK;
    }
    public Long getNodeFK() {
        return nodeFK;
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
    public void setNodeStartFK(Long nodeStartFK) {
        this.nodeStartFK = nodeStartFK;
    } 
    public void setNodeStopFK(Long nodeStopFK) {
        this.nodeStopFK = nodeStopFK;
    }
    public void setPathStartFK(Long pathStartFK) {
        this.pathStartFK = pathStartFK;
    } 
    public void setPathStopFK(Long pathStopFK) {
        this.pathStopFK = pathStopFK;
    }
    public void setNodeFK(Long nodeFK) {
        this.nodeFK = nodeFK;
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
    /*
     * The OID is unique for each DerivedPartOf. 
     *  So this should compare DerivedPartOf by OID only.
     */
    public boolean equals(Object other) {
        return (other instanceof DerivedPartOf) && (oid != null) 
        		? oid.equals(((DerivedPartOf) other).oid) 
        		: (other == this);
    }

    /*
     * Returns the String representation of this DerivedPartOf.
     *  Not required, it just aids log reading.
     */
    public String toString() {
        return String.format("DerivedPartOf [ oid=%d, speciesFK=%s, nodeStartFK=%d, nodeStopFK=%d, pathStartFK=%d, pathStopFK=%d, nodeFK=%d, sequence=%d, depth=%d, fullPath=%s, fullPathOids=%s, fullPathJsonHead=%s, fullPathJsonTail=%s, primary=%d, primaryPath=%d, parentFK=%d ]", 
        		oid, speciesFK, nodeStartFK, nodeStopFK, pathStartFK, pathStopFK, nodeFK, sequence, depth, fullPath, fullPathOids, fullPathJsonHead, fullPathJsonTail, primary, primaryPath, parentFK);
    }
}
