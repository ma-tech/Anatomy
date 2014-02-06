/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayer
*
* Title:        SourceFormat.java
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
* Description:  This class represents a SQL Database Transfer Object for the SourceFormat Table.
*                ANA_SOURCE
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

public class SourceFormat {
    // Properties ---------------------------------------------------------------------------------
	/*
     *   1. SFM_NAME      - varchar(30)
	 */
    private String name; 

    // Constructors -------------------------------------------------------------------------------
    /*
     * Default constructor.
     */
    public SourceFormat() {
        
    }

    /*
     * Minimal constructor. Contains required fields.
     */
    public SourceFormat(String name) {
    	
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

    // Helper -------------------------------------------------------------------------------------
    /*
     * Is this SourceFormat the same as the Supplied SourceFormat?
     */
    public boolean isSameAs(SourceFormat daosourceformat){

    	if (this.getName().equals(daosourceformat.getName()) ) {

        	return true;
        }
        else {

        	return false;
        }
    }

    /*
     * The relation ID is unique for each Node. So this should compare Node by ID only.
     */
    public boolean equals(Object other) {
      
    	return (other instanceof SourceFormat) && (name != null) 
        		? name.equals(((SourceFormat) other).name) 
        		: (other == this);
    }

    /*
     * Returns the String representation of this Node. Not required, it just makes reading logs easier.
     */
    public String toString() {
      
    	return String.format("SourceFormat [ name=%s ]", 
            name); 
    }
}
