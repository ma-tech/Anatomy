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

import Model.DerivedPartOfPerspectivesFK;

import Utility.WhatIsThisString;

import WebApp.Config;

/**
 * The example backing bean for effective datatable paging and sorting.
 *
 * @author BalusC
 * @link http://balusc.blogspot.com/2008/10/effective-datatable-paging-and-sorting.html
 */
public class DerivedPartOfPerspectivesFKTS02BackingBean implements Serializable {

    // Properties ---------------------------------------------------------------------------------

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
    public DerivedPartOfPerspectivesFKTS02BackingBean() {
        // Set default values somehow (properties files?).
        
        // Default search term.
        searchTerm = ""; 
        // Default search Id.
        searchId = ""; 
        // Default search direction.
        searchDirection = "ALL"; 
        // Default search start stage.
        searchStartStage = "1"; 
        // Default search start stage.
        searchEndStage = "1"; 
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
        page(firstRow + rowsPerPage);
    }

    public void pagePrevious() {
        page(firstRow - rowsPerPage);
    }

    public void pageLast() {
        page(totalRows - ((totalRows % rowsPerPage != 0) ? totalRows % rowsPerPage : rowsPerPage));
    }

    public void page(ActionEvent event) {
        page(((Integer) ((UICommand) event.getComponent()).getValue() - 1) * rowsPerPage);
    }

    private void page(int firstRow) {
        this.firstRow = firstRow;
        
        if (validateStages(searchStartStage, searchEndStage)){
            loadDataList(); // Load requested page.
        }

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
    	
        //System.out.println("validateStages\nstageStart = " + stageStart + "\nstageEnd = " + stageEnd);

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

}