package Beans;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.context.FacesContext;

import DAOLayer.DAOUtil;

/*
 * This bean is to be associated with a single form in the JSF page. It provides under each a 'form'
 * property which must be bound with the h:form component in the JSF page using the 'binding'
 * attribute. It also provides several methods for succes, error and exception message handling.
 *
 * @author BalusC
 * @link http://balusc.blogspot.com/2008/07/dao-tutorial-use-in-jsf.html
 */
public abstract class AbstractForm {

    // Constants ----------------------------------------------------------------------------------
    public static final String EXIST_MESSAGE_ID = "Register.AbstractForm.EXIST";
    public static final String INVALID_MESSAGE_ID = "Register.AbstractForm.INVALID";
    public static final String INEQUAL_MESSAGE_ID = "Register.AbstractForm.INEQUAL";
    public static final String PASSCODE_MESSAGE_ID = "Register.AbstractForm.PASSCODE";
    public static final String FAILED_MESSAGE_ID = "Login.AbstractForm.FAILED";
    public static final String UPLOAD_FAILED_MESSAGE_ID = "UploadFile.AbstractForm.FAILED";

    // Statics ------------------------------------------------------------------------------------
    private static ResourceBundle messageBundle = ResourceBundle.getBundle(
        FacesContext.getCurrentInstance().getApplication().getMessageBundle());

    // Properties ---------------------------------------------------------------------------------
    private UIForm form;
    private boolean succes;

    // Getters ------------------------------------------------------------------------------------
    public UIForm getForm() {
        return form;
    }
    public boolean isSucces() {
        return succes;
    }

    // Setters ------------------------------------------------------------------------------------
    public void setForm(UIForm form) {
        this.form = form;
    }
    public void setSucces(boolean succes) {
        this.succes = succes;
    }

    // Special getters ----------------------------------------------------------------------------
    /*
     * Returns the message from the message-bundle associated with the given message ID, formatted
     * with the given parameters, if any.
     */
    public String getMessage(String messageId, Object... params) {
        return MessageFormat.format(messageBundle.getString(messageId), params);
    }

    // Special setters ----------------------------------------------------------------------------
    /*
     * Set the given succes message on the current form.
     */
    public void setSuccesMessage(String message) {
        setSucces(true);
        addMessage(form, message, FacesMessage.SEVERITY_INFO);
    }

    /*
     * Set the given error message on the current form.
     */
    public void setErrorMessage(String message) {
        addMessage(form, message, FacesMessage.SEVERITY_ERROR);
    }

    /*
     * Set the given exception as error message on the current form. Its trace will also be printed.
     */
    public void setErrorMessage(Exception exception) {
        String message = getMessage(exception.getClass().getName(), exception.getMessage());
        addMessage(form, message, FacesMessage.SEVERITY_ERROR);
        exception.printStackTrace(); // Or use logger.
    }

    /*
     * Set the given error message on the given component.
     */
    public void setErrorMessage(UIComponent component, String message) {
        addMessage(component, message, FacesMessage.SEVERITY_ERROR);
    }

    // Utility (can be refactored to public utility class) ----------------------------------------
    /*
     * Add the given message with the given severity to the given component.
     */
    public static void addMessage(UIComponent component, String message, Severity severity) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        facesContext.addMessage(component.getClientId(facesContext),
            new FacesMessage(severity, message, null));
    }

    /*
     * Returns the label attribute of the given component.
     */
    public static String getLabel(UIComponent component) {
        return (String) component.getAttributes().get("label");
    }

}
