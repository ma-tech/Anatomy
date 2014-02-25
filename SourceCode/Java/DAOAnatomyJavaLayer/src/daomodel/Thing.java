/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayer
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
* Version:      1
*
* Description:  This class represents a SQL Database Transfer Object for the Object Table.
*                ANA_OBJECT - ALL Objects in the Anatomy DAG
*                           - Abstract Nodes - ANA_NODE 
*                           - Timed Nodes    - ANA_TIMED_NODE
*                           - Relationships  - ANA_RELATIONSHIP
*                           - Stages         - ANA_STAGE
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

public class Thing {
    // Properties ---------------------------------------------------------------------------------
	/*
     *   1. OBJ_OID               - int(10) unsigned 
     *   2. OBJ_CREATION_DATETIME - datetime         
     *   3. OBJ_CREATOR_FK        - int(10) unsigned 
     *   4. OBJ_DESCRIPTION       - varchar(255)
     *   5. OBJ_TABLE             - varchar(255)
	 */
    private Long oid; 
    private String creationDateTime; 
    private long creatorFK; 
    private String description; 
    private String table; 

    // Constructors -------------------------------------------------------------------------------
    /*
     * Default constructor.
     */
    public Thing() {
        
    }

    /*
     * Minimal constructor. Contains required fields.
     */
    public Thing(Long oid, 
    		String creationDateTime, 
    		long creatorFK,
		    String description,
    		String table) {
    	
        this.oid = oid;
        this.creationDateTime = creationDateTime; 
        this.creatorFK = creatorFK;
        this.description = description; 
        this.table = table; 
    }

    /*
     * Minimal constructor. Contains required fields.
     */
    public Thing(Long oid, 
    		String creationDateTime, 
    		String creatorFK,
		    String description,
    		String table) {
    	
        this.oid = oid;
        this.creationDateTime = creationDateTime; 
        this.creatorFK = ObjectConverter.convert(creatorFK, Long.class);
        this.description = description; 
        this.table = table; 
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
    public String getCreatorFKAsString() {
        return ObjectConverter.convert(creatorFK, String.class);
    }
    public String getDescription() {
        return description;
    }
    public String getTable() {
        return table;
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
    public void setCreatorFK(String creatorFK) {
        this.creatorFK = ObjectConverter.convert(creatorFK, Long.class);
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setTable(String table) {
        this.table = table;
    }

    // Override -----------------------------------------------------------------------------------
    /*
     * Is this Thing the same as the Supplied Thing?
     */
    public boolean isSameAs(Thing daothing){

    	if (this.getCreationDateTime().equals(daothing.getCreationDateTime()) &&
    		this.getCreatorFK() == daothing.getCreatorFK() &&
    		this.getDescription().equals(daothing.getDescription()) &&
    		this.getTable().equals(daothing.getTable()) ) {

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
    	
        return String.format("Object [ oid=%d, creationDateTime=%s, creatorFK=%d, description=%s, table=%s ]", 
            oid, creationDateTime, creatorFK, description, table);
    }
}
