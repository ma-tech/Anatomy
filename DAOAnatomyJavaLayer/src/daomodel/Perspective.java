/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayer
*
* Title:        Perspective.java
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
* Description:  This class represents a SQL Database Transfer Object for the Perspective Table.
*                ANA_PERSPECTIVE_AMBIT
*                 (A List of all the Perspectives) 
*
* Link:         
* 
* Maintenance:  Log changes below, with most recent at top of list.
*
* Who; When; What;
*
* Mike Wicks; 8th December 2013; Create Class
*
*----------------------------------------------------------------------------------------------
*/
package daomodel;

public class Perspective {
    // Properties ---------------------------------------------------------------------------------
	/*
     *   1. PSP_NAME           - varchar(25)
     *   2. PSP_COMMENTS       - varchar(1024)
 	 */
    private String name; 
    private String comments; 

    // Constructors -------------------------------------------------------------------------------
    /*
     * Default constructor.
     */
    public Perspective() {
        
    }

    /*
     * Minimal constructor. Contains required fields.
     */
    public Perspective(String name,  
    	    String comments) {
    	
	    this.name = name;
	    this.comments = comments;
    }

    // Getters ------------------------------------------------------------------------------------
    public String getName() {
        return name;
    }
    public String getComments() {
        return comments;
    }

    // Setters ------------------------------------------------------------------------------------
    public void setName(String name) {
        this.name = name;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }

    // Override -----------------------------------------------------------------------------------
    /*
     * Is this PerspectiveAmbit the same as the Supplied PerspectiveAmbit?
     */
    public boolean isSameAs(Perspective daoperspective){

    	if ( this.getName().equals(daoperspective.getName()) &&
    		this.getComments().equals(daoperspective.getComments()) ) {

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
      
    	return (other instanceof Perspective) && (name != null) 
        		? name.equals(((Perspective) other).name) 
        		: (other == this);
    }

    /*
     * Returns the String representation of this Node. Not required, it just makes reading logs easier.
     */
    public String toString() {
        
    	return String.format("PerspectiveAmbit [ name=%s, comments=%s ]", 
            name, comments); 
    }
}
