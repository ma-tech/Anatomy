/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomy
*
* Title:        ComponentAlternativeDAO.java
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
* Description:  This interface represents a contract for a DAO for the ComponentAlternative model.
*  
*               This DAO should be used as a central point for the mapping between 
*                the ComponentAlternative DTO and a SQL database.
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

import daomodel.ComponentAlternative;


public interface ComponentAlternativeDAO extends BaseDAO {

	// Actions ------------------------------------------------------------------------------------
    /*
     * Returns the daocomponentalternative from the database matching the given OID, otherwise null.
     */
    public ComponentAlternative findByOid(long oid) throws Exception;
    
    /*
     * Returns the daocomponentalternative from the database matching the given OBO ID for an AEO Alternative, otherwise null.
     */
    public ComponentAlternative findByOboIdAEO(String oboid) throws Exception;
    
    /*
     * Returns the daocomponentalternative from the database matching the given OBO ID for an UBERON Alternative, otherwise null.
     */
    public ComponentAlternative findByOboIdUBERON(String oboid) throws Exception;
    
    /*
     * Returns the daocomponentalternative from the database matching the given OBO ID for an CARO Alternative, otherwise null.
     */
    public ComponentAlternative findByOboIdCARO(String oboid) throws Exception;
    
    /*
     * Returns the daocomponentalternatives from the database matching the given OBO ID, otherwise null.
     */
    public List<ComponentAlternative> listByOboId(String oboid) throws Exception;
    
    /*
     * Returns the daocomponentalternatives from the database matching the given OBI Name, otherwise null.
     */
    public List<ComponentAlternative> listByAltId(String text) throws Exception;
    
    /*
     * Returns a list of ALL componentsynonyms, otherwise null.
     */
    public List<ComponentAlternative> listAll() throws Exception;
    
    /*
     * Returns true if the given daocomponentalternative OID exists in the database.
     */
    public boolean existOid(long oid) throws Exception;

    /*
     * Save the given daocomponentalternative in the database.
     * 
     *  If the ComponentAlternative OID is null, 
     *   then it will invoke "create(ComponentAlternative)", 
     *   else it will invoke "update(ComponentAlternative)".
     */
    public void save(ComponentAlternative daocomponentalternative) throws Exception;

    /*
     * Create the given daocomponentalternative in the database. 
     * 
     *  The daocomponentalternative OID must be null, otherwise it will throw IllegalArgumentException.
     *  If the daocomponentalternative OID value is unknown, rather use save(ComponentAlternative).
     *   After creating, the DAO will set the obtained ID in the given daocomponentalternative.
     */
    public void create(ComponentAlternative daocomponentalternative) throws IllegalArgumentException, Exception;
    
    /*
     * Update the given daocomponentalternative in the database.
     *  The daocomponentalternative OID must not be null, otherwise it will throw IllegalArgumentException. 
     *  If the daocomponentalternative OID value is unknown, rather use save(ComponentAlternative)}.
     */
    public void update(ComponentAlternative daocomponentalternative) throws Exception;
     
    /*
     *  Delete the given daocomponentalternative from the database. 
     *  After deleting, the DAO will set the ID of the given daocomponentalternative to null.
     */
    public void delete(ComponentAlternative daocomponentalternative) throws Exception;
    
    /*
     *  Delete the given daocomponentalternative from the database. 
     *  After deleting, the DAO will set the ID of the given daocomponentalternative to null.
     */
    public void empty() throws Exception;

    /*
     * Returns list of ComponentAlternatives for Display purposes
     *  starting at the given first index with the given row count,
     *  sorted by the given sort field and sort order.
     */
    public List<ComponentAlternative> display(int firstRow, int rowCount, String sortField, boolean sortAscending, String searchFirst, String searchSecond)
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
