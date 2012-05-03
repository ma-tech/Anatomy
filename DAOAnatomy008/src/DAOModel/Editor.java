package daomodel;

import java.io.Serializable;

/**
 * This class represents a Data Transfer Object for the Node (Abstract - EMAPA)
 */
public class Editor implements Serializable {

    // Properties ---------------------------------------------------------------------------------
	/*
	 *  ANA_EDITOR - Editors of the Anatomy Database
	 *  
     *  Columns:
     *   1. EDI_OID  - int(10) unsigned
     *   2. EDI_NAME - varchar(50)
     *   
	 */
    private Long oid; 
    private String name; 

    // Constructors -------------------------------------------------------------------------------
    /**
     * Default constructor.
     */
    public Editor() {
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
    public Editor(Long oid, 
    	    String name) {
    	
        this.oid = oid;
	    this.name = name;
    }

    // Getters ------------------------------------------------------------------------------------
    public Long getOid() {
        return oid;
    }
    public String getName() {
        return name;
    }

    // Setters ------------------------------------------------------------------------------------
    public void setOid(Long oid) {
        this.oid = oid;
    }
    public void setName(String name) {
        this.name = name;
    }

    // Override -----------------------------------------------------------------------------------
    /**
     * The relation ID is unique for each Node. So this should compare Node by ID only.
     */
    public boolean equals(Object other) {
        return (other instanceof Editor) && (oid != null) 
        		? oid.equals(((Editor) other).oid) 
        		: (other == this);
    }

    /**
     * Returns the String representation of this Node. Not required, it just pleases reading logs.
     */
    public String toString() {
        return String.format("Editor [ oid=%d, name=%s ]", 
            oid, name); 

    }

}
