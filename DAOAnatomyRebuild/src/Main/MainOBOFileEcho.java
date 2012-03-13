package Main;

import java.text.SimpleDateFormat;

import java.util.Date;

import App.RunOBOTest;

/*
 * Test harness for the OBO package.
 *  This requires the following preconditions:
 *   - A properties file 'obo.properties' in the classpath with the following entries:
 *     - file.url = <filepath>
 *     - file.debug = true/false
 *
 * http://balusc.blogspot.com/2008/07/dao-tutorial-data-layer.html
 */
public class MainOBOFileEcho {

    public static void main(String[] args) throws Exception {

    	long startTime = System.currentTimeMillis();
    	
    	Date startDate = new Date();
    	String dateString = startDate.toString();
    	SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
    	Date parsed = format.parse(dateString);

        System.out.println("=========   ===============");
        System.out.println("EXECUTING - MainOBOFileEcho.java on " + parsed.toString());
        System.out.println("=========   ===============");
        System.out.println("");

        RunOBOTest.run();

    	long endTime = System.currentTimeMillis();
    	long duration = endTime - startTime;

        System.out.println("");
        System.out.println("=========   ===============");
        System.out.println("DONE ------ MainOBOFileEcho.java took " + duration / 1000 + " seconds");
        System.out.println("=========   ===============");

    }

}
