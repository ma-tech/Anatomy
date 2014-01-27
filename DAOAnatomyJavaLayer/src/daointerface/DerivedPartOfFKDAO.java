/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomy
*
* Title:        DerivedPartOfDAO.java
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
* Description:  This interface represents a contract for a DAO for the DerivedPartOf model.
*  
*               This DAO should be used as a central point for the mapping between 
*                the DerivedPartOf DTO and a SQL database.
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
import daomodel.DerivedPartOfFK;

public interface DerivedPartOfFKDAO extends BaseDAO {
    // Actions ------------------------------------------------------------------------------------
    /*
     * Returns the relationship from the database matching the given OID, otherwise null.
     */
    public DerivedPartOfFK findByOid(long oid) throws Exception;

    /*
     * Returns a list of relationship matching the given Node FK, otherwise null.
     */
    public List<DerivedPartOfFK> listAllByNodeFK(String nodeFK) throws Exception;
    
    
    /*
     * Returns true if the given relationship OID exists in the database.
     */
    public boolean existOid(long oid) throws Exception;

    /*
     * Returns list of DerivedPartOfs for Display purposes
     *  starting at the given first index with the given row count,
     *  sorted by the given sort field and sort order.
     */
    public List<DerivedPartOfFK> display(int firstRow, int rowCount, String sortField, boolean sortAscending, String searchTerm)
        throws Exception;

    /*
     * Returns total amount of rows in table.
     */
    public int count(String searchTerm) throws Exception;

}
