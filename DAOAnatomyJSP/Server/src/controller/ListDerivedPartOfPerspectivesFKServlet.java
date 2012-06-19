package controller;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import daolayer.DerivedPartOfPerspectivesFKDAO;

import daomodel.DerivedPartOfPerspectivesFK;
import daomodel.OBOFile;

import form.DerivedPartOfPerspectivesFKForm;

import webapp.Config;

/**
 * FIND a Relation by OID.
 */
public class ListDerivedPartOfPerspectivesFKServlet extends HttpServlet {

    // Constants ----------------------------------------------------------------------------------
    private static final String ATTRIBUTE_FORM = "form";
    private static final String ATTRIBUTE_TIMED_NODE = "timednode";

    
    // Vars ---------------------------------------------------------------------------------------
    private DerivedPartOfPerspectivesFKDAO derivedpartofperspectivesfkDAO;

    
    // HttpServlet actions ------------------------------------------------------------------------
    public void init() throws ServletException {
        // Obtain the UserDAO from DAOFactory by Config.
        this.derivedpartofperspectivesfkDAO = Config.getInstance(getServletContext()).getDAOFactory().getDerivedPartOfPerspectivesFKDAO();
    }

    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
    	String outString = "";
    	
    	DerivedPartOfPerspectivesFKForm derivedpartofperspectivesfkForm = new DerivedPartOfPerspectivesFKForm(derivedpartofperspectivesfkDAO);

        //System.out.println("ListDerivedPartOfPerspectivesFKServlet derivedpartofperspectivesfkForm.listDerivedPartOfPerspectivesFKsByStageAndSearchTerm");

        // Process request and get result.
        List<DerivedPartOfPerspectivesFK> derivedpartofperspectivesfks = derivedpartofperspectivesfkForm.listDerivedPartOfPerspectivesFKsByStageAndSearchTerm(request);

        Iterator<DerivedPartOfPerspectivesFK> iteratorDerivedPartOfPerspectivesFK = derivedpartofperspectivesfks.iterator();

        while (iteratorDerivedPartOfPerspectivesFK.hasNext()) {
        	DerivedPartOfPerspectivesFK derivedpartofperspectivesfk = iteratorDerivedPartOfPerspectivesFK.next();
       		outString = outString + derivedpartofperspectivesfk.getFullPathJson() + ";\n";
      	}

        
        // Postback.
        java.io.PrintWriter out = response.getWriter();
        response.setContentType("text/json");           
        //response.setContentType("text/html");           
        response.setHeader("Cache-Control", "no-cache");
        
        //System.out.println(outString);
        out.println(outString);

    }

}
