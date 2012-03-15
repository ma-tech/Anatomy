/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyRebuild
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
* Version: 1
*
* Description:  This class represents a SQL Database Transfer Object for the Relationship Table.
*                ANA_RELATIONSHIP
*
* Link:         http://balusc.blogspot.com/2008/07/dao-tutorial-data-layer.html
* 
* Maintenance:  Log changes below, with most recent at top of list.
*
* Who; When; What;
*
* Mike Wicks; February 2012; Create Class
*
*----------------------------------------------------------------------------------------------
*/

package DAOModel;

public class Relationship {
    // Properties ---------------------------------------------------------------------------------
	/*
	 *  ANA_RELATIONSHIP - All Relationships between ABSTRACT Nodes in the Anatomy DAG
     *  
     *  Columns:
     *   1. REL_OID                  - int(10) unsigned 
     *   2. REL_RELATIONSHIP_TYPE_FK - varchar(20)      
     *   3. REL_CHILD_FK             - int(10) unsigned 
     *   4. REL_PARENT_FK            - int(10) unsigned 
	 */
    private Long oid; 
    private String typeFK; 
    private Long childFK; 
    private Long parentFK;

    // Constructors -------------------------------------------------------------------------------
    /*
     * Default constructor.
     */
    public Relationship() {
        // Always keep the default constructor alive in a Javabean class.
    }

    /*
     * Minimal constructor. Contains required fields.
     * Full constructor. Contains required and optional fields.
     *  The Full Constructor is the Minimal Constructor
     */
    public Relationship(Long oid, 
    		String typeFK, 
    		Long childFK, 
    		Long parentFK) {
    	
        this.oid = oid;
        this.typeFK = typeFK; 
        this.childFK = childFK; 
        this.parentFK = parentFK;
    }

    // Getters ------------------------------------------------------------------------------------
    public Long getOid() {
        return oid;
    }
    public String getTypeFK() {
        return typeFK;
    }
    public Long getChildFK() {
        return childFK;
    }
    public Long getParentFK() {
        return parentFK;
    }

    // Setters ------------------------------------------------------------------------------------
    public void setOid(Long oid) {
        this.oid = oid;
    }
    public void SetTypeFK(String typeFK) {
        this.typeFK = typeFK;
    }
    public void setChildFK(Long childFK) {
        this.childFK = childFK;
    }
    public void getParentFK(Long parentFK) {
        this.parentFK = parentFK;
    }

    // Override -----------------------------------------------------------------------------------
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
}
