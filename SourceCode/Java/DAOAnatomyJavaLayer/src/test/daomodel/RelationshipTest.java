package test.daomodel;

import daomodel.Relationship;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class RelationshipTest {
	
	private Relationship relationship1;
	private Relationship relationship2;
	private Relationship relationship3;
	private Relationship relationship4;
	
    @Before
    public void setUp() throws Exception {
		
		
		relationship1 = new Relationship( (long) 83, "part-of", (long) 81, (long) 33 );
		relationship2 = new Relationship( (long) 83, "part-of", (long) 81, (long) 33 );
		relationship3 = new Relationship( (long) 84, "part-of", (long) 81, (long) 33 );
		relationship4 = new Relationship( (long) 84, "part-of", (long) 82, (long) 33 );
	}
	
    @After
    public void tearDown() throws Exception {
		
		
		relationship1 = null;
		relationship2 = null;
		relationship3 = null;
		relationship4 = null;
	}
	
    @Test
    public void testEquals() throws Exception {
		
		assertTrue(relationship1.equals(relationship2));
		assertFalse(relationship1.equals(relationship3));
		assertFalse(relationship1.equals(relationship4));
		
		assertFalse(relationship2.equals(relationship3));
		assertFalse(relationship2.equals(relationship4));

		assertTrue(relationship3.equals(relationship4));

		assertTrue(relationship1.isSameAs(relationship2));
		assertTrue(relationship1.isSameAs(relationship3));
		assertFalse(relationship1.isSameAs(relationship4));
		
		assertTrue(relationship2.isSameAs(relationship3));
		assertFalse(relationship2.isSameAs(relationship4));

		assertFalse(relationship3.isSameAs(relationship4));

	}

}
