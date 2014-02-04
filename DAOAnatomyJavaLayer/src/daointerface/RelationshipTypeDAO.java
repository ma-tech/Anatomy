/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayer
*
* Title:        RelationshipTypeDAO.java
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
* Description:  This interface represents a contract for a Data Access Object for the RelationshipType model.
*  
*               This Data Access Object should be used as a central point for the mapping between 
*                the RelationshipType DTO and a SQL database.
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

import daomodel.RelationshipType;

public interface RelationshipTypeDAO extends BaseDAO {

    // Actions ------------------------------------------------------------------------------------
    /*
     * Returns the RelationshipType from the database matching the given OID, otherwise null.
     */
    public RelationshipType findByName(String name) throws Exception;
    
    /*
     * Returns a list of ALL relationshiptypes, otherwise null.
     */
    public List<RelationshipType> listAll() throws Exception;
    
    /*
     * Returns true if the given relationshiptype OID exists in the database.
     */
    public boolean existName(String Name) throws Exception;

    /*
     * Save the given relationshiptype in the database.
     * 
     *  If the RelationshipType OID is null, 
     *   then it will invoke "create(RelationshipType)", 
     *   else it will invoke "update(RelationshipType)".
     */
    public void save(RelationshipType relationshiptype) throws Exception;
    
     /*
     * Create the given relationshiptype in the database. 
     *  The relationshiptype OID must be null, otherwise it will throw IllegalArgumentException.
     *  If the relationshiptype OID value is unknown, rather use save(RelationshipType).
     *   After creating, the Data Access Object will set the obtained ID in the given relationshiptype.
     */
    public void create(RelationshipType relationshiptype) throws IllegalArgumentException, Exception;
    
    /*
     * Update the given relationshiptype in the database.
     * 
     *  The relationshiptype OID must not be null, otherwise it will throw IllegalArgumentException. 
     *  If the relationshiptype OID value is unknown, rather use save(RelationshipType)}.
     */
    public void update(RelationshipType relationshiptype) throws Exception;
     
    /*
     * Delete the given relationshiptype from the database. 
     * 
     *  After deleting, the Data Access Object will set the ID of the given relationshiptype to null.
     */
    public void delete(RelationshipType relationshiptype) throws Exception;
    
     /*
     * Returns list of RelationshipTypes for Display purposes
     *  starting at the given first index with the given row count,
     *  sorted by the given sort field and sort order.
     */
    public List<RelationshipType> display(int firstRow, int rowCount, String sortField, boolean sortAscending, String searchFirst, String searchSecond)
        throws Exception;
    
    /*
     * Returns total amount of rows in table.
     */
    public int count(String searchFirst, String searchSecond) throws Exception;

}
