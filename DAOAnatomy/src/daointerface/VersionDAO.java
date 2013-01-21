/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomy
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
* Version: 1
*
* Description:  This interface represents a contract for a DAO for the Version model.
*  
*               This DAO should be used as a central point for the mapping between 
*                the Version DTO and a SQL database.
*
* Link:         http://balusc.blogspot.com/2008/07/dao-tutorial-data-layer.html
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

import daomodel.Version;

public interface VersionDAO extends BaseDAO {
    // Actions ------------------------------------------------------------------------------------
    /*
     * Returns the version from the database matching the given OID, otherwise null.
     */
    public Version findByOid(Long oid) throws Exception;
    
    /*
     * Returns the version from the database matching the given OID, otherwise null.
     */
    public Version findMostRecent() throws Exception;
    
    /*
     * Returns a list of ALL versions, otherwise null.
     */
    public List<Version> listAll() throws Exception;
    
    /*
     * Returns true if the given version OID exists in the database.
     */
    public boolean existOid(Long oid) throws Exception;

    /*
     * Save the given version in the database.
     * 
     *  If the Version OID is null, 
     *   then it will invoke "create(Version)", 
     *   else it will invoke "update(Version)".
     */
    public void save(Version version) throws Exception;
    
     /*
     * Create the given version in the database. 
     * 
     *  The version OID must be null, otherwise it will throw IllegalArgumentException.
     *  If the version OID value is unknown, rather use save(Version).
     *   After creating, the DAO will set the obtained ID in the given version.
     */
    public void create(Version version) throws IllegalArgumentException, Exception;

    /*
     * Update the given version in the database.
     * 
     *  The version OID must not be null, otherwise it will throw IllegalArgumentException. 
     *  If the version OID value is unknown, rather use save(Version)}.
     */
    public void update(Version version) throws Exception;
     
    /*
     * Delete the given version from the database.
     *  
     *  After deleting, the DAO will set the ID of the given version to null.
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
     * Returns total amount of rows in table.
     */
    public int count(String searchFirst, String searchSecond) throws Exception;

    /*
     * Returns total amount of rows in table.
     */
    public int countAll() throws Exception;

}
