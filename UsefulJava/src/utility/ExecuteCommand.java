/*
*----------------------------------------------------------------------------------------------
* Project:      UsefulJava
*
* Title:        ExecuteCommand.java
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
* Description:  A Java class to execute shell commands
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.ArrayList;

public class ExecuteCommand {
	
    public static ArrayList<String> execute(String cmd) {

    	Runtime run = Runtime.getRuntime();
    	Process pr = null;
    	
    	try {
    		
    		pr = run.exec(cmd);
    	}
    	catch (IOException e) {
    		
    		e.printStackTrace();
    	}
    	
    	try {
    		
    		pr.waitFor();
    	}
    	catch (InterruptedException e) {
    		
    		e.printStackTrace();
    	}
    	
    	BufferedReader buf = new BufferedReader(new InputStreamReader(pr.getInputStream()));
    	String line = "";
    	
    	//int i = 0;
        ArrayList<String> lines = new ArrayList<String>();
    	
    	try {
    		
    		while ((line=buf.readLine())!=null) {
    			
    			lines.add(line);
    		}
    	}
    	catch (IOException e) {
    		
    		e.printStackTrace();
    	}
    	
    	return lines;    	
	}
}
