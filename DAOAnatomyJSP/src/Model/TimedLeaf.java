package Model;
/**
 * This class represents a Data Transfer Object for the LEAF. 
 *  This DTO can be used thorough out all layers:
 *   1. the data layer, 
 *   2. the controller layer and 
 *   3. the view layer.
 */
public class TimedLeaf {
    // Properties ---------------------------------------------------------------------------------
	/*
	 *  This Object is a wrapper to the output from a UNION SQL statement:
	 *  
        SELECT 
          ANAV_STAGE AS STAGE,
          CAST(ANAV_OID_1 AS CHAR) AS ROOT_OID, 
          ANAV_NAME_1 AS ROOT_NAME, 
          ANAV_DESC_1 AS ROOT_DESC, 
          CAST(ANAV_OID_2 AS CHAR) AS CHILD_OID, 
          'LEAF' AS CHILD_ID, 
          ANAV_NAME_2 AS CHILD_NAME, 
          ANAV_DESC_2 AS CHILD_DESC, 
          'No Children' AS GRAND_CHILD_ID, 
          'No Children' AS GRAND_CHILD_NAME, 
          'No Children' AS GRAND_CHILD_DESC
         FROM ANAV_TIMED_LEAF_RELATION
         WHERE ANAV_NAME_1 = 'EMAP:25766'
        UNION
        SELECT 
          STG_NAME AS STAGE,
          ANAV_ID_1, 
          ANAV_NAME_1, 
          ANAV_DESC_1, 
          CAST(ANAV_OID_2 AS CHAR), 
          ANAV_ID_2, 
          ANAV_NAME_2, 
          ANAV_DESC_2, 
          ANAV_ID_3, 
          ANAV_NAME_3, 
          ANAV_DESC_3
         FROM ANAV_TIMED_GRAND_RELATION
         JOIN ANA_STAGE ON STG_OID = ANAV_STAGE
         WHERE ANAV_NAME_1 = 'EMAP:25766'
     *  
     *  Columns:
     *  
     *   1.  STAGE            - Stage Name
     *   2.  ROOT_OID         - Unique Id for Row
     *   3.  ROOT_NAME        - 'Root' node - EMAP ID
     *   4.  ROOT_DESC        - What the 'Root' actually is! eg. 'mouse'
     *   5.  CHILD_OID        - Unique Id for Child
     *   6.  CHILD_ID         - Child Oid for Non-Leaf Nodes 
     *                           OR 'LEAF' for Leaf Nodes 
     *   7.  CHILD_NAME       - Child EMAP ID for BOTH Leaf and Non-Leaf Nodes
     *   8.  CHILD_DESC       - What the Child actually is! eg. 'embryo'
     *   9.  GRAND_CHILD_ID   - Grand-Child Oid for Non-Leaf Nodes
     *                           OR 'No Children' for Non-Leaf Nodes
     *   10. GRAND_CHILD_NAME - Grand-Child EMAP ID for Leaf Nodes 
     *                           OR 'No Children' for Non-Leaf Nodes
     *   11. GRAND_CHILD_DESC - What the Grand-Child actually is! eg. 'compacted morula'
     *                           OR 'No Children' for Non-Leaf Nodes
     *                           
	 */
    private String stage;
      // Stage Name 
    private String rootOid;
      // Unique Id for Row
    private String rootName;
      // 'Root' node - EMAPA ID
    private String rootDescription;
      // What the 'Root' actually is! eg. 'mouse'
    private String childOid;
      // Unique Id for Child
    private String childId;
      // Child Oid for Non-Leaf Nodes 
      //  OR 'LEAF' for Leaf Nodes 
    private String childName;
      // Child EMAP ID for BOTH Leaf and Non-Leaf Nodes
    private String childDescription;
      // What the Child actually is! eg. 'embryo'
    private String grandChildId;
      // Grand-Child Oid for Non-Leaf Nodes
      //  OR 'No Children' for Non-Leaf Nodes
    private String grandChildName;
      // Grand-Child EMAPA ID for Leaf Nodes 
      //  OR 'No Children' for Non-Leaf Nodes
    private String grandChildDescription;
      // What the Grand-Child actually is! eg. 'compacted morula'
      //  OR 'No Children' for Non-Leaf Nodes

    // Constructors -------------------------------------------------------------------------------
    /**
     * Default constructor.
     */
    public TimedLeaf() {
        // Always keep the default constructor alive in a Javabean class.
    }

    /**
     * Minimal constructor. Contains required fields.
     */
    public TimedLeaf(
    		String stage,
    		String rootOid,
    		String rootName, 
    		String rootDescription,
    		String childOid,
    		String childId,
    		String childName,
    		String childDescription, 
    		String grandChildId,
    		String grandChildName, 
    		String grandChildDescription) {
    	
        this.stage = stage;
        this.rootOid = rootOid;
        this.rootName = rootName; 
        this.rootDescription = rootDescription;
        this.childOid = childOid;
        this.childId = childId;
        this.childName = childName; 
        this.childDescription = childDescription;
        this.grandChildId = grandChildId;
        this.grandChildName = grandChildName;
        this.grandChildDescription = grandChildDescription;
    }

    /**
     * Full constructor. Contains required and optional fields.
     * 
     * The Full Constructor is the Minimal Constructor
     * 
     */

    // Getters ------------------------------------------------------------------------------------
    public String getStage() {
        return stage;
    }
    public String getRootOid() {
        return rootOid;
    }
    public String getRootName() {
        return rootName;
    }
    public String getRootDescription() {
        return rootDescription;
    }
    public String getChildOid() {
        return childOid;
    }
    public String getChildId() {
        return childId;
    }
    public String getChildName() {
        return childName;
    }
    public String getChildDescription() {
        return childDescription;
    }
    public String getGrandChildId() {
        return grandChildId;
    }
    public String getGrandChildName() {
        return grandChildName;
    }
    public String getGrandChildDescription() {
        return grandChildDescription;
    }

    // Setters ------------------------------------------------------------------------------------
    public void setStage(String stage) {
        this.stage = stage;
    }
    public void setRootOid(String rootOid) {
        this.rootOid = rootOid;
    }
    public void setRootName(String rootName) {
        this.rootName = rootName;
    }
    public void setRootDescription(String rootDescription) {
        this.rootDescription = rootDescription;
    }
    public void setChildOid(String childOid) {
        this.childOid = childOid;
    }
    public void setChildId(String childId) {
        this.childId = childId;
    }
    public void setChildName(String childName) {
        this.childName = childName;
    }
    public void setChildDescription(String childDescription) {
        this.childDescription = childDescription;
    }
    public void setGrandChildId(String grandChildId) {
        this.grandChildId = grandChildId;
    }
    public void setGrandChildName(String grandChildName) {
        this.grandChildName = grandChildName;
    }
    public void setGrandChildDescription(String grandChildDescription) {
        this.grandChildDescription = grandChildDescription;
    }

    // Override -----------------------------------------------------------------------------------
    /**
     * Returns the String representation of this User. Not required, it just pleases reading logs.
     */
    public String toString() {
        return String.format("Leaf [ stage=%s, rootOid=%s, rootName=%s, rootDescription=%s, childOid=%s, childId=%s, childName=%s, childDescription=%s, grandChildId=%s, grandChildName=%s, grandChildDescription=%s ]\n", 
                                     stage, rootOid, rootName, rootDescription, childOid, childId, childName, childDescription, grandChildId, grandChildName, grandChildDescription);

    }

}
