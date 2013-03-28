/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomy
*
* Title:        Thing.java
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
* Description:  This class represents a SQL Database Transfer Object for the Object Table.
*                ANA_OBJECT - ALL Objects in the Anatomy DAG
*                           - Abstract Nodes - ANA_NODE 
*                           - Timed Nodes    - ANA_TIMED_NODE
*                           - Relationships  - ANA_RELATIONSHIP
*                           - Stages         - ANA_STAGE
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

public class Thing {
    // Properties ---------------------------------------------------------------------------------
	/*
     *   1. OBJ_OID               - int(10) unsigned 
     *   2. OBJ_CREATION_DATETIME - datetime         
     *   3. OBJ_CREATOR_FK        - int(10) unsigned 
     *   4. OBJ_TABLE             - varchar(255)
     *   5. OBJ_DESCRIPTION       - varchar(255)
	 */
    private Long oid; 
    private String creationDateTime; 
    private long creatorFK; 
    private String table; 
    private String description; 

    // Constructors -------------------------------------------------------------------------------
    /*
     * Default constructor.
     */
    public Thing() {
        // Always keep the default constructor alive in a Javabean class.
    }

    /*
     * Minimal constructor. Contains required fields.
     */
    public Thing(Long oid, 
    		String creationDateTime, 
    		long creatorFK,
    		String table,
		    String description) {
    	
        this.oid = oid;
        this.creationDateTime = creationDateTime; 
        this.creatorFK = creatorFK;
        this.table = table; 
        this.description = description; 
    }

    // Getters ------------------------------------------------------------------------------------
    public Long getOid() {
        return oid;
    }
    public String getCreationDateTime() {
        return creationDateTime;
    }
    public long getCreatorFK() {
        return creatorFK;
    }
    public String getTable() {
        return table;
    }
    public String getDescription() {
        return description;
    }

    // Setters ------------------------------------------------------------------------------------
    public void setOid(Long oid) {
        this.oid = oid;
    }
    public void setCreationDateTime(String creationDateTime) {
        this.creationDateTime = creationDateTime;
    }
    public void setCreatorFK(long creatorFK) {
        this.creatorFK = creatorFK;
    }
    public void setTable(String table) {
        this.table = table;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    // Override -----------------------------------------------------------------------------------
    /*
     * Is this Thing the same as the Supplied Thing?
     */
    public boolean isSameAs(Thing daothing){

    	if (this.getCreationDateTime().equals(daothing.getCreationDateTime()) &&
    		this.getCreatorFK() == daothing.getCreatorFK() &&
    		this.getTable().equals(daothing.getTable()) &&
    		this.getDescription().equals(daothing.getDescription()) ) {

        	return true;
        }
        else {

        	return false;
        }
    }

    /*
     * The relation OID is unique for each Thing. 
     *  So this should compare Thing by OID only.
     */
    public boolean equals(Object other) {
    	
        return (other instanceof Thing) && (oid != null) 
        		? oid.equals(((Thing) other).oid) 
        		: (other == this);
    }

    /*
     * Returns the String representation of this Thing.
     *  Not required, it just makes reading logs easier.
     */
    public String toString() {
    	
        return String.format("Object [ oid=%d, creationDateTime=%s, creatorFK=%d, table=%s, description=%s ]", 
            oid, creationDateTime, creatorFK, table, description);
    }
}
