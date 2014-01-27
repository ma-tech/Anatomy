/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomy
*
* Title:        JOINNodeRelationshipNode.java
*
* Date:         2012
*
* Author:       Mike Wicks
*
* Copyright:    2012
*               Medical Research Council, UK.
*               All rights reserved.
*
* Address:      MRC Human Genetics Unit,
*               Western General Hospital,
*               Edinburgh, EH4 2XU, UK.
*
* Version:      1
*
* Description:  This class represents a SQL Database Transfer Object for the 
*                JOINNodeRelationshipNode "Table".
*
*                ANA_NODE & ANA_RELATIONSHIP & ANA_NODE
*                
*                Joined on 
*                 FROM ANA_NODE a 
*                 JOIN ANA_RELATIONSHIP   ON REL_CHILD_FK  = a.ANO_OID
*                 JOIN ANA_NODE         b ON REL_PARENT_FK = b.ANO_OID  
*
* Link:         
* 
* Maintenance:  Log changes below, with most recent at top of list.
*
* Who; When; What;
*
* Mike Wicks; 21st March 2012; Create Class
*
*----------------------------------------------------------------------------------------------
*/
package daomodel;

public class JOINNodeRelationshipNode {
    // Properties ---------------------------------------------------------------------------------
	/*
     *   1. ANO_OID                  - int(10) unsigned 
     *   2. ANO_SPECIES_FK           - varchar(20)      
     *   3. ANO_COMPONENT_NAME       - varchar(255)     
     *   4. ANO_IS_PRIMARY           - tinyint(1)       
     *   5. ANO_IS_GROUP             - tinyint(1)       
     *   6. ANO_PUBLIC_ID            - varchar(20)      
     *   7. ANO_DESCRIPTION          - varchar(2000)    
     *   
     *   1. REL_OID                  - int(10) unsigned 
     *   2. REL_RELATIONSHIP_TYPE_FK - varchar(20)      
     *   3. REL_CHILD_FK             - int(10) unsigned 
     *   4. REL_PARENT_FK            - int(10) unsigned
     *    
     *   1. ANO_OID                  - int(10) unsigned 
     *   2. ANO_SPECIES_FK           - varchar(20)      
     *   3. ANO_COMPONENT_NAME       - varchar(255)     
     *   4. ANO_IS_PRIMARY           - tinyint(1)       
     *   5. ANO_IS_GROUP             - tinyint(1)       
     *   6. ANO_PUBLIC_ID            - varchar(20)      
     *   7. ANO_DESCRIPTION          - varchar(2000)    
 	 */
    private Long AoidNode; 
    private String AspeciesFK; 
    private String AcomponentName; 
    private boolean Aprimary;
    private boolean Agroup;
    private String ApublicId; 
    private String Adescription; 
    private Long oidRelationship; 
    private String typeFK; 
    private long childFK; 
    private long parentFK;
    private Long BoidNode; 
    private String BspeciesFK; 
    private String BcomponentName; 
    private boolean Bprimary;
    private boolean Bgroup;
    private String BpublicId; 
    private String Bdescription; 

    // Constructors -------------------------------------------------------------------------------
    /*
     * Default constructor.
     */
    public JOINNodeRelationshipNode() {
        
    }

    /*
     * Minimal constructor. Contains required fields.
     */
    public JOINNodeRelationshipNode(long AoidNode, 
    	    String AspeciesFK,
    	    String AcomponentName, 
    	    boolean Aprimary,
    	    boolean Agroup,
    	    String ApublicId,
    	    String Adescription,
    	    Long oidRelationship, 
    		String typeFK, 
    		long childFK, 
    		long parentFK,
    		Long BoidNode, 
    	    String BspeciesFK,
    	    String BcomponentName, 
    	    boolean Bprimary,
    	    boolean Bgroup,
    	    String BpublicId,
    	    String Bdescription) {
    	
        this.AoidNode = AoidNode;
	    this.AspeciesFK = AspeciesFK;
	    this.AcomponentName = AcomponentName;
	    this.Aprimary = Aprimary;
	    this.Agroup = Agroup;
	    this.ApublicId = ApublicId;
	    this.Adescription = Adescription;
        this.oidRelationship = oidRelationship;
        this.typeFK = typeFK; 
        this.childFK = childFK; 
        this.parentFK = parentFK;
        this.BoidNode = BoidNode;
	    this.BspeciesFK = BspeciesFK;
	    this.BcomponentName = BcomponentName;
	    this.Bprimary = Bprimary;
	    this.Bgroup = Bgroup;
	    this.BpublicId = BpublicId;
	    this.Bdescription = Bdescription;
    }

    // Getters ------------------------------------------------------------------------------------
    public Long getAOidNode() {
        return AoidNode;
    }
    public String getASpeciesFK() {
        return AspeciesFK;
    }
    public String getAComponentName() {
        return AcomponentName;
    }
    public boolean isAPrimary() {
        return Aprimary;
    }
    public boolean isAGroup() {
        return Agroup;
    }
    public String getAPublicId() {
        return ApublicId;
    }
    public String getADescription() {
        return Adescription;
    }
    
    public Long getOidRelationship() {
        return oidRelationship;
    }
    public String getTypeFK() {
        return typeFK;
    }
    public long getChildFK() {
        return childFK;
    }
    public long getParentFK() {
        return parentFK;
    }

    public Long getBOidNode() {
        return BoidNode;
    }
    public String getBSpeciesFK() {
        return BspeciesFK;
    }
    public String getBComponentName() {
        return BcomponentName;
    }
    public boolean isBPrimary() {
        return Bprimary;
    }
    public boolean isBGroup() {
        return Bgroup;
    }
    public String getBPublicId() {
        return BpublicId;
    }
    public String getBDescription() {
        return Bdescription;
    }
    
    // Setters ------------------------------------------------------------------------------------
    public void setAOidNode(Long AoidNode) {
        this.AoidNode = AoidNode;
    }
    public void getASpeciesFK(String AspeciesFK) {
        this.AspeciesFK = AspeciesFK;
    }
    public void getAComponentName(String AcomponentName) {
        this.AcomponentName = AcomponentName;
    }
    public void setAPrimary(boolean Aprimary) {
        this.Aprimary = Aprimary;
    }
    public void setAGroup(boolean Agroup) {
        this.Agroup = Agroup;
    }
    public void setAPublicId(String ApublicId) {
        this.ApublicId = ApublicId;
    }
    public void setADescription(String Adescription) {
        this.Adescription = Adescription;
    }
    
    public void setOidRelationship(Long oidRelationship) {
        this.oidRelationship = oidRelationship;
    }
    public void SetTypeFK(String typeFK) {
        this.typeFK = typeFK;
    }
    public void setChildFK(long childFK) {
        this.childFK = childFK;
    }
    public void getParentFK(long parentFK) {
        this.parentFK = parentFK;
    }

    public void setBOidNode(Long BoidNode) {
        this.BoidNode = BoidNode;
    }
    public void getBSpeciesFK(String BspeciesFK) {
        this.BspeciesFK = BspeciesFK;
    }
    public void getBComponentName(String BcomponentName) {
        this.BcomponentName = BcomponentName;
    }
    public void setBPrimary(boolean Bprimary) {
        this.Bprimary = Bprimary;
    }
    public void setBGroup(boolean Bgroup) {
        this.Bgroup = Bgroup;
    }
    public void setBPublicId(String BpublicId) {
        this.BpublicId = BpublicId;
    }
    public void setBDescription(String Bdescription) {
        this.Bdescription = Bdescription;
    }
    
    // Override -----------------------------------------------------------------------------------
    /*
     * Is this JOINNodeRelationshipNode the same as the Supplied JOINNodeRelationshipNode?
     */
    public boolean isSameAs(JOINNodeRelationshipNode daojoinnoderelationshipnode){

    	if ( this.getAOidNode().equals(daojoinnoderelationshipnode.getAOidNode()) &&
    		this.getASpeciesFK().equals(daojoinnoderelationshipnode.getASpeciesFK()) &&
    		this.getAComponentName().equals(daojoinnoderelationshipnode.getAComponentName()) &&
    		this.isAPrimary() == daojoinnoderelationshipnode.isAPrimary() &&
    		this.isAGroup() == daojoinnoderelationshipnode.isAGroup() &&
    		this.getAPublicId().equals(daojoinnoderelationshipnode.getAPublicId()) &&
    		this.getADescription().equals(daojoinnoderelationshipnode.getADescription()) &&
    		this.getOidRelationship().equals(daojoinnoderelationshipnode.getOidRelationship()) &&
    		this.getTypeFK().equals(daojoinnoderelationshipnode.getTypeFK()) &&
    		this.getChildFK() == daojoinnoderelationshipnode.getChildFK() &&
    		this.getParentFK() == daojoinnoderelationshipnode.getParentFK() &&
    		this.getBOidNode().equals(daojoinnoderelationshipnode.getBOidNode()) &&
    		this.getBSpeciesFK().equals(daojoinnoderelationshipnode.getBSpeciesFK()) &&
    		this.getBComponentName().equals(daojoinnoderelationshipnode.getBComponentName()) &&
    		this.isBPrimary() == daojoinnoderelationshipnode.isBPrimary() &&
    		this.isBGroup() == daojoinnoderelationshipnode.isBGroup() &&
    		this.getBPublicId().equals(daojoinnoderelationshipnode.getBPublicId()) &&
    		this.getBDescription().equals(daojoinnoderelationshipnode.getBDescription()) ) {

        	return true;
        }
        else {

        	return false;
        }
    }

    /*
     * The A.Node OID is unique for each JOINNodeRelationshipNode. 
     *  So this should compare JOINNodeRelationshipNode by A.Node OID only.
     */
    public boolean equals(Object other) {
    	
        return (other instanceof JOINNodeRelationshipNode) && (AoidNode != null) 
        		? AoidNode.equals(((JOINNodeRelationshipNode) other).AoidNode) 
        		: (other == this);
    }

    /*
     * Returns the String representation of this JOINNodeRelationshipNode. 
     *  Not required, it just makes reading logs easier.
     */
    public String toString() {
    	
        return String.format("JOINNodeRelationshipNode\n" +
        	"A Node [ AoidNode=%d, AspeciesFK=%s, AcomponentName=%s, Aprimary=%b, Agroup=%b, ApublicId=%s, Adescription=%s ]\n" +
        	"Relationship [ oidRelationship=%d, typeFK=%s, childFK=%d, parentFK=%d ]\n" +
        	"B Node [ BoidNode=%d, BspeciesFK=%s, BcomponentName=%s, Bprimary=%b, Bgroup=%b, BpublicId=%s, Bdescription=%s ]\n" +
            AoidNode, AspeciesFK, AcomponentName, Aprimary, Agroup, ApublicId, Adescription, 
            oidRelationship, typeFK, childFK, parentFK, 
            BoidNode, BspeciesFK, BcomponentName, Bprimary, Bgroup, BpublicId, Bdescription); 
    }
}
