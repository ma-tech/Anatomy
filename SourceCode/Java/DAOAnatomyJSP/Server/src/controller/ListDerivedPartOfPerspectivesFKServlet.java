package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import daointerface.DerivedPartOfPerspectivesFKDAO;

import daomodel.DerivedPartOfPerspectivesFK;

import form.DerivedPartOfPerspectivesFKForm;

import webapp.Config;

/**
 * FIND a Relation by OID.
 */
public class ListDerivedPartOfPerspectivesFKServlet extends HttpServlet {
    
    // Vars ---------------------------------------------------------------------------------------
    private DerivedPartOfPerspectivesFKDAO derivedpartofperspectivesfkDAO;
    
    // HttpServlet actions ------------------------------------------------------------------------
    public void init() throws ServletException {
        // Obtain the UserDAO from DAOFactory by Config.
        //this.derivedpartofperspectivesfkDAO = Config.getInstance(getServletContext()).getDAOFactory().getDerivedPartOfPerspectivesFKDAO();
        this.derivedpartofperspectivesfkDAO = Config.getInstance(getServletContext()).getDAOFactory().getDAOImpl(DerivedPartOfPerspectivesFKDAO.class);
    }

    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
    	String outString = "";
    	
    	DerivedPartOfPerspectivesFKForm derivedpartofperspectivesfkForm = new DerivedPartOfPerspectivesFKForm(derivedpartofperspectivesfkDAO);

        // Process request and get result.
        List<DerivedPartOfPerspectivesFK> derivedpartofperspectivesfks = derivedpartofperspectivesfkForm.listDerivedPartOfPerspectivesFKsByStageAndSearchTerm(request);

        outString = derivedpartofperspectivesfkForm.convertDerivedPartOfPerspectivesFKListToStringJson(derivedpartofperspectivesfks).replaceAll("\'", "\\'");
        
        // Postback.
        java.io.PrintWriter out = response.getWriter();
        response.setContentType("text/json");           
        //response.setContentType("text/html");           
        response.setHeader("Cache-Control", "no-cache");
        
        //System.out.println(outString);
        out.println(outString);
    }

}
