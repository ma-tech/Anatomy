package Beans;

import DAOLayer.DAOException;
import DAOModel.User;

/*
 * This bean is to be associated with a single User Registration Form in the JSF page.
 *
 * @author BalusC
 * @link http://balusc.blogspot.com/2008/07/dao-tutorial-use-in-jsf.html
 */
public class RegisterUserForm extends AbstractUserForm {

    // Properties ---------------------------------------------------------------------------------
    private User user = new User();

    // Actions ------------------------------------------------------------------------------------
    /*
     * Register the User.
     */
    public void registerUser() {
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
