/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyRebuild
*
* Title:        DAOUtil.java
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
* Description:  Utility class for DAO's.
* 
*               This class contains commonly used DAO logic which has been re-factored into
*                single static methods.
*                 
*               It contains:
*                - a PreparedStatement values setter
*                - several quiet close methods
*                - a MD5 hasher which conforms to MySQL's own md5() function.
*
*  Link:         http://balusc.blogspot.com/2008/07/dao-tutorial-data-layer.html
* 
* Maintenance:  Log changes below, with most recent at top of list.
*
* Who; When; What;
*
* Mike Wicks; February 2012; Create Class
*
*----------------------------------------------------------------------------------------------
*/

package DAOLayer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class DAOUtil {

    // Constructors -------------------------------------------------------------------------------
    private DAOUtil() {
        // Utility class, hide constructor.
    }

    // Actions ------------------------------------------------------------------------------------
    /*
     * Returns a PreparedStatement of the given connection, set with the given SQL query and the
     * given parameter values.
     */
    public static PreparedStatement prepareStatement
        (Boolean debug, String sqloutput, Connection connection, String sql, boolean returnGeneratedKeys, Object... values)
            throws SQLException {
        
    	PreparedStatement preparedStatement = connection.prepareStatement(sql,
            returnGeneratedKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS);
        
        setValues(preparedStatement, values);
        
        if (debug && sqloutput == null) {
            String strArray [] = preparedStatement.toString().split(": ");
        	System.out.println("DEBUG: " + strArray[1] + ";");
        }
        
        if (debug && sqloutput != null) {
        	try {
                BufferedWriter outputFile =
                        new BufferedWriter(new FileWriter(sqloutput, true));
                String strArray [] = preparedStatement.toString().split(": ");
                outputFile.write(strArray[1] + ";\n");
                outputFile.close();
        	}
        	catch(IOException io) {
        		io.printStackTrace();
        	}
        		
        }
        
        return preparedStatement;
    }

    /*
     * Set the given parameter values in the given PreparedStatement.
     */
    public static void setValues(PreparedStatement preparedStatement, Object... values)
        throws SQLException {
        
    	for ( int i = 0; i < values.length; i++ ) {
            preparedStatement.setObject(i + 1, values[i]);
        }
    }

    /*
     * Quietly close the Connection. Any errors will be printed to the stderr.
     */
    public static void close(Connection connection) {
        
    	if ( connection != null ) {
            try {
                connection.close();
            }
            catch (SQLException e) {
                System.err.println("Closing Connection failed: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    /*
     * Quietly close the Statement. Any errors will be printed to the stderr.
     */
    public static void close(Statement statement) {
        
    	if (statement != null) {
            try {
                statement.close();
            } 
            catch (SQLException e) {
                System.err.println("Closing Statement failed: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    /*
     * Quietly close the ResultSet. Any errors will be printed to the stderr.
     */
    public static void close(ResultSet resultSet) {
        
    	if (resultSet != null) {
            try {
                resultSet.close();
            }
            catch (SQLException e) {
                System.err.println("Closing ResultSet failed: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    /*
     * Quietly close the Connection and Statement. Any errors will be printed to the stderr.
     */
    public static void close(Connection connection, Statement statement) {
        
    	close(statement);
        close(connection);
    }

    /*
     * Quietly close the Connection, Statement and ResultSet. Any errors will be printed to the stderr.
     */
    public static void close(Connection connection, Statement statement, ResultSet resultSet) {
        
    	close(resultSet);
        close(statement);
        close(connection);
    }

    /*
     * Generate MD5 hash for the given String. MD5 is kind of an one-way encryption. 
     *  Very useful for Hashing passwords before saving in database. 
     *  This function generates exactly the same hash as MySQL's own md5() function 
     *   should do.
     */
    public static String hashMD5(String string) {
        
    	byte[] hash;

        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        }
        catch (NoSuchAlgorithmException e) {
            // Unexpected exception. "MD5" is just hardcoded and supported.
            throw new RuntimeException("MD5 should be supported?", e);
        }
        catch (UnsupportedEncodingException e) {
            // Unexpected exception. "UTF-8" is just hardcoded and supported.
            throw new RuntimeException("UTF-8 should be supported?", e);
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        
        for (byte b : hash) {
            if ((b & 0xff) < 0x10) {
            	hex.append("0");
            }
            hex.append(Integer.toHexString(b & 0xff));
        }
        
        return hex.toString();
    }
}
