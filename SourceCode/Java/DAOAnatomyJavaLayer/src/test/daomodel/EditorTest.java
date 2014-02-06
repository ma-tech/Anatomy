package test.daomodel;

import daomodel.Editor;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class EditorTest {
	
	private Editor editor1;
	private Editor editor2;
	private Editor editor3;
	private Editor editor4;
	
    @Before
    public void setUp() throws Exception {
		
		
		editor1 = new Editor( (long) 1, "Jonathan Bard" );
		editor2 = new Editor( (long) 1, "Jonathan Bard" );
		editor3 = new Editor( (long) 2, "Jonathan Bard" );
		editor4 = new Editor( (long) 2, "Jonathan Bard2" );
	}
	
    @After
    public void tearDown() throws Exception {
		
		
		editor1 = null;
		editor2 = null;
		editor3 = null;
		editor4 = null;
	}
	
    @Test
    public void testEquals() throws Exception {
		
		assertTrue(editor1.equals(editor2));
		assertFalse(editor1.equals(editor3));
		assertFalse(editor1.equals(editor4));
		
		assertFalse(editor2.equals(editor3));
		assertFalse(editor2.equals(editor4));

		assertTrue(editor3.equals(editor4));

		assertTrue(editor1.isSameAs(editor2));
		assertTrue(editor1.isSameAs(editor3));
		assertFalse(editor1.isSameAs(editor4));
		
		assertTrue(editor2.isSameAs(editor3));
		assertFalse(editor2.isSameAs(editor4));

		assertFalse(editor3.isSameAs(editor4));

	}

}
