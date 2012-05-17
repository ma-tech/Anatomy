package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

import daolayer.LeafDAO;

import daomodel.Leaf;

import form.LeafForm;

import webapp.Config;

/**
 * Register an user.
 */
public class ListByRootNameServlet extends HttpServlet {

    // Constants ----------------------------------------------------------------------------------
    private static final String VIEW = "listbyrootname.jsp";
    private static final String ATTRIBUTE_FORM = "form";
    private static final String ATTRIBUTE_LEAFS = "leafs";

    
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
        
        //System.out.println("ListByRootNameServlet leafForm.listLeafsByRootNameByChildDesc");
        
        request.setAttribute(ATTRIBUTE_FORM, leafForm);

        // Process request and get result.
        List<Leaf> leafs = leafForm.listLeafsByRootNameByChildDesc(request);
        request.setAttribute(ATTRIBUTE_LEAFS, leafs);

        // Postback.
        request.getRequestDispatcher(VIEW).forward(request, response);
    }

}
