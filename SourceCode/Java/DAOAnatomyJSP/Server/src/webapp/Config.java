package webapp;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import daolayer.DAOFactory;

/**
 * Configures the webapp.
 *
 * @author BalusC
 * @link http://balusc.blogspot.com/2008/07/dao-tutorial-use-in-jspservlet.html
 */
public class Config implements ServletContextListener {

    // Constants ----------------------------------------------------------------------------------

    private static final String ATTRIBUTE_CONFIG = "config";

    // Properties ---------------------------------------------------------------------------------

    private DAOFactory daoFactory;

    // ServletContextListener actions -------------------------------------------------------------

    //@Override
    public void contextInitialized(ServletContextEvent event) {
        // Obtain the DAOFactory and put the Config self in the application scope.
        ServletContext servletContext = event.getServletContext();
        String databaseName = servletContext.getInitParameter("database.name");

        this.daoFactory = DAOFactory.getInstance(databaseName);
        servletContext.setAttribute(ATTRIBUTE_CONFIG, this);
    }

    //@Override
    public void contextDestroyed(ServletContextEvent event) {
        // Nothing to do here.
    }

    // Getters ------------------------------------------------------------------------------------

    /**
     * Returns the current Config instance from the application scope.
     * @param servletContext The application scope to return current Config instance for.
     * @return The current Config instance from the application scope.
     */
    public static Config getInstance(ServletContext servletContext) {
        return (Config) servletContext.getAttribute(ATTRIBUTE_CONFIG);
    }

    /**
     * Returns the DAO Factory associated with this Config.
     * @return The DAO Factory associated with this Config
     */
    public DAOFactory getDAOFactory() {
        return daoFactory;
    }

}
