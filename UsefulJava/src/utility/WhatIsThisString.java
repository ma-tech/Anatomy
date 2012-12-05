/*
*----------------------------------------------------------------------------------------------
* Project:      UsefulJava
*
* Title:        WhatIsThisString.java
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
* Description:  A Java class to verify Strings using Regular Expressions
* 
*               With Thanks to: 
*                http://www.zparacha.com/best-way-to-check-if-a-java-string-is-a-number/
*                http://www.zparacha.com/ultimate-java-regular-expression-to-validate-email-address/
*                http://www.zparacha.com/validate-email-ssn-phone-number-using-java-regular-expression/
*                http://www.zparacha.com/how-to-validate-date-using-java-regular-expression/
* 
* Maintenance:  Log changes below, with most recent at top of list.
*
* Who; When; What;
*
* Mike Wicks; January 2012; Create Class
*
*----------------------------------------------------------------------------------------------
*/
package utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WhatIsThisString{  

	public static boolean isItNumeric(String number){
		/*
		 * If the String is a number
		 * 
		 * Explanation:
		 *  [-+]?    = The string can have an optional - or + sign at the beginning.
		 *  [0-9]*   = The string can have any numbers of digits between 0 and 9 
		 *  \\.?     = The digits may have an optional decimal point.
		 *  [0-9]+$  = The string must have a digit at the end.
		 *  Bonus:
		 *   Exponentials = ^[-+]?[0-9]*\.?[0-9]+([eE][-+]?[0-9]+)?$
		 */
		boolean isValid = false;
		
        String expression = "[-+]?[0-9]*[0-9]+$";  
        CharSequence inputStr = number;  
           
        Pattern pattern = Pattern.compile(expression);  
        Matcher matcher = pattern.matcher(inputStr);  
        
        if ( matcher.matches() ) {  
            isValid = true;  
        }
        
        return isValid;  
    }  

	public static boolean isItNumericWithLeadingSignAndDecimalPoint(String number){

		boolean isValid = false;
		
        String expression = "[-+]?[0-9]*\\.?[0-9]+$";  
        CharSequence inputStr = number;  
           
        Pattern pattern = Pattern.compile(expression);  
        Matcher matcher = pattern.matcher(inputStr);  
        
        if ( matcher.matches() ) {  
            isValid = true;  
        }
        
        return isValid;  
    }  

	public static boolean isItExponentialNumeric(String number){

		boolean isValid = false;
		
        String expression = "^[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?$";  
        CharSequence inputStr = number;  
           
        Pattern pattern = Pattern.compile(expression);  
        Matcher matcher = pattern.matcher(inputStr);  
        
        if ( matcher.matches() ) {  
            isValid = true;  
        }
        
        return isValid;  
    }
	
	public static boolean isItAValidEmailAddress(String emailAddress){  
		   
		boolean isValid = false;
		
		String  expression="^[\\w\\-]([\\.\\w])+[\\w]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";  
		CharSequence inputStr = emailAddress;
		
		Pattern pattern = Pattern.compile(expression,Pattern.CASE_INSENSITIVE);  
		Matcher matcher = pattern.matcher(inputStr);  
		
        if ( matcher.matches() ) {  
            isValid = true;  
        }
        
        return isValid;  
		  
	}
	
	public static boolean isItAValidIPAddress(String ipAddress){  
		   
		boolean isValid = false;
		
		String  expression="^\\b(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\b$";  
		CharSequence inputStr = ipAddress;
		
		Pattern pattern = Pattern.compile(expression,Pattern.CASE_INSENSITIVE);  
		Matcher matcher = pattern.matcher(inputStr);  
		
        if ( matcher.matches() ) {  
            isValid = true;  
        }
        
        return isValid;  
		  
	}
	
	public static boolean  isItAValidDate(String date, boolean isEnglish){  
        /* 
        * ^[0-1][1-9]              : The month starts with a 0 and a digit between 1 and 9 
        * [- / ]?                  : Followed by  an optional "-" or "/". 
        * (0[1-9]|[12][0-9]|3[01]) : The day part must be either between 01-09, or 10-29 or 30-31. 
        * [- / ]?                  : Day part will be followed by  an optional "-" or "/". 
        * (18|19|20|21)\\d{2}$     : Year begins with either 18, 19, 20 or 21 and ends with two digits. 
        */  
		String monthExpression = "[0-1][1-9]";  
        String dayExpression = "(0[1-9]|[12][0-9]|3[01])";
        
        String expression = "";
        
        if ( isEnglish ) {  
            //RegEx to validate date in Metric format.  
            expression = "^" + dayExpression + "[- / ]?" + monthExpression + "[- /]?(18|19|20|21)\\d{2,4}";  
        }
        else {
            //RegEx to validate date in US format.  
            expression = "^" + monthExpression +"[- / ]?" + dayExpression + "[- /]?(18|19|20|21)\\d{2}"; 
        }

		boolean isValid = false;  
        
		CharSequence inputStr = date;  

        Pattern pattern = Pattern.compile(expression,Pattern.CASE_INSENSITIVE);  
        Matcher matcher = pattern.matcher(inputStr);  
        
        if ( matcher.matches() ) {  
            isValid = true;  
        }
        
        return isValid;  
    }
}  
