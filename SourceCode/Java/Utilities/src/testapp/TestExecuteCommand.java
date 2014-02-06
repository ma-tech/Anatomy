package testapp;

import java.util.ArrayList;
import java.util.Iterator;

import utility.Wrapper;
import utility.ExecuteCommand;

public class TestExecuteCommand {

	public static void main(String args[]){  

    	try {
    		long startTime = Wrapper.printPrologue("*", Wrapper.getExecutingClass());

    		//String commandString1 = "ls -al";
            //String commandString1 = "rm /Users/mwicks/Dropbox/Work/Anatomy/TEST/sqloutput.sql";
            String commandString1 = "/Users/mwicks/GitMahost/Anatomy/Database/Tasks/ConvertFromVersion008/Scripts/ExtractOBO.sh &";
            ArrayList<String> results = new ArrayList<String>();
            
    		results = ExecuteCommand.execute(commandString1);

       		System.out.println("Command: " + commandString1);

       		Iterator<String> iteratorresults = results.iterator();

          	while (iteratorresults.hasNext()) {

          		String result = iteratorresults.next();

           		System.out.println("         " + result.toString());
          	}
    		
            Wrapper.printEpilogue("*", Wrapper.getExecutingClass(), startTime);
    	}
    	catch (Exception e) {
    		
    		e.printStackTrace();
    	}
	}  
}
