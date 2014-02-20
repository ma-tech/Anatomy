/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayer
*
* Title:        DerivedPartOfPerspectivesFK.java
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
* Description:  This class represents a Data Transfer Object for the 
*                DerivedPartOfPerspectivesFK Table - ANAD_PART_OF_PERSPECTIVE
*                  with Foreign Keys materialised
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

import utility.ObjectConverter;

/*
 * This class represents a Data Transfer Object for the DerivedPartOfPerspectivesFK. 
 */
public class DerivedPartOfPerspectivesFK {
    // Properties ---------------------------------------------------------------------------------
	/*
     *   1. POP_PERSPECTIVE_FK => varchar(25)
     *   2. FULL_PATH          => varchar(1000)
     *   3. FULL_PATH_OIDS     => varchar(500)
     *   4. FULL_PATH_JSON     => varchar(3000)
     *   5. POP_IS_ANCESTOR    => tinyint
     *   6. EMAPA_PUBLIC_ID    => varchar(25)
     *   7. EMAP_PUBLIC_ID     => varchar(25)
	 */
    private String perspectiveFK; 
    private String fullPath; 
    private String fullPathOids;
    private String fullPathJson; 
    private int ancestor;
    private String nodeEmapa; 
    private String nodeEmap; 

    // Constructors -------------------------------------------------------------------------------
    /*
     * Default constructor.
     */
    public DerivedPartOfPerspectivesFK() {
        
    }

    /*
     * Minimal constructor. Contains required fields.
     * Full constructor. Contains required and optional fields.
     *  The Full Constructor is the Minimal Constructor
     * 
     */
    public DerivedPartOfPerspectivesFK(String perspectiveFK,
    		String fullPath, 
    		String fullPathOids,
    		String fullPathJson, 
    		int ancestor,    		
    		String nodeEmapa,
    		String nodeEmap
    		) {
    	
    	this.perspectiveFK = perspectiveFK;
    	this.fullPath = fullPath;
    	this.fullPathOids = fullPathOids;
    	this.fullPathJson = fullPathJson;
    	this.ancestor = ancestor;
    	this.nodeEmapa = nodeEmapa;
    	this.nodeEmap = nodeEmap;
    }

    /*
     * Minimal constructor. Contains required fields.
     * Full constructor. Contains required and optional fields.
     *  The Full Constructor is the Minimal Constructor
     * 
     */
    public DerivedPartOfPerspectivesFK(String perspectiveFK,
    		String fullPath, 
    		String fullPathOids,
    		String fullPathJson, 
    		String ancestor,    		
    		String nodeEmapa,
    		String nodeEmap
    		) {
    	
    	this.perspectiveFK = perspectiveFK;
    	this.fullPath = fullPath;
    	this.fullPathOids = fullPathOids;
    	this.fullPathJson = fullPathJson;
    	this.ancestor = ObjectConverter.convert(ancestor, Integer.class);
    	this.nodeEmapa = nodeEmapa;
    	this.nodeEmap = nodeEmap;
    }

    // Getters ------------------------------------------------------------------------------------
    public String getPerspectiveFK() {
        return perspectiveFK;
    } 
    public String getFullPath() {
        return fullPath;
    }
    public String getFullPathOids() {
        return fullPathOids;
    }
    public String getFullPathJson() {
        return fullPathJson;
    }
    public int getAncestor() {
        return ancestor;
    } 
    public String getNodeEmapa() {
        return nodeEmapa;
    } 
    public String getNodeEmap() {
        return nodeEmap;
    } 

    // Setters ------------------------------------------------------------------------------------
    public void setPerspectiveFK(String perspectiveFK) {
        this.perspectiveFK = perspectiveFK;
    } 
    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    } 
    public void setFullPathOids(String fullPathOids) {
        this.fullPathOids = fullPathOids;
    }
    public void setFullPathJson(String fullPathJson) {
        this.fullPathJson = fullPathJson;
    } 
    public void setAncestor(int ancestor) {
        this.ancestor = ancestor;
    } 
    public void setAncestor(String ancestor) {
        this.ancestor = ObjectConverter.convert(ancestor, Integer.class);
    } 
    public void setNodeEmapa(String nodeEmapa) {
        this.nodeEmapa = nodeEmapa;
    } 
    public void setNodeEmap(String nodeEmap) {
        this.nodeEmap = nodeEmap;
    } 

    // Override -----------------------------------------------------------------------------------
    /*
     * Is this DerivedPartOfPerspectives the same as the Supplied DerivedPartOfPerspectives?
     */
    public boolean isSameAs(DerivedPartOfPerspectivesFK daoderivedpartofperspectivesfk){

    	if ( this.getPerspectiveFK().equals(daoderivedpartofperspectivesfk.getPerspectiveFK()) &&
    		this.getFullPath().equals(daoderivedpartofperspectivesfk.getFullPath()) &&
    		this.getFullPathJson().equals(daoderivedpartofperspectivesfk.getFullPathJson()) &&
    		this.getNodeEmapa().equals(daoderivedpartofperspectivesfk.getNodeEmapa()) &&
    		this.getNodeEmap().equals(daoderivedpartofperspectivesfk.getNodeEmap()) &&
    		this.getAncestor() == daoderivedpartofperspectivesfk.getAncestor() ) {

        	return true;
        }
        else {

        	return false;
        }
    }

    /*
     * Returns the String representation of this DerivedPartOfPerspectivesFK.
     *  Not required, it just aids log reading.
     */
    public String toString() {
    	
        return String.format("DerivedPartOfPerspectivesFK [ perspectiveFK=%s, fullPath=%s, fullPathJson=%s, nodeEmapa=%s, nodeEmap=%s, ancestor=%d ]", 
        		perspectiveFK, fullPath, fullPathJson, nodeEmapa, nodeEmap, ancestor);
    }
}
