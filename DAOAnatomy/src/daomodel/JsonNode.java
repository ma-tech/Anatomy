/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomy
*
* Title:        JsonNode.java
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
* Description:  This class represents a Node formatted in JSON
*               This class represents an object that represents JSON node values
*                especially for using with AJAX calls from jsTree
*
* Link:         http://balusc.blogspot.com/2008/07/dao-tutorial-data-layer.html
* 
* Maintenance:  Log changes below, with most recent at top of list.
*
* Who; When; What;
*
* Mike Wicks; 21st March 2012; Create Class
*----------------------------------------------------------------------------------------------
*/
package daomodel;

public class JsonNode {
    
    private String ID;
    private String extID;
    private String abstractID;
    private String timedStage;
    private String startStage;
    private String endStage;
    private String name;
    private int childCount;

    // Constructors -------------------------------------------------------------------------------
    /*
     * Default constructor.
     */
    public JsonNode() {
        // Always keep the default constructor alive in a Javabean class.
    }

    /*
     * Constructor for Timed JsonNode
     */
    public JsonNode(String extID, String ID, String abstractID, String timedStage, String name, int childCount) {        
        this.extID = extID;
        this.ID = ID;
        this.abstractID = abstractID;
        this.timedStage = timedStage; 
        this.name = name;
        this.childCount = childCount;
    }
    
    /*
     * Constructor for Abstract JsonNode
     */
    public JsonNode(String extID, String ID, String abstractID, String startStage, String endStage, String name, int childCount) {        
        this.extID = extID;
        this.ID = ID;
        this.abstractID = abstractID;
        this.startStage = startStage; 
        this.endStage = endStage;
        this.name = name;
        this.childCount = childCount;
    }
    
    // Getters ------------------------------------------------------------------------------------
    public String getExtID() {
        return extID;
    }
    public String getID() {
        return ID;
    }
    public String getTimedStage() {
        return timedStage;
    }
    public String getStartStage() {
        return startStage;
    }
    public String getEndStage() {
        return endStage;
    }
    public String getName() {
        return name;
    }
    public int getChildCount() {
        return childCount;
    }

    // Setters ------------------------------------------------------------------------------------
    public void setExtID(String extID) {
        this.extID = extID;
    }
    public void setID(String ID) {
        this.ID = ID;
    }
    public void setTimedStage(String timedStage) {
        this.timedStage = timedStage;
    }
    public void setStartStage(String startStage) {
        this.startStage = startStage;
    }
    public void setEndStage(String endStage) {
        this.endStage = endStage;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setChildCount(int childCount) {
        this.childCount = childCount;
    }
    
    // Helper -------------------------------------------------------------------------------------
    //
    public String printJsonNodeTimed() {
        //make closed node if we know there are children
    	if (childCount>0) {
    		return "{\"attr\": {\"ext_id\": \"" +
    				  extID +
    				  "\",\"id\": \"" +
    				  ID +
    				  "\",\"abstract_id\": \"" + 
    				  abstractID +
    				  "\",\"stage\": \"" +
    				  timedStage +
    				  "\",\"name\": \"" +
    				  name +
    				  "\"},\"data\": \"" +
    				  name +
    				  //"(" + 
    				  //childCount +
    				  //")\"," +
    				  "\", \"state\": \"closed\"},";
    	}
    	else {
    		return "{\"attr\": {\"ext_id\": \"" +
  				  extID +
  				  "\",\"id\": \"" +
  				  ID +
  				  "\",\"abstract_id\": \"" + 
    			  abstractID +
  				  "\",\"stage\": \"" + 
  				  timedStage +
  				  "\",\"name\": \"" +
  				  name +
  				  "\"},\"data\": \"" +
  				  name +
  				  "\"},";
    	}
    	
    }
    
    //
    public String printJsonNodeAbstract() {
    
    	if (childCount>0) {
    		return "{\"attr\": {\"ext_id\": \"" +
    				  extID +
    				  "\",\"id\": \"" +
    				  ID +
    				  "\",\"start\": \"" + 
    				  startStage +
    				  "\",\"end\": \"" + 
    				  endStage +
    				  "\",\"name\": \"" +
    				  name +
    				  "\"},\"data\": \"" +
    				  name +
    				  //"(" + 
    				  //childCount +
    				  //")\"," +
    				  "\", \"state\": \"closed\"},";
    	}
    	else {
    		return "{\"attr\": {\"ext_id\": \"" +
  				  extID +
  				  "\",\"id\": \"" +
  				  ID +
  				  "\",\"start\": \"" + 
  				  startStage +
  				  "\",\"end\": \"" + 
    			  endStage +
  				  "\",\"name\": \"" +
  				  name +
  				  "\"},\"data\": \"" +
  				  name +
  				  "\"},";
    	}
    }
}
