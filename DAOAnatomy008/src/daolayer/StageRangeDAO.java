/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomy008
*
* Title:        StageRangeDAO.java
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
* Description:  This class represents a SQL Database Access Object for the Stage Range DTO.
*  
*               This DAO should be used as a central point for the mapping between 
*                the Stage Range DTO and a SQL database.
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

package daolayer;

import static daolayer.DAOUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import daomodel.StageRange;

public final class StageRangeDAO {

    // Constants ----------------------------------------------------------------------------------
    //Check Existing Child Stage Range is within Existing Parent Stage Range
    private static final String SQL_LIST_EXISTING_CHILD_EXISTING_PARENT =
        "SELECT " +
        "e.aoc_obo_id as CHILD_ID, a.stg_name as CHILD_START, b.stg_name as CHILD_END, " +
        "f.aoc_obo_id as PARENT_ID, c.stg_name as PARENT_START, d.stg_name as PARENT_END " +
        "FROM ANA_OBO_COMPONENT e " +
        "join ana_obo_component_relationship on acr_obo_id = e.aoc_obo_id " +
        "join ana_obo_component f on f.aoc_obo_id = acr_obo_parent " +
        "join ana_stage a on a.stg_name = e.aoc_start " +
        "join ana_stage b on b.stg_name = e.aoc_end " +
        "join ana_stage c on c.stg_name = f.aoc_start " +
        "join ana_stage d on d.stg_name = f.aoc_end " +
        "where substring(e.aoc_obo_id, 1, 3) <> 'TH:' " +
        "and substring(f.aoc_obo_id, 1, 3) <> 'TH:' " +
        "and (a.stg_sequence < c.stg_sequence " +
        "or b.stg_sequence > d.stg_sequence) " +
        "order by e.aoc_obo_id, f.aoc_obo_id";

    //Check Existing Child Stage Range is within Proposed Parent Stage Range
    private static final String SQL_LIST_EXISTING_CHILD_PROPOSED_PARENT =
        "SELECT " +
        "e.aoc_obo_id as CHILD_ID, a.stg_name as CHILD_START, b.stg_name as CHILD_END, " +
        "f.aoc_obo_id as PARENT_ID, c.stg_name as PARENT_START, d.stg_name as PARENT_END " +
        "FROM ANA_OBO_COMPONENT e " +
        "join ana_obo_component_relationship on acr_obo_id = e.aoc_obo_id " +
        "join ana_obo_component f on f.aoc_obo_id = acr_obo_parent " +
        "join ana_stage a on a.stg_name = e.aoc_start " +
        "join ana_stage b on b.stg_name = e.aoc_end " +
        "join ana_stage c on c.stg_name = f.aoc_start " +
        "join ana_stage d on d.stg_name = f.aoc_end " +
        "where substring(e.aoc_obo_id, 1, 3) <> 'TH:' " +
        "and substring(f.aoc_obo_id, 1, 3) = 'TH:' " +
        "and (a.stg_sequence < c.stg_sequence " +
        "or b.stg_sequence > d.stg_sequence) " +
        "order by e.aoc_obo_id, f.aoc_obo_id";
    
    //Check Proposed New Child Node Ranges are within Proposed Parent Stage Ranges
    private static final String SQL_LIST_PROPOSED_CHILD_PROPOSED_PARENT =
        "SELECT " +
        "e.aoc_obo_id as CHILD_ID, a.stg_name as CHILD_START, b.stg_name as CHILD_END, " +
        "f.aoc_obo_id as PARENT_ID, c.stg_name as PARENT_START, d.stg_name as PARENT_END " +
        "FROM ANA_OBO_COMPONENT e " +
        "join ana_obo_component_relationship on acr_obo_id = e.aoc_obo_id " +
        "join ana_obo_component f on f.aoc_obo_id = acr_obo_parent " +
        "join ana_stage a on a.stg_name = e.aoc_start " +
        "join ana_stage b on b.stg_name = e.aoc_end " +
        "join ana_stage c on c.stg_name = f.aoc_start " +
        "join ana_stage d on d.stg_name = f.aoc_end " +
        "where substring(e.aoc_obo_id, 1, 3) = 'TH:' " +
        "and substring(f.aoc_obo_id, 1, 3) = 'TH:' " +
        "and (a.stg_sequence < c.stg_sequence " +
        "or b.stg_sequence > d.stg_sequence) " +
        "order by e.aoc_obo_id, f.aoc_obo_id";
    
    //Check Proposed New Child Node Ranges are within Existing Parent Stage Ranges
    private static final String SQL_LIST_PROPOSED_CHILD_EXISTING_PARENT =
        "SELECT " +
        "e.aoc_obo_id as CHILD_ID, a.stg_name as CHILD_START, b.stg_name as CHILD_END, " +
        "f.aoc_obo_id as PARENT_ID, c.stg_name as PARENT_START, d.stg_name as PARENT_END " +
        "FROM ANA_OBO_COMPONENT e " +
        "join ana_obo_component_relationship on acr_obo_id = e.aoc_obo_id " +
        "join ana_obo_component f on f.aoc_obo_id = acr_obo_parent " +
        "join ana_stage a on a.stg_name = e.aoc_start " +
        "join ana_stage b on b.stg_name = e.aoc_end " +
        "join ana_stage c on c.stg_name = f.aoc_start " +
        "join ana_stage d on d.stg_name = f.aoc_end " +
        "where substring(e.aoc_obo_id, 1, 3) = 'TH:' " +
        "and substring(f.aoc_obo_id, 1, 3) <> 'TH:' " +
        "and (a.stg_sequence < c.stg_sequence " +
        "or b.stg_sequence > d.stg_sequence) " +
        "order by e.aoc_obo_id, f.aoc_obo_id";

    //Check Proposed New Child Node Ranges are within Existing Parent Stage Ranges - against Database
    private static final String SQL_LIST_EXISTING_CHILD_EXISTING_PARENT_DB =
    	"SELECT " +
    	"aoc_obo_id as CHILD_ID, aoc_start as CHILD_START, aoc_end as CHILD_END, " +
    	"acr_obo_parent as PARENT_ID, c.stg_name as PARENT_START, d.stg_name as PARENT_END " +
    	"FROM ANA_OBO_COMPONENT " +
    	"join ANA_OBO_COMPONENT_RELATIONSHIP on acr_obo_id = aoc_obo_id " +
    	"join ana_node g on g.ano_public_id = acr_obo_parent " +
    	"join anav_stage_range on anav_node_fk = g.ano_oid " +
    	"join ana_stage a on a.stg_name = aoc_start " +
    	"join ana_stage b on b.stg_name = aoc_end " +
    	"join ana_stage c on c.stg_sequence = anav_stage_min " +
    	"join ana_stage d on d.stg_sequence = anav_stage_max " +
    	"where (a.stg_sequence < anav_stage_min " +
    	"or b.stg_sequence > anav_stage_max) " +
    	"and aoc_obo_id not in (SELECT e.aoc_obo_id " +
    	"FROM ANA_OBO_COMPONENT e " +
    	"join ana_obo_component_relationship on acr_obo_id = e.aoc_obo_id " +
    	"join ana_obo_component f on f.aoc_obo_id = acr_obo_parent " +
    	"join ana_stage a on a.stg_name = e.aoc_start " +
    	"join ana_stage b on b.stg_name = e.aoc_end " +
    	"join ana_stage c on c.stg_name = f.aoc_start " +
    	"join ana_stage d on d.stg_name = f.aoc_end " +
    	"where substring(e.aoc_obo_id, 1, 3) <> 'TH:' " +
    	"and substring(f.aoc_obo_id, 1, 3) <> 'TH:' " +
    	"and (a.stg_sequence < c.stg_sequence " +
    	"or b.stg_sequence > d.stg_sequence)) " +
    	"order by aoc_obo_id, acr_obo_parent";
    
    //Check Existing Child Stage Range is within Existing Parent Stage Range
    private static final String SQL_COUNT_EXISTING_CHILD_EXISTING_PARENT =
        "SELECT COUNT(*) AS VALUE " +
        "e.aoc_obo_id as CHILD_ID, a.stg_name as CHILD_START, b.stg_name as CHILD_END, " +
        "f.aoc_obo_id as PARENT_ID, c.stg_name as PARENT_START, d.stg_name as PARENT_END " +
        "FROM ANA_OBO_COMPONENT e " +
        "join ana_obo_component_relationship on acr_obo_id = e.aoc_obo_id " +
        "join ana_obo_component f on f.aoc_obo_id = acr_obo_parent " +
        "join ana_stage a on a.stg_name = e.aoc_start " +
        "join ana_stage b on b.stg_name = e.aoc_end " +
        "join ana_stage c on c.stg_name = f.aoc_start " +
        "join ana_stage d on d.stg_name = f.aoc_end " +
        "where substring(e.aoc_obo_id, 1, 3) <> 'TH:' " +
        "and substring(f.aoc_obo_id, 1, 3) <> 'TH:' " +
        "and (a.stg_sequence < c.stg_sequence " +
        "or b.stg_sequence > d.stg_sequence)";

    //Check Existing Child Stage Range is within Proposed Parent Stage Range
    private static final String SQL_COUNT_EXISTING_CHILD_PROPOSED_PARENT =
        "SELECT COUNT(*) AS VALUE " +
        "e.aoc_obo_id as CHILD_ID, a.stg_name as CHILD_START, b.stg_name as CHILD_END, " +
        "f.aoc_obo_id as PARENT_ID, c.stg_name as PARENT_START, d.stg_name as PARENT_END " +
        "FROM ANA_OBO_COMPONENT e " +
        "join ana_obo_component_relationship on acr_obo_id = e.aoc_obo_id " +
        "join ana_obo_component f on f.aoc_obo_id = acr_obo_parent " +
        "join ana_stage a on a.stg_name = e.aoc_start " +
        "join ana_stage b on b.stg_name = e.aoc_end " +
        "join ana_stage c on c.stg_name = f.aoc_start " +
        "join ana_stage d on d.stg_name = f.aoc_end " +
        "where substring(e.aoc_obo_id, 1, 3) <> 'TH:' " +
        "and substring(f.aoc_obo_id, 1, 3) = 'TH:' " +
        "and (a.stg_sequence < c.stg_sequence " +
        "or b.stg_sequence > d.stg_sequence)";
    
    //Check Proposed New Child Node Ranges are within Proposed Parent Stage Ranges
    private static final String SQL_COUNT_PROPOSED_CHILD_PROPOSED_PARENT =
        "SELECT COUNT(*) AS VALUE " +
        "e.aoc_obo_id as CHILD_ID, a.stg_name as CHILD_START, b.stg_name as CHILD_END, " +
        "f.aoc_obo_id as PARENT_ID, c.stg_name as PARENT_START, d.stg_name as PARENT_END " +
        "FROM ANA_OBO_COMPONENT e " +
        "join ana_obo_component_relationship on acr_obo_id = e.aoc_obo_id " +
        "join ana_obo_component f on f.aoc_obo_id = acr_obo_parent " +
        "join ana_stage a on a.stg_name = e.aoc_start " +
        "join ana_stage b on b.stg_name = e.aoc_end " +
        "join ana_stage c on c.stg_name = f.aoc_start " +
        "join ana_stage d on d.stg_name = f.aoc_end " +
        "where substring(e.aoc_obo_id, 1, 3) = 'TH:' " +
        "and substring(f.aoc_obo_id, 1, 3) = 'TH:' " +
        "and (a.stg_sequence < c.stg_sequence " +
        "or b.stg_sequence > d.stg_sequence)";
    
    //Check Proposed New Child Node Ranges are within Existing Parent Stage Ranges
    private static final String SQL_COUNT_PROPOSED_CHILD_EXISTING_PARENT =
        "SELECT COUNT(*) AS VALUE " +
        "e.aoc_obo_id as CHILD_ID, a.stg_name as CHILD_START, b.stg_name as CHILD_END, " +
        "f.aoc_obo_id as PARENT_ID, c.stg_name as PARENT_START, d.stg_name as PARENT_END " +
        "FROM ANA_OBO_COMPONENT e " +
        "join ana_obo_component_relationship on acr_obo_id = e.aoc_obo_id " +
        "join ana_obo_component f on f.aoc_obo_id = acr_obo_parent " +
        "join ana_stage a on a.stg_name = e.aoc_start " +
        "join ana_stage b on b.stg_name = e.aoc_end " +
        "join ana_stage c on c.stg_name = f.aoc_start " +
        "join ana_stage d on d.stg_name = f.aoc_end " +
        "where substring(e.aoc_obo_id, 1, 3) = 'TH:' " +
        "and substring(f.aoc_obo_id, 1, 3) <> 'TH:' " +
        "and (a.stg_sequence < c.stg_sequence " +
        "or b.stg_sequence > d.stg_sequence)";

    //Check Proposed New Child Node Ranges are within Existing Parent Stage Ranges - against Database
    private static final String SQL_COUNT_EXISTING_CHILD_EXISTING_PARENT_DB =
        "SELECT COUNT(*) AS VALUE " +
       	"aoc_obo_id as CHILD_ID, aoc_start as CHILD_START, aoc_end as CHILD_END, " +
       	"acr_obo_parent as PARENT_ID, c.stg_name as PARENT_START, d.stg_name as PARENT_END " +
       	"FROM ANA_OBO_COMPONENT " +
       	"join ANA_OBO_COMPONENT_RELATIONSHIP on acr_obo_id = aoc_obo_id " +
       	"join ana_node g on g.ano_public_id = acr_obo_parent " +
       	"join anav_stage_range on anav_node_fk = g.ano_oid " +
       	"join ana_stage a on a.stg_name = aoc_start " +
       	"join ana_stage b on b.stg_name = aoc_end " +
       	"join ana_stage c on c.stg_sequence = anav_stage_min " +
       	"join ana_stage d on d.stg_sequence = anav_stage_max " +
       	"where (a.stg_sequence < anav_stage_min " +
       	"or b.stg_sequence > anav_stage_max) " +
       	"and aoc_obo_id not in (SELECT e.aoc_obo_id " +
       	"FROM ANA_OBO_COMPONENT e " +
       	"join ana_obo_component_relationship on acr_obo_id = e.aoc_obo_id " +
       	"join ana_obo_component f on f.aoc_obo_id = acr_obo_parent " +
       	"join ana_stage a on a.stg_name = e.aoc_start " +
       	"join ana_stage b on b.stg_name = e.aoc_end " +
       	"join ana_stage c on c.stg_name = f.aoc_start " +
       	"join ana_stage d on d.stg_name = f.aoc_end " +
       	"where substring(e.aoc_obo_id, 1, 3) <> 'TH:' " +
       	"and substring(f.aoc_obo_id, 1, 3) <> 'TH:' " +
       	"and (a.stg_sequence < c.stg_sequence " +
       	"or b.stg_sequence > d.stg_sequence)) " +
       	"order by aoc_obo_id, acr_obo_parent";
    
    // Vars ---------------------------------------------------------------------------------------
    private DAOFactory daoFactory;
    
    // Constructors -------------------------------------------------------------------------------
    /*
     * Construct a StageRange DAO for the given DAOFactory.
     *  Package private so that it can be constructed inside the DAO package only.
     */
    StageRangeDAO(DAOFactory daoFactory) {

    	this.daoFactory = daoFactory;
    }

    
    // Actions ------------------------------------------------------------------------------------
    /*
     * Returns a list of ALL stages ranges by existing child and existing parent, otherwise null
     */
    public List<StageRange> listByExistingChildExistingParent() throws DAOException {
    	
        return list(SQL_LIST_EXISTING_CHILD_EXISTING_PARENT);
    }
    
    /*
     * Returns a list of ALL stages ranges by existing child and proposed parent, otherwise null
     */
    public List<StageRange> listByExistingChildProposedParent() throws DAOException {
    	
        return list(SQL_LIST_EXISTING_CHILD_PROPOSED_PARENT);
    }
    
    /*
     * Returns a list of ALL stages ranges by proposed child and proposed parent, otherwise null
     */
    public List<StageRange> listByProposedChildProposedParent() throws DAOException {
    	
        return list(SQL_LIST_PROPOSED_CHILD_PROPOSED_PARENT);
    }
    
    /*
     * Returns a list of ALL stages ranges by proposed child and existing parent, otherwise null
     */
    public List<StageRange> listByProposedChildExistingParent() throws DAOException {
    	
        return list(SQL_LIST_PROPOSED_CHILD_EXISTING_PARENT);
    }
    
    /*
     * Returns a list of ALL stages ranges by existing child and existing parent from the 
     *  current database, otherwise null
     */
    public List<StageRange> listByExistingChildExistingParentDatabase() throws DAOException {
    	
        return list(SQL_LIST_EXISTING_CHILD_EXISTING_PARENT_DB);
    }
    
    /*
     * Returns a count of ALL stages ranges by existing child and existing parent
     */
    public int countByExistingChildExistingParent() throws DAOException {
    	
        return count(SQL_COUNT_EXISTING_CHILD_EXISTING_PARENT);
    }
    
    /*
     * Returns a count of ALL stages ranges by existing child and proposed parent
     */
    public int countByExistingChildProposedParent() throws DAOException {
    	
        return count(SQL_COUNT_EXISTING_CHILD_PROPOSED_PARENT);
    }
    
    /*
     * Returns a count of ALL stages ranges by proposed child and proposed parent
     */
    public int countByProposedChildProposedParent() throws DAOException {
    	
        return count(SQL_COUNT_PROPOSED_CHILD_PROPOSED_PARENT);
    }
    
    /*
     * Returns a count of ALL stages ranges by proposed child and existing parent
     */
    public int countlistByProposedChildExistingParent() throws DAOException {
    	
        return count(SQL_COUNT_PROPOSED_CHILD_EXISTING_PARENT);
    }
    
    /*
     * Returns a count of ALL stages ranges by existing child and existing parent from the 
     *  current database
     */
    public int countlistByExistingChildExistingParentDatabase() throws DAOException {
    	
        return count(SQL_COUNT_EXISTING_CHILD_EXISTING_PARENT_DB);
    }
    
    /*
     * Returns a list of all stages from the database.
     * 
     *  The list is never null and is empty when the database does not contain any stages.
     */
    public List<StageRange> list(String sql, Object... values) throws DAOException {
     
    	Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<StageRange> stages = new ArrayList<StageRange>();

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.isDebug(), daoFactory.getSqloutput(), connection, sql, false, values);
            resultSet = preparedStatement.executeQuery();
        
            while (resultSet.next()) {
                stages.add(mapStageRange(resultSet));
            }
        } 
        catch (SQLException e) {
            throw new DAOException(e);
        } 
        finally {
            close(connection, preparedStatement, resultSet);
        }

        return stages;
    }
    
    /*
     * Returns a of the number of rows in query.
     */
    public int count(String sql) throws DAOException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int count = 0;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.isDebug(), daoFactory.getSqloutput(), connection, sql, false);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                count = resultSet.getInt("VALUE");
            }
            
        } 
        catch (SQLException e) {
            throw new DAOException(e);
        } 
        finally {
            close(connection, preparedStatement, resultSet);
        }

        return count;
    }

    // Helpers ------------------------------------------------------------------------------------
    /*
     * Map the current row of the given ResultSet to an User.
     */
    private static StageRange mapStageRange(ResultSet resultSet) throws SQLException {

    	return new StageRange(
       		resultSet.getString("CHILD_ID"), 
       		resultSet.getString("CHILD_START"), 
       		resultSet.getString("CHILD_END"), 
       		resultSet.getString("PARENT_ID"), 
       		resultSet.getString("PARENT_START"),
       		resultSet.getString("PARENT_END")
        );
    }
}