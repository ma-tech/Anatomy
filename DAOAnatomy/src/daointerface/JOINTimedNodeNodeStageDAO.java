/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomy
*
* Title:        JOINTimedNodeNodeStageDAO.java
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
* Description:  This interface represents a contract for a DAO for the 
*                JOINTimedNodeNodeStage model.
*  
*               This DAO should be used as a central point for the mapping between 
*                the JOINTimedNodeNodeStage DTO and a SQL database.
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
import daomodel.JOINTimedNodeNodeStage;

public interface JOINTimedNodeNodeStageDAO extends BaseDAO {
    // Actions ------------------------------------------------------------------------------------
    /*
     * Returns the jointimednodenodestage from the database matching the EMAP ID, otherwise null.
     */
    public JOINTimedNodeNodeStage findByEmap(String emapId) throws Exception;

    /*
     * Returns a list of ALL jointimednodenodestages, otherwise null.
     */
    public List<JOINTimedNodeNodeStage> listAll() throws Exception;
    
    /*
     * Returns a list of ALL jointimednodenodestages, otherwise null.
     */
    public List<JOINTimedNodeNodeStage> listAllByStageName( String stage ) throws Exception;
    
}
