/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayer
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
* Version:      1
*
* Description:  This class represents a SQL Database Transfer Object for the 
*                ComponentRelationship Table - ANA_OBO_COMPONENT_RELATIONSHIP
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

public class ComponentRelationship {
    // Properties ---------------------------------------------------------------------------------
	/*
     *   ACR_OID             - bigint(20) unsigned NOT NULL AUTO_INCREMENT,
     *   ACR_OBO_CHILD       - varchar(25) NOT NULL,
     *   ACR_OBO_CHILD_START - int NOT NULL,
     *   ACR_OBO_CHILD_STOP  - int NOT NULL,
     *   ACR_OBO_TYPE        - varchar(25) NOT NULL,
     *   ACR_OBO_PARENT      - varchar(25) NOT NULL,
	 */
    private Long oid;
    private String child;
    private long childStart;
    private long childStop;
    private String type;
    private String parent;

    // Constructors -------------------------------------------------------------------------------
    /*
     * Default constructor.
     */
    public ComponentRelationship() {
        
    }

    /*
     * Minimal constructor. Contains required fields.
     */
    public ComponentRelationship(Long oid,
    		String child, 
    		long childStart,
    		long childStop,
    		String type,
    		String parent) {
    	
    	this.oid = oid;
    	this.child = child;
    	this.childStart = childStart;
    	this.childStop = childStop;
    	this.type = type;
        this.parent = parent;
    }
    
    // Getters ------------------------------------------------------------------------------------
    public Long getOid() {
        return this.oid;
    }
    public String getChild() {
        return this.child;
    }
    public long getChildStart() {
        return this.childStart;
    }
    public long getChildStop() {
        return this.childStop;
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
    public void setChild( String child ) {
        this.child = child;
    }
    public void setChildStart( long childStart ) {
        this.childStart = childStart;
    }
    public void setChildStop( long childStop ) {
        this.childStop = childStop;
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
    public boolean isSameAs(ComponentRelationship daocomponentrelationship){
    	
    	if ( this.getChild().equals(daocomponentrelationship.getChild()) && 
    		this.getChildStart() == daocomponentrelationship.getChildStart() && 
    		this.getChildStop() == daocomponentrelationship.getChildStop() && 
    		this.getType().equals(daocomponentrelationship.getType()) && 
    		this.getParent().equals(daocomponentrelationship.getParent()) ) {
    			 
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
     *  Not required, it just makes reading logs easier.
     */
    public String toString() {
    	
        return String.format("ComponentRelationship [ oid=%d, child=%s, childStart=%d, childStop=%d, type=%s, parent=%s ]", 
        		oid, child, childStart, childStop, type, parent);
    }
}
	