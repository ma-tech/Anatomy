/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomy
*
* Title:        ExtraTimedNodeDAO.java
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
* Description:  TThis interface represents a contract for a DAO for the ExtraTimedNode model.
*  
*               This DAO should be used as a central point for the mapping between 
*                the ExtraTimedNode DTO and a SQL database.
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

import daomodel.ExtraTimedNode;

public interface ExtraTimedNodeDAO extends BaseDAO {
    // Actions ------------------------------------------------------------------------------------
    /*
     * Returns the extratimednode from the database matching the given EMAPA Id and Stage Sequence, 
     *  otherwise null.
     */
    public ExtraTimedNode findByEmapaAndStage(String emapaId, Long stageSeq) throws Exception;

    /*
     * Returns the extratimednode from the database matching the EMAP ID, otherwise null.
     */
    public ExtraTimedNode findByEmap(String emapId) throws Exception;

    /*
     * Returns true if the given extratimednode EMAPA ID and Stage Seq exists in the database.
     */
    public boolean existEmapaIdAndStageSeq(String emapaId, Long stageSeq) throws Exception;

    /*
     * Returns true if the given extratimednode EMAP ID exists in the database.
     */
    public boolean existEmapId(String emapId) throws Exception;

    /*
     * Returns list of Nodes for Display purposes
     *  starting at the given first index with the given row count,
     *  sorted by the given sort field and sort order.
     */
    public List<ExtraTimedNode> display(int firstRow, int rowCount, String sortField, boolean sortAscending, String searchFirst, String searchSecond)
        throws Exception;
    
    /*
     * Returns total amount of rows in table.
     */
    public int count(String searchFirst, String searchSecond) throws Exception;

}