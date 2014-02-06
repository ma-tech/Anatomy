/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayer
*
* Title:        SourceFormatDAO.java
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
* Description:  This class represents a SQL Database Access Object for the SourceFormat DTO.
*  
*               This Data Access Object should be used as a central point for the mapping between 
*                the SourceFormat DTO and a SQL database.
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

import daomodel.SourceFormat;

public interface SourceFormatDAO extends BaseDAO {
    // Actions ------------------------------------------------------------------------------------
    /*
     * Returns the sourceformat from the database matching the given OID, otherwise null.
     */
    public SourceFormat findByName(String name) throws Exception;

    /*
     * Returns a list of ALL sourceformats, otherwise null.
     */
    public List<SourceFormat> listAll() throws Exception;
    
     /*
     * Create the given sourceformat in the database. 
     * The sourceformat OID must be null, otherwise it will throw IllegalArgumentException.
     * If the sourceformat OID value is unknown, rather use save(SourceFormat).
     * After creating, the Data Access Object will set the obtained ID in the given sourceformat.
     */    
    public void create(SourceFormat sourceformat) throws IllegalArgumentException, Exception;
    
    /*
     * Update the given sourceformat in the database.
     *  The sourceformat OID must not be null, otherwise it will throw IllegalArgumentException. 
     *  If the sourceformat OID value is unknown, rather use save(SourceFormat)}.
     */
    public void update(SourceFormat sourceformat) throws Exception;
     
    /*
     * Delete the given sourceformat from the database. 
     *  After deleting, the Data Access Object will set the ID of the given sourceformat to null.
     */
    public void delete(SourceFormat sourceformat) throws Exception;
    
    /*
     * Returns true if the given sourceformat OID exists in the database.
     */
    public boolean existName(String name) throws Exception;

    /*
     * Returns list of Synonyms for Display purposes
     *  starting at the given first index with the given row count,
     *  sorted by the given sort field and sort order.
     */
    public List<SourceFormat> display(int firstRow, int rowCount, String sortField, boolean sortAscending, String searchTerm, String searchExtra)
        throws Exception;

    /*
     * Returns total amount of rows in table.
     */
    public int count(String searchTerm, String searchExtra) throws Exception;

}
