/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayer
*
* Title:        TimedNodeFKDAO.java
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
* Description:  This interface represents a contract for a Data Access Object for the TimedNodeFK model.
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

import daomodel.TimedNodeFK;

public interface TimedNodeFKDAO extends BaseDAO {
    // Actions ------------------------------------------------------------------------------------
    /*
     * Returns the maximum EMAP id.
     */
    public long maximumEmap() throws Exception;
    
    /*
     * Returns the TimedNodeFK from the database matching the given OID, otherwise null.
     */
    public TimedNodeFK findByOid(long oid) throws Exception;
    
    /*
     * Returns a list of ALL timednodefks by Node FK, otherwise null.
     */
    public List<TimedNodeFK> listByNodeFK(long nodeFK) throws Exception;
    
    /*
     * Returns a list of ALL timednodefks by Stage FK, otherwise null.
     */
    public List<TimedNodeFK> listByStageFK(long stageFK) throws Exception;
    
    /*
     * Returns a list of ALL timednodefks by Timed Node Public ID FK, otherwise null.
     */
    public List<TimedNodeFK> listByPublicID(String publicID) throws Exception;
    
    /*
     * Returns a list of ALL timednodefks By Timed Node Display Id, otherwise null.
     */
    public List<TimedNodeFK> listByDisplayId() throws Exception;
    
    /*
     * Returns a list of ALL timednodefks, otherwise null.
     */
    public List<TimedNodeFK> listAll() throws Exception;
    
    /*
     * Returns a list of ALL timednodefks, ordered by Public Id, otherwise null.
     */
    public List<TimedNodeFK> listAllOrderByPublicId() throws Exception;
    
    /*
     * Returns a list of ALL timednodefks, ordered by Display ID, otherwise null.
     */
    public List<TimedNodeFK> listAllOrderByDisplayId() throws Exception;
    
    /*
     * Returns true if the given timednodefk OID exists in the database.
     */
    public boolean existOid(long oid) throws Exception;

    /*
     * Returns true if the given timednodefk publicId exists in the database.
     */
    public boolean existPublicId(String publicId) throws Exception;

    /*
     * Returns true if the given timednodefk displayId exists in the database.
     */
    public boolean existDisplayId(String displayId) throws Exception;

     /*
     * Returns list of TimedNodeFKs for Display purposes
     *  
     *  starting at the given first index with the given row count,
     *  sorted by the given sort field and sort order.
     */
    public List<TimedNodeFK> display(int firstRow, int rowCount, String sortField, boolean sortAscending, String searchFirst, String searchSecond)
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
