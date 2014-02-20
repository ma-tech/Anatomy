/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayer
*
* Title:        Editor.java
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
*                Editor Table - ANA_EDITOR
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

public class Editor {
    // Properties ---------------------------------------------------------------------------------
	/*
     *   1. EDI_OID  - int(10) unsigned
     *   2. EDI_NAME - varchar(50)
	 */
    private Long oid; 
    private String name; 

    // Constructors -------------------------------------------------------------------------------
    /*
     * Default constructor.
     */
    public Editor() {
        
    }

    /*
     * Minimal constructor. Contains required fields.
     */
    public Editor(Long oid, 
    	    String name) {
    	
        this.oid = oid;
	    this.name = name;
    }

    // Getters ------------------------------------------------------------------------------------
    public Long getOid() {
        return oid;
    }
    public String getName() {
        return name;
    }

    // Setters ------------------------------------------------------------------------------------
    public void setOid(Long oid) {
        this.oid = oid;
    }
    public void setName(String name) {
        this.name = name;
    }

    // Override -----------------------------------------------------------------------------------
    /*
     * Is this Editor the same as the Supplied Editor?
     */
    public boolean isSameAs(Editor daoeditor){


    	if ( this.getName().equals(daoeditor.getName()) ) {

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
        
    	return (other instanceof Editor) && (oid != null) 
        		? oid.equals(((Editor) other).oid) 
        		: (other == this);
    }

    /*
     * Returns the String representation of this Node. Not required, it just makes reading logs easier.
     */
    public String toString() {
      
    	return String.format("Editor [ oid=%d, name=%s ]", 
            oid, name); 
    }
    
    /*
     * Returns the String representation of this Node. Not required, it just makes reading logs easier.
     */
    public String toStringThing() {
      
    	return String.format("oid=%d, name=%s", 
            oid, name); 
    }
}
