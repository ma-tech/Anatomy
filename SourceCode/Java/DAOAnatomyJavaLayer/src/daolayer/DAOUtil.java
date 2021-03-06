/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayer
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
* Version:      1
*
* Description:  utility class for Data Access Objects.
* 
*               This class contains commonly used Data Access Object logic which has been re-factored into
*                single static methods.
*                 
*               It contains:
*                - a PreparedStatement values setter
*                - several quiet close methods
*                - a MD5 hasher which conforms to MySQL's own md5() function.
*
*  Link:         
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

import utility.Wrapper;

public final class DAOUtil {

    // Constructors -------------------------------------------------------------------------------
    private DAOUtil() {
        // utility class, hide constructor.
    }

    // Actions ------------------------------------------------------------------------------------
    /*
     * Returns a PreparedStatement of the given connection, set with the given SQL query and the
     * given parameter values.
     */
    public static PreparedStatement prepareStatement(
    		String level,
    		String sqloutput, 
    		Connection connection, 
    		String sql, 
    		boolean returnGeneratedKeys, 
    		Object... values) throws Exception {
        
    	PreparedStatement preparedStatement = connection.prepareStatement(sql,
            returnGeneratedKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS);
        
        setValues(preparedStatement, values);

        String strArray [] = preparedStatement.toString().split(": ");
        
        Wrapper.printMessage("daoutil.prepareStatement;DEBUG " + strArray[1] + ";", "*****", level);
        
        if ( sqloutput != null) {
        	
        	try {

        		BufferedWriter outputFile =
                        new BufferedWriter(new FileWriter(sqloutput, true));
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
    public static void close(String level, Connection connection) throws Exception {
        
    	if ( connection != null ) {
    		
            try {
            	
                connection.close();
            }
            catch (SQLException e) {
            	
                Wrapper.printMessage("daoutil.prepareStatement;Closing Connection failed: " + e.getMessage(), "*", level);
                e.printStackTrace();
            }
        }
    }

    /*
     * Quietly close the Statement. Any errors will be printed to the stderr.
     */
    public static void close(String level, Statement statement) throws Exception {
        
    	if (statement != null) {
    		
            try {
            	
                statement.close();
            } 
            catch (SQLException e) {
            	
                Wrapper.printMessage("daoutil.prepareStatement;Closing Statement failed: " + e.getMessage(), "*", level);
                e.printStackTrace();
            }
        }
    }

    /*
     * Quietly close the ResultSet. Any errors will be printed to the stderr.
     */
    public static void close(String level, ResultSet resultSet) throws Exception {
        
    	if (resultSet != null) {
    		
            try {
                resultSet.close();
            }
            catch (SQLException e) {
            	
                Wrapper.printMessage("daoutil.close;Closing ResultSet failed:  "  + e.getMessage(), "*", level);
                e.printStackTrace();
            }
        }
    }

    /*
     * Quietly close the Connection and Statement. Any errors will be printed to the stderr.
     */
    public static void close(String level, Connection connection, Statement statement) throws Exception {
        
    	close(level, statement);
        close(level, connection);
    }

    /*
     * Quietly close the Connection, Statement and ResultSet. Any errors will be printed to the stderr.
     */
    public static void close(String level, Connection connection, Statement statement, ResultSet resultSet) throws Exception {
        
    	close(level, resultSet);
        close(level, statement);
        close(level, connection);
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
