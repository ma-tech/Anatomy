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

import utility.ObjectConverter;

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

    /*
     * Minimal constructor. Contains required fields.
     * Full constructor. Contains required and optional fields.
     *  The Full Constructor is the Minimal Constructor
     * 
     */
    public DerivedPartOfPerspectives(String perspectiveFK,
    		String partOfFK, 
    		String ancestor,    		
    		String nodeFK
    		) {
    	
    	this.perspectiveFK = perspectiveFK;
    	this.partOfFK = ObjectConverter.convert(partOfFK, Long.class);
    	this.ancestor = ObjectConverter.convert(ancestor, Integer.class);
    	this.nodeFK = ObjectConverter.convert(nodeFK, Long.class);
    }

    // Getters ------------------------------------------------------------------------------------
    public String getPerspectiveFK() {
        return perspectiveFK;
    } 
    public long getPartOfFK() {
        return partOfFK;
    }
    public String getPartOfFKAsString() {
        return ObjectConverter.convert(partOfFK, String.class);
    }
    public int getAncestor() {
        return ancestor;
    } 
    public String getAncestorAsString() {
        return ObjectConverter.convert(ancestor, String.class);
    } 
    public long getNodeFK() {
        return nodeFK;
    } 
    public String getNodeFKAsString() {
        return ObjectConverter.convert(nodeFK, String.class);
    } 

    // Setters ------------------------------------------------------------------------------------
    public void setPerspectiveFK(String perspectiveFK) {
        this.perspectiveFK = perspectiveFK;
    } 
    public void setPartOfFK(long partOfFK) {
        this.partOfFK = partOfFK;
    } 
    public void setPartOfFK(String partOfFK) {
        this.partOfFK = ObjectConverter.convert(partOfFK, Long.class);
    } 
    public void setAncestor(int ancestor) {
        this.ancestor = ancestor;
    } 
    public void setAncestor(String ancestor) {
        this.ancestor = ObjectConverter.convert(ancestor, Integer.class);
    } 
    public void setNodeFK(long nodeFK) {
        this.nodeFK = nodeFK;
    } 
    public void setNodeFK(String nodeFK) {
        this.nodeFK = ObjectConverter.convert(nodeFK, Long.class);
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
