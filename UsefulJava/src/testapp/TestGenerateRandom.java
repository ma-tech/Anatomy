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

        	ArrayList<String> outputStrings = GenerateRandom.getRandomUniqueFretPattern();
            Iterator<String> iteratorOutputStrings = outputStrings.iterator();
            
         	while (iteratorOutputStrings.hasNext()) {
        		
                Wrapper.printMessage( iteratorOutputStrings.next(), "*", "*");
         	}

        	ArrayList<String> outputStrings2 = GenerateRandom.getRandomUniqueDiatonic();
            Iterator<String> iteratorOutputStrings2 = outputStrings2.iterator();

            while (iteratorOutputStrings2.hasNext()) {
        		
                Wrapper.printMessage( iteratorOutputStrings2.next(), "*", "*");
         	}

        	ArrayList<String> outputStrings3 = GenerateRandom.getRandomUniquePentatonic();
            Iterator<String> iteratorOutputStrings3 = outputStrings3.iterator();

            while (iteratorOutputStrings3.hasNext()) {
        		
                Wrapper.printMessage( iteratorOutputStrings3.next(), "*", "*");
         	}

            Wrapper.printEpilogue("*", Wrapper.getExecutingClass(), startTime);
    	}
    	catch (Exception e) {
    		
    		e.printStackTrace();
    	}
	}  
}
