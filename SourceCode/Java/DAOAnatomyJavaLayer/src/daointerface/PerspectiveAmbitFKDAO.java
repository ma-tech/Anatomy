/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayer
*
* Title:        PerspectiveAmbitFKDAO.java
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
* Description:  This interface represents a contract for a Data Access Object for the PerspectiveAmbitFK model.
*  
*               This Data Access Object should be used as a central point for the mapping between 
*                the PerspectiveAmbitFK DTO and a SQL database.
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

import daomodel.PerspectiveAmbitFK;

public interface PerspectiveAmbitFKDAO extends BaseDAO {

	// Actions ------------------------------------------------------------------------------------
    /*
     * Returns the perspectiveAmbitfk from the database matching the given OID, otherwise null.
     */
    public PerspectiveAmbitFK findByOid(long oid) throws Exception;

    /*
     * Returns a list of ALL perspectiveAmbitfks, otherwise null.
     */
    public List<PerspectiveAmbitFK> listAll() throws Exception;
    
    /*
     * Returns true if the given perspectiveAmbitfk OID exists in the database.
     */
    public boolean existOid(long oid) throws Exception;
    
    /*
     * Returns list of Synonyms for Display purposes
     *  starting at the given first index with the given row count,
     *  sorted by the given sort field and sort order.
     */
    public List<PerspectiveAmbitFK> display(int firstRow, int rowCount, String sortField, boolean sortAscending, String searchTerm, String searchExtra)
        throws Exception;

    /*
     * Returns total amount of rows in table.
     */
    public long count(String searchTerm, String searchExtra) throws Exception;

}
