package DAOModel;

import java.io.Serializable;

/**
 * This class represents a Data Transfer Object for the Node (Abstract - EMAPA)
 */
public class PerspectiveAmbit implements Serializable {

    // Properties ---------------------------------------------------------------------------------
	/*
	 *  ANA_PERSPECTIVE_AMBIT - A List of all the Start and Stop nodes for Each Perspective 
	 *  
     *  Columns:
     *   1. PAM_OID            - int(10) unsigned
     *   2. PAM_PERSPECTIVE_FK - varchar(25)
     *   3. PAM_NODE_FK        - int(10) unsigned
     *   4. PAM_IS_START       - tinyint(1)
     *   5. PAM_IS_STOP        - tinyint(1)
     *   6. PAM_COMMENTS       - varchar(255)
 	 */
    private Long oid; 
    private String perspectiveFK; 
    private Long nodeFK; 
    private Integer isStart;
    private Integer isStop;
    private String comments; 

    // Constructors -------------------------------------------------------------------------------
    /**
     * Default constructor.
     */
    public PerspectiveAmbit() {
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
    public PerspectiveAmbit(Long oid, 
    	    String perspectiveFK,  
    	    Long nodeFK, 
    	    Integer isStart, 
    	    Integer isStop, 
    	    String comments) {
    	
        this.oid = oid;
	    this.perspectiveFK = perspectiveFK;
	    this.nodeFK = nodeFK;
	    this.isStart = isStart;
	    this.isStop = isStop;
	    this.comments = comments;
    }

    // Getters ------------------------------------------------------------------------------------
    public Long getOid() {
        return oid;
    }
    public String getPerspectiveFK() {
        return perspectiveFK;
    }
    public Long getNodeFK() {
        return nodeFK;
    }
    public Integer getIsStart() {
        return isStart;
    }
    public Integer getIsStop() {
        return isStop;
    }
    public String getComments() {
        return comments;
    }

    // Setters ------------------------------------------------------------------------------------
    public void setOid(Long oid) {
        this.oid = oid;
    }
    public void setPerspectiveFK(String perspectiveFK) {
        this.perspectiveFK = perspectiveFK;
    }
    public void setNodeFK(Long nodeFK) {
        this.nodeFK = nodeFK;
    }
    public void setIsStart(Integer isStart) {
        this.isStart = isStart;
    }
    public void setIsStop(Integer isStop) {
        this.isStop = isStop;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }

    // Override -----------------------------------------------------------------------------------
    /**
     * The relation ID is unique for each Node. So this should compare Node by ID only.
     */
    public boolean equals(Object other) {
        return (other instanceof PerspectiveAmbit) && (oid != null) 
        		? oid.equals(((PerspectiveAmbit) other).oid) 
        		: (other == this);
    }

    /**
     * Returns the String representation of this Node. Not required, it just pleases reading logs.
     */
    public String toString() {
        return String.format("PerspectiveAmbit [ oid=%d, perspectiveFK=%s, nodeFK=%s, isStart=%d, isStop=%d, comments=%s ]", 
            oid, perspectiveFK, nodeFK, isStart, isStop, comments); 

    }

}
