package WebApp;

import javax.faces.context.FacesContext;

import DAOLayer.DAOFactory;

/**
 * Configures the JSF webapplication.
 */
public class Config {

    // Constants ----------------------------------------------------------------------------------
    private static final String MANAGED_BEAN_NAME = "config";

    // Properties ---------------------------------------------------------------------------------
    private DAOFactory daoFactory;

    // Constructors -------------------------------------------------------------------------------
    /**
     * Constructs the config.
     */
    public Config() {
        // Obtain the DAOFactory.
        FacesContext facesContext = FacesContext.getCurrentInstance();
        String databaseName = facesContext.getExternalContext().getInitParameter("database.name");
        this.daoFactory = DAOFactory.getInstance(databaseName);
    }

    // Getters ------------------------------------------------------------------------------------
    /**
     * Returns the current Config instance from the application scope.
     */
    public static Config getInstance() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        return (Config) facesContext.getApplication().evaluateExpressionGet(
            facesContext, "#{" + MANAGED_BEAN_NAME + "}", Config.class);
    }

    /**
     * Returns the DAO Factory associated with this Config.
     */
    public DAOFactory getDAOFactory() {
        return daoFactory;
    }

}
