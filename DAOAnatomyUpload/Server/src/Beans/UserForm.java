package Beans;

import javax.servlet.http.HttpServletRequest;

import DAOLayer.DAOException;
import DAOLayer.UserDAO;

import DAOModel.User;

/*
 * This class holds all business logic related to the request processing of the User DTO.
 * 
 * @author BalusC
 * @link http://balusc.blogspot.com/2008/07/dao-tutorial-use-in-jspservlet.html
 */
public final class UserForm extends Form {

    // Constants ----------------------------------------------------------------------------------
	//AOU_OID, AOU_NAME, AOU_PASSWORD, AOU_EMAIL, AOU_ORGANISATION
    private static final String FIELD_USERNAME = "AOU_NAME";
    private static final String FIELD_PASSWORD = "AOU_PASSWORD";
    private static final String FIELD_CONFIRM = "AOU_PASSWORD_CONFIRM";
    private static final String FIELD_EMAIL = "AOU_EMAIL";
    private static final String FIELD_ORGANISATION = "AOU_ORGANISATION";
    private static final String FIELD_RESULT = "AOU_RESULT";

    // Variables ----------------------------------------------------------------------------------
    private UserDAO userDAO;

    // Constructors -------------------------------------------------------------------------------
    /*
     * Construct an User Form associated with the given UserDAO.
     */
    public UserForm(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    // Form actions -------------------------------------------------------------------------------
    /*
     * Returns the registered User based on the given request. It will gather all form fields,
     * process and validate the fields and save the created User using the User DAO associated with
     * this form.
     */
    public User registerUser(HttpServletRequest request) {

    	User user = new User();

        try {
            processUsername(request, user);
            processPasswords(request, user);
            processEmail(request, user);
            processOrganisation(request, user);

            if (isSucces()) {
                userDAO.create(user);
                setMessage(FIELD_RESULT, "Registration succeed! Your new user ID is "
                    + user.getOid() + ".");
            }
        }
        catch (DAOException e) {
            setError(FIELD_RESULT, "Registration failed due to database error."
                + " Please try again later. Detail message: " + e.getMessage());
            e.printStackTrace();
        }

        return user;
    }

    /*
     * Returns the registered User based on the given request. It will gather all form fields,
     * process and validate the fields and save the created User using the User DAO associated with
     * this form.
     */
    public User loginUser(HttpServletRequest request) {

    	User user = new User();

        try {
            processUsername(request, user);
            processPassword(request, user);

            userDAO.find(user.getUsername(), user.getPassword());
            
            if ( isSucces() ) {
            	setMessage(FIELD_RESULT, "Login " + user.getUsername() + " successful!");
            }
            else {
            	setMessage(FIELD_RESULT, "Login FAILED!");
            }
        }
        catch (DAOException e) {
            setError(FIELD_RESULT, "Registration failed due to database error."
                + " Please try again later. Detail message: " + e.getMessage());
            e.printStackTrace();
        }

        return user;
    }

    // Field processors ---------------------------------------------------------------------------
    /*
     * Process and validate the username which is to be associated with the given User.
     *  If the username is null or if it is changed in the given user, then it will be validated.
     * 
     * The new username will be set in the given user regardless of the outcome of the validation.
     */
    public void processUsername(HttpServletRequest request, User user) throws DAOException {
        
    	String username = FormUtil.getFieldValue(request, FIELD_USERNAME);

        if (username == null || FormUtil.isChanged(user.getUsername(), username)) {
            try {
                validateUsername(username);
            } 
            catch (ValidatorException e) {
                setError(FIELD_USERNAME, e.getMessage());
            }
            user.setUsername(username);
        }
    }

    /**
     * Process and validate the passwords which is to be associated with the given User.
     *  The passwords will be validated regardless of the current password of the given user.
     *  The password will be set in the given user only if validation succeeds.
     */
    public void processPasswords(HttpServletRequest request, User user) {

    	String password = FormUtil.getFieldValue(request, FIELD_PASSWORD);
        String confirm = FormUtil.getFieldValue(request, FIELD_CONFIRM);

        try {
            validatePasswords(password, confirm);
            user.setPassword(password);
        }
        catch (ValidatorException e) {
            setError(FIELD_PASSWORD, e.getMessage());
            setError(FIELD_CONFIRM, null);
        }
    }

    /*
     * Process and validate the password which is to be associated with the given User.
     *  The password will be validated regardless of the current password of the given user.
     *  The password will be set in the given user only if validation succeeds.
     */
    public void processPassword(HttpServletRequest request, User user) {
        
    	String password = FormUtil.getFieldValue(request, FIELD_PASSWORD);

        try {
            validatePassword(password);
            user.setPassword(password);
        } 
        catch (ValidatorException e) {
            setError(FIELD_PASSWORD, e.getMessage());
        }
    }

    /*
     * Process and validate the email which is to be associated with the given User.
     * If the email is changed in the given user, then it will be validated.
     * The email will be set in the given user regardless of the outcome of the validation.
     */
    public void processEmail(HttpServletRequest request, User user) throws DAOException {

    	String email = FormUtil.getFieldValue(request, FIELD_EMAIL);

        if (FormUtil.isChanged(user.getEmail(), email)) {
            try {
                validateEmail(email);
            } 
            catch (ValidatorException e) {
                setError(FIELD_EMAIL, e.getMessage());
            }
            user.setEmail(email);
        }
    }

    /*
     * Process and validate the organisation which is to be associated with the given User.
     *  If the organisation is changed in the given user, then it will be validated.
     *  The organisation will be set in the given user only if validation succeeds.
     */
    public void processOrganisation(HttpServletRequest request, User user) {
        
    	String organisation = FormUtil.getFieldValue(request, FIELD_ORGANISATION);

        if (FormUtil.isChanged(user.getOrganisation(), organisation)) {
            try {
                validateOrganisation(organisation);
                user.setOrganisation(organisation != null ? organisation : "");
            } 
            catch (ValidatorException e) {
                setError(FIELD_ORGANISATION, e.getMessage());
            }
        }
    }

    // Field validators ---------------------------------------------------------------------------
    /*
     * Validate the given username. It will check if it is not null, is at least 3 characters long 
     * and is not already in use according to the User DAO associated with this form.
     */
    public void validateUsername(String username) throws ValidatorException, DAOException {

    	if (username != null) {
            if (username.length() < 3) {
                throw new ValidatorException("Username should be at least 3 characters long.");
            } 
            else if (userDAO.existUsername(username)) {
                throw new ValidatorException("Username already in use, please choose another.");
            }
        } 
    	else {
            throw new ValidatorException("Please enter username.");
        }
    }

    /*
     * Validate the given passwords. 
     *  It will check if the password and the password confirmation are not null, 
     *   are equal and are at least 3 characters long. 
     */
    public void validatePasswords(String password, String confirm) throws ValidatorException {

    	if (password != null && confirm != null) {
            if (!password.equals(confirm)) {
                throw new ValidatorException("Passwords are not equal, please retype passwords.");
            } 
            else if (password.length() < 3) {
                throw new ValidatorException("Passwords should be at least 3 characters long.");
            }
        } 
    	else {
            throw new ValidatorException("Please enter both password and confirm password.");
        }
    }

    /*
     * Validate the given password. 
     *  It will check if the password is not null and is at least 3 characters long.
     */
    public void validatePassword(String password) throws ValidatorException {

    	if (password != null) {
            if (password.length() < 3) {
                throw new ValidatorException("Password should be at least 3 characters long.");
            }
        }
    	else {
            throw new ValidatorException("Please enter password.");
        }
    }

    /*
     * Validate the given email. 
     *  It will check if it confirms the email address requirements and is
     *  not already in use according to the User DAO associated with this form. 
     */
    public void validateEmail(String email) throws ValidatorException, DAOException {

    	if (email != null) {
            if (!FormUtil.isEmail(email)) {
                throw new ValidatorException("Please enter valid email address.");
            } 
            else if (userDAO.existEmail(email)) {
                throw new ValidatorException("Email address already in use, please choose another.");
            }
        }
    }

    /*
     * Validate the given organisation.
     *  It will check if the password is not null and is at least 1 characters long.
     */
    public void validateOrganisation(String organisation) throws ValidatorException {

    	if (organisation != null) {
            if (organisation.length() < 1) {
                throw new ValidatorException("Organisation should be at least 1 characters long.");
            }
        	else {
                throw new ValidatorException("Please enter organisation.");
            }
        }
    }

}
