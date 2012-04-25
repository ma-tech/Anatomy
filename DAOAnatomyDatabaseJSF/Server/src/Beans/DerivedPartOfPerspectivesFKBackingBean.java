package Beans;

import java.io.Serializable;
import java.util.List;

import javax.faces.component.UICommand;
import javax.faces.component.UIForm;
import javax.faces.component.html.HtmlInputHidden;

import javax.faces.event.ActionEvent;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import DAOLayer.DerivedPartOfPerspectivesFKDAO;
import DAOLayer.DAOException;

import DAOModel.DerivedPartOfPerspectivesFK;

import Utility.WhatIsThisString;
import Utility.FacesUtil;

import WebApp.Config;

/**
 * The example backing bean for effective datatable paging and sorting.
 *
 * @author BalusC
 * @link http://balusc.blogspot.com/2008/10/effective-datatable-paging-and-sorting.html
 */
public class DerivedPartOfPerspectivesFKBackingBean implements Serializable {

    // Properties ---------------------------------------------------------------------------------
	
	// Init ---------------------------------------------------------------------------------------
    private String theilerStage;

	// Form
    private UIForm form;
    
    // DAO.
    private static DerivedPartOfPerspectivesFKDAO dao = Config.getInstance().getDAOFactory().getDerivedPartOfPerspectivesFKDAO();

    // Data.
    private List<DerivedPartOfPerspectivesFK> dataList;
    private int totalRows;

    // Paging.
    private int firstRow;
    private int rowsPerPage;
    private int totalPages;
    private int pageRange;
    private Integer[] pages;
    private int currentPage;

    // Sorting.
    private String sortField;
    private boolean sortAscending;

    // Searching.
    private String searchTerm;
    private String searchId;
    private String searchDirection;
    private String searchStartStage;
    private String searchEndStage;
    private String searchPerspective;

    // Constructors -------------------------------------------------------------------------------
    public DerivedPartOfPerspectivesFKBackingBean() {
        // Set default values somehow (properties files?).

        // Default search term.
        searchTerm = ""; 
        // Default search Id.
        searchId = ""; 
        // Default search direction.
        searchDirection = "ALL"; 
        // Default search start stage.
        searchStartStage = "0"; 
        // Default search start stage.
        searchEndStage = "0"; 
        // Default search perspective.
        searchPerspective = "Whole mouse"; 
        // Default rows per page (max amount of rows to be displayed at once).
        rowsPerPage = 1; 
        // Default page range (max amount of page links to be displayed at once).
        pageRange = 10; 
        // Default sort field.
        sortField = "fullPath"; 
        // Default sort direction.
        sortAscending = true; 

    }

    // Session actions ----------------------------------------------------------------------------
    /*
    public void clearSession() {
        System.out.println("logout action invoked");
        FacesContext.getCurrentInstance().getExternalContext().getSession(arg0);
    }
    */

    // Paging actions -----------------------------------------------------------------------------
    public void pageFirst() {
    	
        page(0);
    }

    public void pageNext() {

       	this.firstRow = (Integer) FacesUtil.getSessionMapValue("firstRow");

    	page(firstRow + rowsPerPage);

    }

    public void pagePrevious() {
       	
    	this.firstRow = (Integer) FacesUtil.getSessionMapValue("firstRow");

      	page(firstRow - rowsPerPage);

    }

    public void pageLast() {
        
      	page(totalRows - ((totalRows % rowsPerPage != 0) ? totalRows % rowsPerPage : rowsPerPage));

    }

    public void page(ActionEvent event) {
        page(((Integer) ((UICommand) event.getComponent()).getValue() - 1) * rowsPerPage);
    }

    private void page(int firstRow) {
    	
      	FacesUtil.deleteApplicationMapValue("firstRow");

        this.firstRow = firstRow;
        
        if (validateStages(searchStartStage, searchEndStage)){
            loadDataList(); // Load requested page.
        }

        FacesUtil.setSessionMapValue("firstRow", firstRow);
    }

    // Sorting actions ----------------------------------------------------------------------------
    public void sort(ActionEvent event) {
        String sortFieldAttribute = (String) event.getComponent().getAttributes().get("sortField");
        
        // If the same field is sorted, then reverse order, else sort the new field ascending.
        if (sortField.equals(sortFieldAttribute)) {
            sortAscending = !sortAscending;
        } 
        else {
            sortField = sortFieldAttribute;
            sortAscending = true;
        }

        pageFirst(); // Go to first page and load requested page.
    }

    // Loaders ------------------------------------------------------------------------------------
    private void loadDataList() {

    	// Load list and totalCount.
        try {
            dataList = dao.display(firstRow, rowsPerPage, sortField, sortAscending, searchTerm, searchId, searchDirection, searchStartStage, searchEndStage, searchPerspective);
            totalRows = dao.count(searchTerm, searchId, searchDirection, searchStartStage, searchEndStage, searchPerspective);
        } 
        catch (DAOException e) {
            throw new RuntimeException(e); // Handle it yourself.
        }

        // Set currentPage, totalPages and pages.
        currentPage = (totalRows / rowsPerPage) - ((totalRows - firstRow) / rowsPerPage) + 1;
        totalPages = (totalRows / rowsPerPage) + ((totalRows % rowsPerPage != 0) ? 1 : 0);
        int pagesLength = Math.min(pageRange, totalPages);
        pages = new Integer[pagesLength];

        // firstPage must be greater than 0 and lesser than totalPages-pageLength.
        int firstPage = Math.min(Math.max(0, currentPage - (pageRange / 2)), totalPages - pagesLength);

        // Create pages (page numbers for page links).
        for (int i = 0; i < pagesLength; i++) {
            pages[i] = ++firstPage;
        }

    }
    

    // Validators ---------------------------------------------------------------------------------
    /**
     * Validate the chosen Stage Range - 
     *  Start must NOT be GT End!
     */
    public boolean validateStages(String stageStart, String stageEnd) {
    	
        int start = 0;
        int end = 0;
        
        if ( WhatIsThisString.isItNumeric(stageStart) ) {
            start = Integer.parseInt(stageStart);
        }
        
        if ( WhatIsThisString.isItNumeric(stageEnd) ) {
            end = Integer.parseInt(stageEnd);
        }
        
        if (start > end) {
        	FacesMessage message = new FacesMessage();
        	
        	message.setSeverity(FacesMessage.SEVERITY_ERROR);
        	message.setSummary("Start Stage is GREATER THAN End Stage:");
        	message.setDetail("Please try again!");

            FacesContext.getCurrentInstance().addMessage(null, message);
            
            return false;
        } 
        else {
        	return true;
        }
    }

    
    // Getters ------------------------------------------------------------------------------------
    /**
     * Returns the form.
     */
    public UIForm getForm() {
        return form;
    }

    public List<DerivedPartOfPerspectivesFK> getDataList() {
    	
        if (dataList == null) {
            // Preload page for the 1st view.
            if (validateStages(searchStartStage, searchEndStage)){

                loadDataList(); // Load requested page.

            }
        }
        return dataList;
    }
    public int getTotalRows() {
        return totalRows;
    }
    public int getFirstRow() {
        return firstRow;
    }
    public int getRowsPerPage() {
        return rowsPerPage;
    }
    public Integer[] getPages() {
        return pages;
    }
    public int getCurrentPage() {
        return currentPage;
    }
    public int getTotalPages() {
        return totalPages;
    }
    public String getSearchTerm() {
        return searchTerm;
    }
    public String getSearchId() {
        return searchId;
    }
    public String getSearchDirection() {
        return searchDirection;
    }
    public String getSearchStartStage() {
        return searchStartStage;
    }
    public String getSearchEndStage() {
        return searchEndStage;
    }
    public String getSearchPerspective() {
        return searchPerspective;
    }

    public String getTheilerStage() {
    	return theilerStage;
    }

    // Setters ------------------------------------------------------------------------------------
    /**
     * Set the form.
     */
    public void setForm(UIForm form) {
        this.form = form;
    }


    public void setRowsPerPage(int rowsPerPage) {
        this.rowsPerPage = rowsPerPage;
    }
    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }
    public void setSearchId(String searchId) {
        this.searchId = searchId;
    }
    public void setSearchDirection(String searchDirection) {
        this.searchDirection = searchDirection;
    }
    public void setSearchStartStage(String searchStartStage) {
        this.searchStartStage = searchStartStage;
    }
    public void setSearchEndStage(String searchEndStage) {
        this.searchEndStage = searchEndStage;
    }
    public void setSearchPerspective(String searchPerspective) {
        this.searchPerspective = searchPerspective;
    }

    public void setTheilerStage(String theilerStage) {

    	if ( theilerStage == null) {
        	this.theilerStage = (String) FacesUtil.getSessionMapValue("theilerStage");
        	this.searchStartStage = (String) FacesUtil.getSessionMapValue("searchStartStage");
        	this.searchEndStage = (String) FacesUtil.getSessionMapValue("searchEndStage");
    	}
    	else {
        	this.theilerStage = theilerStage;
        	
        	if (theilerStage.equals("TS01")) {
        		this.searchStartStage = "0";
        		this.searchEndStage = "0";
        	}
        	else if (theilerStage.equals("TS02")) {
        		this.searchStartStage = "1";
        		this.searchEndStage = "1";
        	}
        	else if (theilerStage.equals("TS03")) {
        		this.searchStartStage = "2";
        		this.searchEndStage = "2";
        	}
        	else if (theilerStage.equals("TS04")) {
        		this.searchStartStage = "3";
        		this.searchEndStage = "3";
        	}
        	else if (theilerStage.equals("TS05")) {
        		this.searchStartStage = "4";
        		this.searchEndStage = "4";
        	}
        	else if (theilerStage.equals("TS06")) {
        		this.searchStartStage = "5";
        		this.searchEndStage = "5";
        	}
        	else if (theilerStage.equals("TS07")) {
        		this.searchStartStage = "6";
        		this.searchEndStage = "6";
        	}
        	else if (theilerStage.equals("TS08")) {
        		this.searchStartStage = "7";
        		this.searchEndStage = "7";
        	}
        	else if (theilerStage.equals("TS09")) {
        		this.searchStartStage = "8";
        		this.searchEndStage = "8";
        	}
        	else if (theilerStage.equals("TS10")) {
        		this.searchStartStage = "9";
        		this.searchEndStage = "9";
        	}
        	else if (theilerStage.equals("TS11")) {
        		this.searchStartStage = "10";
        		this.searchEndStage = "10";
        	}
        	else if (theilerStage.equals("TS12")) {
        		this.searchStartStage = "11";
        		this.searchEndStage = "11";
        	}
        	else if (theilerStage.equals("TS13")) {
        		this.searchStartStage = "12";
        		this.searchEndStage = "12";
        	}
        	else if (theilerStage.equals("TS14")) {
        		this.searchStartStage = "13";
        		this.searchEndStage = "13";
        	}
        	else if (theilerStage.equals("TS15")) {
        		this.searchStartStage = "14";
        		this.searchEndStage = "14";
        	}
        	else if (theilerStage.equals("TS16")) {
        		this.searchStartStage = "15";
        		this.searchEndStage = "15";
        	}
        	else if (theilerStage.equals("TS17")) {
        		this.searchStartStage = "16";
        		this.searchEndStage = "16";
        	}
        	else if (theilerStage.equals("TS18")) {
        		this.searchStartStage = "17";
        		this.searchEndStage = "17";
        	}
        	else if (theilerStage.equals("TS19")) {
        		this.searchStartStage = "18";
        		this.searchEndStage = "18";
        	}
        	else if (theilerStage.equals("TS20")) {
        		this.searchStartStage = "19";
        		this.searchEndStage = "19";
        	}
        	else if (theilerStage.equals("TS21")) {
        		this.searchStartStage = "20";
        		this.searchEndStage = "20";
        	}
        	else if (theilerStage.equals("TS22")) {
        		this.searchStartStage = "21";
        		this.searchEndStage = "21";
        	}
        	else if (theilerStage.equals("TS23")) {
        		this.searchStartStage = "22";
        		this.searchEndStage = "22";
        	}
        	else if (theilerStage.equals("TS24")) {
        		this.searchStartStage = "23";
        		this.searchEndStage = "23";
        	}
        	else if (theilerStage.equals("TS25")) {
        		this.searchStartStage = "24";
        		this.searchEndStage = "24";
        	}
        	else if (theilerStage.equals("TS26")) {
        		this.searchStartStage = "25";
        		this.searchEndStage = "25";
        	}
        	else if (theilerStage.equals("TS27")) {
        		this.searchStartStage = "26";
        		this.searchEndStage = "26";
        	}
        	else if (theilerStage.equals("TS28")) {
        		this.searchStartStage = "27";
        		this.searchEndStage = "27";
        	}
        	
        	FacesUtil.setSessionMapValue("theilerStage", this.theilerStage);
        	FacesUtil.setSessionMapValue("searchStartStage", this.searchStartStage);
        	FacesUtil.setSessionMapValue("searchEndStage", this.searchEndStage);

    	}
    	
    }

}