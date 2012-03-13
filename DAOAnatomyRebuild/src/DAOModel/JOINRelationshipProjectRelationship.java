package DAOModel;
/**
 * This class represents a Data Transfer Object for the JOINRelationshipProjectRelationship (Abstract - EMAPA)
 */
public class JOINRelationshipProjectRelationship {

    // Properties ---------------------------------------------------------------------------------
	/*
	 *  ANA_NODE - ABSTRACT JOINRelationshipProjectRelationships in the Anatomy DAG
	 *              - EMAPA:.... 
	 *  ANA_RELATIONSHIP - All Relationships between ABSTRACT Nodes in the Anatomy DAG
     *  
	 *  ANA_RELATIONSHIP_PROJECT - All Relationships between ABSTRACT Nodes in the Anatomy DAG
     *  
     *  Columns:
     *   1. RLP_OID                  - int(10) unsigned
     *   2. RLP_RELATIONSHIP_FK      - int(10)
     *   3. RLP_PROJECT_FK           - char(30)
     *   4. RLP_SEQUENCE             - int(10)
     *   1. REL_OID                  - int(10) unsigned 
     *   2. REL_RELATIONSHIP_TYPE_FK - varchar(20)      
     *   3. REL_CHILD_FK             - int(10) unsigned 
     *   4. REL_PARENT_FK            - int(10) unsigned 
 	 */
    private Long oidRelationshipProject; 
    private Long relationshipFK; 
    private String projectFK; 
    private Long sequenceFK;
    private Long oidRelationship; 
    private String typeFK; 
    private Long childFK; 
    private Long parentFK;

    // Constructors -------------------------------------------------------------------------------
    /**
     * Default constructor.
     */
    public JOINRelationshipProjectRelationship() {
        // Always keep the default constructor alive in a Javabean class.
    }

    /**
     * Minimal constructor. Contains required fields.
     */

    /**
     * Full constructor. Contains required and optional fields.
     * 
     * The Full Constructor is the Minimal Constructor
     * 
     */
    public JOINRelationshipProjectRelationship(Long oidRelationshipProject, 
    		Long relationshipFK, 
    		String projectFK, 
    		Long sequenceFK,
    	    Long oidRelationship, 
    		String typeFK, 
    		Long childFK, 
    		Long parentFK) {
    	
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
    public Long getRelationshipFK() {
        return relationshipFK;
    }
    public String getProjectFK() {
        return projectFK;
    }
    public Long getSequenceFK() {
        return sequenceFK;
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
    public void setOidRelationshipProject(Long oidRelationshipProject) {
        this.oidRelationshipProject = oidRelationshipProject;
    }
    public void setRelationshipFK(Long relationshipFK) {
        this.relationshipFK = relationshipFK;
    }
    public void setProjectFK(String projectFK) {
        this.projectFK = projectFK;
    }
    public void setSequenceFK(Long sequenceFK) {
        this.sequenceFK = sequenceFK;
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
    /**
     * The relation ID is unique for each JOINRelationshipProjectRelationship. So this should compare JOINRelationshipProjectRelationship by ID only.
     */
    public boolean equals(Object other) {
        return (other instanceof JOINRelationshipProjectRelationship) && (oidRelationshipProject != null) 
        		? oidRelationshipProject.equals(((JOINRelationshipProjectRelationship) other).oidRelationshipProject) 
        		: (other == this);
    }

    /**
     * Returns the String representation of this JOINRelationshipProjectRelationship. Not required, it just pleases reading logs.
     */
    public String toString() {
        return String.format("JOINRelationshipProjectRelationship\n" +
        	"RelationshipProject [ oidRelationshipProject=%d, relationshipFK=%d, projectFK=%s, sequenceFK=%d ]" +
        	"Relationship [ oidRelationship=%d, typeFK=%s, childFK=%d, parentFK=%d ]\n",
            oidRelationshipProject, relationshipFK, projectFK, sequenceFK, oidRelationship, typeFK, childFK, parentFK); 

    }

}
