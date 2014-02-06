/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayer
*
* Title:        PerspectiveAmbitDAO.java
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
* Description:  This interface represents a contract for a Data Access Object for the PerspectiveAmbit model.
*  
*               This Data Access Object should be used as a central point for the mapping between 
*                the PerspectiveAmbit DTO and a SQL database.
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

import daomodel.PerspectiveAmbit;

public interface PerspectiveAmbitDAO extends BaseDAO {

	// Actions ------------------------------------------------------------------------------------
    /*
     * Returns the perspectiveAmbit from the database matching the given OID, otherwise null.
     */
    public PerspectiveAmbit findByOid(long oid) throws Exception;

    /*
     * Returns a list of ALL perspectiveAmbits, otherwise null.
     */
    public List<PerspectiveAmbit> listAll() throws Exception;
    
    /*
     * Create the given perspectiveAmbit in the database. 
     * The perspectiveAmbit OID must be null, otherwise it will throw IllegalArgumentException.
     * If the perspectiveAmbit OID value is unknown, rather use save(PerspectiveAmbit).
     * After creating, the Data Access Object will set the obtained ID in the given perspectiveAmbit.
     */
    public void create(PerspectiveAmbit perspectiveAmbit) throws IllegalArgumentException, Exception;
    
    /*
     * Update the given perspectiveAmbit in the database.
     *  The perspectiveAmbit OID must not be null, otherwise it will throw IllegalArgumentException. 
     *  If the perspectiveAmbit OID value is unknown, rather use save(PerspectiveAmbit)}.
     */
    public void update(PerspectiveAmbit perspectiveAmbit) throws Exception;
    
    /*
     * Delete the given perspectiveAmbit from the database. 
     *  After deleting, the Data Access Object will set the ID of the given perspectiveAmbit to null.
     */
    public void delete(PerspectiveAmbit perspectiveAmbit) throws Exception;
    
    /*
     * Returns true if the given perspectiveAmbit OID exists in the database.
     */
    public boolean existOid(long oid) throws Exception;
    
    /*
     * Returns list of Synonyms for Display purposes
     *  starting at the given first index with the given row count,
     *  sorted by the given sort field and sort order.
     */
    public List<PerspectiveAmbit> display(int firstRow, int rowCount, String sortField, boolean sortAscending, String searchTerm, String searchExtra)
        throws Exception;

    /*
     * Returns total amount of rows in table.
     */
    public int count(String searchTerm, String searchExtra) throws Exception;

}
