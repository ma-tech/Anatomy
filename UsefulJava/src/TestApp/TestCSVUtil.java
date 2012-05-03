package testapp;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import java.io.File;
import java.io.InputStream;

import utility.CsvUtil;
import utility.FileUtil;

public class TestCSVUtil {

	public static void main(String args[]){  

		long startTime = System.currentTimeMillis();
    	
    	Date startDate = new Date();
    	String dateString = startDate.toString();
    	SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
    	
    	try {
    	   	Date parsed = format.parse(dateString);
            System.out.println("=========   -------------------");
            System.out.println("EXECUTING - TestCSVUtil.java on " + parsed.toString());
            System.out.println("=========   -------------------");
            System.out.println("");

            
            // Create CSV.
            List<List<String>> csvList = new ArrayList<List<String>>();
            csvList.add(Arrays.asList("field1", "field2", "field3"));
            csvList.add(Arrays.asList("field1,", "field2", "fie\"ld3"));
            csvList.add(Arrays.asList("\"field1\"", ",field2,", ",\",\",\""));

            // Format CSV.
            InputStream csvInput = CsvUtil.formatCsv(csvList, ';');

            // Save CSV.
            FileUtil.write(new File("/Users/mwicks/Desktop/test.csv"), csvInput);

            long endTime = System.currentTimeMillis();
        	long duration = endTime - startTime;

            System.out.println("");
            System.out.println("====       ---------------------");
            System.out.println("DONE ----- TestCSVUtil.java took " + duration + " milliseconds");
            System.out.println("====       ---------------------");

    	}
    	catch (Exception e) {
    		e.printStackTrace();
    	}

	}  
	
}
