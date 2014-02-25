/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayer
*
* Title:        RelationshipFKDAO.java
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
* Description:  This interface represents a contract for a Data Access Object for the RelationshipFK model.
*  
*               This Data Access Object should be used as a central point for the mapping between 
*                the RelationshipFK DTO and a SQL database.
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

import daomodel.RelationshipFK;

public interface RelationshipFKDAO extends BaseDAO {

    // Actions ------------------------------------------------------------------------------------
    /*
     * Returns the maximum Oid.
     */
    public long maximumOid() throws Exception;
    
    /*
     * Returns the RelationshipFK from the database matching the given OID, otherwise null.
     */
    public RelationshipFK findByOid(long oid) throws Exception;
    
    /*
     * Returns a list of ALL relationshipfks by Parent FK, otherwise null.
     */
    public List<RelationshipFK> listByParentFK(long parentFK) throws Exception;
    
    /*
     * Returns a list of ALL relationshipfks by Child FK, otherwise null.
     */
    public List<RelationshipFK> listByChildFK(long childFK) throws Exception;
    
    /*
     * Returns a list of ALL relationshipfks by Parent FK AND Child FK, otherwise null.
     */
    public List<RelationshipFK> listByParentFKAndChildFK(long parentFK, long childFK) throws Exception;
    
    /*
     * Returns a list of ALL relationshipfks by RelationshipFK Type FK, otherwise null.
     */
    public List<RelationshipFK> listByRelationshipFKTypeFK(String relationshipfkTypeFK) throws Exception;
    
    /*
     * Returns a list of ALL relationshipfks, otherwise null.
     */
    public List<RelationshipFK> listAll() throws Exception;
    
    /*
     * Returns true if the given relationshipfk OID exists in the database.
     */
    public boolean existOid(long oid) throws Exception;

     /*
     * Returns list of RelationshipFKs for Display purposes
     *  starting at the given first index with the given row count,
     *  sorted by the given sort field and sort order.
     */
    public List<RelationshipFK> display(int firstRow, int rowCount, String sortField, boolean sortAscending, String searchFirst, String searchSecond)
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
