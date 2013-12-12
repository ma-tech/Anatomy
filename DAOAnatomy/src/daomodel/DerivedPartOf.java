/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomy
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
    public long getNodeStartFK() {
        return nodeStartFK;
    } 
    public long getNodeStopFK() {
        return nodeStopFK;
    }
    public long getPathStartFK() {
        return pathStartFK;
    } 
    public long getPathStopFK() {
        return pathStopFK;
    }
    public long getNodeFK() {
        return nodeFK;
    } 
    public long getSequence() {
        return sequence;
    } 
    public long getDepth() {
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
    public boolean isPrimary() {
        return primary;
    } 
    public boolean isPrimaryPath() {
        return primaryPath;
    } 
    public long getParentFK() {
        return parentFK;
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
    public void setNodeStopFK(long nodeStopFK) {
        this.nodeStopFK = nodeStopFK;
    }
    public void setPathStartFK(long pathStartFK) {
        this.pathStartFK = pathStartFK;
    } 
    public void setPathStopFK(long pathStopFK) {
        this.pathStopFK = pathStopFK;
    }
    public void setNodeFK(long nodeFK) {
        this.nodeFK = nodeFK;
    } 
    public void setSequence(long sequence) {
        this.sequence = sequence;
    } 
    public void setDepth(long depth) {
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
    public void setPrimary(boolean primary) {
        this.primary = primary;
    } 
    public void setPrimaryPath(boolean primaryPath) {
        this.primaryPath = primaryPath;
    } 
    public void setParentFK(long parentFK) {
        this.parentFK = parentFK;
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
    	
        return String.format("DerivedPartOf [ oid=%d, speciesFK=%s, nodeStartFK=%d, nodeStopFK=%d, pathStartFK=%d, pathStopFK=%d, nodeFK=%d, sequence=%d, depth=%d, fullPath=%s, fullPathOids=%s, fullPathJsonHead=%s, fullPathJsonTail=%s, primary=%b, primaryPath=%b, parentFK=%d ]", 
        		oid, speciesFK, nodeStartFK, nodeStopFK, pathStartFK, pathStopFK, nodeFK, sequence, depth, fullPath, fullPathOids, fullPathJsonHead, fullPathJsonTail, primary, primaryPath, parentFK);
    }
}
