package testapp;

import utility.Wrapper;

import daolayer.DAOProperty;

public class TestPropertiesUtil {

	public static void main(String args[]){  

    	try {
    		long startTime = Wrapper.printPrologue("*", Wrapper.getExecutingClass());

            //ArrayList<daoProperty> daopropertylist = new ArrayList<daoProperty>();
            //daopropertylist = utility.PropertiesUtil.readFile("dao.properties");

            /*
			if ( !daopropertylist.isEmpty() ) {

            	Iterator<daoProperty> iteratorDaoPropertyList = daopropertylist.iterator();
            	
                while ( iteratorDaoPropertyList.hasNext() ) {
                	
                	daoProperty daoproperty = iteratorDaoPropertyList.next();
            
                	System.out.println(daoproperty.toStringFile());
                }
            }
            */
    		DAOProperty daoproperty = new DAOProperty();
    		
    		if ( daoproperty.setDAOProperty("/Users/mwicks/Desktop/dao.properties.input", "mouse008Caperdonich") ){

    			System.out.println("SUCCESS!!!");
    		}
    		else {
    			
    			System.out.println("FAIL!!!");
    		}


            /*
    		daoProperty daoproperty1 = new daoProperty("mouse011Localhost","JDBC", "true", "com.mysql.jdbc.Driver", "*", "banana", "/tmp/sqlout.log", "true", "jdbc:mysql://localhost:3306/mouse011", "root");
    		daoProperty daoproperty2 = new daoProperty("mouse999Localhost","JDBC", "true", "com.mysql.jdbc.Driver", "*", "satsuma", "/tmp/sqlout.log", "true", "jdbc:mysql://localhost:3306/mouse011", "root");

            //utility.PropertiesUtil.writeFile("/Users/mwicks/GitMahost/Anatomy/UsefulJava/config.properties", daoproperty);

            ArrayList<daoProperty> daopropertylist = new ArrayList<daoProperty>();
            daopropertylist.add(daoproperty1);
            daopropertylist.add(daoproperty2);
            
            utility.PropertiesUtil.writeFile("/Users/mwicks/GitMahost/Anatomy/UsefulJava/config.properties", daopropertylist);
            //utility.PropertiesUtil.printOne("config.properties");

            //utility.PropertiesUtil.writeProperties("config.properties", daoproperty);
            
    		daoProperty daoproperty = new daoProperty();
    		String majorKey = "";
    		
    		majorKey = "mouse999Localhost";
            daoproperty = utility.PropertiesUtil.readFile("config.properties", majorKey);
            
            if (daoproperty == null) {
            	
    			System.out.println("daoproperty NOT FOUND for majorKey = " + majorKey);
            }
            else {
            	
    			System.out.println("daoproperty FOUND for majorKey = " + majorKey);
    			//System.out.println(daoproperty.toStringFile());
                utility.PropertiesUtil.writeProperty("test.properties", daoproperty);
            }
                        
    		majorKey = "mouse011Localhost";
            daoproperty = utility.PropertiesUtil.readFile("config.properties", majorKey);
            
            if (daoproperty == null) {
            	
    			System.out.println("daoproperty NOT FOUND for majorKey = " + majorKey);
            }
            else {
            	
    			System.out.println("daoproperty FOUND for majorKey = " + majorKey);
    			//System.out.println(daoproperty.toStringFile());
                utility.PropertiesUtil.writeProperty("test.properties", daoproperty);
            }
            
    		majorKey = "mouse666Localhost";
            daoproperty = utility.PropertiesUtil.readFile("config.properties", majorKey);
            
            if (daoproperty == null) {
            	
    			System.out.println("daoproperty NOT FOUND for majorKey = " + majorKey);
            }
            else {
            	
    			System.out.println("daoproperty FOUND for majorKey = " + majorKey);
    			//System.out.println(daoproperty.toStringFile());
                utility.PropertiesUtil.writeProperty("test.properties", daoproperty);
            }

    		majorKey = "mouse011Localhost";
            daoproperty = utility.PropertiesUtil.findProperties("test.properties", majorKey);
            
            if (daoproperty == null) {
            	
    			System.out.println("test.properties: daoproperty NOT FOUND for majorKey = " + majorKey);
            }
            else {
            	
    			System.out.println("test.properties: daoproperty FOUND for majorKey = " + majorKey);
    			System.out.println(daoproperty.toStringFile());
            }

    		majorKey = "mouse666Localhost";
            daoproperty = utility.PropertiesUtil.findProperties("test.properties", majorKey);
            
            if (daoproperty == null) {
            	
    			System.out.println("test.properties: daoproperty NOT FOUND for majorKey = " + majorKey);
            }
            else {
            	
    			System.out.println("test.properties: daoproperty FOUND for majorKey = " + majorKey);
    			System.out.println(daoproperty.toStringFile());
            }

    		majorKey = "mouse999Localhost";
            daoproperty = utility.PropertiesUtil.findProperties("test.properties", majorKey);
            
            if (daoproperty == null) {
            	
    			System.out.println("test.properties: daoproperty NOT FOUND for majorKey = " + majorKey);
            }
            else {
            	
    			System.out.println("test.properties: daoproperty FOUND for majorKey = " + majorKey);
    			System.out.println(daoproperty.toStringFile());
            }
           */
            
            Wrapper.printEpilogue("*", Wrapper.getExecutingClass(), startTime);
    	}
    	catch (Exception e) {
    		
    		e.printStackTrace();
    	}
	}  
}
