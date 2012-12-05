/*
*----------------------------------------------------------------------------------------------
* Project:      UsefulJava
*
* Title:        StringStreamConverter.java
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
* Description:  A Java class to Convert an InputStream into a String 
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

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ByteArrayInputStream;

public final class StringStreamConverter {

    // Init ---------------------------------------------------------------------------------------
    private StringStreamConverter() {
        // utility class, hide the constructor.
    }

    // Action -------------------------------------------------------------------------------------
    public static String convertStreamToString(InputStream is) throws IOException {
        /*
         * To convert the InputStream to String we use the
         * Reader.read(char[] buffer) method. We iterate until the
         * Reader return -1 which means there's no more data to
         * read. We use the StringWriter class to produce the string.
         */
        if (is != null) {
        	
            Writer writer = new StringWriter();
 
            char[] buffer = new char[1024];
            
            try {
            	
                Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                int n;
                
                while ((n = reader.read(buffer)) != -1) {
                	
                    writer.write(buffer, 0, n);
                }
            }
            finally {
            	
                is.close();
            }
            return writer.toString();
        } 
        else {        
        	
            return "";
        }
    }

    public static InputStream convertStringToStream(String string) throws IOException {
    	
    	InputStream is = new ByteArrayInputStream(string.getBytes("UTF-8"));
    	
      	return is;
    }
}