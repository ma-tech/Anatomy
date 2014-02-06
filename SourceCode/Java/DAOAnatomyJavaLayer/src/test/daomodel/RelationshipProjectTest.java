package test.daomodel;

import daomodel.RelationshipProject;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class RelationshipProjectTest {
	
	private RelationshipProject relationshipproject1;
	private RelationshipProject relationshipproject2;
	private RelationshipProject relationshipproject3;
	private RelationshipProject relationshipproject4;
	
    @Before
    public void setUp() throws Exception {
		
		
		relationshipproject1 = new RelationshipProject( (long) 12071, (long) 34701, "EMAP", (long) 0 );
		relationshipproject2 = new RelationshipProject( (long) 12071, (long) 34701, "EMAP", (long) 0 );
		relationshipproject3 = new RelationshipProject( (long) 12072, (long) 34701, "EMAP", (long) 0 );
		relationshipproject4 = new RelationshipProject( (long) 12072, (long) 34701, "EMAP2", (long) 0 );
	}
	
    @After
    public void tearDown() throws Exception {
		
		
		relationshipproject1 = null;
		relationshipproject2 = null;
		relationshipproject3 = null;
		relationshipproject4 = null;
	}
	
    @Test
    public void testEquals() throws Exception {
		
		assertTrue(relationshipproject1.equals(relationshipproject2));
		assertFalse(relationshipproject1.equals(relationshipproject3));
		assertFalse(relationshipproject1.equals(relationshipproject4));
		
		assertFalse(relationshipproject2.equals(relationshipproject3));
		assertFalse(relationshipproject2.equals(relationshipproject4));

		assertTrue(relationshipproject3.equals(relationshipproject4));

		assertTrue(relationshipproject1.isSameAs(relationshipproject2));
		assertTrue(relationshipproject1.isSameAs(relationshipproject3));
		assertFalse(relationshipproject1.isSameAs(relationshipproject4));
		
		assertTrue(relationshipproject2.isSameAs(relationshipproject3));
		assertFalse(relationshipproject2.isSameAs(relationshipproject4));

		assertFalse(relationshipproject3.isSameAs(relationshipproject4));

	}

}
