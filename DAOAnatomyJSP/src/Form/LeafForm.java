package Form;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;

import DAOLayer.DAOException;
import DAOLayer.LeafDAO;

import DAOModel.Leaf;

/**
 * This class holds all business logic related to the request processing of the Leaf DTO.
 * 
 */
public final class LeafForm extends Form {

    // Constants ----------------------------------------------------------------------------------
    private static final String FIELD_ROOT_NAME = "rootName";
    private static final String FIELD_ROOT_DESCRIPTION = "rootDescription";
    private static final String FIELD_RESULT = "result";

    
    // Variables ----------------------------------------------------------------------------------
    private LeafDAO leafDAO;

    
    // Constructors -------------------------------------------------------------------------------
    /**
     * Construct an Leaf Form associated with the given LeafDTO.
     */
    public LeafForm(LeafDAO leafDAO) {
        this.leafDAO = leafDAO;
    }

    
    // Form actions -------------------------------------------------------------------------------
    /**
     * Returns the Leafs based on the given request. It will gather all form fields,
     * process and validate the fields and retrieve the requested LEafs using the Leaf DAO 
     * associated with this form.
     */
    public List<Leaf> listLeafsByRootName(HttpServletRequest request) {
        
    	List<Leaf> leafs = new ArrayList<Leaf>();
    	Leaf leaf = new Leaf();

        try {
            processRootName(request, leaf);

            if (isSuccess()) {
            	leafs = leafDAO.listAllNodesByRootName(leaf.getRootName(), leaf.getRootName());
                setMessage(FIELD_RESULT, "Success!\n" + "Node " + leaf.getRootName() + " has " + leafs.size() + " Leaves \n");
            }
        } catch (DAOException e) {
            setError(FIELD_RESULT, "FIND failed due to database error." + 
                " Please try again later. Detail message: " + e.getMessage());
            e.printStackTrace();
        }

        return leafs;
    }


    /**
     * Returns the Leafs based on the given request. It will gather all form fields,
     * process and validate the fields and retrieve the requested LEafs using the Leaf DAO 
     * associated with this form.
     */
    public List<Leaf> listLeafsByRootDesc(HttpServletRequest request) {
        
    	List<Leaf> leafs = new ArrayList<Leaf>();
    	Leaf leaf = new Leaf();

        try {
            processRootDescription(request, leaf);

            if (isSuccess()) {
            	leafs = leafDAO.listAllNodesByRootDesc(leaf.getRootDescription(), leaf.getRootDescription());
                setMessage(FIELD_RESULT, "Success!\n" + "Node " + leaf.getRootDescription() + " has " + leafs.size() + " Leaves \n");
            }
        } catch (DAOException e) {
            setError(FIELD_RESULT, "FIND failed due to database error." + 
                " Please try again later. Detail message: " + e.getMessage());
            e.printStackTrace();
        }

        return leafs;
    }

    
    // Field processors ---------------------------------------------------------------------------
    /**
     * Process and validate the Name which is to be associated with the given Leaf.
     */
    public void processRootName(HttpServletRequest request, Leaf leaf) throws DAOException {
        String rootName = FormUtil.getFieldValue(request, FIELD_ROOT_NAME);

        if (rootName == null || FormUtil.isChanged(leaf.getRootName(), rootName)) {
            try {
                validateName(rootName);
            } catch (ValidatorException e) {
                setError(FIELD_ROOT_NAME, e.getMessage());
            }
            leaf.setRootName(rootName);
        }
    }


    /**
     * Process and validate the Description which is to be associated with the given Leaf.
     */
    public void processRootDescription(HttpServletRequest request, Leaf leaf) throws DAOException {
        String rootDesc = FormUtil.getFieldValue(request, FIELD_ROOT_DESCRIPTION);

        if (rootDesc == null || FormUtil.isChanged(leaf.getRootDescription(), rootDesc)) {
            try {
                validateDescription(rootDesc);
            } catch (ValidatorException e) {
                setError(FIELD_ROOT_DESCRIPTION, e.getMessage());
            }
            leaf.setRootDescription(rootDesc);
        }
    }


    // Field validators ---------------------------------------------------------------------------
    /**
     * Validate the given Name.
     *  It will check if it is not null, is at least 10 characters long according to the 
     *  Leaf DAO associated with this form.
     */
    public void validateName(String name) throws ValidatorException, DAOException {
        if (name != null) {
            if (name.length() < 10) {
                throw new ValidatorException("NAME should be at least 10 characters long.");
            } 
        } else {
            throw new ValidatorException("Please enter a NAME.");
        }
    }
    
    /**
     * Validate the given Description.
     *  It will check if it is not null, is at least 2 characters long according to the 
     *  Leaf DAO associated with this form.
     */
    public void validateDescription(String desc) throws ValidatorException, DAOException {
        if (desc != null) {
            if (desc.length() < 1) {
                throw new ValidatorException("DESCRIPTION should be at least 1 character long.");
            } 
        } else {
            throw new ValidatorException("Please enter a DESCRIPTION.");
        }
    }
    
    
}