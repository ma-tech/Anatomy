package test.daomodel;

import daomodel.ComponentOrder;

import org.junit.After;
import org.junit.Before;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

public class ComponentOrderTest {
	
	private ComponentOrder componentorder1;
	private ComponentOrder componentorder2;
	private ComponentOrder componentorder3;
	private ComponentOrder componentorder4;
	
    @Before
    public void setUp() throws Exception {
		
		componentorder1 = new ComponentOrder( (long) 37429, "EMAPA:30627", "EMAPA:29316", "PART_OF", (long) 1, (long) 1 );
		componentorder2 = new ComponentOrder( (long) 37429, "EMAPA:30627", "EMAPA:29316", "PART_OF", (long) 1, (long) 1 );
		componentorder3 = new ComponentOrder( (long) 37430, "EMAPA:30627", "EMAPA:29316", "PART_OF", (long) 1, (long) 1 );
		componentorder4 = new ComponentOrder( (long) 37430, "EMAPA:30628", "EMAPA:29316", "PART_OF", (long) 1, (long) 1 );
	}
	
    @After
    public void tearDown() throws Exception {
		
		componentorder1 = null;
		componentorder2 = null;
		componentorder3 = null;
		componentorder4 = null;
	}
	
    @Test
    public void testEquals() throws Exception {
		
		assertTrue(componentorder1.equals(componentorder2));
		assertFalse(componentorder1.equals(componentorder3));
		assertFalse(componentorder1.equals(componentorder4));
		
		assertFalse(componentorder2.equals(componentorder3));
		assertFalse(componentorder2.equals(componentorder4));

		assertTrue(componentorder3.equals(componentorder4));

		assertTrue(componentorder1.isSameAs(componentorder2));
		assertTrue(componentorder1.isSameAs(componentorder3));
		assertFalse(componentorder1.isSameAs(componentorder4));
		
		assertTrue(componentorder2.isSameAs(componentorder3));
		assertFalse(componentorder2.isSameAs(componentorder4));

		assertFalse(componentorder3.isSameAs(componentorder4));

	}

}
