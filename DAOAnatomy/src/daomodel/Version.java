/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomy
*
* Title:        Version.java
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
* Description:  This class represents a SQL Database Transfer Object for the Version Table.
*                ANA_VERSION
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

public class Version {
    // Properties ---------------------------------------------------------------------------------
	/*
     *   1. VER_OID      - int(10) unsigned
     *   2. VER_NUMBER   - int(10) unsigned
     *   3. VER_DATE     - datetime
     *   4. VER_COMMENTS - varchar(2000)
	 */
    private Long oid; 
    private Long number; 
    private String date; 
    private String comments; 

    // Constructors -------------------------------------------------------------------------------
    /*
     * Default constructor.
     */
    public Version() {
        // Always keep the default constructor alive in a Javabean class.
    }

    /*
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

    // Helper -------------------------------------------------------------------------------------
    /*
     * Is this Version the same as the Supplied Version?
     */
    public boolean isSameAs(Version daoversion){


    	if ( this.getNumber() == daoversion.getNumber() &&
    		this.getDate().equals(daoversion.getDate())  &&
    		this.getComments().equals(daoversion.getComments()) ) {

        	return true;
        }
        else {

        	return false;
        }
    }

    /*
     * The relation ID is unique for each Version. 
     *  So this should compare Version by ID only.
     */
    public boolean equals(Object other) {
    	
        return (other instanceof Version) && (oid != null) 
        		? oid.equals(((Version) other).oid) 
        		: (other == this);
    }

    /*
     * Returns the String representation of this Version. 
     *  Not required, it just make reading logs easier.
     */
    public String toString() {
    	
        return String.format("Version [ oid=%d, number=%s, date=%s, comments=%s ]", 
            oid, number, date, comments); 
    }
}
