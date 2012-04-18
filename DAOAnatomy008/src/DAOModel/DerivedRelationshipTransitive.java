package DAOModel;

import java.io.Serializable;

/**
 * This class represents a Data Transfer Object for the DerivedPartOf. 
 */
public class DerivedRelationshipTransitive implements Serializable {

    // Properties ---------------------------------------------------------------------------------
	/*
	 *  ANAD_RELATIONSHIP_TRANSITIVE - All DerivedRelationshipTransitives between ABSTRACT Nodes 
	 *   in the Anatomy DAG
     *  
     *  Columns:
     *   1. RTR_OID                   => int(10)
     *   2. RTR_RELATIONSHIP_TYPE_FK  => varchar(20)
     *   3. RTR_DESCENDENT_FK         => int(10)
     *   4. RTR_ANCESTOR_FK           => int(10)
	 */
    private Long oid; 
    private String relTypeFK; 
    private Long descendantFK; 
    private Long ancestorFK; 

    // Constructors -------------------------------------------------------------------------------
    /**
     * Default constructor.
     */
    public DerivedRelationshipTransitive() {
        // Always keep the default constructor alive in a Javabean class.
    }

    /**
     * Minimal constructor. Contains required fields.
     * Full constructor. Contains required and optional fields.
     *  The Full Constructor is the Minimal Constructor
     * 
     */
    public DerivedRelationshipTransitive(Long oid,
    	    String relTypeFK, 
    	    Long descendantFK, 
    	    Long ancestorFK) {
    	
    	this.oid = oid;
    	this.relTypeFK = relTypeFK;
    	this.descendantFK = descendantFK;
    	this.ancestorFK = ancestorFK;
    }

    // Getters ------------------------------------------------------------------------------------
    public Long getOid() {
        return oid;
    }
    public String getRelTypeFK() {
        return relTypeFK;
    } 
    public Long getDescendantFK() {
        return descendantFK;
    } 
    public Long getAncestorFK() {
        return ancestorFK;
    }

    // Setters ------------------------------------------------------------------------------------
    public void setOid(Long oid) {
        this.oid = oid;
    }
    public void setRelTypeFK(String relTypeFK) {
        this.relTypeFK = relTypeFK;
    } 
    public void setDescendantFK(Long descendantFK) {
        this.descendantFK = descendantFK;
    } 
    public void setAncestorFK(Long ancestorFK) {
        this.ancestorFK = ancestorFK;
    }

    // Override -----------------------------------------------------------------------------------
    /**
     * The relation ID is unique for each DerivedPartOf. 
     *  So this should compare DerivedPartOf by ID only.
     */
    public boolean equals(Object other) {
        return (other instanceof DerivedRelationshipTransitive) && (oid != null) 
        		? oid.equals(((DerivedRelationshipTransitive) other).oid) 
        		: (other == this);
    }

    /**
     * Returns the String representation of this DerivedPartOf.
     *  Not required, it just aids log reading.
     */
    public String toString() {
        return String.format("DerivedRelationshipTransitive [ oid=%d, relTypeFK=%s, descendantFK=%d, ancestorFK=%d ]", 
        		oid, relTypeFK, descendantFK, ancestorFK);

    }
}
