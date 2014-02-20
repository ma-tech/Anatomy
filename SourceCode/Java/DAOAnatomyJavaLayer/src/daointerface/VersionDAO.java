/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayer
*
* Title:        VersionDAO.java
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
* Description:  This interface represents a contract for a Data Access Object for the Version model.
*  
*               This Data Access Object should be used as a central point for the mapping between 
*                the Version DTO and a SQL database.
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

import daomodel.Version;

public interface VersionDAO extends BaseDAO {
    // Actions ------------------------------------------------------------------------------------
    /*
     * Returns the version from the database matching the given OID, otherwise null.
     */
    public Version findByOid(long oid) throws Exception;
    
    /*
     * Returns the most recent version from the database matching the given OID, otherwise null.
     */
    public Version findMostRecent() throws Exception;
    
    /*
     * Returns a list of ALL versions, otherwise null.
     */
    public List<Version> listAll() throws Exception;
    
    /*
     * Returns true if the given version OID exists in the database.
     */
    public boolean existOid(long oid) throws Exception;

    /*
     * Save the given version in the database.
     * 
     *  IF the Version OID is null THEN, 
     *   it will invoke "create(Version)",
     *  ELSE 
     *   it will invoke "update(Version)".
     */
    public void save(Version version) throws Exception;
    
     /*
     * Create the given version in the database. 
     * 
     *  The version OID must be null, otherwise it will throw IllegalArgumentException.
     *  
     *  If the version OID value is unknown, use save(Version) instead
     *  
     *   After creating, the Data Access Object will set the obtained ID in the supplied version.
     */
    public void create(Version version) throws IllegalArgumentException, Exception;

    /*
     * Update the given version in the database.
     * 
     *  The version OID must NOT be null, otherwise it will throw IllegalArgumentException.
     *   
     *  If the version OID value is known, use save(Version) instead.
     */
    public void update(Version version) throws Exception;
     
    /*
     * Delete the given version from the database.
     *  
     *  After deleting, the Data Access Object will set the ID of the supplied version to null.
     */
    public void delete(Version version) throws Exception;
    
     /*
     * Returns list of Versions for Display purposes
     *  starting at the given first index with the given row count,
     *  sorted by the given sort field and sort order.
     */
    public List<Version> display(int firstRow, int rowCount, String sortField, boolean sortAscending, String searchFirst, String searchSecond)
        throws Exception;

    /*
     * Returns total amount of rows in table for 2 possible search values.
     */
    public long count(String searchFirst, String searchSecond) throws Exception;

    /*
     * Returns total amount of rows in table.
     */
    public long countAll() throws Exception;

}
