/*
*----------------------------------------------------------------------------------------------
* Project:      UsefulJava
*
* Title:        Factorial.java
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
* Description:  A Java class to calculate Factorials of given numbers
*                And why not?
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

public class Factorial {

    public static double factorial(int x) {
    	
    	if ( x < 0 ) {
    		return 0.0;
    	}
    	
    	double fact = 1.0;
    	
    	while ( x > 1 ) {
    		fact = fact * x;
    		
    		x = x - 1;
    	}
    	
        return fact;
    }
}