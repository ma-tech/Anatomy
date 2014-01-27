/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomy
*
* Title:        RelationshipType.java
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
* Description:  This class represents a SQL Database Transfer Object for the RelationshipType Table.
*                ANA_RELATIONSHIP_TYPE
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

public class RelationshipType {
    // Properties ---------------------------------------------------------------------------------
	/*
     *   2. RTY_NAME                    - varchar(20)      
     *   3. RTY_CHILD_TO_PARENT_DISPLAY - varchar(40) 
     *   4. RTY_PARENT_TO_CHILD_DISPLAY - varchar(40) 
	 */
    private String name; 
    private String child2parent; 
    private String parent2child; 

    // Constructors -------------------------------------------------------------------------------
    /*
     * Default constructor.
     */
    public RelationshipType() {
        
    }

    /*
     * Minimal constructor. Contains required fields.
     * Full constructor. Contains required and optional fields.
     *  The Full Constructor is the Minimal Constructor
     */
    public RelationshipType(String name, 
    		String parent2child, 
    		String child2parent) {
    	
        this.name = name; 
        this.parent2child = parent2child; 
        this.child2parent = child2parent;
    }

    // Getters ------------------------------------------------------------------------------------
    public String getName() {
        return name;
    }
    public String getParent2Child() {
        return parent2child;
    }
    public String getChild2Parent() {
        return child2parent;
    }

    // Setters ------------------------------------------------------------------------------------
    public void setName(String name) {
        this.name = name;
    }
    public void setParent2Child(String parent2child) {
        this.parent2child = parent2child;
    }
    public void setChild2Parent(String child2parent) {
        this.child2parent = child2parent;
    }

    // Override -----------------------------------------------------------------------------------
    /*
     * Is this RelationshipType the same as the Supplied RelationshipType?
     */
    public boolean isSameAs(RelationshipType daorelationshiptype){

    	if (this.getName().equals(daorelationshiptype.getName()) &&
    		this.getParent2Child() == daorelationshiptype.getParent2Child() &&
    		this.getChild2Parent() == daorelationshiptype.getChild2Parent() ) {

        	return true;
        }
        else {

        	return false;
        }
    }

    /*
     * The relation OID is unique for each RelationshipType. 
     *  So this should compare RelationshipType by OID only.
     */
    public boolean equals(Object other) {
    	
        return (other instanceof RelationshipType) && (name != null) 
        		? name.equals(((RelationshipType) other).name) 
        		: (other == this);
    }

    /*
     * Returns the String representation of this RelationshipType.
     *  Not required, it just aids log reading.
     */
    public String toString() {
    	
        return String.format("RelationshipType [ name=%s, parent2child=%s, child2parent=%s ]", 
            name, parent2child, child2parent);
    }
}
