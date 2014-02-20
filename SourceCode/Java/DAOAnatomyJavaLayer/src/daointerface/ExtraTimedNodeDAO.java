/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayer
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
* Version:      1
*
* Description:  TThis interface represents a contract for a Data Access Object for the ExtraTimedNode model.
*  
*               This Data Access Object should be used as a central point for the mapping between 
*                the ExtraTimedNode DTO and a SQL database.
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

import daomodel.ExtraTimedNode;

public interface ExtraTimedNodeDAO extends BaseDAO {
    // Actions ------------------------------------------------------------------------------------
    /*
     * Returns the extratimednode from the database matching the given Oid from TimedNode, 
     *  otherwise null.
     */
    public ExtraTimedNode findByOid(long Oid) throws Exception;

    /*
     * Returns the extratimednode from the database matching the given EMAPA Id and Stage Sequence, 
     *  otherwise null.
     */
    public ExtraTimedNode findByEmapaAndStage(String emapaId, long stageSeq) throws Exception;

    /*
     * Returns the extratimednode from the database matching the EMAP ID, otherwise null.
     */
    public ExtraTimedNode findByEmap(String emapId) throws Exception;

    /*
     * Returns true if the given extratimednode Oid exists in the database.
     */
    public boolean existOid(long Oid) throws Exception;

    /*
     * Returns true if the given extratimednode EMAPA ID and Stage Seq exists in the database.
     */
    public boolean existEmapaIdAndStageSeq(String emapaId, long stageSeq) throws Exception;

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
    public long count(String searchFirst, String searchSecond) throws Exception;

}