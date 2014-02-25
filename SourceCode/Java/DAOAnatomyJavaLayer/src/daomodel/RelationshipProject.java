/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayer
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
* Version:      1
*
* Description:  This class represents a SQL Database Transfer Object for the RelationshipProject Table.
*                ANA_RELATIONSHIP_PROJECT
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

public class RelationshipProject {
    // Properties ---------------------------------------------------------------------------------
	/*
     *   1. RLP_OID             - int(10) unsigned 
     *   2. RLP_RELATIONSHIP_FK - int(10) unsigned      
     *   3. RLP_PROJECT_FK      - char(30)
     *   4. RLP_SEQUENCE        - int(10) unsigned 
	 */
    private Long oid; 
    private long relationshipFK; 
    private String projectFK; 
    private long sequence;

    // Constructors -------------------------------------------------------------------------------
    /*
     * Default constructor.
     */
    public RelationshipProject() {
        
    }

    /*
     * Minimal constructor. Contains required fields.
     * Full constructor. Contains required and optional fields.
     *  The Full Constructor is the Minimal Constructor
     */
    public RelationshipProject(Long oid, 
    		long relationshipFK, 
    		String projectFK, 
    		long sequence) {
    	
        this.oid = oid;
        this.relationshipFK = relationshipFK; 
        this.projectFK = projectFK; 
        this.sequence = sequence;
    }

    /*
     * Minimal constructor. Contains required fields.
     * Full constructor. Contains required and optional fields.
     *  The Full Constructor is the Minimal Constructor
     */
    public RelationshipProject(Long oid, 
    		String relationshipFK, 
    		String projectFK, 
    		String sequence) {
    	
        this.oid = oid;
        this.relationshipFK = ObjectConverter.convert(relationshipFK, Long.class);
        this.projectFK = projectFK; 
        this.sequence = ObjectConverter.convert(sequence, Long.class);
    }

    // Getters ------------------------------------------------------------------------------------
    public Long getOid() {
        return oid;
    }
    public long getRelationshipFK() {
        return relationshipFK;
    }
    public String getRelationshipFKAsString() {
        return ObjectConverter.convert(relationshipFK, String.class);
    }
    public String getProjectFK() {
        return projectFK;
    }
    public long getSequence() {
        return sequence;
    }
    public String getSequenceAsString() {
        return ObjectConverter.convert(sequence, String.class);
    }

    // Setters ------------------------------------------------------------------------------------
    public void setOid(Long oid) {
        this.oid = oid;
    }
    public void SetTypeFK(long relationshipFK) {
        this.relationshipFK = relationshipFK;
    }
    public void SetTypeFK(String relationshipFK) {
        this.relationshipFK = ObjectConverter.convert(relationshipFK, Long.class);
    }
    public void setProjectFK(String projectFK) {
        this.projectFK = projectFK;
    }
    public void setSequence(long sequence) {
        this.sequence = sequence;
    }
    public void setSequence(String sequence) {
        this.sequence = ObjectConverter.convert(sequence, Long.class);
    }

    // Override -----------------------------------------------------------------------------------
    /*
     * Is this RelationshipProject the same as the Supplied RelationshipProject?
     */
    public boolean isSameAs(RelationshipProject daorelationshipproject){

    	if (this.getRelationshipFK() == daorelationshipproject.getRelationshipFK() &&
    		this.getProjectFK().equals(daorelationshipproject.getProjectFK()) &&
    		this.getSequence() == daorelationshipproject.getSequence() ) {

        	return true;
        }
        else {

        	return false;
        }
    }

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
