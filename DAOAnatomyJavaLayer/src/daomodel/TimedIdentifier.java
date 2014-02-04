/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayer
*
* Title:        TimedIdentifier.java
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
* Description:  This class represents a SQL Database Transfer Object for the Timed Identifier Table.
*                ANA_TIMED_IDENTIFIER
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

public class TimedIdentifier {
    // Properties ---------------------------------------------------------------------------------
	/*
     *   1. ATI_OID               - int(10) unsigned 
     *   2. ATI_OLD_PUBLIC_ID     - varchar(20)      
     *   3. ATI_OLD_DISPLAY_ID    - varchar(20)      
	 */
    private Long oid; 
    private String oldPublicId;
    private String oldDisplayId;

    // Constructors -------------------------------------------------------------------------------
    /*
     * Default constructor.
     */
    public TimedIdentifier() {
        
    }

    /*
     * Minimal constructor. Contains required fields.
     */
    public TimedIdentifier(Long oid, 
    		String oldPublicId,
    		String oldDisplayId) {
    	
        this.oid = oid;
        this.oldPublicId = oldPublicId;
        this.oldDisplayId = oldDisplayId;
    }

    // Getters ------------------------------------------------------------------------------------
    public Long getOid() {
        return oid;
    }
    public String getOldPublicId() {
        return oldPublicId;
    }
    public String getOldDisplayId() {
        return oldDisplayId;
    }

    // Setters ------------------------------------------------------------------------------------
    public void setOid(Long oid) {
        this.oid = oid;
    }
    public void setOldPublicId(String oldPublicId) {
        this.oldPublicId = oldPublicId;
    }
    public void setOldDisplayId(String oldDisplayId) {
        this.oldDisplayId = oldDisplayId;
    }

    // Helper -------------------------------------------------------------------------------------
    /*
     * Is this TimedIdentifier the same as the Supplied TimedIdentifier?
     */
    public boolean isSameAs(TimedIdentifier daotimedidentifier){

    	if (this.getOldPublicId().equals(daotimedidentifier.getOldPublicId()) &&
    		this.getOldDisplayId().equals(daotimedidentifier.getOldDisplayId()) ) {

        	return true;
        }
        else {

        	return false;
        }
    }

    /*
     * The Timed Node OID is unique for each Timed Node.
     *  So this should compare Timed Node by OID only.
     */
    public boolean equals(Object other) {
    	
        return (other instanceof TimedIdentifier) && (oid != null) 
        		? oid.equals(((TimedIdentifier) other).oid) 
        		: (other == this);
    }

    /*
     * Returns the String representation of this Timed Node. 
     *  Not required, it just makes reading logs easier
     */
    public String toString() {
    	
        return String.format("TimedIdentifier [ oid=%d, oldPublicId=%s, oldDisplayId=%s ]", 
            oid, oldPublicId, oldDisplayId);
    }
}
