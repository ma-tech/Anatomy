/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomy
*
* Title:        Synonym.java
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
* Description:  This class represents a SQL Database Transfer Object for the Synonym Table.
*                ANA_SYNONYM
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

public class Synonym {
    // Properties ---------------------------------------------------------------------------------
	/*
     *   1. SYN_OID         - int(10) unsigned
     *   2. SYN_OBJECT_FK   - int(10) unsigned
     *   3. SYN_SYNONYM     - varchar(100)
	 */
    private Long oid; 
    private long thingFK; 
    private String name; 

    // Constructors -------------------------------------------------------------------------------
    /*
     * Default constructor.
     */
    public Synonym() {
        // Always keep the default constructor alive in a Javabean class.
    }

    /*
     * Minimal constructor. Contains required fields.
     */
    public Synonym(Long oid, 
    		long thingFK,
    	    String name) {
    	
        this.oid = oid;
	    this.thingFK = thingFK;
	    this.name = name;
    }

    // Getters ------------------------------------------------------------------------------------
    public Long getOid() {
        return oid;
    }
    public long getThingFK() {
        return thingFK;
    }
    public String getName() {
        return name;
    }

    // Setters ------------------------------------------------------------------------------------
    public void setOid(Long oid) {
        this.oid = oid;
    }
    public void setThingFK(long thingFK) {
        this.thingFK = thingFK;
    }
    public void setName(String name) {
        this.name = name;
    }

    // Override -----------------------------------------------------------------------------------
    /*
     * Is this Synonym the same as the Supplied Synonym?
     */
    public boolean isSameAs(Synonym daosynonym){

    	if (this.getThingFK() == daosynonym.getThingFK() &&
    		this.getName().equals(daosynonym.getName()) ) {

        	return true;
        }
        else {

        	return false;
        }
    }

    /*
     * The OID is unique for each Synonym.
     *  So this should compare Synonym by OID only.
     */
    public boolean equals(Object other) {
    	
        return (other instanceof Synonym) && (oid != null) 
        		? oid.equals(((Synonym) other).oid) 
        		: (other == this);
    }

    /*
     * Returns the String representation of this Synonym. 
     *  Not required, it just makes reading logs easier.
     */
    public String toString() {
    	
        return String.format("Synonym [ oid=%d, thingFK=%d, name=%s ]", 
            oid, thingFK, name); 
    }
}
