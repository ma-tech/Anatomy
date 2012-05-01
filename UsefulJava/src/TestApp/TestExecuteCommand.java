package TestApp;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.ArrayList;
import java.util.Iterator;

import Utility.ExecuteCommand;

public class TestExecuteCommand {

	public static void main(String args[]){  

		long startTime = System.currentTimeMillis();
    	
    	Date startDate = new Date();
    	String dateString = startDate.toString();
    	SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
    	
    	try {
    	   	Date parsed = format.parse(dateString);
            System.out.println("=========   ------------------");
            System.out.println("EXECUTING - TestExecuteCommand.java on " + parsed.toString());
            System.out.println("=========   ------------------");
            System.out.println("");

            //String commandString1 = "ls -al";
            //String commandString1 = "rm /Users/mwicks/Dropbox/Work/Anatomy/TEST/sqloutput.sql";
            String commandString1 = "/Users/mwicks/GitMahost/Anatomy/Database/Tasks/ConvertFromVersion008/Scripts/ExtractOBO.sh &";
            ArrayList<String> results = new ArrayList<String>();
            
    		long startCommand = System.currentTimeMillis();
    		results = ExecuteCommand.execute(commandString1);
    		long endCommand = System.currentTimeMillis();
    		long timeDiff = endCommand - startCommand;

       		System.out.println("Command: " + commandString1);

       		Iterator<String> iteratorresults = results.iterator();

          	while (iteratorresults.hasNext()) {
          		String result = iteratorresults.next();

           		System.out.println("         " + result.toString());
           		
          	}

            System.out.println("");
            System.out.println("DONE execute took " + timeDiff + " milliseconds");
            System.out.println("");

        	long endTime = System.currentTimeMillis();
        	long duration = endTime - startTime;

            System.out.println("====       ------------------");
            System.out.println("DONE ----- TestExecuteCommand.java took " + duration + " milliseconds");
            System.out.println("====       ------------------");

    	}
    	catch (Exception e) {
    		e.printStackTrace();
    	}

	}  
	
}
