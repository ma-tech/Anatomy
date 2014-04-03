/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayer
*
* Title:        DAOFactory.java
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
* Description:  
* 
* This class represents a Data Access Object factory for a SQL database. 
* 
*  You can use "getInstance(String)" to obtain a new instance for the given database name. 
*  The specific instance returned depends on the properties file configuration. 
*  You can obtain Data Access Objects for the Data Access Object factory instance using the Data Access Object getters.
* 
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
package daolayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.sql.DataSource;
import javax.naming.InitialContext;

import java.util.ArrayList;
import java.util.Iterator;

import utility.Wrapper;
import utility.ExecuteCommand;

import daointerface.BaseDAO;

public abstract class DAOFactory {
    // Constants ----------------------------------------------------------------------------------
    private static final String PROPERTIES_FILE = "dao.properties";
    private static final String JNDI_ROOT = "java:comp/env/";
    
    // Actions ------------------------------------------------------------------------------------

    public <DAO extends BaseDAO> DAO getDAOImpl(Class<DAO> daoInterface)
            throws DAOConfigurationException
        {
            String daoInterfaceName = daoInterface.getName();

            if (!daoInterface.isInterface()) {
            	
                throw new DAOConfigurationException("Class '" + daoInterfaceName + "'"
                    + " is actually not an Interface.");
            }
            
            String daoClassName = "daojdbc." + daoInterface.getSimpleName() + this.getAccessMethod();
            
            DAO daoImplementation;

            try {
            	
                daoImplementation = daoInterface.cast(Class.forName(daoClassName).newInstance());
            } 
            catch (ClassNotFoundException e) {
            	
                throw new DAOConfigurationException("DAO class '" + daoClassName
                    + "' is missing in classpath. Verify the class or the '" + daoInterfaceName
                    + "' property.", e);
            }
            catch (IllegalAccessException e) {
            	
                throw new DAOConfigurationException("DAO class '" + daoClassName
                    + "' cannot be accessed. Verify the class or the '" + daoInterfaceName
                    + "' property.", e);
            }
            catch (InstantiationException e) {
            	
                throw new DAOConfigurationException("DAO class '" + daoClassName
                    + "' cannot be instantiated. Verify the class or the '" + daoInterfaceName
                    + "' property.", e);
            }
            catch (ClassCastException e) {
            	
                throw new DAOConfigurationException("DAO class '" + daoClassName
                    + "' does not implement '" + daoInterfaceName + "'. Verify the class or the '"
                    + daoInterfaceName + "' property.", e);
            }

            daoImplementation.setDAOFactory(this);

            return daoImplementation;
        }



    /*
     * Returns a new DAOFactory instance for the given database name.
     */
    public static DAOFactory getInstance(String name) throws DAOConfigurationException {
    	
        DAOFactory instance = null;

        if (name == null) {
        	
            throw new DAOConfigurationException("Database name is null.");
        }

        DAOProperty daoproperty = DAOProperty.findProperties(PROPERTIES_FILE, name);
        
        String strFilename = PROPERTIES_FILE;
        
        String strMajorKey = daoproperty.getMajorKey();

        String strAccessMethod = daoproperty.getAccess();
        String strDebug = daoproperty.getDebug();
        String strDriverClassName = daoproperty.getDriver();
        String strMsgLevel = daoproperty.getMsglevel();
        String strPassword = daoproperty.getPassword();
        String strSqloutput = daoproperty.getSqloutput();
        String strUpdate = daoproperty.getUpdate();
        String strUrl = daoproperty.getUrl();
        String strUsername = daoproperty.getUsername();
        
    	try {
            
            if (daoproperty.isDebug()) {
            
    			Wrapper.printMessage("=====     :", "*", strMsgLevel);
            	Wrapper.printMessage("DEBUG     : Data Access Object Properties File     : " + strFilename, "*", strMsgLevel);
            	Wrapper.printMessage("-----     : -------------------", "*", strMsgLevel);
            	Wrapper.printMessage("          : MajorKey                : " + strMajorKey, "*", strMsgLevel);
            	Wrapper.printMessage("          : --------                :", "*", strMsgLevel);
            	Wrapper.printMessage("          : access                  : " + strAccessMethod, "*", strMsgLevel);
            	Wrapper.printMessage("          : debug                   : " + strDebug, "*", strMsgLevel);
            	Wrapper.printMessage("          : driver                  : " + strDriverClassName, "*", strMsgLevel);
            	Wrapper.printMessage("          : msglevel                : " + strMsgLevel, "*", strMsgLevel);
            	Wrapper.printMessage("          : password                : " + strPassword, "*", strMsgLevel);
            	Wrapper.printMessage("          : sqloutput               : " + strSqloutput, "*", strMsgLevel);
            	Wrapper.printMessage("          : update                  : " + strUpdate, "*", strMsgLevel);
            	Wrapper.printMessage("          : url                     : " + strUrl, "*", strMsgLevel);
            	Wrapper.printMessage("          : username                : " + strUsername, "*", strMsgLevel);
            }

            if ( daoproperty.isDebug() ) {
            	
                if ( strSqloutput != null) {
                	
                    String commandString1 = "rm " + strSqloutput;
                    ArrayList<String> results = new ArrayList<String>();
                    
            		results = ExecuteCommand.execute(commandString1);

                	Wrapper.printMessage("--------- :", "*", strMsgLevel);
                	Wrapper.printMessage("          : Empty SQL Command Log File", "*", strMsgLevel);
                	Wrapper.printMessage("          : Command: " + commandString1, "*", strMsgLevel);

                	Iterator<String> iteratorresults = results.iterator();

                  	while (iteratorresults.hasNext()) {
                  		
                    	Wrapper.printMessage("                 " + iteratorresults.next(), "*", strMsgLevel);
                  	}
                }

                Wrapper.printMessage("========= :", "*", strMsgLevel);
            }

            // If driver is specified, then load it to let it register itself with DriverManager.
            if (strDriverClassName != null) {
                
            	Class.forName(strDriverClassName);

            	instance = new DriverManagerDAOFactory(strAccessMethod, 
            			strMsgLevel, 
            			daoproperty.isUpdate(), 
            			daoproperty.isDebug(), 
            			strSqloutput, 
            			strUrl, 
            			strUsername, 
            			strPassword);
                
            }
            // Else assume URL as DataSource URL and lookup it in the JNDI.
            else {
            	
                DataSource dataSource;
                
                dataSource = (DataSource) new InitialContext().lookup(JNDI_ROOT + strUrl);
                
                if (strUsername != null) {
                	
                    instance = new DataSourceWithLoginDAOFactory(strAccessMethod,
                    		strMsgLevel, 
                    		daoproperty.isUpdate(), 
                    		daoproperty.isDebug(), 
                    		strSqloutput, 
                    		dataSource, 
                    		strUsername, 
                    		strPassword);
                }
                else {
                	
                    instance = new DataSourceDAOFactory(strAccessMethod, 
                    		strMsgLevel, 
                    		daoproperty.isUpdate(), 
                    		daoproperty.isDebug(), 
                    		strSqloutput, 
                    		dataSource);
                }
            }
		} 
    	catch (Exception e) {

			e.printStackTrace();
		}

        return instance;
    }

    /*
     * Returns a connection to the database. 
     *  Package private so that it can be used inside the Data Access Object package only.
     */
    public abstract Connection getConnection() throws SQLException;
    public abstract boolean isDebug();
    public abstract boolean isUpdate();
    public abstract String getUrl();
    public abstract String getSqloutput();
    public abstract String getMsgLevel();
    public abstract String getAccessMethod();

}

// Default DAOFactory implementations -------------------------------------------------------------
/*
 * The DriverManager based DAOFactory.
 */
class DriverManagerDAOFactory extends DAOFactory {
    
	private String url;
    private String username;
    private String password;
	private boolean debug;
	private boolean update;
    private String sqloutput;
    private String level;
    private String access;
	
    DriverManagerDAOFactory(String access, String level, boolean update, boolean debug, String sqloutput, String url, String username, String password) {
    
    	this.url = url;
        this.username = username;
        this.password = password;
        this.debug = debug;
        this.update = update;
        this.sqloutput = sqloutput;
        this.level = level;
        this.access = access;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
    public boolean isDebug() {
        return debug;
    }
    public boolean isUpdate() {
        return update;
    }
    public String getUrl() {
        return url;
    }
    public String getSqloutput() {
        return sqloutput;
    }
    public String getMsgLevel() {
        return level;
    }
    public String getAccessMethod() {
        return access;
    }
}

/*
 * The DataSource based DAOFactory.
 */
class DataSourceDAOFactory extends DAOFactory {
  
	private DataSource dataSource;
	private boolean debug;
	private boolean update;
    private String sqloutput;
    private String level;
    private String access;

    DataSourceDAOFactory(String access, String level, boolean update, boolean debug, String sqloutput, DataSource dataSource) {

    	this.dataSource = dataSource;
        this.debug = debug;
        this.update = update;
        this.sqloutput = sqloutput;
        this.level = level;
        this.access = access;
    }

    public Connection getConnection() throws SQLException {
    	return dataSource.getConnection();
    }
    public boolean isDebug() {
        return debug;
    }
    public boolean isUpdate() {
        return update;
    }
    public String getUrl() {
        return "";
    }
    public String getSqloutput() {
        return sqloutput;
    }
    public String getMsgLevel() {
        return level;
    }
    public String getAccessMethod() {
        return access;
    }
}

/*
 * The DataSource-with-Login based DAOFactory.
 */
class DataSourceWithLoginDAOFactory extends DAOFactory {
  
	private DataSource dataSource;
    private String username;
    private String password;
	private boolean debug;
	private boolean update;
    private String sqloutput;
    private String level;
    private String access;

    DataSourceWithLoginDAOFactory(
    		String access, 
    		String level, 
    		boolean update, 
    		boolean debug, 
    		String sqloutput, 
    		DataSource dataSource, 
    		String username, 
    		String password) {
    
    	this.dataSource = dataSource;
        this.username = username;
        this.password = password;
        this.debug = debug;
        this.update = update;
        this.sqloutput = sqloutput;
        this.level = level;
        this.access = access;
    }

    public Connection getConnection() throws SQLException {
    	return dataSource.getConnection(username, password);
    }
    public boolean isDebug() {
        return debug;
    }
    public boolean isUpdate() {
        return update;
    }
    public String getUrl() {
        return "";
    }
    public String getSqloutput() {
        return sqloutput;
    }
    public String getMsgLevel() {
        return level;
    }
    public String getAccessMethod() {
        return access;
    }
}
