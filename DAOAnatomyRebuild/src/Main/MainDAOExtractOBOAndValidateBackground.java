/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyRebuild
*
* Title:        MainDAOExtractOBOAndValidate.java
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
* Description:  A Main Executable Class 
* 
* Maintenance:  Log changes below, with most recent at top of list.
*
* Who; When; What;
*
* Mike Wicks; February 2012; Create Class
*
*----------------------------------------------------------------------------------------------
*/

package main;

import java.io.IOException;

import java.util.*;

//import daolayer.DAOException;
//import daomodel.OBOFile;

public class MainDAOExtractOBOAndValidateBackground {
	/*
	 * Main Class
	 */
    public static void main(String[] args) throws Exception {

    	System.out.println("args[0] = " + args[0]);
    	
    	long startTime = System.currentTimeMillis();
    	/*
        System.out.println("=========   ----------------------------");
        System.out.println("EXECUTING - MainDAOExtractOBOAndValidate.java on " + utility.MySQLDateTime.now());
        System.out.println("=========   ----------------------------");
        System.out.println("");
        */
        /*
         * MAINLINE
         */
        try {
            /*
            String commandString1 = "pwd";
            ArrayList<String> results = new ArrayList<String>();
            
    		results = ExecuteCommand.execute(commandString1);

       		System.out.println("Command: " + commandString1);

       		Iterator<String> iteratorresults = results.iterator();

          	while (iteratorresults.hasNext()) {
          		String result = iteratorresults.next();

           		System.out.println("         " + result.toString());
          	}
          	*/
            String process = "java";
            String argument1 =  "-jar";
            String argument2 =  "MainDAOExtractOBOAndValidate.jar";
            String argument3 =  args[0];
            ProcessBuilder pb = new ProcessBuilder(process, argument1, argument2, argument3);
            Map env = pb.environment();
            env.clear();
            Process p = pb.start();
       		System.out.println("pb.environment().toString() " + pb.environment().toString());
       		System.out.println("p.toString() " + p.toString());
            

            /*
            OBOFile obofile = dao.findWithBinary(validateFieldAttribute);
            obofile.setValidation("PENDING");
            dao.save(obofile);
            */
        }
        catch (IOException ioe) {
            // Handle it yourself.
            throw new RuntimeException( ioe ); 
        }
        /*
        catch (DAOException daoe) {
            // Handle it yourself.
            throw new RuntimeException( daoe ); 
        }
        */

    	long endTime = System.currentTimeMillis();
    	long duration = endTime - startTime;
    	/*
        System.out.println("");
        System.out.println("====       ----------------------------");
        System.out.println("DONE ----- MainDAOExtractOBOAndValidate.java took " + duration / 1000 + " seconds");
        System.out.println("====       ----------------------------");
        */
    }
}
