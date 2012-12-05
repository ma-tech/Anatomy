package testapp;

import utility.Wrapper;

public class TestMD5Hash {

	public static void main(String args[]){  

    	try {
    		long startTime = Wrapper.printPrologue("LOW", Wrapper.getExecutingClass());

            System.out.println("utility.MD5Hash.convert(\"banana\") " + utility.MD5Hash.convert("banana"));
            
            Wrapper.printEpilogue("LOW", Wrapper.getExecutingClass(), startTime);
    	}
    	catch (Exception e) {
    		
    		e.printStackTrace();
    	}
	}  
}
