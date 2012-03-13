package Model;

import java.io.Serializable;

/**
 * This class represents a Data Transfer Object for the Node (Abstract - EMAPA)
 */
public class Node implements Serializable {

    // Properties ---------------------------------------------------------------------------------
	/*
	 *  ANA_NODE - ABSTRACT Nodes in the Anatomy DAG
	 *              - EMAPA:.... 
     *  
     *  Columns:
     *   1. ANO_OID            - int(10) unsigned 
     *   2. ANO_SPECIES_FK     - varchar(20)      
     *   3. ANO_COMPONENT_NAME - varchar(255)     
     *   4. ANO_IS_PRIMARY     - tinyint(1)       
     *   5. ANO_IS_GROUP       - tinyint(1)       
     *   6. ANO_PUBLIC_ID      - varchar(20)      
     *   7. ANO_DESCRIPTION    - varchar(2000)    
	 */
    private Long oid; 
    private String speciesFK; 
    private String componentName; 
    private Integer primary;
    private Integer group;
    private String publicId; 
    private String description; 

    // Constructors -------------------------------------------------------------------------------
    /**
     * Default constructor.
     */
    public Node() {
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
    public Node(Long oid, 
    	    String speciesFK,
    	    String componentName, 
    	    Integer primary,
    	    Integer group,
    	    String publicId,
    	    String description) {
    	
        this.oid = oid;
	    this.speciesFK = speciesFK;
	    this.componentName = componentName;
	    this.primary = primary;
	    this.group = group;
	    this.publicId = publicId;
	    this.description = description;
    }

    // Getters ------------------------------------------------------------------------------------
    public Long getOid() {
        return oid;
    }
    public String getSpeciesFK() {
        return speciesFK;
    }
    public String getComponentName() {
        return componentName;
    }
    public Integer getPrimary() {
        return primary;
    }
    public Integer getGroup() {
        return group;
    }
    public String getPublicId() {
        return publicId;
    }
    public String getDescription() {
        return description;
    }

    // Setters ------------------------------------------------------------------------------------
    public void setOid(Long oid) {
        this.oid = oid;
    }
    public void getSpeciesFK(String speciesFK) {
        this.speciesFK = speciesFK;
    }
    public void getComponentName(String componentName) {
        this.componentName = componentName;
    }
    public void setPrimary(Integer primary) {
        this.primary = primary;
    }
    public void setGroup(Integer group) {
        this.group = group;
    }
    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    // Override -----------------------------------------------------------------------------------
    /**
     * The relation ID is unique for each Node. So this should compare Node by ID only.
     */
    public boolean equals(Object other) {
        return (other instanceof Node) && (oid != null) 
        		? oid.equals(((Node) other).oid) 
        		: (other == this);
    }

    /**
     * Returns the String representation of this Node. Not required, it just pleases reading logs.
     */
    public String toString() {
        return String.format("Node [ oid=%d, speciesFK=%s, componentName=%s, primary=%d, group=%d, publicId=%s, description=%s ]", 
            oid, speciesFK, componentName, primary, group, publicId, description); 

    }

}
