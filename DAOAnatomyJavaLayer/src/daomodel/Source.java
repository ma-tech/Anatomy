/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayer
*
* Title:        Source.java
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
* Description:  This class represents a SQL Database Transfer Object for the Source Table.
*                ANA_SOURCE
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

public class Source {
    // Properties ---------------------------------------------------------------------------------
	/*
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
    private long year;

    // Constructors -------------------------------------------------------------------------------
    /*
     * Default constructor.
     */
    public Source() {
        
    }

    /*
     * Minimal constructor. Contains required fields.
     */
    public Source(Long oid, 
    	    String name,
    	    String authors, 
    	    String formatFK,
    	    long year) {
    	
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
    public long getYear() {
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
    public void setYear(long year) {
        this.year = year;
    }

    // Helper -------------------------------------------------------------------------------------
    /*
     * Is this Source the same as the Supplied Source?
     */
    public boolean isSameAs(Source daosource){

    	if (this.getName().equals(daosource.getName()) && 
    		this.getAuthors().equals(daosource.getAuthors()) &&
    		this.getFormatFK().equals(daosource.getFormatFK()) &&
    		this.getYear() == daosource.getYear() ) {

        	return true;
        }
        else {

        	return false;
        }
    }

    /*
     * The relation ID is unique for each Node. So this should compare Node by ID only.
     */
    public boolean equals(Object other) {
      
    	return (other instanceof Source) && (oid != null) 
        		? oid.equals(((Source) other).oid) 
        		: (other == this);
    }

    /*
     * Returns the String representation of this Node. Not required, it just makes reading logs easier.
     */
    public String toString() {
      
    	return String.format("Source [ oid=%d, name=%s, authors=%s, formatFK=%s, year=%d ]", 
            oid, name, authors, formatFK, year); 
    }
}
