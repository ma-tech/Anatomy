/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomy
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
* Version: 1
*
* Description:  This interface represents a contract for a DAO for the 
*                JOINNodeRelationshipNode model.
*  
*               This DAO should be used as a central point for the mapping between 
*                the JOINNodeRelationshipNode DTO and a SQL database.
*
* Link:         http://balusc.blogspot.com/2008/07/dao-tutorial-data-layer.html
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

import daomodel.JOINNodeRelationshipNode;

public interface JOINNodeRelationshipNodeDAO extends BaseDAO {
    // Actions ------------------------------------------------------------------------------------
    /*
     * Returns a list of ALL rows, otherwise null.
     */
    public List<JOINNodeRelationshipNode> listAll() throws Exception;

    /*
     * Returns a list of ALL rows, otherwise null.
     */
    public List<JOINNodeRelationshipNode> listAllByParentId(String parentId) throws Exception;
    
    /*
     * Returns a list of ALL rows, otherwise null.
     */
    public List<JOINNodeRelationshipNode> listAllByChildIdAndParentId(String childId, String parentId) throws Exception;
    
}
