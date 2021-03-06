/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayer
*
* Title:        ComponentComment.java
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
* Description:  This class represents a SQL Database Transfer Object for the 
*                ComponentComment Table - ANA_OBO_COMPONENT_COMMENT
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

public class ComponentComment {
    // Properties ---------------------------------------------------------------------------------
	/*
     *   ACC_OID                 - bigint(20) unsigned NOT NULL AUTO_INCREMENT,
     *   ACC_OBO_ID              - varchar(25) NOT NULL,
     *   ACC_OBO_GENERAL_COMMENT - varchar(1000) NOT NULL,
     *   ACC_OBO_USER_COMMENT    - varchar(1000) NOT NULL,
     *   ACC_OBO_ORDER_COMMENT   - varchar(1000) NOT NULL,
	 */
    private Long oid;
    private String id;
    private String general;
    private String user;
    private String order;

    // Constructors -------------------------------------------------------------------------------
    /*
     * Default constructor.
     */
    public ComponentComment() {
        
    }

    /*
     * Minimal constructor. Contains required fields.
     */
    public ComponentComment(Long oid,
    		String id, 
    		String general,
    		String user,
    		String order) {
    	
    	this.oid = oid;
    	this.id = id;
    	this.general = general;
        this.user = user;
        this.order = order;
    }
    
    // Getters ------------------------------------------------------------------------------------
    public Long getOid() {
        return this.oid;
    }
    public String getId() {
        return this.id;
    }
    public String getGeneral() {
        return this.general;
    }
    public String getUser() {
        return this.user;
    }
    public String getOrder() {
        return this.order;
    }

    // Setters ------------------------------------------------------------------------------------
    public void setOid( Long oid ) {
        this.oid = oid;
    }
    public void setId( String id ) {
        this.id = id;
    }
    public void setGeneral( String general ) {
        this.general = general;
    }
    public void setUser( String user ) {
        this.user = user;
    }
    public void setOrder( String order ) {
        this.order = order;
    }
    
    // Helpers ------------------------------------------------------------------------------------
    /*
     * Is this ComponentComment the same as the Supplied ComponentComment?
     */
    public boolean isSameAs(ComponentComment daocomponentcomment){

    	if ( this.getId().equals(daocomponentcomment.getId()) && 
             this.getGeneral() == daocomponentcomment.getGeneral() &&
             this.getUser() == daocomponentcomment.getUser() &&
             this.getOrder() == daocomponentcomment.getOrder() ){

        	return true;
        }
        else {
        	
            return false;
        }
    }

    /*
     * The OID is unique for each ComponentComment.
     *  So this should compare ComponentComment by OID only.
     */
    public boolean equals(Object other) {
    	
        return (other instanceof ComponentComment) && (oid != null) 
        		? oid.equals(((ComponentComment) other).oid) 
        		: (other == this);
    }

    /*
     * Returns the String representation of this ComponentComment.
     *  Not required, it just makes reading logs easier.
     */
    public String toString() {
    	
        return String.format("ComponentComment [ oid=%d, id=%s, general=%s, user=%s, order=%s ]", 
        		oid, id, general, user, order);
    }
}
	