package form;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import daolayer.DAOException;
import daolayer.DerivedPartOfPerspectivesFKDAO;

import daomodel.DerivedPartOfPerspectivesFK;

import utility.ObjectConverter;

/**
 * This class holds all business logic related to the request processing of the DerivedPartOfPerspectivesFK DTO.
 * 
 */
public final class DerivedPartOfPerspectivesFKForm extends Form {

    // Constants ----------------------------------------------------------------------------------
    private static final String FIELD_STAGE = "stage";
    private static final String FIELD_SEARCH_TERM = "searchTerm";
    private static final String FIELD_RESULT = "result";

    
    // Variables ----------------------------------------------------------------------------------
    private DerivedPartOfPerspectivesFKDAO derivedpartofperspectivesfkDAO;

    
    // Constructors -------------------------------------------------------------------------------
    /**
     * Construct an DerivedPartOfPerspectivesFK Form associated with the given DerivedPartOfPerspectivesFKDTO.
     */
    public DerivedPartOfPerspectivesFKForm(DerivedPartOfPerspectivesFKDAO derivedpartofperspectivesfkDAO) {
        this.derivedpartofperspectivesfkDAO = derivedpartofperspectivesfkDAO;
    }

    
    // Form actions -------------------------------------------------------------------------------

    /*
     * Convert the Leaf ResultSet to JSON.
     */
    public String convertDerivedPartOfPerspectivesFKListToStringJson(List<DerivedPartOfPerspectivesFK> derivedpartofperspectivesfks) {

	    //String returnString = "[";
	    String fullPathJson = "";
	    String fullPathOidJson = "";

        Iterator<DerivedPartOfPerspectivesFK> iteratorDerivedPartOfPerspectivesFK = derivedpartofperspectivesfks.iterator();

    	while (iteratorDerivedPartOfPerspectivesFK.hasNext()) {
        	
        	DerivedPartOfPerspectivesFK derivedpartofperspectivesfk = iteratorDerivedPartOfPerspectivesFK.next();        	
        	
        	/*
        	System.out.println("##PS## convertDerivedPartOfPerspectivesFKListToStringJson jsonpath=" + derivedpartofperspectivesfk.getFullPathJson());
        	System.out.println("##PS## convertDerivedPartOfPerspectivesFKListToStringJson fullpath=" + derivedpartofperspectivesfk.getFullPath());
        	System.out.println("##PS## convertDerivedPartOfPerspectivesFKListToStringJson fullpathOids=" + derivedpartofperspectivesfk.getFullPathOids());
        	System.out.println("##PS## convertDerivedPartOfPerspectivesFKListToStringJson nodeemap=" + derivedpartofperspectivesfk.getNodeEmap());
        	System.out.println("##PS## convertDerivedPartOfPerspectivesFKListToStringJson nodeemapa=" + derivedpartofperspectivesfk.getNodeEmapa());
        	System.out.println("##PS## convertDerivedPartOfPerspectivesFKListToStringJson perspectivefk=" + derivedpartofperspectivesfk.getPerspectiveFK());
        	System.out.println("##PS## convertDerivedPartOfPerspectivesFKListToStringJson ancestor=" + derivedpartofperspectivesfk.getAncestor());
        	*/
        	
        	fullPathJson = fullPathJson + "\"" + derivedpartofperspectivesfk.getFullPath() + "\",";
        	fullPathOidJson = fullPathOidJson + "\"" + derivedpartofperspectivesfk.getFullPathOids() + "\",";
      	}
    	fullPathJson = fullPathJson.substring(0, fullPathJson.lastIndexOf(","));
    	fullPathOidJson = fullPathOidJson.substring(0, fullPathOidJson.lastIndexOf(","));

		//System.out.println("##PS## ");
		//System.out.println("##PS## convertDerivedPartOfPerspectivesFKListToStringJson returnString=" + "[[" + fullPathJson + "],[" + fullPathOidJson + "]]");
		//System.out.println("##PS## ");
		
        return "[[" + fullPathJson + "],[" + fullPathOidJson + "]]";
    }

    /**
     * Returns the DerivedPartOfPerspectivesFKs based on the given request. It will gather all form fields,
     * process and validate the fields and retrieve the requested LEafs using the DerivedPartOfPerspectivesFK DAO 
     * associated with this form.
     */
    public List<DerivedPartOfPerspectivesFK> listDerivedPartOfPerspectivesFKsByStageAndSearchTerm(HttpServletRequest request) {
        
    	List<DerivedPartOfPerspectivesFK> derivedpartofperspectivesfks = new ArrayList<DerivedPartOfPerspectivesFK>();
    	DerivedPartOfPerspectivesFK derivedpartofperspectivesfk = new DerivedPartOfPerspectivesFK();

        try {

        	int firstRow = 0;
        	int rowCount = 1000;
        	String sortField = "fullPath";
        	boolean sortAscending = true;
        	String searchTerm = processSearchTerm(request);
        	String searchId = "";
        	String searchDirection = "ALL";
        	String searchStartStage = ObjectConverter.convert(convertStage(processStage(request)), String.class);
        	String searchEndStage = "";
        	String searchPerspective = "Whole mouse";

        	if (isSuccess()) {
            	derivedpartofperspectivesfks = derivedpartofperspectivesfkDAO.display(firstRow, rowCount, sortField, sortAscending, searchTerm, searchId, searchDirection, searchStartStage, searchEndStage, searchPerspective);
                setMessage(FIELD_RESULT, 
                	"SUCCESS!\nSearch Term " +
                			searchTerm + " has " + derivedpartofperspectivesfks.size() + " matching Pathways in the Ontology Tree \n");
            }
        } 
        catch (DAOException e) {
            setError(FIELD_RESULT, 
            	"FAILURE!\nFIND failed due to database error. Please try again later. Detailed message: " + 
            	e.getMessage());
            e.printStackTrace();
        }

        return derivedpartofperspectivesfks;
    }

    
    // Field convertors ---------------------------------------------------------------------------
    /**
     * Process and validate the Name which is to be associated with the given DerivedPartOfPerspectivesFK.
     */
    public int convertStage(String stage) {
        
    	int outStage = 0;

    	if (stage.equals("TS01")) {
    		outStage = 0;
    	}
    	else if (stage.equals("TS02")) {
    		outStage = 1;
    	}
    	else if (stage.equals("TS03")) {
    		outStage = 2;
    	}
    	else if (stage.equals("TS04")) {
    		outStage = 3;
    	}
    	else if (stage.equals("TS05")) {
    		outStage = 4;
    	}
    	else if (stage.equals("TS06")) {
    		outStage = 5;
    	}
    	else if (stage.equals("TS07")) {
    		outStage = 6;
    	}
    	else if (stage.equals("TS08")) {
    		outStage = 7;
    	}
    	else if (stage.equals("TS09")) {
    		outStage = 8;
    	}
    	else if (stage.equals("TS10")) {
    		outStage = 9;
    	}
    	else if (stage.equals("TS11")) {
    		outStage = 10;
    	}
    	else if (stage.equals("TS12")) {
    		outStage = 11;
    	}
    	else if (stage.equals("TS13")) {
    		outStage = 12;
    	}
    	else if (stage.equals("TS14")) {
    		outStage = 13;
    	}
    	else if (stage.equals("TS15")) {
    		outStage = 14;
    	}
    	else if (stage.equals("TS16")) {
    		outStage = 15;
    	}
    	else if (stage.equals("TS17")) {
    		outStage = 16;
    	}
    	else if (stage.equals("TS18")) {
    		outStage = 17;
    	}
    	else if (stage.equals("TS19")) {
    		outStage = 18;
    	}
    	else if (stage.equals("TS20")) {
    		outStage = 19;
    	}
    	else if (stage.equals("TS21")) {
    		outStage = 20;
    	}
    	else if (stage.equals("TS22")) {
    		outStage = 21;
    	}
    	else if (stage.equals("TS23")) {
    		outStage = 22;
    	}
    	else if (stage.equals("TS24")) {
    		outStage = 23;
    	}
    	else if (stage.equals("TS25")) {
    		outStage = 24;
    	}
    	else if (stage.equals("TS26")) {
    		outStage = 25;
    	}
    	else if (stage.equals("TS27")) {
    		outStage = 26;
    	}
    	else if (stage.equals("TS28")) {
    		outStage = 27;
    	}
    	else if (stage.equals("CS01")) {
    		outStage = 0;
    	}
    	else if (stage.equals("CS02")) {
    		outStage = 1;
    	}
    	else if (stage.equals("CS03")) {
    		outStage = 2;
    	}
    	else if (stage.equals("CS04")) {
    		outStage = 3;
    	}
    	else if (stage.equals("CS05a")) {
    		outStage = 4;
    	}
    	else if (stage.equals("CS05b")) {
    		outStage = 5;
    	}
    	else if (stage.equals("CS05c")) {
    		outStage = 6;
    	}
    	else if (stage.equals("CS06a")) {
    		outStage = 7;
    	}
    	else if (stage.equals("CS06b")) {
    		outStage = 8;
    	}
    	else if (stage.equals("CS07")) {
    		outStage = 9;
    	}
    	else if (stage.equals("CS08")) {
    		outStage = 10;
    	}
    	else if (stage.equals("CS09")) {
    		outStage = 11;
    	}
    	else if (stage.equals("CS10")) {
    		outStage = 12;
    	}
    	else if (stage.equals("CS11")) {
    		outStage = 13;
    	}
    	else if (stage.equals("CS12")) {
    		outStage = 14;
    	}
    	else if (stage.equals("CS13")) {
    		outStage = 15;
    	}
    	else if (stage.equals("CS14")) {
    		outStage = 16;
    	}
    	else if (stage.equals("CS15")) {
    		outStage = 17;
    	}
    	else if (stage.equals("CS16")) {
    		outStage = 18;
    	}
    	else if (stage.equals("CS17")) {
    		outStage = 19;
    	}
    	else if (stage.equals("CS18")) {
    		outStage = 20;
    	}
    	else if (stage.equals("CS19")) {
    		outStage = 21;
    	}
    	else if (stage.equals("CS20")) {
    		outStage = 22;
    	}
    	else if (stage.equals("CS21")) {
    		outStage = 23;
    	}
    	else if (stage.equals("CS22")) {
    		outStage = 24;
    	}
    	else if (stage.equals("CS23")) {
    		outStage = 25;
    	}
    	else if (stage.equals("EGK-I")) {
    		outStage = 0;
    	}
    	else if (stage.equals("EGK-II")) {
    		outStage = 1;
    	}
    	else if (stage.equals("EGK-III")) {
    		outStage = 2;
    	}
    	else if (stage.equals("EGK-IV")) {
    		outStage = 3;
    	}
    	else if (stage.equals("EGK-V")) {
    		outStage = 4;
    	}
    	else if (stage.equals("EGK-VI")) {
    		outStage = 5;
    	}
    	else if (stage.equals("EGK-VII")) {
    		outStage = 6;
    	}
    	else if (stage.equals("EGK-VIII")) {
    		outStage = 7;
    	}
    	else if (stage.equals("EGK-IX")) {
    		outStage = 8;
    	}
    	else if (stage.equals("EGK-X")) {
    		outStage = 9;
    	}
    	else if (stage.equals("EGK-XI")) {
    		outStage = 10;
    	}
    	else if (stage.equals("EGK-XII")) {
    		outStage = 11;
    	}
    	else if (stage.equals("EGK-XIII")) {
    		outStage = 12;
    	}
    	else if (stage.equals("EGK-XIV")) {
    		outStage = 13;
    	}
    	else if (stage.equals("HH02")) {
    		outStage = 14;
    	}
    	else if (stage.equals("HH03")) {
    		outStage = 15;
    	}
    	else if (stage.equals("HH04")) {
    		outStage = 16;
    	}
    	else if (stage.equals("HH05")) {
    		outStage = 17;
    	}
    	else if (stage.equals("HH06")) {
    		outStage = 18;
    	}
    	else if (stage.equals("HH07")) {
    		outStage = 19;
    	}
    	else if (stage.equals("HH08")) {
    		outStage = 20;
    	}
    	else if (stage.equals("HH09")) {
    		outStage = 21;
    	}
    	else if (stage.equals("HH10")) {
    		outStage = 22;
    	}
    	else if (stage.equals("HH11")) {
    		outStage = 23;
    	}
    	else if (stage.equals("HH12")) {
    		outStage = 24;
    	}
    	else if (stage.equals("HH13")) {
    		outStage = 25;
    	}
    	else if (stage.equals("HH14")) {
    		outStage = 26;
    	}
    	else if (stage.equals("HH15")) {
    		outStage = 27;
    	}
    	else if (stage.equals("HH16")) {
    		outStage = 28;
    	}
    	else if (stage.equals("HH17")) {
    		outStage = 29;
    	}
    	else if (stage.equals("HH18")) {
    		outStage = 30;
    	}
    	else if (stage.equals("HH19")) {
    		outStage = 31;
    	}
    	else if (stage.equals("HH20")) {
    		outStage = 32;
    	}
    	else if (stage.equals("HH21")) {
    		outStage = 33;
    	}
    	else if (stage.equals("HH22")) {
    		outStage = 34;
    	}
    	else if (stage.equals("HH23")) {
    		outStage = 35;
    	}
    	else if (stage.equals("HH24")) {
    		outStage = 36;
    	}
    	else if (stage.equals("HH25")) {
    		outStage = 37;
    	}
    	else if (stage.equals("HH26")) {
    		outStage = 38;
    	}
    	else if (stage.equals("HH27")) {
    		outStage = 39;
    	}
    	else if (stage.equals("HH28")) {
    		outStage = 40;
    	}
    	else if (stage.equals("HH29")) {
    		outStage = 41;
    	}
    	else if (stage.equals("HH30")) {
    		outStage = 42;
    	}
    	else if (stage.equals("HH31")) {
    		outStage = 43;
    	}
    	else if (stage.equals("HH32")) {
    		outStage = 44;
    	}
    	else if (stage.equals("HH33")) {
    		outStage = 45;
    	}
    	else if (stage.equals("HH34")) {
    		outStage = 46;
    	}
    	else if (stage.equals("HH35")) {
    		outStage = 47;
    	}
    	else if (stage.equals("HH36")) {
    		outStage = 48;
    	}
    	else if (stage.equals("HH37")) {
    		outStage = 49;
    	}
    	else if (stage.equals("HH38")) {
    		outStage = 50;
    	}
    	else if (stage.equals("HH39")) {
    		outStage = 51;
    	}
    	else if (stage.equals("HH40")) {
    		outStage = 52;
    	}
    	else if (stage.equals("HH41")) {
    		outStage = 53;
    	}
    	else if (stage.equals("HH42")) {
    		outStage = 54;
    	}
    	else if (stage.equals("HH43")) {
    		outStage = 55;
    	}
    	else if (stage.equals("HH44")) {
    		outStage = 56;
    	}
    	else if (stage.equals("HH45")) {
    		outStage = 57;
    	}
    	else if (stage.equals("HH46")) {
    		outStage = 58;
    	}
    	else if (stage.equals("HH47")) {
    		outStage = 59;
    	}
    	else if (stage.equals("HH48")) {
    		outStage = 60;
    	}
    	else {
    		outStage = -1;
    	}

    	return outStage;
    }


    // Field processors ---------------------------------------------------------------------------
    /**
     * Process and validate the Name which is to be associated with the given DerivedPartOfPerspectivesFK.
     */
    public String processStage(HttpServletRequest request) 
    		throws DAOException {
        
    	String outString = "";
        String stage = FormUtil.getFieldValue(request, FIELD_STAGE);

        try {
            
        	validateStage(stage);
        	outString = stage;
        } 
        catch (ValidatorException e) {
        
        	outString = e.getMessage();
        }
        
        return outString;
    }


    /**
     * Process and validate the Description which is to be associated with the given DerivedPartOfPerspectivesFK.
     */
    public String processSearchTerm(HttpServletRequest request)
    		throws DAOException {

    	String outString = "";
    	String searchterm = FormUtil.getFieldValue(request, FIELD_SEARCH_TERM);

    	try {
        
    		validateSearchTerm(searchterm);
    		outString = searchterm; 
    	}
    	catch (ValidatorException e) {
        
        	outString = e.getMessage();
    	}
        
        return outString;
    }


    // Field validators ---------------------------------------------------------------------------
    /**
     * Validate the given Name.
     */
    public void validateStage(String stage) throws ValidatorException, DAOException {

    	if (stage != null) {
    		
            if (stage.length() < 4) {
                throw new ValidatorException("STAGE should be at least 4 characters long.");
            } 
        } 
    	else {
            throw new ValidatorException("Please enter a STAGE.");
        }
    }
    
    /**
     * Validate the given SearchTerm.
     */
    public void validateSearchTerm(String searchterm) throws ValidatorException, DAOException {

    	if (searchterm != null) {
    		
            if (searchterm.length() < 1) {
                throw new ValidatorException("SEARCHTERM should be at least 1 character long.");
            } 
        } 
    	else {
            throw new ValidatorException("Please enter a SEARCHTERM.");
        }
    }
    
    
}
