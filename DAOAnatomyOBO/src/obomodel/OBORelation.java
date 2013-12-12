/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyOBO
*
* Title:        Relation.java
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
* Description:  A Wrapper Class for accessing OBO Relation Entities
*
* Maintenance:  Log changes below, with most recent at top of list.
*
* Who; When; What;
*
* Mike Wicks; February 2012; Create Class
*
*----------------------------------------------------------------------------------------------
*/
package obomodel;

public class OBORelation {
	// Constants ----------------------------------------------------------------------------------
    // Vars ---------------------------------------------------------------------------------------
	private String id;
    private String name;
    private String transitive;
    
    // Constructors -------------------------------------------------------------------------------
    /*
     * Construct a Relation 
     */
    public OBORelation() {
        this.id = "";
        this.name = "";
        this.transitive = "";
    }

    // Getters ------------------------------------------------------------------------------------
    public String getID() {
        return this.id;
    }
    public String getName() {
        return this.name;
    }
    public String getTransitive() {
        return this.transitive;
    }

    // Setters ------------------------------------------------------------------------------------
    public void setID( String id ) {
        this.id = id;
    }
    public void setName( String name ) {
        this.name = name;
    }
    public void setTransitive( String transitive ) {
        this.transitive = transitive;
    }

	// Actions ------------------------------------------------------------------------------------
    /*
     * NONE!
     */
}
