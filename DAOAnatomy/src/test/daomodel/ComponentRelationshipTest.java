package test.daomodel;

import daomodel.ComponentRelationship;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ComponentRelationshipTest {
	
	private ComponentRelationship componentrelationship1;
	private ComponentRelationship componentrelationship2;
	private ComponentRelationship componentrelationship3;
	private ComponentRelationship componentrelationship4;
	
    @Before
    public void setUp() throws Exception {
		
		
		componentrelationship1 = new ComponentRelationship( (long) 315711, "EMAPA:30627", (long) 23, (long) 26, "PART_OF", "EMAPA:29316" );
		componentrelationship2 = new ComponentRelationship( (long) 315711, "EMAPA:30627", (long) 23, (long) 26, "PART_OF", "EMAPA:29316" );
		componentrelationship3 = new ComponentRelationship( (long) 315712, "EMAPA:30627", (long) 23, (long) 26, "PART_OF", "EMAPA:29316" );
		componentrelationship4 = new ComponentRelationship( (long) 315712, "EMAPA:30628", (long) 23, (long) 26, "PART_OF", "EMAPA:29316" );
	}
	
    @After
    public void tearDown() throws Exception {
		
		
		componentrelationship1 = null;
		componentrelationship2 = null;
		componentrelationship3 = null;
		componentrelationship4 = null;
	}
	
    @Test
    public void testEquals() throws Exception {
		
		assertTrue(componentrelationship1.equals(componentrelationship2));
		assertFalse(componentrelationship1.equals(componentrelationship3));
		assertFalse(componentrelationship1.equals(componentrelationship4));
		
		assertFalse(componentrelationship2.equals(componentrelationship3));
		assertFalse(componentrelationship2.equals(componentrelationship4));

		assertTrue(componentrelationship3.equals(componentrelationship4));

		assertTrue(componentrelationship1.isSameAs(componentrelationship2));
		assertTrue(componentrelationship1.isSameAs(componentrelationship3));
		assertFalse(componentrelationship1.isSameAs(componentrelationship4));
		
		assertTrue(componentrelationship2.isSameAs(componentrelationship3));
		assertFalse(componentrelationship2.isSameAs(componentrelationship4));

		assertFalse(componentrelationship3.isSameAs(componentrelationship4));

	}

}
