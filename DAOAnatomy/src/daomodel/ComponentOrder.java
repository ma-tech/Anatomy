/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomy008
*
* Title:        ComponentOrder.java
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
*                ComponentOrder Table - ANA_OBO_COMPONENT_RELATIONSHIP
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

public class ComponentOrder {
    // Properties ---------------------------------------------------------------------------------
	/*
     *  Columns:
     *   ACO_OID bigint(20) unsigned NOT NULL AUTO_INCREMENT,
     *   ACO_OBO_ID varchar(25) NOT NULL,
     *   ACO_OBO_PARENT varchar(25) NOT NULL,
     *   ACO_OBO_TYPE varchar(25) NOT NULL,
     *   ACO_OBO_ALPHA_ORDER int(20) unsigned NULL,
     *   ACO_OBO_SPECIAL_ORDER int(20) unsigned NULL,
	 */
    private Long oid;
    private String child;
    private String parent;
    private String type;
    private Long alphaorder;
    private Long specialorder;

    // Constructors -------------------------------------------------------------------------------
    /*
     * Default constructor.
     */
    public ComponentOrder() {
        // Always keep the default constructor alive in a Javabean class.
    }

    /*
     * Minimal constructor. Contains required fields.
     */
    public ComponentOrder(Long oid,
    		String child, 
    		String parent,
    		String type,
    		Long alphaorder,
    		Long specialorder) {
    	
    	this.oid = oid;
    	this.child = child;
        this.parent = parent;
        this.type = type;
        this.alphaorder = alphaorder;
        this.specialorder = specialorder;
    }
    
    // Getters ------------------------------------------------------------------------------------
    public Long getOid() {
        return this.oid;
    }
    public String getChild() {
        return this.child;
    }
    public String getParent() {
        return this.parent;
    }
    public String getType() {
        return this.type;
    }
    public Long getAlphaorder() {
        return this.alphaorder;
    }
    public Long getSpecialorder() {
        return this.specialorder;
    }

    // Setters ------------------------------------------------------------------------------------
    public void setOid( Long oid ) {
        this.oid = oid;
    }
    public void setChild( String child ) {
        this.child = child;
    }
    public void setParent( String parent ) {
        this.parent = parent;
    }
    public void setType( String type ) {
        this.type = type;
    }
    public void setAlphaorder( Long alphaorder ) {
        this.alphaorder = alphaorder;
    }
    public void setSpecialorder( Long specialorder ) {
        this.specialorder = specialorder;
    }
    
    // Helpers ------------------------------------------------------------------------------------
    /*
     * Is this ComponentOrder the same as the Supplied ComponentOrder?
     */
    public boolean isComponentOrderSameAs(ComponentOrder daocomponent){
    	
        //EMAP id, description, start stage, end stage, parents, synonyms

    	if ( this.getChild().equals(daocomponent.getChild()) && 
             this.getParent() == daocomponent.getParent() && 
             this.getType() == daocomponent.getType() && 
             this.getAlphaorder() == daocomponent.getAlphaorder() && 
             this.getSpecialorder() == daocomponent.getSpecialorder()){

        	return true;
        }
        else {
            return false;
        }
    }

    /*
     * The OID is unique for each ComponentOrder.
     *  So this should compare ComponentOrder by OID only.
     */
    public boolean equals(Object other) {
        return (other instanceof ComponentOrder) && (oid != null) 
        		? oid.equals(((ComponentOrder) other).oid) 
        		: (other == this);
    }

    /*
     * Returns the String representation of this ComponentOrder.
     *  Not required, it just pleases reading logs.
     */
    public String toString() {
        return String.format("ComponentOrder [ oid=%d, child=%s, parent=%s, type=%s, alphaorder=%d, specialorder=%d ]", 
        		oid, child, parent, type, alphaorder, specialorder);
    }
}
	