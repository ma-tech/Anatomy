/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomy
*
* Title:        ComponentSynonymDAO.java
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
* Description:  This interface represents a contract for a DAO for the ComponentSynonym model.
*  
*               This DAO should be used as a central point for the mapping between 
*                the ComponentSynonym DTO and a SQL database.
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
import daomodel.ComponentSynonym;

public interface ComponentSynonymDAO extends BaseDAO {
    // Actions ------------------------------------------------------------------------------------
    /*
     * Returns the daocomponentsynonym from the database matching the given OID, otherwise null.
     */
    public ComponentSynonym findByOid(long oid) throws Exception;
    
    /*
     * Returns the daocomponentsynonyms from the database matching the given OBO ID, otherwise null.
     */
    public List<ComponentSynonym> listByOboId(String oboid) throws Exception;
    
    /*
     * Returns the daocomponentsynonyms from the database matching the given OBI Name, otherwise null.
     */
    public List<ComponentSynonym> listByText(String text) throws Exception;
    
    /*
     * Returns a list of ALL componentsynonyms, otherwise null.
     */
    public List<ComponentSynonym> listAll() throws Exception;
    
    /*
     * Returns true if the given daocomponentsynonym OID exists in the database.
     */
    public boolean existOid(long oid) throws Exception;

    /*
     * Save the given daocomponentsynonym in the database.
     * 
     *  If the ComponentSynonym OID is null, 
     *   then it will invoke "create(ComponentSynonym)", 
     *   else it will invoke "update(ComponentSynonym)".
     */
    public void save(ComponentSynonym daocomponentsynonym) throws Exception;

    /*
     * Create the given daocomponentsynonym in the database. 
     * 
     *  The daocomponentsynonym OID must be null, otherwise it will throw IllegalArgumentException.
     *  If the daocomponentsynonym OID value is unknown, rather use save(ComponentSynonym).
     *   After creating, the DAO will set the obtained ID in the given daocomponentsynonym.
     */
    public void create(ComponentSynonym daocomponentsynonym) throws IllegalArgumentException, Exception;
    
    /*
     * Update the given daocomponentsynonym in the database.
     *  The daocomponentsynonym OID must not be null, otherwise it will throw IllegalArgumentException. 
     *  If the daocomponentsynonym OID value is unknown, rather use save(ComponentSynonym)}.
     */
    public void update(ComponentSynonym daocomponentsynonym) throws Exception;
     
    /*
     *  Delete the given daocomponentsynonym from the database. 
     *  After deleting, the DAO will set the ID of the given daocomponentsynonym to null.
     */
    public void delete(ComponentSynonym daocomponentsynonym) throws Exception;
    
    /*
     *  Delete the given daocomponentsynonym from the database. 
     *  After deleting, the DAO will set the ID of the given daocomponentsynonym to null.
     */
    public void empty() throws Exception;
    
    /*
     * Returns list of ComponentSynonyms for Display purposes
     *  starting at the given first index with the given row count,
     *  sorted by the given sort field and sort order.
     */
    public List<ComponentSynonym> display(int firstRow, int rowCount, String sortField, boolean sortAscending, String searchFirst, String searchSecond)
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
