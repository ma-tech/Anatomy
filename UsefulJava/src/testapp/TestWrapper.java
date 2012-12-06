package testapp;

import utility.Wrapper;

public class TestWrapper {

	public static void main(String args[]){  
    	
    	try {
    		long startTime = Wrapper.printPrologue("*", Wrapper.getExecutingClass());
    		
            System.out.println("HELLO WORLD!");

            Wrapper.printEpilogue("*", Wrapper.getExecutingClass(), startTime);
    	}
    	catch (Exception e) {
    		
    		e.printStackTrace();
    	}
	}  
}
