/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayer
*
* Title:        UserDAO.java
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
* Description:  This class represents a SQL Database Access Object for the User DTO.
*  
*               This Data Access Object should be used as a central point for the mapping between 
*                the User DTO and a SQL database.
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

import daomodel.User;

import daointerface.UserDAO;

import daolayer.DAOFactory;
import daolayer.DAOException;

import static daolayer.DAOUtil.*;

public final class UserDAOJDBC implements UserDAO {
    // Constants ----------------------------------------------------------------------------------
    private static final String SQL_FIND_BY_ID =
        "SELECT AOU_OID, AOU_NAME, AOU_PASSWORD, AOU_EMAIL, AOU_ORGANISATION " +
        "FROM ANA_OBO_USER " +
        "WHERE AOU_OID = ?";
    
    private static final String SQL_FIND_BY_USERNAME_AND_PASSWORD =
        "SELECT AOU_OID, AOU_NAME, AOU_PASSWORD, AOU_EMAIL, AOU_ORGANISATION " +
        "FROM ANA_OBO_USER " +
        "WHERE AOU_NAME = ? " +
        "AND AOU_PASSWORD = ?";
    
    private static final String SQL_LIST_ORDER_BY_ID =
        "SELECT AOU_OID, AOU_NAME, AOU_PASSWORD, AOU_EMAIL, AOU_ORGANISATION " +
        "FROM ANA_OBO_USER " +
        "ORDER BY AOU_OID";
    
    private static final String SQL_INSERT =
        "INSERT INTO ANA_OBO_USER " +
        "(AOU_NAME, AOU_PASSWORD, AOU_EMAIL, AOU_ORGANISATION) " +
        "VALUES (?, ?, ?, ?)";
    
    private static final String SQL_UPDATE =
        "UPDATE ANA_OBO_USER " +
        "SET AOU_NAME = ?, " +
        "AOU_PASSWORD = ?, " +
        "AOU_EMAIL = ?, " +
        "AOU_ORGANISATION = ? " +
        "WHERE AOU_OID = ?";
    
    private static final String SQL_DELETE =
        "DELETE FROM ANA_OBO_USER " +
        "WHERE AOU_OID = ?";
    
    private static final String SQL_EXIST_USERNAME =
        "SELECT AOU_OID " +
        "FROM ANA_OBO_USER " +
        "WHERE AOU_NAME = ?";
    
    private static final String SQL_EXIST_EMAIL =
        "SELECT AOU_OID " +
        "FROM ANA_OBO_USER " +
        "WHERE AOU_EMAIL = ?";

    private static final String SQL_EXIST_OID =
            "SELECT AOU_OID " +
            "FROM ANA_OBO_USER " +
            "WHERE AOU_OID = ?";

    // Vars ---------------------------------------------------------------------------------------
    private DAOFactory daoFactory;

    // Constructors -------------------------------------------------------------------------------
    /*
     * Construct an User Data Access Object for the given DAOFactory. Package private so that it can be constructed
     * inside the Data Access Object package only.
     */
    public UserDAOJDBC() {
    	
    }
    
    public UserDAOJDBC(DAOFactory daoFactory) {
    	
        this.daoFactory = daoFactory;
    }

    // Actions ------------------------------------------------------------------------------------
	public void setDAOFactory(DAOFactory daofactory) {

		this.daoFactory = daofactory;
	}
    
    /*
     * Returns the user from the database matching the given OID, otherwise null.
     */
    public User findByOid(long oid) throws Exception {
    	
        return find(SQL_FIND_BY_ID, oid);
    }

    /*
     * Returns the user from the database matching the given username and password, otherwise null.
     */
    public User find(String username, String password) throws Exception {
    	
        return find(SQL_FIND_BY_USERNAME_AND_PASSWORD, username, hashMD5(password));
    }

    /*
     * Returns the user from the database matching the given SQL query with the given values.
     */
    private User find(String sql, Object... values) throws Exception {
    	
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, sql, false, values);
            resultSet = preparedStatement.executeQuery();
            
            if (resultSet.next()) {
            	
                user = mapUser(resultSet);
            }
        } 
        catch (SQLException e) {
        	
            throw new DAOException(e);
        } 
        finally {
        	
            close(daoFactory.getMsgLevel(), connection, preparedStatement, resultSet);
        }

        return user;
    }

    /*
     * Returns a list of all users from the database ordered by user OID. 
     *  The list is never null and is empty when the database does not contain any user.
     */
    public List<User> list() throws Exception {
    	
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<User> users = new ArrayList<User>();

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement(SQL_LIST_ORDER_BY_ID);
            resultSet = preparedStatement.executeQuery();
            
            while (resultSet.next()) {
            	
                users.add(mapUser(resultSet));
            }
        } 
        catch (SQLException e) {
        	
            throw new DAOException(e);
        } 
        finally {
        	
            close(daoFactory.getMsgLevel(), connection, preparedStatement, resultSet);
        }

        return users;
    }

    /*
     * Create the given user in the database. 
     * 
     * The user OID must be null, otherwise it will throw IllegalArgumentException. 
     *  If the user OID value is unknown, rather use save(User).
     *  After creating, the Data Access Object will set the obtained OID in the given user.
     */
    public void create(User user) throws IllegalArgumentException, Exception {
        
    	if (user.getOid() != null) {
    		
            throw new IllegalArgumentException("User is already created, the user OID is not null.");
        }

        Object[] values = {
            user.getUsername(),
            hashMD5IfNecessary(user.getPassword()),
            user.getEmail(),
            user.getOrganisation()
        };

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet generatedKeys = null;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, SQL_INSERT, true, values);
            int affectedRows = preparedStatement.executeUpdate();
            
            if (affectedRows == 0) {
            	
                throw new DAOException("Creating user failed, no rows affected.");
            }
            generatedKeys = preparedStatement.getGeneratedKeys();
            
            if (generatedKeys.next()) {
            	
                user.setOid(generatedKeys.getLong(1));
            } 
            else {
            	
                throw new DAOException("Creating user failed, no generated key obtained.");
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
     * Update the given user in the database. 
     *  The user OID must not be null, otherwise it will throw IllegalArgumentException. 
     *  If the user OID value is unknown, rather use save(User).
     */
    public void update(User user) throws Exception {
    	
        if (user.getOid() == null) {
        	
            throw new IllegalArgumentException("User is not created yet, the user OID is null.");
        }

        Object[] values = {
            user.getUsername(),
            hashMD5IfNecessary(user.getPassword()),
            user.getEmail(),
            user.getOrganisation(),
            user.getOid()
        };

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, SQL_UPDATE, false, values);
            int affectedRows = preparedStatement.executeUpdate();
            
            if (affectedRows == 0) {
            	
                throw new DAOException("Updating user failed, no rows affected.");
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
     * Save the given user in the database. 
     *  If the user OID is null, then it will invoke create(User), else it will invoke update(User).
     */
    public void save(User user) throws Exception {
    	
        if (user.getOid() == null) {
        
        	create(user);
        } 
        else {
        	
            update(user);
        }
    }

    /*
     * Delete the given user from the database. 
     *  After deleting, the Data Access Object will set the ID of the given user to null.
     */
    public void delete(User user) throws Exception {

    	Object[] values = { user.getOid() };

        if (user.getOid() == null) {
        	
            throw new IllegalArgumentException("User is not created yet, the user OID is null.");
        }

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, SQL_DELETE, false, values);
            int affectedRows = preparedStatement.executeUpdate();
            
            if (affectedRows == 0) {
            	
                throw new DAOException("Deleting user failed, no rows affected.");
            }
            else {
            	
                user.setOid(null);
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
     * Returns true if the given oid exist in the database.
     */
    public boolean existOid(long oid) throws Exception {

    	return exist(SQL_EXIST_OID, oid);
    }

    /*
     * Returns true if the given username exist in the database.
     */
    public boolean existUsername(String username) throws Exception {

    	return exist(SQL_EXIST_USERNAME, username);
    }

    /*
     * Returns true if the given email address exist in the database.
     */
    public boolean existEmail(String email) throws Exception {

    	return exist(SQL_EXIST_EMAIL, email);
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
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, sql, false, values);
            resultSet = preparedStatement.executeQuery();
            exist = resultSet.next();
        }
        catch (SQLException e) {
        	
            throw new DAOException(e);
        } 
        finally {
        	
            close(daoFactory.getMsgLevel(), connection, preparedStatement, resultSet);
        }

        return exist;
    }

    // Helpers ------------------------------------------------------------------------------------
    /*
     * Generate MD5 hash for the given password if necessary. That is, if it is not already hashed.
     */
    private static String hashMD5IfNecessary(String password) {

    	return !"^[a-f0-9]{32}$".matches(password) ? hashMD5(password) : password;
    }

    /*
     * Map the current row of the given ResultSet to an User.
     */
    private static User mapUser(ResultSet resultSet) throws SQLException {

    	return new User(
            resultSet.getLong("AOU_OID"),
            resultSet.getString("AOU_NAME"),
            resultSet.getString("AOU_PASSWORD"),
            resultSet.getString("AOU_EMAIL"),
            resultSet.getString("AOU_ORGANISATION")
        );
    }
}
