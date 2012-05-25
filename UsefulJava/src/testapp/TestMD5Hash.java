package testapp;

public class TestMD5Hash {

	public static void main(String args[]){  

    	try {
    		long startTime = System.currentTimeMillis();
        	
            System.out.println("=========   ===========");
            System.out.println("EXECUTING - TestMD5Hash.java on " + utility.MySQLDateTime.now());
            System.out.println("=========   ===========");
            System.out.println("");

            System.out.println("utility.MD5Hash.MD5Hash(\"banana\") " + utility.MD5Hash.MD5Hash("banana"));
            System.out.println("");

        	long endTime = System.currentTimeMillis();
        	
        	long duration = endTime - startTime;

            System.out.println("====       ===========");
            System.out.println("DONE ----- TestMD5Hash.java took " + duration + " milliseconds");
            System.out.println("====       ===========");

    	}
    	catch (Exception e) {
    		e.printStackTrace();
    	}

	}  
	
}
