/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayer
*
* Title:        PerspectiveDAO.java
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
* Description:  This interface represents a contract for a Data Access Object for the Perspective model.
*  
*               This Data Access Object should be used as a central point for the mapping between 
*                the Perspective DTO and a SQL database.
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

import daomodel.Perspective;

public interface PerspectiveDAO extends BaseDAO {

	// Actions ------------------------------------------------------------------------------------
    /*
     * Returns the perspective from the database matching the given OID, otherwise null.
     */
    public Perspective findByName(String name) throws Exception;

    /*
     * Returns a list of ALL perspectives, otherwise null.
     */
    public List<Perspective> listAll() throws Exception;
    
    /*
     * Create the given perspective in the database. 
     * The perspective OID must be null, otherwise it will throw IllegalArgumentException.
     * If the perspective OID value is unknown, rather use save(Perspective).
     * After creating, the Data Access Object will set the obtained ID in the given perspective.
     */
    public void create(Perspective perspective) throws IllegalArgumentException, Exception;
    
    /*
     * Update the given perspective in the database.
     *  The perspective OID must not be null, otherwise it will throw IllegalArgumentException. 
     *  If the perspective OID value is unknown, rather use save(Perspective)}.
     */
    public void update(Perspective perspective) throws Exception;
    
    /*
     * Delete the given perspective from the database. 
     *  After deleting, the Data Access Object will set the ID of the given perspective to null.
     */
    public void delete(Perspective perspective) throws Exception;
    
    /*
     * Returns true if the given perspective OID exists in the database.
     */
    public boolean existName(String name) throws Exception;
    
    /*
     * Returns list of Synonyms for Display purposes
     *  starting at the given first index with the given row count,
     *  sorted by the given sort field and sort order.
     */
    public List<Perspective> display(int firstRow, int rowCount, String sortField, boolean sortAscending, String searchTerm, String searchExtra)
        throws Exception;

    /*
     * Returns total amount of rows in table.
     */
    public int count(String searchTerm, String searchExtra) throws Exception;

}
