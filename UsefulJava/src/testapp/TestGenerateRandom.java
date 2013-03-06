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
    	    
    	    for (int idx = 1; idx <= 10; ++idx){

                Wrapper.printMessage("GenerateRandom.getRandomChord(): " + GenerateRandom.getRandomChord(), "*", "*");
    	    }
    	    */

    		System.out.println(GenerateRandom.randomString(-229985452) + " " + GenerateRandom.randomString(-147909649));

    		int[] a = {-73, -157512326, -112386651, 71425, -104434815, -128911, -88019, -7691161, 1115727};
    		int[] b = {-727295876, -128911, -1611659, -235516779};
    		int i;
        
    		for (i = 0; i < a.length; i++) {
        
    			System.out.println(GenerateRandom.randomString(a[i]));
    		}
    		for (i = 0; i < b.length; i++) {
    	        
    			System.out.println(GenerateRandom.randomString(b[i]));
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
