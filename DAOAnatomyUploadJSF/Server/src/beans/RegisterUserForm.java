package beans;

import webapp.Config;
import daointerface.UserDAO;
import daolayer.DAOException;

import daomodel.User;

/*
 * This bean is to be associated with a single User Registration Form in the JSF page.
 *
 * @author BalusC
 * @link http://balusc.blogspot.com/2008/07/dao-tutorial-use-in-jsf.html
 */
public class RegisterUserForm extends AbstractUserForm {

    /*
     * The User DAO associated with the User Form.
     */
    protected final UserDAO userDAO = Config.getInstance().getDAOFactory().getDAOImpl(UserDAO.class);

    // Properties ---------------------------------------------------------------------------------
    private User user = new User();

    // Actions ------------------------------------------------------------------------------------
    /*
     * Register the User.
     */
    public void registerUser() throws IllegalArgumentException, Exception {
        try {
            userDAO.create(user);
            setSuccesMessage(getMessage(REGISTERED_MESSAGE_ID, user.getOid()));
        } 
        catch (DAOException e) {
            setErrorMessage(e);
        }
    }

    // Getters ------------------------------------------------------------------------------------
    /*
     * Returns the User.
     */
    public User getUser() {
        return user;
    }
}
