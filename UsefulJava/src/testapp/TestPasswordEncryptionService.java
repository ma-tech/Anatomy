package testapp;

import java.text.SimpleDateFormat;

import java.util.Date;

import utility.PasswordEncryptionService;

public class TestPasswordEncryptionService {

	public static void main(String args[]){  

		long startTime = System.currentTimeMillis();
    	
    	Date startDate = new Date();
    	String dateString = startDate.toString();
    	SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
    	
    	try {
    	   	Date parsed = format.parse(dateString);
            System.out.println("=========   ------------");
            System.out.println("EXECUTING - TestPassword.java on " + parsed.toString());
            System.out.println("=========   ------------");
            System.out.println("");

            // generate Salt
            byte[] salt = new byte[8]; 
            salt = PasswordEncryptionService.generateSalt();
            System.out.println("Generated Salt = " + salt.toString());
            System.out.println("");

            // generate encrypted password1
            String attemptedPassword1 = "banana";
            byte[] encryptedPassword1 = new byte[8];
            encryptedPassword1 = PasswordEncryptionService.getEncryptedPassword(attemptedPassword1, salt);

            // generate encrypted password2
            String attemptedPassword2 = "orange";
            byte[] encryptedPassword2 = new byte[8];
            encryptedPassword2 = PasswordEncryptionService.getEncryptedPassword(attemptedPassword2, salt);
            
            if ( PasswordEncryptionService.authenticate(attemptedPassword1, encryptedPassword1, salt)) {
            	System.out.println("SUCCESS: Password " + attemptedPassword1 + " MATCHES encrypted password       " + encryptedPassword1);
            }
            else {
            	System.out.println("FAILURE: Password " + attemptedPassword1 + " DID NOT MATCH encrypted password " + encryptedPassword1);
            }
            
            if ( PasswordEncryptionService.authenticate(attemptedPassword2, encryptedPassword1, salt)) {
            	System.out.println("SUCCESS: Password " + attemptedPassword2 + " MATCHES encrypted password       " + encryptedPassword1);
            }
            else {
            	System.out.println("FAILURE: Password " + attemptedPassword2 + " DID NOT MATCH encrypted password " + encryptedPassword1);
            }
            
            if ( PasswordEncryptionService.authenticate(attemptedPassword2, encryptedPassword2, salt)) {
            	System.out.println("SUCCESS: Password " + attemptedPassword2 + " MATCHES encrypted password       " + encryptedPassword2);
            }
            else {
            	System.out.println("FAILURE: Password " + attemptedPassword2 + " DID NOT MATCH encrypted password " + encryptedPassword2);
            }
            
            if ( PasswordEncryptionService.authenticate(attemptedPassword1, encryptedPassword2, salt)) {
            	System.out.println("SUCCESS: Password " + attemptedPassword1 + " MATCHES encrypted password       " + encryptedPassword2);
            }
            else {
            	System.out.println("FAILURE: Password " + attemptedPassword1 + " DID NOT MATCH encrypted password " + encryptedPassword2);
            }
            
            
            long endTime = System.currentTimeMillis();
        	long duration = endTime - startTime;

            System.out.println("");
            System.out.println("=========   ------------");
            System.out.println("DONE ----- TestPassword.java took " + duration + " milliseconds");
            System.out.println("=========   ------------");

    	}
    	catch (Exception e) {
    		e.printStackTrace();
    	}

	}  
	
}
