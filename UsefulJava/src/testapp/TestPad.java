package testapp;

import utility.StringPad;

import utility.ObjectConverter;

import utility.Wrapper;

public class TestPad {

	public static void main(String args[]){  

    	try {
    		long startTime = Wrapper.printPrologue("LOW", Wrapper.getExecutingClass());

    		String testString = args[0];
    		System.out.println("testString                 = " + testString);
    		
    		int testStringAsInt = ObjectConverter.convert(args[0], Integer.class);
    		System.out.println("testStringAsInt            = " + testStringAsInt);
    		
    		int testLength = ObjectConverter.convert(args[1], Integer.class);
    		System.out.println("testLength                 = " + testLength);  

    		char[] characters = args[2].toCharArray();
    		char testCharacter = characters[0];
    		System.out.println("testCharacter              = " + testCharacter);  

    		String paddedString = StringPad.pad(testString, testLength, testCharacter); 
    		System.out.println("paddedString(input String) = " + paddedString);  

    		paddedString = StringPad.pad(testStringAsInt, testLength, testCharacter); 
    		System.out.println("paddedString(input int)    = " + paddedString);  

            char padChar = '0';
            int intX = 12345;
            System.out.println("utility.StringPad.pad(intX, 7, padChar):" + utility.StringPad.pad(intX, 7, padChar));
            
            Wrapper.printEpilogue("LOW", Wrapper.getExecutingClass(), startTime);
    	}
    	catch (Exception e) {
    		
    		e.printStackTrace();
    	}
	}  
}
