package testapp;

import utility.FileNamingDateTime;
import utility.Wrapper;

public class TestFileNamingDateTime {

	public static void main(String args[]){  

    	try {
    		long startTime = Wrapper.printPrologue("*", Wrapper.getExecutingClass());

    		Wrapper.printMessage("FileNamingDateTime.now() = " + FileNamingDateTime.now(), "*", "*");
    		
            Wrapper.printEpilogue("*", Wrapper.getExecutingClass(), startTime);
    	}
    	catch (Exception e) {
    		
    		e.printStackTrace();
    	}
	}  
}
