/*
-----------------------------------------------------------------------------------------------
# Project:      DAOAnatomyRebuild
#
# Title:        ComponentSynonym.java
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
# Description:  A Wrapper Object for an OBO ComponentSynonym inserted into the Anatomy Database
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

public class ComponentSynonym implements Serializable{

    // daocomponentsynonym fields
    private Long oid;
    private String id;
    private String text;

    // Constructors -------------------------------------------------------------------------------
    /*
     * Default constructor.
     */
    public ComponentSynonym() {
        // Always keep the default constructor alive in a Javabean class.
    }

    /*
     * Minimal constructor. Contains required fields.
     */
    public ComponentSynonym(Long oid,
    		String id, 
    		String text) {
    	
    	this.oid = oid;
    	this.id = id;
    	this.text = text;
 
    }

    
    // Getters ------------------------------------------------------------------------------------
    public Long getOid() {
        return this.oid;
    }
    public String getId() {
        return this.id;
    }
    public String getText() {
        return this.text;
    }

    // Setters ------------------------------------------------------------------------------------
    public void setOid( Long oid ) {
        this.oid = oid;
    }
    public void setId( String id ) {
        this.id = id;
    }
    public void setText( String text ) {
        this.text = text;
    }
    
    // Helpers ------------------------------------------------------------------------------------

    /*
     * Is this ComponentSynonym the same as the Supplied ComponentSynonym?
     */
    public boolean isComponentSynonymSameAs(ComponentSynonym daocomponentsynonym){
    	
        //EMAP id, description, start stage, end stage, parents, synonyms

    	if ( this.getId().equals(daocomponentsynonym.getId()) && 
             this.getText() == daocomponentsynonym.getText() ){

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
        return (other instanceof ComponentSynonym) && (oid != null) 
        		? oid.equals(((ComponentSynonym) other).oid) 
        		: (other == this);
    }

    /*
     * Returns the String representation of this User. Not required, it just pleases reading logs.
     */
    public String toString() {
        return String.format("ComponentSynonym [ oid=%d, id=%s, text=%s ]", 
        		oid, id, text );
    }

}
	