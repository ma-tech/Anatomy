/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomy008
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
* Version: 1
*
* Description:  This class represents a SQL Database Transfer Object for the Node Table.
*                ANA_NODE
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

public class Node {
    // Properties ---------------------------------------------------------------------------------
	/*
     *  Columns:
     *   1. ANO_OID            - int(10) unsigned 
     *   2. ANO_SPECIES_FK     - varchar(20)      
     *   3. ANO_COMPONENT_NAME - varchar(255)     
     *   4. ANO_IS_PRIMARY     - tinyint(1)       
     *   5. ANO_IS_GROUP       - tinyint(1)       
     *   6. ANO_PUBLIC_ID      - varchar(20)      
     *   7. ANO_DESCRIPTION    - varchar(2000)    
	 */
    private Long oid; 
    private String speciesFK; 
    private String componentName; 
    private Boolean primary;
    private Boolean group;
    private String publicId; 
    private String description; 

    // Constructors -------------------------------------------------------------------------------
    /*
     * Default constructor.
     */
    public Node() {
        // Always keep the default constructor alive in a Javabean class.
    }

    /*
     * Minimal constructor. Contains required fields.
     */

    /*
     * Full constructor. Contains required and optional fields.
     * 
     * The Full Constructor is the Minimal Constructor
     * 
     */
    public Node(Long oid, 
    	    String speciesFK,
    	    String componentName, 
    	    Boolean primary,
    	    Boolean group,
    	    String publicId,
    	    String description) {
    	
        this.oid = oid;
	    this.speciesFK = speciesFK;
	    this.componentName = componentName;
	    this.primary = primary;
	    this.group = group;
	    this.publicId = publicId;
	    this.description = description;
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
    public Boolean isPrimary() {
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
    public Boolean isGroup() {
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
    public void setPrimary(Boolean primary) {
        this.primary = primary;
    }
    public void setGroup(Boolean group) {
        this.group = group;
    }
    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    // Override -----------------------------------------------------------------------------------
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
        return String.format("Node [ oid=%d, speciesFK=%s, componentName=%s, primary=%b, group=%b, publicId=%s, description=%s ]", 
            oid, speciesFK, componentName, primary, group, publicId, description); 
    }
}
