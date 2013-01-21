/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomy
*
* Title:        SourceDAO.java
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
* Description:  This class represents a SQL Database Access Object for the Source DTO.
*  
*               This DAO should be used as a central point for the mapping between 
*                the Source DTO and a SQL database.
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

import daomodel.Source;

public interface SourceDAO extends BaseDAO {
    // Actions ------------------------------------------------------------------------------------
    /*
     * Returns the source from the database matching the given OID, otherwise null.
     */
    public Source find(Long oid) throws Exception;

    /*
     * Returns a list of ALL sources, otherwise null.
     */
    public List<Source> listAll() throws Exception;
    
     /*
     * Create the given source in the database. 
     * The source OID must be null, otherwise it will throw IllegalArgumentException.
     * If the source OID value is unknown, rather use save(Source).
     * After creating, the DAO will set the obtained ID in the given source.
     */    
    public void create(Source source) throws IllegalArgumentException, Exception;
    
    /*
     * Update the given source in the database.
     *  The source OID must not be null, otherwise it will throw IllegalArgumentException. 
     *  If the source OID value is unknown, rather use save(Source)}.
     */
    public void update(Source source) throws Exception;
     
    /*
     * Delete the given source from the database. 
     *  After deleting, the DAO will set the ID of the given source to null.
     */
    public void delete(Source source) throws Exception;
    
    /*
     * Returns true if the given source OID exists in the database.
     */
    public boolean existOid(String oid) throws Exception;

    /*
     * Returns list of Synonyms for Display purposes
     *  starting at the given first index with the given row count,
     *  sorted by the given sort field and sort order.
     */
    public List<Source> display(int firstRow, int rowCount, String sortField, boolean sortAscending, String searchTerm, String searchExtra)
        throws Exception;

    /*
     * Returns total amount of rows in table.
     */
    public int count(String searchTerm, String searchExtra) throws Exception;

}
