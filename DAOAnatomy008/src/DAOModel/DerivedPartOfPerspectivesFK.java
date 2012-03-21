package DAOModel;

import java.io.Serializable;

/**
 * This class represents a Data Transfer Object for the DerivedPartOfPerspectivesFK. 
 */
public class DerivedPartOfPerspectivesFK implements Serializable {

    // Properties ---------------------------------------------------------------------------------
	/*
	 *  ANA_DerivedPartOfPerspectivesFK - All DerivedPartOfPerspectivesFKs between ABSTRACT Nodes in the Anatomy DAG
     *  
     *  Columns:
     *   1. POP_PERSPECTIVE_FK => varchar(25)
     *   2. FULL_PATH          => varchar(1000)
     *   2. FULL_PATH_JSON     => varchar(3000)
     *   3. POP_IS_ANCESTOR    => tinyint
     *   4. EMAPA_PUBLIC_ID    => varchar(25)
     *   5. EMAP_PUBLIC_ID     => varchar(25)
	 */
    private String perspectiveFK; 
    private String fullPath; 
    private String fullPathJson; 
    private int ancestor;
    private String nodeEmapa; 
    private String nodeEmap; 

    // Constructors -------------------------------------------------------------------------------
    /**
     * Default constructor.
     */
    public DerivedPartOfPerspectivesFK() {
        // Always keep the default constructor alive in a Javabean class.
    }

    /**
     * Minimal constructor. Contains required fields.
     * Full constructor. Contains required and optional fields.
     *  The Full Constructor is the Minimal Constructor
     * 
     */
    public DerivedPartOfPerspectivesFK(String perspectiveFK,
    		String fullPath, 
    		String fullPathJson, 
    		int ancestor,    		
    		String nodeEmapa,
    		String nodeEmap
    		) {
    	
    	this.perspectiveFK = perspectiveFK;
    	this.fullPath = fullPath;
    	this.fullPathJson = fullPathJson;
    	this.ancestor = ancestor;
    	this.nodeEmapa = nodeEmapa;
    	this.nodeEmap = nodeEmap;
    }

    // Getters ------------------------------------------------------------------------------------
    public String getPerspectiveFK() {
        return perspectiveFK;
    } 
    public String getFullPath() {
        return fullPath;
    }
    public String getFullPathJson() {
        return fullPathJson;
    }
    public String getNodeEmapa() {
        return nodeEmapa;
    } 
    public String getNodeEmap() {
        return nodeEmap;
    } 
    public int getAncestor() {
        return ancestor;
    } 

    // Setters ------------------------------------------------------------------------------------
    public void setPerspectiveFK(String perspectiveFK) {
        this.perspectiveFK = perspectiveFK;
    } 
    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    } 
    public void setFullPathJson(String fullPathJson) {
        this.fullPathJson = fullPathJson;
    } 
    public void setNodeEmapa(String nodeEmapa) {
        this.nodeEmapa = nodeEmapa;
    } 
    public void setNodeEmap(String nodeEmap) {
        this.nodeEmap = nodeEmap;
    } 
    public void setAncestor(int ancestor) {
        this.ancestor = ancestor;
    } 

    // Override -----------------------------------------------------------------------------------
    /**
     * Returns the String representation of this DerivedPartOfPerspectivesFK.
     *  Not required, it just aids log reading.
     */
    public String toString() {
        return String.format("DerivedPartOfPerspectivesFK [ perspectiveFK=%s, fullPath=%s, fullPathJson=%s, nodeEmapa=%s, nodeEmap=%s, ancestor=%d ]", 
        		perspectiveFK, fullPath, fullPathJson, nodeEmapa, nodeEmap, ancestor);

    }
}
