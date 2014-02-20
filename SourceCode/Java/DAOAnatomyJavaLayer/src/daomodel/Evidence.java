/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayer
*
* Title:        Evidence.java
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
*                Evidence Table - ANA_EVIDENCE
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

public class Evidence {
    // Properties ---------------------------------------------------------------------------------
	/*
     *   1. EVI_NAME - varchar(50)
	 */
    private String name; 

    // Constructors -------------------------------------------------------------------------------
    /*
     * Default constructor.
     */
    public Evidence() {
        
    }

    /*
     * Minimal constructor. Contains required fields.
     */
    public Evidence(
    	    String name) {
    	
	    this.name = name;
    }

    // Getters ------------------------------------------------------------------------------------
    public String getName() {
        return name;
    }

    // Setters ------------------------------------------------------------------------------------
    public void setName(String name) {
        this.name = name;
    }

    // Override -----------------------------------------------------------------------------------
    /*
     * Is this Evidence the same as the Supplied Evidence?
     */
    public boolean isSameAs(Evidence daoevidence){


    	if ( this.getName().equals(daoevidence.getName()) ) {

        	return true;
        }
        else {

        	return false;
        }
    }

    /*
     * Returns the String representation of this Node. Not required, it just makes reading logs easier.
     */
    public String toString() {
      
    	return String.format("Evidence [ name=%s ]", 
            name); 
    }
}
