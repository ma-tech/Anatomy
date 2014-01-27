/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomy
*
* Title:        ComponentSynonym.java
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
*                ComponentSynonym Table - ANA_OBO_COMPONENT_SYNONYM
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

public class ComponentSynonym {
    // Properties ---------------------------------------------------------------------------------
	/*
     *   ACS_OID      - bigint(20) unsigned NOT NULL AUTO_INCREMENT,
     *   ACS_OBO_ID   - varchar(25) NOT NULL,
     *   ACS_OBO_TEXT - varchar(1000) NOT NULL,
	 */
    private Long oid;
    private String id;
    private String text;

    // Constructors -------------------------------------------------------------------------------
    /*
     * Default constructor.
     */
    public ComponentSynonym() {
        
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
    public boolean isSameAs(ComponentSynonym daocomponentsynonym){

    	if ( this.getId().equals(daocomponentsynonym.getId()) && 
             this.getText() == daocomponentsynonym.getText() ){

        	return true;
        }
        else {
        	
            return false;
        }
    }

    /*
     * The OID is unique for each ComponentSynonym.
     *  So this should compare ComponentSynonym by OID only.
     */
    public boolean equals(Object other) {
    	
        return (other instanceof ComponentSynonym) && (oid != null) 
        		? oid.equals(((ComponentSynonym) other).oid) 
        		: (other == this);
    }

    /*
     * Returns the String representation of this ComponentSynonym.
     *  Not required, it just makes reading logs easier.
     */
    public String toString() {
    	
        return String.format("ComponentSynonym [ oid=%d, id=%s, text=%s ]", 
        		oid, id, text );
    }
}
	