/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomy
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
* Version: 1
*
* Description:  This interface represents a contract for a DAO for the Project model.
*  
*               This DAO should be used as a central point for the mapping between 
*                the Project DTO and a SQL database.
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
import daomodel.Project;

public interface ProjectDAO extends BaseDAO {

    // Actions ------------------------------------------------------------------------------------
    /*
     * Returns a list of ALL versions, otherwise null.
     */
    public List<Project> listAll() throws Exception;
    
}
