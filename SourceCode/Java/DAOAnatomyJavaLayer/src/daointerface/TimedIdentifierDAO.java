/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayer
*
* Title:        TimedIdentifierDAO.java
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
* Description:  This interface represents a contract for a Data Access Object for the TimedIdentifier model.
*  
*               This Data Access Object should be used as a central point for the mapping between 
*                the Timed Node DTO and a SQL database.
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
package daointerface;

import java.util.List;

import daointerface.BaseDAO;

import daomodel.TimedIdentifier;

public interface TimedIdentifierDAO extends BaseDAO {
    // Actions ------------------------------------------------------------------------------------
    /*
     * Returns the TimedIdentifier from the database matching the given OID, otherwise null.
     */
    public TimedIdentifier findByOid(long oid) throws Exception;
    
    /*
     * Returns a list of ALL timedidentifiers, otherwise null.
     */
    public List<TimedIdentifier> listAll() throws Exception;
    
    /*
     * Returns true if the given timedidentifier OID exists in the database.
     */
    public boolean existOid(long oid) throws Exception;

    /*
     * Save the given timedidentifier in the database.
     * 
     *  If the TimedIdentifier OID is null, 
     *   then it will invoke "create(TimedIdentifier)", 
     *   else it will invoke "update(TimedIdentifier)".
     */
    public void save(TimedIdentifier timedidentifier) throws Exception;
   
 
    /*
     * Create the given timedidentifier in the database.
     *  
     *  The timedidentifier OID must be null, otherwise it will throw IllegalArgumentException.
     *  
     *  If the timedidentifier OID value is unknown, use save(TimedIdentifier) instead.
     *  
     *   After creating, the Data Access Object will set the obtained ID in the given timedidentifier.
     */
    public void create(TimedIdentifier timedidentifier) throws IllegalArgumentException, Exception;
    
    /*
     * Update the given timedidentifier in the database.
     * 
     *  The timedidentifier OID must not be null, otherwise it will throw IllegalArgumentException.
     *   
     *  If the timedidentifier OID value is unknown, rather use save(TimedIdentifier).
     */
    public void update(TimedIdentifier timedidentifier) throws Exception;
     
    /*
     * Delete the given timedidentifier from the database. 
     * 
     *  After deleting, the Data Access Object will set the ID of the given timedidentifier to null.
     */
    public void delete(TimedIdentifier timedidentifier) throws Exception;
    
     /*
     * Returns list of TimedIdentifiers for Display purposes
     *  
     *  starting at the given first index with the given row count,
     *  sorted by the given sort field and sort order.
     */
    public List<TimedIdentifier> display(int firstRow, int rowCount, String sortField, boolean sortAscending, String searchFirst, String searchSecond)
        throws Exception;
    
    /*
     * Returns total number of rows in table.
     */
    public int count(String searchFirst, String searchSecond) throws Exception;

}
