/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomy
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
* Version: 1
*
* Description:  This interface represents a contract for a DAO for the TimedNode model.
*  
*               This DAO should be used as a central point for the mapping between 
*                the Timed Node DTO and a SQL database.
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

import daomodel.TimedNode;

public interface TimedNodeDAO extends BaseDAO {
    // Actions ------------------------------------------------------------------------------------
    /*
     * Returns the maximum EMAP id.
     */
    public int maximumEmap() throws Exception;
    
    /*
     * Returns the TimedNode from the database matching the given OID, otherwise null.
     */
    public TimedNode findByOid(Long oid) throws Exception;
    
    /*
     * Returns a list of ALL timednodes by Parent FK, otherwise null.
     */
    public List<TimedNode> listByNodeFK(Long nodeFK) throws Exception;
    
    /*
     * Returns a list of ALL timednodes by Parent FK, otherwise null.
     */
    public List<TimedNode> listByStageFK(Long stageFK) throws Exception;
    
    /*
     * Returns a list of ALL timednodes by Parent FK, otherwise null.
     */
    public List<TimedNode> listByPublicID(String publicID) throws Exception;
    
    /*
     * Returns a list of ALL timednodes By Display Id, otherwise null.
     */
    public List<TimedNode> listByDisplayId() throws Exception;
    
    /*
     * Returns a list of ALL timednodes, otherwise null.
     */
    public List<TimedNode> listAll() throws Exception;
    
    /*
     * Returns a list of ALL timednodes, otherwise null.
     */
    public List<TimedNode> listAllOrderByPublicId() throws Exception;
    
    /*
     * Returns a list of ALL timednodes, otherwise null.
     */
    public List<TimedNode> listAllOrderByDisplayId() throws Exception;
    
    /*
     * Returns true if the given timednode OID exists in the database.
     */
    public boolean existOid(Long oid) throws Exception;

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
     *  If the timednode OID value is unknown, rather use save(TimedNode).
     *   After creating, the DAO will set the obtained ID in the given timednode.
     */
    public void create(TimedNode timednode) throws IllegalArgumentException, Exception;
    
    /*
     * Update the given timednode in the database.
     * 
     *  The timednode OID must not be null, otherwise it will throw IllegalArgumentException. 
     *  If the timednode OID value is unknown, rather use save(TimedNode).
     */
    public void update(TimedNode timednode) throws Exception;
     
    /*
     * Delete the given timednode from the database. 
     * 
     *  After deleting, the DAO will set the ID of the given timednode to null.
     */
    public void delete(TimedNode timednode) throws Exception;
    
    /*
     * Delete the given timednode from the database.
     * 
     *  After deleting, the DAO will set the ID of the given timednode to null.
     */
    public void deleteByNodeAndStage(Long nodeFK, Long stageFK) throws Exception;
    
     /*
     * Returns list of TimedNodes for Display purposes
     *  starting at the given first index with the given row count,
     *  sorted by the given sort field and sort order.
     */
    public List<TimedNode> display(int firstRow, int rowCount, String sortField, boolean sortAscending, String searchFirst, String searchSecond)
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
