/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomy
*
* Title:        JOINRelationshipProjectRelationship.java
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
*                JOINRelationshipProjectRelationship "Table".
*
*                ANA_RELATIONSHIP_PROJECT & ANA_RELATIONSHIP
*                
*                Joined on 
*                 FROM ANA_RELATIONSHIP 
*                 JOIN ANA_RELATIONSHIP_PROJECT ON REL_OID = RLP_RELATIONSHIP_FK
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

public class JOINRelationshipProjectRelationship {
    // Properties ---------------------------------------------------------------------------------
	/*
     *   1. RLP_OID                  - int(10) unsigned
     *   2. RLP_RELATIONSHIP_FK      - int(10)
     *   3. RLP_PROJECT_FK           - char(30)
     *   4. RLP_SEQUENCE             - int(10)
     *   
     *   1. REL_OID                  - int(10) unsigned 
     *   2. REL_RELATIONSHIP_TYPE_FK - varchar(20)      
     *   3. REL_CHILD_FK             - int(10) unsigned 
     *   4. REL_PARENT_FK            - int(10) unsigned 
 	 */
    private Long oidRelationshipProject; 
    private long relationshipFK; 
    private String projectFK; 
    private long sequenceFK;
    private Long oidRelationship; 
    private String typeFK; 
    private long childFK; 
    private long parentFK;

    // Constructors -------------------------------------------------------------------------------
    /*
     * Default constructor.
     */
    public JOINRelationshipProjectRelationship() {
        
    }

    /*
     * Full constructor. Contains required and optional fields.
     * 
     * The Full Constructor is the Minimal Constructor
     * 
     */
    public JOINRelationshipProjectRelationship(long oidRelationshipProject, 
    		long relationshipFK, 
    		String projectFK, 
    		long sequenceFK,
    	    Long oidRelationship, 
    		String typeFK, 
    		long childFK, 
    		long parentFK) {
    	
        this.oidRelationshipProject = oidRelationshipProject;
        this.relationshipFK = relationshipFK; 
        this.projectFK = projectFK; 
        this.sequenceFK = sequenceFK;
        this.oidRelationship = oidRelationship;
        this.typeFK = typeFK; 
        this.childFK = childFK; 
        this.parentFK = parentFK;
    }

    // Getters ------------------------------------------------------------------------------------
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

    // Setters ------------------------------------------------------------------------------------
    public void setOidRelationshipProject(Long oidRelationshipProject) {
        this.oidRelationshipProject = oidRelationshipProject;
    }
    public void setRelationshipFK(long relationshipFK) {
        this.relationshipFK = relationshipFK;
    }
    public void setProjectFK(String projectFK) {
        this.projectFK = projectFK;
    }
    public void setSequenceFK(long sequenceFK) {
        this.sequenceFK = sequenceFK;
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
    public void getParentFK(long parentFK) {
        this.parentFK = parentFK;
    }

    // Override -----------------------------------------------------------------------------------
    /*
     * Is this JOINRelationshipProjectRelationship the same as the Supplied JOINRelationshipProjectRelationship?
     */
    public boolean isSameAs(JOINRelationshipProjectRelationship daojoinrelationshipprojectrelationship){

    	if ( this.getOidRelationshipProject().equals(daojoinrelationshipprojectrelationship.getOidRelationshipProject()) && 
    		this.getRelationshipFK() == daojoinrelationshipprojectrelationship.getRelationshipFK() && 
    		this.getProjectFK().equals(daojoinrelationshipprojectrelationship.getProjectFK()) && 
    		this.getSequenceFK() == daojoinrelationshipprojectrelationship.getSequenceFK() && 
    		this.getOidRelationship().equals(daojoinrelationshipprojectrelationship.getOidRelationship()) && 
    		this.getTypeFK().equals(daojoinrelationshipprojectrelationship.getTypeFK()) && 
    		this.getChildFK() == daojoinrelationshipprojectrelationship.getChildFK() && 
    		this.getParentFK() == daojoinrelationshipprojectrelationship.getParentFK() ) {

        	return true;
        }
        else {

        	return false;
        }
    }

    /*
     * The RelationshipProject OID is unique for each JOINRelationshipProjectRelationship. 
     * So this should compare JOINRelationshipProjectRelationship by RelationshipProject OID only.
     */
    public boolean equals(Object other) {
    	
        return (other instanceof JOINRelationshipProjectRelationship) && (oidRelationshipProject != null) 
        		? oidRelationshipProject.equals(((JOINRelationshipProjectRelationship) other).oidRelationshipProject) 
        		: (other == this);
    }

    /*
     * Returns the String representation of this JOINRelationshipProjectRelationship. 
     *  Not required, it just makes reading logs easier.
     */
    public String toString() {
    	
        return String.format("JOINRelationshipProjectRelationship\n" +
        	"RelationshipProject [ oidRelationshipProject=%d, relationshipFK=%d, projectFK=%s, sequenceFK=%d ]\n" +
        	"Relationship [ oidRelationship=%d, typeFK=%s, childFK=%d, parentFK=%d ]",
            oidRelationshipProject, relationshipFK, projectFK, sequenceFK, 
            oidRelationship, typeFK, childFK, parentFK); 
    }
}
