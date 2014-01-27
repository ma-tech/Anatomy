package test.daojdbc;

import daointerface.ComponentDAO;

import daolayer.DAOFactory;
import daolayer.DAOException;

import daomodel.Component;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import org.junit.Test;

import static org.junit.Assert.*;


public class ComponentJDBCTest {
	
	private static Component component1;
	private static Component component2;
	private static Component component3;
	private static Component component4;

	private static DAOFactory daofactory;
	
	private static ComponentDAO componentDAO;
	
	@BeforeClass
    public static void testSetUp() throws Exception {
		
		componentDAO = daofactory.getInstance("test011Localhost").getDAOImpl(ComponentDAO.class);
		
		component1 = new Component( (long) 286920, "epithelial layer of rest of oviduct", "EMAPA:29907", "TBD", "TBD", "abstract_anatomy", "", false, "TS28", "TS28", false, "", "UNCHECKED" );
		component2 = new Component( (long) 286921, "scrotal fold mesenchyme of male", "EMAPA:30624", "TBD", "TBD", "abstract_anatomy", "", false, "TS24", "TS27", false, "", "UNCHECKED" );
		component3 = new Component( null, "extraembryonic venous system", "EMAPA:66666", "TBD", "TBD", "abstract_anatomy", "", false, "TS13", "TS26", false, "", "UNCHECKED" );
		component4 = new Component( (long) 1, "extraembryonic venous system", "EMAPA:16374", "TBD", "TBD", "abstract_anatomy", "", false, "TS13", "TS26", false, "", "UNCHECKED" );
	}
	
	@AfterClass
    public static void testCleanup() throws Exception {
		
		component1 = null; 
		component2 = null; 
		component3 = null; 
		component4 = null; 

		daofactory = null;
		
		componentDAO = null;
	}
	
    @Test
    public void testExistOid() throws Exception {
		assertTrue(componentDAO.existOid((long) 286920));
    }
    @Test
    public void testNotExistOid() throws Exception {
		assertFalse(componentDAO.existOid((long) 999999));
    }

    @Test
    public void testFindByOid() throws Exception {
		assertTrue(component1.isSameAs(componentDAO.findByOid((long) 286920)));
    }
    @Test
    public void testNotFindByOid() throws Exception {
		assertFalse(component1.isSameAs(componentDAO.findByOid((long) 286921)));
    }

    @Test
    public void testFindByOboId() throws Exception {
		assertTrue(component1.isSameAs(componentDAO.findByOboId("EMAPA:29907")));
    }
    @Test
    public void testNotFindByOboId() throws Exception {
		assertFalse(component1.isSameAs(componentDAO.findByOboId("EMAPA:30624")));
    }

    @Test
    public void testFindByOboName() throws Exception {
    	assertTrue(component1.isSameAs(componentDAO.findByOboName("epithelial layer of rest of oviduct")));
    }
    @Test
    public void testNotFindByOboName() throws Exception {
		assertFalse(component1.isSameAs(componentDAO.findByOboName("scrotal fold mesenchyme of male")));
	}

    @Test
    public void testDeletePresent() throws Exception {
    	componentDAO.delete(component2);
    	component2.setOid(null);
		componentDAO.save(component2);
    }
    @Test (expected=IllegalArgumentException.class) 
    public void testDeleteNullOid() throws Exception {
    	componentDAO.delete(component3);
    }
    @Test (expected=DAOException.class)
    public void testDeleteNotPresent() throws Exception {
    	componentDAO.delete(component4);
    }

    @Test 
    public void testUpdate() throws Exception {
    	componentDAO.update(component1);
    }
    @Test (expected=IllegalArgumentException.class) 
    public void testUpdateNullOid() throws Exception {
    	componentDAO.update(component3);
    }

    @Test  
    public void testCreateNullOid() throws Exception {
    	componentDAO.create(component3);
    	component4 = componentDAO.findByOid((long) componentDAO.maximumOid());
    	componentDAO.delete(component4);
    	assertTrue(component4.isSameAs(component3));
    	
    }
    @Test 
    public void testSaveNullOid() throws Exception {
    	componentDAO.save(component3);
    	component4 = componentDAO.findByOid((long) componentDAO.maximumOid());
    	componentDAO.delete(component4);
    	assertTrue(component4.isSameAs(component3));
    }

    @Test 
    public void testLists() throws Exception {
	    assertTrue(componentDAO.listAll().size() == componentDAO.listAllOrderByEMAPA().size());
    }
	        
    @Test 
    public void testCountEquals() throws Exception {
	    assertTrue(componentDAO.countAll() == componentDAO.count("", ""));
    }

    @Test 
    public void testCountNotEqual() throws Exception {
	    assertFalse(componentDAO.countAll() == componentDAO.count("EMAPA:77777", "BANANA"));
    }
	        
    @Test 
    public void testNotEmpty() throws Exception {
	    assertFalse(componentDAO.countAll() == 0);
    }

    @Test 
    public void testEmpty() throws Exception {
    	componentDAO.empty();
    	assertTrue(componentDAO.countAll() == 0);
    }
	        
}
