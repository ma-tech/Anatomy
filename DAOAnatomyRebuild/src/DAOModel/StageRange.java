package DAOModel;
/**
 * This class represents a Data Transfer Object for the StageRange. 
 */
public class StageRange {

    // Properties ---------------------------------------------------------------------------------
	/*
	 * CHILD_ID     - varchar(25)
	 * CHILD_START  - varchar(25)
	 * CHILD_END    - varchar(25)
	 * PARENT_ID    - varchar(25)
	 * PARENT_START - varchar(25)
	 * PARENT_END   - varchar(25)
	 */
    private String childId; 
    private String childStart; 
    private String childEnd; 
    private String parentId; 
    private String parentStart; 
    private String parentEnd; 

    // Constructors -------------------------------------------------------------------------------
    /**
     * Default constructor.
     */
    public StageRange() {
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
    public StageRange(String childId, 
    		String childStart, 
    		String childEnd, 
    		String parentId, 
    		String parentStart, 
    		String parentEnd) {
    	
        this.childId = childId;
        this.childStart = childStart; 
        this.childEnd = childEnd; 
        this.parentId = parentId;
        this.parentStart = parentStart;
        this.parentEnd = parentEnd; 
    }

    // Getters ------------------------------------------------------------------------------------
    public String getChildId() {
        return childId;
    }
    public String getChildStart() {
        return childStart;
    }
    public String getChildEnd() {
        return childEnd;
    }
    public String getParentId() {
        return parentId;
    }
    public String getParentStart() {
        return parentStart;
    }
    public String getParentEnd() {
        return parentEnd;
    }

    // Setters ------------------------------------------------------------------------------------
    public void SetChildId(String childId) {
        this.childId = childId;
    }
    public void SetChildStart(String childStart) {
        this.childStart = childStart;
    }
    public void SetChildEnd(String childEnd) {
        this.childEnd = childEnd;
    }
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
    public void setParentStart(String parentStart) {
        this.parentStart = parentStart;
    }
    public void setParentEnd(String parentEnd) {
        this.parentEnd = parentEnd;
    }

    // Override -----------------------------------------------------------------------------------
    /**
     * Returns the String representation of this StageRange. Not required, it just pleases reading logs.
     */
    public String toString() {
        return String.format("StageRange [ childId=%s, childStart=%s, childEnd=%s, parentId=%s, parentStart=%s, parentEnd=%s ]", 
        		childId, childStart, childEnd, parentId, parentStart, parentEnd);

    }

    public String reportStageRange() {
        return String.format("\t%s\t%s\t\t%s\t\t%s\t%s\t\t%s", 
        		childId, childStart, childEnd, parentId, parentStart, parentEnd);

    }
}