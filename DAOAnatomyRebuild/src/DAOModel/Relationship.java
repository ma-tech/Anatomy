package DAOModel;
/**
 * This class represents a Data Transfer Object for the Relationship. 
 */
public class Relationship {

    // Properties ---------------------------------------------------------------------------------
	/*
	 *  ANA_RELATIONSHIP - All Relationships between ABSTRACT Nodes in the Anatomy DAG
     *  
     *  Columns:
     *   1. REL_OID                  - int(10) unsigned 
     *   2. REL_RELATIONSHIP_TYPE_FK - varchar(20)      
     *   3. REL_CHILD_FK             - int(10) unsigned 
     *   4. REL_PARENT_FK            - int(10) unsigned 
	 */
    private Long oid; 
    private String typeFK; 
    private Long childFK; 
    private Long parentFK;

    // Constructors -------------------------------------------------------------------------------
    /**
     * Default constructor.
     */
    public Relationship() {
        // Always keep the default constructor alive in a Javabean class.
    }

    /**
     * Minimal constructor. Contains required fields.
     * Full constructor. Contains required and optional fields.
     *  The Full Constructor is the Minimal Constructor
     * 
     */
    public Relationship(Long oid, 
    		String typeFK, 
    		Long childFK, 
    		Long parentFK) {
    	
        this.oid = oid;
        this.typeFK = typeFK; 
        this.childFK = childFK; 
        this.parentFK = parentFK;
    }

    // Getters ------------------------------------------------------------------------------------
    public Long getOid() {
        return oid;
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
    public void setOid(Long oid) {
        this.oid = oid;
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
     * The relation ID is unique for each Relationship. 
     *  So this should compare Relationship by ID only.
     */
    public boolean equals(Object other) {
        return (other instanceof Relationship) && (oid != null) 
        		? oid.equals(((Relationship) other).oid) 
        		: (other == this);
    }

    /**
     * Returns the String representation of this Relationship.
     *  Not required, it just aids log reading.
     */
    public String toString() {
        return String.format("Relationship [ oid=%d, typeFK=%s, childFK=%d, parentFK=%d ]", 
            oid, typeFK, childFK, parentFK);

    }
}
