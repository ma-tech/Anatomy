/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayer
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
* Version:      1
*
* Description:  This class represents a Data Transfer Object for the 
*                DerivedPartOfPerspectives Table - ANAD_PART_OF_PERSPECTIVE
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

public class DerivedPartOfPerspectives {
    // Properties ---------------------------------------------------------------------------------
	/*
     *   1. POP_PERSPECTIVE_FK => varchar(25)
     *   2. POP_APO_FK         => int(10)
     *   3. POP_IS_ANCESTOR    => tinyint
     *   4. POP_NODE_FK        => int(10)
	 */
    private String perspectiveFK; 
    private long partOfFK; 
    private int ancestor;
    private long nodeFK; 

    // Constructors -------------------------------------------------------------------------------
    /*
     * Default constructor.
     */
    public DerivedPartOfPerspectives() {
        
    }

    /*
     * Minimal constructor. Contains required fields.
     * Full constructor. Contains required and optional fields.
     *  The Full Constructor is the Minimal Constructor
     * 
     */
    public DerivedPartOfPerspectives(String perspectiveFK,
    		long partOfFK, 
    		int ancestor,    		
    		long nodeFK
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
    public long getPartOfFK() {
        return partOfFK;
    }
    public long getNodeFK() {
        return nodeFK;
    } 
    public int getAncestor() {
        return ancestor;
    } 

    // Setters ------------------------------------------------------------------------------------
    public void setPerspectiveFK(String perspectiveFK) {
        this.perspectiveFK = perspectiveFK;
    } 
    public void setPartOfFK(long partOfFK) {
        this.partOfFK = partOfFK;
    } 
    public void setNodeFK(long nodeFK) {
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
