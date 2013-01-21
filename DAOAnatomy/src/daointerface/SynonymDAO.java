/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomy
*
* Title:        SynonymDAO.java
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
* Description:  This interface represents a contract for a DAO for the Synonym model.
*  
*               This DAO should be used as a central point for the mapping between 
*                the Synonym DTO and a SQL database.
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

import daomodel.Synonym;

public interface SynonymDAO extends BaseDAO {

    // Actions ------------------------------------------------------------------------------------
    /*
     * Returns the Synonym from the database matching the given OID, otherwise null.
     */
    public Synonym findByOid(Long oid) throws Exception;
    
    /*
     * Returns a list of ALL synonyms by Parent FK, otherwise null.
     */
    public List<Synonym> listByObjectFKAndSynonym(Long objectFK, String synonym) throws Exception;
    
    /*
     * Returns a list of ALL synonyms by Parent FK, otherwise null.
     */
    public List<Synonym> listByObjectFK(Long objectFK) throws Exception;
    
    /*
     * Returns a list of ALL synonyms, otherwise null.
     */
    public List<Synonym> listAll() throws Exception;
    
    /*
     * Returns true if the given synonym OID exists in the database.
     */
    public boolean existOid(Long oid) throws Exception;

    /*
     * Save the given synonym in the database.
     * 
     *  If the Synonym OID is null, 
     *   then it will invoke "create(Synonym)", 
     *   else it will invoke "update(Synonym)".
     */
    public void save(Synonym synonym) throws Exception;
    
     /*
     * Create the given synonym in the database.
     *  
     *  The synonym OID must be null, otherwise it will throw IllegalArgumentException.
     *  If the synonym OID value is unknown, rather use save(Synonym).
     *   After creating, the DAO will set the obtained ID in the given synonym.
     */
    public void create(Synonym synonym) throws IllegalArgumentException, Exception;
    
    /*
     * Update the given synonym in the database.
     * 
     *  The synonym OID must not be null, otherwise it will throw IllegalArgumentException. 
     *  If the synonym OID value is unknown, rather use save(Synonym)}.
     */
    public void update(Synonym synonym) throws Exception;
     
    /*
     * Delete the given synonym from the database. 
     *  After deleting, the DAO will set the ID of the given synonym to null.
     */
    public void delete(Synonym synonym) throws Exception;
     
    /*
     * Returns list of Synonyms for Display purposes
     *  starting at the given first index with the given row count,
     *  sorted by the given sort field and sort order.
     */
    public List<Synonym> display(int firstRow, int rowCount, String sortField, boolean sortAscending, String searchFirst, String searchSecond)
        throws Exception;
    
    /*
     * Returns total amount of rows in table.
     */
    public int count(String searchFirst, String searchSecond) throws Exception;

}
