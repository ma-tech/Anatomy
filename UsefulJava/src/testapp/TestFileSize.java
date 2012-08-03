package testapp;

import java.text.SimpleDateFormat;

import java.util.Date;

import java.io.File;

import utility.FileUtil;


public class TestFileSize {

	public static void main(String args[]){  

		long startTime = System.currentTimeMillis();
    	
    	Date startDate = new Date();
    	String dateString = startDate.toString();
    	SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
    	
    	try {
    	   	Date parsed = format.parse(dateString);
            System.out.println("=========   ------------");
            System.out.println("EXECUTING - TestFileSize.java on " + parsed.toString());
            System.out.println("=========   ------------");
            System.out.println("");
            

            //String directory = "/Users/mwicks/eclipseWorkspace/DB2OBO_Sept_2011";
            
            if (args.length != 1) {
            	System.out.println(" ERROR - There MUST be 1 argument passed to this program!\n ERROR - Try Again!");
            }
            else {
                String directory = args[0];

                String[] filenames = null;
                File[] directories = null;
                File[] files = null;
                
                System.out.println("1. ALL File Names AND Directories within the directory:\n    " + directory);
                System.out.println("");

                filenames = FileUtil.listAllFilenamesInDirectory(directory);
                
                if (filenames[0].equals("EMPTY Directory!") || filenames[0].equals("Directory DOES NOT Exist!") ) {

                	System.out.println("ERROR!  Try Another Directory!");
                }
                else { 
                
                	for (int i=0; i<filenames.length; i++) {

                    	String filename = filenames[i];
                        System.out.println("filename: " + filename);
                    }
                }


                System.out.println("");
                System.out.println("2. All File Names AND Directories that DO NOT Start with a \".\" within the directory:\n    " + directory);
                System.out.println("");

                filenames = FileUtil.listAllUnhiddenFilenamesInDirectory(directory);
                
                if (filenames[0].equals("No UnHidden Files!") || filenames[0].equals("Directory DOES NOT Exist!") ) {

                	System.out.println("ERROR!  Try Another Directory!");
                }
                else { 
                
                	for (int i=0; i<filenames.length; i++) {

                    	String filename = filenames[i];
                        System.out.println("filename: " + filename);
                    }
                }

                
                System.out.println("");
                System.out.println("3. Directory Names ONLY within the directory:\n    " + directory);
                System.out.println("");
                
                // The list of files can also be retrieved as File objects
                directories = FileUtil.listAllDirectoriesInDirectory(directory);

                if ( directories == null) {
                	
                	System.out.println("ERROR! No Directories in Driectory, OR Directory does NOT Exist!");
                }
                else {
                	
                    for (int i=0; i<directories.length; i++) {

                    	// Get filename of file or directory
                        String subDirectory = directories[i].getName();
                        System.out.println("directory: " + subDirectory);
                    }
                }


                System.out.println("");
                System.out.println("4. File Names ONLY within the directory:\n    " + directory);
                System.out.println("");
                
                // The list of files can also be retrieved as File objects
                files = FileUtil.listAllFilesInDirectory(directory);

                if ( files == null) {
                	
                	System.out.println("ERROR! No Directories in Driectory, OR Directory does NOT Exist!");
                }
                else {
                	
                    for (int i=0; i<files.length; i++) {

                    	// Get filename of file or directory
                        String file = files[i].getName();
                        System.out.println("filename: " + file + " : " + FileUtil.roundObjectSize(files[i].length()));
                    }
                }

            }

            long endTime = System.currentTimeMillis();
        	long duration = endTime - startTime;
            System.out.println("");
            System.out.println("=========   ------------");
            System.out.println("DONE      - TestFileSize.java took " + duration + " milliseconds");
            System.out.println("=========   ------------");

    	}
    	catch (Exception e) {
    		e.printStackTrace();
    	}

	}  
	
}
