/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayer
*
* Title:        DerivedRelationshipTransitiveDAO.java
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
* Description:  This interface represents a contract for a Data Access Object for the DerivedRelationshipTransitive model.
*  
*               This Data Access Object should be used as a central point for the mapping between 
*                the DerivedRelationshipTransitive DTO and a SQL database.
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

import daomodel.DerivedRelationshipTransitive;


public interface DerivedRelationshipTransitiveDAO extends BaseDAO {
    // Actions ------------------------------------------------------------------------------------
	
    /*
     * Returns the DerivedRelationshipTransitive from the database matching the given OID, otherwise null.
     */
    public DerivedRelationshipTransitive findByOid(long oid) throws Exception;
    
    
    /*
     * Returns a list of ALL DerivedRelationshipTransitive, otherwise null.
     */
    public List<DerivedRelationshipTransitive> listAll() throws Exception;
    
    
    /*
     * Returns a list of ALL DerivedRelationshipTransitive, otherwise null.
     */
    public List<DerivedRelationshipTransitive> listAllByAncestor(long ancestor) throws Exception;
    
    
    /*
     * Returns true if the given derivedrelationshiptransitive OID exists in the database.
     */
    public boolean existOid(long oid) throws Exception;

    
    /*
     * Returns true if the given derivedrelationshiptransitive OID exists in the database.
     */
    public boolean existAncestorDescendent(long ancestor, long descendent) throws Exception;

    
    /*
     * Save the given derivedrelationshiptransitive in the database.
     * 
     *  If the derivedrelationshiptransitive OID is null, 
     *   then it will invoke "create(derivedrelationshiptransitive)", 
     *   else it will invoke "update(derivedrelationshiptransitive)".
     */
    public void save(DerivedRelationshipTransitive derivedrelationshiptransitive) throws Exception;
    

    /*
     * Create the given derivedrelationshiptransitive in the database. 
     * 
     *  The derivedrelationshiptransitive OID must be null, otherwise it will throw IllegalArgumentException.
     *   If the derivedrelationshiptransitive OID value is unknown, rather use save(derivedrelationshiptransitive).
     *    After creating, the Data Access Object will set the obtained ID in the given derivedrelationshiptransitive.
     */
    public void create(DerivedRelationshipTransitive derivedrelationshiptransitive) throws IllegalArgumentException, Exception;
    
    
    /*
     * Update the given derivedrelationshiptransitive in the database.
     * 
     *  The derivedrelationshiptransitive OID must not be null, otherwise it will throw IllegalArgumentException. 
     *  If the derivedrelationshiptransitive OID value is unknown, rather use save(derivedrelationshiptransitive)}.
     */
    public void update(DerivedRelationshipTransitive derivedrelationshiptransitive) throws Exception;
     
    
    /*
     * Delete the given derivedrelationshiptransitive from the database. 
     * 
     *  After deleting, the Data Access Object will set the ID of the given derivedrelationshiptransitive to null.
     */
    public void delete(DerivedRelationshipTransitive derivedrelationshiptransitive) throws Exception;
    
    
    /*
     * Returns list of DerivedPartOfs for Display purposes
     *  starting at the given first index with the given row count,
     *  sorted by the given sort field and sort order.
     */
    public List<DerivedRelationshipTransitive> display(int firstRow, int rowCount, String sortField, boolean sortAscending, String searchFirst, String searchSecond)
        throws Exception;
    
    
    /*
     * Returns total amount of rows in table.
     */
    public long count(String searchFirst, String searchSecond) throws Exception;


    /*
     * Returns a list of relationship matching the given Node FK, otherwise null.
     */
    public List<DerivedRelationshipTransitive> listAllByDescendantFK(String descendantFK) throws Exception;
    
    
    /*
     * Returns list of DerivedRelationshipTransitives for Display purposes
     *  starting at the given first index with the given row count,
     *  sorted by the given sort field and sort order.
     */
    public List<DerivedRelationshipTransitive> display(int firstRow, int rowCount, String sortField, boolean sortAscending, String searchTerm)
        throws Exception;
    

    /*
     * Returns total amount of rows in table.
     */
    public long count(String searchTerm) throws Exception;


    /*
     * Returns total amount of rows in table.
     */
    public long countAll() throws Exception;


   /*
    *  Empty the DerivedRelationshipTransitive table.
    */
    public void empty() throws Exception;
   

}
