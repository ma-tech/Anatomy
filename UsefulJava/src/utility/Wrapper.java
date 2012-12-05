/*
*----------------------------------------------------------------------------------------------
* Project:      UsefulJava
*
* Title:        Wrapper.java
*
* Date:         2012
*
* Author:       Mike Wicks
*
* Copyright:    2012
*               Medical Research Council, UK.
*               All rights reserved.
*
* Address:      MRC Human Genetics Unit,
*               Western General Hospital,
*               Edinburgh, EH4 2XU, UK.
*
* Version: 1
*
* Description:  A Java class to print Prologue and Epilogues to Main Classes 
* 
* Maintenance:  Log changes below, with most recent at top of list.
*
* Who; When; What;
*
* Mike Wicks; November 2012; Create Class
*
*----------------------------------------------------------------------------------------------
*/
package utility;

import java.text.SimpleDateFormat;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Wrapper {
	
     /*
      * A List of Valid Message Levels - LOW, MEDIUM & HIGH 
      */
     private static final Set<String> VALID_VALUES = new HashSet<String>(Arrays.asList(
        new String[] 
    	    {"LOW","MEDIUM","HIGH"}
        ));

	/*
	 * Determine the class name of the currently executing class 
	 */
    public static String getExecutingClass() throws Exception {

		StackTraceElement[] stack = Thread.currentThread ().getStackTrace ();
	    StackTraceElement main = stack[stack.length - 1];
	    String executingClass = main.getClassName ();
    	
    	return executingClass;
	}

	/*
	 * Print a Prologue Message to Sysout
	 */
    public static long printPrologue(String requestPriority, String executingClass) throws Exception {

    	long startTime = System.currentTimeMillis();
    	Date startDate = new Date();
    	String dateString = startDate.toString();
    	SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
    	Date parsed = format.parse(dateString);
    	
    	char dash = '-';
    	String underlines = StringPad.pad("", executingClass.length(), dash);
    	
        Wrapper.printMessage("=========   " + underlines, "HIGH", requestPriority);
        Wrapper.printMessage("EXECUTING - " + executingClass + ".java on " + parsed.toString(), "HIGH", requestPriority);
        Wrapper.printMessage("=========   " + underlines, "HIGH", requestPriority);
        
        return startTime;
	}

	/*
	 * Print an Epilogue Message to Sysout
	 */
    public static void printEpilogue(String requestPriority, String executingClass, long startTime) throws Exception {

    	long endTime = System.currentTimeMillis();
    	long duration = endTime - startTime;
    	
    	char dash = '-';
    	String underlines = StringPad.pad("", executingClass.length(), dash);
    	
        Wrapper.printMessage("=========   " + underlines, "HIGH", requestPriority);
        Wrapper.printMessage("DONE      - " + executingClass + ".java took " + duration / 1000 + " seconds", "HIGH", requestPriority);
        Wrapper.printMessage("=========   " + underlines, "HIGH", requestPriority);
    }

	/*
	 * Print Messages to Sysout
	 */
    public static void printMessage(String message, String messagePriority, String requestPriority) throws Exception {

    	/*
    	 * Check that the messagePriority and requestPriority values are one of HIGH, MEDIUM or LOW
    	 */
    	if ( VALID_VALUES.contains( messagePriority ) &&
    		VALID_VALUES.contains( requestPriority ) ) {
    		
    		/*
    		 * If the Executing Message Level (requestPriority) is LOW
    		 *  then print ALL messages (HIGH, MEDIUM & LOW)
    		 */
        	if ( requestPriority.equals("LOW") && ( 
        			messagePriority.equals("HIGH") ||
        			messagePriority.equals("MEDIUM") ||
        			messagePriority.equals("LOW") ) ) {
    
        		System.out.println(messagePriority + " : " + message);
        	}
        	
    		/*
    		 * If the Executing Message Level (requestPriority) is MEDIUM
    		 *  then print ONLY messages of HIGH and MEDIUM Priority
    		 *  Ignore LOW priority Messages
    		 */
        	if ( requestPriority.equals("MEDIUM") && ( 
        	    	messagePriority.equals("HIGH") ||
        			messagePriority.equals("MEDIUM") ) ) {
        	    		
        		System.out.println(messagePriority + " : " + message);
        	}
        	
    		/*
    		 * If the Executing Message Level (requestPriority) is HIGH
    		 *  then print ONLY messages of HIGH Priority
    		 *  Ignore LOW and MEDIUM priority Messages
    		 */
        	if ( requestPriority.equals("HIGH") &&  
        			messagePriority.equals("HIGH") ) {
        	    
        		System.out.println(messagePriority + " : " + message);
        	}
    	}
    	else {
    		
	    	System.out.println("Invalid Input Parameters to utility.Wrapper.printMessage!");
    		System.out.println("message = " + message);
    		System.out.println("messagePriority = " + messagePriority);
    		System.out.println("requestPriority = " + requestPriority);
    	}
    }
}
