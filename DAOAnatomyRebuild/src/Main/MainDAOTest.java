package Main;

import java.text.SimpleDateFormat;

import java.util.Date;

import App.RunDAOTest;


public class MainDAOTest {

    public static void main(String[] args) throws Exception {

    	long startTime = System.currentTimeMillis();
    	
    	Date startDate = new Date();
    	String dateString = startDate.toString();
    	SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
    	Date parsed = format.parse(dateString);

        System.out.println("=========   -----------");
        System.out.println("EXECUTING - MainDAOTest.java on " + parsed.toString());
        System.out.println("=========   -----------");
        System.out.println("");

        RunDAOTest.run();

    	long endTime = System.currentTimeMillis();
    	long duration = endTime - startTime;

        System.out.println("");
        System.out.println("====       -----------");
        System.out.println("DONE ----- MainDAOTest.java took " + duration / 1000 + " seconds");
        System.out.println("====       -----------");

    }

}
