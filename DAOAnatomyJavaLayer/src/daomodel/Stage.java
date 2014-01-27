/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomy
*
* Title:        Stage.java
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
* Description:  This class represents a SQL Database Transfer Object for the Stage Table.
*                ANA_STAGE 
*                  - All Stages in the Anatomy DAG
*                    - Mouse - Theiler, TS
*                    - Human - Carnegie, CS
*                    - Chick - Hamburger Hamilton, HH & EGK
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

public class Stage {
    // Properties ---------------------------------------------------------------------------------
	/*
     *   1. STG_OID              - int(10) unsigned
     *   2. STG_SPECIES_FK       - varchar(20)      
     *   3. STG_NAME             - varchar(20)      
     *   4. STG_SEQUENCE         - int(10) unsigned 
     *   5. STG_DESCRIPTION      - varchar(2000)    
     *   6. STG_SHORT_EXTRA_TEXT - varchar(25)      
     *   7. STG_PUBLIC_ID        - varchar(20)      
	 */
    private Long oid; 
    private String speciesFK; 
    private String name; 
    private long sequence;
    private String description;
    private String extraText; 
    private String publicId; 

    // Constructors -------------------------------------------------------------------------------
    /*
     * Default constructor.
     */
    public Stage() {
        
    }

    /*
     * Minimal constructor. Contains required fields.
     */
    public Stage(Long oid, 
    		String speciesFK, 
    		String name, 
    		long sequence, 
    		String description, 
    		String extraText, 
    		String publicId) {
    	
        this.oid = oid;
        this.speciesFK = speciesFK; 
        this.name = name; 
        this.sequence = sequence;
        this.description = description;
        this.extraText = extraText; 
        this.publicId = publicId; 
    }

    // Getters ------------------------------------------------------------------------------------
    public Long getOid() {
        return oid;
    }
    public String getSpeciesFK() {
        return speciesFK;
    }
    public String getName() {
        return name;
    }
    public long getSequence() {
        return sequence;
    }
    public String getDescription() {
        return description;
    }
    public String getExtraText() {
        return extraText;
    }
    public String getPublicId() {
        return publicId;
    }

    // Setters ------------------------------------------------------------------------------------
    public void setOid(Long oid) {
        this.oid = oid;
    }
    public void setSpeciesFK(String speciesFK) {
        this.speciesFK = speciesFK;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setSequence(long sequence) {
        this.sequence = sequence;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setExtraText(String extraText) {
        this.extraText = extraText;
    }
    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }

    // Helper -------------------------------------------------------------------------------------
    /*
     * Is this Stage the same as the Supplied Stage?
     */
    public boolean isSameAs(Stage daostage){

    	if (this.getSpeciesFK().equals(daostage.getSpeciesFK()) &&
    		this.getName().equals(daostage.getName()) &&
    		this.getSequence() == daostage.getSequence() &&
    		this.getDescription().equals(daostage.getDescription()) &&
    		this.getExtraText().equals(daostage.getExtraText()) &&
    		this.getPublicId().equals(daostage.getPublicId()) ) {

        	return true;
        }
        else {

        	return false;
        }
    }

    /*
     * The relation ID is unique for each Stage. 
     *  So this should compare Stage by ID only.
     */
    public boolean equals(Object other) {
    	
        return (other instanceof Stage) && (oid != null) 
        		? oid.equals(((Stage) other).oid) 
        		: (other == this);
    }

    /*
     * Returns the String representation of this Stage. 
     *  Not required, it just makes reading logs easier.
     */
    public String toString() {
    	
        return String.format("Stage [ oid=%d, speciesFK=%s, name=%s, sequence=%d, description=%s, extraText=%s, publicId=%s ]", 
            oid, speciesFK, name, sequence, description, extraText, publicId);
    }
}
