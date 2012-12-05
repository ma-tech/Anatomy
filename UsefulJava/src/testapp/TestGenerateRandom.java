package testapp;

import java.util.ArrayList;
import java.util.Iterator;

import utility.GenerateRandom;
import utility.Wrapper;

public class TestGenerateRandom {

	public static void main(String args[]){  
    	
    	try {

    		long startTime = Wrapper.printPrologue("LOW", Wrapper.getExecutingClass());

    		/*
    		for (int idx = 1; idx <= 10; ++idx){

                Wrapper.printMessage("GenerateRandom.getRandomString(): " + GenerateRandom.getRandomString(), "LOW", "LOW");
    	    }

    		for (int idx = 1; idx <= 10; ++idx){

                Wrapper.printMessage("GenerateRandom.getRandomPentatonic(): " + GenerateRandom.getRandomPentatonic(), "LOW", "LOW");
    	    }
    	    
    	    for (int idx = 1; idx <= 10; ++idx){

                Wrapper.printMessage("GenerateRandom.getRandomDiatonic(): " + GenerateRandom.getRandomDiatonic(), "LOW", "LOW");
    	    }
    	    */

        	ArrayList<String> outputStrings = GenerateRandom.getRandomUniquePentatonic();
            Iterator<String> iteratorOutputStrings = outputStrings.iterator();
            int i = 0;
            
         	while (iteratorOutputStrings.hasNext()) {
        		
         		i++;
                Wrapper.printMessage(i + ": " + iteratorOutputStrings.next(), "LOW", "LOW");
         	}

         	/*
        	ArrayList<String> outputStrings2 = GenerateRandom.getRandomUniqueDiatonic();
            Iterator<String> iteratorOutputStrings2 = outputStrings2.iterator();
            int k = 0;

            while (iteratorOutputStrings2.hasNext()) {
        		
         		k++;
                Wrapper.printMessage(k + ": " + iteratorOutputStrings2.next(), "LOW", "LOW");
         	}
         	*/

            Wrapper.printEpilogue("LOW", Wrapper.getExecutingClass(), startTime);
    	}
    	catch (Exception e) {
    		
    		e.printStackTrace();
    	}
	}  
}
