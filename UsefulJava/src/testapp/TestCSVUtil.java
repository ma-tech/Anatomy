package testapp;

import utility.Wrapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import java.io.File;
import java.io.InputStream;

import utility.CsvUtil;
import utility.FileUtil;

public class TestCSVUtil {

	public static void main(String args[]){  

    	try {
    		long startTime = Wrapper.printPrologue("*", Wrapper.getExecutingClass());
            
            // Create CSV.
            List<List<String>> csvList = new ArrayList<List<String>>();
            csvList.add(Arrays.asList("field1", "field2", "field3"));
            csvList.add(Arrays.asList("field1,", "field2", "fie\"ld3"));
            csvList.add(Arrays.asList("\"field1\"", ",field2,", ",\",\",\""));

            // Format CSV.
            InputStream csvInput = CsvUtil.formatCsv(csvList, ';');

            // Save CSV.
            FileUtil.write(new File("/Users/mwicks/Desktop/test.csv"), csvInput);

            Wrapper.printEpilogue("*", Wrapper.getExecutingClass(), startTime);
    	}
    	catch (Exception e) {
    		
    		e.printStackTrace();
    	}
	}  
}
