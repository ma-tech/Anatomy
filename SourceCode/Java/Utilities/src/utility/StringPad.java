/*
*----------------------------------------------------------------------------------------------
* Project:      UsefulJava
*
* Title:        StringPad.java
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
* Version:      1
*
* Description:  A Java class to PAD Strings
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

import utility.ObjectConverter;

public class StringPad{  

    public static String pad(String input, int minLength, char padChar) {
	
    	int length = input.length();  
		
    	if (length >= minLength) {
		
    		return input;
    	}
	    
    	StringBuffer buffer = new StringBuffer(length);  

    	while (length++ < minLength) {
		
    		buffer.append(padChar);  
    	}
		
    	buffer.append(input);  
		
    	return buffer.toString();  
    } 
	  
    public static String pad(int input, int minLength, char padChar) {
		  
    	String strInput = ObjectConverter.convert(input, String.class);
		
    	int length = strInput.length();
		
    	if (length >= minLength) {
		
    		return strInput;
    	}
		
    	StringBuffer buffer = new StringBuffer(length);  
		
    	while (length++ < minLength) {
		
    		buffer.append(padChar);  
    	}
		
    	buffer.append(input);  
	    
    	return buffer.toString();  
    } 
	
    public static String pad(long input, int minLength, char padChar) {

    	String strInput = ObjectConverter.convert(input, String.class);
		  
    	int length = strInput.length();
		
    	if (length >= minLength) {

    		return strInput;
    	}
		
    	StringBuffer buffer = new StringBuffer(length);  
		
    	while (length++ < minLength) {
		
    		buffer.append(padChar);  
    	}
	      
    	buffer.append(input);  
		
    	return buffer.toString();  
    } 
}