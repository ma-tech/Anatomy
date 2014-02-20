/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayer
*
* Title:        StageModifierDAO.java
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
* Description:  This class represents a SQL Database Access Object for the StageModifier DTO.
*  
*               This Data Access Object should be used as a central point for the mapping between 
*                the StageModifier DTO and a SQL database.
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

import daomodel.StageModifier;

public interface StageModifierDAO extends BaseDAO {
    // Actions ------------------------------------------------------------------------------------
    /*
     * Returns the stagemodifier from the database matching the given OID, otherwise null.
     */
    public StageModifier findByName(String name) throws Exception;

    /*
     * Returns a list of ALL stagemodifiers, otherwise null.
     */
    public List<StageModifier> listAll() throws Exception;
    
     /*
     * Create the given stagemodifier in the database. 
     * The stagemodifier OID must be null, otherwise it will throw IllegalArgumentException.
     * If the stagemodifier OID value is unknown, rather use save(StageModifier).
     * After creating, the Data Access Object will set the obtained ID in the given stagemodifier.
     */    
    public void create(StageModifier stagemodifier) throws IllegalArgumentException, Exception;
    
    /*
     * Update the given stagemodifier in the database.
     *  The stagemodifier OID must not be null, otherwise it will throw IllegalArgumentException. 
     *  If the stagemodifier OID value is unknown, rather use save(StageModifier)}.
     */
    public void update(StageModifier stagemodifier) throws Exception;
     
    /*
     * Delete the given stagemodifier from the database. 
     *  After deleting, the Data Access Object will set the ID of the given stagemodifier to null.
     */
    public void delete(StageModifier stagemodifier) throws Exception;
    
    /*
     * Returns true if the given stagemodifier OID exists in the database.
     */
    public boolean existName(String name) throws Exception;

    /*
     * Returns list of Synonyms for Display purposes
     *  starting at the given first index with the given row count,
     *  sorted by the given sort field and sort order.
     */
    public List<StageModifier> display(int firstRow, int rowCount, String sortField, boolean sortAscending, String searchTerm, String searchExtra)
        throws Exception;

    /*
     * Returns total amount of rows in table.
     */
    public long count(String searchTerm, String searchExtra) throws Exception;

}
