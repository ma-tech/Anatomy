/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomy
*
* Title:        EditorDAO.java
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
* Description:  This interface represents a contract for a DAO for the Editor model.
*  
*               This DAO should be used as a central point for the mapping between 
*                the Editor DTO and a SQL database.
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

import daomodel.Editor;

public interface EditorDAO extends BaseDAO {
    // Actions ------------------------------------------------------------------------------------
    /*
     * Returns the editor from the database matching the given OID, otherwise null.
     */
    public Editor find(Long oid) throws Exception;

    /*
     * Returns a list of ALL editors, otherwise null.
     */
    public List<Editor> listAll() throws Exception;
    
    /*
     * Create the given editor in the database. 
     * The editor OID must be null, otherwise it will throw IllegalArgumentException.
     * If the editor OID value is unknown, rather use save(Editor).
     * After creating, the DAO will set the obtained ID in the given editor.
     */
    public void create(Editor editor) throws IllegalArgumentException, Exception;
    
    /*
     * Update the given editor in the database.
     *  The editor OID must not be null, otherwise it will throw IllegalArgumentException. 
     *  If the editor OID value is unknown, rather use save(Editor)}.
     */
    public void update(Editor editor) throws Exception;
    
    /*
     * Delete the given editor from the database. 
     *  After deleting, the DAO will set the ID of the given editor to null.
     */
    public void delete(Editor editor) throws Exception;
    
    /*
     * Returns true if the given editor OID exists in the database.
     */
    public boolean existOid(String oid) throws Exception;

    /*
     * Returns list of Synonyms for Display purposes
     *  starting at the given first index with the given row count,
     *  sorted by the given sort field and sort order.
     */
    public List<Editor> display(int firstRow, int rowCount, String sortField, boolean sortAscending, String searchTerm)
        throws Exception;

    /*
     * Returns total amount of rows in table.
     */
    public int count(String searchTerm) throws Exception;

}