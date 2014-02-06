/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayer
*
* Title:        StageModifier.java
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
* Description:  This class represents a SQL Database Transfer Object for the StageModifier Table.
*                ANA_STAGE 
*                  - All StageModifiers in the Anatomy DAG
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

public class StageModifier {
    // Properties ---------------------------------------------------------------------------------
	/*
     *   1. SMO_NAME             - varchar(20)      
	 */
    private String name; 

    // Constructors -------------------------------------------------------------------------------
    /*
     * Default constructor.
     */
    public StageModifier() {
        
    }

    /*
     * Minimal constructor. Contains required fields.
     */
    public StageModifier( 
    		String name) {
    	
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

    // Helper -------------------------------------------------------------------------------------
    /*
     * Is this StageModifier the same as the Supplied StageModifier?
     */
    public boolean isSameAs(StageModifier daostagemodifier){

    	if (this.getName().equals(daostagemodifier.getName()) ) {

        	return true;
        }
        else {

        	return false;
        }
    }

    /*
     * The relation ID is unique for each StageModifier. 
     *  So this should compare StageModifier by ID only.
     */
    public boolean equals(Object other) {
    	
        return (other instanceof StageModifier) && (name != null) 
        		? name.equals(((StageModifier) other).name) 
        		: (other == this);
    }

    /*
     * Returns the String representation of this StageModifier. 
     *  Not required, it just makes reading logs easier.
     */
    public String toString() {
    	
        return String.format("StageModifier [ name=%s ]", 
            name);
    }
}
