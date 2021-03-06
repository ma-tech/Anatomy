package beans;

import javax.faces.context.FacesContext;

import webapp.Config;

import daointerface.UserDAO;
import daolayer.DAOException;

import daomodel.User;

//import daointerface.crud
/*
 * This bean is to be associated with a single User Registration Form in the JSF page.
 *
 * @author BalusC
 * @link http://balusc.blogspot.com/2008/07/dao-tutorial-use-in-jsf.html
 */
public class LoginUserForm extends AbstractUserForm {

    // Constants ----------------------------------------------------------------------------------
    public static final String AUTH_KEY = "app.user.name";

    // Properties ---------------------------------------------------------------------------------
    private User user = new User();

    /*
     * The User DAO associated with the User Form.
     */
    protected final UserDAO userDAO = Config.getInstance().getDAOFactory().getDAOImpl(UserDAO.class);

    // Actions ------------------------------------------------------------------------------------
    /*
     * Login the User.
     */
    public String loginUser() throws Exception {
    	
    	String returnString = "";
    	
        try {
            user = userDAO.find(user.getUsername(), user.getPassword());
            
            if ( user == null ) {
            	setSuccesMessage(getMessage(FAILED_MESSAGE_ID));
            }
            else {
            	setSuccesMessage(getMessage(LOGGED_IN_MESSAGE_ID, user.getUsername()));
        		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(AUTH_KEY, user.getUsername());
        		returnString = "secret";
            }
        } 
        catch (DAOException e) {
            setErrorMessage(e);
        }
        
        return returnString;
    }

    /*
     * Logout the User.
     */
    public String logoutUser() {
    	
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove(AUTH_KEY);
        
        return null;
    }

    /*
     * Is the User Logged In?
     */
	public boolean isLoggedIn() {
		
		return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(AUTH_KEY) != null;
    }

    // Getters ------------------------------------------------------------------------------------
    /*
     * Returns the User.
     */
    public User getUser() {
        return user;
    }
}
