/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomy
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
* Version: 1
*
* Description:  This interface represents a contract for a DAO for the User model.
*  
*               This DAO should be used as a central point for the mapping between 
*                the User DTO and a SQL database.
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

import daomodel.User;

public interface UserDAO extends BaseDAO {
    // Actions ------------------------------------------------------------------------------------
    /*
     * Returns the user from the database matching the given OID, otherwise null.
     */
    public User find(Long oid) throws Exception;

    /*
     * Returns the user from the database matching the given username and password, otherwise null.
     */
    public User find(String username, String password) throws Exception;
 
    /*
     * Returns a list of all users from the database ordered by user OID. 
     *  The list is never null and is empty when the database does not contain any user.
     */
    public List<User> list() throws Exception;

    /*
     * Create the given user in the database. 
     * 
     * The user OID must be null, otherwise it will throw IllegalArgumentException. 
     *  If the user OID value is unknown, rather use save(User).
     *  After creating, the DAO will set the obtained OID in the given user.
     */
    public void create(User user) throws IllegalArgumentException, Exception;

    /*
     * Update the given user in the database. 
     *  The user OID must not be null, otherwise it will throw IllegalArgumentException. 
     *  If the user OID value is unknown, rather use save(User).
     */
    public void update(User user) throws Exception;

    /*
     * Save the given user in the database. 
     *  If the user OID is null, then it will invoke create(User), else it will invoke update(User).
     */
    public void save(User user) throws Exception;

    /*
     * Delete the given user from the database. 
     *  After deleting, the DAO will set the ID of the given user to null.
     */
    public void delete(User user) throws Exception;

    /*
     * Returns true if the given username exist in the database.
     */
    public boolean existUsername(String username) throws Exception;

    /*
     * Returns true if the given email address exist in the database.
     */
    public boolean existEmail(String email) throws Exception;

}
