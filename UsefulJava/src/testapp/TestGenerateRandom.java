package testapp;

import java.util.ArrayList;
import java.util.Iterator;

import utility.GenerateRandom;
import utility.Wrapper;

public class TestGenerateRandom {

	public static void main(String args[]){  
    	
    	try {

    		long startTime = Wrapper.printPrologue("*", Wrapper.getExecutingClass());

    		/*
    		for (int idx = 1; idx <= 10; ++idx){

                Wrapper.printMessage("GenerateRandom.getRandomString(): " + GenerateRandom.getRandomString(), "*", "*");
    	    }

    		for (int idx = 1; idx <= 10; ++idx){

                Wrapper.printMessage("GenerateRandom.getRandomPentatonic(): " + GenerateRandom.getRandomPentatonic(), "*", "*");
    	    }
    	    
    	    for (int idx = 1; idx <= 10; ++idx){

                Wrapper.printMessage("GenerateRandom.getRandomDiatonic(): " + GenerateRandom.getRandomDiatonic(), "*", "*");
    	    }
    	    */

        	ArrayList<String> outputStrings = GenerateRandom.getRandomUniquePentatonic();
            Iterator<String> iteratorOutputStrings = outputStrings.iterator();
            int i = 0;
            
         	while (iteratorOutputStrings.hasNext()) {
        		
         		i++;
                Wrapper.printMessage(i + ": " + iteratorOutputStrings.next(), "*", "*");
         	}

         	/*
        	ArrayList<String> outputStrings2 = GenerateRandom.getRandomUniqueDiatonic();
            Iterator<String> iteratorOutputStrings2 = outputStrings2.iterator();
            int k = 0;

            while (iteratorOutputStrings2.hasNext()) {
        		
         		k++;
                Wrapper.printMessage(k + ": " + iteratorOutputStrings2.next(), "*", "*");
         	}
         	*/

            Wrapper.printEpilogue("*", Wrapper.getExecutingClass(), startTime);
    	}
    	catch (Exception e) {
    		
    		e.printStackTrace();
    	}
	}  
}
