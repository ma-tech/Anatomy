/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomy
*
* Title:        RelationshipProjectDAO.java
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
* Description:  This interface represents a contract for a DAO for the RelationshipProject model.
*  
*               This DAO should be used as a central point for the mapping between 
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
import daomodel.RelationshipProject;

public interface RelationshipProjectDAO extends BaseDAO {

    // Actions ------------------------------------------------------------------------------------
    /*
     * Returns the maximum Oid.
     */
    public int maximumOid() throws Exception;
    
    /*
     * Returns the relationshipproject from the database matching the given OID, otherwise null.
     */
    public RelationshipProject findByOid(long oid) throws Exception;
    
    /*
     * Returns a list of ALL relationshipprojects by Parent FK, otherwise null.
     */
    public List<RelationshipProject> listByProjectFK(long projectFK) throws Exception;
    
    /*
     * Returns a list of ALL relationshipprojects by Sequence Number, otherwise null.
     */
    public List<RelationshipProject> listBySequence(long sequence) throws Exception;
    
    /*
     * Returns a list of ALL relationshipprojects by Relationship FK, otherwise null.
     */
    public List<RelationshipProject> listByRelationshipFK(long relationshipFK) throws Exception;
    
    /*
     * Returns a list of ALL relationshipprojects, otherwise null.
     */
    public List<RelationshipProject> listAll() throws Exception;
    
    /*
     * Returns true if the given relationshipproject OID exists in the database.
     */
    public boolean existOid(long oid) throws Exception;

    /*
     * Save the given relationshipproject in the database.
     * 
     *  If the RelationshipProject OID is null, 
     *   then it will invoke "create(RelationshipProject)", 
     *   else it will invoke "update(RelationshipProject)".
     */
    public void save(RelationshipProject relationshipproject) throws Exception;
    
    /*
     * Create the given relationshipproject in the database.
     *  
     *  The relationshipproject OID must be null, otherwise it will throw IllegalArgumentException.
     *  If the relationshipproject OID value is unknown, rather use save(RelationshipProject).
     *   After creating, the DAO will set the obtained ID in the given relationshipproject.
     */
    public void create(RelationshipProject relationshipproject) throws IllegalArgumentException, Exception;
    
    /*
     * Update the given relationshipproject in the database.
     * 
     *  The relationshipproject OID must not be null, otherwise it will throw IllegalArgumentException. 
     *  If the relationshipproject OID value is unknown, rather use save(RelationshipProject).
     */
    public void update(RelationshipProject relationshipproject) throws Exception;
    
    /*
     * Delete the given relationshipproject from the database. 
     * 
     *  After deleting, the DAO will set the ID of the given relationshipproject to null.
     */
    public void delete(RelationshipProject relationshipproject) throws Exception;
    
    /*
     * Returns list of RelationshipProjects for Display purposes
     *  starting at the given first index with the given row count,
     *  sorted by the given sort field and sort order.
     */
    public List<RelationshipProject> display(int firstRow, int rowCount, String sortField, boolean sortAscending, String searchFirst, String searchSecond)
        throws Exception;
    
    /*
     * Returns total amount of rows in table.
     */
    public int count(String searchFirst, String searchSecond) throws Exception;

    /*
     * Returns total amount of rows in table.
     */
    public int maximum(String sql) throws Exception;

}
