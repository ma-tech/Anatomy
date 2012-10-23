package daomodel;
/**
 * This class represents an object that represents JSON node values
 * especially for using with AJAX calls from jsTree
 */
public class JsonNode {
    
    private String ID;
    private String extID;
    private String timedStage;
    private String startStage;
    private String endStage;
    private String name;
    private int childCount;

    // Constructors -------------------------------------------------------------------------------
    /**
     * Default constructor.
     */
    public JsonNode() {
        // Always keep the default constructor alive in a Javabean class.
    }

    /**
     * Constructor for timed nodes
     */
    public JsonNode(String extID, String ID, String timedStage, String name, int childCount) {        
        this.extID = extID;
        this.ID = ID;
        this.timedStage = timedStage; 
        this.name = name;
        this.childCount = childCount;
    }
    
    /**
     * Constructor for abstract nodes
     */
    public JsonNode(String extID, String ID, String startStage, String endStage, String name, int childCount) {        
        this.extID = extID;
        this.ID = ID;
        this.startStage = startStage; 
        this.endStage = endStage;
        this.name = name;
        this.childCount = childCount;
    }
    
    //
    public String printJsonNodeTimed() {
    
    	if (childCount>0) {
    		return "{\"attr\": {\"ext_id\": \"" +
    				  extID +
    				  "\",\"id\": \"" +
    				  ID +
    				  "\",\"stage\": \"" + 
    				  timedStage +
    				  "\",\"name\": \"" +
    				  name +
    				  "\"},\"data\": \"" +
    				  name +
    				  "(" + 
    				  childCount +
    				  ")\",\"state\": \"closed\"},";
    	}
    	else {
    		return "{\"attr\": {\"ext_id\": \"" +
  				  extID +
  				  "\",\"id\": \"" +
  				  ID +
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
    				  "(" + 
    				  childCount +
    				  ")\",\"state\": \"closed\"},";
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
}
