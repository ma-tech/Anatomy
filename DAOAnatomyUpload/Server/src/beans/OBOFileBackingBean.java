package beans;

import java.io.Serializable;
import java.io.IOException;
import java.io.File;

import java.util.List;
import java.util.Map;

import javax.faces.component.UICommand;
import javax.faces.event.ActionEvent;

import javax.faces.context.FacesContext;

import daolayer.OBOFileDAO;
import daolayer.DAOException;

import daomodel.OBOFile;

import webapp.Config;


/**
 * The example backing bean for effective datatable paging and sorting.
 *
 * @author BalusC
 * @link http://balusc.blogspot.com/2008/10/effective-datatable-paging-and-sorting.html
 */
public class OBOFileBackingBean implements Serializable {

    // Constants ----------------------------------------------------------------------------------
    public static final String AUTH_KEY = "app.user.name";

    // Properties ---------------------------------------------------------------------------------
    
    // DAO.
    private static OBOFileDAO dao = Config.getInstance().getDAOFactory().getOBOFileDAO();

    // Data.
    private List<OBOFile> dataList;
    private int totalRows;

    // Paging.
    private int firstRow;
    private int rowsPerPage;
    private int totalPages;
    private int pageRange;
    private Integer[] pages;
    private int currentPage;

    // downloading.
    private String downloadField;

    // Sorting.
    private String sortField;
    private boolean sortAscending;

    // Searching.
    private String searchTerm;
    private String searchTable;

    // Constructors -------------------------------------------------------------------------------
    public OBOFileBackingBean() {
        // Set default values somehow (properties files?).
        
        // Default search term.
        searchTerm = ""; 
        // Default search term.
        searchTable = ""; 
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

    // Validate actions ----------------------------------------------------------------------------
    public void validate(ActionEvent event) {

    	Long validateFieldAttribute = (Long) event.getComponent().getAttributes().get("validateField");
        
        try {
            String oidString = String.valueOf(validateFieldAttribute);
            
            // My Mac Laptop
            String process = "/usr/bin/java";
            // Caperdonich
            //String process = "/opt/java6/bin/java";
            String argument1 =  "-jar";
            String argument2 =  "MainDAOExtractOBOAndValidate.jar";
            String argument3 =  oidString;
            ProcessBuilder pb = new ProcessBuilder(process, argument1, argument2, argument3);
            // My Mac Laptop
            String webInfLib = "/wtpwebapps/DAOAnatomyUpload/WEB-INF/lib";
            // Caperdonich
            //String webInfLib = "/webapps/DAOAnatomyUpload/WEB-INF/lib";
            String catalinaBase = System.getProperty("catalina.base");
            String directory = catalinaBase + webInfLib;

            Map<String, String> environ = pb.environment();
            pb.directory(new File(directory));
            Process p = pb.start();

            OBOFile obofile = dao.findWithBinary(validateFieldAttribute);
            obofile.setValidation("VALIDATING");
            dao.save(obofile);
        }
        catch (IOException ioe) {
            // Handle it yourself.
            throw new RuntimeException( ioe ); 
        }
        catch (DAOException daoe) {
            // Handle it yourself.
            throw new RuntimeException( daoe ); 
        }

        // redisplay current page.
        page( this.firstRow ); 
       
    }

    // Update actions ----------------------------------------------------------------------------
    public void update(ActionEvent event) {

    	Long updateFieldAttribute = (Long) event.getComponent().getAttributes().get("validateField");
        
        try {
            String oidString = String.valueOf(updateFieldAttribute);

            // My Mac Laptop
            String process = "/usr/bin/java";
            // Caperdonich
            //String process = "/opt/java6/bin/java";
            String argument1 =  "-jar";
            String argument2 =  "MainOBOLoadFileIntoDatabase.jar";
            String argument3 =  oidString;
            ProcessBuilder pb = new ProcessBuilder(process, argument1, argument2, argument3);
            // My Mac Laptop
            String webInfLib = "/wtpwebapps/DAOAnatomyUpload/WEB-INF/lib";
            // Caperdonich
            //String webInfLib = "/webapps/DAOAnatomyUpload/WEB-INF/lib";
            String catalinaBase = System.getProperty("catalina.base");
            String directory = catalinaBase + webInfLib;

            Map<String, String> environ = pb.environment();
            pb.directory(new File(directory));
            Process p = pb.start();

            OBOFile obofile = dao.findWithBinary(updateFieldAttribute);
            obofile.setValidation("UPDATING");
            dao.save(obofile);
        }
        
        catch (IOException ioe) {
            // Handle it yourself.
            throw new RuntimeException( ioe ); 
        }
        
        catch (DAOException daoe) {
            // Handle it yourself.
            throw new RuntimeException( daoe ); 
        }

        // redisplay current page.
        page( this.firstRow ); 
       
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

    // logout actions ----------------------------------------------------------------------------
    public String logout(ActionEvent event) {

    	try {
        	FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove(AUTH_KEY);
        	FacesContext.getCurrentInstance().getExternalContext().redirect("../index.html");
        	return null;
    	}
    	catch (IOException ioe) {
            throw new RuntimeException(ioe); // Handle it yourself.
    	}
    }

    // Loaders ------------------------------------------------------------------------------------
    private void loadDataList() {

        // Load list and totalCount.
        try {
            dataList = dao.display(firstRow, rowsPerPage, sortField, sortAscending, searchTerm, searchTable);
            totalRows = dao.count(searchTerm, searchTable);
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
    public List<OBOFile> getDataList() {
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
    public String getSearchTable() {
        return searchTable;
    }

    // Setters ------------------------------------------------------------------------------------
    public void setRowsPerPage(int rowsPerPage) {
        this.rowsPerPage = rowsPerPage;
    }
    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }
    public void setSearchTable(String searchTable) {
        this.searchTable = searchTable;
    }
}