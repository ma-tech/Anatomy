/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomy008
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
import java.util.Iterator;

import javax.sql.DataSource;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import utility.ExecuteCommand;

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

    // Actions ------------------------------------------------------------------------------------
    /*
     * Returns a new DAOFactory instance for the given database name.
     */
    public static DAOFactory getInstance(String name) throws DAOConfigurationException {
    	
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

        Boolean update = false;
        
        if (strUpdate.equals("true")) {
        	update = true;
        }

        Boolean debug = false;
        
        if (strDebug.equals("true")) {
        	debug = true;
        	System.out.println("=====");
        	System.out.println("DEBUG : DAO Properties File : " + filename);
        	System.out.println("-----");
        	System.out.println("      : url                 : " + url);
        	System.out.println("      : driverClassName     : " + driverClassName);
        	System.out.println("      : username            : " + username);
        	System.out.println("      : password            : " + password);
        	System.out.println("      : debug               : " + strDebug);
        	System.out.println("      : update              : " + strUpdate);
        	System.out.println("      : sqloutput           : " + sqloutput);
        }
        
        if (debug ) {
            if (sqloutput != null) {
                String commandString1 = "rm " + sqloutput;
                ArrayList<String> results = new ArrayList<String>();
                
        		results = ExecuteCommand.execute(commandString1);

            	System.out.println("-----");
            	System.out.println("        Empty SQL Command Log File");

           		System.out.println("        Command: " + commandString1);

           		Iterator<String> iteratorresults = results.iterator();

              	while (iteratorresults.hasNext()) {
              		String result = iteratorresults.next();

               		System.out.println("                 " + result.toString());
              	}
            }
        	System.out.println("=====");
        }
        
        DAOFactory instance;

        // If driver is specified, then load it to let it register itself with DriverManager.
        if (driverClassName != null) {
            
        	try {
                Class.forName(driverClassName);
            }
            catch (ClassNotFoundException e) {
                throw new DAOConfigurationException(
                    "Driver class '" + driverClassName + "' is missing in classpath.", e);
            }
            instance = new DriverManagerDAOFactory(update, debug, sqloutput, url, username, password);
            
        }
        // Else assume URL as DataSource URL and lookup it in the JNDI.
        else {
            DataSource dataSource;
            
            try {
                dataSource = (DataSource) new InitialContext().lookup(JNDI_ROOT + url);
            }
            catch (NamingException e) {
                throw new DAOConfigurationException(
                    "DataSource '" + url + "' is missing in JNDI.", e);
            }
            if (username != null) {
                instance = new DataSourceWithLoginDAOFactory(update, debug, sqloutput, dataSource, username, password);
            }
            else {
                instance = new DataSourceDAOFactory(update, debug, sqloutput, dataSource);
            }
        }

        return instance;
    }

    /*
     * Returns a connection to the database. Package private so that it can be used inside the DAO
     * package only.
     */
    abstract Connection getConnection() throws SQLException;
    abstract Boolean isDebug();
    abstract Boolean isUpdate();
    abstract String getUrl();
    abstract String getSqloutput();
    
    // DAO getters --------------------------------------------------------------------------------
    /*
     * Returns the ... DAO associated with the current DAOFactory.
     */
    public OBOFileDAO getOBOFileDAO() {
        return new OBOFileDAO(this);
    }

    public DerivedPartOfDAO getDerivedPartOfDAO() {
        return new DerivedPartOfDAO(this);
    }
    public DerivedPartOfFKDAO getDerivedPartOfFKDAO() {
        return new DerivedPartOfFKDAO(this);
    }
    public DerivedPartOfPerspectivesDAO getDerivedPartOfPerspectivesDAO() {
        return new DerivedPartOfPerspectivesDAO(this);
    }
    public DerivedPartOfPerspectivesFKDAO getDerivedPartOfPerspectivesFKDAO() {
        return new DerivedPartOfPerspectivesFKDAO(this);
    }
    public DerivedPartOfPerspectivesJsonFKDAO getDerivedPartOfPerspectivesJsonFKDAO() {
        return new DerivedPartOfPerspectivesJsonFKDAO(this);
    }
    public DerivedRelationshipTransitiveDAO getDerivedRelationshipTransitiveDAO() {
        return new DerivedRelationshipTransitiveDAO(this);
    }

    public LogDAO getLogDAO() {
        return new LogDAO(this);
    }
    public NodeDAO getNodeDAO() {
        return new NodeDAO(this);
    }
    public RelationshipDAO getRelationshipDAO() {
        return new RelationshipDAO(this);
    }
    public RelationshipProjectDAO getRelationshipProjectDAO() {
        return new RelationshipProjectDAO(this);
    }
    public StageDAO getStageDAO() {
        return new StageDAO(this);
    }
    public SynonymDAO getSynonymDAO() {
        return new SynonymDAO(this);
    }
    public ThingDAO getThingDAO() {
        return new ThingDAO(this);
    }
    public TimedNodeDAO getTimedNodeDAO() {
        return new TimedNodeDAO(this);
    }
    public VersionDAO getVersionDAO() {
        return new VersionDAO(this);
    }
    public ProjectDAO getProjectDAO() {
        return new ProjectDAO(this);
    }
    
    public ComponentDAO getComponentDAO() {
        return new ComponentDAO(this);
    }
    public ComponentRelationshipDAO getComponentRelationshipDAO() {
        return new ComponentRelationshipDAO(this);
    }
    public ComponentCommentDAO getComponentCommentDAO() {
        return new ComponentCommentDAO(this);
    }
    public ComponentSynonymDAO getComponentSynonymDAO() {
        return new ComponentSynonymDAO(this);
    }
    public ComponentAlternativeDAO getComponentAlternativeDAO() {
        return new ComponentAlternativeDAO(this);
    }
    public ComponentOrderDAO getComponentOrderDAO() {
        return new ComponentOrderDAO(this);
    }
    
    public StageRangeDAO getStageRangeDAO() {
        return new StageRangeDAO(this);
    }
    
    public JOINNodeRelationshipDAO getJOINNodeRelationshipDAO() {
        return new JOINNodeRelationshipDAO(this);
    }
    public JOINNodeRelationshipNodeDAO getJOINNodeRelationshipNodeDAO() {
        return new JOINNodeRelationshipNodeDAO(this);
    }
    public JOINNodeRelationshipRelationshipProjectDAO getJOINNodeRelationshipRelationshipProjectDAO() {
        return new JOINNodeRelationshipRelationshipProjectDAO(this);
    }
    public JOINRelationshipProjectRelationshipDAO getJOINRelationshipProjectRelationshipDAO() {
        return new JOINRelationshipProjectRelationshipDAO(this);
    }
    public JOINTimedNodeNodeStageDAO getJOINTimedNodeNodeStageDAO() {
        return new JOINTimedNodeNodeStageDAO(this);
    }
    public JOINTimedNodeStageDAO getJOINTimedNodeStageDAO() {
        return new JOINTimedNodeStageDAO(this);
    }

    public LeafDAO getLeafDAO() {
        return new LeafDAO(this);
    }
    public ExtraTimedNodeDAO getExtraTimedNodeDAO() {
        return new ExtraTimedNodeDAO(this);
    }
    public TimedLeafDAO getTimedLeafDAO() {
        return new TimedLeafDAO(this);
    }

    public EditorDAO getEditorDAO() {
        return new EditorDAO(this);
    }
    public PerspectiveAmbitDAO getPerspectiveAmbitDAO() {
        return new PerspectiveAmbitDAO(this);
    }
    public SourceDAO getSourceDAO() {
        return new SourceDAO(this);
    }

    public UserDAO getUserDAO() {
        return new UserDAO(this);
    }

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
	
    DriverManagerDAOFactory(Boolean update, Boolean debug, String sqloutput, String url, String username, String password) {
    
    	this.url = url;
        this.username = username;
        this.password = password;
        this.debug = debug;
        this.update = update;
        this.sqloutput = sqloutput;
    }

    Connection getConnection() throws SQLException {
    	
        return DriverManager.getConnection(url, username, password);
    }
    
    Boolean isDebug() {
    	
        return debug;
    }

    Boolean isUpdate() {
    	
        return update;
    }

    String getUrl() {
    	
        return url;
    }

    String getSqloutput() {
    	
        return sqloutput;
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

    DataSourceDAOFactory(Boolean update, Boolean debug, String sqloutput, DataSource dataSource) {

    	this.dataSource = dataSource;
        this.debug = debug;
        this.update = update;
        this.sqloutput = sqloutput;
    }

    Connection getConnection() throws SQLException {
        
    	return dataSource.getConnection();
    }
    
    Boolean isDebug() {
    	
        return debug;
    }

    Boolean isUpdate() {
    	
        return update;
    }

    String getUrl() {
    	
        return "";
    }

    String getSqloutput() {
    	
        return sqloutput;
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

    DataSourceWithLoginDAOFactory(Boolean update, Boolean debug, String sqloutput, DataSource dataSource, String username, String password) {
    
    	this.dataSource = dataSource;
        this.username = username;
        this.password = password;
        this.debug = debug;
        this.update = update;
        this.sqloutput = sqloutput;
    }

    Connection getConnection() throws SQLException {
        
    	return dataSource.getConnection(username, password);
    }
    
    Boolean isDebug() {
    	
        return debug;
    }

    Boolean isUpdate() {
    	
        return update;
    }

    String getUrl() {
    	
        return "";
    }

    String getSqloutput() {
    	
        return sqloutput;
    }
}