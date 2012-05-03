/*
 * A Java class to execute shell commands
 * 
 * M Wicks, 05/03/2012
 * 
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
    	
    	int i = 0;
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
