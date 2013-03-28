package test.daomodel;

import daomodel.Synonym;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class SynonymTest {
	
	private Synonym synonym1;
	private Synonym synonym2;
	private Synonym synonym3;
	private Synonym synonym4;
	
    @Before
    public void setUp() throws Exception {
		
		
		synonym1 = new Synonym( (long) 739, (long) 731, "proximal endoderm" );
		synonym2 = new Synonym( (long) 739, (long) 731, "proximal endoderm" );
		synonym3 = new Synonym( (long) 740, (long) 731, "proximal endoderm" );
		synonym4 = new Synonym( (long) 740, (long) 731, "proximal endoderm1" );
	}
	
    @After
    public void tearDown() throws Exception {
		
		
		synonym1 = null;
		synonym2 = null;
		synonym3 = null;
		synonym4 = null;
	}
	
    @Test
    public void testEquals() throws Exception {
		
		assertTrue(synonym1.equals(synonym2));
		assertFalse(synonym1.equals(synonym3));
		assertFalse(synonym1.equals(synonym4));
		
		assertFalse(synonym2.equals(synonym3));
		assertFalse(synonym2.equals(synonym4));

		assertTrue(synonym3.equals(synonym4));

		assertTrue(synonym1.isSameAs(synonym2));
		assertTrue(synonym1.isSameAs(synonym3));
		assertFalse(synonym1.isSameAs(synonym4));
		
		assertTrue(synonym2.isSameAs(synonym3));
		assertFalse(synonym2.isSameAs(synonym4));

		assertFalse(synonym3.isSameAs(synonym4));

	}

}
