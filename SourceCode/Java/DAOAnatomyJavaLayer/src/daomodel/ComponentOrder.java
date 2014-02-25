/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayer
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
* Version:      1
*
* Description:  This class represents a SQL Database Transfer Object for the 
*                ComponentOrder Table - ANA_OBO_COMPONENT_ORDER
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

import utility.ObjectConverter;

public class ComponentOrder {
    // Properties ---------------------------------------------------------------------------------
	/*
     *   ACO_OID               - bigint(20) unsigned NOT NULL AUTO_INCREMENT,
     *   ACO_OBO_CHILD         - varchar(25) NOT NULL,
     *   ACO_OBO_PARENT        - varchar(25) NOT NULL,
     *   ACO_OBO_TYPE          - varchar(25) NOT NULL,
     *   ACO_OBO_ALPHA_ORDER   - int(20) unsigned NULL,
     *   ACO_OBO_SPECIAL_ORDER - int(20) unsigned NULL,
	 */
    private Long oid;
    private String child;
    private String parent;
    private String type;
    private long alphaorder;
    private long specialorder;

    // Constructors -------------------------------------------------------------------------------
    /*
     * Default constructor.
     */
    public ComponentOrder() {
        
    }

    /*
     * Minimal constructor. Contains required fields.
     */
    public ComponentOrder(Long oid,
    		String child, 
    		String parent,
    		String type,
    		long alphaorder,
    		long specialorder) {
    	
    	this.oid = oid;
    	this.child = child;
        this.parent = parent;
        this.type = type;
        this.alphaorder = alphaorder;
        this.specialorder = specialorder;
    }
    
    /*
     * Minimal constructor. Contains required fields.
     */
    public ComponentOrder(Long oid,
    		String child, 
    		String parent,
    		String type,
    		String alphaorder,
    		String specialorder) {
    	
    	this.oid = oid;
    	this.child = child;
        this.parent = parent;
        this.type = type;
        this.alphaorder = ObjectConverter.convert(alphaorder, Long.class);
        this.specialorder = ObjectConverter.convert(specialorder, Long.class);
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
    public long getAlphaorder() {
        return this.alphaorder;
    }
    public String getAlphaorderAsString() {
        return ObjectConverter.convert(alphaorder, String.class);
    }
    public long getSpecialorder() {
        return this.specialorder;
    }
    public String getSpecialorderAsString() {
        return ObjectConverter.convert(specialorder, String.class);
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
    public void setAlphaorder( long alphaorder ) {
        this.alphaorder = alphaorder;
    }
    public void setAlphaorder( String alphaorder ) {
        this.alphaorder = ObjectConverter.convert(alphaorder, Long.class);
    }
    public void setSpecialorder( long specialorder ) {
        this.specialorder = specialorder;
    }
    public void setSpecialorder( String specialorder ) {
        this.specialorder = ObjectConverter.convert(specialorder, Long.class);
    }
    
    // Helpers ------------------------------------------------------------------------------------
    /*
     * Is this ComponentOrder the same as the Supplied ComponentOrder?
     */
    public boolean isSameAs(ComponentOrder daocomponentorder){
    	
    	if ( this.getChild().equals(daocomponentorder.getChild()) && 
             this.getParent() == daocomponentorder.getParent() && 
             this.getType() == daocomponentorder.getType() && 
             this.getAlphaorder() == daocomponentorder.getAlphaorder() && 
             this.getSpecialorder() == daocomponentorder.getSpecialorder() ){

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
     *  Not required, it just makes reading logs easier.
     */
    public String toString() {
    	
        return String.format("ComponentOrder [ oid=%d, child=%s, parent=%s, type=%s, alphaorder=%d, specialorder=%d ]", 
        		oid, child, parent, type, alphaorder, specialorder);
    }
}
	