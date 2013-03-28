package beans;

import java.io.Serializable;
import java.util.List;

import javax.faces.component.UICommand;
import javax.faces.event.ActionEvent;

import daolayer.DAOException;

import daointerface.DerivedPartOfDAO;

import daomodel.DerivedPartOf;

import webapp.Config;

/**
 * The example backing bean for effective datatable paging and sorting.
 *
 * @author BalusC
 * @link http://balusc.blogspot.com/2008/10/effective-datatable-paging-and-sorting.html
 */
public class DerivedPartOfBackingBean implements Serializable {

    // Properties ---------------------------------------------------------------------------------

    // DAO.
    private static DerivedPartOfDAO dao = Config.getInstance().getDAOFactory().getDAOImpl(DerivedPartOfDAO.class);

    // Data.
    private List<DerivedPartOf> dataList;
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
    public DerivedPartOfBackingBean() {
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
    public void pageFirst() throws Exception {
        page(0);
    }

    public void pageNext() throws Exception {
        page(firstRow + rowsPerPage);
    }

    public void pagePrevious() throws Exception {
        page(firstRow - rowsPerPage);
    }

    public void pageLast() throws Exception {
        page(totalRows - ((totalRows % rowsPerPage != 0) ? totalRows % rowsPerPage : rowsPerPage));
    }

    public void page(ActionEvent event) throws Exception {
        page(((Integer) ((UICommand) event.getComponent()).getValue() - 1) * rowsPerPage);
    }

    private void page(int firstRow) throws Exception {
        this.firstRow = firstRow;
        loadDataList(); // Load requested page.
    }

    // Sorting actions ----------------------------------------------------------------------------
    public void sort(ActionEvent event) throws Exception {
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
    private void loadDataList() throws Exception {

        // Load list and totalCount.
        try {
            dataList = dao.display(firstRow, rowsPerPage, sortField, sortAscending, searchTerm, "");
            totalRows = dao.count(searchTerm, "");
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
    public List<DerivedPartOf> getDataList() throws Exception {
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