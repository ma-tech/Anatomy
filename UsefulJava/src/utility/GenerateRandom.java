/*
*----------------------------------------------------------------------------------------------
* Project:      UsefulJava
*
* Title:        GenerateRandom.java
*
* Date:         2012
*
* Author:       Mike Wicks
*
* Copyright:    2012
*               Medical Research Council, UK.
*               All rights reserved.
*
* Address:      MRC Human Genetics Unit,
*               Western General Hospital,
*               Edinburgh, EH4 2XU, UK.
*
* Version: 1
*
* Description:  A Java class to generate Random Numbers
* 
* Maintenance:  Log changes below, with most recent at top of list.
*
* Who; When; What;
*
* Mike Wicks; November 2012; Create Class
*
*----------------------------------------------------------------------------------------------
*/
package utility;

import java.util.Iterator;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class GenerateRandom {
	
    private static String [] validValues = new String[] 
   	    {"*****","***","*"};

    private static String [] validNotes = new String[] 
       	    {"A","A Sharp / B Flat","B","C","C Sharp / D Flat","D","D Sharp / E Flat","E","F","F Sharp / G Flat","G","G Sharp / A Flat"};

    private static String [] validKeys = new String[] 
       	    {"Major","Minor"};

    private static String [] validShapes = new String[] 
       	    {"C Shape","A Shape","G Shape","E Shape","D Shape"};

    private static String [] validModes = new String[] 
       	    {"Ionian","Dorian","Phrygian","Lydian","Mixolydian","Aeolian","Locrian"};

    private static String [] validChords = new String[] 
       	    {"Major 7th","Minor 7th","Minor 7th","Major 7th","Dominant 7th","Minor 7th","Minor 7th Flat 5th"};

    
    public static int getRandom(int lowerLimit, int upperLimit) throws Exception {

    	int aStart = lowerLimit;
        int aEnd = upperLimit;
        
		Random aRandom = new Random();
		
		long range = (long)aEnd - (long)aStart + 1;
		
	    // compute a fraction of the range, 0 <= frac < range
	    long fraction = (long)(range * aRandom.nextDouble());
	    int randomNumber =  (int)(fraction + aStart);		
    	
	    return randomNumber;
	}
    
    public static ArrayList<String> getRandomUniquePentatonic() throws Exception {

    	ArrayList<String> outputStrings = new ArrayList<String>();
    	
    	ArrayList<Integer> newIntegers = (ArrayList<Integer>) getRandomNumberList(11);
    	
        Iterator<Integer> iteratorNewIntegers = newIntegers.iterator();
        
     	while (iteratorNewIntegers.hasNext()) {
    		
    		Integer newInt = iteratorNewIntegers.next();
    		int indexNotes = newInt.intValue();

        	int indexKeys = getRandom(0, 1);
        	int indexShapes = getRandom(0, 4);

        	String pentatonic = validNotes[indexNotes] + " " + validKeys[indexKeys] + " Pentatonic, " + validShapes[indexShapes];
        	
        	outputStrings.add(pentatonic);
     	}
     	
     	return outputStrings;
	}

    public static String getRandomPentatonic() throws Exception {

    	int indexNotes = getRandom(0, 11);
    	int indexKeys = getRandom(0, 1);
    	int indexShapes = getRandom(0, 4);
    	
    	String pentatonic = validNotes[indexNotes] + " " + validKeys[indexKeys] + " Pentatonic, " + validShapes[indexShapes]; 
    	
    	return pentatonic;
	}

    public static ArrayList<String> getRandomUniqueDiatonic() throws Exception {

    	ArrayList<String> outputStrings = new ArrayList<String>();
    	
    	ArrayList<Integer> newIntegers = (ArrayList<Integer>) getRandomNumberList(11);
    	
        Iterator<Integer> iteratorNewIntegers = newIntegers.iterator();
        
     	while (iteratorNewIntegers.hasNext()) {
    		
    		Integer newInt = iteratorNewIntegers.next();
    		
    		int indexNotes = newInt.intValue();

        	int indexModes = getRandom(0, 6);
        	
        	String diatonic = validNotes[indexNotes] + " " + validModes[indexModes] + " (play over a " + validChords[indexModes] + " Chord)"; 
        	
        	outputStrings.add(diatonic);
     	}
     	
     	return outputStrings;
	}

    public static String getRandomDiatonic() throws Exception {

    	int indexNotes = getRandom(0, 11);
    	int indexModes = getRandom(0, 6);
    	
    	String diatonic = validNotes[indexNotes] + " " + validModes[indexModes] + " (play over a " + validChords[indexModes] + " Chord)"; 
    	
    	return diatonic;
	}

    public static String getRandomString() throws Exception {

    	int index = getRandom(0, 2);
    	
    	return validValues[index];
	}

    public static List<Integer> getRandomNumberList(int range) throws Exception {

    	List<Integer> nums = new ArrayList<Integer>(range + 1);
    
    	for (int i = 0; i <= range; i++) {
    		
    	   nums.add(new Integer(i));
    	}
    	
    	Collections.shuffle(nums);
    	
    	return nums;
    }
}
