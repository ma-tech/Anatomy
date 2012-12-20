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
    	    {"*****","****","***","**","*"}
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
    	
        Wrapper.printMessage("========= : " + underlines, "*", requestPriority);
        Wrapper.printMessage("EXECUTING : " + executingClass + ".java on " + parsed.toString(), "*", requestPriority);
        Wrapper.printMessage("========= : " + underlines, "*", requestPriority);
        
        return startTime;
	}

	/*
	 * Print an Epilogue Message to Sysout
	 */
    public static void printEpilogue(String requestPriority, String executingClass, long startTime) throws Exception {

    	long endTime = System.currentTimeMillis();
    	long duration = endTime - startTime;
    	
    	Date endDate = new Date();
    	String dateString = endDate.toString();
    	SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
    	Date parsed = format.parse(dateString);

    	char dash = '-';
    	String underlines = StringPad.pad("", executingClass.length(), dash);
    	
        Wrapper.printMessage("========= : " + underlines, "*", requestPriority);
        Wrapper.printMessage("DONE      : " + executingClass + ".java on " + parsed.toString() + " took " + duration / 1000 + " seconds", "*", requestPriority);
        Wrapper.printMessage("========= : " + underlines, "*", requestPriority);
    }

	/*
	 * Print Messages to Sysout
	 */
    public static void printMessage(String message, String messagePriority, String requestPriority) throws Exception {

        char padChar = ' ';

        /*
    	 * Check that the messagePriority and requestPriority values are one of 
    	 * HIGH        - *, 
    	 * MEDIUM-HIGH - **, 
    	 * MEDIUM      - ***, 
    	 * MEDIUM-LOW  - ****, 
    	 * LOW         - *****
    	 */
    	if ( VALID_VALUES.contains( messagePriority ) &&
    		VALID_VALUES.contains( requestPriority ) ) {
    		
    		/*
    		 * If the Executing Message Level (requestPriority) is *****
    		 *  then print ALL messages 
    		 */
        	if ( requestPriority.equals("*****") && ( 
        			messagePriority.equals("*") ||
        			messagePriority.equals("**") ||
        			messagePriority.equals("***") ||
        			messagePriority.equals("****") ||
        			messagePriority.equals("*****") ) ) {

        		System.out.println(utility.StringPad.pad(messagePriority, 5, padChar) + " : " + message);
        	}
        	
    		/*
    		 * If the Executing Message Level (requestPriority) is ****
    		 *  then print ONLY messages of *, **, *** & **** Priority
    		 *  Ignore *****  priority Messages
    		 */
        	if ( requestPriority.equals("****") && ( 
        	    	messagePriority.equals("*") ||
        	    	messagePriority.equals("**") ||
        			messagePriority.equals("***") ||
        			messagePriority.equals("****") ) ) {
        	    		
        		System.out.println(utility.StringPad.pad(messagePriority, 5, padChar) + " : " + message);
        	}
        	
    		/*
    		 * If the Executing Message Level (requestPriority) is ***
    		 *  then print ONLY messages of *, **, & ***Priority
    		 *  Ignore **** & *****  priority Messages
    		 */
        	if ( requestPriority.equals("***") && ( 
        	    	messagePriority.equals("*") ||
        	    	messagePriority.equals("**") ||
        			messagePriority.equals("***") ) ) {
        	    		
        		System.out.println(utility.StringPad.pad(messagePriority, 5, padChar) + " : " + message);
        	}
        	
    		/*
    		 * If the Executing Message Level (requestPriority) is **
    		 *  then print messages of * & ** Priority ONLY
    		 *  Ignore ***, **** & *****  priority Messages
    		 */
        	if ( requestPriority.equals("**") && ( 
        	    	messagePriority.equals("*") ||
        	    	messagePriority.equals("**") ) ) {
        	    		
        		System.out.println(utility.StringPad.pad(messagePriority, 5, padChar) + " : " + message);
        	}
        	
    		/*
    		 * If the Executing Message Level (requestPriority) is HIGH
    		 *  then print ONLY messages of HIGH Priority
    		 *  Ignore LOW and MEDIUM priority Messages
    		 */
        	if ( requestPriority.equals("*") &&  
        			messagePriority.equals("*") ) {
        	    
        		System.out.println(utility.StringPad.pad(messagePriority, 5, padChar) + " : " + message);
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
