/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayer
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
* Version:      1
*
* Description:  This class represents a SQL Database Transfer Object for the 
*                DerivedPartOf Table - ANAD_PART_OF
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

public class DerivedPartOf {
    // Properties ---------------------------------------------------------------------------------
	/*
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
     *   12. APO_FULL_PATH_EMAPAS    => varchar(500)
     *   12. APO_FULL_PATH_JSON_HEAD => varchar(3000)
     *   13. APO_FULL_PATH_JSON_TAIL => varchar(500)
     *   14. APO_IS_PRIMARY          => tinyint(1)
     *   15. APO_IS_PRIMARY_PATH     => tinyint(1)
     *   16. APO_PARENT_APO_FK       => int(10)
	 */
    private Long oid; 
    private String speciesFK; 
    private long nodeStartFK; 
    private long nodeStopFK;
    private long pathStartFK; 
    private long pathStopFK;
    private long nodeFK; 
    private long sequence; 
    private long depth; 
    private String fullPath; 
    private String fullPathOids; 
    private String fullPathEmapas; 
    private String fullPathJsonHead; 
    private String fullPathJsonTail; 
    private boolean primary; 
    private boolean primaryPath; 
    private long parentFK;

    // Constructors -------------------------------------------------------------------------------
    /*
     * Default constructor.
     */
    public DerivedPartOf() {
        
    }

    /*
     * Minimal constructor. Contains required fields.
     * Full constructor. Contains required and optional fields.
     *  The Full Constructor is the Minimal Constructor
     */
    public DerivedPartOf(Long oid,
    		String speciesFK, 
    		long nodeStartFK, 
    		long nodeStopFK,
    		long pathStartFK, 
    		long pathStopFK,
    		long nodeFK, 
    		long sequence, 
    		long depth, 
    		String fullPath, 
    		String fullPathOids, 
    		String fullPathEmapas, 
    		String fullPathJsonHead, 
    		String fullPathJsonTail, 
    		boolean primary, 
    		boolean primaryPath, 
    		long parentFK) {
    	
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
    	this.fullPathEmapas = fullPathEmapas;
    	this.fullPathJsonHead = fullPathJsonHead;
    	this.fullPathJsonTail = fullPathJsonTail;
    	this.primary = primary;
    	this.primaryPath = primaryPath;
    	this.parentFK = parentFK;
    }

    /*
     * Minimal constructor. Contains required fields.
     * Full constructor. Contains required and optional fields.
     *  The Full Constructor is the Minimal Constructor
     */
    public DerivedPartOf(Long oid,
    		String speciesFK, 
    		String nodeStartFK, 
    		String nodeStopFK,
    		String pathStartFK, 
    		String pathStopFK,
    		String nodeFK, 
    		String sequence, 
    		String depth, 
    		String fullPath, 
    		String fullPathOids, 
    		String fullPathEmapas, 
    		String fullPathJsonHead, 
    		String fullPathJsonTail, 
    		String primary, 
    		String primaryPath, 
    		String parentFK) {
    	
    	this.oid = oid;
    	this.speciesFK = speciesFK;
    	this.nodeStartFK = ObjectConverter.convert(nodeStartFK, Long.class);
    	this.nodeStopFK = ObjectConverter.convert(nodeStopFK, Long.class);
    	this.pathStartFK = ObjectConverter.convert(pathStartFK, Long.class);
    	this.pathStopFK = ObjectConverter.convert(pathStopFK, Long.class);
    	this.nodeFK = ObjectConverter.convert(nodeFK, Long.class);
    	this.sequence = ObjectConverter.convert(sequence, Long.class);
    	this.depth = ObjectConverter.convert(depth, Long.class);
    	this.fullPath = fullPath;
    	this.fullPathOids = fullPathOids;
    	this.fullPathEmapas = fullPathEmapas;
    	this.fullPathJsonHead = fullPathJsonHead;
    	this.fullPathJsonTail = fullPathJsonTail;
    	this.primary = ObjectConverter.convert(primary, Boolean.class);
    	this.primaryPath = ObjectConverter.convert(primaryPath, Boolean.class);
    	this.parentFK = ObjectConverter.convert(parentFK, Long.class);
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
    public long getNodeStartFK() {
        return nodeStartFK;
    } 
    public String getNodeStartFKAsString() {
        return ObjectConverter.convert(nodeStartFK, String.class);
    } 
    public long getNodeStopFK() {
        return nodeStopFK;
    }
    public String getNodeStopFKAsString() {
        return ObjectConverter.convert(nodeStopFK, String.class);
    }
    public long getPathStartFK() {
        return pathStartFK;
    } 
    public String getPathStartFKAsString() {
        return ObjectConverter.convert(pathStartFK, String.class);
    } 
    public long getPathStopFK() {
        return pathStopFK;
    }
    public String getPathStopFKAsString() {
        return ObjectConverter.convert(pathStopFK, String.class);
    }
    public long getNodeFK() {
        return nodeFK;
    } 
    public String getNodeFKAsString() {
        return ObjectConverter.convert(nodeFK, String.class);
    } 
    public long getSequence() {
        return sequence;
    } 
    public String getSequenceAsString() {
        return ObjectConverter.convert(sequence, String.class);
    } 
    public long getDepth() {
        return depth;
    } 
    public String getDepthAsString() {
        return ObjectConverter.convert(depth, String.class);
    } 
    public String getFullPath() {
        return fullPath;
    } 
    public String getFullPathOids() {
        return fullPathOids;
    } 
    public String getFullPathEmapas() {
        return fullPathEmapas;
    } 
    public String getFullPathJsonHead() {
        return fullPathJsonHead;
    } 
    public String getFullPathJsonTail() {
        return fullPathJsonTail;
    } 
    public boolean isPrimary() {
        return primary;
    } 
    public String getPrimary() {
        return ObjectConverter.convert(primary, String.class);
    } 
    public boolean isPrimaryPath() {
        return primaryPath;
    } 
    public String getPrimaryPath() {
        return ObjectConverter.convert(primaryPath, String.class);
    } 
    public long getParentFK() {
        return parentFK;
    } 
    public String getParentFKAsString() {
        return ObjectConverter.convert(parentFK, String.class);
    } 

    // Setters ------------------------------------------------------------------------------------
    public void setOid(Long oid) {
        this.oid = oid;
    }
    public void setSpeciesFK(String speciesFK) {
        this.speciesFK = speciesFK;
    } 
    public void setNodeStartFK(long nodeStartFK) {
        this.nodeStartFK = nodeStartFK;
    } 
    public void setNodeStartFK(String nodeStartFK) {
        this.nodeStartFK = ObjectConverter.convert(nodeStartFK, Long.class);
    } 
    public void setNodeStopFK(long nodeStopFK) {
        this.nodeStopFK = nodeStopFK;
    }
    public void setNodeStopFK(String nodeStopFK) {
        this.nodeStopFK = ObjectConverter.convert(nodeStopFK, Long.class);
    }
    public void setPathStartFK(long pathStartFK) {
        this.pathStartFK = pathStartFK;
    } 
    public void setPathStartFK(String pathStartFK) {
        this.pathStartFK = ObjectConverter.convert(pathStartFK, Long.class);
    } 
    public void setPathStopFK(long pathStopFK) {
        this.pathStopFK = pathStopFK;
    }
    public void setPathStopFK(String pathStopFK) {
        this.pathStopFK = ObjectConverter.convert(pathStopFK, Long.class);
    }
    public void setNodeFK(long nodeFK) {
        this.nodeFK = nodeFK;
    } 
    public void setNodeFK(String nodeFK) {
        this.nodeFK = ObjectConverter.convert(nodeFK, Long.class);
    } 
    public void setSequence(long sequence) {
        this.sequence = sequence;
    } 
    public void setSequence(String sequence) {
        this.sequence = ObjectConverter.convert(sequence, Long.class);
    } 
    public void setDepth(long depth) {
        this.depth = depth;
    } 
    public void setDepth(String depth) {
        this.depth = ObjectConverter.convert(depth, Long.class);
    } 
    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    } 
    public void setFullPathOids(String fullPathOids) {
        this.fullPathOids = fullPathOids;
    } 
    public void setFullPathEmapas(String fullPathEmapas) {
        this.fullPathEmapas = fullPathEmapas;
    } 
    public void setFullPathJsonHead(String fullPathJsonHead) {
        this.fullPathJsonHead = fullPathJsonHead;
    } 
    public void setFullPathJsonTail(String fullPathJsonTail) {
        this.fullPathJsonTail = fullPathJsonTail;
    } 
    public void setPrimary(boolean primary) {
        this.primary = primary;
    } 
    public void setPrimary(String primary) {
        this.primary = ObjectConverter.convert(primary, Boolean.class);
    } 
    public void setPrimaryPath(boolean primaryPath) {
        this.primaryPath = primaryPath;
    } 
    public void setPrimaryPath(String primaryPath) {
        this.primaryPath = ObjectConverter.convert(primaryPath, Boolean.class);
    } 
    public void setParentFK(long parentFK) {
        this.parentFK = parentFK;
    } 
    public void setParentFK(String parentFK) {
        this.parentFK = ObjectConverter.convert(parentFK, Long.class);
    } 

    // Helper -------------------------------------------------------------------------------------
    /*
     * Is this DerivedPartOf the same as the Supplied DerivedPartOf?
     */
    public boolean isSameAs(DerivedPartOf daoderivedpartof){

    	if ( this.getSpeciesFK().equals(daoderivedpartof.getSpeciesFK()) && 
    		this.getNodeStartFK() == daoderivedpartof.getNodeStartFK() && 
    		this.getNodeStopFK() == daoderivedpartof.getNodeStopFK() && 
    		this.getPathStartFK() == daoderivedpartof.getPathStartFK() && 
    		this.getPathStopFK() == daoderivedpartof.getPathStopFK() && 
    		this.getNodeFK() == daoderivedpartof.getNodeFK() && 
    		this.getSequence() == daoderivedpartof.getSequence() && 
    		this.getDepth() == daoderivedpartof.getDepth() && 
    		this.getFullPath().equals(daoderivedpartof.getFullPath()) && 
    		this.getFullPathOids().equals(daoderivedpartof.getFullPathOids()) && 
    		this.getFullPathJsonHead().equals(daoderivedpartof.getFullPathJsonHead()) && 
    		this.getFullPathJsonTail().equals(daoderivedpartof.getFullPathJsonTail()) && 
    		this.isPrimary() == daoderivedpartof.isPrimary() && 
    		this.isPrimaryPath() == daoderivedpartof.isPrimaryPath() && 
    		this.getParentFK() == daoderivedpartof.getParentFK() ) {

        	return true;
        }
        else {

        	return false;
        }
    }

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
    	
        return String.format("DerivedPartOf [ oid=%d, speciesFK=%s, nodeStartFK=%d, nodeStopFK=%d, pathStartFK=%d, pathStopFK=%d, nodeFK=%d, sequence=%d, depth=%d, fullPath=%s, fullPathOids=%s, fullPathEmapas=%s, fullPathJsonHead=%s, fullPathJsonTail=%s, primary=%b, primaryPath=%b, parentFK=%d ]", 
        		oid, speciesFK, nodeStartFK, nodeStopFK, pathStartFK, pathStopFK, nodeFK, sequence, depth, fullPath, fullPathOids, fullPathEmapas, fullPathJsonHead, fullPathJsonTail, primary, primaryPath, parentFK);
    }
}
