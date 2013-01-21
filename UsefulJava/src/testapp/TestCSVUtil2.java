package testapp;

import utility.Wrapper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import java.io.File;
import java.io.InputStream;

import utility.CsvUtil;
import utility.FileUtil;

public class TestCSVUtil2 {

	public static void main(String args[]){  

    	try {
    		long startTime = Wrapper.printPrologue("*", Wrapper.getExecutingClass());
            
            // Format InputStream for CSV.
            InputStream csvInput = FileUtil.readStream(new File("/Users/mwicks/Documents/GallusGallus/Scripts/xml_parser/TMP2ECAPA_2013-01-16.csv"));
            
            // Create CSV List
            List<List<String>> csvList = CsvUtil.parseCsv(csvInput);

            // Create Output List
            List<String> sqlList = new ArrayList<String>();

            Iterator<List<String>> iteratorRow = csvList.iterator();
            
         	while (iteratorRow.hasNext()) {
        		
        		List<String> row = iteratorRow.next();

                Iterator<String> iteratorColumn = row.iterator();
                
                int i = 1;
                
                String column1 = "";
                String column2 = "";
                
             	while (iteratorColumn.hasNext()) {
            		
            		String column = iteratorColumn.next();
            		
            		if ( i == 1 ) {
            			column1 = column;
            		}
            		
            		if ( i == 2) {
            			column2 = column;
            		}
            		
            		i++;
             	}

                String query = "update assays_development.components set abstract_component_id = \"" + column1 + "\" where abstract_component_id = \"" + column2 + "\";";

                sqlList.add(query);
         	}

            // Output File
            FileUtil.write(new File("/Users/mwicks/Documents/GallusGallus/Scripts/xml_parser/TMP2ECAPA_2013-01-16.sql"), sqlList);
            
            Wrapper.printEpilogue("*", Wrapper.getExecutingClass(), startTime);
    	}
    	catch (Exception e) {
    		
    		e.printStackTrace();
    	}
	}  
}
