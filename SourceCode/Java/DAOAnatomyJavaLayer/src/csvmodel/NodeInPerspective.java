/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayer
*
* Title:        NodeInPerspective.java
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
* Description:  This class represents a CSV for a file the NodeInPerspective records.
*
* Link:         
* 
* Maintenance:  Log changes below, with most recent at top of list.
*
* Who; When; What;
*
* Mike Wicks; 8th April 2014; Create Class
*
*----------------------------------------------------------------------------------------------
*/
package csvmodel;

public class NodeInPerspective {
    // Properties ---------------------------------------------------------------------------------
	/*
     *   1. PSP_NAME           - varchar(25)
     *   2. ANO_PUBLIC_ID      - varchar(20)      
     *   3. ANO_COMPONENT_NAME - varchar(255)     
	 */
    private String perspectiveName; 
    private String publicId; 
    private String componentName; 

    // Constructors -------------------------------------------------------------------------------
    /*
     * Default constructor.
     */
    public NodeInPerspective() {
        
    }

    /*
     * Minimal constructor. Contains required fields.
     */
    public NodeInPerspective(String perspectiveName,
    	    String publicId,
    	    String componentName) {
    	
	    this.perspectiveName = perspectiveName;
	    this.publicId = publicId;
	    this.componentName = componentName;
    }

    // Getters ------------------------------------------------------------------------------------
    public String getPerspectiveName() {
        return perspectiveName;
    }
    public String getPublicId() {
        return publicId;
    }
    public String getComponentName() {
        return componentName;
    }

    // Setters ------------------------------------------------------------------------------------
    public void setPerspectiveName(String perspectiveName) {
        this.perspectiveName = perspectiveName;
    }
    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }
    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    // Helper -------------------------------------------------------------------------------------
    /*
     * Is this NodeInPerspective the same as the Supplied NodeInPerspective?
     */
    public boolean isSameAs(NodeInPerspective daonode){

    	if ( this.getPerspectiveName().equals(daonode.getPerspectiveName()) &&
    		this.getComponentName().equals(daonode.getComponentName()) &&
    		this.getPublicId().equals(daonode.getPublicId()) ) {

        	return true;
        }
        else {

        	return false;
        }
    }

    /*
     * The NodeInPerspective OID is unique for each NodeInPerspective.
     *  So this should compare NodeInPerspective by OID only.
     */
    public boolean equals(Object other) {
    	
        return (other instanceof NodeInPerspective) && (publicId != null) 
        		? publicId.equals(((NodeInPerspective) other).publicId) 
        		: (other == this);
    }

    /*
     * Returns the String representation of this NodeInPerspective.
     *  Not required, it just makes reading logs easier.
     */
    public String toString() {
    	
        return String.format("NodeInPerspective [ perspectiveName=%s, publicId=%s, componentName=%s ]", 
            perspectiveName, publicId, componentName); 
    }

    /*
     * Returns the String representation of this NodeInPerspective.
     *  Not required, it just makes reading logs easier.
     */
    public String toStringThing() {
    	
        return String.format("perspectiveName=%s, publicId=%s, componentName=%s", 
                perspectiveName, publicId, componentName); 
    }
}
