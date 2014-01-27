package test.daomodel;

import daomodel.Component;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ComponentTest {
	
	private Component component1;
	private Component component2;
	private Component component3;
	private Component component4;
	
    @Before
    public void setUp() throws Exception {
		
		
		component1 = new Component( (long) 286970, "primitive ventricle cardiac muscle", "EMAPA:16353", "TBD", "TBD", "abstract_anatomy", "", true, "TS13", "TS18", false, "", "UNCHECKED" );
		component2 = new Component( (long) 286970, "primitive ventricle cardiac muscle", "EMAPA:16353", "TBD", "TBD", "abstract_anatomy", "", true, "TS13", "TS18", false, "", "UNCHECKED" );
		component3 = new Component( (long) 286971, "primitive ventricle cardiac muscle", "EMAPA:16353", "TBD", "TBD", "abstract_anatomy", "", true, "TS13", "TS18", false, "", "UNCHECKED" );
		component4 = new Component( (long) 286971, "primitive ventricle cardiac muscle2", "EMAPA:16353", "TBD", "TBD", "abstract_anatomy", "", true, "TS13", "TS18", false, "", "UNCHECKED" );
	}
	
    @After
    public void tearDown() throws Exception {
		
		
		component1 = null;
		component2 = null;
		component3 = null;
		component4 = null;
	}
	
    @Test
    public void testEquals() throws Exception {
		
		assertTrue(component1.equals(component2));
		assertFalse(component1.equals(component3));
		assertFalse(component1.equals(component4));
		
		assertFalse(component2.equals(component3));
		assertFalse(component2.equals(component4));

		assertTrue(component3.equals(component4));

		assertTrue(component1.isSameAs(component2));
		assertTrue(component1.isSameAs(component3));
		assertFalse(component1.isSameAs(component4));
		
		assertTrue(component2.isSameAs(component3));
		assertFalse(component2.isSameAs(component4));

		assertFalse(component3.isSameAs(component4));

	}

}
