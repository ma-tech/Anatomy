/*
 * A Java class to reverse Strings - in 7 different ways!
 * 
 * M Wicks, 05/03/2012
 * 
 */
package utility;

import java.text.SimpleDateFormat;

import java.util.Date;

public class MySQLDateTime{  

    public static String now() {
	    
    	Date dt = new Date();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	return sdf.format(dt);
	}

    public static String formatDate(Date dt) {
	    
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	return sdf.format(dt);
	}
}  
