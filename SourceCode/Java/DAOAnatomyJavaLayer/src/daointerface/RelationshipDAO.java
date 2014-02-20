/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayer
*
* Title:        RelationshipDAO.java
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
* Description:  This interface represents a contract for a Data Access Object for the Relationship model.
*  
*               This Data Access Object should be used as a central point for the mapping between 
*                the Relationship DTO and a SQL database.
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

import daomodel.Relationship;

public interface RelationshipDAO extends BaseDAO {

    // Actions ------------------------------------------------------------------------------------
    /*
     * Returns the maximum Oid.
     */
    public long maximumOid() throws Exception;
    
    /*
     * Returns the Relationship from the database matching the given OID, otherwise null.
     */
    public Relationship findByOid(long oid) throws Exception;
    
    /*
     * Returns a list of ALL relationships by Parent FK, otherwise null.
     */
    public List<Relationship> listByParentFK(long parentFK) throws Exception;
    
    /*
     * Returns a list of ALL relationships by Child FK, otherwise null.
     */
    public List<Relationship> listByChildFK(long childFK) throws Exception;
    
    /*
     * Returns a list of ALL relationships by Parent FK AND Child FK, otherwise null.
     */
    public List<Relationship> listByParentFKAndChildFK(long parentFK, long childFK) throws Exception;
    
    /*
     * Returns a list of ALL relationships by Relationship Type FK, otherwise null.
     */
    public List<Relationship> listByRelationshipTypeFK(String relationshipTypeFK) throws Exception;
    
    /*
     * Returns a list of ALL relationships, otherwise null.
     */
    public List<Relationship> listAll() throws Exception;
    
    /*
     * Returns true if the given relationship OID exists in the database.
     */
    public boolean existOid(long oid) throws Exception;

    /*
     * Save the given relationship in the database.
     * 
     *  If the Relationship OID is null, 
     *   then it will invoke "create(Relationship)", 
     *   else it will invoke "update(Relationship)".
     */
    public void save(Relationship relationship) throws Exception;
    
     /*
     * Create the given relationship in the database. 
     *  The relationship OID must be null, otherwise it will throw IllegalArgumentException.
     *  If the relationship OID value is unknown, rather use save(Relationship).
     *   After creating, the Data Access Object will set the obtained ID in the given relationship.
     */
    public void create(Relationship relationship) throws IllegalArgumentException, Exception;
    
    /*
     * Update the given relationship in the database.
     * 
     *  The relationship OID must not be null, otherwise it will throw IllegalArgumentException. 
     *  If the relationship OID value is unknown, rather use save(Relationship)}.
     */
    public void update(Relationship relationship) throws Exception;
     
    /*
     * Delete the given relationship from the database. 
     * 
     *  After deleting, the Data Access Object will set the ID of the given relationship to null.
     */
    public void delete(Relationship relationship) throws Exception;
    
     /*
     * Returns list of Relationships for Display purposes
     *  starting at the given first index with the given row count,
     *  sorted by the given sort field and sort order.
     */
    public List<Relationship> display(int firstRow, int rowCount, String sortField, boolean sortAscending, String searchFirst, String searchSecond)
        throws Exception;
    
    /*
     * Returns total amount of rows in table.
     */
    public long count(String searchFirst, String searchSecond) throws Exception;

    /*
     * Returns total amount of rows in table.
     */
    public long maximum(String sql) throws Exception;
    
}
