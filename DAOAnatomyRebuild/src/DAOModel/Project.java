package DAOModel;

import java.io.Serializable;

/**
 * This class represents a Data Transfer Object for the Node (Abstract - EMAPA)
 */
public class Project implements Serializable {

    // Properties ---------------------------------------------------------------------------------
	/*
	 *  ANA_PROJECT - A record of all the versions of the Anatomy Database
	 *              - EMAPA:.... 
     *  
     *  Columns:
     *   1. APJ_NAME - char(300
     *   
	 */
    private String name; 

    // Constructors -------------------------------------------------------------------------------
    /**
     * Default constructor.
     */
    public Project() {
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
    public Project(String name) {

	    this.name = name;
    }

    // Getters ------------------------------------------------------------------------------------
    public String getName() {
        return name;
    }

    // Setters ------------------------------------------------------------------------------------
    public void setName(String name) {
        this.name = name;
    }

    // Override -----------------------------------------------------------------------------------
    /**
     * The relation ID is unique for each Node. So this should compare Node by ID only.
     */
    public boolean equals(Object other) {
        return (other instanceof Project) && (name != null) 
        		? name.equals(((Project) other).name) 
        		: (other == this);
    }

    /**
     * Returns the String representation of this Node. Not required, it just pleases reading logs.
     */
    public String toString() {
        return String.format("Project [ name=%s ]", 
            name); 

    }

}
