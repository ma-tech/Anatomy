package beans;

import java.io.IOException;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.myfaces.custom.fileupload.UploadedFile;

import webapp.Config;

import daolayer.OBOFileDAO;
import daolayer.DAOException;

import daomodel.OBOFile;
import daomodel.User;

/*
 * This bean is to be associated with a single User Registration Form in the JSF page.
 *
 * @author BalusC
 * @link http://balusc.blogspot.com/2008/07/dao-tutorial-use-in-jsf.html
 */
public class UploadFileForm extends AbstractFileForm {

    // Constants ----------------------------------------------------------------------------------
    public static final String AUTH_KEY = "app.user.name";

    // Properties ---------------------------------------------------------------------------------
	// DAO.
    private static OBOFileDAO dao = Config.getInstance().getDAOFactory().getOBOFileDAO();
    
    private User user = new User();
    
    private String file;
    private UploadedFile uploadedFile;

    // Actions ------------------------------------------------------------------------------------
    public void upload() {
    	
        try {
    		user.setUsername(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(AUTH_KEY).toString());

        	OBOFile filerow = new OBOFile(null, 
            		uploadedFile.getName(), 
            		uploadedFile.getInputStream(), 
            		uploadedFile.getContentType(), 
            		uploadedFile.getSize(), 
            		utility.MySQLDateTime.now(), 
            		"NEW", 
            		user.getUsername());
            
            dao.save(filerow);
            
            // Show succes message.
            FacesContext.getCurrentInstance().addMessage("uploadFileForm", new FacesMessage(
                FacesMessage.SEVERITY_INFO, "File upload SUCCEEDED!", null));
        } 
        catch (DAOException dao) {
            // Show error message.
            FacesContext.getCurrentInstance().addMessage("uploadFileForm", new FacesMessage(
                FacesMessage.SEVERITY_ERROR, "File upload FAILED with Database error.", null));

            // Always log stacktraces (with a real logger).
            dao.printStackTrace();
        }
        catch (IOException e) {
            // Show error message.
            FacesContext.getCurrentInstance().addMessage("uploadFileForm", new FacesMessage(
                FacesMessage.SEVERITY_ERROR, "File upload FAILED with I/O error.", null));

            // Always log stacktraces (with a real logger).
            e.printStackTrace();
        }

    }

    /*
     * Logout the User.
     */
    public String logout(ActionEvent event) {

    	try {
        	FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove(AUTH_KEY);
        	FacesContext.getCurrentInstance().getExternalContext().redirect("../index.html");
        	return null;
    	}
    	catch (IOException ioe) {
            throw new RuntimeException(ioe); // Handle it yourself.
    	}
    }
    
    // Getters ------------------------------------------------------------------------------------
    public User getUser() {
        return user;
    }
    public String getFile() {
        return file;
    }
    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    // Setters ------------------------------------------------------------------------------------
    public void setUser(User user) {
        this.user = user;
    }
    public void setFile(String file) {
        this.file = file;
    }
    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }
}
