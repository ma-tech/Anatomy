package test.daomodel;

import daomodel.ExtraTimedNode;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ExtraTimedNodeTest {
	
	private ExtraTimedNode extratimednode1;
	private ExtraTimedNode extratimednode2;
	private ExtraTimedNode extratimednode3;
	private ExtraTimedNode extratimednode4;
	
    @Before
    public void setUp() throws Exception {
		
		
		extratimednode1 = new ExtraTimedNode( (long) 34, (long) 33, (long) 6, "", "EMAP:25766", "EMAPA:25765", "TS01", (long) 0, "TS01", "TS28" );
		extratimednode2 = new ExtraTimedNode( (long) 34, (long) 33, (long) 6, "", "EMAP:25766", "EMAPA:25765", "TS01", (long) 0, "TS01", "TS28" );
		extratimednode3 = new ExtraTimedNode( (long) 35, (long) 33, (long) 6, "", "EMAP:25766", "EMAPA:25765", "TS01", (long) 0, "TS01", "TS28" );
		extratimednode4 = new ExtraTimedNode( (long) 35, (long) 33, (long) 6, "", "EMAP:25767", "EMAPA:25765", "TS01", (long) 0, "TS01", "TS28" );
	}
	
    @After
    public void tearDown() throws Exception {
		
		
		extratimednode1 = null;
		extratimednode2 = null;
		extratimednode3 = null;
		extratimednode4 = null;
	}
	
    @Test
    public void testEquals() throws Exception {
		
		assertTrue(extratimednode1.equals(extratimednode2));
		assertFalse(extratimednode1.equals(extratimednode3));
		assertFalse(extratimednode1.equals(extratimednode4));
		
		assertFalse(extratimednode2.equals(extratimednode3));
		assertFalse(extratimednode2.equals(extratimednode4));

		assertTrue(extratimednode3.equals(extratimednode4));

		assertTrue(extratimednode1.isSameAs(extratimednode2));
		assertTrue(extratimednode1.isSameAs(extratimednode3));
		assertFalse(extratimednode1.isSameAs(extratimednode4));
		
		assertTrue(extratimednode2.isSameAs(extratimednode3));
		assertFalse(extratimednode2.isSameAs(extratimednode4));

		assertFalse(extratimednode3.isSameAs(extratimednode4));

	}

}
