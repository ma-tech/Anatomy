/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomy
*
* Title:        OBOFileDAO.java
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
* Description:  This interface represents a contract for a DAO for the OBOFile model.
*  
*               This DAO should be used as a central point for the mapping between 
*                the OBOFile DTO and a SQL database.
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

import daomodel.OBOFile;

public interface OBOFileDAO extends BaseDAO {

	// Actions ------------------------------------------------------------------------------------
    /*
     * Returns the obofile from the database matching the given OID, otherwise null.
     */
    public OBOFile find(Long oid) throws Exception;

    /*
     * Returns the obofile from the database matching the given OID, otherwise null.
     */
    public OBOFile findWithBinary(Long oid) throws Exception;

    /*
     * Returns a list of ALL files, otherwise null.
     */
    public List<OBOFile> listAll() throws Exception;
    
    /*
     * Returns a list of ALL files, otherwise null.
     */
    public List<OBOFile> listAllWithBinary() throws Exception;
    
    /*
     * Create the given OBOFile in the database. 
     *  The OBOFile OID must be null, otherwise it will throw IllegalArgumentException.
     *  If the OBOFile OID value is unknown, rather use #save(obofile).
     *  
     * After creating, the DAO will set the obtained ID in the given obofile.
     */
    public void create(OBOFile obofile) throws IllegalArgumentException, Exception;
    
    /*
     * Update the given obofile in the database.
     *  The obofile OID must not be null, otherwise it will throw IllegalArgumentException. 
     *  If the obofile OID value is unknown, rather use save(OBOFile).
     */
    public void update(OBOFile obofile) throws Exception;
     
    /*
     * Save the given obofile in the database.
     *  If the obofile OID is null, then 
     *   it will invoke create(OBOFile), else 
     *   it will invoke update(OBOFile).
     */
    public void save(OBOFile obofile) throws Exception;
    
    /*
     * Delete the given obofile from the database. 
     *  After deleting, the DAO will set the ID of the given obofile to null.
     */
    public void delete(OBOFile obofile) throws Exception;
    
    /*
     * Returns true if the given obofile OID exists in the database.
     */
    public boolean existOid(String oid) throws Exception;

    /*
     * Returns the obofile from the database matching the given OID, otherwise null.
     */
    public Long findMaxOid() throws Exception;

    /*
     * Returns list of OBOFiles for Display purposes
     *  starting at the given first index with the given row count,
     *  sorted by the given sort field and sort order.
     */

    public List<OBOFile> display(int firstRow, int rowCount, String sortField, boolean sortAscending, String searchTerm, String searchTable)
        throws Exception;

    /*
     * Returns total amount of rows in table.
     */
    public int count(String searchTerm, String searchTable) throws Exception;

}
