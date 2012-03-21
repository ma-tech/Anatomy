/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomy008
*
* Title:        RelationshipProject.java
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
* Description:  This class represents a SQL Database Transfer Object for the RelationshipProject Table.
*                ANA_RELATIONSHIP_PROJECT
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

package DAOModel;

public class RelationshipProject {
    // Properties ---------------------------------------------------------------------------------
	/*
	 *  ANA_RELATIONSHIP_PROJECT 
	 *   All RelationshipProjects between ABSTRACT Nodes in the Anatomy DAG
     *  
     *  Columns:
     *   1. RLP_OID             - int(10) unsigned 
     *   2. RLP_RELATIONSHIP_FK - int(10) unsigned      
     *   3. RLP_PROJECT_FK      - char(30)
     *   4. RLP_SEQUENCE        - int(10) unsigned 
	 */
    private Long oid; 
    private Long relationshipFK; 
    private String projectFK; 
    private Long sequence;

    // Constructors -------------------------------------------------------------------------------
    /*
     * Default constructor.
     */
    public RelationshipProject() {
        // Always keep the default constructor alive in a Javabean class.
    }

    /*
     * Minimal constructor. Contains required fields.
     * Full constructor. Contains required and optional fields.
     *  The Full Constructor is the Minimal Constructor
     */
    public RelationshipProject(Long oid, 
    		Long relationshipFK, 
    		String projectFK, 
    		Long sequence) {
    	
        this.oid = oid;
        this.relationshipFK = relationshipFK; 
        this.projectFK = projectFK; 
        this.sequence = sequence;
    }

    // Getters ------------------------------------------------------------------------------------
    public Long getOid() {
        return oid;
    }
    public Long getRelationshipFK() {
        return relationshipFK;
    }
    public String getProjectFK() {
        return projectFK;
    }
    public Long getSequence() {
        return sequence;
    }

    // Setters ------------------------------------------------------------------------------------
    public void setOid(Long oid) {
        this.oid = oid;
    }
    public void SetTypeFK(Long relationshipFK) {
        this.relationshipFK = relationshipFK;
    }
    public void setProjectFK(String projectFK) {
        this.projectFK = projectFK;
    }
    public void setSequence(Long sequence) {
        this.sequence = sequence;
    }

    // Override -----------------------------------------------------------------------------------
    /*
     * The RelationshipProject OID is unique for each RelationshipProject. 
     *  So this should compare RelationshipProject by OID only.
     */
    public boolean equals(Object other) {
        return (other instanceof RelationshipProject) && (oid != null) 
        		? oid.equals(((RelationshipProject) other).oid) 
        		: (other == this);
    }

    /*
     * Returns the String representation of this RelationshipProject.
     *  Not required, it just aids log reading.
     */
    public String toString() {
        return String.format("RelationshipProject [ oid=%d, relationshipFK=%d, projectFK=%s, sequence=%d ]", 
            oid, relationshipFK, projectFK, sequence);
    }
}
