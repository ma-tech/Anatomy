/*
 * A Java class to PAD Strings
 * 
 * M Wicks, 2012-11-14
 * 
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
