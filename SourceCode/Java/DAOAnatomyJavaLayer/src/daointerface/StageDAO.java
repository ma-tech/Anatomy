/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayer
*
* Title:        StageDAO.java
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
* Description:  This interface represents a contract for a Data Access Object for the Stage model.
*  
*               This Data Access Object should be used as a central point for the mapping between 
*                the Stage DTO and a SQL database.
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

import daomodel.Stage;

public interface StageDAO extends BaseDAO {

    // Actions ------------------------------------------------------------------------------------
    /*
     * Returns the Stage from the database matching the given OID, otherwise null.
     */
    public Stage findByOid(long oid) throws Exception;
    
    /*
     * Returns the Stage from the database matching the given Name, otherwise null.
     */
    public Stage findByName(String name) throws Exception;
    
    /*
     * Returns the Stage from the database matching the given Sequence Number, otherwise null.
     */
    public Stage findBySequence(long seq) throws Exception;
    
    /*
     * Returns a list of ALL stages, ordered by Sequence otherwise null.
     */
    public List<Stage> listAllBySequence() throws Exception;
    
    /*
     * Returns a list of ALL stages, otherwise null.
     */
    public List<Stage> listAll() throws Exception;
    
    /*
     * Returns true if the given stage OID exists in the database.
     */
    public boolean existOid(long oid) throws Exception;

    /*
     * Returns the Maximum stage sequence in the database.
     */
    public int valueMaxSequence() throws Exception;

    /*
     * Returns the Minimum stage sequence in the database.
     */
    public int valueMinSequence() throws Exception;

    /*
     * Save the given stage in the database.
     * 
     *  If the Stage OID is null, 
     *   then it will invoke "create(Stage)", 
     *   else it will invoke "update(Stage)".
     */
    public void save(Stage stage) throws Exception;
    
     /*
     * Create the given stage in the database.
     *  
     *  The stage OID must be null, otherwise it will throw IllegalArgumentException.
     *  If the stage OID value is unknown, rather use save(Stage).
     *   After creating, the Data Access Object will set the obtained ID in the given stage.
     */
    public void create(Stage stage) throws IllegalArgumentException, Exception;
    
    /*
     * Update the given stage in the database.
     * 
     *  The stage OID must not be null, otherwise it will throw IllegalArgumentException. 
     *  If the stage OID value is unknown, rather use save(Stage)}.
     */
    public void update(Stage stage) throws Exception;
     
    /*
     * Delete the given stage from the database. 
     * 
     *  After deleting, the Data Access Object will set the ID of the given stage to null.
     */
    public void delete(Stage stage) throws Exception;
    
     /*
     * Returns list of Stages for Display purposes
     *  starting at the given first index with the given row count,
     *  sorted by the given sort field and sort order.
     */
    public List<Stage> display(int firstRow, int rowCount, String sortField, boolean sortAscending, String searchFirst, String searchSecond)
        throws Exception;
    
    /*
     * Returns total amount of rows in table.
     */
    public int count(String searchFirst, String searchSecond) throws Exception;

    /*
     * Returns total amount of rows in table.
     */
    public int value(String sql) throws Exception;

}
