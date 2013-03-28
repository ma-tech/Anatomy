package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

import daointerface.LeafDAO;

import daomodel.Leaf;

import form.LeafForm;

import webapp.Config;

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
        this.leafDAO = Config.getInstance(getServletContext()).getDAOFactory().getDAOImpl(LeafDAO.class);
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        // Prepare form bean.
        LeafForm leafForm = new LeafForm(leafDAO);

        // Process request and get result.
        List<Leaf> leafs;
		
        try {

			leafs = leafForm.listLeafsByRootNameByChildDesc(request);
	        String leafTree = leafDAO.convertLeafListToStringJsonLines(leafs);
	        
	        java.io.PrintWriter out = response.getWriter();
	        response.setContentType("text/html");           
	        response.setHeader("Cache-Control", "no-cache");
	        //System.out.println(leafTree);
	        out.println(leafTree);
		} 
        catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
