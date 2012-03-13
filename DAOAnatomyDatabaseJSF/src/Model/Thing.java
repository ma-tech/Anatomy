package Model;

import java.io.Serializable;

/**
 * This class represents a Data Transfer Object for the Object. 
 */
public class Thing implements Serializable {

    // Properties ---------------------------------------------------------------------------------
	/*
	 *  ANA_OBJECT - ALL Objects in the Anatomy DAG
	 *                - Abstract Nodes - ANA_NODE 
	 *                - Timed Nodes    - ANA_TIMED_NODE
	 *                - Relationships  - ANA_RELATIONSHIP
	 *                - Stages         - ANA_STAGE
     *  
     *  Columns:
     *   1. OBJ_OID               - int(10) unsigned 
     *   2. OBJ_CREATION_DATETIME - datetime         
     *   3. OBJ_CREATOR_FK        - int(10) unsigned 
     *   4. OBJ_TABLE             - varchar(255)
     *   5. OBJ_DESCRIPTION       - varchar(255)
	 */
    private Long oid; 
    private String creationDateTime; 
    private Long creatorFK; 
    private String table; 
    private String description; 

    // Constructors -------------------------------------------------------------------------------
    /**
     * Default constructor.
     */
    public Thing() {
        // Always keep the default constructor alive in a Javabean class.
    }

    /**
     * Minimal constructor. Contains required fields.
     */

    /**
     * Full constructor. Contains required and optional fields.
     * 
     * The Full Constructor is the Minimal Constructor
     * 
     */
    public Thing(Long oid, 
    		String creationDateTime, 
    		Long creatorFK,
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
    public Long getCreatorFK() {
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
    public void setCreatorFK(Long creatorFK) {
        this.creatorFK = creatorFK;
    }
    public void setTable(String table) {
        this.table = table;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    // Override -----------------------------------------------------------------------------------
    /**
     * The relation ID is unique for each Thing. So this should compare Thing by ID only.
     */
    public boolean equals(Object other) {
        return (other instanceof Thing) && (oid != null) 
        		? oid.equals(((Thing) other).oid) 
        		: (other == this);
    }

    /**
     * Returns the String representation of this User. Not required, it just pleases reading logs.
     */
    public String toString() {
        return String.format("Object [ oid=%d, creationDateTime=%s, creatorFK=%d, table=%s, description=%s ]", 
            oid, creationDateTime, creatorFK, table, description);
    }

}
