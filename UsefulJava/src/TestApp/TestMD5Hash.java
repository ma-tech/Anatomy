package TestApp;

public class TestMD5Hash {

	public static void main(String args[]){  

    	try {
    		long startTime = System.currentTimeMillis();
        	
            System.out.println("=========   ===========");
            System.out.println("EXECUTING - TestMD5Hash.java on " + Utility.MySQLDateTime.now());
            System.out.println("=========   ===========");
            System.out.println("");

            String MD5Hash = Utility.MD5Hash.MD5Hash("banana");
            System.out.println("Utility.MD5Hash.MD5Hash(\"banana\") " + Utility.MD5Hash.MD5Hash("banana"));
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
