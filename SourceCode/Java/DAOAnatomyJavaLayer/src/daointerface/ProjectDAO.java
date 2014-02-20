/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayer
*
* Title:        ProjectDAO.java
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
* Description:  This interface represents a contract for a Data Access Object for the Project model.
*  
*               This Data Access Object should be used as a central point for the mapping between 
*                the Project DTO and a SQL database.
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

import daomodel.Project;

public interface ProjectDAO extends BaseDAO {

    // Actions ------------------------------------------------------------------------------------
    /*
     * Returns the project from the database matching the given OID, otherwise null.
     */
    public Project findByName(String name) throws Exception;

    /*
     * Returns a list of ALL projects, otherwise null.
     */
    public List<Project> listAll() throws Exception;
    
    /*
     * Create the given project in the database. 
     * The project OID must be null, otherwise it will throw IllegalArgumentException.
     * If the project OID value is unknown, rather use save(Project).
     * After creating, the Data Access Object will set the obtained ID in the given project.
     */
    public void create(Project project) throws IllegalArgumentException, Exception;
    
    /*
     * Update the given project in the database.
     *  The project OID must not be null, otherwise it will throw IllegalArgumentException. 
     *  If the project OID value is unknown, rather use save(Project)}.
     */
    public void update(Project project) throws Exception;
    
    /*
     * Delete the given project from the database. 
     *  After deleting, the Data Access Object will set the ID of the given project to null.
     */
    public void delete(Project project) throws Exception;
    
    /*
     * Returns list of Synonyms for Display purposes
     *  starting at the given first index with the given row count,
     *  sorted by the given sort field and sort order.
     */
    public List<Project> display(int firstRow, int rowCount, String sortField, boolean sortAscending, String searchTerm)
        throws Exception;

    /*
     * Returns total amount of rows in table.
     */
    public long count(String searchTerm) throws Exception;
}
