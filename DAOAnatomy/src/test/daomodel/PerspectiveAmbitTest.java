package test.daomodel;

import daomodel.PerspectiveAmbit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class PerspectiveAmbitTest {
	
	private PerspectiveAmbit perspectiveambit1;
	private PerspectiveAmbit perspectiveambit2;
	private PerspectiveAmbit perspectiveambit3;
	private PerspectiveAmbit perspectiveambit4;
	
    @Before
    public void setUp() throws Exception {
		
		
		perspectiveambit1 = new PerspectiveAmbit( (long) 31616, "Urogenital", (long) 26962, true, false, "" );
		perspectiveambit2 = new PerspectiveAmbit( (long) 31616, "Urogenital", (long) 26962, true, false, "" );
		perspectiveambit3 = new PerspectiveAmbit( (long) 31617, "Urogenital", (long) 26962, true, false, "" );
		perspectiveambit4 = new PerspectiveAmbit( (long) 31617, "Urogenital2", (long) 26962, true, false, "" );
		
		/*
		if ( !perspectiveambit1.isSameAs(perspectiveambit2) ) {
			
			if (!perspectiveambit1.getPerspectiveFK().equals(perspectiveambit2.getPerspectiveFK())) {
				
				System.out.println("PerspectiveFK Different");
			}
			else {
				
				System.out.println("PerspectiveFK Match");
			}
				
			if (perspectiveambit1.getNodeFK().equals(perspectiveambit2.getNodeFK()) ) {
				
				System.out.println("NodeFK Different");
			}
			else {
				
				System.out.println("NodeFK Match");
			}

			if (perspectiveambit1.getStart() != perspectiveambit2.getStart()) {
				
				System.out.println("Start Different");
			}
			else {
				
				System.out.println("Start Match");
			}

			if (perspectiveambit1.getStop() != perspectiveambit2.getStop()) {
				
				System.out.println("Stop Different");
			}
			else {
				
				System.out.println("Stop Match");
			}

			if (!perspectiveambit1.getComments().equals(perspectiveambit2.getComments())) {
				
				System.out.println("Comments Different");
			}
			else {
				
				System.out.println("Comments Match");
			}
        }
		
		System.out.println("perspectiveambit1.toString()" + perspectiveambit1.toString());
		System.out.println("perspectiveambit2.toString()" + perspectiveambit2.toString());
		System.out.println("perspectiveambit3.toString()" + perspectiveambit3.toString());
		System.out.println("perspectiveambit4.toString()" + perspectiveambit4.toString());
		*/
	}
	
    @After
    public void tearDown() throws Exception {
		
		
		perspectiveambit1 = null;
		perspectiveambit2 = null;
		perspectiveambit3 = null;
		perspectiveambit4 = null;
	}
	
    @Test
    public void testEquals() throws Exception {
		
		assertTrue(perspectiveambit1.equals(perspectiveambit2));
		assertFalse(perspectiveambit1.equals(perspectiveambit3));
		assertFalse(perspectiveambit1.equals(perspectiveambit4));
		
		assertFalse(perspectiveambit2.equals(perspectiveambit3));
		assertFalse(perspectiveambit2.equals(perspectiveambit4));

		assertTrue(perspectiveambit3.equals(perspectiveambit4));

		assertTrue(perspectiveambit1.isSameAs(perspectiveambit2));
		assertTrue(perspectiveambit1.isSameAs(perspectiveambit3));
		assertFalse(perspectiveambit1.isSameAs(perspectiveambit4));
		
		assertTrue(perspectiveambit2.isSameAs(perspectiveambit3));
		assertFalse(perspectiveambit2.isSameAs(perspectiveambit4));

		assertFalse(perspectiveambit3.isSameAs(perspectiveambit4));

	}

}
