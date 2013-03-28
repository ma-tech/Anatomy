package test.daomodel;

import daomodel.OBOFile;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class OBOFileTest {
	
	private OBOFile obofile1;
	private OBOFile obofile2;
	private OBOFile obofile3;
	private OBOFile obofile4;
	
    @Before
    public void setUp() throws Exception {
		
		
		obofile1 = new OBOFile( (long) 1, "ADD.obo", "","UTF-8", (long) 1200170, "2013-03-25 15:08:02.0", "PASSED VALIDATION", "SYSTEM", "summaryReport_2012-12-07.txt", "","UTF-8", (long) 1774, "2013-03-25 15:08:02.0", "summaryReport_2012-12-07.pdf", "","UTF-8", (long) 4506, "2013-03-25 15:08:02.0" );
		obofile2 = new OBOFile( (long) 1, "ADD.obo", "","UTF-8", (long) 1200170, "2013-03-25 15:08:02.0", "PASSED VALIDATION", "SYSTEM", "summaryReport_2012-12-07.txt", "","UTF-8", (long) 1774, "2013-03-25 15:08:02.0", "summaryReport_2012-12-07.pdf", "","UTF-8", (long) 4506, "2013-03-25 15:08:02.0" );
		obofile3 = new OBOFile( (long) 2, "ADD.obo", "","UTF-8", (long) 1200170, "2013-03-25 15:08:02.0", "PASSED VALIDATION", "SYSTEM", "summaryReport_2012-12-07.txt", "","UTF-8", (long) 1774, "2013-03-25 15:08:02.0", "summaryReport_2012-12-07.pdf", "","UTF-8", (long) 4506, "2013-03-25 15:08:02.0" );
		obofile4 = new OBOFile( (long) 2, "ADD2.obo", "","UTF-8", (long) 1200170, "2013-03-25 15:08:02.0", "PASSED VALIDATION", "SYSTEM", "summaryReport_2012-12-07.txt", "","UTF-8", (long) 1774, "2013-03-25 15:08:02.0", "summaryReport_2012-12-07.pdf", "","UTF-8", (long) 4506, "2013-03-25 15:08:02.0" );
	}
	
    @After
    public void tearDown() throws Exception {
		
		
		obofile1 = null;
		obofile2 = null;
		obofile3 = null;
		obofile4 = null;
	}
	
    @Test
    public void testEquals() throws Exception {
		
		assertTrue(obofile1.equals(obofile2));
		assertFalse(obofile1.equals(obofile3));
		assertFalse(obofile1.equals(obofile4));
		
		assertFalse(obofile2.equals(obofile3));
		assertFalse(obofile2.equals(obofile4));

		assertTrue(obofile3.equals(obofile4));

		assertTrue(obofile1.isSameAs(obofile2));
		assertTrue(obofile1.isSameAs(obofile3));
		assertFalse(obofile1.isSameAs(obofile4));
		
		assertTrue(obofile2.isSameAs(obofile3));
		assertFalse(obofile2.isSameAs(obofile4));

		assertFalse(obofile3.isSameAs(obofile4));

	}

}
