/*
*----------------------------------------------------------------------------------------------
* Project:      UsefulJava
*
* Title:        MD5Hash.java
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
* Description:  Generate an MD5Hash for a given String
*                MD5 is kind of an one-way encryption. 
*                Very useful for Hashing passwords before saving in database. 
*                This function generates exactly the same hash as MySQL's own md5() function 
*                 should do.
*               
* Maintenance:  Log changes below, with most recent at top of list.
*
* Who; When; What;
*
* Mike Wicks; March 2012; Create Class
*
*----------------------------------------------------------------------------------------------
*/
package utility;

import java.io.UnsupportedEncodingException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Hash{  

	public static String convert(String string) {
	    
		byte[] hash;

	    try {
	    	
	        hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
	    }
	    catch (NoSuchAlgorithmException e) {
	    	
	        // Unexpected exception. "MD5" is just hardcoded and supported.
	        throw new RuntimeException("MD5 should be supported?", e);
	    }
	    catch (UnsupportedEncodingException e) {
	    	
	        // Unexpected exception. "UTF-8" is just hardcoded and supported.
	        throw new RuntimeException("UTF-8 should be supported?", e);
	    }

	    StringBuilder hex = new StringBuilder(hash.length * 2);
	    
	    for (byte b : hash) {
	    	
	        if ((b & 0xff) < 0x10) {
	        	
	        	hex.append("0");
	        }
	        
	        hex.append(Integer.toHexString(b & 0xff));
	    }
	    
	    return hex.toString();
	}
}

