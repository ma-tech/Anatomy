package beans;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import daolayer.DAOException;
import daolayer.UserDAO;

import webapp.Config;

/*
 * This bean is to be associated with a single User form in the JSF page. It provides under each
 * the User DAO and several validation methods for the properties of the User DTO.
 *
 * @author BalusC
 * @link http://balusc.blogspot.com/2008/07/dao-tutorial-use-in-jsf.html
 */
public abstract class AbstractUserForm extends AbstractForm {

    // Constants ----------------------------------------------------------------------------------
    /*
     * The message ID of the message-bundle to be associated with the 'registered' message.
     * It contains one MessageFormat placeholder for the User ID value.
     */
    public static final String REGISTERED_MESSAGE_ID = "Register.AbstractUserForm.REGISTERED";
    public static final String LOGGED_IN_MESSAGE_ID = "Login.AbstractUserForm.LOGGED_IN";

    // Vars ---------------------------------------------------------------------------------------
    /*
     * The User DAO associated with the User Form.
     */
    protected final UserDAO userDAO = Config.getInstance().getDAOFactory().getUserDAO();

    // Validators ---------------------------------------------------------------------------------
    /*
     * Validate the username.
     */
    @SuppressWarnings("unused")
    public void validateUsername(FacesContext facesContext, UIComponent component, Object value)
        throws ValidatorException
    {
        String username = (String) value;
        try {
           
        	if (userDAO.existUsername(username)) {
                throw new ValidatorException(
                    new FacesMessage(getMessage(EXIST_MESSAGE_ID, getLabel(component))));
            }
        } 
        catch (DAOException e) {
            setErrorMessage(e);
        }
    }

    /*
     * Validate the passwords. It will fail if the passwords doesn't match.
     */
    public void validatePasscode(FacesContext facesContext, UIComponent component, Object value)
        throws ValidatorException
    {
        String passCode = (String) component.getAttributes().get("passCode");
        UIInput passCodeInput = (UIInput) facesContext.getViewRoot().findComponent(passCode);
        String passcode = (String) value;
        
        if (!passcode.equals("London_2$12")) {

            // Reset value of the both fields so that user is forced to retype passwords.
        	passCodeInput.resetValue();

            // This validator is been invoked by the second field (confirm password), but we want
            // the error message being displayed at the first field.
            setErrorMessage(passCodeInput,
                getMessage(PASSCODE_MESSAGE_ID, getLabel(passCodeInput)));

            // Throw a ValidatorException to indicate that the second field is also invalid. The
            // message is empty to prevent message duplication.
            throw new ValidatorException(new FacesMessage(""));
        }
    }

    /*
     * Validate the passwords. It will fail if the passwords doesn't match.
     */
    public void validatePassword(FacesContext facesContext, UIComponent component, Object value)
        throws ValidatorException
    {
        String passwordId = (String) component.getAttributes().get("passwordId");
        UIInput passwordInput = (UIInput) facesContext.getViewRoot().findComponent(passwordId);
        UIInput confirmInput = (UIInput) component;
        String password = (String) passwordInput.getValue();
        String confirm = (String) value;

        if (!confirm.equals(password)) {

            // Reset value of the both fields so that user is forced to retype passwords.
            passwordInput.resetValue();
            confirmInput.resetValue();

            // This validator is been invoked by the second field (confirm password), but we want
            // the error message being displayed at the first field.
            setErrorMessage(passwordInput,
                getMessage(INEQUAL_MESSAGE_ID, getLabel(passwordInput), getLabel(confirmInput)));

            // Throw a ValidatorException to indicate that the second field is also invalid. The
            // message is empty to prevent message duplication.
            throw new ValidatorException(new FacesMessage(""));
        }
    }

    /*
     * Validate the email address. It will fail if the email address is invalid or already exist.
     */
    @SuppressWarnings("unused")
    public void validateEmail(FacesContext facesContext, UIComponent component, Object value)
        throws ValidatorException
    {
        String email = (String) value;
        
        if (!email.matches("([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)")) {
            throw new ValidatorException(
                new FacesMessage(getMessage(INVALID_MESSAGE_ID, getLabel(component))));
        }
        else {
            try {
                
            	if (userDAO.existEmail(email)) {
                    throw new ValidatorException(
                        new FacesMessage(getMessage(EXIST_MESSAGE_ID, getLabel(component))));
                }
            } 
            catch (DAOException e) {
                setErrorMessage(e);
            }
        }
    }

}
