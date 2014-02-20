/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayer
*
* Title:        TimedNodeDAO.java
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
* Description:  This interface represents a contract for a Data Access Object for the TimedNode model.
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

import daomodel.TimedNode;

public interface TimedNodeDAO extends BaseDAO {
    // Actions ------------------------------------------------------------------------------------
    /*
     * Returns the maximum EMAP id.
     */
    public long maximumEmap() throws Exception;
    
    /*
     * Returns the TimedNode from the database matching the given OID, otherwise null.
     */
    public TimedNode findByOid(long oid) throws Exception;
    
    /*
     * Returns a list of ALL timednodes by Node FK, otherwise null.
     */
    public List<TimedNode> listByNodeFK(long nodeFK) throws Exception;
    
    /*
     * Returns a list of ALL timednodes by Stage FK, otherwise null.
     */
    public List<TimedNode> listByStageFK(long stageFK) throws Exception;
    
    /*
     * Returns a list of ALL timednodes by Timed Node Public ID FK, otherwise null.
     */
    public List<TimedNode> listByPublicID(String publicID) throws Exception;
    
    /*
     * Returns a list of ALL timednodes By Timed Node Display Id, otherwise null.
     */
    public List<TimedNode> listByDisplayId() throws Exception;
    
    /*
     * Returns a list of ALL timednodes, otherwise null.
     */
    public List<TimedNode> listAll() throws Exception;
    
    /*
     * Returns a list of ALL timednodes, ordered by Public Id, otherwise null.
     */
    public List<TimedNode> listAllOrderByPublicId() throws Exception;
    
    /*
     * Returns a list of ALL timednodes, ordered by Display ID, otherwise null.
     */
    public List<TimedNode> listAllOrderByDisplayId() throws Exception;
    
    /*
     * Returns true if the given timednode OID exists in the database.
     */
    public boolean existOid(long oid) throws Exception;

    /*
     * Returns true if the given timednode publicId exists in the database.
     */
    public boolean existPublicId(String publicId) throws Exception;

    /*
     * Returns true if the given timednode displayId exists in the database.
     */
    public boolean existDisplayId(String displayId) throws Exception;

    /*
     * Save the given timednode in the database.
     * 
     *  If the TimedNode OID is null, 
     *   then it will invoke "create(TimedNode)", 
     *   else it will invoke "update(TimedNode)".
     */
    public void save(TimedNode timednode) throws Exception;
   
 
    /*
     * Create the given timednode in the database.
     *  
     *  The timednode OID must be null, otherwise it will throw IllegalArgumentException.
     *  
     *  If the timednode OID value is unknown, use save(TimedNode) instead.
     *  
     *   After creating, the Data Access Object will set the obtained ID in the given timednode.
     */
    public void create(TimedNode timednode) throws IllegalArgumentException, Exception;
    
    /*
     * Update the given timednode in the database.
     * 
     *  The timednode OID must not be null, otherwise it will throw IllegalArgumentException.
     *   
     *  If the timednode OID value is unknown, rather use save(TimedNode).
     */
    public void update(TimedNode timednode) throws Exception;
     
    /*
     * Delete the given timednode from the database. 
     * 
     *  After deleting, the Data Access Object will set the ID of the given timednode to null.
     */
    public void delete(TimedNode timednode) throws Exception;
    
    /*
     * Delete the given timednode from the database.
     * 
     *  After deleting, the Data Access Object will set the ID of the given timednode to null.
     */
    public void deleteByNodeAndStage(long nodeFK, long stageFK) throws Exception;
    
     /*
     * Returns list of TimedNodes for Display purposes
     *  
     *  starting at the given first index with the given row count,
     *  sorted by the given sort field and sort order.
     */
    public List<TimedNode> display(int firstRow, int rowCount, String sortField, boolean sortAscending, String searchFirst, String searchSecond)
        throws Exception;
    
    /*
     * Returns total number of rows in table.
     */
    public long count(String searchFirst, String searchSecond) throws Exception;

    /*
     * Returns the largest used Public (EMAP) Id.
     */
    public long maximum(String sql) throws Exception;
    
}
