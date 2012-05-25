package daomodel;

/**
 * This class represents a Data Transfer Object for the DerivedPartOfPerspectives. 
 */
public class DerivedPartOfPerspectives {

    // Properties ---------------------------------------------------------------------------------
	/*
	 *  ANA_DerivedPartOfPerspectives - All DerivedPartOfPerspectivess between ABSTRACT Nodes in the Anatomy DAG
     *  
     *  Columns:
     *   1. POP_PERSPECTIVE_FK => varchar(25)
     *   2. POP_APO_FK         => int(10)
     *   3. POP_IS_ANCESTOR    => tinyint
     *   4. POP_NODE_FK        => int(10)
	 */
    private String perspectiveFK; 
    private Long partOfFK; 
    private int ancestor;
    private Long nodeFK; 

    // Constructors -------------------------------------------------------------------------------
    /**
     * Default constructor.
     */
    public DerivedPartOfPerspectives() {
        // Always keep the default constructor alive in a Javabean class.
    }

    /**
     * Minimal constructor. Contains required fields.
     * Full constructor. Contains required and optional fields.
     *  The Full Constructor is the Minimal Constructor
     * 
     */
    public DerivedPartOfPerspectives(String perspectiveFK,
    		Long partOfFK, 
    		int ancestor,    		
    		Long nodeFK
    		) {
    	
    	this.perspectiveFK = perspectiveFK;
    	this.partOfFK = partOfFK;
    	this.ancestor = ancestor;
    	this.nodeFK = nodeFK;
    }

    // Getters ------------------------------------------------------------------------------------
    public String getPerspectiveFK() {
        return perspectiveFK;
    } 
    public Long getPartOfFK() {
        return partOfFK;
    }
    public Long getNodeFK() {
        return nodeFK;
    } 
    public int getAncestor() {
        return ancestor;
    } 

    // Setters ------------------------------------------------------------------------------------
    public void setPerspectiveFK(String perspectiveFK) {
        this.perspectiveFK = perspectiveFK;
    } 
    public void setPartOfFK(Long partOfFK) {
        this.partOfFK = partOfFK;
    } 
    public void setNodeFK(Long nodeFK) {
        this.nodeFK = nodeFK;
    } 
    public void setAncestor(int ancestor) {
        this.ancestor = ancestor;
    } 

    // Override -----------------------------------------------------------------------------------
    /**
     * Returns the String representation of this DerivedPartOfPerspectives.
     *  Not required, it just aids log reading.
     */
    public String toString() {
        return String.format("DerivedPartOfPerspectives [ perspectiveFK=%s, partOfFK=%d, nodeFK=%d, ancestor=%d ]", 
        		perspectiveFK, partOfFK, nodeFK, ancestor);

    }
}
