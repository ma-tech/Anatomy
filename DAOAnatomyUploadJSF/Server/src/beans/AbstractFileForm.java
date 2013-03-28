package beans;

import daointerface.UserDAO;

import webapp.Config;

/*
 * This bean is to be associated with a single User form in the JSF page. It provides under each
 * the User DAO and several validation methods for the properties of the User DTO.
 *
 * @author BalusC
 * @link http://balusc.blogspot.com/2008/07/dao-tutorial-use-in-jsf.html
 */
public abstract class AbstractFileForm extends AbstractForm {

    // Constants ----------------------------------------------------------------------------------
    /*
     * The message ID of the message-bundle to be associated with the 'registered' message.
     * It contains one MessageFormat placeholder for the User ID value.
     */
    public static final String LOADED_MESSAGE_ID = "UploadFile.AbstractFileForm.LOADED";

    // Vars ---------------------------------------------------------------------------------------
    /*
     * The User DAO associated with the User Form.
     */
    protected final UserDAO userDAO = Config.getInstance().getDAOFactory().getDAOImpl(UserDAO.class);

    // Validators ---------------------------------------------------------------------------------
}
