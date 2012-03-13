package Controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

import DAOLayer.LeafDAO;

import Model.Leaf;
import Model.LeafForm;

import WebApp.Config;

/**
 * Register an user.
 */
public class ListByRootNameJsonServlet extends HttpServlet {

    // Constants ----------------------------------------------------------------------------------
    private static final String VIEW = "listbyrootnamejson.jsp";
    private static final String ATTRIBUTE_FORM = "form";
    private static final String ATTRIBUTE_LEAF_TREE = "leafTree";

    
    // Vars ---------------------------------------------------------------------------------------
    private LeafDAO leafDAO;

    
    // HttpServlet actions ------------------------------------------------------------------------
    public void init() throws ServletException {
        // Obtain the UserDAO from DAOFactory by Config.
        this.leafDAO = Config.getInstance(getServletContext()).getDAOFactory().getLeafDAO();
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
        LeafForm leafForm = new LeafForm(leafDAO);
        request.setAttribute(ATTRIBUTE_FORM, leafForm);

        // Process request and get result.
        List<Leaf> leafs = leafForm.listLeafsByRootName(request);
        String leafTree = leafDAO.convertLeafListToStringJson(leafs);
        request.setAttribute(ATTRIBUTE_LEAF_TREE, leafTree);

        // Postback.
        request.getRequestDispatcher(VIEW).forward(request, response);
    }

}
