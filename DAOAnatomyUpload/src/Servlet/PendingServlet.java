package Servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.faces.context.FacesContext;

import DAOLayer.DAOException;
import DAOLayer.DAOFactory;
import DAOLayer.OBOFileDAO;

import DAOModel.OBOFile;

/**
 * The File servlet for serving from database.
 * @author BalusC
 * @link http://balusc.blogspot.com/2007/07/fileservlet.html
 */
public class PendingServlet extends HttpServlet {

    // Constants ----------------------------------------------------------------------------------
    //private static final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.
    //private static final String VIEW = "listFiles.jsp";

    // Statics ------------------------------------------------------------------------------------
    private static OBOFileDAO obofileDAO = DAOFactory.getInstance("anatomy008").getOBOFileDAO();

    // Actions ------------------------------------------------------------------------------------
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
    	
    	//String url = "http://localhost:8080/DAOAnatomyUpload/listFiles.jsf";
    	
        //FacesContext context = FacesContext.getCurrentInstance();  

        //context.getExternalContext().dispatch(url);  
        
        // Get ID from request.
        String fileOid = request.getParameter("oid");
        
        // Check if ID is supplied to the request.
        if (fileOid == null) {
            /*
             * Do your thing if the ID is not supplied to the request.
             *  Throw an exception, or send 404, or show default/warning page, or just ignore it.
             */
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
            
            return;
        }

        try {
            // Lookup File by FileId in database.
            OBOFile obofile = obofileDAO.find(Long.valueOf(fileOid));

            // Check if file is actually retrieved from database.
            if (obofile == null) {
                /*
                 * Do your thing if the file does not exist in database.
                 *  Throw an exception, or send 404, or show default/warning page, or just ignore it.
                 */
                response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.

                return;
            }

            // Init servlet response.
            //response.reset();
            
            // Update Status Flag
            obofile.setValidation("PENDING");
            obofileDAO.save(obofile);
            
         
            // Show view.
            //request.getRequestDispatcher(VIEW).forward(request, response);

        } 
        catch ( DAOException daoe ){
        	daoe.printStackTrace();
        }
        /*
        finally {  
            context.responseComplete();  
        } 
        */ 

    }
}
