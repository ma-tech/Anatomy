/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayer
*
* Title:        ComponentDAO.java
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
* Description:  This interface represents a contract for a Data Access Object for the Component model.
*  
*               This Data Access Object should be used as a central point for the mapping between 
*                the Component DTO and a SQL database.
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

import daomodel.Component;

public interface ComponentDAO extends BaseDAO {

    // Actions ------------------------------------------------------------------------------------
    /*
     * Returns the daocomponent from the database matching the given OID, otherwise null.
     */
    public Component findByOid(long oid) throws Exception;
    
    /*
     * Returns the daocomponent from the database matching the given OBO ID, otherwise null.
     */
    public Component findByOboId(String oboid) throws Exception;
    
    /*
     * Returns the daocomponent from the database matching the given OBO Name, otherwise null.
     */
    public Component findByOboName(String oboname) throws Exception;
    
    /*
     * Returns a list of ALL components, otherwise null.
     */
    public List<Component> listAll() throws Exception;
    
    /*
     * Returns a list of ALL components, ordered by EMAPA Id, otherwise null.
     */
    public List<Component> listAllOrderByEMAPA() throws Exception;
    
    /*
     * Returns true if the given daocomponent OID exists in the database.
     */
    public boolean existOid(long oid) throws Exception;

    /*
     * Save the given daocomponent in the database.
     * 
     *  If the Component OID is null, 
     *   then it will invoke "create(Component)", 
     *   else it will invoke "update(Component)".
     */
    public void save(Component daocomponent) throws Exception;

    /*
     * Create the given daocomponent in the database.
     *  
     *  The daocomponent OID must be null, otherwise it will throw IllegalArgumentException.
     * 
     *  If the daocomponent OID value is unknown, rather use save(Component).
     *  
     * After creating, the Data Access Object will set the obtained ID in the given daocomponent.
     */
    public void create(Component daocomponent) throws IllegalArgumentException, Exception;
    
    /*
     * Update the given daocomponent in the database.
     * 
     *  The daocomponent OID must not be null, otherwise it will throw IllegalArgumentException. 
     *  
     *  If the daocomponent OID value is unknown, rather use save(Component)}.
     */
    public void update(Component daocomponent) throws Exception;
     
    /*
     *  Delete the given daocomponent from the database. 
     *  
     *  After deleting, the Data Access Object will set the ID of the given daocomponent to null.
     */
    public void delete(Component daocomponent) throws Exception;
    
    /*
     *  Delete the all daocomponents from the database.
     *   
     *  After deleting, the Data Access Object will set the ID of the given daocomponent to null.
     */
    public void empty() throws Exception;
    
    /*
     * Returns list of Components for Display purposes
     *  starting at the given first index with the given row count,
     *  sorted by the given sort field and sort order.
     */
    public List<Component> display(int firstRow, int rowCount, String sortField, boolean sortAscending, String searchFirst, String searchSecond)
        throws Exception;
    
    /*
     * Returns total amount of rows in table
     *  for the given search terms
     */
    public long count(String searchFirst, String searchSecond) throws Exception;

    /*
     * Returns total amount of rows in table.
     */
    public long countAll() throws Exception;

    /*
     * Returns the maximum Oid.
     */
    public long maximumOid() throws Exception;
    
}
