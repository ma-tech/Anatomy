/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayer
*
* Title:        EvidenceDAO.java
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
* Description:  This interface represents a contract for a Data Access Object for the Evidence model.
*  
*               This Data Access Object should be used as a central point for the mapping between 
*                the Evidence DTO and a SQL database.
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

import daomodel.Evidence;

public interface EvidenceDAO extends BaseDAO {
    // Actions ------------------------------------------------------------------------------------
    /*
     * Returns the evidence from the database matching the given OID, otherwise null.
     */
    public Evidence findByName(String name) throws Exception;

    /*
     * Returns a list of ALL evidences, otherwise null.
     */
    public List<Evidence> listAll() throws Exception;
    
    /*
     * Create the given evidence in the database. 
     * The evidence OID must be null, otherwise it will throw IllegalArgumentException.
     * If the evidence OID value is unknown, rather use save(Evidence).
     * After creating, the Data Access Object will set the obtained ID in the given evidence.
     */
    public void create(Evidence evidence) throws IllegalArgumentException, Exception;
    
    /*
     * Update the given evidence in the database.
     *  The evidence OID must not be null, otherwise it will throw IllegalArgumentException. 
     *  If the evidence OID value is unknown, rather use save(Evidence)}.
     */
    public void update(Evidence evidence) throws Exception;
    
    /*
     * Delete the given evidence from the database. 
     *  After deleting, the Data Access Object will set the ID of the given evidence to null.
     */
    public void delete(Evidence evidence) throws Exception;
    
    /*
     * Returns list of Synonyms for Display purposes
     *  starting at the given first index with the given row count,
     *  sorted by the given sort field and sort order.
     */
    public List<Evidence> display(int firstRow, int rowCount, String sortField, boolean sortAscending, String searchTerm)
        throws Exception;

    /*
     * Returns total amount of rows in table.
     */
    public long count(String searchTerm) throws Exception;

}
