/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayer
*
* Title:        RelationshipFK.java
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
* Description:  This class represents a SQL Database Transfer Object for the RelationshipFK Table.
*                ANA_RELATIONSHIP
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

public class RelationshipFK {
    // Properties ---------------------------------------------------------------------------------
	/*
     *   1. REL_OID                  - int(10) unsigned 
     *   2. REL_RELATIONSHIP_TYPE_FK - varchar(20)      
     *   3. a.ANO_PUBLIC_ID = REL_CHILD_FK - varchar(20)      
     *   4. b.ANO_PUBLIC_ID = REL_PARENT_FK - varchar(20)      
	 */
    private Long oid; 
    private String typeFK; 
    private String childNameFK; 
    private String parentNameFK;

    // Constructors -------------------------------------------------------------------------------
    /*
     * Default constructor.
     */
    public RelationshipFK() {
        
    }

    /*
     * Minimal constructor. Contains required fields.
     * Full constructor. Contains required and optional fields.
     *  The Full Constructor is the Minimal Constructor
     */
    public RelationshipFK(Long oid, 
    		String typeFK, 
    		String childNameFK, 
    		String parentNameFK) {
    	
        this.oid = oid;
        this.typeFK = typeFK; 
        this.childNameFK = childNameFK; 
        this.parentNameFK = parentNameFK;
    }

    // Getters ------------------------------------------------------------------------------------
    public Long getOid() {
        return oid;
    }
    public String getTypeFK() {
        return typeFK;
    }
    public String getChildNameFK() {
        return childNameFK;
    }
    public String getParentNameFK() {
        return parentNameFK;
    }

    // Setters ------------------------------------------------------------------------------------
    public void setOid(Long oid) {
        this.oid = oid;
    }
    public void setTypeFK(String typeFK) {
        this.typeFK = typeFK;
    }
    public void setChildNameFK(String childNameFK) {
        this.childNameFK = childNameFK;
    }
    public void setParentNameFK(String parentNameFK) {
        this.parentNameFK = parentNameFK;
    }

    // Override -----------------------------------------------------------------------------------
    /*
     * Is this RelationshipFK the same as the Supplied RelationshipFK?
     */
    public boolean isSameAs(RelationshipFK daorelationshipfk){

    	if (this.getTypeFK().equals(daorelationshipfk.getTypeFK()) &&
    		this.getChildNameFK().equals(daorelationshipfk.getChildNameFK()) &&
    		this.getParentNameFK().equals(daorelationshipfk.getParentNameFK()) ) {

        	return true;
        }
        else {

        	return false;
        }
    }

    /*
     * The relation OID is unique for each RelationshipFK. 
     *  So this should compare RelationshipFK by OID only.
     */
    public boolean equals(Object other) {
    	
        return (other instanceof RelationshipFK) && (oid != null) 
        		? oid.equals(((RelationshipFK) other).oid) 
        		: (other == this);
    }

    /*
     * Returns the String representation of this RelationshipFK.
     *  Not required, it just aids log reading.
     */
    public String toString() {
    	
        return String.format("RelationshipFK [ oid=%d, typeFK=%s, childNameFK=%s, parentNameFK=%s ]", 
            oid, typeFK, childNameFK, parentNameFK);
    }

    /*
     * Returns the String representation of this RelationshipFK.
     *  Not required, it just aids log reading.
     */
    public String toStringThing() {
    	
        return String.format("oid=%d, typeFK=%s, childNameFK=%s, parentNameFK=%s", 
            oid, typeFK, childNameFK, parentNameFK);
    }
}
