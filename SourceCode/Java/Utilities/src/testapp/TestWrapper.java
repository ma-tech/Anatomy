package testapp;

import utility.Wrapper;

public class TestWrapper {

	public static void main(String args[]){  
    	
    	try {
    		long startTime = Wrapper.printPrologue("*", Wrapper.getExecutingClass());
    		
    		String message = "HELLO WORLD!";

    		//System.out.println(message);
    		
            
            //Wrapper.printMessage(message, "*", "*");

    		long startTime1 = startTime - 10000000;
            Wrapper.printEpilogue("*", Wrapper.getExecutingClass(), startTime1);
            
    		long startTime2 = startTime - 1000000;
            Wrapper.printEpilogue("*", Wrapper.getExecutingClass(), startTime2);
            
    		long startTime3 = startTime - 1000;
            Wrapper.printEpilogue("*", Wrapper.getExecutingClass(), startTime3);
    	}
    	catch (Exception e) {
    		
    		e.printStackTrace();
    	}
	}  
}
