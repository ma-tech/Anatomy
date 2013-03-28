package test.daomodel;

import daomodel.Version;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class VersionTest {
	
	private Version version1;
	private Version version2;
	private Version version3;
	private Version version4;
	
    @Before
    public void setUp() throws Exception {
		
		
		version1 = new Version( (long) 33977, (long) 9, "2012-07-12 14:22:55.0", "DB2OBO Update - Editing the ontology" );
		version2 = new Version( (long) 33977, (long) 9, "2012-07-12 14:22:55.0", "DB2OBO Update - Editing the ontology" );
		version3 = new Version( (long) 33978, (long) 9, "2012-07-12 14:22:55.0", "DB2OBO Update - Editing the ontology" );
		version4 = new Version( (long) 33978, (long) 9, "2012-07-12 14:22:55.0", "DB2OBO Update - Editing the ontology2" );
	}
	
    @After
    public void tearDown() throws Exception {
		
		
		version1 = null;
		version2 = null;
		version3 = null;
		version4 = null;
	}
	
    @Test
    public void testEquals() throws Exception {
		
		assertTrue(version1.equals(version2));
		assertFalse(version1.equals(version3));
		assertFalse(version1.equals(version4));
		
		assertFalse(version2.equals(version3));
		assertFalse(version2.equals(version4));

		assertTrue(version3.equals(version4));

		assertTrue(version1.isSameAs(version2));
		assertTrue(version1.isSameAs(version3));
		assertFalse(version1.isSameAs(version4));
		
		assertTrue(version2.isSameAs(version3));
		assertFalse(version2.isSameAs(version4));

		assertFalse(version3.isSameAs(version4));

	}

}
