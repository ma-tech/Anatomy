/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomy008
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
* Version: 1
*
* Description:  This class represents a SQL Database Transfer Object for the Project Table.
*                ANA_PROJECT
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

package DAOModel;

import java.io.Serializable;

public class Project implements Serializable {
    // Properties ---------------------------------------------------------------------------------
	/*
     *  Columns:
     *   1. APJ_NAME - char(300
	 */
    private String name; 

    // Constructors -------------------------------------------------------------------------------
    /*
     * Default constructor.
     */
    public Project() {
        // Always keep the default constructor alive in a Javabean class.
    }

    /*
     * Minimal constructor. Contains required fields.
     */

    /*
     * Full constructor. Contains required and optional fields.
     * 
     * The Full Constructor is the Minimal Constructor
     * 
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

    // Override -----------------------------------------------------------------------------------
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
     * Returns the String representation of this project.
     *  Not required, it just pleases reading logs.
     */
    public String toString() {
        return String.format("Project [ name=%s ]", 
            name); 
    }
}
