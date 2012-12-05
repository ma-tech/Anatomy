/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyOBO
*
* Title:        DatabaseException.java
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
* Description:  This class represents a generic Database exception.
*                It should wrap any exception of the underlying code, such as SQLExceptions.
*                
* Maintenance:  Log changes below, with most recent at top of list.
*
* Who; When; What;
*
* Mike Wicks; November 2012; Create Class
*
*----------------------------------------------------------------------------------------------
*/
package routines.database;

public class DatabaseException extends Exception {
    // Constructors -------------------------------------------------------------------------------
    /*
     * Constructs a DatabaseException with the given detail message.
     */
    public DatabaseException(String message) {

    	super(message);
    }

    /*
     * Constructs a DatabaseException with the given root cause.
     */
    public DatabaseException(Throwable cause) {
        
    	super(cause);
    }

    /*
     * Constructs a DatabaseException with the given detail message and root cause.
     */
    public DatabaseException(String message, Throwable cause) {
        
    	super(message, cause);
    }
}
