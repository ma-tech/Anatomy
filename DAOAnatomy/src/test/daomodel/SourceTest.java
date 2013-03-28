package test.daomodel;

import daomodel.Source;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class SourceTest {
	
	private Source source1;
	private Source source2;
	private Source source3;
	private Source source4;
	
    @Before
    public void setUp() throws Exception {
		
		
		source1 = new Source( (long) 4, "The Anatomical Basis of Mouse Development", "Kaufman, MH, and Bard, JBL", "book", (long) 1999 );
		source2 = new Source( (long) 4, "The Anatomical Basis of Mouse Development", "Kaufman, MH, and Bard, JBL", "book", (long) 1999 );
		source3 = new Source( (long) 5, "The Anatomical Basis of Mouse Development", "Kaufman, MH, and Bard, JBL", "book", (long) 1999 );
		source4 = new Source( (long) 5, "The Anatomical Basis of Mouse Development2", "Kaufman, MH, and Bard, JBL", "book", (long) 1999 );
	}
	
    @After
    public void tearDown() throws Exception {
		
		
		source1 = null;
		source2 = null;
		source3 = null;
		source4 = null;
	}
	
    @Test
    public void testEquals() throws Exception {
		
		assertTrue(source1.equals(source2));
		assertFalse(source1.equals(source3));
		assertFalse(source1.equals(source4));
		
		assertFalse(source2.equals(source3));
		assertFalse(source2.equals(source4));

		assertTrue(source3.equals(source4));

		assertTrue(source1.isSameAs(source2));
		assertTrue(source1.isSameAs(source3));
		assertFalse(source1.isSameAs(source4));
		
		assertTrue(source2.isSameAs(source3));
		assertFalse(source2.isSameAs(source4));

		assertFalse(source3.isSameAs(source4));

	}

}
