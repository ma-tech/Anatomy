/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayer
*
* Title:        JOINNodeRelationshipRelationshipProject.java
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
* Description:  This class represents a SQL Database Transfer Object for the 
*                JOINNodeRelationshipRelationshipProject "Table".
*
*                ANA_NODE & ANA_RELATIONSHIP & ANA_RELATIONSHIP_PROJECT
*                
*                Joined on 
*                 FROM ANA_RELATIONSHIP
*                 JOIN ANA_NODE                  ON REL_PARENT_FK = ANO_OID 
*                 JOIN ANA_RELATIONSHIP_PROJECT  ON REL_OID       = RLP_RELATIONSHIP_FK 
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

public class JOINNodeRelationshipRelationshipProject {
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
     *    
     *   1. RLP_OID                  - int(10) unsigned
     *   2. RLP_RELATIONSHIP_FK      - int(10)
     *   3. RLP_PROJECT_FK           - char(30)
     *   4. RLP_SEQUENCE             - int(10)
 	 */
    private Long oidNode; 
    private String speciesFK; 
    private String componentName; 
    private boolean primary;
    private boolean group;
    private String publicId; 
    private String description; 
    
    private Long oidRelationship; 
    private String typeFK; 
    private long childFK; 
    private long parentFK;
    
    private Long oidRelationshipProject; 
    private long relationshipFK; 
    private String projectFK; 
    private long sequenceFK;

    // Constructors -------------------------------------------------------------------------------
    /*
     * Default constructor.
     */
    public JOINNodeRelationshipRelationshipProject() {
        
    }

    /*
     * Minimal constructor. Contains required fields.
     */
    public JOINNodeRelationshipRelationshipProject(Long oidNode, 
    	    String speciesFK,
    	    String componentName, 
    	    boolean primary,
    	    boolean group,
    	    String publicId,
    	    String description,
    	    Long oidRelationship, 
    		String typeFK, 
    		long childFK, 
    		long parentFK, 
    		Long oidRelationshipProject, 
    		long relationshipFK, 
    		String projectFK, 
    		long sequenceFK) {
    	
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
        this.oidRelationshipProject = oidRelationshipProject;
        this.relationshipFK = relationshipFK; 
        this.projectFK = projectFK; 
        this.sequenceFK = sequenceFK;
    }

    /*
     * Minimal constructor. Contains required fields.
     */
    public JOINNodeRelationshipRelationshipProject(Long oidNode, 
    	    String speciesFK,
    	    String componentName, 
    	    String primary,
    	    String group,
    	    String publicId,
    	    String description,
    	    Long oidRelationship, 
    		String typeFK, 
    		String childFK, 
    		String parentFK, 
    		Long oidRelationshipProject, 
    		String relationshipFK, 
    		String projectFK, 
    		String sequenceFK) {
    	
        this.oidNode = oidNode;
	    this.speciesFK = speciesFK;
	    this.componentName = componentName;
	    this.primary = ObjectConverter.convert(primary, Boolean.class);
	    this.group = ObjectConverter.convert(group, Boolean.class);
	    this.publicId = publicId;
	    this.description = description;
        this.oidRelationship = oidRelationship;
        this.typeFK = typeFK; 
        this.childFK = ObjectConverter.convert(childFK, Long.class);
        this.parentFK = ObjectConverter.convert(parentFK, Long.class);
        this.oidRelationshipProject = oidRelationshipProject;
        this.relationshipFK = ObjectConverter.convert(relationshipFK, Long.class);
        this.projectFK = projectFK; 
        this.sequenceFK = ObjectConverter.convert(sequenceFK, Long.class);
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
    
    public Long getOidRelationship() {
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

    public Long getOidRelationshipProject() {
        return oidRelationshipProject;
    }
    public long getRelationshipFK() {
        return relationshipFK;
    }
    public String getProjectFK() {
        return projectFK;
    }
    public long getSequenceFK() {
        return sequenceFK;
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
    public void setPrimary(String primary) {
        this.primary = ObjectConverter.convert(primary, Boolean.class);
    }
    public void setGroup(boolean group) {
        this.group = group;
    }
    public void setGroup(String group) {
        this.group = ObjectConverter.convert(group, Boolean.class);
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
    public void setChildFK(long childFK) {
        this.childFK = childFK;
    }
    public void setChildFK(String childFK) {
        this.childFK = ObjectConverter.convert(childFK, Long.class);
    }
    public void getParentFK(long parentFK) {
        this.parentFK = parentFK;
    }
    public void getParentFK(String parentFK) {
        this.parentFK = ObjectConverter.convert(parentFK, Long.class);
    }

    public void setOidRelationshipProject(Long oidRelationshipProject) {
        this.oidRelationshipProject = oidRelationshipProject;
    }
    public void setRelationshipFK(long relationshipFK) {
        this.relationshipFK = relationshipFK;
    }
    public void setRelationshipFK(String relationshipFK) {
        this.relationshipFK = ObjectConverter.convert(relationshipFK, Long.class);
    }
    public void setProjectFK(String projectFK) {
        this.projectFK = projectFK;
    }
    public void setSequenceFK(long sequenceFK) {
        this.sequenceFK = sequenceFK;
    }
    public void setSequenceFK(String sequenceFK) {
        this.sequenceFK = ObjectConverter.convert(sequenceFK, Long.class);
    }

    // Helper -------------------------------------------------------------------------------------
    /*
     * Is this JOINNodeRelationshipRelationshipProject the same as the Supplied JOINNodeRelationshipRelationshipProject?
     */
    public boolean isSameAs(JOINNodeRelationshipRelationshipProject daojoinnoderelationshiprelationshipproject){

    	if (this.getOidNode().equals(daojoinnoderelationshiprelationshipproject.getOidNode()) && 
    		this.getSpeciesFK().equals(daojoinnoderelationshiprelationshipproject.getSpeciesFK()) && 
    		this.getComponentName().equals(daojoinnoderelationshiprelationshipproject.getComponentName()) && 
    		this.isPrimary() == daojoinnoderelationshiprelationshipproject.isPrimary() && 
    		this.isGroup() == daojoinnoderelationshiprelationshipproject.isGroup() && 
    		this.getPublicId().equals(daojoinnoderelationshiprelationshipproject.getPublicId()) && 
    		this.getDescription().equals(daojoinnoderelationshiprelationshipproject.getDescription()) && 
    		this.getOidRelationship().equals(daojoinnoderelationshiprelationshipproject.getOidRelationship()) && 
    		this.getTypeFK().equals(daojoinnoderelationshiprelationshipproject.getTypeFK()) && 
    		this.getChildFK() == daojoinnoderelationshiprelationshipproject.getChildFK() && 
    		this.getParentFK() == daojoinnoderelationshiprelationshipproject.getParentFK() && 
    		this.getOidRelationshipProject().equals(daojoinnoderelationshiprelationshipproject.getOidRelationshipProject()) && 
    		this.getRelationshipFK() == daojoinnoderelationshiprelationshipproject.getRelationshipFK() && 
    		this.getProjectFK().equals(daojoinnoderelationshiprelationshipproject.getProjectFK()) && 
    		this.getSequenceFK() == daojoinnoderelationshiprelationshipproject.getSequenceFK() ) {

        	return true;
        }
        else {

        	return false;
        }
    }

    /*
     * The Node OID is unique for each JOINNodeRelationshipRelationshipProject.
     *  So this should compare JOINNodeRelationshipRelationshipProject by Node OID only.
     */
    public boolean equals(Object other) {
    	
        return (other instanceof JOINNodeRelationshipRelationshipProject) && (oidNode != null) 
        		? oidNode.equals(((JOINNodeRelationshipRelationshipProject) other).oidNode) 
        		: (other == this);
    }

    /*
     * Returns the String representation of this JOINNodeRelationshipRelationshipProject.
     *  Not required, it just makes reading logs easier.
     */
    public String toString() {
    	
        return String.format("JOINNodeRelationshipRelationshipProject\n" +
        	"Node [ oidNode=%d, speciesFK=%s, componentName=%s, primary=%b, group=%b, publicId=%s, description=%s ]\n" +
        	"Relationship [ oidRelationship=%d, typeFK=%s, childFK=%d, parentFK=%d ]\n" +
        	"RelationshipProject [ oidRelationshipProject=%d, relationshipFK=%d, projectFK=%s, sequenceFK=%d ]", 
            oidNode, speciesFK, componentName, primary, group, publicId, description, 
            oidRelationship, typeFK, childFK, parentFK, 
            oidRelationshipProject, relationshipFK, projectFK, sequenceFK); 
    }
}
