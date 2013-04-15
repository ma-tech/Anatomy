/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomy
*
* Title:        TimedLeafDAO.java
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
* Description:  This interface represents a contract for a DAO for the TimedLeaf model.
*  
*               This DAO should be used as a central point for the mapping between 
*                the Timed Leaf DTO and a SQL database.
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
import daomodel.TimedLeaf;

public interface TimedLeafDAO extends BaseDAO {
	// Actions ------------------------------------------------------------------------------------
	// LIST    ------------------------------------------------------------------------------------
	/*
	 * Returns a list of All Leafs for the given Root Name, otherwise null.
	 */
	public List<TimedLeaf> listAllTimedNodesByRootName(String rootName1, String stage1, String rootName2, String stage2) 
			throws Exception;

	/*
	 * Returns a list of All Leafs for the given Root Description, otherwise null.
	 */
	public List<TimedLeaf> listAllTimedNodesByRootDesc(String rootDesc1, String stage1, String rootDesc2, String stage2) 
			throws Exception;

	/*
	 * Returns a list of All Leafs for the given Root Name, otherwise null.
	 */
	public List<TimedLeaf> listAllTimedNodesByRootNameByChildDesc(String rootName1, String stage1, String rootName2, String stage2) 
			throws Exception;

	/*
	 * Returns a list of All Leafs for the given Root Description, otherwise null.
	 */
	public List<TimedLeaf> listAllTimedNodesByRootDescByChildDesc(String rootDesc1, String stage1, String rootDesc2, String stage2) 
			throws Exception;

	/*
	 * Convert the Leaf ResultSet to JSON for Ajax calls
	 */
	public String convertLeafListToStringJsonChildren(List<TimedLeaf> timedleafs);

	/*
	 * Convert the Leaf ResultSet to JSON for Ajax calls
	 */
	public String convertLeafListToStringJsonLines(List<TimedLeaf> timedleafs);

	/*
	 * Convert the Leaf ResultSet to JSON for Ajax calls
	 */
	public String convertLeafListToStringJsonAggregateOld(List<TimedLeaf> timedleafs);

	/*
	 * Convert the Leaf ResultSet to JSON for Ajax calls
	 */
	public String convertLeafListToStringJsonAggregate(List<TimedLeaf> timedleafs);
}
