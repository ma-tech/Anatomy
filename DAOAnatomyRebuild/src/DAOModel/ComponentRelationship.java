/*
-----------------------------------------------------------------------------------------------
# Project:      DAOAnatomyRebuild
#
# Title:        ComponentRelationship.java
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
# Description:  A Wrapper Object for an OBO ComponentRelationship inserted into the Anatomy Database
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

public class ComponentRelationship implements Serializable{

    // daocomponent fields
    private Long oid;
    private String id;
    private String type;
    private String parent;

    // Constructors -------------------------------------------------------------------------------
    /*
     * Default constructor.
     */
    public ComponentRelationship() {
        // Always keep the default constructor alive in a Javabean class.
    }

    /*
     * Minimal constructor. Contains required fields.
     */
    public ComponentRelationship(Long oid,
    		String id, 
    		String type,
    		String parent) {
    	
    	this.oid = oid;
    	this.id = id;
    	this.type = type;
        this.parent = parent;

    }

    
    // Getters ------------------------------------------------------------------------------------
    public Long getOid() {
        return this.oid;
    }
    public String getId() {
        return this.id;
    }
    public String getType() {
        return this.type;
    }
    public String getParent() {
        return this.parent;
    }

    // Setters ------------------------------------------------------------------------------------
    public void setOid( Long oid ) {
        this.oid = oid;
    }
    public void setId( String id ) {
        this.id = id;
    }
    public void setType( String type ) {
        this.type = type;
    }
    public void setParent( String parent ) {
        this.parent = parent;
    }
    
    // Helpers ------------------------------------------------------------------------------------

    /*
     * Is this ComponentRelationship the same as the Supplied ComponentRelationship?
     */
    public boolean isComponentRelationshipSameAs(ComponentRelationship daocomponent){
    	
        //EMAP id, description, start stage, end stage, parents, synonyms

    	if ( this.getId().equals(daocomponent.getId()) && 
             this.getType() == daocomponent.getType() &&
             this.getParent() == daocomponent.getParent() ){

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
        return (other instanceof ComponentRelationship) && (oid != null) 
        		? oid.equals(((ComponentRelationship) other).oid) 
        		: (other == this);
    }

    /*
     * Returns the String representation of this User. Not required, it just pleases reading logs.
     */
    public String toString() {
        return String.format("ComponentRelationship [ oid=%d, id=%s, type=%s, parent=%s ]", 
        		oid, id, type, parent);
    }

}
	