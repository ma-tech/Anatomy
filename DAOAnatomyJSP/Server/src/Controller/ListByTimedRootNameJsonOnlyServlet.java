package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

import daolayer.TimedLeafDAO;

import daomodel.TimedLeaf;

import form.TimedLeafForm;

import webapp.Config;

/**
 * Register an user.
 */
public class ListByTimedRootNameJsonOnlyServlet extends HttpServlet {

    // Constants ----------------------------------------------------------------------------------
    
    // Vars ---------------------------------------------------------------------------------------
    private TimedLeafDAO timedleafDAO;

    
    // HttpServlet actions ------------------------------------------------------------------------
    public void init() throws ServletException {
        // Obtain the UserDAO from DAOFactory by Config.
        this.timedleafDAO = Config.getInstance(getServletContext()).getDAOFactory().getTimedLeafDAO();
    }

    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        // Prepare form bean.
        TimedLeafForm timedleafForm = new TimedLeafForm(timedleafDAO);
        
        //System.out.println("ListByTimedRootNameJsonOnlyServlet timedleafForm.listTimedLeafsByRootNameByChildDesc");
        
        // Process request and get result.
        List<TimedLeaf> timedleafs = timedleafForm.listTimedLeafsByRootNameByChildDesc(request);
        String leafTree = timedleafDAO.convertLeafListToStringJsonLines(timedleafs);
        
        java.io.PrintWriter out = response.getWriter();
        response.setContentType("text/html");           
        response.setHeader("Cache-Control", "no-cache");
        System.out.println(leafTree);
        //out.println(leafTree);

    }

}
