/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomy
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
*                ANA_NODE & ANA_RELATIONSHIP
*                
*                Joined on 
*                 FROM ANA_NODE 
*                 JOIN ANA_RELATIONSHIP   ON REL_CHILD_FK  = a.ANO_OID
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
     *   1. ANO_OID                  - int(10) unsigned 
     *   2. ANO_SPECIES_FK           - varchar(20)      
     *   3. ANO_COMPONENT_NAME       - varchar(255)     
     *   4. ANO_IS_PRIMARY           - tinyint(1)       
     *   5. ANO_IS_GROUP             - tinyint(1)       
     *   6. ANO_PUBLIC_ID            - varchar(20)      
     *   7. ANO_DESCRIPTION          - varchar(2000)    
     *   
     *   1. REL_OID                  - int(10) unsigned 
     *   2. REL_RELATIONSHIP_TYPE_FK - varchar(20)      
     *   3. REL_CHILD_FK             - int(10) unsigned 
     *   4. REL_PARENT_FK            - int(10) unsigned 
 	 */
    private Long oidNode; 
    private String speciesFK; 
    private String componentName; 
    private boolean primary;
    private boolean group;
    private String publicId; 
    private String description; 
    private long oidRelationship; 
    private String typeFK; 
    private long childFK; 
    private long parentFK;

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
    public JOINNodeRelationship(Long oidNode, 
    	    String speciesFK,
    	    String componentName, 
    	    boolean primary,
    	    boolean group,
    	    String publicId,
    	    String description,
    	    long oidRelationship, 
    		String typeFK, 
    		long childFK, 
    		long parentFK) {
    	
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
    public boolean isPrimary() {
        return primary;
    }
    public boolean isGroup() {
        return group;
    }
    public String getPublicId() {
        return publicId;
    }
    public String getDescription() {
        return description;
    }
    
    public long getOidRelationship() {
        return oidRelationship;
    }
    public String getTypeFK() {
        return typeFK;
    }
    public long getChildFK() {
        return childFK;
    }
    public long getParentFK() {
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
    
    public void setOidRelationship(long oidRelationship) {
        this.oidRelationship = oidRelationship;
    }
    public void SetTypeFK(String typeFK) {
        this.typeFK = typeFK;
    }
    public void setChildFK(long childFK) {
        this.childFK = childFK;
    }
    public void getParentFK(long parentFK) {
        this.parentFK = parentFK;
    }

    // Helper -------------------------------------------------------------------------------------
    /*
     * Is this JOINNodeRelationship the same as the Supplied JOINNodeRelationship?
     */
    public boolean isSameAs(JOINNodeRelationship daojoinoderelationship){

    	if ( this.getOidNode().equals(daojoinoderelationship.getOidNode()) &&
    		this.getSpeciesFK().equals(daojoinoderelationship.getSpeciesFK()) &&
    		this.getComponentName().equals(daojoinoderelationship.getComponentName()) &&
    		this.isPrimary() == daojoinoderelationship.isPrimary() &&
    		this.isGroup() == daojoinoderelationship.isGroup() &&
    		this.getPublicId().equals(daojoinoderelationship.getPublicId()) &&
    		this.getDescription().equals(daojoinoderelationship.getDescription()) &&
    		this.getOidRelationship() == daojoinoderelationship.getOidRelationship() &&
    		this.getTypeFK().equals(daojoinoderelationship.getTypeFK()) &&
    		this.getChildFK() == daojoinoderelationship.getChildFK() &&
    		this.getParentFK() == daojoinoderelationship.getParentFK() ) {

        	return true;
        }
        else {

        	return false;
        }
    }

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
            oidNode, speciesFK, componentName, primary, group, publicId, description, 
            oidRelationship, typeFK, childFK, parentFK); 
    }
}
