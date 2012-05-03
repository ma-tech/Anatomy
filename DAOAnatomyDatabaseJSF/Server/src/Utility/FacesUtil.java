package utility;

import javax.faces.event.ActionEvent;
import javax.faces.context.FacesContext;

public class FacesUtil {

    // Getters -----------------------------------------------------------------------------------

    public static String getActionAttribute(ActionEvent event, String name) {
        return (String) event.getComponent().getAttributes().get(name);
    }

    public static String getRequestParameter(String name) {
        return (String) FacesContext.getCurrentInstance().getExternalContext()
            .getRequestParameterMap().get(name);
    }
    

    public static Object getSessionMapValue(String key) {
        return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(key);
    }

    public static Object getApplicationMapValue(String key) {
        return FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().get(key);
    }


    // Setters -----------------------------------------------------------------------------------
    public static void setSessionMapValue(String key, Object value) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(key, value);
    }

    public static void setApplicationMapValue(String key, Object value) {
        FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().put(key, value);
    }

    // Others ------------------------------------------------------------------------------------
    public static void deleteApplicationMapValue(String key) {
        FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().remove(key);
    }


}