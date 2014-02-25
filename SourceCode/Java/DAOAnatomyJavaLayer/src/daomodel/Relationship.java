/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayer
*
* Title:        Relationship.java
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
* Description:  This class represents a SQL Database Transfer Object for the Relationship Table.
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

import utility.ObjectConverter;

public class Relationship {
    // Properties ---------------------------------------------------------------------------------
	/*
     *   1. REL_OID                  - int(10) unsigned 
     *   2. REL_RELATIONSHIP_TYPE_FK - varchar(20)      
     *   3. REL_CHILD_FK             - int(10) unsigned 
     *   4. REL_PARENT_FK            - int(10) unsigned 
	 */
    private Long oid; 
    private String typeFK; 
    private long childFK; 
    private long parentFK;

    // Constructors -------------------------------------------------------------------------------
    /*
     * Default constructor.
     */
    public Relationship() {
        
    }

    /*
     * Minimal constructor. Contains required fields.
     * Full constructor. Contains required and optional fields.
     *  The Full Constructor is the Minimal Constructor
     */
    public Relationship(Long oid, 
    		String typeFK, 
    		long childFK, 
    		long parentFK) {
    	
        this.oid = oid;
        this.typeFK = typeFK; 
        this.childFK = childFK; 
        this.parentFK = parentFK;
    }

    /*
     * Minimal constructor. Contains required fields.
     * Full constructor. Contains required and optional fields.
     *  The Full Constructor is the Minimal Constructor
     */
    public Relationship(Long oid, 
    		String typeFK, 
    		String childFK, 
    		String parentFK) {
    	
        this.oid = oid;
        this.typeFK = typeFK; 
        this.childFK = ObjectConverter.convert(childFK, Long.class);
        this.parentFK = ObjectConverter.convert(parentFK, Long.class);
    }

    // Getters ------------------------------------------------------------------------------------
    public Long getOid() {
        return oid;
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

    // Setters ------------------------------------------------------------------------------------
    public void setOid(Long oid) {
        this.oid = oid;
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

    // Override -----------------------------------------------------------------------------------
    /*
     * Is this Relationship the same as the Supplied Relationship?
     */
    public boolean isSameAs(Relationship daorelationship){

    	if (this.getTypeFK().equals(daorelationship.getTypeFK()) &&
    		this.getChildFK() == daorelationship.getChildFK() &&
    		this.getParentFK() == daorelationship.getParentFK() ) {

        	return true;
        }
        else {

        	return false;
        }
    }

    /*
     * The relation OID is unique for each Relationship. 
     *  So this should compare Relationship by OID only.
     */
    public boolean equals(Object other) {
    	
        return (other instanceof Relationship) && (oid != null) 
        		? oid.equals(((Relationship) other).oid) 
        		: (other == this);
    }

    /*
     * Returns the String representation of this Relationship.
     *  Not required, it just aids log reading.
     */
    public String toString() {
    	
        return String.format("Relationship [ oid=%d, typeFK=%s, childFK=%d, parentFK=%d ]", 
            oid, typeFK, childFK, parentFK);
    }

    /*
     * Returns the String representation of this Relationship.
     *  Not required, it just aids log reading.
     */
    public String toStringThing() {
    	
        return String.format("oid=%d, typeFK=%s, childFK=%d, parentFK=%d", 
            oid, typeFK, childFK, parentFK);
    }
}
