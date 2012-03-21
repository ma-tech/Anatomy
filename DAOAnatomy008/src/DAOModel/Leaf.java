package DAOModel;
/**
 * This class represents a Data Transfer Object for the LEAF. 
 *  This DTO can be used thorough out all layers:
 *   1. the data layer, 
 *   2. the controller layer and 
 *   3. the view layer.
 */
public class Leaf {
    // Properties ---------------------------------------------------------------------------------
	/*
	 *  This Object is a wrapper to the output from a UNION SQL statement:
	 *  
        "SELECT" +
        "  CAST(ANAV_OID_1 as CHAR) as ROOT_OID, " +  
        "  ANAV_NAME_1 as ROOT_NAME, " +
        "  ANAV_DESC_1 as ROOT_DESC, " +
        "  a.STG_NAME AS STG_MIN_1, " +
        "  b.STG_NAME AS STG_MAX_1, " +
        "  CAST(ANAV_OID_2 as CHAR) as CHILD_OID, " + 
        "  ANAV_NAME_2 as CHILD_NAME, " +
        "  'LEAF' as CHILD_ID, " +
        "  ANAV_DESC_2 as CHILD_DESC, " +
        "  c.STG_NAME AS STG_MIN_2, " +
        "  d.STG_NAME AS STG_MAX_2, " + 
        "  'No Children' as GRAND_CHILD_ID, " + 
        "  'No Children' as GRAND_CHILD_NAME, " +
        "  'No Children' as GRAND_CHILD_DESC " +
        "FROM ANAV_LEAF_RELATION " +
        "JOIN ANA_STAGE a on a.STG_SEQUENCE = ANAV_MIN_1 " +
        "JOIN ANA_STAGE b on b.STG_SEQUENCE = ANAV_MAX_1 " +
        "JOIN ANA_STAGE c on c.STG_SEQUENCE = ANAV_MIN_2 " +
        "JOIN ANA_STAGE d on d.STG_SEQUENCE = ANAV_MAX_2 " +
        "WHERE ANAV_NAME_1 = ? " +
        "UNION " +
        "SELECT  " +
        "  ANAV_ID_1, " +
        "  ANAV_NAME_1, " +
        "  ANAV_DESC_1, " +
        "  a.STG_NAME AS STG_MIN_1, " +
        "  b.STG_NAME AS STG_MAX_1, " + 
        "  CAST(ANAV_OID_2 as CHAR), " +
        "  ANAV_NAME_2, " +
        "  ANAV_ID_2, " +
        "  ANAV_DESC_2, " +
        "  c.STG_NAME AS STG_MIN_2, " +
        "  d.STG_NAME AS STG_MAX_2, " +
        "  ANAV_ID_3, " +
        "  ANAV_NAME_3, " +
        "  ANAV_DESC_3 " +
        "FROM ANAV_GRAND_RELATION " +
        "JOIN ANA_STAGE a on a.STG_SEQUENCE = ANAV_STG_MIN_1 " +
        "JOIN ANA_STAGE b on b.STG_SEQUENCE = ANAV_STG_MAX_1 " +
        "JOIN ANA_STAGE c on c.STG_SEQUENCE = ANAV_STG_MIN_2 " +
        "JOIN ANA_STAGE d on d.STG_SEQUENCE = ANAV_STG_MAX_2 " +
        "WHERE ANAV_NAME_1 = ? " +
        "ORDER BY ROOT_DESC, CHILD_ID DESC, CHILD_DESC";
     *  
     *  Columns:
     *  
     *   1.  ROOT_OID         - Unique Id for Row
     *   2.  ROOT_NAME        - 'Root' node - EMAPA ID
     *   3.  ROOT_DESC        - What the 'Root' actually is! eg. 'mouse'
     *   4.  STG_MIN_1        - START Stage for Root Node
     *   5.  STG_MAX_1        - END Stage for Root Node
     *   6.  CHILD_OID        - Unique Id for Child
     *   7.  CHILD_ID         - Child Oid for Non-Leaf Nodes 
     *                           OR 'LEAF' for Leaf Nodes 
     *   8.  CHILD_NAME       - Child EMAPA ID for BOTH Leaf and Non-Leaf Nodes
     *   9.  CHILD_DESC       - What the Child actually is! eg. 'embryo'
     *   10. STG_MIN_2        - START Stage for Child Node
     *   11. STG_MAX_2        - END Stage for Child Node
     *   12. GRAND_CHILD_ID   - Grand-Child Oid for Non-Leaf Nodes
     *                           OR 'No Children' for Non-Leaf Nodes
     *   13. GRAND_CHILD_NAME - Grand-Child EMAPA ID for Leaf Nodes 
     *                           OR 'No Children' for Non-Leaf Nodes
     *   14. GRAND_CHILD_DESC - What the Grand-Child actually is! eg. 'compacted morula'
     *                           OR 'No Children' for Non-Leaf Nodes
     *                           
	 */
    private String rootOid;
      // Unique Id for Row
    private String rootName;
      // 'Root' node - EMAPA ID
    private String rootDescription;
      // What the 'Root' actually is! eg. 'mouse'
    private String rootStart;
      // What Stage the Root Node starts at
    private String rootEnd;
    // What Stage the Root Node ends at
    private String childOid;
      // Unique Id for Child
    private String childId;
      // Child Oid for Non-Leaf Nodes 
      //  OR 'LEAF' for Leaf Nodes 
    private String childName;
      // Child EMAPA ID for BOTH Leaf and Non-Leaf Nodes
    private String childDescription;
      // What the Child actually is! eg. 'embryo'
    private String childStart;
      // What Stage the Root Node starts at
    private String childEnd;
      // What Stage the Root Node ends at
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
    public Leaf() {
        // Always keep the default constructor alive in a Javabean class.
    }

    /**
     * Minimal constructor. Contains required fields.
     */
    public Leaf(String rootOid,
    		String rootName, 
    		String rootDescription,
    		String rootStart,
    		String rootEnd,
    		String childOid,
    		String childId,
    		String childName,
    		String childDescription, 
    		String childStart,
    		String childEnd,
    		String grandChildId,
    		String grandChildName, 
    		String grandChildDescription) {
    	
        this.rootOid = rootOid;
        this.rootName = rootName; 
        this.rootDescription = rootDescription;
        this.rootStart = rootStart;
        this.rootEnd = rootEnd;
        this.childOid = childOid;
        this.childId = childId;
        this.childName = childName; 
        this.childDescription = childDescription;
        this.childStart = childStart;
        this.childEnd = childEnd;
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
    public String getRootOid() {
        return rootOid;
    }
    public String getRootName() {
        return rootName;
    }
    public String getRootDescription() {
        return rootDescription;
    }
    public String getRootStart() {
        return rootStart;
    }
    public String getRootEnd() {
        return rootEnd;
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
    public String getChildStart() {
        return childStart;
    }
    public String getChildEnd() {
        return childEnd;
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
    public void setRootOid(String rootOid) {
        this.rootOid = rootOid;
    }
    public void setRootName(String rootName) {
        this.rootName = rootName;
    }
    public void setRootDescription(String rootDescription) {
        this.rootDescription = rootDescription;
    }
    public void setRootStart(String rootStart) {
        this.rootStart = rootStart;
    }
    public void setRootEnd(String rootEnd) {
        this.rootEnd = rootEnd;
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
    public void setChildStart(String childStart) {
        this.childStart = childStart;
    }
    public void setChildEnd(String childEnd) {
        this.childEnd = childEnd;
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
        return String.format("Leaf [ rootOid=%s, rootName=%s, rootDescription=%s, rootStart=%s, rootEnd=%s, childOid=%s, childId=%s, childName=%s, childDescription=%s, childStart=%s, childEnd=%s, grandChildId=%s, grandChildName=%s, grandChildDescription=%s ]\n", 
                                     rootOid, rootName, rootDescription, rootStart, rootEnd, childOid, childId, childName, childDescription, childStart, childEnd, grandChildId, grandChildName, grandChildDescription);

    }

}
