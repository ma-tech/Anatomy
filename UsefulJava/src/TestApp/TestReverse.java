package TestApp;

import java.text.SimpleDateFormat;
import java.util.Date;

import Utility.StringReverse;

public class TestReverse {

	public static void main(String args[]){  

		long startTime = System.currentTimeMillis();
    	
    	Date startDate = new Date();
    	String dateString = startDate.toString();
    	SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
    	
    	try {
    	   	Date parsed = format.parse(dateString);
            System.out.println("=========   ===========");
            System.out.println("EXECUTING - TestReverse.java on " + parsed.toString());
            System.out.println("=========   ===========");
            System.out.println("");

            String inString = "The quick brown fox jumps over the lazy dog";
            String outString = "";
            
    		long startReverse = System.currentTimeMillis();
    		outString = StringReverse.reverse1(inString);
    		long endReverse = System.currentTimeMillis();
    		long timeDiff = endReverse - startReverse;
            System.out.println("inString : " + inString);
            System.out.println("outString: " + outString);
            System.out.println("DONE reverse1 took " + timeDiff + " milliseconds");
            System.out.println("");

    		startReverse = System.currentTimeMillis();
    		outString = StringReverse.reverse2(inString);
    		endReverse = System.currentTimeMillis();
    		timeDiff = endReverse - startReverse;
            System.out.println("inString : " + inString);
            System.out.println("outString: " + outString);
    		System.out.println("DONE reverse2 took " + timeDiff + " milliseconds");
            System.out.println("");

    		startReverse = System.currentTimeMillis();
    		outString = StringReverse.reverse3(inString);
    		endReverse = System.currentTimeMillis();
    		timeDiff = endReverse - startReverse;
            System.out.println("inString : " + inString);
            System.out.println("outString: " + outString);
    		System.out.println("DONE reverse3 took " + timeDiff + " milliseconds");
            System.out.println("");

    		startReverse = System.currentTimeMillis();
    		outString = StringReverse.reverse4(inString);
    		endReverse = System.currentTimeMillis();
    		timeDiff = endReverse - startReverse;
            System.out.println("inString : " + inString);
            System.out.println("outString: " + outString);
    		System.out.println("DONE reverse4 took " + timeDiff + " milliseconds");
            System.out.println("");
            
    		startReverse = System.currentTimeMillis();
    		outString = StringReverse.reverse5(inString);
    		endReverse = System.currentTimeMillis();
    		timeDiff = endReverse - startReverse;
            System.out.println("inString : " + inString);
            System.out.println("outString: " + outString);
    		System.out.println("DONE reverse5 took " + timeDiff + " milliseconds");
            System.out.println("");
    		
    		startReverse = System.currentTimeMillis();
    		outString = StringReverse.reverse6(inString);
    		endReverse = System.currentTimeMillis();
    		timeDiff = endReverse - startReverse;
            System.out.println("inString : " + inString);
            System.out.println("outString: " + outString);
    		System.out.println("DONE reverse6 took " + timeDiff + " milliseconds");
            System.out.println("");

    		startReverse = System.currentTimeMillis();
    		outString = StringReverse.reverse7(inString);
    		endReverse = System.currentTimeMillis();
    		timeDiff = endReverse - startReverse;
            System.out.println("inString : " + inString);
            System.out.println("outString: " + outString);
    		System.out.println("DONE reverse7 took " + timeDiff + " milliseconds");
            System.out.println("");

            System.out.println("");

        	long endTime = System.currentTimeMillis();
        	
        	long duration = endTime - startTime;

            System.out.println("====       ===========");
            System.out.println("DONE ----- TestReverse.java took " + duration + " milliseconds");
            System.out.println("====       ===========");

    	}
    	catch (Exception e) {
    		e.printStackTrace();
    	}

	}  
	
}
