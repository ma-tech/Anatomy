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
    	
    	String diatonic = validNotes[indexNotes] + " " + validModes[indexModes] + " (play over a " + validChords[indexModes] + " Chord)"; 
    	
    	return diatonic;
	}

    public static String getRandomFrets() throws Exception {

    	int indexNotes = getRandom(0, 11);
    	int indexModes = getRandom(0, 6);
    	
    	String diatonic = validNotes[indexNotes] + " " + validModes[indexModes] + " (play over a " + validChords[indexModes] + " Chord)"; 
    	
    	return diatonic;
	}

    public static String getRandomPentatonic() throws Exception {

    	int indexNotes = getRandom(0, 11);
    	int indexKeys = getRandom(0, 1);
    	int indexShapes = getRandom(0, 4);
    	
    	String pentatonic = validNotes[indexNotes] + " " + validKeys[indexKeys] + " Pentatonic, " + validShapes[indexShapes]; 
    	
    	return pentatonic;
	}

    public static String getRandomNote() throws Exception {

    	int indexNotes = getRandom(0, 11);
    	
    	String note = validNotes[indexNotes];
    	
    	return note;
	}

    public static ArrayList<String> getRandomUniqueDiatonic() throws Exception {

    	ArrayList<String> outputStrings = new ArrayList<String>();
    	
    	ArrayList<Integer> newIntegers = (ArrayList<Integer>) getRandomNumberList(11);
    	
        Iterator<Integer> iteratorNewIntegers = newIntegers.iterator();
        
        int i = 0;
        
     	while (iteratorNewIntegers.hasNext()) {
    		
     		i++;
     		
    		Integer newInt = iteratorNewIntegers.next();
    		
    		int indexNotes = newInt.intValue();

        	int indexModes = getRandom(0, 6);
        	
    		char padChar = ' ';

    		//String diatonic = utility.StringPad.pad(i, 2, padChar) + ". " + utility.StringPad.pad(validNotes[indexNotes], 16, padChar) + " " + utility.StringPad.pad(validModes[indexModes], 10, padChar) + " (play over a " + utility.StringPad.pad(validChords[indexModes], 18, padChar) + " Chord)";
    		String diatonic = i + ". " + validNotes[indexNotes] + " " + validModes[indexModes] + " (play over a " + validChords[indexModes] + " Chord)";
        	
        	outputStrings.add(diatonic);
     	}
     	
     	return outputStrings;
	}


    public static List<Integer> getRandomNumberList(int range) throws Exception {

    	List<Integer> nums = new ArrayList<Integer>(range + 1);
    
    	for (int i = 0; i <= range; i++) {
    		
    	   nums.add(new Integer(i));
    	}
    	
    	Collections.shuffle(nums);
    	
    	return nums;
    }

    public static ArrayList<String> getRandomUniqueFretPattern() throws Exception {

    	ArrayList<String> outputStrings = new ArrayList<String>();
    	
    	for ( int i = 0; i < 12; i++ ) {
    		
    		String outputString = "";
    		
        	ArrayList<Integer> newIntegers = (ArrayList<Integer>) getRandomNumberList(3);

        	Iterator<Integer> iteratorNewIntegers = newIntegers.iterator();
            
        	int j = 0;
        	
         	while (iteratorNewIntegers.hasNext()) {
        	
         		j++;
        		Integer newInt = iteratorNewIntegers.next();
        		int indexFrets = newInt.intValue();

        		char padChar = ' ';
        		
        		if ( j == 1) {
        			
                    //outputString = outputString + utility.StringPad.pad(i + 1, 2, padChar) + ".";
                    outputString = outputString + i + ". ";
        		}
        		
                //outputString = outputString + utility.StringPad.pad(validFrets[indexFrets], 3, padChar);
                outputString = outputString + validFrets[indexFrets] + " ";

        		if ( j == 4) {
        	
            		if ( i % 2 == 0 ) {
            			
                        //outputString = outputString + utility.StringPad.pad("UP", 5, padChar) + ": ";
                        outputString = outputString + "- UP";
            		}
            		else {
            			
                        //outputString = outputString + utility.StringPad.pad("DOWN", 5, padChar) + ": ";
                        outputString = outputString + "- DOWN";
            		}
        		}
         	}

         	outputStrings.add(outputString);
    	}
    	
     	
     	return outputStrings;
	}

    public static ArrayList<String> getRandomUniquePentatonic() throws Exception {

    	ArrayList<String> outputStrings = new ArrayList<String>();
    	
    	ArrayList<Integer> newIntegers = (ArrayList<Integer>) getRandomNumberList(11);
    	
        Iterator<Integer> iteratorNewIntegers = newIntegers.iterator();
        
    	int i = 0;

     	while (iteratorNewIntegers.hasNext()) {

     		i++;
     		
    		Integer newInt = iteratorNewIntegers.next();
    		int indexNotes = newInt.intValue();

        	int indexKeys = getRandom(0, 1);
        	int indexShapes = getRandom(0, 4);
    		char padChar = ' ';

        	//String pentatonic = utility.StringPad.pad(i, 2, padChar) + ". " + utility.StringPad.pad(validNotes[indexNotes], 16, padChar) + " " + validKeys[indexKeys] + " Pentatonic, " + validShapes[indexShapes];        	
        	String pentatonic = i + ". " + validNotes[indexNotes] + " " + validKeys[indexKeys] + " Pentatonic, " + validShapes[indexShapes];        	
        	outputStrings.add(pentatonic);
     	}
     	
     	return outputStrings;
	}
}
