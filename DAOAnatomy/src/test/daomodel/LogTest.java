package test.daomodel;

import daomodel.Log;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class LogTest {
	
	private Log log1;
	private Log log2;
	private Log log3;
	private Log log4;
	
    @Before
    public void setUp() throws Exception {

    	log1 = new Log( (long) 1, (long) 10458, (long) 28765, "ANO_COMPONENT_NAME", "paramesonephric duct", "", "2013-03-28 12:07:19.0", "ANA_NODE" );
    	log2 = new Log( (long) 1, (long) 10458, (long) 28765, "ANO_COMPONENT_NAME", "paramesonephric duct", "", "2013-03-28 12:07:19.0", "ANA_NODE" );
    	log3 = new Log( (long) 2, (long) 10458, (long) 28765, "ANO_COMPONENT_NAME", "paramesonephric duct", "", "2013-03-28 12:07:19.0", "ANA_NODE" );
    	log4 = new Log( (long) 2, (long) 10458, (long) 28765, "ANO_COMPONENT_NAME2", "paramesonephric duct", "", "2013-03-28 12:07:19.0", "ANA_NODE" );
	}
	
    @After
    public void tearDown() throws Exception {
		
		log1 = null;
		log2 = null;
		log3 = null;
		log4 = null;
	}
	
    @Test
    public void testEquals() throws Exception {
		
		assertTrue(log1.equals(log2));
		assertFalse(log1.equals(log3));
		assertFalse(log1.equals(log4));
		
		assertFalse(log2.equals(log3));
		assertFalse(log2.equals(log4));

		assertTrue(log3.equals(log4));

		assertTrue(log1.isSameAs(log2));
		assertTrue(log1.isSameAs(log3));
		assertFalse(log1.isSameAs(log4));
		
		assertTrue(log2.isSameAs(log3));
		assertFalse(log2.isSameAs(log4));

		assertFalse(log3.isSameAs(log4));

	}

}
