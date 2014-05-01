/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayer
*
* Title:        DerivedPartOfPerspectivesDAO.java
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
* Description:  This class represents a SQL Database Access Object for the DerivedPartOfPerspectives DTO.
*  
*               This Data Access Object should be used as a central point for the mapping between 
*                the DerivedPartOfPerspectives DTO and a SQL database.
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
package daojdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import utility.Wrapper;
import daomodel.DerivedPartOf;
import daomodel.DerivedPartOfPerspectives;
import daointerface.DerivedPartOfPerspectivesDAO;
import daolayer.DAOFactory;
import daolayer.DAOException;
import static daolayer.DAOUtil.*;


public final class DerivedPartOfPerspectivesDAOJDBC implements DerivedPartOfPerspectivesDAO{
    // Constants ----------------------------------------------------------------------------------
    private static final String SQL_INSERT =
        "INSERT INTO ANAD_PART_OF_PERSPECTIVE " +
        "( POP_PERSPECTIVE_FK, POP_APO_FK, POP_IS_ANCESTOR, POP_NODE_FK ) " +
        "VALUES (?, ?, ?, ?)";

    private static final String SQL_DELETE =
        "DELETE FROM ANAD_PART_OF_PERSPECTIVE " +
        "WHERE POP_NODE_FK = ?";

    private static final String SQL_ROW_COUNT =
        "SELECT COUNT(*) AS VALUE " +
        "FROM ANAD_PART_OF_PERSPECTIVE ";

    private static final String SQL_ROW_COUNT_BY_PERSPECTIVE =
        "SELECT COUNT(*) AS VALUE " +
        "FROM ANAD_PART_OF_PERSPECTIVE " +
        "WHERE POP_PERSPECTIVE_FK = ? ";

    private static final String SQL_ROW_COUNT_BY_PERSPECTIVE_AND =
        "SELECT COUNT(*) AS VALUE " +
        "FROM ANAD_PART_OF_PERSPECTIVE " +
        "WHERE POP_PERSPECTIVE_FK = ? " +
        "AND POP_NODE_FK = ? ";

    private static final String SQL_FIND_BY_NODE_FK_AND_PERSPECTIVE =
        "SELECT POP_PERSPECTIVE_FK, POP_APO_FK, POP_IS_ANCESTOR, POP_NODE_FK " +
        "FROM ANAD_PART_OF_PERSPECTIVE " +
        "WHERE POP_NODE_FK = ? " + 
        "AND POP_PERSPECTIVE_FK = ? ";
        
    private static final String SQL_FIND_BY_APO_FK_AND_PERSPECTIVE =
    	"SELECT POP_PERSPECTIVE_FK, POP_APO_FK, POP_IS_ANCESTOR, POP_NODE_FK " +
    	"FROM ANAD_PART_OF_PERSPECTIVE " +
        "WHERE POP_APO_FK = ? " + 
        "AND POP_PERSPECTIVE_FK = ? ";
            
    private static final String SQL_DISPLAY_BY_ORDER_AND_LIMIT_BY_PERSPECTIVE =
        "SELECT POP_PERSPECTIVE_FK, POP_APO_FK, POP_IS_ANCESTOR, POP_NODE_FK " +
        "FROM ANAD_PART_OF_PERSPECTIVE " +
        "WHERE POP_PERSPECTIVE_FK = ? " +
        "ORDER BY %s %s "+
        "LIMIT ?, ?";
        
    private static final String SQL_DISPLAY_BY_ORDER_AND_LIMIT_BY_PERSPECTIVE_AND =
        "SELECT POP_PERSPECTIVE_FK, POP_APO_FK, POP_IS_ANCESTOR, POP_NODE_FK " +
        "FROM ANAD_PART_OF_PERSPECTIVE " +
        "WHERE POP_PERSPECTIVE_FK = ? " +
        "AND POP_NODE_FK = ? " +
        "ORDER BY %s %s "+
        "LIMIT ?, ?";

    private static final String SQL_EMPTY =
        "DELETE FROM ANAD_PART_OF_PERSPECTIVE";

    
    // Vars ---------------------------------------------------------------------------------------
    private DAOFactory daoFactory;

    // Constructors -------------------------------------------------------------------------------
    /*
     * Construct a DerivedPartOfPerspectives Data Access Object for the given DAOFactory.
     *  Package private so that it can be constructed inside the Data Access Object package only.
     */
    public DerivedPartOfPerspectivesDAOJDBC() {
    	
    }
    
    public DerivedPartOfPerspectivesDAOJDBC(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    // Actions ------------------------------------------------------------------------------------
	public void setDAOFactory(DAOFactory daofactory) {

		this.daoFactory = daofactory;
	}
    
    /*
     * Returns list of DerivedPartOfPerspectivess for Display purposes
     *  starting at the given first index with the given row count,
     *  sorted by the given sort field and sort order.
     */
    public List<DerivedPartOfPerspectives> display(int firstRow, int rowCount, String sortField, boolean sortAscending, String searchTerm, String searchPerspective)
        throws Exception {

    	String sqlSortField = "POP_PERSPECTIVE_FK";
    	
        if (sortField.equals("perspectiveFK")) {
        	sqlSortField = "POP_PERSPECTIVE_FK";      
        }
        if (sortField.equals("nodeFK")) {
        	sqlSortField = "POP_NODE_FK";      
        }
        if (sortField.equals("partOfFK")) {
        	sqlSortField = "POP_APO_FK";         
        }
        if (sortField.equals("ancestor")) {
        	sqlSortField = "POP_IS_ANCESTOR";         
        }
        
        String searchWithWildCards = searchTerm;

        String sortDirection = sortAscending ? "ASC" : "DESC";
        String sql = "";

        if (searchTerm.equals("")){

        	sql = String.format(SQL_DISPLAY_BY_ORDER_AND_LIMIT_BY_PERSPECTIVE, sqlSortField, sortDirection);

        	Object[] values = {
            		searchPerspective,
                    firstRow, 
                    rowCount
            };

            Connection connection = null;
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;
            
            List<DerivedPartOfPerspectives> dataList = new ArrayList<DerivedPartOfPerspectives>();

            try {
            	
            	
                connection = daoFactory.getConnection();

                preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, sql, false, values);

                resultSet = preparedStatement.executeQuery();
            
                while (resultSet.next()) {
                	
                    dataList.add(mapDerivedPartOfPerspectives(resultSet));
                }
                
            } 
            catch (SQLException e) {
            	
                throw new DAOException(e);
            } 
            finally {
            	
                close(daoFactory.getMsgLevel(), connection, preparedStatement, resultSet);
            }

            return dataList;
        }
        else {
        	
            sql = String.format(SQL_DISPLAY_BY_ORDER_AND_LIMIT_BY_PERSPECTIVE_AND, sqlSortField, sortDirection);

            Object[] values = {
            		searchPerspective,
            		searchWithWildCards,
                    firstRow, 
                    rowCount
            };

            Connection connection = null;
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;
            
            List<DerivedPartOfPerspectives> dataList = new ArrayList<DerivedPartOfPerspectives>();

            try {
            	
                connection = daoFactory.getConnection();

                preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, sql, false, values);

                resultSet = preparedStatement.executeQuery();
            
                while (resultSet.next()) {
                	
                    dataList.add(mapDerivedPartOfPerspectives(resultSet));
                }
                
            } 
            catch (SQLException e) {
            	
                throw new DAOException(e);
            } 
            finally {
            	
                close(daoFactory.getMsgLevel(), connection, preparedStatement, resultSet);
            }

            return dataList;
        }
    }

    /*
     * Returns total amount of rows in table.
     */
    public long count(String searchTerm, String searchPerspective) throws Exception {

        String searchWithWildCards = searchTerm;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        long count = 0;

        if (searchTerm.equals("")){
        	
            try {

            	Object[] values = {
            			searchPerspective
                };

                connection = daoFactory.getConnection();

                preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, SQL_ROW_COUNT_BY_PERSPECTIVE, false, values);

                resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                	
                    count = resultSet.getLong("VALUE");
                }
                
            } 
            catch (SQLException e) {
            	
                throw new DAOException(e);
            } 
            finally {
            	
                close(daoFactory.getMsgLevel(), connection, preparedStatement, resultSet);
            }
        }
        else {
        	
            try {

            	Object[] values = {
            			searchPerspective,
                		searchWithWildCards
                };

                connection = daoFactory.getConnection();

                preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, SQL_ROW_COUNT_BY_PERSPECTIVE_AND, false, values);

                resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                	
                    count = resultSet.getLong("VALUE");
                }
                
            } 
            catch (SQLException e) {
            	
                throw new DAOException(e);
            } 
            finally {
            	
                close(daoFactory.getMsgLevel(), connection, preparedStatement, resultSet);
            }
        }

        return count;
    }


    /*
     * Returns total amount of rows in table.
     */
    public long countAll() throws Exception {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        long count = 0;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, SQL_ROW_COUNT, false);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
            	
                count = resultSet.getLong("VALUE");
            }
        } 
        catch (SQLException e) {
        	
            throw new DAOException(e);
        } 
        finally {
        	
            close(daoFactory.getMsgLevel(), connection, preparedStatement, resultSet);
        }

        return count;
    }


    /*
     *  Empty the ANAD_PART_OF_PERSPECTIVE Table from the database. 
     */
    public void empty() throws Exception {
    	
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, SQL_EMPTY, false);

            if ( daoFactory.isUpdate() ) {

            	int affectedRows = preparedStatement.executeUpdate();
                
                if (affectedRows == 0) {
                	
                    throw new DAOException("Deleting ALL ANAD_PART_OF_PERSPECTIVE failed, no rows affected.");
                } 
            }
            else {
            	
    		    Wrapper.printMessage("UPDATE: Delete ANAD_PART_OF_PERSPECTIVE Skipped", "***", daoFactory.getMsgLevel());
            }
        } 
        catch (SQLException e) {
        	
            throw new DAOException(e);
        } 
        finally {
        	
            close(daoFactory.getMsgLevel(),connection, preparedStatement);
        }
    }


    /*
     * Create the given derivedpartof in the database. 
     * 
     *  The derivedpartof OID must be null, otherwise it will throw IllegalArgumentException.
     *   If the derivedpartof OID value is unknown, rather use save(DerivedPartOf).
     *    After creating, the Data Access Object will set the obtained ID in the given derivedpartof.
     */
	public void create(DerivedPartOfPerspectives derivedpartofperspectives)
			throws IllegalArgumentException, Exception {
    	
    	Object[] values = {
    			derivedpartofperspectives.getPerspectiveFK(),
    			derivedpartofperspectives.getPartOfFK(),
    			derivedpartofperspectives.getAncestor(),
    			derivedpartofperspectives.getNodeFK()
    	};

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet generatedKeys = null;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, SQL_INSERT, true, values);

            if ( daoFactory.isUpdate() ) {

            	int affectedRows = preparedStatement.executeUpdate();
                
                if (affectedRows == 0) {
                	
                    throw new DAOException("Creating DerivedPartOfPerspectives failed, no rows affected.");
                } 
            }
            else {
            	
    		    Wrapper.printMessage("UPDATE: Create ANAD_PART_OF_PERSPECTIVE Skipped", "***", daoFactory.getMsgLevel());
            }
        } 
        catch (SQLException e) {
        	
            throw new DAOException(e);
        } 
        finally {
        	
            close(daoFactory.getMsgLevel(), connection, preparedStatement, generatedKeys);
        }
    }
	
    
	/*
     * Returns the DerivedPartOfPerspectives from the database matching the given Pathway , otherwise null.
     */
    public DerivedPartOfPerspectives findByNodeFKAndPerspective(long nodefk, String perspective) throws Exception {
    	
        return find(SQL_FIND_BY_NODE_FK_AND_PERSPECTIVE, nodefk, perspective);
    }
    
    
	/*
     * Returns the DerivedPartOfPerspectives from the database matching the given Pathway , otherwise null.
     */
    public DerivedPartOfPerspectives findByApoFKAndPerspective(long apofk, String perspective) throws Exception {
    	
        return find(SQL_FIND_BY_APO_FK_AND_PERSPECTIVE, apofk, perspective);
    }
    
    
    /*
     * Returns the derivedpartof from the database matching the given 
     *  SQL query with the given values.
     */
    private DerivedPartOfPerspectives find(String sql, Object... values) throws Exception {
    
    	Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        DerivedPartOfPerspectives derivedpartofperspectives = null;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, sql, false, values);
            resultSet = preparedStatement.executeQuery();
        
            if (resultSet.next()) {
            	
            	derivedpartofperspectives = mapDerivedPartOfPerspectives(resultSet);
            }
        } 
        catch (SQLException e) {
        	
            throw new DAOException(e);
        } 
        finally {
        	
            close(daoFactory.getMsgLevel(), connection, preparedStatement, resultSet);
        }

        return derivedpartofperspectives;
    }


    /*
     * Delete the given derivedpartof from the database. 
     * 
     *  After deleting, the Data Access Object will set the ID of the given derivedpartof to null.
     */
    public void delete(DerivedPartOfPerspectives derivedpartofperspectives) throws Exception {
    	
        Object[] values = { 
        		derivedpartofperspectives.getNodeFK()
        };

        if (derivedpartofperspectives.getNodeFKAsString() == null) {
        	
            throw new IllegalArgumentException("DerivedPartOfPerspectives is not created yet, so the derivedpartofperspectives NodeFK cannot be null.");
        }

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, SQL_DELETE, false, values);

            if ( daoFactory.isUpdate() ) {

            	int affectedRows = preparedStatement.executeUpdate();
                
                if (affectedRows == 0) {
                	
                    throw new DAOException("Deleting derivedpartofperspectives failed, no rows affected.");
                } 
                else {
                	
                	derivedpartofperspectives.setNodeFK(0);
                }
            }
            else {
            	
    		    Wrapper.printMessage("UPDATE: Delete ANAD_PART_OF_PERSPECTIVE Skipped", "***", daoFactory.getMsgLevel());
            }
        } 
        catch (SQLException e) {
        	
            throw new DAOException(e);
        } 
        finally {
        	
            close(daoFactory.getMsgLevel(),connection, preparedStatement);
        }
    }


    // Helpers ------------------------------------------------------------------------------------
    /*
     * Map the current row of the given ResultSet to an User.
     */
    private static DerivedPartOfPerspectives mapDerivedPartOfPerspectives(ResultSet resultSet) throws SQLException {
    	
        return new DerivedPartOfPerspectives(
       		resultSet.getString("POP_PERSPECTIVE_FK"), 
       		resultSet.getLong("POP_APO_FK"), 
       		resultSet.getInt("POP_IS_ANCESTOR"), 
       		resultSet.getLong("POP_NODE_FK")
        );
    }

}
