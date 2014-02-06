/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayer
*
* Title:        ComponentAlternative.java
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
*                ComponentAlternative Table - ANA_OBO_COMPONENT_ALTERNATIVE
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

public class ComponentAlternative {
    // Properties ---------------------------------------------------------------------------------
	/*
     *   ACA_OID        - bigint(20) unsigned NOT NULL AUTO_INCREMENT,
     *   ACA_OBO_ID     - varchar(25) NOT NULL,
     *   ACA_OBO_ALT_ID - varchar(25) NOT NULL,
	 */
    private Long oid;
    private String id;
    private String altId;

    // Constructors -------------------------------------------------------------------------------
    /*
     * Default constructor.
     */
    public ComponentAlternative() {
        
    }

    /*
     * Minimal constructor. Contains required fields.
     */
    public ComponentAlternative(Long oid,
    		String id, 
    		String altId) {
    	
    	this.oid = oid;
    	this.id = id;
    	this.altId = altId;
    }
    
    // Getters ------------------------------------------------------------------------------------
    public Long getOid() {
        return this.oid;
    }
    public String getId() {
        return this.id;
    }
    public String getAltId() {
        return this.altId;
    }

    // Setters ------------------------------------------------------------------------------------
    public void setOid( Long oid ) {
        this.oid = oid;
    }
    public void setId( String id ) {
        this.id = id;
    }
    public void setAltId( String altId ) {
        this.altId = altId;
    }
    
    // Helpers ------------------------------------------------------------------------------------
    /*
     * Is this ComponentAlternative the same as the Supplied ComponentAlternative?
     */
    public boolean isSameAs(ComponentAlternative daocomponentalternative){

    	if ( this.getId().equals(daocomponentalternative.getId()) && 
             this.getAltId() == daocomponentalternative.getAltId() ){

        	return true;
        }
        else {
        	
            return false;
        }
    }

    /*
     * The OID is unique for each ComponentAlternative.
     *  So this should compare ComponentAlternative by OID only.
     */
    public boolean equals(Object other) {
    	
        return (other instanceof ComponentAlternative) && (oid != null) 
        		? oid.equals(((ComponentAlternative) other).oid) 
        		: (other == this);
    }

    /*
     * Returns the String representation of this ComponentAlternative.
     *  Not required, it just makes reading logs easier.
     */
    public String toString() {
    	
        return String.format("ComponentAlternative [ oid=%d, id=%s, altId=%s ]", 
        		oid, id, altId );
    }
}
	