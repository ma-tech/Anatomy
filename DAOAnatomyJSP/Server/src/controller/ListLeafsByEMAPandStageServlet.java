package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import daointerface.TimedLeafDAO;

import daomodel.TimedLeaf;

import form.TimedLeafForm;

import webapp.Config;

/**
 * FIND a Relation by OID.
 */
public class ListLeafsByEMAPandStageServlet extends HttpServlet {

    // Constants ----------------------------------------------------------------------------------
    private static final String ATTRIBUTE_FORM = "form";
    private static final String ATTRIBUTE_TIMED_NODE = "timedleaf";

    
    // Vars ---------------------------------------------------------------------------------------
    private TimedLeafDAO timedleafDAO;

    
    // HttpServlet actions ------------------------------------------------------------------------
    public void init() throws ServletException {
        // Obtain the UserDAO from DAOFactory by Config.
        this.timedleafDAO = Config.getInstance(getServletContext()).getDAOFactory().getDAOImpl(TimedLeafDAO.class);
    }

    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
    	String leafTree = "";
        TimedLeafForm timedleafForm = new TimedLeafForm(timedleafDAO);

        // Process request and get result.
        String outString = timedleafForm.checkTimedLeafsByRootNameByChildDesc(request);
        
        if ( outString.equals("SUCCESS!")) {
            List<TimedLeaf> timedleafs = timedleafForm.listTimedLeafsByRootNameByChildDesc(request);
            leafTree = timedleafDAO.convertLeafListToStringJsonLines(timedleafs);
        }
        else {
        	leafTree = outString;
        }
        
        java.io.PrintWriter out = response.getWriter();
        response.setContentType("text/json");           
        response.setHeader("Cache-Control", "no-cache");
        
        //System.out.println(leafTree);
        out.println(leafTree);
        
    }

}
