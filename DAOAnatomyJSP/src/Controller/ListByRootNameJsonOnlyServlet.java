package Controller;

import java.io.IOException;
import java.io.PrintWriter;

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
public class ListByRootNameJsonOnlyServlet extends HttpServlet {

    // Constants ----------------------------------------------------------------------------------
    
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
        // Prepare form bean.
        LeafForm leafForm = new LeafForm(leafDAO);

        // Process request and get result.
        List<Leaf> leafs = leafForm.listLeafsByRootName(request);
        String leafTree = leafDAO.convertLeafListToStringJsonLines(leafs);
        
        java.io.PrintWriter out = response.getWriter();
        response.setContentType("text/html");           
        response.setHeader("Cache-Control", "no-cache");
        //System.out.println(leafTree);
        out.println(leafTree);

    }

}
