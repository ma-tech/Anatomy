package DAOModel;
/**
 * This class represents a Data Transfer Object for the RelationshipProject. 
 */
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
    /**
     * Default constructor.
     */
    public RelationshipProject() {
        // Always keep the default constructor alive in a Javabean class.
    }

    /**
     * Minimal constructor. Contains required fields.
     * Full constructor. Contains required and optional fields.
     *  The Full Constructor is the Minimal Constructor
     * 
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
    /**
     * The relation ID is unique for each RelationshipProject. 
     *  So this should compare RelationshipProject by ID only.
     */
    public boolean equals(Object other) {
        return (other instanceof RelationshipProject) && (oid != null) 
        		? oid.equals(((RelationshipProject) other).oid) 
        		: (other == this);
    }

    /**
     * Returns the String representation of this RelationshipProject.
     *  Not required, it just aids log reading.
     */
    public String toString() {
        return String.format("RelationshipProject [ oid=%d, relationshipFK=%d, projectFK=%s, sequence=%d ]", 
            oid, relationshipFK, projectFK, sequence);

    }
}
