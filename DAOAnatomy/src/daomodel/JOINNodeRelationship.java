/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomy008
*
* Title:        JOINNodeRelationship.java
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
* Description:  This class represents a SQL Database Transfer Object for the 
*                JOINNodeRelationship "Table".
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

public class JOINNodeRelationship {
    // Properties ---------------------------------------------------------------------------------
	/*
	 *  ANA_NODE & ANA_RELATIONSHIP 
     *  
     *  Columns:
     *   1. ANO_OID                  - int(10) unsigned 
     *   2. ANO_SPECIES_FK           - varchar(20)      
     *   3. ANO_COMPONENT_NAME       - varchar(255)     
     *   4. ANO_IS_PRIMARY           - tinyint(1)       
     *   5. ANO_IS_GROUP             - tinyint(1)       
     *   6. ANO_PUBLIC_ID            - varchar(20)      
     *   7. ANO_DESCRIPTION          - varchar(2000)    
     *   1. REL_OID                  - int(10) unsigned 
     *   2. REL_RELATIONSHIP_TYPE_FK - varchar(20)      
     *   3. REL_CHILD_FK             - int(10) unsigned 
     *   4. REL_PARENT_FK            - int(10) unsigned 
 	 */
    private Long oidNode; 
    private String speciesFK; 
    private String componentName; 
    private Boolean primary;
    private Boolean group;
    private String publicId; 
    private String description; 
    private Long oidRelationship; 
    private String typeFK; 
    private Long childFK; 
    private Long parentFK;

    // Constructors -------------------------------------------------------------------------------
    /*
     * Default constructor.
     */
    public JOINNodeRelationship() {
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
    public JOINNodeRelationship(Long oidNode, 
    	    String speciesFK,
    	    String componentName, 
    	    Boolean primary,
    	    Boolean group,
    	    String publicId,
    	    String description,
    	    Long oidRelationship, 
    		String typeFK, 
    		Long childFK, 
    		Long parentFK) {
    	
        this.oidNode = oidNode;
	    this.speciesFK = speciesFK;
	    this.componentName = componentName;
	    this.primary = primary;
	    this.group = group;
	    this.publicId = publicId;
	    this.description = description;
        this.oidRelationship = oidRelationship;
        this.typeFK = typeFK; 
        this.childFK = childFK; 
        this.parentFK = parentFK;
    }

    // Getters ------------------------------------------------------------------------------------
    public Long getOidNode() {
        return oidNode;
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
    public Boolean isGroup() {
        return group;
    }
    public String getPublicId() {
        return publicId;
    }
    public String getDescription() {
        return description;
    }
    
    public Long getOidRelationship() {
        return oidRelationship;
    }
    public String getTypeFK() {
        return typeFK;
    }
    public Long getChildFK() {
        return childFK;
    }
    public Long getParentFK() {
        return parentFK;
    }

    // Setters ------------------------------------------------------------------------------------
    public void setOidNode(Long oidNode) {
        this.oidNode = oidNode;
    }
    public void getSpeciesFK(String speciesFK) {
        this.speciesFK = speciesFK;
    }
    public void getComponentName(String componentName) {
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
    
    public void setOidRelationship(Long oidRelationship) {
        this.oidRelationship = oidRelationship;
    }
    public void SetTypeFK(String typeFK) {
        this.typeFK = typeFK;
    }
    public void setChildFK(Long childFK) {
        this.childFK = childFK;
    }
    public void getParentFK(Long parentFK) {
        this.parentFK = parentFK;
    }

    // Override -----------------------------------------------------------------------------------
    /*
     * The Node oid is unique for each JOINNodeRelationship.
     *  So this should compare JOINNodeRelationship by Node oid only.
     */
    public boolean equals(Object other) {
        return (other instanceof JOINNodeRelationship) && (oidNode != null) 
        		? oidNode.equals(((JOINNodeRelationship) other).oidNode) 
        		: (other == this);
    }

    /*
     * Returns the String representation of this JOINNodeRelationship.
     *  Not required, it just makes reading logs easier.
     */
    public String toString() {
        return String.format("JOINNodeRelationship\n" +
        	"Node [ oidNode=%d, speciesFK=%s, componentName=%s, primary=%b, group=%b, publicId=%s, description=%s ]\n" +
        	"Relationship [ oidRelationship=%d, typeFK=%s, childFK=%d, parentFK=%d ]\n", 
            oidNode, speciesFK, componentName, primary, group, publicId, description, oidRelationship, typeFK, childFK, parentFK); 
    }
}
