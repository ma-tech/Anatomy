/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomy
*
* Title:        DerivedPartOfPerspectives.java
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
* Description:  This class represents a Data Transfer Object for the 
*                DerivedPartOfPerspectives Table - ANAD_PART_OF_PERSPECTIVE
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
package daomodel;

public class DerivedPartOfPerspectives {
    // Properties ---------------------------------------------------------------------------------
	/*
     *   1. POP_PERSPECTIVE_FK => varchar(25)
     *   2. POP_APO_FK         => int(10)
     *   3. POP_IS_ANCESTOR    => tinyint
     *   4. POP_NODE_FK        => int(10)
	 */
    private String perspectiveFK; 
    private Long partOfFK; 
    private int ancestor;
    private Long nodeFK; 

    // Constructors -------------------------------------------------------------------------------
    /*
     * Default constructor.
     */
    public DerivedPartOfPerspectives() {
        // Always keep the default constructor alive in a Javabean class.
    }

    /*
     * Minimal constructor. Contains required fields.
     * Full constructor. Contains required and optional fields.
     *  The Full Constructor is the Minimal Constructor
     * 
     */
    public DerivedPartOfPerspectives(String perspectiveFK,
    		Long partOfFK, 
    		int ancestor,    		
    		Long nodeFK
    		) {
    	
    	this.perspectiveFK = perspectiveFK;
    	this.partOfFK = partOfFK;
    	this.ancestor = ancestor;
    	this.nodeFK = nodeFK;
    }

    // Getters ------------------------------------------------------------------------------------
    public String getPerspectiveFK() {
        return perspectiveFK;
    } 
    public Long getPartOfFK() {
        return partOfFK;
    }
    public Long getNodeFK() {
        return nodeFK;
    } 
    public int getAncestor() {
        return ancestor;
    } 

    // Setters ------------------------------------------------------------------------------------
    public void setPerspectiveFK(String perspectiveFK) {
        this.perspectiveFK = perspectiveFK;
    } 
    public void setPartOfFK(Long partOfFK) {
        this.partOfFK = partOfFK;
    } 
    public void setNodeFK(Long nodeFK) {
        this.nodeFK = nodeFK;
    } 
    public void setAncestor(int ancestor) {
        this.ancestor = ancestor;
    } 

    // Override -----------------------------------------------------------------------------------
    /*
     * Is this DerivedPartOfPerspectives the same as the Supplied DerivedPartOfPerspectives?
     */
    public boolean isSameAs(DerivedPartOfPerspectives daoderivedpartofperspectives){


    	if ( this.getPerspectiveFK().equals(daoderivedpartofperspectives.getPerspectiveFK()) &&  
    		this.getPartOfFK() == daoderivedpartofperspectives.getPartOfFK() &&  
    		this.getNodeFK() == daoderivedpartofperspectives.getNodeFK() &&  
    		this.getAncestor() == daoderivedpartofperspectives.getAncestor() ) {

        	return true;
        }
        else {

        	return false;
        }
    }

    /*
     * Returns the String representation of this DerivedPartOfPerspectives.
     *  Not required, it just aids log reading.
     */
    public String toString() {
    	
        return String.format("DerivedPartOfPerspectives [ perspectiveFK=%s, partOfFK=%d, nodeFK=%d, ancestor=%d ]", 
        		perspectiveFK, partOfFK, nodeFK, ancestor);
    }
}
