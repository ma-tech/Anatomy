/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayer
*
* Title:        SynonymFK.java
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
* Description:  This class represents a SQL Database Transfer Object for the SynonymFK Table.
*                ANA_SYNONYM
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

public class SynonymFK {
    // Properties ---------------------------------------------------------------------------------
	/*
     *   1. SYN_OID         - int(10) unsigned
     *   2. SYN_OBJECT_FK   - int(10) unsigned
     *   3. SYN_SYNONYM     - varchar(100)
	 */
    private Long oid; 
    private String thingNameFK; 
    private String name; 

    // Constructors -------------------------------------------------------------------------------
    /*
     * Default constructor.
     */
    public SynonymFK() {
        
    }

    /*
     * Minimal constructor. Contains required fields.
     */
    public SynonymFK(Long oid, 
    		String thingNameFK,
    	    String name) {
    	
        this.oid = oid;
	    this.thingNameFK = thingNameFK;
	    this.name = name;
    }

    // Getters ------------------------------------------------------------------------------------
    public Long getOid() {
        return oid;
    }
    public String getThingNameFK() {
        return thingNameFK;
    }
    public String getName() {
        return name;
    }

    // Setters ------------------------------------------------------------------------------------
    public void setOid(Long oid) {
        this.oid = oid;
    }
    public void setThingNameFK(String thingNameFK) {
        this.thingNameFK = thingNameFK;
    }
    public void setName(String name) {
        this.name = name;
    }

    // Override -----------------------------------------------------------------------------------
    /*
     * Is this SynonymFK the same as the Supplied SynonymFK?
     */
    public boolean isSameAs(SynonymFK daosynonym){

    	if (this.getThingNameFK().equals(daosynonym.getThingNameFK()) &&
    		this.getName().equals(daosynonym.getName()) ) {

        	return true;
        }
        else {

        	return false;
        }
    }

    /*
     * The OID is unique for each SynonymFK.
     *  So this should compare SynonymFK by OID only.
     */
    public boolean equals(Object other) {
    	
        return (other instanceof SynonymFK) && (oid != null) 
        		? oid.equals(((SynonymFK) other).oid) 
        		: (other == this);
    }

    /*
     * Returns the String representation of this SynonymFK. 
     *  Not required, it just makes reading logs easier.
     */
    public String toString() {
    	
        return String.format("SynonymFK [ oid=%d, thingNameFK=%s, name=%s ]", 
            oid, thingNameFK, name); 
    }

    /*
     * Returns the String representation of this SynonymFK. 
     *  Not required, it just makes reading logs easier.
     */
    public String toStringThing() {
    	
        return String.format("oid=%d, thingNameFK=%d, name=%s", 
            oid, thingNameFK, name); 
    }
}
