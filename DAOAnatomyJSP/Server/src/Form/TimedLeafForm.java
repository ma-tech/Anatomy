package Form;

import javax.servlet.http.HttpServletRequest;

import Utility.WhatIsThisString;

import java.util.ArrayList;
import java.util.List;

import DAOLayer.DAOException;
import DAOLayer.TimedLeafDAO;

import DAOModel.TimedLeaf;

/**
 * This class holds all business logic related to the request processing of the Leaf DTO.
 * 
 */
public final class TimedLeafForm extends Form {

    // Constants ----------------------------------------------------------------------------------
    private static final String FIELD_STAGE = "stage";
    private static final String FIELD_EMAP_NAME = "rootName";
    private static final String FIELD_RESULT = "result";

    
    // Variables ----------------------------------------------------------------------------------
    private TimedLeafDAO timedleafDAO;

    
    // Constructors -------------------------------------------------------------------------------
    /**
     * Construct an Leaf Form associated with the given LeafDTO.
     */
    public TimedLeafForm(TimedLeafDAO timedleafDAO) {
        this.timedleafDAO = timedleafDAO;
    }

    
    // Form actions -------------------------------------------------------------------------------
    /**
     * Returns the Leafs based on the given request. It will gather all form fields,
     * process and validate the fields and retrieve the requested LEafs using the Leaf DAO 
     * associated with this form.
     */
    public List<TimedLeaf> listTimedLeafsByRootName(HttpServletRequest request) {
        
    	List<TimedLeaf> timedleafs = new ArrayList<TimedLeaf>();
    	TimedLeaf timedleaf = new TimedLeaf();

        try {
            processStage(request, timedleaf);
            processRootName(request, timedleaf);

            if (isSuccess()) {
            	timedleafs = timedleafDAO.listAllTimedNodesByRootName(timedleaf.getRootName(), timedleaf.getStage(), timedleaf.getRootName(), timedleaf.getStage());
                setMessage(FIELD_RESULT, "SUCCESS:" + "Node " + timedleaf.getRootName() + " has " + timedleafs.size() + " Leaves \n");
            }
        }
        catch (DAOException e) {
            setError(FIELD_RESULT, "FAIL! FIND failed due to database error, please try again later. Detail message: " + e.getMessage());
            e.printStackTrace();
        }

        return timedleafs;
    }


    /**
     * Returns the Leafs based on the given request. It will gather all form fields,
     * process and validate the fields and retrieve the requested LEafs using the Leaf DAO 
     * associated with this form.
     */
    public String checkTimedLeafsByRootName(HttpServletRequest request) {
        
    	String outString = "";
    	List<TimedLeaf> timedleafs = new ArrayList<TimedLeaf>();
    	TimedLeaf timedleaf = new TimedLeaf();

        try {
            outString = processStage(request, timedleaf);
            
            if (outString.equals("")) {
            	outString = processRootName(request, timedleaf);
            	
                if (outString.equals("")) {
                    if (isSuccess()) {
                    	timedleafs = timedleafDAO.listAllTimedNodesByRootName(timedleaf.getRootName(), timedleaf.getStage(), timedleaf.getRootName(), timedleaf.getStage());
                    	if ( timedleafs.size() > 0 ){
                            outString = "SUCCESS!";
                    	}
                    	else {
                            outString = "FAIL! Node " + timedleaf.getRootName() + " has No Leaves.";
                    	}
                    }
                }
            }
        }
        catch (DAOException e) {
            setError(FIELD_RESULT, "FAIL! FIND failed due to database error, please try again later. Detail message: " + e.getMessage());
            e.printStackTrace();
        }

        return outString;
    }


    /**
     * Returns the Leafs based on the given request. It will gather all form fields,
     * process and validate the fields and retrieve the requested LEafs using the Leaf DAO 
     * associated with this form.
     */
    public List<TimedLeaf> listTimedLeafsByRootNameByChildDesc(HttpServletRequest request) {
        
    	List<TimedLeaf> timedleafs = new ArrayList<TimedLeaf>();
    	TimedLeaf timedleaf = new TimedLeaf();

        try {
            processStage(request, timedleaf);
            processRootName(request, timedleaf);

            if (isSuccess()) {
            	timedleafs = timedleafDAO.listAllTimedNodesByRootNameByChildDesc(timedleaf.getRootName(), timedleaf.getStage(), timedleaf.getRootName(), timedleaf.getStage());
                setMessage(FIELD_RESULT, "SUCCESS:" + "Node " + timedleaf.getRootName() + " has " + timedleafs.size() + " Leaves \n");
            }
        }
        catch (DAOException e) {
            setError(FIELD_RESULT, "FAIL! FIND failed due to database error, please try again later. Detail message: " + e.getMessage());
            e.printStackTrace();
        }

        return timedleafs;
    }


    /**
     * Returns the Leafs based on the given request. It will gather all form fields,
     * process and validate the fields and retrieve the requested LEafs using the Leaf DAO 
     * associated with this form.
     */
    public String checkTimedLeafsByRootNameByChildDesc(HttpServletRequest request) {
        
    	String outString = "";
    	List<TimedLeaf> timedleafs = new ArrayList<TimedLeaf>();
    	TimedLeaf timedleaf = new TimedLeaf();

        try {
            outString = processStage(request, timedleaf);
            
            if (outString.equals("")) {
            	outString = processRootName(request, timedleaf);
            	
                if (outString.equals("")) {
                    if (isSuccess()) {
                    	timedleafs = timedleafDAO.listAllTimedNodesByRootNameByChildDesc(timedleaf.getRootName(), timedleaf.getStage(), timedleaf.getRootName(), timedleaf.getStage());
                    	if ( timedleafs.size() > 0 ){
                            outString = "SUCCESS!";
                    	}
                    	else {
                            outString = "FAIL! Node " + timedleaf.getRootName() + " has No Leaves.";
                    	}
                    }
                }
            }
        }
        catch (DAOException e) {
            setError(FIELD_RESULT, "FAIL! FIND failed due to database error, please try again later. Detail message: " + e.getMessage());
            e.printStackTrace();
        }

        return outString;
    }


    
    // Field processors ---------------------------------------------------------------------------
    /**
     * Process the Stage which is to be associated with the given Leaf.
     */
    public String processStage(HttpServletRequest request, TimedLeaf timedleaf) 
    	throws DAOException {
        
    	String outString = "";
    	String stage = FormUtil.getFieldValue(request, FIELD_STAGE);

        if (stage == null || FormUtil.isChanged(timedleaf.getStage(), stage)) {
            try {
                validateStage(stage);
            }
            catch (ValidatorException e) {
                outString = e.getMessage();
            }
            timedleaf.setStage(stage);
        }
        
        return outString;
    }


    /**
     * Process and validate the Name which is to be associated with the given Leaf.
     */
    public String processRootName(HttpServletRequest request, TimedLeaf timedleaf) 
    	throws DAOException {
        
    	String outString = "";
    	String rootName = FormUtil.getFieldValue(request, FIELD_EMAP_NAME);

        if (rootName == null || FormUtil.isChanged(timedleaf.getRootName(), rootName)) {
            try {
                validateName(rootName);
            }
            catch (ValidatorException e) {
                outString = e.getMessage();
            }
            timedleaf.setRootName("EMAP:" + rootName);
        }
        
        return outString;
    }


    // Field validators ---------------------------------------------------------------------------
    /**
     * Validate the given EMAP Id.
     */
    public void validateName(String emapName) throws ValidatorException {
        
    	if ( emapName != null ) {
            if ( WhatIsThisString.isItNumeric(emapName) ){
                if ( emapName.length() < 1 ) {
                    throw new ValidatorException("FAIL! EMAP Id should be at least 1 Digit long!");
                } 
                if ( emapName.length() > 5 ) {
                    throw new ValidatorException("FAIL! EMAP Id should be at least 5 Digits long!");
                } 
            } 
            else {
            	throw new ValidatorException("FAIL! EMAP Id should be Digits ONLY!");
            }
        }
    	else {
            throw new ValidatorException("FAIL! Please enter an EMAP Id.");
        }
    	
    }
    
    /**
     * Validate the given Stage.
     */
    public void validateStage(String stage) throws ValidatorException {
        
    	if ( stage != null ) {
            if ( stage.length() < 4 ) {
                throw new ValidatorException("FAIL! Stage should be at least 4 Characters long!");
            } 
            if ( stage.length() > 4 ) {
                throw new ValidatorException("FAIL! Stage should be at least 4 Characters long!");
            } 
        }
    	else {
            throw new ValidatorException("FAIL! Please enter a Stage!");
        }
    	
    }
    
    
}
