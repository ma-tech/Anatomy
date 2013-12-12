/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomy
*
* Title:        JOINTimedNodeStageDAO.java
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
* Description:  This interface represents a contract for a DAO for the JOINTimedNodeStage model.
*  
*               This DAO should be used as a central point for the mapping between 
*                the JOINTimedNodeStage DTO and a SQL database.
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
import daomodel.JOINTimedNodeStage;

public interface JOINTimedNodeStageDAO extends BaseDAO {
    // Actions ------------------------------------------------------------------------------------
    /*
     * Returns a list of ALL jointimednodestages, otherwise null.
     */
    public List<JOINTimedNodeStage> listAll() throws Exception;
    
    /*
     * Returns a list of ALL jointimednodestages by Node Fk, Ordered by Stage Name, otherwise null.
     */
    public List<JOINTimedNodeStage> listAllByNodeFkOrderByStageName(long nodeFk) throws Exception;
    
    /*
     * Returns a list of ALL jointimednodestages by Node Fk, Ordered by Stage Sequence, otherwise null.
     */
    public List<JOINTimedNodeStage> listAllByNodeFkOrderByStageSequence(long nodeFk) throws Exception;
    
    /*
     * Returns a list of ALL jointimednodestages by Node Fk and Stage Name, otherwise null.
     */
    public List<JOINTimedNodeStage> listAllByNodeFkAndStageName(long nodeFk, String stageName) throws Exception;
    
    /*
     * Returns a list of ALL jointimednodestages by Node Fk and Stage Name, otherwise null.
     */
    public List<JOINTimedNodeStage> listAllByNodeFkAndStageSequence(long nodeFk, long stageSequence) throws Exception;
    
    /*
     * Returns a list of ALL jointimednodestages by node Fk and Sequence, otherwise null.
     */
    public List<JOINTimedNodeStage> listAllByNodeFkAndSequence(long nodeFk, String sequence) throws Exception;

    /*
     * Returns a count of ALL jointimednodestages by node FK, otherwise null.
     */
    public int countAllByNodeFk(long nodeFk) throws Exception;
    
    /*
     * Returns a count of ALL jointimednodestages by node FK, otherwise null.
     */
    public int maxSequenceByNodeFk(long nodeFk) throws Exception;
    
    /*
     * Returns a count of ALL jointimednodestages by node FK, otherwise null.
     */
    public int minSequenceByNodeFk(long nodeFk) throws Exception;
    
    /*
     * Returns total amount of rows in table.
     */
    public int count(String sql, long key) throws Exception;
    
    /*
     * Returns total amount of rows in table.
     */
    public int maxMin(String sql, long key) throws Exception;
    
}
