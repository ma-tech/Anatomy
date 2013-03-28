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
* Description:  This class represents a SQL Database Access Object for the ExtraTimedNode DTO.
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
package daojdbc;

import static daolayer.DAOUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import daomodel.Editor;
import daomodel.ExtraTimedNode;

import daointerface.ExtraTimedNodeDAO;

import daolayer.DAOFactory;
import daolayer.DAOException;

public final class ExtraTimedNodeDAOJDBC implements ExtraTimedNodeDAO {
    // Constants ----------------------------------------------------------------------------------
    private static final String SQL_DISPLAY_BY_ORDER_AND_LIMIT_WHERE =
        "SELECT a.ATN_OID, a.ATN_NODE_FK, a.ATN_STAGE_FK, a.ATN_STAGE_MODIFIER_FK, a.ATN_PUBLIC_ID, " +
        "b.ANO_PUBLIC_ID, c.STG_NAME, c.STG_SEQUENCE, " +
        "d.STG_NAME AS STAGE_MIN, e.STG_NAME AS STAGE_MAX " +
        "FROM ANA_TIMED_NODE a " +
        "JOIN ANAV_STAGE_RANGE ON ANAV_NODE_FK = a.ATN_NODE_FK " +
        "JOIN ANA_NODE b ON b.ANO_OID = a.ATN_NODE_FK " +
        "JOIN ANA_STAGE c ON c.STG_OID = a.ATN_STAGE_FK " +
        "JOIN ANA_STAGE d ON ANAV_STAGE_MIN = d.STG_SEQUENCE " +
        "JOIN ANA_STAGE e ON ANAV_STAGE_MAX = e.STG_SEQUENCE " +
        "WHERE a.ATN_PUBLIC_ID LIKE ? " +
        "AND b.ANO_PUBLIC_ID LIKE ? " +
        "ORDER BY %s %s "+
        "LIMIT ?, ?";

    private static final String SQL_DISPLAY_BY_ORDER_AND_LIMIT =
        "SELECT a.ATN_OID, a.ATN_NODE_FK, a.ATN_STAGE_FK, a.ATN_STAGE_MODIFIER_FK, a.ATN_PUBLIC_ID, " +
        "b.ANO_PUBLIC_ID, c.STG_NAME, c.STG_SEQUENCE, " +
        "d.STG_NAME AS STAGE_MIN, e.STG_NAME AS STAGE_MAX " +
        "FROM ANA_TIMED_NODE a " +
        "JOIN ANAV_STAGE_RANGE ON ANAV_NODE_FK = a.ATN_NODE_FK " +
        "JOIN ANA_NODE b ON b.ANO_OID = a.ATN_NODE_FK " +
        "JOIN ANA_STAGE c ON c.STG_OID = a.ATN_STAGE_FK " +
        "JOIN ANA_STAGE d ON ANAV_STAGE_MIN = d.STG_SEQUENCE " +
        "JOIN ANA_STAGE e ON ANAV_STAGE_MAX = e.STG_SEQUENCE " +
        "ORDER BY %s %s "+
        "LIMIT ?, ?";

    private static final String SQL_ROW_COUNT_WHERE =
        "SELECT COUNT(*) AS VALUE " +
        "FROM ANA_TIMED_NODE a " +
        "JOIN ANAV_STAGE_RANGE ON ANAV_NODE_FK = a.ATN_NODE_FK " +
        "JOIN ANA_NODE b ON b.ANO_OID = a.ATN_NODE_FK " +
        "JOIN ANA_STAGE c ON c.STG_OID = a.ATN_STAGE_FK " +
        "JOIN ANA_STAGE d ON ANAV_STAGE_MIN = d.STG_SEQUENCE " +
        "JOIN ANA_STAGE e ON ANAV_STAGE_MAX = e.STG_SEQUENCE " +
        "WHERE a.ATN_PUBLIC_ID LIKE ? " +
        "AND b.ANO_PUBLIC_ID LIKE ? ";

    private static final String SQL_ROW_COUNT =
        "SELECT COUNT(*) AS VALUE " +
        "FROM ANA_TIMED_NODE a " +
        "JOIN ANAV_STAGE_RANGE ON ANAV_NODE_FK = a.ATN_NODE_FK " +
        "JOIN ANA_NODE b ON b.ANO_OID = a.ATN_NODE_FK " +
        "JOIN ANA_STAGE c ON c.STG_OID = a.ATN_STAGE_FK " +
        "JOIN ANA_STAGE d ON ANAV_STAGE_MIN = d.STG_SEQUENCE " +
        "JOIN ANA_STAGE e ON ANAV_STAGE_MAX = e.STG_SEQUENCE ";

    private static final String SQL_FIND_BY_EMAPA_AND_STAGE =
        "SELECT a.ATN_OID, a.ATN_NODE_FK, a.ATN_STAGE_FK, a.ATN_STAGE_MODIFIER_FK, a.ATN_PUBLIC_ID, " +
        "b.ANO_PUBLIC_ID, c.STG_NAME, c.STG_SEQUENCE, " +
        "d.STG_NAME AS STAGE_MIN, e.STG_NAME AS STAGE_MAX " +
        "FROM ANA_TIMED_NODE a " +
        "JOIN ANAV_STAGE_RANGE ON ANAV_NODE_FK = a.ATN_NODE_FK " +
        "JOIN ANA_NODE b ON b.ANO_OID = a.ATN_NODE_FK " +
        "JOIN ANA_STAGE c ON c.STG_OID = a.ATN_STAGE_FK " +
        "JOIN ANA_STAGE d ON ANAV_STAGE_MIN = d.STG_SEQUENCE " +
        "JOIN ANA_STAGE e ON ANAV_STAGE_MAX = e.STG_SEQUENCE " +
        "WHERE b.ANO_PUBLIC_ID = ? " +
        "AND c.STG_SEQUENCE = ? ";
        
    private static final String SQL_EXIST_EMAPA_AND_STAGE =
        "SELECT ATN_OID " +
        "FROM ANA_TIMED_NODE a " +
        "JOIN ANA_NODE b ON b.ANO_OID = a.ATN_NODE_FK " +
        "JOIN ANA_STAGE c ON c.STG_OID = a.ATN_STAGE_FK " +
        "WHERE b.ANO_PUBLIC_ID = ? " +
        "AND c.STG_SEQUENCE = ? ";

    private static final String SQL_FIND_BY_EMAP =
        "SELECT a.ATN_OID, a.ATN_NODE_FK, a.ATN_STAGE_FK, a.ATN_STAGE_MODIFIER_FK, a.ATN_PUBLIC_ID, " +
        "b.ANO_PUBLIC_ID, c.STG_NAME, c.STG_SEQUENCE, " +
        "d.STG_NAME AS STAGE_MIN, e.STG_NAME AS STAGE_MAX " +
        "FROM ANA_TIMED_NODE a " +
        "JOIN ANAV_STAGE_RANGE ON ANAV_NODE_FK = a.ATN_NODE_FK " +
        "JOIN ANA_NODE b ON b.ANO_OID = a.ATN_NODE_FK " +
        "JOIN ANA_STAGE c ON c.STG_OID = a.ATN_STAGE_FK " +
        "JOIN ANA_STAGE d ON ANAV_STAGE_MIN = d.STG_SEQUENCE " +
        "JOIN ANA_STAGE e ON ANAV_STAGE_MAX = e.STG_SEQUENCE " +
        "WHERE a.ATN_PUBLIC_ID = ? ";
            
    private static final String SQL_FIND_BY_OID =
        "SELECT a.ATN_OID, a.ATN_NODE_FK, a.ATN_STAGE_FK, a.ATN_STAGE_MODIFIER_FK, a.ATN_PUBLIC_ID, " +
        "b.ANO_PUBLIC_ID, c.STG_NAME, c.STG_SEQUENCE, " +
        "d.STG_NAME AS STAGE_MIN, e.STG_NAME AS STAGE_MAX " +
        "FROM ANA_TIMED_NODE a " +
        "JOIN ANAV_STAGE_RANGE ON ANAV_NODE_FK = a.ATN_NODE_FK " +
        "JOIN ANA_NODE b ON b.ANO_OID = a.ATN_NODE_FK " +
        "JOIN ANA_STAGE c ON c.STG_OID = a.ATN_STAGE_FK " +
        "JOIN ANA_STAGE d ON ANAV_STAGE_MIN = d.STG_SEQUENCE " +
        "JOIN ANA_STAGE e ON ANAV_STAGE_MAX = e.STG_SEQUENCE " +
        "WHERE a.ATN_OID = ? ";
                
    private static final String SQL_EXIST_EMAP =
        "SELECT a.ATN_OID " +
        "FROM ANA_TIMED_NODE a " +
        "JOIN ANAV_STAGE_RANGE ON ANAV_NODE_FK = a.ATN_NODE_FK " +
        "JOIN ANA_NODE b ON b.ANO_OID = a.ATN_NODE_FK " +
        "JOIN ANA_STAGE c ON c.STG_OID = a.ATN_STAGE_FK " +
        "JOIN ANA_STAGE d ON ANAV_STAGE_MIN = d.STG_SEQUENCE " +
        "JOIN ANA_STAGE e ON ANAV_STAGE_MAX = e.STG_SEQUENCE " +
        "WHERE a.ATN_PUBLIC_ID = ? ";

    // Vars ---------------------------------------------------------------------------------------
    private DAOFactory daoFactory;

    // Constructors -------------------------------------------------------------------------------
    /*
     * Construct a ExtraTimedNode DAO for the given DAOFactory.
     *  Package private so that it can be constructed inside the DAO package only.
     */
    public ExtraTimedNodeDAOJDBC() {
    	
    }
    
    public ExtraTimedNodeDAOJDBC(DAOFactory daoFactory) {
    	
        this.daoFactory = daoFactory;
    }

    // Actions ------------------------------------------------------------------------------------
	public void setDAOFactory(DAOFactory daofactory) {

		this.daoFactory = daofactory;
	}
    
    /*
     * Returns the extratimednode from the database matching the given OID, otherwise null.
     */
    public ExtraTimedNode findByOid(long oid) throws Exception {
    	
        return find(SQL_FIND_BY_OID, oid);
    }

    /*
     * Returns the extratimednode from the database matching the given EMAPA Id and Stage Sequence, 
     *  otherwise null.
     */
    public ExtraTimedNode findByEmapaAndStage(String emapaId, long stageSeq) throws Exception {
    	
        return find(SQL_FIND_BY_EMAPA_AND_STAGE, emapaId, stageSeq);
    }

    /*
     * Returns the extratimednode from the database matching the EMAP ID, otherwise null.
     */
    public ExtraTimedNode findByEmap(String emapId) throws Exception {
    	
        return find(SQL_FIND_BY_EMAP, emapId);
    }

    /*
     * Returns the extratimednode from the database matching the given 
     *  SQL query with the given values.
     */
    private ExtraTimedNode find(String sql, Object... values) throws Exception {
    	
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ExtraTimedNode extratimednode = null;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getLevel(), daoFactory.getSqloutput(), connection, sql, false, values);

            resultSet = preparedStatement.executeQuery();
            
            if (resultSet.next()) {
            	
                extratimednode = mapExtraTimedNode(resultSet);
            }
        }
        catch (SQLException e) {
        	
            throw new DAOException(e);
        }
        finally {
        	
            close(daoFactory.getLevel(), connection, preparedStatement, resultSet);
        }

        return extratimednode;
    }

    /*
     * Returns true if the given extratimednode EMAPA ID and Stage Seq exists in the database.
     */
    public boolean existOid(long Oid) throws Exception {
    	
        return exist(SQL_FIND_BY_OID, Oid);
    }

    /*
     * Returns true if the given extratimednode EMAPA ID and Stage Seq exists in the database.
     */
    public boolean existEmapaIdAndStageSeq(String emapaId, long stageSeq) throws Exception {
    	
        return exist(SQL_EXIST_EMAPA_AND_STAGE, emapaId, stageSeq);
    }

    /*
     * Returns true if the given extratimednode EMAP ID exists in the database.
     */
    public boolean existEmapId(String emapId) throws Exception {
    	
        return exist(SQL_EXIST_EMAP, emapId);
    }

    /*
     * Returns true if the given SQL query with the given values returns at least one row.
     */
    private boolean exist(String sql, Object... values) throws Exception {

    	Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean exist = false;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getLevel(), daoFactory.getSqloutput(), connection, sql, false, values);
            
            resultSet = preparedStatement.executeQuery();

            exist = resultSet.next();
        } 
        catch (SQLException e) {
        	
            throw new DAOException(e);
        } 
        finally {
        	
            close(daoFactory.getLevel(), connection, preparedStatement, resultSet);
        }

        return exist;
    }

    /*
     * Returns list of Nodes for Display purposes
     *  starting at the given first index with the given row count,
     *  sorted by the given sort field and sort order.
     */
    public List<ExtraTimedNode> display(int firstRow, int rowCount, String sortField, boolean sortAscending, String searchFirst, String searchSecond)
        throws Exception {
    	
        String searchFirstWithWildCards = "";
        String searchSecondWithWildCards = "";

        if (searchFirst.equals("")) {
        	searchFirstWithWildCards = "%" + searchFirst + "%";
    	}
        else {
        	searchFirstWithWildCards = "%" + searchFirst + "%";
        }

        if (searchSecond.equals("")) {
        	searchSecondWithWildCards = "%" + searchSecond + "%";
    	}
        else {
        	searchSecondWithWildCards = "%" + searchSecond + "%";
        }

        String sqlSortField = "a.ATN_OID";

    	if (sortField.equals("a.ATN_OID")) {
        	sqlSortField = "a.ATN_OID";       
        }
        if (sortField.equals("a.ATN_NODE_FK")) {
        	sqlSortField = "a.ATN_NODE_FK";      
        }
        if (sortField.equals("a.ATN_STAGE_FK")) {
        	sqlSortField = "a.ATN_STAGE_FK";         
        }
        if (sortField.equals("a.ATN_STAGE_MODIFIER_FK")) {
        	sqlSortField = "a.ATN_STAGE_MODIFIER_FK";         
        }
        if (sortField.equals("a.ATN_PUBLIC_ID")) {
        	sqlSortField = "a.ATN_PUBLIC_ID";         
        }
        if (sortField.equals("b.ANO_PUBLIC_ID")) {
        	sqlSortField = "b.ANO_PUBLIC_ID";         
        }
        if (sortField.equals("c.STG_NAME")) {
        	sqlSortField = "c.STG_NAME";         
        }
        if (sortField.equals("c.STG_SEQUENCE")) {
        	sqlSortField = "c.STG_SEQUENCE";         
        }
        if (sortField.equals("STAGE_MIN")) {
        	sqlSortField = "STAGE_MIN";         
        }
        if (sortField.equals("STAGE_MAX")) {
        	sqlSortField = "STAGE_MAX";         
        }
        
        String sortDirection = sortAscending ? "ASC" : "DESC";
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        List<ExtraTimedNode> dataList = new ArrayList<ExtraTimedNode>();

        try {
        	
            connection = daoFactory.getConnection();
            
            if ( searchFirst.equals("")) {
            	
                Object[] values = {
                        firstRow, 
                        rowCount
                    };
                String sql = String.format(SQL_DISPLAY_BY_ORDER_AND_LIMIT, sqlSortField, sortDirection);
                preparedStatement = prepareStatement(daoFactory.getLevel(), daoFactory.getSqloutput(), connection, sql, false, values);

            }
            else {
            	
                Object[] values = {
                    	searchFirstWithWildCards, 
                    	searchSecondWithWildCards,
                        firstRow, 
                        rowCount
                    };
                String sql = String.format(SQL_DISPLAY_BY_ORDER_AND_LIMIT_WHERE, sqlSortField, sortDirection);
                preparedStatement = prepareStatement(daoFactory.getLevel(), daoFactory.getSqloutput(), connection, sql, false, values);
            }

            resultSet = preparedStatement.executeQuery();
        
            while (resultSet.next()) {
            	
                dataList.add(mapExtraTimedNode(resultSet));
            }
            
        } 
        catch (SQLException e) {
        	
            throw new DAOException(e);
        } 
        finally {
        	
            close(daoFactory.getLevel(), connection, preparedStatement, resultSet);
        }

        return dataList;
    }
    
    /*
     * Returns total amount of rows in table.
     */
    public int count(String searchFirst, String searchSecond) throws Exception {

        String searchFirstWithWildCards = "";
        String searchSecondWithWildCards = "";

        if (searchFirst.equals("")) {
        	searchFirstWithWildCards = "%" + searchFirst + "%";
    	}
        else {
        	searchFirstWithWildCards = "%" + searchFirst + "%";
        }

        if (searchSecond.equals("")) {
        	searchSecondWithWildCards = "%" + searchSecond + "%";
    	}
        else {
        	searchSecondWithWildCards = "%" + searchSecond + "%";
        }

        Object[] values = {
        	searchFirstWithWildCards, 
       		searchSecondWithWildCards
        };

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int count = 0;

        try {
        	
            connection = daoFactory.getConnection();

            if ( searchFirst.equals("")) {
            	
                String sql = SQL_ROW_COUNT;
                preparedStatement = prepareStatement(daoFactory.getLevel(), daoFactory.getSqloutput(), connection, sql, false);

            }
            else {
            	
                String sql = SQL_ROW_COUNT_WHERE;
                preparedStatement = prepareStatement(daoFactory.getLevel(), daoFactory.getSqloutput(), connection, sql, false, values);
            }

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
            	
                count = resultSet.getInt("VALUE");
            }
            
        } 
        catch (SQLException e) {
        	
            throw new DAOException(e);
        } 
        finally {
        	
            close(daoFactory.getLevel(), connection, preparedStatement, resultSet);
        }

        return count;
    }


    // Helpers ------------------------------------------------------------------------------------
    /*
     * Map the current row of the given ResultSet to an User.
     */
    private static ExtraTimedNode mapExtraTimedNode(ResultSet resultSet) throws SQLException {
    	
        return new ExtraTimedNode(
      		resultSet.getLong("a.ATN_OID"), 
       		resultSet.getLong("a.ATN_NODE_FK"), 
       		resultSet.getLong("a.ATN_STAGE_FK"), 
       		resultSet.getString("a.ATN_STAGE_MODIFIER_FK"), 
       		resultSet.getString("a.ATN_PUBLIC_ID"), 
       		resultSet.getString("b.ANO_PUBLIC_ID"), 
       		resultSet.getString("c.STG_NAME"), 
       		resultSet.getLong("c.STG_SEQUENCE"), 
       		resultSet.getString("STAGE_MIN"), 
       		resultSet.getString("STAGE_MAX") 
        );
    }
}