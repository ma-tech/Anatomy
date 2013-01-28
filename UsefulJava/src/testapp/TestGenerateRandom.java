package testapp;

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
    	    
    	    */
    	    for (int idx = 1; idx <= 10; ++idx){

                Wrapper.printMessage("GenerateRandom.getRandomChord(): " + GenerateRandom.getRandomChord(), "*", "*");
    	    }


    	    /*
    	    for (int idx = 1; idx <= 10; ++idx){

                Wrapper.printMessage("GenerateRandom.getRandomFretPattern(): " + GenerateRandom.getRandomFretPattern(), "*", "*");
         	}
        	*/


            Wrapper.printEpilogue("*", Wrapper.getExecutingClass(), startTime);
    	}
    	catch (Exception e) {
    		
    		e.printStackTrace();
    	}
	}  
}
