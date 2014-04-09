/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyRebuild
*
* Title:        AnatomyInPerspective.java
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
* Description:  This Class represents a CSV File of AnatomyInPerspective Data
*                The class is instantiated with a 2D Array of Strings, and a Separator Character
*
* Link:         
* 
* Maintenance:  Log changes below, with most recent at top of list.
*
* Who; When; What;
*
* Mike Wicks; 24th Feb2014; Create Class
*
*----------------------------------------------------------------------------------------------
*/
package csvmodel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import csvmodel.NodeInPerspective;
import daolayer.DAOException;

public class AnatomyInPerspective {
    // Properties ---------------------------------------------------------------------------------

    private List<List<String>> csv2DStringArray;

    private List<List<String>> listStringNodeInPerspective;
    private List<String> listPublicId;
    private List<String> listComponentName;
    private List<NodeInPerspective> listObjectNodeInPerspective;

    private Set<String> setPublicId;
    private Set<String> setComponentName;
    

    // Constructors -------------------------------------------------------------------------------
    /*
     * Default constructor.
     */
    public AnatomyInPerspective() {
        
    	this.csv2DStringArray = new ArrayList<List<String>>();
    	
    	this.listStringNodeInPerspective = new ArrayList<List<String>>();

    	this.listObjectNodeInPerspective = new ArrayList<NodeInPerspective>();

    	this.listPublicId = new ArrayList<String>();
    	
    	this.listComponentName = new ArrayList<String>();
    }

    /*
     * constructor #2
     *  Contains required fields.
     */
    public AnatomyInPerspective(
    		List<List<String>> csv2DStringArray) {
    	
    	this.csv2DStringArray = csv2DStringArray;
    	
    	this.listStringNodeInPerspective = new ArrayList<List<String>>();

    	this.listObjectNodeInPerspective = new ArrayList<NodeInPerspective>();

    	this.listPublicId = new ArrayList<String>();
    	
    	this.listComponentName = new ArrayList<String>();
    }

    /*
     * Constructor #3
     */
    public AnatomyInPerspective(
    		List<List<String>> csv2DStringArray,
    		List<List<String>> listStringNodeInPerspective) {

    	this(csv2DStringArray);

    	this.listStringNodeInPerspective = listStringNodeInPerspective;

    	this.listObjectNodeInPerspective = new ArrayList<NodeInPerspective>();

    	this.listPublicId = new ArrayList<String>();
    	
    	this.listComponentName = new ArrayList<String>();
    }

    /*
     * Constructor #4
     */
    public AnatomyInPerspective(
    		List<List<String>> csv2DStringArray,
    		List<List<String>> listStringNodeInPerspective,
    		List<NodeInPerspective> listObjectNodeInPerspective) {

    	this(csv2DStringArray,
    			listStringNodeInPerspective);
    	
    	this.listObjectNodeInPerspective = listObjectNodeInPerspective;

    	this.listPublicId = new ArrayList<String>();

    	this.listComponentName = new ArrayList<String>();
    }
    
    /*
     * Constructor #5
     */
    public AnatomyInPerspective(
    		List<List<String>> csv2DStringArray,
    		List<List<String>> listStringNodeInPerspective,
     		List<NodeInPerspective> listObjectNodeInPerspective,
    		List<String> listPublicId) {

    	this(csv2DStringArray,
    			listStringNodeInPerspective,
    			listObjectNodeInPerspective);
    	
    	this.listPublicId = listPublicId;
    	
    	setSetPublicId(listPublicId);

    	this.listComponentName = new ArrayList<String>();
    }
    
    /*
     * Constructor #6
     */
    public AnatomyInPerspective(
    		List<List<String>> csv2DStringArray,
    		List<List<String>> listStringNodeInPerspective,
     		List<NodeInPerspective> listObjectNodeInPerspective,
    		List<String> listPublicId,
    		List<String> listComponentName) {

    	this(csv2DStringArray,
    			listStringNodeInPerspective,
    			listObjectNodeInPerspective,
    			listPublicId);
    	
    	this.listComponentName = listComponentName;
    	
    	setSetComponentName(listComponentName);
    }
    
    // Getters ------------------------------------------------------------------------------------
    public List<List<String>> getCsv2DStringArray() {
        return this.csv2DStringArray;
    }
    public List<List<String>> getListStringNodeInPerspective() {
    	return this.listStringNodeInPerspective;
    }
    public List<NodeInPerspective> getListObjectNodeInPerspective() {
    	return this.listObjectNodeInPerspective;
    }
    public List<String> getListPublicId() {
    	return this.listPublicId;
    }
    public Set<String> getSetPublicId() {
    	return this.setPublicId;
    }
    public List<String> getListComponentName() {
    	return this.listComponentName;
    }
    public Set<String> getSetComponentName() {
    	return this.setComponentName;
    }

    // Setters ------------------------------------------------------------------------------------
    public void setCsv2DStringArray( List<List<String>> csv2DStringArray ) {
        this.csv2DStringArray = csv2DStringArray;
    }
    public void setListStringNodeInPerspective( List<List<String>> listStringNodeInPerspective ) {
    	this.listStringNodeInPerspective = listStringNodeInPerspective;
    }
    public void setListObjectNodeInPerspective( List<NodeInPerspective> listObjectNodeInPerspective ) {
    	this.listObjectNodeInPerspective = listObjectNodeInPerspective;
    }
    public void setListPublicId( List<String> listPublicId ) {
    	this.listPublicId = listPublicId;
    }
    public void setSetPublicId( Set<String> setPublicId ) {
    	this.setPublicId = setPublicId;
    }
    public void setSetPublicId( List<String> listPublicId ) {
    	this.setPublicId = new HashSet<String>(listPublicId);
    }
    public void setListComponentName( List<String> listComponentName ) {
    	this.listComponentName = listComponentName;
    }
    public void setSetComponentName( Set<String> setComponentName ) {
    	this.setComponentName = setComponentName;
    }
    public void setSetComponentName( List<String> listComponentName ) {
    	this.setComponentName = new HashSet<String>(listComponentName);
    }
    
    // Helpers ------------------------------------------------------------------------------------

	/*
     * does the set of Component Names contain the one you want?
     */
	public boolean containsComponentName(String componentName) {
		
		if (this.setComponentName.contains(componentName) ) {
			
			return true;
		}
		else {
			
			return false;
		}
	}

	/*
     * does the set of Component Names contain the one you want?
     */
	public boolean containsPublicId(String publicId) {
		
		if (this.setPublicId.contains(publicId) ) {
			
			return true;
		}
		else {
			
			return false;
		}
	}

	/*
     * update the  Anatomy DB Table from 16 Lists of Objects
     */
	public void importCSVFile() throws Exception {
		
    	try {

    		splitArrayIntoLists();

    		convertStringListsIntoObjectLists();
        	
    	}
        catch ( Exception ex ) {
        	
            ex.printStackTrace();
        } 
	}

	/*
     * Sort the Mega 2D Array into 16 Sub 2D Arrays for Each Anatomy DB Table
     */
    public void splitArrayIntoLists() {
    	
        //Process each Row into Lists for Each Table
    	Iterator<List<String>> iteratorRow = this.csv2DStringArray.iterator();

    	List<String> listRow = new ArrayList<String>();
    	
     	while (iteratorRow.hasNext()) {

     		listRow = iteratorRow.next();
     			
     		this.listStringNodeInPerspective.add(listRow);
     	}
    }

    /*
     * Convert the List of Strings into Lists of Objects for Each AnatomyInPerspective DB Table
     */
    public void convertStringListsIntoObjectLists() {
    	
    	createObjectsFromNodeInPerspectiveStrings();
    }
    
    /*
     * Convert the 2D Array of Perspective Node Strings into a list of Perspective Objects
     */
    private void createObjectsFromNodeInPerspectiveStrings() {
    	
     	Iterator<List<String>> iteratorRow = this.listStringNodeInPerspective.iterator();
     	
        while (iteratorRow.hasNext()) {
        		
        	List<String> row = iteratorRow.next();

        	NodeInPerspective nodeinperspective = new NodeInPerspective();

     		int i = 1;
            
         	Iterator<String> iteratorColumn = row.iterator();
         	
     		while (iteratorColumn.hasNext()) {

     			String column = iteratorColumn.next();
            	
     			if ( i == 1 ) {
     	 			//System.out.println(column);
     				nodeinperspective.setPublicId(column);
     				this.listPublicId.add(column);
     			}
     			if ( i == 2 ) {
     				nodeinperspective.setPerspectiveName(column);
     			}
     			if ( i == 3 ) {
     				nodeinperspective.setComponentName(column);
     				this.listComponentName.add(column);
     			}
            	
     			i++;
     		}

 			//System.out.println(nodeinperspective.toStringThing());
 			
            this.listObjectNodeInPerspective.add(nodeinperspective);
     	}
        
        setSetPublicId(this.listPublicId);
        setSetComponentName(this.listComponentName);
    }

}
