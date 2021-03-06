/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayer
*
* Title:        JOINNodeRelationshipNodeDAO.java
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
* Description:  This interface represents a contract for a Data Access Object for the 
*                JOINNodeRelationshipNode model.
*  
*               This Data Access Object should be used as a central point for the mapping between 
*                the JOINNodeRelationshipNode DTO and a SQL database.
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

import daomodel.JOINNodeRelationshipNode;

public interface JOINNodeRelationshipNodeDAO extends BaseDAO {
    // Actions ------------------------------------------------------------------------------------
    /*
     * Returns a list of ALL rows, otherwise null.
     */
    public List<JOINNodeRelationshipNode> listAll() throws Exception;

    /*
     * Returns a list of ALL PART_OF rows, otherwise null.
     */
    public List<JOINNodeRelationshipNode> listAllPartOfs() throws Exception;

    /*
     * Returns a list of ALL rows, otherwise null.
     */
    public List<JOINNodeRelationshipNode> listAllByParentId(String parentId) throws Exception;
    
    /*
     * Returns a list of ALL rows, otherwise null.
     */
    public List<JOINNodeRelationshipNode> listAllByChildIdAndParentId(String childId, String parentId) throws Exception;

    /*
     * Returns a list of ALL IS_A rows, otherwise null.
     */
	public List<JOINNodeRelationshipNode> listAllIsAsByChild(String childId) throws Exception;
    
}
