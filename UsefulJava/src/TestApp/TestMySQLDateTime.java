package TestApp;

import Utility.MySQLDateTime;

public class TestMySQLDateTime {

	public static void main(String args[]){  

    	try {
    		long startTime = System.currentTimeMillis();
        	
            System.out.println("=========   =================");
            System.out.println("EXECUTING - TestMySQLDateTime.java on " + Utility.MySQLDateTime.now());
            System.out.println("=========   =================");
            System.out.println("");

            System.out.println("");

        	long endTime = System.currentTimeMillis();
        	
        	long duration = endTime - startTime;

            System.out.println("====       =================");
            System.out.println("DONE ----- TestMySQLDateTime.java took " + duration + " milliseconds");
            System.out.println("====       =================");

    	}
    	catch (Exception e) {
    		e.printStackTrace();
    	}

	}  
	
}
