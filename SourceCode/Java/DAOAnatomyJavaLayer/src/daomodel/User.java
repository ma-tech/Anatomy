/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayer
*
* Title:        User.java
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
* Description:  This class represents a SQL Database Transfer Object for the OBO User Table.
*                ANA_OBO_USER
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

public class User {
    // Properties ---------------------------------------------------------------------------------
	/*
	 * 1. AOU_OID          - bigint(20) unsigned NOT NULL AUTO_INCREMENT
	 * 2. AOU_NAME         - varchar(255) NOT NULL
	 * 3. AOU_PASSWORD     - varchar(255) NOT NULL
	 * 4. AOU_EMAIL        - varchar(255) NOT NULL
	 * 5. AOU_ORGANISATION - varchar(255) DEFAULT NULL
	 */
    private Long oid;
    private String username;
    private String password;
    private String email;
    private String organisation;

    // Constructors -------------------------------------------------------------------------------
    /*
     * Default constructor.
     */
    public User() {
        
    }

    /*
     * Minimal constructor. Contains required fields.
     */
    public User(Long oid, 
    		String username, 
    		String password) {
    	
        this.oid = oid;
        this.username = username;
        this.password = password;
    }

    /*
     * Full constructor. Contains required and optional fields.
     */
    public User(Long oid, 
    		String username, 
    		String password, 
    		String email, 
    		String organisation) {
    	
        this(oid, username, password);
        this.email = email;
        this.organisation = organisation;
    }

    // Getters ------------------------------------------------------------------------------------
    public Long getOid() {
        return oid;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public String getEmail() {
        return email;
    }
    public String getOrganisation() {
        return organisation;
    }

    // Setters ------------------------------------------------------------------------------------
    public void setOid(Long oid) {
        this.oid = oid;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setOrganisation(String organisation) {
        this.organisation = organisation;
    }

    // Override -----------------------------------------------------------------------------------
    /*
     * Is this User the same as the Supplied User?
     */
    public boolean isSameAs(User daouser){

    	if (this.getUsername().equals(daouser.getUsername()) &&
    		this.getPassword().equals(daouser.getPassword()) &&
    		this.getEmail().equals(daouser.getEmail()) &&
    		this.getOrganisation().equals(daouser.getOrganisation()) ) {

        	return true;
        }
        else {

        	return false;
        }
    }

    /*
     * The user OID is unique for each User. 
     *  So this should compare User by OID only.
     */
    public boolean equals(Object other) {
    	
        return (other instanceof User) && (oid != null) 
        		? oid.equals(((User) other).oid) 
        				: (other == this);
    }

    /*
     * The user OID is unique for each User. 
     *  So User with same OID should return same hashcode.
     */
    public int hashCode() {
    	
        return (oid != null) ? (this.getClass().hashCode() + oid.hashCode()) : super.hashCode();
    }

    /*
     * Returns the String representation of this User. Not required, it just makes reading logs easier.
     */
    public String toString() {
    	
        return String.format("User [ oid=%d, username=%s, password=%s, email=%s, organisation=%s]", 
            oid, username, password, email, organisation);
    }
}
