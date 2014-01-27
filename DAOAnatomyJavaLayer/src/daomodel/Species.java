/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomy
*
* Title:        Species.java
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
* Description:  This class represents a SQL Database Transfer Object for the Species Table.
*                REF_SPECIES
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

public class Species {
    // Properties ---------------------------------------------------------------------------------
	/*
     *   1. RSP_NAME                 - varchar(20)      
     *   2. RSP_LATIN_NAME           - varchar(30) 
     *   3. RSP_TIMED_NODE_ID_PREFIX - varchar(20) 
     *   4. RSP_NODE_ID_PREFIX       - varchar(20) 
	 */
    private String name; 
    private String latinName; 
    private String timedPrefix; 
    private String abstractPrefix; 

    // Constructors -------------------------------------------------------------------------------
    /*
     * Default constructor.
     */
    public Species() {
        
    }

    /*
     * Minimal constructor. Contains required fields.
     * Full constructor. Contains required and optional fields.
     *  The Full Constructor is the Minimal Constructor
     */
    public Species(String name, 
    		String latinName, 
    		String timedPrefix, 
    		String abstractPrefix) {
    	
        this.name = name; 
        this.latinName = latinName; 
        this.timedPrefix = timedPrefix;
        this.abstractPrefix = abstractPrefix;
    }

    // Getters ------------------------------------------------------------------------------------
    public String getName() {
        return name;
    }
    public String getLatinName() {
        return latinName;
    }
    public String getTimedPrefix() {
        return timedPrefix;
    }
    public String getAbstractPrefix() {
        return abstractPrefix;
    }

    // Setters ------------------------------------------------------------------------------------
    public void setName(String name) {
        this.name = name;
    }
    public void setLatinName(String latinName) {
        this.latinName = latinName;
    }
    public void setTimedPrefix(String timedPrefix) {
        this.timedPrefix = timedPrefix;
    }
    public void setAbstractPrefix(String abstractPrefix) {
        this.abstractPrefix = abstractPrefix;
    }

    // Override -----------------------------------------------------------------------------------
    /*
     * Is this Species the same as the Supplied Species?
     */
    public boolean isSameAs(Species daospecies){

    	if (this.getName().equals(daospecies.getName()) &&
    		this.getLatinName() == daospecies.getLatinName() &&
    		this.getTimedPrefix() == daospecies.getTimedPrefix() &&
    		this.getAbstractPrefix() == daospecies.getAbstractPrefix() ) {

        	return true;
        }
        else {

        	return false;
        }
    }

    /*
     * The relation OID is unique for each Species. 
     *  So this should compare Species by OID only.
     */
    public boolean equals(Object other) {
    	
        return (other instanceof Species) && (name != null) 
        		? name.equals(((Species) other).name) 
        		: (other == this);
    }

    /*
     * Returns the String representation of this Species.
     *  Not required, it just aids log reading.
     */
    public String toString() {
    	
        return String.format("Species [ name=%s, latinName=%s, timedPrefix=%s, abstractPrefix=%s ]", 
            name, latinName, timedPrefix, abstractPrefix);
    }
}
