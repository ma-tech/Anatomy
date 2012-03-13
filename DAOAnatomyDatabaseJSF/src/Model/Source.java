package Model;

import java.io.Serializable;

/**
 * This class represents a Data Transfer Object for the Node (Abstract - EMAPA)
 */
public class Source implements Serializable {

    // Properties ---------------------------------------------------------------------------------
	/*
	 *  ANA_Source - Source Materials for the Anatomy Database
     *  
     *  Columns:
     *   1. SRC_OID       - int(10) unsigned
     *   2. SRC_NAME      - varchar(255)
     *   3. SRC_AUTHORS   - varchar(255)
     *   4. SRC_FORMAT_FK - varchar(30)
     *   5. SRC_YEAR      - year(4)
	 */
    private Long oid; 
    private String name; 
    private String authors; 
    private String formatFK;
    private Long year;

    // Constructors -------------------------------------------------------------------------------
    /**
     * Default constructor.
     */
    public Source() {
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
    public Source(Long oid, 
    	    String name,
    	    String authors, 
    	    String formatFK,
    	    Long year) {
    	
        this.oid = oid;
	    this.name = name;
	    this.authors = authors;
	    this.formatFK = formatFK;
	    this.year = year;
    }

    // Getters ------------------------------------------------------------------------------------
    public Long getOid() {
        return oid;
    }
    public String getName() {
        return name;
    }
    public String getAuthors() {
        return authors;
    }
    public String getFormatFK() {
        return formatFK;
    }
    public Long getYear() {
        return year;
    }

    // Setters ------------------------------------------------------------------------------------
    public void setOid(Long oid) {
        this.oid = oid;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setAuthors(String authors) {
        this.authors = authors;
    }
    public void setFormatFK(String formatFK) {
        this.formatFK = formatFK;
    }
    public void setYear(Long year) {
        this.year = year;
    }

    // Override -----------------------------------------------------------------------------------
    /**
     * The relation ID is unique for each Node. So this should compare Node by ID only.
     */
    public boolean equals(Object other) {
        return (other instanceof Source) && (oid != null) 
        		? oid.equals(((Source) other).oid) 
        		: (other == this);
    }

    /**
     * Returns the String representation of this Node. Not required, it just pleases reading logs.
     */
    public String toString() {
        return String.format("Source [ oid=%d, name=%s, authors=%s, formatFK=%d, year=%d ]", 
            oid, name, authors, formatFK, year); 

    }

}
