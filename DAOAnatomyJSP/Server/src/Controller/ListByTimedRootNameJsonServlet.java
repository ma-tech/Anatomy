package controller;

import java.io.IOException;

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
public class ListByTimedRootNameJsonServlet extends HttpServlet {

    // Constants ----------------------------------------------------------------------------------
    private static final String VIEW = "listbytimedrootnamejson.jsp";
    private static final String ATTRIBUTE_FORM = "form";
    private static final String ATTRIBUTE_LEAF_TREE = "leafTree";

    
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
        // Show view.
        request.getRequestDispatcher(VIEW).forward(request, response);
    }

    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        // Prepare form bean.
        TimedLeafForm timedleafForm = new TimedLeafForm(timedleafDAO);
        
        //System.out.println("ListByTimedRootNameJsonServlet timedleafForm.listTimedLeafsByRootNameByChildDesc");
        
        request.setAttribute(ATTRIBUTE_FORM, timedleafForm);

        // Process request and get result.
        List<TimedLeaf> timedleafs = timedleafForm.listTimedLeafsByRootNameByChildDesc(request);
        String leafTree = timedleafDAO.convertLeafListToStringJson(timedleafs);
        request.setAttribute(ATTRIBUTE_LEAF_TREE, leafTree);

        // Postback.
        request.getRequestDispatcher(VIEW).forward(request, response);
    }

}
