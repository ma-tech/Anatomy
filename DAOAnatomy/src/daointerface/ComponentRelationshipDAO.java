/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomy
*
* Title:        ComponentRelationshipDAO.java
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
* Description:  This interface represents a contract for a DAO for the ComponentRelationship model.
*  
*               This DAO should be used as a central point for the mapping between 
*                the ComponentRelationship DTO and a SQL database.
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

import daomodel.ComponentRelationship;

public interface ComponentRelationshipDAO extends BaseDAO {
    // Actions ------------------------------------------------------------------------------------
    /*
     * Returns the daocomponentrelationship from the database matching the given OID, otherwise null.
     */
    public ComponentRelationship findByOid(Long oid) throws Exception;
    
    /*
     * Returns the daocomponentrelationships from the database matching the given OBO ID, otherwise null.
     */
    public List<ComponentRelationship> listByChild(String child) throws Exception;
    
    /*
     * Returns the daocomponentrelationships from the database matching the given OBI Name, otherwise null.
     */
    public List<ComponentRelationship> listByParent(String parent) throws Exception;
    
    /*
     * Returns a list of ALL componentrelationships, otherwise null.
     */
    public List<ComponentRelationship> listAll() throws Exception;
    
    /*
     * Returns the daocomponentrelationship from the database matching the given OID, otherwise null.
     */
    public List<ComponentRelationship> listAllAlphabeticWithinParentIdPartOF() throws Exception;
    
    /*
     * Returns the daocomponentrelationship from the database matching the given OID, otherwise null.
     */
    public List<ComponentRelationship> listAllAlphabeticWithinParentIdNotPartOf() throws Exception;
    
    /*
     * Returns true if the given daocomponentrelationship OID exists in the database.
     */
    public boolean existOid(String oid) throws Exception;

    /*
     * Save the given daocomponentrelationship in the database.
     * 
     *  If the ComponentRelationship OID is null, 
     *   then it will invoke "create(ComponentRelationship)", 
     *   else it will invoke "update(ComponentRelationship)".
     */
    public void save(ComponentRelationship daocomponentrelationship) throws Exception;

    /*
     * Create the given daocomponentrelationship in the database. 
     *  The daocomponentrelationship OID must be null, otherwise it will throw IllegalArgumentException.
     *  If the daocomponentrelationship OID value is unknown, rather use save(ComponentRelationship).
     *   After creating, the DAO will set the obtained ID in the given daocomponentrelationship.
     */
    public void create(ComponentRelationship daocomponentrelationship) throws IllegalArgumentException, Exception;
    
    /*
     * Update the given daocomponentrelationship in the database.
     *  The daocomponentrelationship OID must not be null, otherwise it will throw IllegalArgumentException. 
     *  If the daocomponentrelationship OID value is unknown, rather use save(ComponentRelationship)}.
     */
    public void update(ComponentRelationship daocomponentrelationship) throws Exception;
     
    /*
     *  Delete the given daocomponentrelationship from the database. 
     *  After deleting, the DAO will set the ID of the given daocomponentrelationship to null.
     */
    public void delete(ComponentRelationship daocomponentrelationship) throws Exception;
    
    /*
     *  Delete the given daocomponentrelationship from the database. 
     *  After deleting, the DAO will set the ID of the given daocomponentrelationship to null.
     */
    public void empty() throws Exception;
    
    /*
     * Returns list of ComponentRelationships for Display purposes
     *  starting at the given first index with the given row count,
     *  sorted by the given sort field and sort order.
     */
    public List<ComponentRelationship> display(int firstRow, int rowCount, String sortField, boolean sortAscending, String searchFirst, String searchSecond)
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
