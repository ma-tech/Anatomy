/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomy
*
* Title:        JOINNodeRelationshipRelationshipProjectDAO.java
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
*                JOINNodeRelationshipRelationshipProject model.
*  
*               This DAO should be used as a central point for the mapping between 
*                the JOINNodeRelationshipRelationshipProject DTO and a SQL database.
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

import daointerface.BaseDAO;
import daomodel.JOINNodeRelationshipRelationshipProject;

public interface JOINNodeRelationshipRelationshipProjectDAO extends BaseDAO {
    // Actions ------------------------------------------------------------------------------------
    /*
     * Returns a list of ALL rows, otherwise null.
     */
    public List<JOINNodeRelationshipRelationshipProject> listAll() throws Exception;
    
    /*
     * Returns a list of ALL rows, otherwise null.
     */
    public List<JOINNodeRelationshipRelationshipProject> listAllByChildFK(long childFK) throws Exception;
    
    /*
     * Returns a list of ALL rows, otherwise null.
     */
    public List<JOINNodeRelationshipRelationshipProject> listAllByChildAndProject(long childFK, String project) throws Exception;
    
}
