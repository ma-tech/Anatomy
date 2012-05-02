package TestApp;

import java.text.SimpleDateFormat;

import java.util.Date;

import java.math.BigDecimal;

import Utility.ObjectConverter;

public class TestObjectConverter {

	public static void main(String args[]){  

		long startTime = System.currentTimeMillis();
    	
    	Date startDate = new Date();
    	String dateString = startDate.toString();
    	SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
    	
    	try {
    	   	Date parsed = format.parse(dateString);
            System.out.println("=========   -------------------");
            System.out.println("EXECUTING - TestObjectConverter.java on " + parsed.toString());
            System.out.println("=========   -------------------");
            System.out.println("");

            /*
             * 1.  integerToBoolean
             * 2.  booleanToInteger
             * 3.  doubleToBigDecimal
             * 4.  bigDecimalToDouble
             * 5.  integerToString
             * 6.  stringToInteger
             * 7.  booleanToString
             * 8.  stringToBoolean
             * 9.  longToString
             * 10. stringToLong
             * 11. longToInteger
             * 12. integerToLong
             */

            //Object o1 = new Integer(0);
            int o1_integer = 0;
    		boolean b1;
    		
            //Object o2 = Boolean.TRUE;
            boolean o2_boolean = true;
    		int i1;
            
    		//Object o3 = new Double(100.99);
            double o3_double = 100.99;
    		BigDecimal bd1 = new BigDecimal(0);
            
    		//Object o4 = new BigDecimal(123);
            BigDecimal o4_bigdecimal = new BigDecimal(123);
    		double d1;
            
    		//Object o5 = new Integer(456);
            int o5_integer = 456;
    		String s1;
            
    		//Object o6 = "789";
            String o6_string = "789";
    		int i2;
            
    		//Object o7 = Boolean.FALSE;
            boolean o7_boolean = false;
    		String s2;
            
    		//Object o8 = "0";
            String o8_string = "true";
    		boolean b2;
            
    		//Object o9 = new Long(2000);
            //Long o9_long = new Long(2000);
            long o9_long = 2000;
    		String s3;
            
    		//Object o10 = "3000";
            String o10_string = "3000";
    		long l1;
            
    		//Object o11 = new Long(2000);
            long o11_long = 2000;
    		int i3;
            
    		//Object o12 = new Integer(4000);
            int o12_integer = 4000;
    		long l2;
            
            // 1.  integerToBoolean
    		System.out.println("1.  integerToBoolean");
    		System.out.println("Integer " + o1_integer);
    		b1 = ObjectConverter.convert(o1_integer, Boolean.class);
    		System.out.println("Boolean " + b1);

            // 2. booleanToInteger
    		System.out.println("2. booleanToInteger");
    		System.out.println("Boolean " + o2_boolean);
    		i1 = ObjectConverter.convert(o2_boolean, Integer.class);
    		System.out.println("Integer " + i1);

            // 3.  doubleToBigDecimal
    		System.out.println("3.  doubleToBigDecimal");
    		System.out.println("Double " + o3_double);
    		bd1 = ObjectConverter.convert(o3_double, BigDecimal.class);
    		System.out.println("BigDecimal " + bd1);
    		
            // 4.  bigDecimalToDouble
    		System.out.println("4.  bigDecimalToDouble");
    		System.out.println("BigDecimal " + o4_bigdecimal);
    		d1 = ObjectConverter.convert(o4_bigdecimal, Double.class);
    		System.out.println("Double " + d1);
    		
    		// 5.  integerToString
    		System.out.println("5.  integerToString");
    		System.out.println("Integer " + o5_integer);
    		s1 = ObjectConverter.convert(o5_integer, String.class);
    		System.out.println("String " + s1);
    		
            // 6.  stringToInteger
    		System.out.println("6.  stringToInteger");
    		System.out.println("String " + o6_string);
    		i2 = ObjectConverter.convert(o6_string, Integer.class);
    		System.out.println("Integer " + i2);
    		
            // 7.  booleanToString
    		System.out.println("7.  booleanToString");
    		System.out.println("Boolean " + o7_boolean);
    		s2 = ObjectConverter.convert(o7_boolean, String.class);
    		System.out.println("String " + s2);
    		
            // 8.  stringToBoolean
    		System.out.println("8.  stringToBoolean");
    		System.out.println("String " + o8_string);
    		b2 = ObjectConverter.convert(o8_string, Boolean.class);
    		System.out.println("Boolean " + b2);
    		
            // 9.  longToString
    		System.out.println("9.  longToString");
    		System.out.println("Long " + o9_long);
    		s3 = ObjectConverter.convert(o9_long, String.class);
    		System.out.println("String " + s3);

    		// 10. stringToLong
    		System.out.println("10. stringToLong");
    		System.out.println("String " + o10_string);
    		l1 = ObjectConverter.convert(o10_string, Long.class);
    		System.out.println("Long " + l1);

    		// 11. longToInteger
    		System.out.println("11. longToInteger");
    		System.out.println("Long " + o11_long);
    		i3 = ObjectConverter.convert(o11_long, Integer.class);
    		System.out.println("Integer " + i3);

            // 12. integerToLong
    		System.out.println("12. integerToLong");
    		System.out.println("Integer " + o12_integer);
    		l2 = ObjectConverter.convert(o12_integer, Long.class);
    		System.out.println("Long " + l2);


        	long endTime = System.currentTimeMillis();
        	long duration = endTime - startTime;

            System.out.println("");
            System.out.println("====       ------------------");
            System.out.println("DONE ----- TestObjectConverter.java took " + duration + " milliseconds");
            System.out.println("====       ------------------");

    	}
    	catch (Exception e) {
    		e.printStackTrace();
    	}

	}  
	
}
