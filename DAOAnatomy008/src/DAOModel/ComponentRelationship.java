/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomy008
*
* Title:        ComponentRelationship.java
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
* Description:  This class represents a SQL Database Transfer Object for the 
*                ComponentRelationship Table - ANA_OBO_COMPONENT_RELATIONSHIP
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

import java.io.Serializable;

public class ComponentRelationship implements Serializable{
    // Properties ---------------------------------------------------------------------------------
	/*
     *  Columns:
     *   ACR_OID bigint(20) unsigned NOT NULL AUTO_INCREMENT,
     *   ACR_OBO_ID varchar(25) NOT NULL,
     *   ACR_OBO_TYPE varchar(25) NOT NULL,
     *   ACR_OBO_PARENT varchar(25) NOT NULL,
	 */
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
     * The OID is unique for each ComponentRelationship.
     *  So this should compare ComponentRelationship by OID only.
     */
    public boolean equals(Object other) {
        return (other instanceof ComponentRelationship) && (oid != null) 
        		? oid.equals(((ComponentRelationship) other).oid) 
        		: (other == this);
    }

    /*
     * Returns the String representation of this ComponentRelationship.
     *  Not required, it just pleases reading logs.
     */
    public String toString() {
        return String.format("ComponentRelationship [ oid=%d, id=%s, type=%s, parent=%s ]", 
        		oid, id, type, parent);
    }
}
	