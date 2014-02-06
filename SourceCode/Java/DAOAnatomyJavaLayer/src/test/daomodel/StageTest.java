package test.daomodel;

import daomodel.Stage;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class StageTest {
	
	private Stage stage1;
	private Stage stage2;
	private Stage stage3;
	private Stage stage4;
	
    @Before
    public void setUp() throws Exception {
		
		
		stage1 = new Stage( (long) 7, "mouse", "TS02", (long) 1, "", "E1-2.5", "" );
		stage2 = new Stage( (long) 7, "mouse", "TS02", (long) 1, "", "E1-2.5", "" );
		stage3 = new Stage( (long) 8, "mouse", "TS02", (long) 1, "", "E1-2.5", "" );
		stage4 = new Stage( (long) 8, "mouse", "TS02a", (long) 1, "", "E1-2.5", "" );
	}
	
    @After
    public void tearDown() throws Exception {
		
		
		stage1 = null;
		stage2 = null;
		stage3 = null;
		stage4 = null;
	}
	
    @Test
    public void testEquals() throws Exception {
		
		assertTrue(stage1.equals(stage2));
		assertFalse(stage1.equals(stage3));
		assertFalse(stage1.equals(stage4));
		
		assertFalse(stage2.equals(stage3));
		assertFalse(stage2.equals(stage4));

		assertTrue(stage3.equals(stage4));

		assertTrue(stage1.isSameAs(stage2));
		assertTrue(stage1.isSameAs(stage3));
		assertFalse(stage1.isSameAs(stage4));
		
		assertTrue(stage2.isSameAs(stage3));
		assertFalse(stage2.isSameAs(stage4));

		assertFalse(stage3.isSameAs(stage4));

	}

}
