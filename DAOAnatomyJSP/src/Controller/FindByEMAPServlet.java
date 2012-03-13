package Controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAOLayer.TimedNodeDAO;

import Model.TimedNode;
import Model.TimedNodeForm;

import WebApp.Config;

/**
 * FIND a Relation by OID.
 */
public class FindByEMAPServlet extends HttpServlet {

    // Constants ----------------------------------------------------------------------------------
    private static final String ATTRIBUTE_FORM = "form";
    private static final String ATTRIBUTE_TIMED_NODE = "timednode";

    
    // Vars ---------------------------------------------------------------------------------------
    private TimedNodeDAO timednodeDAO;

    
    // HttpServlet actions ------------------------------------------------------------------------
    public void init() throws ServletException {
        // Obtain the UserDAO from DAOFactory by Config.
        this.timednodeDAO = Config.getInstance(getServletContext()).getDAOFactory().getTimedNodeDAO();
    }

    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
    	TimedNodeForm timednodeForm = new TimedNodeForm(timednodeDAO);

        // Process request and get result.
        String outString = timednodeForm.findTimedNodeByEmap(request);

        java.io.PrintWriter out = response.getWriter();
        response.setContentType("text");           
        response.setHeader("Cache-Control", "no-cache");
        
        out.println(outString);
    }

}
