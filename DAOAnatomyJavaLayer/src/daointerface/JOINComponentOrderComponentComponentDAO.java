/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayer
*
* Title:        ComponentOrderDAO.java
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
* Description:  This interface represents a contract for a Data Access Object for the ComponentOrder model.
*  
*               This Data Access Object should be used as a central point for the mapping between 
*                the ComponentOrder DTO and a SQL database.
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

import daomodel.JOINComponentOrderComponentComponent;

public interface JOINComponentOrderComponentComponentDAO extends BaseDAO {
    // Actions ------------------------------------------------------------------------------------
    /*
     * Returns the daocomponentorder from the database matching the given OID, otherwise null.
     */
    public List<JOINComponentOrderComponentComponent> listOrderByParentByAlphaOrder(String parentId) throws Exception;
    
}
