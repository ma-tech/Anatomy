/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayer
*
* Title:        LogDAO.java
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
* Description:  This interface represents a contract for a Data Access Object for the Log model.
*  
*               This Data Access Object should be used as a central point for the mapping between 
*                the Log DTO and a SQL database.
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

import daomodel.Log;

public interface LogDAO extends BaseDAO {
	
    // Actions ------------------------------------------------------------------------------------
    /*
     * Returns the maximum Oid.
     */
    public long maximumOid() throws Exception;
    
    /*
     * Returns the maximum Logged Oid.
     */
    public long maximumLoggedOid() throws Exception;
    
    /*
     * Returns the log from the database matching the given OID, otherwise null.
     */
    public Log findByOid(long oid) throws Exception;
    
    /*
     * Returns the log from the database matching the given Logged OID, otherwise null.
     */
    public Log findByLoggedOid(long loggedOid) throws Exception;
    
    /*
     * Returns a list of ALL logs, otherwise null.
     */
    public List<Log> listAll() throws Exception;
    
    /*
     * Returns true if the given log OID exists in the database.
     */
    public boolean existOid(long oid) throws Exception;

    /*
     * Save the given log in the database.
     * 
     *  If the Log OID is null, 
     *   then it will invoke "create(Log)", 
     *   else it will invoke "update(Log)".
     */
    public void save(Log log) throws Exception;
    
    /*
     * Create the given log in the database. 
     * 
     *  The log OID must be null, otherwise it will throw IllegalArgumentException.
     *  If the log OID value is unknown, rather use save(Log).
     *   After creating, the Data Access Object will set the obtained ID in the given log.
     */
    public void create(Log log) throws IllegalArgumentException, Exception;
    
    /*
     * Update the given log in the database.
     * 
     *  The log OID must not be null, otherwise it will throw IllegalArgumentException. 
     *  If the log OID value is unknown, rather use save(Log)}.
     */
    public void update(Log log) throws Exception;
     
    /*
     *  Delete the given log from the database. 
     *  
     *  After deleting, the Data Access Object will set the ID of the given log to null.
     */
    public void delete(Log log) throws Exception;
    
    /*
     * Returns list of Logs for Display purposes
     *  starting at the given first index with the given row count,
     *  sorted by the given sort field and sort order.
     */
    public List<Log> display(int firstRow, int rowCount, String sortField, boolean sortAscending, String searchFirst, String searchSecond)
        throws Exception;
    
    /*
     * Returns total amount of rows in table.
     */
    public long count(String searchFirst, String searchSecond) throws Exception;
    
    /*
     * Returns total amount of rows in table.
     */
    public long maximum(String sql) throws Exception;
    
}
