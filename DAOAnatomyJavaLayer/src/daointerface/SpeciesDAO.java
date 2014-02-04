/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayer
*
* Title:        SpeciesDAO.java
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
* Description:  This interface represents a contract for a Data Access Object for the Species model.
*  
*               This Data Access Object should be used as a central point for the mapping between 
*                the Species DTO and a SQL database.
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

import daomodel.Species;

public interface SpeciesDAO extends BaseDAO {

    // Actions ------------------------------------------------------------------------------------
    /*
     * Returns the Species from the database matching the given OID, otherwise null.
     */
    public Species findByName(String name) throws Exception;
    
    /*
     * Returns a list of ALL speciess, otherwise null.
     */
    public List<Species> listAll() throws Exception;
    
    /*
     * Returns true if the given species OID exists in the database.
     */
    public boolean existName(String Name) throws Exception;

    /*
     * Save the given species in the database.
     * 
     *  If the Species OID is null, 
     *   then it will invoke "create(Species)", 
     *   else it will invoke "update(Species)".
     */
    public void save(Species species) throws Exception;
    
     /*
     * Create the given species in the database. 
     *  The species OID must be null, otherwise it will throw IllegalArgumentException.
     *  If the species OID value is unknown, rather use save(Species).
     *   After creating, the Data Access Object will set the obtained ID in the given species.
     */
    public void create(Species species) throws IllegalArgumentException, Exception;
    
    /*
     * Update the given species in the database.
     * 
     *  The species OID must not be null, otherwise it will throw IllegalArgumentException. 
     *  If the species OID value is unknown, rather use save(Species)}.
     */
    public void update(Species species) throws Exception;
     
    /*
     * Delete the given species from the database. 
     * 
     *  After deleting, the Data Access Object will set the ID of the given species to null.
     */
    public void delete(Species species) throws Exception;
    
     /*
     * Returns list of Speciess for Display purposes
     *  starting at the given first index with the given row count,
     *  sorted by the given sort field and sort order.
     */
    public List<Species> display(int firstRow, int rowCount, String sortField, boolean sortAscending, String searchFirst, String searchSecond)
        throws Exception;
    
    /*
     * Returns total amount of rows in table.
     */
    public int count(String searchFirst, String searchSecond) throws Exception;

}
