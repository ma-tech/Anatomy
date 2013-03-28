package test.daomodel;

import daomodel.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class UserTest {
	
	private User user1;
	private User user2;
	private User user3;
	private User user4;
	
    @Before
    public void setUp() throws Exception {
		
		
		user1 = new User( (long) 1, "mwicks", "72b302bf297a228a75730123efef7c41", "mike.wicks@gmail.com", "HGU" );
		user2 = new User( (long) 1, "mwicks", "72b302bf297a228a75730123efef7c41", "mike.wicks@gmail.com", "HGU" );
		user3 = new User( (long) 2, "mwicks", "72b302bf297a228a75730123efef7c41", "mike.wicks@gmail.com", "HGU" );
		user4 = new User( (long) 2, "mwicks2", "72b302bf297a228a75730123efef7c41", "mike.wicks@gmail.com", "HGU" );
	}
	
    @After
    public void tearDown() throws Exception {
		
		
		user1 = null;
		user2 = null;
		user3 = null;
		user4 = null;
	}
	
    @Test
    public void testEquals() throws Exception {
		
		assertTrue(user1.equals(user2));
		assertFalse(user1.equals(user3));
		assertFalse(user1.equals(user4));
		
		assertFalse(user2.equals(user3));
		assertFalse(user2.equals(user4));

		assertTrue(user3.equals(user4));

		assertTrue(user1.isSameAs(user2));
		assertTrue(user1.isSameAs(user3));
		assertFalse(user1.isSameAs(user4));
		
		assertTrue(user2.isSameAs(user3));
		assertFalse(user2.isSameAs(user4));

		assertFalse(user3.isSameAs(user4));

	}

}
