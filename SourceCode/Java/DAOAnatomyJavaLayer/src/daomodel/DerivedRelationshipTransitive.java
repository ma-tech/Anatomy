/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayer
*
* Title:        DerivedRelationshipTransitive.java
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
*                DerivedRelationshipTransitive Table - ANAD_RELATIONSHIP_TRANSITIVE
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

public class DerivedRelationshipTransitive {
    // Properties ---------------------------------------------------------------------------------
	/*
     *   1. RTR_OID                   => int(10)
     *   2. RTR_RELATIONSHIP_TYPE_FK  => varchar(20)
     *   3. RTR_DESCENDENT_FK         => int(10)
     *   4. RTR_ANCESTOR_FK           => int(10)
	 */
    private Long oid; 
    private String relTypeFK; 
    private long descendantFK; 
    private long ancestorFK; 

    // Constructors -------------------------------------------------------------------------------
    /*
     * Default constructor.
     */
    public DerivedRelationshipTransitive() {
        
    }

    /*
     * Minimal constructor. Contains required fields.
     * Full constructor. Contains required and optional fields.
     *  The Full Constructor is the Minimal Constructor
     * 
     */
    public DerivedRelationshipTransitive(Long oid,
    	    String relTypeFK, 
    	    long descendantFK, 
    	    long ancestorFK) {
    	
    	this.oid = oid;
    	this.relTypeFK = relTypeFK;
    	this.descendantFK = descendantFK;
    	this.ancestorFK = ancestorFK;
    }

    /*
     * Minimal constructor. Contains required fields.
     * Full constructor. Contains required and optional fields.
     *  The Full Constructor is the Minimal Constructor
     * 
     */
    public DerivedRelationshipTransitive(Long oid,
    	    String relTypeFK, 
    	    String descendantFK, 
    	    String ancestorFK) {
    	
    	this.oid = oid;
    	this.relTypeFK = relTypeFK;
    	this.descendantFK = ObjectConverter.convert(descendantFK, Long.class);
    	this.ancestorFK = ObjectConverter.convert(ancestorFK, Long.class);
    }

    // Getters ------------------------------------------------------------------------------------
    public Long getOid() {
        return oid;
    }
    public String getRelTypeFK() {
        return relTypeFK;
    } 
    public long getDescendantFK() {
        return descendantFK;
    } 
    public String getDescendantFKAsString() {
        return ObjectConverter.convert(descendantFK, String.class);
    } 
    public long getAncestorFK() {
        return ancestorFK;
    }
    public String getAncestorFKAsString() {
        return ObjectConverter.convert(ancestorFK, String.class);
    }

    // Setters ------------------------------------------------------------------------------------
    public void setOid(Long oid) {
        this.oid = oid;
    }
    public void setRelTypeFK(String relTypeFK) {
        this.relTypeFK = relTypeFK;
    } 
    public void setDescendantFK(long descendantFK) {
        this.descendantFK = descendantFK;
    } 
    public void setDescendantFK(String descendantFK) {
        this.descendantFK = ObjectConverter.convert(descendantFK, Long.class);
    } 
    public void setAncestorFK(long ancestorFK) {
        this.ancestorFK = ancestorFK;
    }
    public void setAncestorFK(String ancestorFK) {
        this.ancestorFK = ObjectConverter.convert(ancestorFK, Long.class);
    }

    // Override -----------------------------------------------------------------------------------
    /*
     * Is this DerivedRelationshipTransitive the same as the Supplied DerivedRelationshipTransitive?
     */
    public boolean isSameAs(DerivedRelationshipTransitive daoderivedrelationshiptransitive){

    	if ( this.getRelTypeFK().equals(daoderivedrelationshiptransitive.getRelTypeFK()) &&
    			this.getDescendantFK() == daoderivedrelationshiptransitive.getDescendantFK() && 
    			this.getAncestorFK() == daoderivedrelationshiptransitive.getAncestorFK() ) {

        	return true;
        }
        else {

        	return false;
        }
    }

    /*
     * The relation ID is unique for each DerivedPartOf. 
     *  So this should compare DerivedPartOf by ID only.
     */
    public boolean equals(Object other) {
    	
        return (other instanceof DerivedRelationshipTransitive) && (oid != null) 
        		? oid.equals(((DerivedRelationshipTransitive) other).oid) 
        		: (other == this);
    }

    /*
     * Returns the String representation of this DerivedPartOf.
     *  Not required, it just aids log reading.
     */
    public String toString() {
    	
        return String.format("DerivedRelationshipTransitive [ oid=%d, relTypeFK=%s, descendantFK=%d, ancestorFK=%d ]", 
        		oid, relTypeFK, descendantFK, ancestorFK);
    }
}
