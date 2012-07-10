package controller;

import java.io.IOException;
import java.io.PrintWriter;

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
public class ListByRootNameJsonOnlyAggregatedServlet extends HttpServlet {

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
    	String leafTree = "";

        // Process request and get result.
        String outString = leafForm.checkLeafsByRootName(request);
        
        if ( outString.equals("SUCCESS!")) {
            List<Leaf> leafs = leafForm.listLeafsByRootNameByChildDesc(request);
            leafTree = leafDAO.convertLeafListToStringJsonAggregate(leafs);
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
