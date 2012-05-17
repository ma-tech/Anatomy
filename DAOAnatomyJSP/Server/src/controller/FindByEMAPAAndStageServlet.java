package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import daolayer.ExtraTimedNodeDAO;

import daomodel.ExtraTimedNode;

import form.TimedNodeForm;

import webapp.Config;

/**
 * FIND a Relation by OID.
 */
public class FindByEMAPAAndStageServlet extends HttpServlet {

    // Constants ----------------------------------------------------------------------------------
    private static final String ATTRIBUTE_FORM = "form";
    private static final String ATTRIBUTE_TIMED_NODE = "timednode";

    
    // Vars ---------------------------------------------------------------------------------------
    private ExtraTimedNodeDAO extratimednodeDAO;

    
    // HttpServlet actions ------------------------------------------------------------------------
    public void init() throws ServletException {
        // Obtain the UserDAO from DAOFactory by Config.
        this.extratimednodeDAO = Config.getInstance(getServletContext()).getDAOFactory().getExtraTimedNodeDAO();
    }

    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
    	TimedNodeForm timednodeForm = new TimedNodeForm(extratimednodeDAO);

        //System.out.println("FindByEMAPAAndStageServlet timednodeForm.findTimedNodeByEmapaAndStage");

        // Process request and get result.
        String outString = timednodeForm.findTimedNodeByEmapaAndStage(request);

        // Postback.
        java.io.PrintWriter out = response.getWriter();
        response.setContentType("text/html");           
        response.setHeader("Cache-Control", "no-cache");

        //System.out.println(outString);
        
        out.println(outString);
        
    }

}
