/*
-----------------------------------------------------------------------------------------------
# Project:      DAOAnatomyRebuild
#
# Title:        ComponentComment.java
#
# Date:         2012
#
# Author:       Mike Wicks
#
# Copyright:    2012
#               Medical Research Council, UK.
#               All rights reserved.
#
# Address:      MRC Human Genetics Unit,
#               Western General Hospital,
#               Edinburgh, EH4 2XU, UK.
#
# Version: 1
#
# Description:  A Wrapper Object for an OBO ComponentComment inserted into the Anatomy Database
#
# Maintenance:  Log changes below, with most recent at top of list.
#
# Who; When; What;
#
# Mike Wicks; February 2012; Create Class
#
-----------------------------------------------------------------------------------------------
*/
package DAOModel;

import java.io.Serializable;

public class ComponentComment implements Serializable{

    // daocomponentcomment fields
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
        // Always keep the default constructor alive in a Javabean class.
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
    public boolean isComponentCommentSameAs(ComponentComment daocomponentcomment){
    	
        //EMAP id, description, start stage, end stage, parents, synonyms

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
     * The relation ID is unique for each Thing. So this should compare Thing by ID only.
     */
    public boolean equals(Object other) {
        return (other instanceof ComponentComment) && (oid != null) 
        		? oid.equals(((ComponentComment) other).oid) 
        		: (other == this);
    }

    /*
     * Returns the String representation of this User. Not required, it just pleases reading logs.
     */
    public String toString() {
        return String.format("ComponentComment [ oid=%d, id=%s, general=%s, user=%s, order=%s ]", 
        		oid, id, general, user, order);
    }

}
	