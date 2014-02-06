/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayer
*
* Title:        Project.java
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
* Description:  This class represents a SQL Database Transfer Object for the Project Table.
*                ANA_PROJECT
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

public class Project {
    // Properties ---------------------------------------------------------------------------------
	/*
     *  Columns:
     *   1. APJ_NAME - char(30)
	 */
    private String name; 

    // Constructors -------------------------------------------------------------------------------
    /*
     * Default constructor.
     */
    public Project() {
        
    }

    /*
     * Minimal constructor. Contains required fields.
     */
    public Project(String name) {

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
     * The Project Name is unique for each Project.
     *  So this should compare Project by Name only.
     */
    public boolean equals(Object other) {
        return (other instanceof Project) && (name != null) 
        		? name.equals(((Project) other).name) 
        		: (other == this);
    }

    /*
     * Is this Project the same as the Supplied Project?
     */
    public boolean isSameAs(Project daoproject){

    	if ( this.getName().equals(daoproject.getName()) ) {

        	return true;
        }
        else {

        	return false;
        }
    }

    /*
     * Returns the String representation of this project.
     *  Not required, it just makes reading logs easier.
     */
    public String toString() {
        return String.format("Project [ name=%s ]", 
            name); 
    }
}
