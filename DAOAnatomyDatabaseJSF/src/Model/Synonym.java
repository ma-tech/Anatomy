package Model;

import java.io.Serializable;

/**
 * This class represents a Data Transfer Object for the Synonym (Abstract - EMAPA)
 */
public class Synonym implements Serializable{

    // Properties ---------------------------------------------------------------------------------
	/*
	 *  ANA_NODE - ABSTRACT Synonyms in the Anatomy DAG
	 *              - EMAPA:.... 
     *  
     *  Columns:
     *   1. SYN_OID         - int(10) unsigned
     *   2. SYN_OBJECT_FK   - int(10) unsigned
     *   3. SYN_SYNONYM     - varchar(100)
	 */
    private Long oid; 
    private Long thingFK; 
    private String name; 

    // Constructors -------------------------------------------------------------------------------
    /**
     * Default constructor.
     */
    public Synonym() {
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
    public Synonym(Long oid, 
    		Long thingFK,
    	    String name) {
    	
        this.oid = oid;
	    this.thingFK = thingFK;
	    this.name = name;
    }

    // Getters ------------------------------------------------------------------------------------
    public Long getOid() {
        return oid;
    }
    public Long getThingFK() {
        return thingFK;
    }
    public String getName() {
        return name;
    }

    // Setters ------------------------------------------------------------------------------------
    public void setOid(Long oid) {
        this.oid = oid;
    }
    public void setThingFK(Long thingFK) {
        this.thingFK = thingFK;
    }
    public void setName(String name) {
        this.name = name;
    }

    // Override -----------------------------------------------------------------------------------
    /**
     * The OID is unique for each Synonym.
     *  So this should compare Synonym by ID only.
     */
    public boolean equals(Object other) {
        return (other instanceof Synonym) && (oid != null) 
        		? oid.equals(((Synonym) other).oid) 
        		: (other == this);
    }

    /**
     * Returns the String representation of this Synonym. 
     *  Not required, it just helps reading logs.
     */
    public String toString() {
        return String.format("Synonym [ oid=%d, thingFK=%d, name=%s ]", 
            oid, thingFK, name); 

    }

}
