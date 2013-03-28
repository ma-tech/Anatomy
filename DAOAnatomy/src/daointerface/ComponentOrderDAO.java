/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomy
*
* Title:        ComponentOrderDAO.java
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
* Description:  This interface represents a contract for a DAO for the ComponentOrder model.
*  
*               This DAO should be used as a central point for the mapping between 
*                the ComponentOrder DTO and a SQL database.
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

import daomodel.ComponentOrder;

public interface ComponentOrderDAO extends BaseDAO {
    // Actions ------------------------------------------------------------------------------------
    /*
     * Returns the daocomponentorder from the database matching the given OID, otherwise null.
     */
    public ComponentOrder findByOid(long oid) throws Exception;
    
    /*
     * Returns the daocomponentorder from the database matching the given OID, otherwise null.
     */
    public List<ComponentOrder> listOrderByParentBySpecialOrder() throws Exception;
    
    /*
     * Returns the daocomponentorder from the database matching the given OID, otherwise null.
     */
    public List<ComponentOrder> listOrderByParentByAlphaOrder() throws Exception;
    
    /*
     * Returns the daocomponentorder from the database matching the given OID, otherwise null.
     */
    public List<ComponentOrder> listByChildIdAndParentID(String childId, String parentId) throws Exception;
    
    /*
     * Returns the daocomponentorders from the database matching the given OBO ID, otherwise null.
     */
    public List<ComponentOrder> listByChild(String child) throws Exception;
    
    /*
     * Returns the daocomponentorders from the database matching the given OBI Name, otherwise null.
     */
    public List<ComponentOrder> listByParent(String parent) throws Exception;
    
    /*
     * Returns a list of ALL componentorders, otherwise null.
     */
    public List<ComponentOrder> listAll() throws Exception;
    
    /*
     * Returns true if the given daocomponentorder OID exists in the database.
     */
    public boolean existOid(long oid) throws Exception;

    /*
     * Save the given daocomponentorder in the database.
     * 
     *  If the ComponentOrder OID is null, 
     *   then it will invoke "create(ComponentOrder)", 
     *   else it will invoke "update(ComponentOrder)".
     */
    public void save(ComponentOrder daocomponentorder) throws Exception;

    /*
     * Create the given daocomponentorder in the database. 
     *  The daocomponentorder OID must be null, otherwise it will throw IllegalArgumentException.
     *  If the daocomponentorder OID value is unknown, rather use save(ComponentOrder).
     *   After creating, the DAO will set the obtained ID in the given daocomponentorder.
     */
    public void create(ComponentOrder daocomponentorder) throws IllegalArgumentException, Exception;
    
    /*
     * Update the given daocomponentorder in the database.
     *  The daocomponentorder OID must not be null, otherwise it will throw IllegalArgumentException. 
     *  If the daocomponentorder OID value is unknown, rather use save(ComponentOrder)}.
     */
    public void update(ComponentOrder daocomponentorder) throws Exception;
     
    /*
     *  Delete the given daocomponentorder from the database. 
     *  After deleting, the DAO will set the ID of the given daocomponentorder to null.
     */
    public void delete(ComponentOrder daocomponentorder) throws Exception;
    
    /*
     *  Delete the given daocomponentorder from the database. 
     *  After deleting, the DAO will set the ID of the given daocomponentorder to null.
     */
    public void empty() throws Exception;
    
    /*
     * Returns list of ComponentOrders for Display purposes
     *  starting at the given first index with the given row count,
     *  sorted by the given sort field and sort order.
     */
    public List<ComponentOrder> display(int firstRow, int rowCount, String sortField, boolean sortAscending, String searchFirst, String searchSecond)
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
