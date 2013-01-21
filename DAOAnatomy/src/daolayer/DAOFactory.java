/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomy
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
* Version: 1
*
* Description:  
* 
* This class represents a DAO factory for a SQL database. 
* 
*  You can use "getInstance(String)" to obtain a new instance for the given database name. 
*  The specific instance returned depends on the properties file configuration. 
*  You can obtain DAO's for the DAO factory instance using the DAO getters.
* 
* This class requires a properties file named 'dao.properties' in the classpath with under each
* the following properties:
* 
*  name.url *
*  name.driver
*  name.username
*  name.password
*  name.debug *
*  name.update *
* 
* Those marked with * are required, others are optional and can be left away or empty. 
* 
* Only the username is required when any password is specified.
* 
*  The 'name' must represent the database name in "getInstance(String)".
*  
*  The 'name.url' must represent either the JDBC URL or JNDI name of the database.
*  The 'name.driver' must represent the full qualified class name of the JDBC driver.
*  The 'name.username' must represent the username of the database login.
*  The 'name.password' must represent the password of the database login.
* 
*  The 'name.debug' states whether the factory is to print out SQL statements and other Information
*   to System.out.
*  The 'name.update' states whether the factory is to UPDATE the database.
*  
*  
* If you specify the driver property, then the url property will be assumed as JDBC URL.
*  
* If you omit the driver property, then the url property will be assumed as JNDI name. 
* 
* When using JNDI with username/password preconfigured, you can omit the username and 
*  password properties as well.
* 
* Here are basic examples of valid properties for a MySQL database with the name 'anatomy008':
* 
*  anatomy008.url = jdbc:mysql://localhost:3306/anatomy008
*  anatomy008.driver = com.mysql.jdbc.Driver
*  anatomy008.username = root
*  anatomy008.password = banana
* 
*  anatomy008.jndi.url = jdbc/anatomy008
* 
* Here is a basic use example:
* 
*  DAOFactory javabase = DAOFactory.getInstance("anatomy008");
*  NodeDAO nodeDAO = javabase.getnodeDAO();
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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.sql.DataSource;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import utility.ExecuteCommand;
import utility.Wrapper;

import daointerface.BaseDAO;

/*
import daointerface.NodeDAO;

import daojdbc.ComponentAlternativeDAOJDBC;
import daojdbc.ComponentCommentDAOJDBC;
import daojdbc.ComponentDAOJDBC;
import daojdbc.ComponentOrderDAOJDBC;
import daojdbc.ComponentRelationshipDAOJDBC;
import daojdbc.ComponentSynonymDAOJDBC;
import daojdbc.DerivedPartOfDAOJDBC;
import daojdbc.DerivedPartOfFKDAOJDBC;
import daojdbc.DerivedPartOfPerspectivesDAOJDBC;
import daojdbc.DerivedPartOfPerspectivesJsonFKDAOJDBC;
import daojdbc.DerivedRelationshipTransitiveDAOJDBC;
import daojdbc.EditorDAOJDBC;
import daojdbc.ExtraTimedNodeDAOJDBC;
import daojdbc.JOINNodeRelationshipDAOJDBC;
import daojdbc.JOINNodeRelationshipNodeDAOJDBC;
import daojdbc.JOINNodeRelationshipRelationshipProjectDAOJDBC;
import daojdbc.JOINRelationshipProjectRelationshipDAOJDBC;
import daojdbc.JOINTimedNodeNodeStageDAOJDBC;
import daojdbc.JOINTimedNodeNodeStageRelationshipNodeTimedNodeStageDAOJDBC;
import daojdbc.JOINTimedNodeStageDAOJDBC;
import daojdbc.LeafDAOJDBC;
import daojdbc.LogDAOJDBC;
import daojdbc.NodeDAOJDBC;
import daojdbc.OBOFileDAOJDBC;
import daojdbc.PerspectiveAmbitDAOJDBC;
import daojdbc.ProjectDAOJDBC;
import daojdbc.RelationshipDAOJDBC;
import daojdbc.RelationshipProjectDAOJDBC;
import daojdbc.SourceDAOJDBC;
import daojdbc.StageDAOJDBC;
import daojdbc.StageRangeDAOJDBC;
import daojdbc.SynonymDAOJDBC;
import daojdbc.ThingDAOJDBC;
import daojdbc.TimedLeafDAOJDBC;
import daojdbc.TimedNodeDAOJDBC;
import daojdbc.UserDAOJDBC;
import daojdbc.VersionDAOJDBC;
*/


public abstract class DAOFactory {
    // Constants ----------------------------------------------------------------------------------
    private static final String PROPERTY_URL = "url";
    private static final String PROPERTY_DRIVER = "driver";
    private static final String PROPERTY_USERNAME = "username";
    private static final String PROPERTY_PASSWORD = "password";
    private static final String JNDI_ROOT = "java:comp/env/";
    private static final String PROPERTY_DEBUG = "debug";
    private static final String PROPERTY_UPDATE = "update";
    private static final String PROPERTY_SQL_OUTPUT = "sqloutput";
    private static final String PROPERTY_MESSAGE_LEVEL = "msglevel";
    private static final String PROPERTY_ACCESS_METHOD = "access";

    private static final Set<String> VALID_LEVELS = new HashSet<String>(Arrays.asList(
            new String[] 
        	    {"*****","****","***","**","*"}
            ));
    
    private static final Set<String> VALID_BOOLS = new HashSet<String>(Arrays.asList(
            new String[] 
        	    {"true","false"}
            ));

    private static final Set<String> VALID_METHODS = new HashSet<String>(Arrays.asList(
            new String[] 
        	    {"JDBC"}
            ));

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

        DAOProperties properties = new DAOProperties(name);
        
        String filename = properties.getName();
        
        String url = properties.getProperty(PROPERTY_URL, true);
        String driverClassName = properties.getProperty(PROPERTY_DRIVER, false);
        String password = properties.getProperty(PROPERTY_PASSWORD, false);
        String username = properties.getProperty(PROPERTY_USERNAME, password != null);
        String strDebug = properties.getProperty(PROPERTY_DEBUG, true);
        String strUpdate = properties.getProperty(PROPERTY_UPDATE, true);
        String sqloutput = properties.getProperty(PROPERTY_SQL_OUTPUT, false);
        String strMsgLevel = properties.getProperty(PROPERTY_MESSAGE_LEVEL, true);
        String strAccessMethod = properties.getProperty(PROPERTY_ACCESS_METHOD, true);

    	Boolean update = false;
        Boolean debug = false;

        String level = "";
        String access = "";

        try {
            	
        	if ( !VALID_METHODS.contains( strAccessMethod ) ) {
        	
                throw new DAOConfigurationException(
                		"Access Method '" + strAccessMethod + "' : INVALID Value!");
        	}
        	else {
        		
        		access = strAccessMethod;
        	}
        		
        	if ( !VALID_LEVELS.contains( strMsgLevel ) ) {
            	
                throw new DAOConfigurationException(
                        "Message Level '" + strMsgLevel + "' : INVALID Value!");
        	}
        	else {
        		
        		level = strMsgLevel;
        	}
        		
        	if ( !VALID_BOOLS.contains( strUpdate ) ) {
            	
                throw new DAOConfigurationException(
                        "Update '" + strUpdate + "' : INVALID Value!");
        	}
        	else {
        		
                if (strUpdate.equals("true")) {
                	
                	update = true;
                }
                if (strUpdate.equals("false")) {
                	
                	update = true;
                }
        	}
            
        	if ( !VALID_BOOLS.contains( strDebug ) ) {
            	
                throw new DAOConfigurationException(
                        "Debug '" + strDebug + "' : INVALID Value!");
        	}
        	else {
        		
                if (strDebug.equals("true")) {
                	
                	debug = true;
                	Wrapper.printMessage("========= :", "*", level);
                	Wrapper.printMessage("DEBUG     : DAO Properties File     : " + filename, "*", level);
                	Wrapper.printMessage("--------- :", "*", level);
                	Wrapper.printMessage("          : url                     : " + url, "*", level);
                	Wrapper.printMessage("          : driverClassName         : " + driverClassName, "*", level);
                	Wrapper.printMessage("          : username                : " + username, "*", level);
                	Wrapper.printMessage("          : password                : " + password, "*", level);
                	Wrapper.printMessage("          : debug                   : " + debug, "*", level);
                	Wrapper.printMessage("          : update                  : " + update, "*", level);
                	Wrapper.printMessage("          : sqloutput               : " + sqloutput, "*", level);
                	Wrapper.printMessage("          : strMsgLevel             : " + strMsgLevel, "*", level);
                	Wrapper.printMessage("          : strAccessMethod         : " + strAccessMethod, "*", level);
                }
                
                if (strDebug.equals("false")) {
                	
                	debug = false;
                }
        	}
        		
            if ( debug ) {
            	
                if ( sqloutput != null) {
                	
                    String commandString1 = "rm " + sqloutput;
                    ArrayList<String> results = new ArrayList<String>();
                    
            		results = ExecuteCommand.execute(commandString1);

                	Wrapper.printMessage("--------- :", "*", level);
                	Wrapper.printMessage("          : Empty SQL Command Log File", "*", level);
                	Wrapper.printMessage("          : Command: " + commandString1, "*", level);

                	Iterator<String> iteratorresults = results.iterator();

                  	while (iteratorresults.hasNext()) {
                  		
                    	Wrapper.printMessage("                 " + iteratorresults.next(), "*", level);
                  	}
                }

                Wrapper.printMessage("========= :", "*", level);
            }
            
            // If driver is specified, then load it to let it register itself with DriverManager.
            if (driverClassName != null) {
                
            	Class.forName(driverClassName);

            	instance = new DriverManagerDAOFactory(access, level, update, debug, sqloutput, url, username, password);
                
            }
            // Else assume URL as DataSource URL and lookup it in the JNDI.
            else {
            	
                DataSource dataSource;
                
                dataSource = (DataSource) new InitialContext().lookup(JNDI_ROOT + url);
                
                if (username != null) {
                	
                    instance = new DataSourceWithLoginDAOFactory(access, level, update, debug, sqloutput, dataSource, username, password);
                }
                else {
                	
                    instance = new DataSourceDAOFactory(access, level, update, debug, sqloutput, dataSource);
                }
            }
    	}
        catch (ClassNotFoundException e) {
        	
            throw new DAOConfigurationException(
                "Driver class '" + driverClassName + "' is missing in classpath.", e);
        }
        catch (NamingException e) {
        	
            throw new DAOConfigurationException(
                "DataSource '" + url + "' is missing in JNDI.", e);
        }
    	catch (Exception e) {
    		
    		e.printStackTrace();
    	}

        return instance;
    }

    /*
     * Returns a connection to the database. 
     *  Package private so that it can be used inside the DAO package only.
     */
    public abstract Connection getConnection() throws SQLException;
    public abstract Boolean isDebug();
    public abstract Boolean isUpdate();
    public abstract String getUrl();
    public abstract String getSqloutput();
    public abstract String getLevel();
    public abstract String getAccessMethod();
    
    // DAO getters --------------------------------------------------------------------------------

    /* Returns the ... DAO associated with the current DAOFactory.
     */
    
    /*
    public OBOFileDAOJDBC getOBOFileDAOJDBC() {
        return new OBOFileDAOJDBC(this);
    }

    public DerivedPartOfDAOJDBC getDerivedPartOfDAOJDBC() {
        return new DerivedPartOfDAOJDBC(this);
    }
    public DerivedPartOfFKDAOJDBC getDerivedPartOfFKDAOJDBC() {
        return new DerivedPartOfFKDAOJDBC(this);
    }
    public DerivedPartOfPerspectivesDAOJDBC getDerivedPartOfPerspectivesDAOJDBC() {
        return new DerivedPartOfPerspectivesDAOJDBC(this);
    }
    public DerivedPartOfPerspectivesJsonFKDAOJDBC getDerivedPartOfPerspectivesJsonFKDAOJDBC() {
        return new DerivedPartOfPerspectivesJsonFKDAOJDBC(this);
    }
    public DerivedRelationshipTransitiveDAOJDBC getDerivedRelationshipTransitiveDAOJDBC() {
        return new DerivedRelationshipTransitiveDAOJDBC(this);
    }

    public LogDAOJDBC getLogDAOJDBC() {
        return new LogDAOJDBC(this);
    }
    public NodeDAOJDBC getNodeDAOJDBC() {
        return new NodeDAOJDBC(this);
    }
    public RelationshipDAOJDBC getRelationshipDAOJDBC() {
        return new RelationshipDAOJDBC(this);
    }
    public RelationshipProjectDAOJDBC getRelationshipProjectDAOJDBC() {
        return new RelationshipProjectDAOJDBC(this);
    }
    public StageDAOJDBC getStageDAOJDBC() {
        return new StageDAOJDBC(this);
    }
    public SynonymDAOJDBC getSynonymDAOJDBC() {
        return new SynonymDAOJDBC(this);
    }
    public ThingDAOJDBC getThingDAOJDBC() {
        return new ThingDAOJDBC(this);
    }
    public TimedNodeDAOJDBC getTimedNodeDAOJDBC() {
        return new TimedNodeDAOJDBC(this);
    }
    public VersionDAOJDBC getVersionDAOJDBC() {
        return new VersionDAOJDBC(this);
    }
    public ProjectDAOJDBC getProjectDAOJDBC() {
        return new ProjectDAOJDBC(this);
    }
    
    public ComponentDAOJDBC getComponentDAOJDBC() {
        return new ComponentDAOJDBC(this);
    }
    public ComponentRelationshipDAOJDBC getComponentRelationshipDAOJDBC() {
        return new ComponentRelationshipDAOJDBC(this);
    }
    public ComponentCommentDAOJDBC getComponentCommentDAOJDBC() {
        return new ComponentCommentDAOJDBC(this);
    }
    public ComponentSynonymDAOJDBC getComponentSynonymDAOJDBC() {
        return new ComponentSynonymDAOJDBC(this);
    }
    public ComponentAlternativeDAOJDBC getComponentAlternativeDAOJDBC() {
        return new ComponentAlternativeDAOJDBC(this);
    }
    public ComponentOrderDAOJDBC getComponentOrderDAOJDBC() {
        return new ComponentOrderDAOJDBC(this);
    }
    
    public StageRangeDAOJDBC getStageRangeDAOJDBC() {
        return new StageRangeDAOJDBC(this);
    }
    
    public JOINNodeRelationshipDAOJDBC getJOINNodeRelationshipDAOJDBC() {
        return new JOINNodeRelationshipDAOJDBC(this);
    }
    public JOINNodeRelationshipNodeDAOJDBC getJOINNodeRelationshipNodeDAOJDBC() {
        return new JOINNodeRelationshipNodeDAOJDBC(this);
    }
    public JOINNodeRelationshipRelationshipProjectDAOJDBC getJOINNodeRelationshipRelationshipProjectDAOJDBC() {
        return new JOINNodeRelationshipRelationshipProjectDAOJDBC(this);
    }
    public JOINRelationshipProjectRelationshipDAOJDBC getJOINRelationshipProjectRelationshipDAOJDBC() {
        return new JOINRelationshipProjectRelationshipDAOJDBC(this);
    }
    public JOINTimedNodeNodeStageDAOJDBC getJOINTimedNodeNodeStageDAOJDBC() {
        return new JOINTimedNodeNodeStageDAOJDBC(this);
    }
    public JOINTimedNodeStageDAOJDBC getJOINTimedNodeStageDAOJDBC() {
        return new JOINTimedNodeStageDAOJDBC(this);
    }
    public JOINTimedNodeNodeStageRelationshipNodeTimedNodeStageDAOJDBC getJOINTimedNodeNodeStageRelationshipNodeTimedNodeStageDAOJDBC() {
        return new JOINTimedNodeNodeStageRelationshipNodeTimedNodeStageDAOJDBC(this);
    }

    public LeafDAOJDBC getLeafDAOJDBC() {
        return new LeafDAOJDBC(this);
    }
    public ExtraTimedNodeDAOJDBC getExtraTimedNodeDAOJDBC() {
        return new ExtraTimedNodeDAOJDBC(this);
    }
    public TimedLeafDAOJDBC getTimedLeafDAOJDBC() {
        return new TimedLeafDAOJDBC(this);
    }

    public EditorDAOJDBC getEditorDAOJDBC() {
        return new EditorDAOJDBC(this);
    }
    public PerspectiveAmbitDAOJDBC getPerspectiveAmbitDAOJDBC() {
        return new PerspectiveAmbitDAOJDBC(this);
    }
    public SourceDAOJDBC getSourceDAOJDBC() {
        return new SourceDAOJDBC(this);
    }

    public UserDAOJDBC getUserDAOJDBC() {
        return new UserDAOJDBC(this);
    }
    */

    // You can add more DAO getters here.

}

// Default DAOFactory implementations -------------------------------------------------------------
/*
 * The DriverManager based DAOFactory.
 */
class DriverManagerDAOFactory extends DAOFactory {
    
	private String url;
    private String username;
    private String password;
	private Boolean debug;
	private Boolean update;
    private String sqloutput;
    private String level;
    private String access;
	
    DriverManagerDAOFactory(String access, String level, Boolean update, Boolean debug, String sqloutput, String url, String username, String password) {
    
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
    public Boolean isDebug() {
        return debug;
    }
    public Boolean isUpdate() {
        return update;
    }
    public String getUrl() {
        return url;
    }
    public String getSqloutput() {
        return sqloutput;
    }
    public String getLevel() {
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
	private Boolean debug;
	private Boolean update;
    private String sqloutput;
    private String level;
    private String access;

    DataSourceDAOFactory(String access, String level, Boolean update, Boolean debug, String sqloutput, DataSource dataSource) {

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
    public Boolean isDebug() {
        return debug;
    }
    public Boolean isUpdate() {
        return update;
    }
    public String getUrl() {
        return "";
    }
    public String getSqloutput() {
        return sqloutput;
    }
    public String getLevel() {
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
	private Boolean debug;
	private Boolean update;
    private String sqloutput;
    private String level;
    private String access;

    DataSourceWithLoginDAOFactory(
    		String access, 
    		String level, 
    		Boolean update, 
    		Boolean debug, 
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
    public Boolean isDebug() {
        return debug;
    }
    public Boolean isUpdate() {
        return update;
    }
    public String getUrl() {
        return "";
    }
    public String getSqloutput() {
        return sqloutput;
    }
    public String getLevel() {
        return level;
    }
    public String getAccessMethod() {
        return access;
    }
}
