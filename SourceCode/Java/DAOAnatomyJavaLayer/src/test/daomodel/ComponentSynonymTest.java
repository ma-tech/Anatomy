package test.daomodel;

import daomodel.ComponentSynonym;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ComponentSynonymTest {
	
	private ComponentSynonym componentsynonym1;
	private ComponentSynonym componentsynonym2;
	private ComponentSynonym componentsynonym3;
	private ComponentSynonym componentsynonym4;
	
    @Before
    public void setUp() throws Exception {
		
		
		componentsynonym1 = new ComponentSynonym( (long) 18154, "EMAPA:27678", "epithelial vesicle" );
		componentsynonym2 = new ComponentSynonym( (long) 18154, "EMAPA:27678", "epithelial vesicle" );
		componentsynonym3 = new ComponentSynonym( (long) 18155, "EMAPA:27678", "epithelial vesicle" );
		componentsynonym4 = new ComponentSynonym( (long) 18155, "EMAPA:27678", "epithelial vesicle2" );
	}
	
    @After
    public void tearDown() throws Exception {
		
		
		componentsynonym1 = null;
		componentsynonym2 = null;
		componentsynonym3 = null;
		componentsynonym4 = null;
	}
	
    @Test
    public void testEquals() throws Exception {
		
		assertTrue(componentsynonym1.equals(componentsynonym2));
		assertFalse(componentsynonym1.equals(componentsynonym3));
		assertFalse(componentsynonym1.equals(componentsynonym4));
		
		assertFalse(componentsynonym2.equals(componentsynonym3));
		assertFalse(componentsynonym2.equals(componentsynonym4));

		assertTrue(componentsynonym3.equals(componentsynonym4));

		assertTrue(componentsynonym1.isSameAs(componentsynonym2));
		assertTrue(componentsynonym1.isSameAs(componentsynonym3));
		assertFalse(componentsynonym1.isSameAs(componentsynonym4));
		
		assertTrue(componentsynonym2.isSameAs(componentsynonym3));
		assertFalse(componentsynonym2.isSameAs(componentsynonym4));

		assertFalse(componentsynonym3.isSameAs(componentsynonym4));

	}

}
