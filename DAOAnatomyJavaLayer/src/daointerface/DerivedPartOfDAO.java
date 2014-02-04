/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayer
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
* Description:  This interface represents a contract for a Data Access Object for the DerivedPartOf model.
*  
*               This Data Access Object should be used as a central point for the mapping between 
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

import daomodel.DerivedPartOf;

public interface DerivedPartOfDAO extends BaseDAO {

    // Actions ------------------------------------------------------------------------------------
    /*
     * Returns the DerivedPartOf from the database matching the given OID, otherwise null.
     */
    public DerivedPartOf findByOid(long oid) throws Exception;
    
    /*
     * Returns a list of ALL derivedpartofs, otherwise null.
     */
    public List<DerivedPartOf> listAll() throws Exception;
    
    /*
     * Returns true if the given derivedpartof OID exists in the database.
     */
    public boolean existOid(long oid) throws Exception;

    /*
     * Save the given derivedpartof in the database.
     * 
     *  If the DerivedPartOf OID is null, 
     *   then it will invoke "create(DerivedPartOf)", 
     *   else it will invoke "update(DerivedPartOf)".
     */
    public void save(DerivedPartOf derivedpartof) throws Exception;
    
    /*
     * Returns a list of relationship matching the given Node FK, otherwise null.
     */
    public List<DerivedPartOf> listAllByNodeFK(String nodeFK) throws Exception;
    
    /*
     * Create the given derivedpartof in the database. 
     * 
     *  The derivedpartof OID must be null, otherwise it will throw IllegalArgumentException.
     *   If the derivedpartof OID value is unknown, rather use save(DerivedPartOf).
     *    After creating, the Data Access Object will set the obtained ID in the given derivedpartof.
     */
    public void create(DerivedPartOf derivedpartof) throws IllegalArgumentException, Exception;
    
    /*
     * Update the given derivedpartof in the database.
     * 
     *  The derivedpartof OID must not be null, otherwise it will throw IllegalArgumentException. 
     *  If the derivedpartof OID value is unknown, rather use save(DerivedPartOf)}.
     */
    public void update(DerivedPartOf derivedpartof) throws Exception;
     
    /*
     * Delete the given derivedpartof from the database. 
     * 
     *  After deleting, the Data Access Object will set the ID of the given derivedpartof to null.
     */
    public void delete(DerivedPartOf derivedpartof) throws Exception;
    
    /*
     * Returns list of DerivedPartOfs for Display purposes
     *  starting at the given first index with the given row count,
     *  sorted by the given sort field and sort order.
     */
    public List<DerivedPartOf> display(int firstRow, int rowCount, String sortField, boolean sortAscending, String searchFirst, String searchSecond)
        throws Exception;
    
    /*
     * Returns total amount of rows in table.
     */
    public int count(String searchFirst, String searchSecond) throws Exception;

}