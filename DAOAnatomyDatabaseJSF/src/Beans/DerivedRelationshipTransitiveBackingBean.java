package Beans;

import java.io.Serializable;
import java.util.List;

import javax.faces.component.UICommand;
import javax.faces.event.ActionEvent;

import DAOLayer.DerivedRelationshipTransitiveDAO;
import DAOLayer.DAOException;

import DAOModel.DerivedRelationshipTransitive;

import WebApp.Config;

/**
 * The example backing bean for effective datatable paging and sorting.
 *
 * @author BalusC
 * @link http://balusc.blogspot.com/2008/10/effective-datatable-paging-and-sorting.html
 */
public class DerivedRelationshipTransitiveBackingBean implements Serializable {

    // Properties ---------------------------------------------------------------------------------

    // DAO.
    private static DerivedRelationshipTransitiveDAO dao = Config.getInstance().getDAOFactory().getDerivedRelationshipTransitiveDAO();

    // Data.
    private List<DerivedRelationshipTransitive> dataList;
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

    // Constructors -------------------------------------------------------------------------------
    public DerivedRelationshipTransitiveBackingBean() {
        // Set default values somehow (properties files?).
        
        // Default search term.
        searchTerm = ""; 
        // Default rows per page (max amount of rows to be displayed at once).
        rowsPerPage = 30; 
        // Default page range (max amount of page links to be displayed at once).
        pageRange = 10; 
        // Default sort field.
        sortField = "oid"; 
        // Default sort direction.
        sortAscending = true; 
    }

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
        loadDataList(); // Load requested page.
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
            dataList = dao.display(firstRow, rowsPerPage, sortField, sortAscending, searchTerm);
            totalRows = dao.count(searchTerm);
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

    // Getters ------------------------------------------------------------------------------------
    public List<DerivedRelationshipTransitive> getDataList() {
        if (dataList == null) {
            // Preload page for the 1st view.
            loadDataList(); 
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

    // Setters ------------------------------------------------------------------------------------
    public void setRowsPerPage(int rowsPerPage) {
        this.rowsPerPage = rowsPerPage;
    }
    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

}