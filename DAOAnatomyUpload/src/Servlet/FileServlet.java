package Servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAOLayer.DAOException;
import DAOLayer.DAOFactory;
import DAOLayer.OBOFileDAO;

import DAOModel.OBOFile;

import WebApp.Config;

/**
 * The File servlet for serving from database.
 * @author BalusC
 * @link http://balusc.blogspot.com/2007/07/fileservlet.html
 */
public class FileServlet extends HttpServlet {

    // Constants ----------------------------------------------------------------------------------
    private static final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.

    // Statics ------------------------------------------------------------------------------------
    private static OBOFileDAO obofileDAO = DAOFactory.getInstance("anatomy008").getOBOFileDAO();

    // Actions ------------------------------------------------------------------------------------
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
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
            // Do your "SELECT * FROM File WHERE FileID" thing.
            OBOFile obofile = obofileDAO.findWithBinary(Long.valueOf(fileOid));

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
            response.reset();
            response.setBufferSize(DEFAULT_BUFFER_SIZE);
            response.setContentType(obofile.getContenttype());
            response.setHeader("Content-Length", String.valueOf(obofile.getContentlength()));
            response.setHeader("Content-Disposition", "attachment; filename=\"" + obofile.getName() + "\"");

            // Prepare streams.
            BufferedInputStream input = null;
            BufferedOutputStream output = null;

            // Open streams.
            input = new BufferedInputStream(obofile.getContent(), DEFAULT_BUFFER_SIZE);
            output = new BufferedOutputStream(response.getOutputStream(), DEFAULT_BUFFER_SIZE);

            // Write file contents to response.
            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }

            // Gently close streams.
            close(output);
            close(input);

        } 
        catch ( DAOException daoe ){
        	daoe.printStackTrace();
        }
    }

    // Helpers (can be refactored to public utility class) ----------------------------------------

    private static void close(Closeable resource) {
        if (resource != null) {
            try {
                resource.close();
            } 
            catch (IOException e) {
                // Do your thing with the exception. Print it, log it or mail it.
                e.printStackTrace();
            }
        }
    }

}