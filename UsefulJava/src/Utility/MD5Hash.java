/*
 * A Java class to reverse Strings - in 7 different ways!
 * 
 * M Wicks, 05/03/2012
 * 
 */
package utility;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Hash{  

	/*
	 * Generate MD5 hash for the given String. MD5 is kind of an one-way encryption. 
	 *  Very useful for Hashing passwords before saving in database. 
	 *  This function generates exactly the same hash as MySQL's own md5() function 
	 *   should do.
	 */
	public static String MD5Hash(String string) {
	    
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

