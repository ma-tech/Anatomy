/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayer
*
* Title:        NodeDAO.java
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
* Description:  This interface represents a contract for a Data Access Object for the Node DTO.  
*  
*               This Data Access Object should be used as a central point for the mapping between 
*                the Node DTO and a SQL database.
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

import daomodel.Node;

public interface NodeDAO extends BaseDAO {

    // Actions ------------------------------------------------------------------------------------
    /*
     * Returns the maximum EMAPA id.
     */
    public int maximumEmapa() throws Exception; 
    
    /*
     * Returns the node from the database matching the given OID, otherwise null.
     */
    public Node findByOid(long oid) throws Exception;
    
    /*
     * Returns the node from the database matching the given publicId, otherwise null.
     */
    public Node findByPublicId(String publicId) throws Exception;
    
    /*
     * Returns the node from the database matching the given displayId, otherwise null.
     */
    public Node findByDisplayId(String displayId) throws Exception;
    
    /*
     * Returns the node from the database matching the given componentName, otherwise null.
     */
    public Node findByComponentName(String componentName) throws Exception;
    
    /*
     * Returns a list of ALL nodes, otherwise null.
     */
    public List<Node> listAll() throws Exception;
    
    /*
     * Returns a list of ALL timednodes, otherwise null.
     */
    public List<Node> listAllOrderByPublicId() throws Exception;
    
    /*
     * Returns a list of ALL timednodes, otherwise null.
     */
    public List<Node> listAllOrderByDisplayId() throws Exception;
    
    /*
     * Returns true if the given node OID exists in the database.
     */
    public boolean existOid(long oid) throws Exception;
    
    /*
     * Returns true if the given node publicId exists in the database.
     */
    public boolean existPublicId(String publicId) throws Exception;
    
    /*
     * Returns true if the given node publicId exists in the database.
     */
    public boolean existDisplayId(String displayId) throws Exception;
    
    /*
     * Save the given node in the database.
     * 
     *  If the Node OID is null, 
     *   then it will invoke "create(Node)", 
     *   else it will invoke "update(Node)".
     */
    public void save(Node node) throws Exception;
    
    /*
     * Returns a list of all nodes from the database. 
     *  The list is never null and is empty when the database does not contain any nodes.
     */
    public List<Node> list(String sql, Object... values) throws Exception;
        
    /*
     * Create the given node in the database.
     * 
     *  The node OID must be null, otherwise it will throw IllegalArgumentException.
     *   If the node OID value is unknown, rather use save(Node).
     *    After creating, the Data Access Object will set the obtained ID in the given node.
     */
    public void create(Node node) throws IllegalArgumentException, Exception;
    
    
    /*
     * Update the given node in the database.
     * 
     *  The node OID must not be null, otherwise it will throw IllegalArgumentException. 
     *  If the node OID value is unknown, rather use save(Node)}.
     */
    public void update(Node node) throws Exception;
    
    
    /*
     *  Delete the given node from the database. 
     *  
     *  After deleting, the Data Access Object will set the ID of the given node to null.
     */
    public void delete(Node node) throws Exception;
    
    
    /*
     * Returns list of Nodes for Display purposes
     *  starting at the given first index with the given row count,
     *  sorted by the given sort field and sort order.
     */
    public List<Node> display(int firstRow, int rowCount, String sortField, boolean sortAscending, String searchFirst, String searchSecond)
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
