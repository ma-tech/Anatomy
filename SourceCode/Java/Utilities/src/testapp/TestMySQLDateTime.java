package testapp;

import utility.Wrapper;
import utility.MySQLDateTime;

public class TestMySQLDateTime {

	public static void main(String args[]){  

    	try {
    		long startTime = Wrapper.printPrologue("*", Wrapper.getExecutingClass());

    		Wrapper.printMessage("MySQLDateTime.now() = " + MySQLDateTime.now(), "*", "*");
    		
            Wrapper.printEpilogue("*", Wrapper.getExecutingClass(), startTime);
    	}
    	catch (Exception e) {
    		
    		e.printStackTrace();
    	}
	}  
}
