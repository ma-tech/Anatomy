/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayer
*
* Title:        DerivedPartOfFK.java
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
* Description:  This class represents a Data Transfer Object for the 
*                DerivedPartOf Table - ANAD_PART_OF with Foreign Keys materialised
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

public class DerivedPartOfFK {
    // Properties ---------------------------------------------------------------------------------
	/*
     *   1.  APO_OID                 => int(10)
     *   2.  APO_SPECIES_FK          => varchar(20)
     *   3.  NODE_START_STAGE        => varchar(255)
     *   4.  NODE_END_STAGE          => varchar(255)
     *   5.  PATH_START_STAGE        => varchar(255)
     *   6.  PATH_END_STAGE          => varchar(255)
     *   7.  NODE_ID                 => int(10)
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
    private String nodeStart; 
    private String nodeStop;
    private String pathStart; 
    private String pathStop;
    private String node; 
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
    public DerivedPartOfFK() {
        
    }

    /*
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

    /*
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
    		String sequence, 
    		String depth, 
    		String fullPath, 
    		String fullPathOids, 
    		String fullPathJsonHead, 
    		String fullPathJsonTail, 
    		String primary, 
    		String primaryPath, 
    		String parentFK) {
    	
    	this.oid = oid;
    	this.speciesFK   = speciesFK;
    	this.nodeStart   = nodeStart;
    	this.nodeStop    = nodeStop;
    	this.pathStart   = pathStart;
    	this.pathStop    = pathStop;
    	this.node        = node;
    	this.sequence    = ObjectConverter.convert(sequence, Long.class);
    	this.depth       = ObjectConverter.convert(depth, Long.class);
    	this.fullPath = fullPath;
    	this.fullPathOids = fullPathOids;
    	this.fullPathJsonHead = fullPathJsonHead;
    	this.fullPathJsonTail = fullPathJsonTail;
    	this.primary     = ObjectConverter.convert(primary, Boolean.class);
    	this.primaryPath = ObjectConverter.convert(primaryPath, Boolean.class);
    	this.parentFK    = ObjectConverter.convert(parentFK, Long.class);
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

    // Override -----------------------------------------------------------------------------------
    /*
     * Is this DerivedPartOfFK the same as the Supplied DerivedPartOfFK?
     */
    public boolean isSameAs(DerivedPartOfFK daoderivedpartoffk){

    	if ( this.getSpeciesFK().equals(daoderivedpartoffk.getSpeciesFK()) &&  
    		this.getNodeStart().equals(daoderivedpartoffk.getNodeStart()) &&  
    		this.getNodeStop().equals(daoderivedpartoffk.getNodeStop()) &&  
    		this.getPathStart().equals(daoderivedpartoffk.getPathStart()) &&  
    		this.getPathStop().equals(daoderivedpartoffk.getPathStop()) &&  
    		this.getNode().equals(daoderivedpartoffk.getNode()) &&  
    		this.getSequence() == daoderivedpartoffk.getSequence() &&  
    		this.getDepth() == daoderivedpartoffk.getDepth() &&  
    		this.getFullPath().equals(daoderivedpartoffk.getFullPath()) &&  
    		this.getFullPathOids().equals(daoderivedpartoffk.getFullPathOids()) &&  
    		this.getFullPathJsonHead().equals(daoderivedpartoffk.getFullPathJsonHead()) &&  
    		this.getFullPathJsonTail().equals(daoderivedpartoffk.getFullPathJsonTail()) &&  
    		this.isPrimary() == daoderivedpartoffk.isPrimary() &&  
    		this.isPrimaryPath() == daoderivedpartoffk.isPrimaryPath() &&  
    		this.getParentFK() == daoderivedpartoffk.getParentFK() ) {

        	return true;
        }
        else {

        	return false;
        }
    }
    
    /*
     * The relation ID is unique for each DerivedPartOfFK. 
     *  So this should compare DerivedPartOfFK by ID only.
     */
    public boolean equals(Object other) {
      
    	return (other instanceof DerivedPartOfFK) && (oid != null) 
        		? oid.equals(((DerivedPartOfFK) other).oid) 
        		: (other == this);
    }

    /*
     * Returns the String representation of this DerivedPartOfFK.
     *  Not required, it just aids log reading.
     */
    public String toString() {
      
    	return String.format("DerivedPartOfFK [ oid=%d, speciesFK=%s, nodeStart=%s, nodeStop=%s, pathStart=%s, pathStop=%s, node=%s, sequence=%d, depth=%d, fullPath=%s, fullPathOids=%s, fullPathJsonHead=%s, fullPathJsonTail=%s, primary=%b, primaryPath=%b, parentFK=%d ]", 
        		oid, speciesFK, nodeStart, nodeStop, pathStart, pathStop, node, sequence, depth, fullPath, fullPathOids, fullPathJsonHead, fullPathJsonTail, primary, primaryPath, parentFK);
    }
}
