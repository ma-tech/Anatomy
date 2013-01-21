/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomy
*
* Title:        ThingDAO.java
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
* Description:  This interface represents a contract for a DAO for the Thing (Object) model.
*  
*               This DAO should be used as a central point for the mapping between 
*                the Thing (Object) DTO and a SQL database.
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

import daomodel.Thing;

public interface ThingDAO extends BaseDAO {
    // Actions ------------------------------------------------------------------------------------
    /*
     * Return the OBO Factory Debug flag - from the OBO properties file
     */
    public String getLevel() throws Exception;

    /*
     * Returns the maximum EMAPA id.
     */
    public int maximumOid() throws Exception;
    
    /*
     * Returns the Thing from the database matching the given OID, otherwise null.
     */
    public Thing findByOid(Long oid) throws Exception;
    
    /*
     * Returns a list of ALL things, otherwise null.
     */
    public List<Thing> listAll() throws Exception;
    
    /*
     * Returns true if the given thing OID exists in the database.
     */
    public boolean existOid(Long oid) throws Exception;

    /*
     * Save the given thing in the database.
     * 
     *  If the Thing OID is null, 
     *   then it will invoke "create(Thing)", 
     *   else it will invoke "update(Thing)".
     */
    public void save(Thing thing) throws Exception;

    /*
     * Create the given thing in the database. 
     * 
     *  The thing OID must be null, otherwise it will throw IllegalArgumentException.
     *  If the thing OID value is unknown, rather use save(Thing).
     *   After creating, the DAO will set the obtained ID in the given thing.
     */
    public void create(Thing thing) throws IllegalArgumentException, Exception;
    
    /*
     * Update the given thing in the database.
     * 
     *  The thing OID must not be null, otherwise it will throw IllegalArgumentException. 
     *  If the thing OID value is unknown, rather use save(Thing)}.
     */
    public void update(Thing thing) throws Exception;
    
    /*
     * Delete the given thing from the database. 
     * 
     *  After deleting, the DAO will set the ID of the given thing to null.
     */
    public void delete(Thing thing) throws Exception;
    
     /*
     * Returns list of Things for Display purposes
     *  starting at the given first index with the given row count,
     *  sorted by the given sort field and sort order.
     */
    public List<Thing> display(int firstRow, int rowCount, String sortField, boolean sortAscending, String searchFirst, String searchSecond)
        throws Exception;
    
    /*
     * Returns total amount of rows in table.
     */
    public int count(String searchFirst, String searchSecond) throws Exception;

    /*
     * Returns total amount of rows in table.
     */
    public int maximum(String sql) throws Exception;
    
}
