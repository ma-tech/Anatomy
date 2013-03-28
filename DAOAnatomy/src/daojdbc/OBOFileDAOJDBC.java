/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomy
*
* Title:        OBOFileDAO.java
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
* Description:  This class represents a SQL Database Access Object for the 
*                OBOFile DTO.
*  
*               This DAO should be used as a central point for the mapping between 
*                the OBOFile DTO and a SQL database.
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

import daomodel.OBOFile;

import daointerface.OBOFileDAO;

import daolayer.DAOFactory;
import daolayer.DAOException;


public final class OBOFileDAOJDBC implements OBOFileDAO {
    // Constants ----------------------------------------------------------------------------------
    private static final String SQL_DISPLAY_BY_ORDER_AND_LIMIT =
        "SELECT AOF_OID, AOF_FILE_NAME, AOF_FILE_CONTENT, AOF_FILE_CONTENT_TYPE, AOF_FILE_CONTENT_LENGTH, AOF_FILE_CONTENT_DATE, AOF_FILE_VALIDATION, AOF_FILE_AUTHOR, AOF_TEXT_REPORT_NAME, AOF_TEXT_REPORT, AOF_TEXT_REPORT_TYPE, AOF_TEXT_REPORT_LENGTH, AOF_TEXT_REPORT_DATE, AOF_PDF_REPORT_NAME, AOF_PDF_REPORT, AOF_PDF_REPORT_TYPE, AOF_PDF_REPORT_LENGTH, AOF_PDF_REPORT_DATE " +
        "FROM ANA_OBO_FILE " +
        "WHERE AOF_FILE_NAME LIKE ? " +
        "AND AOF_FILE_CONTENT_TYPE LIKE ? " +
        "ORDER BY %s %s "+
        "LIMIT ?, ?";

    private static final String SQL_ROW_COUNT_BY_ORDER_AND_LIMIT =
        "SELECT count(*) AS VALUE " +
        "FROM ANA_OBO_FILE " +
        "WHERE AOF_FILE_NAME LIKE ? " +
        "AND AOF_FILE_CONTENT_TYPE LIKE ? ";

    private static final String SQL_FIND_BY_OID =
        "SELECT AOF_OID, AOF_FILE_NAME, AOF_FILE_CONTENT, AOF_FILE_CONTENT_TYPE, AOF_FILE_CONTENT_LENGTH, AOF_FILE_CONTENT_DATE, AOF_FILE_VALIDATION, AOF_FILE_AUTHOR, AOF_TEXT_REPORT_NAME, AOF_TEXT_REPORT, AOF_TEXT_REPORT_TYPE, AOF_TEXT_REPORT_LENGTH, AOF_TEXT_REPORT_DATE, AOF_PDF_REPORT_NAME, AOF_PDF_REPORT, AOF_PDF_REPORT_TYPE, AOF_PDF_REPORT_LENGTH, AOF_PDF_REPORT_DATE " +
        "FROM ANA_OBO_FILE " +
        "WHERE AOF_OID = ?";
    
    private static final String SQL_GET_MAX_OID =
        "SELECT MAX(AOF_OID) AS VALUE " +
        "FROM ANA_OBO_FILE ";
    
    private static final String SQL_LIST_ALL =
        "SELECT AOF_OID, AOF_FILE_NAME, AOF_FILE_CONTENT, AOF_FILE_CONTENT_TYPE, AOF_FILE_CONTENT_LENGTH, AOF_FILE_CONTENT_DATE, AOF_FILE_VALIDATION, AOF_FILE_AUTHOR, AOF_TEXT_REPORT_NAME, AOF_TEXT_REPORT, AOF_TEXT_REPORT_TYPE, AOF_TEXT_REPORT_LENGTH, AOF_TEXT_REPORT_DATE, AOF_PDF_REPORT_NAME, AOF_PDF_REPORT, AOF_PDF_REPORT_TYPE, AOF_PDF_REPORT_LENGTH, AOF_PDF_REPORT_DATE " +
        "FROM ANA_OBO_FILE ";
    
    private static final String SQL_INSERT =
        "INSERT INTO ANA_OBO_FILE " +
        "(AOF_OID, AOF_FILE_NAME, AOF_FILE_CONTENT, AOF_FILE_CONTENT_TYPE, AOF_FILE_CONTENT_LENGTH, AOF_FILE_CONTENT_DATE, AOF_FILE_VALIDATION, AOF_FILE_AUTHOR, AOF_TEXT_REPORT_NAME, AOF_TEXT_REPORT, AOF_TEXT_REPORT_TYPE, AOF_TEXT_REPORT_LENGTH, AOF_TEXT_REPORT_DATE, AOF_PDF_REPORT_NAME, AOF_PDF_REPORT, AOF_PDF_REPORT_TYPE, AOF_PDF_REPORT_LENGTH, AOF_PDF_REPORT_DATE ) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";

    private static final String SQL_UPDATE =
        "UPDATE ANA_OBO_FILE " +
        "SET AOF_FILE_NAME = ?, " +
        "AOF_FILE_CONTENT = ?, " + 
        "AOF_FILE_CONTENT_TYPE = ?, " + 
        "AOF_FILE_CONTENT_LENGTH = ?, " + 
        "AOF_FILE_CONTENT_DATE = ?, " + 
        "AOF_FILE_VALIDATION = ?, " +
        "AOF_FILE_AUTHOR = ?, " +
        "AOF_TEXT_REPORT_NAME = ?, " +
        "AOF_TEXT_REPORT = ?, " +
        "AOF_TEXT_REPORT_TYPE = ?, " +
        "AOF_TEXT_REPORT_LENGTH = ?, " +
        "AOF_TEXT_REPORT_DATE = ?, " +
        "AOF_PDF_REPORT_NAME = ?, " +
        "AOF_PDF_REPORT = ?, " +
        "AOF_PDF_REPORT_TYPE = ?, " +
        "AOF_PDF_REPORT_LENGTH = ?, " +
        "AOF_PDF_REPORT_DATE = ? " +
        "WHERE AOF_OID = ?";
    
    private static final String SQL_DELETE =
        "DELETE FROM ANA_OBO_FILE " +
        "WHERE AOF_OID = ?";

    private static final String SQL_EXIST_OID =
        "SELECT AOF_OID " +
        "FROM ANA_OBO_FILE " +
        "WHERE AOF_OID = ?";
    
    // Vars ---------------------------------------------------------------------------------------
    private DAOFactory daoFactory;

    // Constructors -------------------------------------------------------------------------------
    /*
     * Construct a OBOFile DAO for the given DAOFactory.
     *  Package private so that it can be constructed inside the DAO package only.
     */
    public OBOFileDAOJDBC() {
    	
    }

    public OBOFileDAOJDBC(DAOFactory daoFactory) {
    	
        this.daoFactory = daoFactory;
    }

    // Actions ------------------------------------------------------------------------------------
	public void setDAOFactory(DAOFactory daofactory) {

		this.daoFactory = daofactory;
	}
    
    /*
     * Returns the obofile from the database matching the given OID, otherwise null.
     */
    public OBOFile findByOid(long oid) throws Exception {
    	
        return find(SQL_FIND_BY_OID, oid);
    }

    /*
     * Returns the obofile from the database matching the given OID, otherwise null.
     */
    public OBOFile findWithBinary(long oid) throws Exception {
    	
        return findWithBinary(SQL_FIND_BY_OID, oid);
    }

    /*
     * Returns the obofile from the database matching the given 
     *  SQL query with the given values.
     */
    private OBOFile find(String sql, Object... values) throws Exception {
    	
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        OBOFile obofile = null;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getLevel(), daoFactory.getSqloutput(), connection, sql, false, values);
            resultSet = preparedStatement.executeQuery();
            
            if (resultSet.next()) {
            
            	obofile = mapOBOFile(resultSet);
            }
        } 
        catch (SQLException e) {
        	
            throw new DAOException(e);
        } 
        finally {
        	
            close(daoFactory.getLevel(), connection, preparedStatement, resultSet);
        }

        return obofile;
    }

    /*
     * Returns the obofile from the database matching the given 
     *  SQL query with the given values.
     */
    private OBOFile findWithBinary(String sql, Object... values) throws Exception {
    	
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        OBOFile obofile = null;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getLevel(), daoFactory.getSqloutput(), connection, sql, false, values);
            resultSet = preparedStatement.executeQuery();
            
            if (resultSet.next()) {
            
            	obofile = mapOBOFileWithBinary(resultSet);
            }
        } 
        catch (SQLException e) {
        	
            throw new DAOException(e);
        } 
        finally {
        	
            close(daoFactory.getLevel(), connection, preparedStatement, resultSet);
        }

        return obofile;
    }

    /*
     * Returns a list of ALL files, otherwise null.
     */
    public List<OBOFile> listAll() throws Exception {
    	
        return list(SQL_LIST_ALL);
    }
    
    /*
     * Returns a list of ALL files, otherwise null.
     */
    public List<OBOFile> listAllWithBinary() throws Exception {
    	
        return listWithBinary(SQL_LIST_ALL, (Object[]) null);
    }
    
    /*
     * Returns a list of all files from the database. 
     *  The list is never null and is empty when the database does not contain any files.
     */
    public List<OBOFile> list(String sql, Object... values) throws Exception {
    	
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<OBOFile> files = new ArrayList<OBOFile>();

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getLevel(), daoFactory.getSqloutput(), connection, sql, false, values);
            resultSet = preparedStatement.executeQuery();
            
            while (resultSet.next()) {
            
            	files.add(mapOBOFile(resultSet));
            }
        } 
        catch (SQLException e) {
        	
            throw new DAOException(e);
        } 
        finally {
        	
            close(daoFactory.getLevel(), connection, preparedStatement, resultSet);
        }

        return files;
    }

    /*
     * Returns a list of all files from the database. 
     *  The list is never null and is empty when the database does not contain any files.
     */
    public List<OBOFile> listWithBinary(String sql, Object... values) throws Exception {
    	
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<OBOFile> files = new ArrayList<OBOFile>();

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getLevel(), daoFactory.getSqloutput(), connection, sql, false, values);
            resultSet = preparedStatement.executeQuery();
            
            while (resultSet.next()) {
            
            	files.add(mapOBOFileWithBinary(resultSet));
            }
        } 
        catch (SQLException e) {
        	
            throw new DAOException(e);
        } 
        finally {
        	
            close(daoFactory.getLevel(), connection, preparedStatement, resultSet);
        }

        return files;
    }

    /*
     * Create the given OBOFile in the database. 
     *  The OBOFile OID must be null, otherwise it will throw IllegalArgumentException.
     *  If the OBOFile OID value is unknown, rather use #save(obofile).
     *  
     * After creating, the DAO will set the obtained ID in the given obofile.
     */
    public void create(OBOFile obofile) throws IllegalArgumentException, Exception {
    	
        if (obofile.getOid() != null) {
        	
            throw new IllegalArgumentException("OBOFile has already been created, as the obofile OID is not null.");
        }
        
        Long maxOid = findMaxOid();
        maxOid++;
        obofile.setOid(maxOid);

        Object[] values = {
        	obofile.getOid(),
        	obofile.getName(),
        	obofile.getContent(),
        	obofile.getContenttype(),
        	obofile.getContentlength(),
        	obofile.getContentdate(),
        	obofile.getValidation(),
        	obofile.getAuthor(),
        	obofile.getTextreportname(),
        	obofile.getTextreport(),
        	obofile.getTextreporttype(),
        	obofile.getTextreportlength(),
        	obofile.getTextreportdate(),
        	obofile.getPdfreportname(),
        	obofile.getPdfreport(),
        	obofile.getPdfreporttype(),
        	obofile.getPdfreportlength(),
        	obofile.getPdfreportdate()
        };

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet generatedKeys = null;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getLevel(), daoFactory.getSqloutput(), connection, SQL_INSERT, true, values);
            
            int affectedRows = preparedStatement.executeUpdate();
            
            if (affectedRows == 0) {
            	
                throw new DAOException("Creating obofile failed, no rows affected.");
            }
        } 
        catch (SQLException e) {
        	
            throw new DAOException(e);
        } 
        finally {
        	
            close(daoFactory.getLevel(), connection, preparedStatement, generatedKeys);
        }
    }
    
    /*
     * Update the given obofile in the database.
     *  The obofile OID must not be null, otherwise it will throw IllegalArgumentException. 
     *  If the obofile OID value is unknown, rather use save(OBOFile).
     */
    public void update(OBOFile obofile) throws Exception {
    	
        if (obofile.getOid() == null) {
        	
            throw new IllegalArgumentException("OBOFile is not created yet, so the obofile OID cannot be null.");
        }

        Object[] values = {
           	obofile.getName(),
           	obofile.getContent(),
           	obofile.getContenttype(),
           	obofile.getContentlength(),
           	obofile.getContentdate(),
           	obofile.getValidation(),
           	obofile.getAuthor(),
           	obofile.getTextreportname(),
           	obofile.getTextreport(),
           	obofile.getTextreporttype(),
           	obofile.getTextreportlength(),
           	obofile.getTextreportdate(),
           	obofile.getPdfreportname(),
           	obofile.getPdfreport(),
           	obofile.getPdfreporttype(),
           	obofile.getPdfreportlength(),
           	obofile.getPdfreportdate(),
            obofile.getOid()
        };

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getLevel(), daoFactory.getSqloutput(), connection, SQL_UPDATE, false, values);
            
            int affectedRows = preparedStatement.executeUpdate();
            
            if (affectedRows == 0) {
            	
                throw new DAOException("Updating obofile failed, no rows affected.");
            }
        } 
        catch (SQLException e) {
        	
            throw new DAOException(e);
        } 
        finally {
        	
            close(daoFactory.getLevel(),connection, preparedStatement);
        }
    }
     
    /*
     * Save the given obofile in the database.
     *  If the obofile OID is null, then 
     *   it will invoke create(OBOFile), else 
     *   it will invoke update(OBOFile).
     */
    public void save(OBOFile obofile) throws Exception {
    	
        if (obofile.getOid() == null) {
        	
            create(obofile);
        } 
        else {
        	
            update(obofile);
        }
    }
    
    /*
     * Delete the given obofile from the database. 
     *  After deleting, the DAO will set the ID of the given obofile to null.
     */
    public void delete(OBOFile obofile) throws Exception {
    	
        Object[] values = { 
        	obofile.getOid() 
        };

        if (obofile.getOid() == null) {
        	
            throw new IllegalArgumentException("OBOFile is not created yet, so the obofile OID cannot be null.");
        }

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getLevel(), daoFactory.getSqloutput(), connection, SQL_DELETE, false, values);
            int affectedRows = preparedStatement.executeUpdate();
            
            if (affectedRows == 0) {
            	
                throw new DAOException("Deleting obofile failed, no rows affected.");
            } 
            else {
            	
            	obofile.setOid(null);
            }
        } 
        catch (SQLException e) {
        	
            throw new DAOException(e);
        } 
        finally {
        	
            close(daoFactory.getLevel(),connection, preparedStatement);
        }
    }
    
    /*
     * Returns true if the given obofile OID exists in the database.
     */
    public boolean existOid(long oid) throws Exception {
    	
        return exist(SQL_EXIST_OID, oid);
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
     * Returns the obofile from the database matching the given OID, otherwise null.
     */
    public long findMaxOid() throws Exception {
    	
        return value(SQL_GET_MAX_OID);
    }

    /*
     * Returns a value if the given SQL query with the given values returns at least one row.
     */
    private long value(String sql) throws Exception {
    	
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Long value;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getLevel(), daoFactory.getSqloutput(), connection, sql, false);

            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            
            value = resultSet.getLong("VALUE");
        } 
        catch (SQLException e) {
        	
            throw new DAOException(e);
        } 
        finally {
        	
            close(daoFactory.getLevel(), connection, preparedStatement, resultSet);
        }

        return value;
    }

    /*
     * Returns list of OBOFiles for Display purposes
     *  starting at the given first index with the given row count,
     *  sorted by the given sort field and sort order.
     */

    public List<OBOFile> display(int firstRow, int rowCount, String sortField, boolean sortAscending, String searchTerm, String searchTable)
        throws Exception {
    	
    	String sqlSortField = "AOF_OID";

        if (sortField.equals("AOF_OID")) {
        	sqlSortField = "AOF_OID";       
        }
        if (sortField.equals("AOF_FILE_NAME")) {
        	sqlSortField = "AOF_FILE_NAME";      
        }
        if (sortField.equals("AOF_FILE_VALIDATION")) {
        	sqlSortField = "AOF_FILE_VALIDATION";       
        }
        if (sortField.equals("AOF_FILE_AUTHOR")) {
        	sqlSortField = "AOF_FILE_AUTHOR";      
        }
        if (sortField.equals("AOF_FILE_CONTENT_TYPE")) {
        	sqlSortField = "AOF_FILE_CONTENT_TYPE";         
        }
        if (sortField.equals("AOF_FILE_CONTENT_LENGTH")) {
        	sqlSortField = "AOF_FILE_CONTENT_LENGTH";         
        }
        if (sortField.equals("AOF_FILE_CONTENT_DATE")) {
        	sqlSortField = "AOF_FILE_CONTENT_DATE";         
        }
        if (sortField.equals("AOF_TEXT_REPORT_NAME")) {
        	sqlSortField = "AOF_TEXT_REPORT_NAME";         
        }
        if (sortField.equals("AOF_TEXT_REPORT_TYPE")) {
        	sqlSortField = "AOF_TEXT_REPORT_TYPE";         
        }
        if (sortField.equals("AOF_TEXT_REPORT_LENGTH")) {
        	sqlSortField = "AOF_TEXT_REPORT_LENGTH";         
        }
        if (sortField.equals("AOF_TEXT_REPORT_DATE")) {
        	sqlSortField = "AOF_TEXT_REPORT_DATE";         
        }
        if (sortField.equals("AOF_PDF_REPORT_NAME")) {
        	sqlSortField = "AOF_PDF_REPORT_NAME";         
        }
        if (sortField.equals("AOF_PDF_REPORT_TYPE")) {
        	sqlSortField = "AOF_PDF_REPORT_TYPE";         
        }
        if (sortField.equals("AOF_PDF_REPORT_LENGTH")) {
        	sqlSortField = "AOF_PDF_REPORT_LENGTH";         
        }
        if (sortField.equals("AOF_PDF_REPORT_DATE")) {
        	sqlSortField = "AOF_PDF_REPORT_DATE";         
        }
        
        String searchWithWildCards = "%" + searchTerm + "%";
        String tableWithWildCards = "%" + searchTable + "%";

        Object[] values = {
        		searchWithWildCards,
        		tableWithWildCards,
                firstRow, 
                rowCount
        };

        String sortDirection = sortAscending ? "ASC" : "DESC";
        String sql = String.format(SQL_DISPLAY_BY_ORDER_AND_LIMIT, sqlSortField, sortDirection);
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        List<OBOFile> dataList = new ArrayList<OBOFile>();
        
        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getLevel(), daoFactory.getSqloutput(), connection, sql, false, values);

            resultSet = preparedStatement.executeQuery();
        
            while (resultSet.next()) {
            	
                dataList.add(mapOBOFile(resultSet));
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
    public int count(String searchTerm, String searchTable) throws Exception {

        String searchWithWildCards = "%" + searchTerm + "%";
        String tableWithWildCards = "%" + searchTable + "%";

        Object[] values = {
        		searchWithWildCards,
        		tableWithWildCards
        };

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int count = 0;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getLevel(), daoFactory.getSqloutput(), connection, SQL_ROW_COUNT_BY_ORDER_AND_LIMIT, false, values);
            
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
    private static OBOFile mapOBOFile(ResultSet resultSet) throws SQLException {

    	return new OBOFile(
      		resultSet.getLong("AOF_OID"), 
       		resultSet.getString("AOF_FILE_NAME"),
       		"",
       		resultSet.getString("AOF_FILE_CONTENT_TYPE"),
       		resultSet.getLong("AOF_FILE_CONTENT_LENGTH"),
       		resultSet.getString("AOF_FILE_CONTENT_DATE"),
       		resultSet.getString("AOF_FILE_VALIDATION"), 
      		resultSet.getString("AOF_FILE_AUTHOR"),
       		resultSet.getString("AOF_TEXT_REPORT_NAME"),
      		"",
       		resultSet.getString("AOF_TEXT_REPORT_TYPE"),
       		resultSet.getLong("AOF_TEXT_REPORT_LENGTH"),
       		resultSet.getString("AOF_TEXT_REPORT_DATE"),
       		resultSet.getString("AOF_PDF_REPORT_NAME"),
       		"",
       		resultSet.getString("AOF_PDF_REPORT_TYPE"),
       		resultSet.getLong("AOF_PDF_REPORT_LENGTH"),
       		resultSet.getString("AOF_PDF_REPORT_DATE")
        );
    }
    
    /*
     * Map the current row of the given ResultSet to an User.
     */
    private static OBOFile mapOBOFileWithBinary(ResultSet resultSet) throws SQLException {

    	return new OBOFile(
      		resultSet.getLong("AOF_OID"), 
       		resultSet.getString("AOF_FILE_NAME"),
       		resultSet.getBinaryStream("AOF_FILE_CONTENT"),
       		resultSet.getString("AOF_FILE_CONTENT_TYPE"),
       		resultSet.getLong("AOF_FILE_CONTENT_LENGTH"),
       		resultSet.getString("AOF_FILE_CONTENT_DATE"),
       		resultSet.getString("AOF_FILE_VALIDATION"), 
      		resultSet.getString("AOF_FILE_AUTHOR"),
       		resultSet.getString("AOF_TEXT_REPORT_NAME"),
       		resultSet.getBinaryStream("AOF_TEXT_REPORT"),
       		resultSet.getString("AOF_TEXT_REPORT_TYPE"),
       		resultSet.getLong("AOF_TEXT_REPORT_LENGTH"),
       		resultSet.getString("AOF_TEXT_REPORT_DATE"),
       		resultSet.getString("AOF_PDF_REPORT_NAME"),
       		resultSet.getBinaryStream("AOF_PDF_REPORT"),
       		resultSet.getString("AOF_PDF_REPORT_TYPE"),
       		resultSet.getLong("AOF_PDF_REPORT_LENGTH"),
       		resultSet.getString("AOF_PDF_REPORT_DATE")
        );
    }
}
