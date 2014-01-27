/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomy
*
* Title:        LeafDAO.java
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
* Description:  This interface represents a contract for a DAO for the Leaf model.
*  
*               This DAO should be used as a central point for the mapping between 
*                the Leaf DTO and a SQL database.
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
import daomodel.Leaf;

public interface LeafDAO extends BaseDAO {

    // Actions ------------------------------------------------------------------------------------
    // LIST    ------------------------------------------------------------------------------------
    /*
     * Returns a list of All Leafs for the given Root Name, otherwise null.
     */
    public List<Leaf> listAllNodesByRootName(String rootName1, String rootName2) throws Exception;
    
    /*
     * Returns a list of All Leafs for the given Root Name ordered by Child Desc, otherwise null.
     */
    public List<Leaf> listAllNodesByRootNameByChildDesc(String rootName1, String rootName2) throws Exception;
    
    /*
     * Returns a list of All Leafs for the given Root Description, otherwise null.
     */
    public List<Leaf> listAllNodesByRootDesc(String rootDesc1, String rootDesc2) throws Exception;
    
    /*
     * Returns a list of All Leafs for the given Root Description, otherwise null.
     */
    public List<Leaf> listAllNodesByRootDescByChildDesc(String rootDesc1, String rootDesc2) throws Exception;
    

    /*
     * Convert the Leaf ResultSet to JSON for Ajax calls
     */
    public String convertLeafListToStringJsonChildren(List<Leaf> leafs);
    
    /*
     * Convert the Leaf ResultSet to JSON for Ajax calls
     */
    public String convertLeafListToStringJsonLines(List<Leaf> leafs);

    /*
     * Convert the Leaf ResultSet to JSON for Ajax calls
     */
    public String convertLeafListToStringJsonAggregateOld(List<Leaf> leafs);
    
	/*
	 * Convert the Leaf ResultSet to JSON for Ajax calls
	 */
	public String convertLeafListToStringJsonAggregate(List<Leaf> leafs);
}
