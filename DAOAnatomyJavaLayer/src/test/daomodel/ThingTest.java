package test.daomodel;

import daomodel.Thing;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ThingTest {
	
	private Thing thing1;
	private Thing thing2;
	private Thing thing3;
	private Thing thing4;
	
    @Before
    public void setUp() throws Exception {
		
		
		thing1 = new Thing( (long) 100, "1999-04-08 15:11:27.0", (long) 2, "ANA_TIMED_NODE","EMAP:1461 IS EMAPA:16039 (embryo) AT TS16");
		thing2 = new Thing( (long) 100, "1999-04-08 15:11:27.0", (long) 2, "ANA_TIMED_NODE","EMAP:1461 IS EMAPA:16039 (embryo) AT TS16");
		thing3 = new Thing( (long) 101, "1999-04-08 15:11:27.0", (long) 2, "ANA_TIMED_NODE","EMAP:1461 IS EMAPA:16039 (embryo) AT TS16");
		thing4 = new Thing( (long) 101, "1999-04-08 15:11:27.0", (long) 2, "ANA_TIMED_NODE3","EMAP:1461 IS EMAPA:16039 (embryo) AT TS16");
	}
	
    @After
    public void tearDown() throws Exception {
		
		
		thing1 = null;
		thing2 = null;
		thing3 = null;
		thing4 = null;
	}
	
    @Test
    public void testEquals() throws Exception {
		
		assertTrue(thing1.equals(thing2));
		assertFalse(thing1.equals(thing3));
		assertFalse(thing1.equals(thing4));
		
		assertFalse(thing2.equals(thing3));
		assertFalse(thing2.equals(thing4));

		assertTrue(thing3.equals(thing4));

		assertTrue(thing1.isSameAs(thing2));
		assertTrue(thing1.isSameAs(thing3));
		assertFalse(thing1.isSameAs(thing4));
		
		assertTrue(thing2.isSameAs(thing3));
		assertFalse(thing2.isSameAs(thing4));

		assertFalse(thing3.isSameAs(thing4));

	}

}
