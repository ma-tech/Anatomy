package DAOModel;
/**
 * This class represents a Data Transfer Object for the JOINNodeRelationshipNode (Abstract - EMAPA)
 */
public class JOINNodeRelationshipNode {

    // Properties ---------------------------------------------------------------------------------
	/*
	 *  ANA_NODE - ABSTRACT JOINNodeRelationshipNodes in the Anatomy DAG
	 *              - EMAPA:.... 
	 *  ANA_RELATIONSHIP - All Relationships between ABSTRACT Nodes in the Anatomy DAG
     *  
	 *  ANA_NODE - ABSTRACT JOINNodeRelationshipNodes in the Anatomy DAG
	 *              - EMAPA:.... 
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
     *   1. ANO_OID                  - int(10) unsigned 
     *   2. ANO_SPECIES_FK           - varchar(20)      
     *   3. ANO_COMPONENT_NAME       - varchar(255)     
     *   4. ANO_IS_PRIMARY           - tinyint(1)       
     *   5. ANO_IS_GROUP             - tinyint(1)       
     *   6. ANO_PUBLIC_ID            - varchar(20)      
     *   7. ANO_DESCRIPTION          - varchar(2000)    
 	 */
    private Long AoidNode; 
    private String AspeciesFK; 
    private String AcomponentName; 
    private Boolean Aprimary;
    private Boolean Agroup;
    private String ApublicId; 
    private String Adescription; 
    private Long oidRelationship; 
    private String typeFK; 
    private Long childFK; 
    private Long parentFK;
    private Long BoidNode; 
    private String BspeciesFK; 
    private String BcomponentName; 
    private Boolean Bprimary;
    private Boolean Bgroup;
    private String BpublicId; 
    private String Bdescription; 

    // Constructors -------------------------------------------------------------------------------
    /**
     * Default constructor.
     */
    public JOINNodeRelationshipNode() {
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
    public JOINNodeRelationshipNode(Long AoidNode, 
    	    String AspeciesFK,
    	    String AcomponentName, 
    	    Boolean Aprimary,
    	    Boolean Agroup,
    	    String ApublicId,
    	    String Adescription,
    	    Long oidRelationship, 
    		String typeFK, 
    		Long childFK, 
    		Long parentFK,
    		Long BoidNode, 
    	    String BspeciesFK,
    	    String BcomponentName, 
    	    Boolean Bprimary,
    	    Boolean Bgroup,
    	    String BpublicId,
    	    String Bdescription) {
    	
        this.AoidNode = AoidNode;
	    this.AspeciesFK = AspeciesFK;
	    this.AcomponentName = AcomponentName;
	    this.Aprimary = Aprimary;
	    this.Agroup = Agroup;
	    this.ApublicId = ApublicId;
	    this.Adescription = Adescription;
        this.oidRelationship = oidRelationship;
        this.typeFK = typeFK; 
        this.childFK = childFK; 
        this.parentFK = parentFK;
        this.BoidNode = BoidNode;
	    this.BspeciesFK = BspeciesFK;
	    this.BcomponentName = BcomponentName;
	    this.Bprimary = Bprimary;
	    this.Bgroup = Bgroup;
	    this.BpublicId = BpublicId;
	    this.Bdescription = Bdescription;
    }

    // Getters ------------------------------------------------------------------------------------
    public Long getAOidNode() {
        return AoidNode;
    }
    public String getASpeciesFK() {
        return AspeciesFK;
    }
    public String getAComponentName() {
        return AcomponentName;
    }
    public Boolean isAPrimary() {
        return Aprimary;
    }
    public Boolean isAGroup() {
        return Agroup;
    }
    public String getAPublicId() {
        return ApublicId;
    }
    public String getADescription() {
        return Adescription;
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

    public Long getBOidNode() {
        return BoidNode;
    }
    public String getBSpeciesFK() {
        return BspeciesFK;
    }
    public String getBComponentName() {
        return BcomponentName;
    }
    public Boolean isBPrimary() {
        return Bprimary;
    }
    public Boolean isBGroup() {
        return Bgroup;
    }
    public String getBPublicId() {
        return BpublicId;
    }
    public String getBDescription() {
        return Bdescription;
    }
    
    // Setters ------------------------------------------------------------------------------------
    public void setAOidNode(Long AoidNode) {
        this.AoidNode = AoidNode;
    }
    public void getASpeciesFK(String AspeciesFK) {
        this.AspeciesFK = AspeciesFK;
    }
    public void getAComponentName(String AcomponentName) {
        this.AcomponentName = AcomponentName;
    }
    public void setAPrimary(Boolean Aprimary) {
        this.Aprimary = Aprimary;
    }
    public void setAGroup(Boolean Agroup) {
        this.Agroup = Agroup;
    }
    public void setAPublicId(String ApublicId) {
        this.ApublicId = ApublicId;
    }
    public void setADescription(String Adescription) {
        this.Adescription = Adescription;
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

    public void setBOidNode(Long BoidNode) {
        this.BoidNode = BoidNode;
    }
    public void getBSpeciesFK(String BspeciesFK) {
        this.BspeciesFK = BspeciesFK;
    }
    public void getBComponentName(String BcomponentName) {
        this.BcomponentName = BcomponentName;
    }
    public void setBPrimary(Boolean Bprimary) {
        this.Bprimary = Bprimary;
    }
    public void setBGroup(Boolean Bgroup) {
        this.Bgroup = Bgroup;
    }
    public void setBPublicId(String BpublicId) {
        this.BpublicId = BpublicId;
    }
    public void setBDescription(String Bdescription) {
        this.Bdescription = Bdescription;
    }
    
    // Override -----------------------------------------------------------------------------------
    /**
     * The relation ID is unique for each JOINNodeRelationshipNode. So this should compare JOINNodeRelationshipNode by ID only.
     */
    public boolean equals(Object other) {
        return (other instanceof JOINNodeRelationshipNode) && (AoidNode != null) 
        		? AoidNode.equals(((JOINNodeRelationshipNode) other).AoidNode) 
        		: (other == this);
    }

    /**
     * Returns the String representation of this JOINNodeRelationshipNode. Not required, it just pleases reading logs.
     */
    public String toString() {
        return String.format("JOINNodeRelationshipNode\n" +
        	"A Node [ AoidNode=%d, AspeciesFK=%s, AcomponentName=%s, Aprimary=%b, Agroup=%b, ApublicId=%s, Adescription=%s ]\n" +
        	"Relationship [ oidRelationship=%d, typeFK=%s, childFK=%d, parentFK=%d ]\n" +
        	"B Node [ BoidNode=%d, BspeciesFK=%s, BcomponentName=%s, Bprimary=%b, Bgroup=%b, BpublicId=%s, Bdescription=%s ]\n" +
            AoidNode, AspeciesFK, AcomponentName, Aprimary, Agroup, ApublicId, Adescription, 
            oidRelationship, typeFK, childFK, parentFK, 
            BoidNode, BspeciesFK, BcomponentName, Bprimary, Bgroup, BpublicId, Bdescription); 

    }

}