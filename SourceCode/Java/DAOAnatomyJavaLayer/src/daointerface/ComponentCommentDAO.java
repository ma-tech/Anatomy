/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayer
*
* Title:        ComponentCommentDAO.java
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
* Description:  This interface represents a contract for a Data Access Object for the ComponentComment model.
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

import daomodel.ComponentComment;

public interface ComponentCommentDAO extends BaseDAO {

    // Actions ------------------------------------------------------------------------------------
    /*
     * Returns the daocomponentcomment from the database matching the given OID, otherwise null.
     */
    public ComponentComment findByOid(long oid) throws Exception;
    
    /*
     * Returns the daocomponentcomments from the database matching the given OBO ID, otherwise null.
     */
    public List<ComponentComment> listByOboId(String oboid) throws Exception;
    
    /*
     * Returns a list of ALL componentcomments, otherwise null.
     */
    public List<ComponentComment> listAll() throws Exception;
    
    /*
     * Returns true if the given daocomponentcomment OID exists in the database.
     */
    public boolean existOid(long oid) throws Exception;

    /*
     * Save the given daocomponentcomment in the database.
     * 
     *  If the ComponentComment OID is null, 
     *   then it will invoke "create(ComponentComment)", 
     *   else it will invoke "update(ComponentComment)".
     */
    public void save(ComponentComment daocomponentcomment) throws Exception;

     /*
     * Create the given daocomponentcomment in the database. 
     * 
     *  The daocomponentcomment OID must be null, otherwise it will throw IllegalArgumentException.
     *  If the daocomponentcomment OID value is unknown, rather use save(ComponentComment).
     *   After creating, the Data Access Object will set the obtained ID in the given daocomponentcomment.
     */
    public void create(ComponentComment daocomponentcomment) throws IllegalArgumentException, Exception;
    
    /*
     * Update the given daocomponentcomment in the database.
     * 
     *  The daocomponentcomment OID must not be null, otherwise it will throw IllegalArgumentException. 
     *  If the daocomponentcomment OID value is unknown, rather use save(ComponentComment)}.
     */
    public void update(ComponentComment daocomponentcomment) throws Exception;
     
    /*
     *  Delete the given daocomponentcomment from the database. 
     *  After deleting, the Data Access Object will set the ID of the given daocomponentcomment to null.
     */
    public void delete(ComponentComment daocomponentcomment) throws Exception;
    
    /*
     *  Delete the given daocomponentcomment from the database. 
     *  After deleting, the Data Access Object will set the ID of the given daocomponentcomment to null.
     */
    public void empty() throws Exception;
    
     /*
     * Returns list of ComponentComments for Display purposes
     *  starting at the given first index with the given row count,
     *  sorted by the given sort field and sort order.
     */
    public List<ComponentComment> display(int firstRow, int rowCount, String sortField, boolean sortAscending, String searchFirst, String searchSecond)
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
