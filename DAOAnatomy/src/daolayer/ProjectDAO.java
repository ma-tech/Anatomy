/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomy008
*
* Title:        ProjectDAO.java
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
* Description:  This class represents a SQL Database Access Object for the Project DTO.
*  
*               This DAO should be used as a central point for the mapping between 
*                the Project DTO and a SQL database.
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

import daomodel.Project;

public final class ProjectDAO {

    // Constants ----------------------------------------------------------------------------------
    private static final String SQL_LIST_ALL =
        "SELECT AJP_NAME " +
        "FROM ANA_PROJECT ";
    
    // Vars ---------------------------------------------------------------------------------------
    private DAOFactory daoFactory;
    
    // Constructors -------------------------------------------------------------------------------
    /*
     * Construct a Project DAO for the given DAOFactory.
     *  Package private so that it can be constructed inside the DAO package only.
     */
    ProjectDAO(DAOFactory daoFactory) {

    	this.daoFactory = daoFactory;
    }
    
    // Actions ------------------------------------------------------------------------------------
    /*
     * Returns a list of ALL versions, otherwise null.
     */
    public List<Project> listAll() throws DAOException {
    	
        return list(SQL_LIST_ALL);
        
    }
    
    /*
     * Returns a list of all versions from the database. 
     *  The list is never null and is empty when the database does not contain any versions.
     */
    public List<Project> list(String sql, Object... values) throws DAOException {
     
    	Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Project> versions = new ArrayList<Project>();

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.isDebug(), daoFactory.getSqloutput(), connection, sql, false, values);
            resultSet = preparedStatement.executeQuery();
        
            while (resultSet.next()) {
                versions.add(mapProject(resultSet));
            }
        } 
        catch (SQLException e) {
            throw new DAOException(e);
        } 
        finally {
            close(connection, preparedStatement, resultSet);
        }

        return versions;
    }

    // Helpers ------------------------------------------------------------------------------------
    /*
     * Map the current row of the given ResultSet to an User.
     */
    private static Project mapProject(ResultSet resultSet) throws SQLException {
      
    	return new Project(
       		resultSet.getString("APJ_NAME")
        );
    }
}
