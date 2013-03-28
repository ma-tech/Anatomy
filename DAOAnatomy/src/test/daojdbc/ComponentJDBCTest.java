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
	private static Component component10;
	private static Component component11;
	private static Component component12;

	private static DAOFactory daofactory;
	
	private static ComponentDAO componentDAO;
	
	@BeforeClass
    public static void testSetUp() throws Exception {
		
		componentDAO = daofactory.getInstance("test011Localhost").getDAOImpl(ComponentDAO.class);
		
		component1 = new Component( (long) 269654, "epithelial layer of rest of oviduct", "EMAPA:29907", "TBD", "TBD", "abstract_anatomy", "", false, "TS28", "TS28", false, "", "UNCHECKED" );
		component10 = new Component( (long) 269663, "extraembryonic venous system", "EMAPA:16374", "TBD", "TBD", "abstract_anatomy", "", false, "TS13", "TS26", false, "", "UNCHECKED" );
		component11 = new Component( null, "extraembryonic venous system", "EMAPA:66666", "TBD", "TBD", "abstract_anatomy", "", false, "TS13", "TS26", false, "", "UNCHECKED" );
		component12 = new Component( (long) 1, "extraembryonic venous system", "EMAPA:16374", "TBD", "TBD", "abstract_anatomy", "", false, "TS13", "TS26", false, "", "UNCHECKED" );
	}
	
	@AfterClass
    public static void testCleanup() throws Exception {
		
		component1 = null; 
		component10 = null; 
		component11 = null; 
		component12 = null; 

		daofactory = null;
		
		componentDAO = null;
	}
	
    @Test
    public void testExistOid() throws Exception {
		assertTrue(componentDAO.existOid((long) 269654));
    }
    @Test
    public void testNotExistOid() throws Exception {
		assertFalse(componentDAO.existOid((long) 999999));
    }

    @Test
    public void testFindByOid() throws Exception {
		assertTrue(component1.isSameAs(componentDAO.findByOid((long) 269654)));
    }
    @Test
    public void testNotFindByOid() throws Exception {
		assertFalse(component1.isSameAs(componentDAO.findByOid((long) 269655)));
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
    	componentDAO.delete(component10);
    	component10.setOid(null);
		componentDAO.save(component10);
    }
    @Test (expected=IllegalArgumentException.class) 
    public void testDeleteNullOid() throws Exception {
    	componentDAO.delete(component11);
    }
    @Test (expected=DAOException.class)
    public void testDeleteNotPresent() throws Exception {
    	componentDAO.delete(component12);
    }

    @Test 
    public void testUpdate() throws Exception {
    	componentDAO.update(component1);
    }
    @Test (expected=IllegalArgumentException.class) 
    public void testUpdateNullOid() throws Exception {
    	componentDAO.update(component11);
    }

    @Test  
    public void testCreateNullOid() throws Exception {
    	componentDAO.create(component11);
    	component12 = componentDAO.findByOid((long) componentDAO.maximumOid());
    	componentDAO.delete(component12);
    	assertTrue(component12.isSameAs(component11));
    	
    }
    @Test 
    public void testSaveNullOid() throws Exception {
    	componentDAO.save(component11);
    	component12 = componentDAO.findByOid((long) componentDAO.maximumOid());
    	componentDAO.delete(component12);
    	assertTrue(component12.isSameAs(component11));
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
