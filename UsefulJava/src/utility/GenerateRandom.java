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
   	    {"*****","****","***","**","*"};

    private static String [] validNotes = new String[] 
       	    {"A","A\u266F/B\u266D","B","C","C\u266F/D\u266D","D","D\u266F/E\u266D","E","F","F\u266F/G\u266D","G","G\u266F/A\u266D"};

    private static String [] validKeys = new String[] 
       	    {"Major","Minor"};

    private static String [] validShapes = new String[] 
       	    {"C Shape","A Shape","G Shape","E Shape","D Shape"};

    private static String [] validModes = new String[] 
       	    {"Ionian","Dorian","Phrygian","Lydian","Mixolydian","Aeolian","Locrian"};

    private static String [] validChords = new String[] 
       	    {"Major 7th","Minor 7th","Minor 7th","Major 7th","Dominant 7th","Minor 7th","Minor 7th \u266D 5th"};

    private static String [] validFrets = new String[] 
       	    {"1","2","3","4"};

    
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
    
   public static String getRandomString() throws Exception {

    	int index = getRandom(0, 4);
    	
    	return validValues[index];
	}

    public static String getRandomDiatonic() throws Exception {

    	int indexNotes = getRandom(0, 11);
    	int indexModes = getRandom(0, 6);
    	
    	String diatonic = validNotes[indexNotes] + " " + validModes[indexModes]; 
    	
    	return diatonic;
	}

    public static String getRandomChord() throws Exception {

    	int indexNotes = getRandom(0, 11);
    	int indexModes = getRandom(0, 6);
    	
    	String chord = validNotes[indexNotes] + " " + validModes[indexModes] + " (Over " + validNotes[indexNotes] + " " + validChords[indexModes] + ")"; 
    	
    	return chord;
	}

    public static String getRandomPentatonic() throws Exception {

    	int indexNotes = getRandom(0, 11);
    	int indexKeys = getRandom(0, 1);
    	int indexShapes = getRandom(0, 4);
    	
    	String pentatonic = validNotes[indexNotes] + " " + validKeys[indexKeys] + " " + validShapes[indexShapes]; 
    	
    	return pentatonic;
	}

    public static String getRandomNote() throws Exception {

    	int indexNotes = getRandom(0, 11);
    	
    	String note = validNotes[indexNotes];
    	
    	return note;
	}

    public static List<Integer> getRandomNumberList(int range) throws Exception {

    	List<Integer> nums = new ArrayList<Integer>(range + 1);
    
    	for (int i = 0; i <= range; i++) {
    		
    	   nums.add(new Integer(i));
    	}
    	
    	Collections.shuffle(nums);
    	
    	return nums;
    }

    public static String getRandomFretPattern() throws Exception {

    	String outputString = "";

    	ArrayList<Integer> newIntegers = (ArrayList<Integer>) getRandomNumberList(3);
        Iterator<Integer> iteratorOutputIntegers = newIntegers.iterator();
        
     	while (iteratorOutputIntegers.hasNext()) {
    		
        	outputString = outputString + validFrets[iteratorOutputIntegers.next()] + " ";
     	}

     	return outputString;
	}
}
