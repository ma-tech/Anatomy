package DAOModel;

import java.io.Serializable;

/**
 * This class represents a Data Transfer Object for the DerivedPartOfPerspectivesJsonFK. 
 */
public class DerivedPartOfPerspectivesJsonFK implements Serializable {

    // Properties ---------------------------------------------------------------------------------
	/*
	 *  ANA_DerivedPartOfPerspectivesJsonFK - All DerivedPartOfPerspectivesJsonFKs between ABSTRACT Nodes in the Anatomy DAG
     *  
     *  Columns:
     *   1. POP_PERSPECTIVE_FK => varchar(25)
     *   2. FULL_PATH_JSON     => varchar(3000)
	 */
    private String perspectiveFK; 
    private String fullPathJson; 

    // Constructors -------------------------------------------------------------------------------
    /**
     * Default constructor.
     */
    public DerivedPartOfPerspectivesJsonFK() {
        // Always keep the default constructor alive in a Javabean class.
    }

    /**
     * Minimal constructor. Contains required fields.
     * Full constructor. Contains required and optional fields.
     *  The Full Constructor is the Minimal Constructor
     * 
     */
    public DerivedPartOfPerspectivesJsonFK(String perspectiveFK,
    		String fullPathJson
    		) {
    	
    	this.perspectiveFK = perspectiveFK;
    	this.fullPathJson = fullPathJson;
    }

    // Getters ------------------------------------------------------------------------------------
    public String getPerspectiveFK() {
        return perspectiveFK;
    } 
    public String getFullPathJson() {
        return fullPathJson;
    }

    // Setters ------------------------------------------------------------------------------------
    public void setPerspectiveFK(String perspectiveFK) {
        this.perspectiveFK = perspectiveFK;
    } 
    public void setFullPathJson(String fullPathJson) {
        this.fullPathJson = fullPathJson;
    } 

    // Override -----------------------------------------------------------------------------------
    /**
     * Returns the String representation of this DerivedPartOfPerspectivesJsonFK.
     *  Not required, it just aids log reading.
     */
    public String toString() {
        return String.format("DerivedPartOfPerspectivesJsonFK [ perspectiveFK=%s, fullPathJson=%s ]", 
        		perspectiveFK, fullPathJson);

    }
}
