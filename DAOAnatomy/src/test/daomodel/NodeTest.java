package test.daomodel;

import daomodel.Node;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class NodeTest {
	
	private Node node1;
	private Node node2;
	private Node node3;
	private Node node4;
	
    @Before
    public void setUp() throws Exception {
		
		
		node1 = new Node( (long) 33, "mouse", "mouse", true, false, "EMAPA:25765", "", "EMAPA:0025765" );
		node2 = new Node( (long) 33, "mouse", "mouse", true, false, "EMAPA:25765", "", "EMAPA:0025765" );
		node3 = new Node( (long) 34, "mouse", "mouse", true, false, "EMAPA:25765", "", "EMAPA:0025765" );
		node4 = new Node( (long) 34, "mouse", "mouse", true, false, "EMAPA:25766", "", "EMAPA:0025765" );
	}
	
    @After
    public void tearDown() throws Exception {
		
		
		node1 = null;
		node2 = null;
		node3 = null;
		node4 = null;
	}
	
    @Test
    public void testEquals() throws Exception {
		
		assertTrue(node1.equals(node2));
		assertFalse(node1.equals(node3));
		assertFalse(node1.equals(node4));
		
		assertFalse(node2.equals(node3));
		assertFalse(node2.equals(node4));

		assertTrue(node3.equals(node4));

		assertTrue(node1.isSameAs(node2));
		assertTrue(node1.isSameAs(node3));
		assertFalse(node1.isSameAs(node4));
		
		assertTrue(node2.isSameAs(node3));
		assertFalse(node2.isSameAs(node4));

		assertFalse(node3.isSameAs(node4));

	}

}
