package testapp;

import utility.Wrapper;
import utility.Factorial;

public class TestFactorial {

	public static void main(String args[]){  

    	try {
    		long startTime = Wrapper.printPrologue("*", Wrapper.getExecutingClass());
    		
            int input = Integer.parseInt(args[0]);
            double result = Factorial.factorial(input);
            System.out.println(input + " Factorial is: " + result);

            Wrapper.printEpilogue("*", Wrapper.getExecutingClass(), startTime);
    	}
    	catch (Exception e) {
    		
    		e.printStackTrace();
    	}
	}  
}