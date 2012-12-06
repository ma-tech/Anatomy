package testapp;

import utility.Wrapper;

import utility.WhatIsThisString;

public class TestString {

	public static void main(String args[]){  

    	try {
    		long startTime = Wrapper.printPrologue("*", Wrapper.getExecutingClass());

    		for (int i = 0; i < args.length; i++ ) {
    		    String testString = args[i];

    		    if ( WhatIsThisString.isItAValidEmailAddress(testString) ) {
    		    	System.out.println(testString + " is a VALID email address.");  
    		    } 
    		    else if ( WhatIsThisString.isItNumeric(testString) ) {
    		    	System.out.println(testString + " is a VALID Number.");  
    		    } 
    		    else if ( WhatIsThisString.isItNumericWithLeadingSignAndDecimalPoint(testString) ) {
    		    	System.out.println(testString + " is a VALID Decimal Number with a Optional Leading Sign.");  
    		    } 
    		    else if ( WhatIsThisString.isItExponentialNumeric(testString) ) {
    		    	System.out.println(testString + " is a VALID Exponential Number.");  
    		    } 
    		    else if ( WhatIsThisString.isItAValidDate(testString, true) ) {
    		    	System.out.println(testString + " is a VALID UK (Metric) Date.");  
    		    } 
    		    else if ( WhatIsThisString.isItAValidDate(testString, false) ) {
    		    	System.out.println(testString + " is a VALID US (Non-Metric) Date.");  
    		    } 
    		    else if ( WhatIsThisString.isItAValidIPAddress(testString) ) {
    		    	System.out.println(testString + " is a VALID IP Address.");  
    		    } 
    		    else {  
    		    	System.out.println(testString + " is NEITHER Numeric in some way OR an Email Address OR a UK OR US Date OR an IP Address!");  
    		    }  
    		}

            Wrapper.printEpilogue("*", Wrapper.getExecutingClass(), startTime);
    	}
    	catch (Exception e) {
    		
    		e.printStackTrace();
    	}
	}  
}
