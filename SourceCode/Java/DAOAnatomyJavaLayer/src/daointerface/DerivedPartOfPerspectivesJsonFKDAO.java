/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayer
*
* Title:        DerivedPartOfPerspectivesJsonFKDAO.java
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
* Description:  This interface represents a contract for a Data Access Object for the DerivedPartOfPerspectivesJsonFK model.
*  
*               This Data Access Object should be used as a central point for the mapping between 
*                the DerivedPartOfPerspectivesJsonFK DTO and a SQL database.
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

import daomodel.DerivedPartOfPerspectivesJsonFK;

public interface DerivedPartOfPerspectivesJsonFKDAO extends BaseDAO {
    // Actions ------------------------------------------------------------------------------------
    /*
     * Returns list of DerivedPartOfs for Display purposes
     *  starting at the given first index with the given row count,
     *  sorted by the given sort field and sort order.
     */
    public List<DerivedPartOfPerspectivesJsonFK> display(int firstRow, int rowCount, String sortField, boolean sortAscending, String searchTerm, String searchPerspective)
        throws Exception;

    /*
     * Returns total amount of rows in table.
     */
    public long count(String searchTerm, String searchPerspective) throws Exception;

}
