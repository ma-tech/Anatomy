package form;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;

import daolayer.DAOException;
import daolayer.ExtraTimedNodeDAO;

import daomodel.ExtraTimedNode;

import utility.WhatIsThisString;

/**
 * This class holds all business logic related to the request processing of the Leaf DTO.
 * 
 */
public final class TimedNodeForm extends Form {

    // Constants ----------------------------------------------------------------------------------
    private static final String FIELD_NODE_EMAP = "publicEmapId";
    private static final String FIELD_NODE_EMAPA = "publicEmapaId";
    private static final String FIELD_NODE_STAGE = "stageSeq";
    private static final String FIELD_RESULT = "result";

    
    // Variables ----------------------------------------------------------------------------------
    private ExtraTimedNodeDAO extratimednodeDAO;

    
    // Constructors -------------------------------------------------------------------------------
    /**
     * Construct an Leaf Form associated with the given LeafDTO.
     */
    public TimedNodeForm(ExtraTimedNodeDAO extratimednodeDAO) {
        this.extratimednodeDAO = extratimednodeDAO;
    }

    
    // Form actions -------------------------------------------------------------------------------
    /**
     * Returns the Leafs based on the given request. It will gather all form fields,
     * process and validate the fields and retrieve the requested LEafs using the Leaf DAO 
     * associated with this form.
     */
    public String findTimedNodeByEmap(HttpServletRequest request) {
        
    	ExtraTimedNode extratimednode = new ExtraTimedNode();
    	String outString = "";

        try {
        	outString = processEMAPId(request, extratimednode);

        	if ( outString.equals("")) {

                if (isSuccess()) {
                 	if (extratimednodeDAO.existEmapId(extratimednode.getPublicEmapId())) {
                       	extratimednode = extratimednodeDAO.findByEmap(extratimednode.getPublicEmapId());

                       	outString = "SUCCESS: " + "Node " + extratimednode.getPublicEmapId() + " is " + extratimednode.getPublicEmapaId() + " from " + extratimednode.getStageMinName() + " to " + extratimednode.getStageMaxName();
                   	}
                	else {
                    	outString = "FAIL! " + "Node " + extratimednode.getPublicEmapId() + " does NOT EXIST!";
                	}
                }
       	    }
        }
        catch (DAOException e) {
        	outString = "FAIL! FIND failed due to database error, please try again later. Detailed message: " + e.getMessage();
            e.printStackTrace();
        }

        //System.out.println(outString);
        return outString;
    }


    /**
     * Returns the Leafs based on the given request. It will gather all form fields,
     * process and validate the fields and retrieve the requested LEafs using the Leaf DAO 
     * associated with this form.
     */
    public String findTimedNodeByEmapaAndStage(HttpServletRequest request) {
        
    	ExtraTimedNode extratimednode = new ExtraTimedNode();
    	String outString = "";

        try {
        	outString = processEMAPAId(request, extratimednode);
        	
        	if ( outString.equals("")) {

        		outString = processStageSeq(request, extratimednode);

            	if ( outString.equals("")) {

                    if (isSuccess()) {
                    	if (extratimednodeDAO.existEmapaIdAndStageSeq(extratimednode.getPublicEmapaId(), extratimednode.getStageSeq())) {
                        	extratimednode = extratimednodeDAO.findByEmapaAndStage(extratimednode.getPublicEmapaId(), extratimednode.getStageSeq());

                        	outString = "SUCCESS: " + "Node " + extratimednode.getPublicEmapaId() + " is " + extratimednode.getPublicEmapId() + " at Stage Sequence " + extratimednode.getStageSeq();
                    	}
                    	else {
                        	outString = "FAIL! " + "Node " + extratimednode.getPublicEmapaId() + " does NOT EXIST at Stage Sequence " + extratimednode.getStageSeq();
                    	}
                    }
            	}
       	    }
        }
        catch (DAOException e) {
        	outString = "FAIL! FIND failed due to database error, please try again later. Detailed message: " + e.getMessage();
            e.printStackTrace();
        }

        //System.out.println(outString);
        return outString;
    }


    // Field processors ---------------------------------------------------------------------------
    /**
     * Process and validate the Name which is to be associated with the given Leaf.
     */
    public String processEMAPId(HttpServletRequest request, ExtraTimedNode extratimednode) throws DAOException {
    	
    	String outString = "";
        String emapId = FormUtil.getFieldValue(request, FIELD_NODE_EMAP);

        if (emapId == null || FormUtil.isChanged(extratimednode.getPublicEmapId(), emapId)) {
            
        	try {
        		validateEmap(emapId);
            } 
            catch (ValidatorException e) {
            	outString = e.getMessage();
            }
        	
        	extratimednode.setPublicEmapId("EMAP:" + emapId);
        }
        
        //System.out.println(outString);
        return outString;
    }


    /**
     * Process and validate the Name which is to be associated with the given Leaf.
     */
    public String processEMAPAId(HttpServletRequest request, ExtraTimedNode extratimednode) throws DAOException {
    	
    	String outString = "";
        String emapaId = FormUtil.getFieldValue(request, FIELD_NODE_EMAPA);

        if (emapaId == null || FormUtil.isChanged(extratimednode.getPublicEmapaId(), emapaId)) {
            
        	try {
        		validateEmapa(emapaId);
            } 
            catch (ValidatorException e) {
            	outString = e.getMessage();
            }
        	
        	extratimednode.setPublicEmapaId("EMAPA:" + emapaId);
        }
        
        //System.out.println(outString);
        return outString;
    }


    /**
     * Process and validate the Description which is to be associated with the given Leaf.
     */
    public String processStageSeq(HttpServletRequest request, ExtraTimedNode extratimednode) throws DAOException {
    	
    	String outString = "";
        String stageSeq = FormUtil.getFieldValue(request, FIELD_NODE_STAGE);

        if (stageSeq == null || FormUtil.isChanged(extratimednode.getStageSeq(), stageSeq)) {
        	
            try {
            	validateStageSeq(stageSeq);

            	long l = Long.parseLong(stageSeq);

                extratimednode.setStageSeq(l);
            }
            catch (ValidatorException e) {
            	outString = e.getMessage();
            }
            
        }
        
        //System.out.println(outString);
        return outString;
    }


    // Field validators ---------------------------------------------------------------------------
    /**
     * Validate the given Name.
     */
    public void validateEmap(String emapId) throws ValidatorException, DAOException {
        
    	if ( emapId != null ) {
            if ( WhatIsThisString.isItNumeric(emapId) ){
                if ( emapId.length() < 1 ) {
                    throw new ValidatorException("FAIL! EMAP Id should be at least 1 Digit long!");
                } 
                if ( emapId.length() > 5 ) {
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
     * Validate the given Name.
     */
    public void validateEmapa(String emapaId) throws ValidatorException, DAOException {
        
    	if ( emapaId != null ) {
            if ( WhatIsThisString.isItNumeric(emapaId) ){
                if ( emapaId.length() < 5 ) {
                    throw new ValidatorException("FAIL! EMAPA Id should be at least 5 Digits long!");
                } 
                if ( emapaId.length() > 5 ) {
                    throw new ValidatorException("FAIL! EMAPA Id should be at least 5 Digits long!");
                } 
            } 
            else {
            	throw new ValidatorException("FAIL! EMAPA Id should be Digits ONLY!");
            }
        }
    	else {
            throw new ValidatorException("FAIL! Please enter an EMAPA Id.");
        }
    	
    }
    
    /**
     * Validate the given Description.
     *  It will check if it is not null, is at least 2 characters long according to the 
     *  Leaf DAO associated with this form.
     */
    public void validateStageSeq(String stageSeq) throws ValidatorException, DAOException {
    	
        if ( stageSeq != null ) {
            
        	if ( stageSeq.length() < 1 ) {
                throw new ValidatorException("FAIL! Stage Sequence should be at least 1 Digit!");
            } 
        	if ( stageSeq.length() > 2 ) {
                throw new ValidatorException("FAIL! Stage Sequence cannot be more than 2 Digits!");
            } 
            if ( WhatIsThisString.isItNumeric(stageSeq) ){
            	int i = Integer.parseInt(stageSeq);
            	if ( i > 27 ){
                    throw new ValidatorException("FAIL! Stage Sequence cannot be more than 27!");
            	}
            	if ( i < 0 ){
                    throw new ValidatorException("FAIL! Stage Sequence cannot be less than 0!");
            	}
            }
            else {
                throw new ValidatorException("FAIL! Stage Sequence should be Digits ONLY!");
            }
        } 
        else {
            throw new ValidatorException("FAIL! Please enter a Stage Sequence.");
        }
        
    }
    
    
}
