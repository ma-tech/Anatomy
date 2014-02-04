/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayer
*
* Title:        StageRangeDAO.java
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
* Description:  This interface represents a contract for a Data Access Object for the Stage Range model.
*  
*               This Data Access Object should be used as a central point for the mapping between 
*                the Stage Range DTO and a SQL database.
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

import daomodel.StageRange;

public interface StageRangeDAO extends BaseDAO {

    // Actions ------------------------------------------------------------------------------------
    /*
     * Returns a list of ALL stages ranges by existing child and existing parent, otherwise null
     */
    public List<StageRange> listByExistingChildExistingParent() throws Exception;
    
    /*
     * Returns a list of ALL stages ranges by existing child and existing parent, otherwise null, PART_OFs ONLY
     */
    public List<StageRange> listByExistingChildPartOfExistingParent() throws Exception;
    
    /*
     * Returns a list of ALL stages ranges by existing child and proposed parent, otherwise null
     */
    public List<StageRange> listByExistingChildProposedParent() throws Exception;
    
    /*
     * Returns a list of ALL stages ranges by proposed child and proposed parent, otherwise null
     */
    public List<StageRange> listByProposedChildProposedParent() throws Exception;
    
    /*
     * Returns a list of ALL stages ranges by proposed child and existing parent, otherwise null
     */
    public List<StageRange> listByProposedChildExistingParent() throws Exception;
    
    /*
     * Returns a list of ALL stages ranges by existing child and existing parent from the 
     *  current database, otherwise null
     */
    public List<StageRange> listByExistingChildExistingParentDatabase() throws Exception;
    
    /*
     * Returns a count of ALL stages ranges by existing child and existing parent
     */
    public int countByExistingChildExistingParent() throws Exception;
    
    /*
     * Returns a count of ALL stages ranges by existing child and proposed parent
     */
    public int countByExistingChildProposedParent() throws Exception;
    
    /*
     * Returns a count of ALL stages ranges by proposed child and proposed parent
     */
    public int countByProposedChildProposedParent() throws Exception;
    
    /*
     * Returns a count of ALL stages ranges by proposed child and existing parent
     */
    public int countlistByProposedChildExistingParent() throws Exception;
    
    /*
     * Returns a count of ALL stages ranges by existing child and existing parent from the 
     *  current database
     */
    public int countlistByExistingChildExistingParentDatabase() throws Exception;
    

    /*
     * Returns a of the number of rows in query.
     */
    public int count(String sql) throws Exception;
 
}
