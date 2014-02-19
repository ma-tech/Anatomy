/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayer
*
* Title:        Node.java
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
* Description:  This class represents a SQL Database Transfer Object for the Node Table.
*                ANA_NODE
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

public class Node {
    // Properties ---------------------------------------------------------------------------------
	/*
     *   1. ANO_OID            - int(10) unsigned 
     *   2. ANO_SPECIES_FK     - varchar(20)      
     *   3. ANO_COMPONENT_NAME - varchar(255)     
     *   4. ANO_IS_PRIMARY     - tinyint(1)       
     *   5. ANO_IS_GROUP       - tinyint(1)       
     *   6. ANO_PUBLIC_ID      - varchar(20)      
     *   7. ANO_DESCRIPTION    - varchar(2000)    
     *   8. ANO_DISPLAY_ID     - varchar(20)      
	 */
    private Long oid; 
    private String speciesFK; 
    private String componentName; 
    private boolean primary;
    private boolean group;
    private String publicId; 
    private String description; 
    private String displayId; 

    // Constructors -------------------------------------------------------------------------------
    /*
     * Default constructor.
     */
    public Node() {
        
    }

    /*
     * Minimal constructor. Contains required fields.
     */
    public Node(Long oid, 
    	    String speciesFK,
    	    String componentName, 
    	    boolean primary,
    	    boolean group,
    	    String publicId,
    	    String description,
    	    String displayId) {
    	
        this.oid = oid;
	    this.speciesFK = speciesFK;
	    this.componentName = componentName;
	    this.primary = primary;
	    this.group = group;
	    this.publicId = publicId;
	    this.description = description;
	    this.displayId = displayId;
    }

    // Getters ------------------------------------------------------------------------------------
    public Long getOid() {
        return oid;
    }
    public String getSpeciesFK() {
        return speciesFK;
    }
    public String getComponentName() {
        return componentName;
    }
    public boolean isPrimary() {
        return primary;
    }
    public int getPrimary() {
        if ( primary ) {
        	return 1;
        }
        else {
        	return 0;
        }
    }
    public boolean isGroup() {
        return group;
    }
    public int getGroup() {
        if ( group ) {
        	return 1;
        }
        else {
        	return 0;
        }
    }
    public String getPublicId() {
        return publicId;
    }
    public String getDescription() {
        return description;
    }
    public String getDisplayId() {
        return displayId;
    }

    // Setters ------------------------------------------------------------------------------------
    public void setOid(Long oid) {
        this.oid = oid;
    }
    public void setSpeciesFK(String speciesFK) {
        this.speciesFK = speciesFK;
    }
    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }
    public void setPrimary(boolean primary) {
        this.primary = primary;
    }
    public void setGroup(boolean group) {
        this.group = group;
    }
    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setDisplayId(String displayId) {
        this.displayId = displayId;
    }

    // Helper -------------------------------------------------------------------------------------
    /*
     * Is this Node the same as the Supplied Node?
     */
    public boolean isSameAs(Node daonode){

    	if ( this.getSpeciesFK().equals(daonode.getSpeciesFK()) &&
    		this.getComponentName().equals(daonode.getComponentName()) &&
    		this.isPrimary() == daonode.isPrimary() &&
    		this.isGroup() == daonode.isGroup() &&
    		this.getPublicId().equals(daonode.getPublicId()) &&
    		this.getDescription().equals(daonode.getDescription()) &&
    		this.getDisplayId().equals(daonode.getDisplayId()) ) {

        	return true;
        }
        else {

        	return false;
        }
    }

    /*
     * The Node OID is unique for each Node.
     *  So this should compare Node by OID only.
     */
    public boolean equals(Object other) {
    	
        return (other instanceof Node) && (oid != null) 
        		? oid.equals(((Node) other).oid) 
        		: (other == this);
    }

    /*
     * Returns the String representation of this Node.
     *  Not required, it just makes reading logs easier.
     */
    public String toString() {
    	
        return String.format("Node [ oid=%d, speciesFK=%s, componentName=%s, primary=%b, group=%b, publicId=%s, description=%s, displayId=%s ]", 
            oid, speciesFK, componentName, primary, group, publicId, description, displayId); 
    }

    /*
     * Returns the String representation of this Node.
     *  Not required, it just makes reading logs easier.
     */
    public String toStringThing() {
    	
        return String.format("oid=%d, speciesFK=%s, componentName=%s, primary=%b, group=%b, publicId=%s, description=%s, displayId=%s", 
            oid, speciesFK, componentName, primary, group, publicId, description, displayId); 
    }
}
