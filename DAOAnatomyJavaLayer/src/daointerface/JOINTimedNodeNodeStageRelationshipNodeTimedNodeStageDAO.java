/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayer
*
* Title:        JOINTimedNodeNodeStageRelationshipNodeTimedNodeStageDAO.java
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
*                JOINTimedNodeNodeStageRelationshipNodeTimedNodeStage model.
*  
*               This Data Access Object should be used as a central point for the mapping between 
*                the JOINTimedNodeNodeStageRelationshipNodeTimedNodeStage DTO and a SQL database.
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

import daomodel.JOINTimedNodeNodeStageRelationshipNodeTimedNodeStage;

public interface JOINTimedNodeNodeStageRelationshipNodeTimedNodeStageDAO extends BaseDAO {
    // Actions ------------------------------------------------------------------------------------
    /*
     * Returns a list of ALL JOINTimedNodeNodeStageRelationshipNodeTimedNodeStage, otherwise null.
     */
    public List<JOINTimedNodeNodeStageRelationshipNodeTimedNodeStage> listAll() 
    		throws Exception;
    
    /*
     * Returns a list of ALL JOINTimedNodeNodeStageRelationshipNodeTimedNodeStage by Node Fk, Ordered by Stage Name, otherwise null.
     */
    public List<JOINTimedNodeNodeStageRelationshipNodeTimedNodeStage> listAllByStageName(String stageName1, String stageName2, String parentTimedNode)
    		throws Exception;
    
    /*
     * Returns a list of ALL JOINTimedNodeNodeStageRelationshipNodeTimedNodeStage by Node Fk, Ordered by Stage Sequence, otherwise null.
     */
    public List<JOINTimedNodeNodeStageRelationshipNodeTimedNodeStage> listAllStageNameAndParent(String stageName1, String stageName2, String parentTimedNode) 
    		throws Exception;
    
}
