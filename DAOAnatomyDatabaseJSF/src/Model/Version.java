package Model;

import java.io.Serializable;

/**
 * This class represents a Data Transfer Object for the Node (Abstract - EMAPA)
 */
public class Version implements Serializable {

    // Properties ---------------------------------------------------------------------------------
	/*
	 *  ANA_VERSION - A record of all the versions of the Anatomy Database
	 *              - EMAPA:.... 
     *  
     *  Columns:
     *   1. VER_OID      - int(10) unsigned
     *   2. VER_NUMBER   - int(10) unsigned
     *   3. VER_DATE     - datetime
     *   4. VER_COMMENTS - varchar(2000)
     *   
	 */
    private Long oid; 
    private Long number; 
    private String date; 
    private String comments; 

    // Constructors -------------------------------------------------------------------------------
    /**
     * Default constructor.
     */
    public Version() {
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
    public Version(Long oid, 
    		Long number,
    		String date, 
    		String comments) {

    	this.oid = oid;
	    this.number = number;
	    this.date = date;
	    this.comments = comments;
    }

    // Getters ------------------------------------------------------------------------------------
    public Long getOid() {
        return oid;
    }
    public Long getNumber() {
        return number;
    }
    public String getDate() {
        return date;
    }
    public String getComments() {
        return comments;
    }

    // Setters ------------------------------------------------------------------------------------
    public void setOid(Long oid) {
        this.oid = oid;
    }
    public void setNumber(Long number) {
        this.number = number;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }

    // Override -----------------------------------------------------------------------------------
    /**
     * The relation ID is unique for each Node. So this should compare Node by ID only.
     */
    public boolean equals(Object other) {
        return (other instanceof Version) && (oid != null) 
        		? oid.equals(((Version) other).oid) 
        		: (other == this);
    }

    /**
     * Returns the String representation of this Node. Not required, it just pleases reading logs.
     */
    public String toString() {
        return String.format("Version [ oid=%d, number=%s, date=%s, comments=%s ]", 
            oid, number, date, comments); 

    }

}
