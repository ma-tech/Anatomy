/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomy
*
* Title:        DerivedPartOfPerspectivesJsonFK.java
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
* Description:  This class represents a Data Transfer Object for 
*                All DerivedPartOfPerspectivesJsonFKs between ABSTRACT Nodes in the Anatomy DAG
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

public class DerivedPartOfPerspectivesJsonFK {
    // Properties ---------------------------------------------------------------------------------
	/*
     *   1. POP_PERSPECTIVE_FK => varchar(25)
     *   2. FULL_PATH_JSON     => varchar(3000)
	 */
    private String perspectiveFK; 
    private String fullPathJson; 

    // Constructors -------------------------------------------------------------------------------
    /*
     * Default constructor.
     */
    public DerivedPartOfPerspectivesJsonFK() {
        
    }

    /*
     * Minimal constructor. Contains required fields.
     * Full constructor. Contains required and optional fields.
     *  The Full Constructor is the Minimal Constructor
     * 
     */
    public DerivedPartOfPerspectivesJsonFK(String perspectiveFK,
    		String fullPathJson
    		) {
    	
    	this.perspectiveFK = perspectiveFK;
    	this.fullPathJson = fullPathJson;
    }

    // Getters ------------------------------------------------------------------------------------
    public String getPerspectiveFK() {
        return perspectiveFK;
    } 
    public String getFullPathJson() {
        return fullPathJson;
    }

    // Setters ------------------------------------------------------------------------------------
    public void setPerspectiveFK(String perspectiveFK) {
        this.perspectiveFK = perspectiveFK;
    } 
    public void setFullPathJson(String fullPathJson) {
        this.fullPathJson = fullPathJson;
    } 

    // Override -----------------------------------------------------------------------------------
    /*     
     * Returns the String representation of this DerivedPartOfPerspectivesJsonFK.
     *  Not required, it just aids log reading.
     */
    public String toString() {
    	
        return String.format("DerivedPartOfPerspectivesJsonFK [ perspectiveFK=%s, fullPathJson=%s ]", 
        		perspectiveFK, fullPathJson);
    }
}
