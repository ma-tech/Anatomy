package test.daomodel;

import daomodel.TimedNode;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TimedNodeTest {
	
	private TimedNode timednode1;
	private TimedNode timednode2;
	private TimedNode timednode3;
	private TimedNode timednode4;
	
    @Before
    public void setUp() throws Exception {
		
		
		timednode1 = new TimedNode( (long) 34, (long) 33, (long) 6, "", "EMAP:25766", "EMAP:0025766" );
		timednode2 = new TimedNode( (long) 34, (long) 33, (long) 6, "", "EMAP:25766", "EMAP:0025766" );
		timednode3 = new TimedNode( (long) 35, (long) 33, (long) 6, "", "EMAP:25766", "EMAP:0025766" );
		timednode4 = new TimedNode( (long) 35, (long) 33, (long) 6, "", "EMAP:25767", "EMAP:0025766" );
	}
	
    @After
    public void tearDown() throws Exception {
		
		
		timednode1 = null;
		timednode2 = null;
		timednode3 = null;
		timednode4 = null;
	}
	
    @Test
    public void testEquals() throws Exception {
		
		assertTrue(timednode1.equals(timednode2));
		assertFalse(timednode1.equals(timednode3));
		assertFalse(timednode1.equals(timednode4));
		
		assertFalse(timednode2.equals(timednode3));
		assertFalse(timednode2.equals(timednode4));

		assertTrue(timednode3.equals(timednode4));

		assertTrue(timednode1.isSameAs(timednode2));
		assertTrue(timednode1.isSameAs(timednode3));
		assertFalse(timednode1.isSameAs(timednode4));
		
		assertTrue(timednode2.isSameAs(timednode3));
		assertFalse(timednode2.isSameAs(timednode4));

		assertFalse(timednode3.isSameAs(timednode4));

	}

}
