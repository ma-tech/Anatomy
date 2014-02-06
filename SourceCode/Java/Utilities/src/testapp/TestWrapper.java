package testapp;

import utility.Wrapper;

public class TestWrapper {

	public static void main(String args[]){  
    	
    	try {
    		long startTime = Wrapper.printPrologue("*", Wrapper.getExecutingClass());
    		
    		String message = "HELLO WORLD!";

    		System.out.println(message);
            
            Wrapper.printMessage(message, "*", "*");

            Wrapper.printEpilogue("*", Wrapper.getExecutingClass(), startTime);
    	}
    	catch (Exception e) {
    		
    		e.printStackTrace();
    	}
	}  
}
