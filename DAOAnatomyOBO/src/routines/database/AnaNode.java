/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyOBO
*
* Title:        AnaNode.java
*
* Date:         2012
*
* Author:       Mike Wicks
*
* Copyright:    2009 Medical Research Council, UK.
*               All rights reserved.
*
* Address:      MRC Human Genetics Unit,
*               Western General Hospital,
*               Edinburgh, EH4 2XU, UK.
*
* Version: 1
*
* Description:  A Wrapper Class for the Table ANA_NODE;
*                Constructor requires a DAOFactory Object;
*                Pass the Class Methods: a List of OBOComponents; CalledFrom String
*               
*               Methods:
*                1.  insertANA_NODE
*                2.  insertANA_OBO_COMPONENT_ALTERNATIVE
*                3.  updateANA_OBO_COMPONENT
*                4.  updateANA_OBO_COMPONENT_SYNONYM
*                5.  updateANA_OBO_COMPONENT_COMMENT
*                6.  updateANA_OBO_COMPONENT_RELATIONSHIP
*                7.  updateANA_OBO_COMPONENT_ORDER
*                8.  deleteANA_NODE
*                9.  updateANA_NODE
*                10. updateANA_NODE_primary
*                11. setDatabaseOIDs
*
* Maintenance:  Log changes below, with most recent at top of list.
*
* Who; When; What;
*
* Mike Wicks; November 2012; Create Class
*
*----------------------------------------------------------------------------------------------
*/
package routines.database;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.HashMap;
import java.util.Vector;

import routines.base.TreeBuilder;

import utility.Wrapper;
import utility.MySQLDateTime;

import daointerface.NodeDAO;
import daointerface.ThingDAO;
import daointerface.LogDAO;
import daointerface.VersionDAO;
import daointerface.ComponentAlternativeDAO;
import daointerface.ComponentCommentDAO;
import daointerface.ComponentDAO;
import daointerface.ComponentOrderDAO;
import daointerface.ComponentRelationshipDAO;
import daointerface.ComponentSynonymDAO;

import daolayer.DAOException;
import daolayer.DAOFactory;

import daomodel.Node;
import daomodel.Log;
import daomodel.Thing;
import daomodel.Version;
import daomodel.Component;
import daomodel.ComponentAlternative;
import daomodel.ComponentComment;
import daomodel.ComponentOrder;
import daomodel.ComponentRelationship;
import daomodel.ComponentSynonym;

import obomodel.OBOComponent;

public class AnaNode {
	// Properties ---------------------------------------------------------------------------------
    private DAOFactory daofactory; 

    private String requestMsgLevel; 
	
    //check whether was processed all the way
    private boolean processed;
    
    //Data Access Objects (DAOs)
    private NodeDAO nodeDAO;
    private ThingDAO thingDAO;
    private LogDAO logDAO;
    private VersionDAO versionDAO;
    private ComponentDAO componentDAO;
    private ComponentAlternativeDAO componentalternativeDAO;
    private ComponentRelationshipDAO componentrelationshipDAO;
    private ComponentOrderDAO componentorderDAO;
    private ComponentSynonymDAO componentsynonymDAO;
    private ComponentCommentDAO componentcommentDAO;

    //input term list 
    private ArrayList<OBOComponent> startingComponentList;
    
    //output term list 
    private ArrayList<OBOComponent> updatedComponentList;
    
    //term list for disallowed deleted components 
    // 1 - not found in db
    // 2 - is primary and have undeleted children
    private ArrayList<OBOComponent> unDeletedComponentList;
    
    //term list for disallowed modified components
    // 1 - not found in db
    private ArrayList<OBOComponent> unModifiedComponentList;
    
    private long longLOG_VERSION_FK;

    
    // Constructors -------------------------------------------------------------------------------
    public AnaNode() {
    	
    }

    public AnaNode( String requestMsgLevel, DAOFactory daofactory) {
    	
    	try {
    		
        	this.requestMsgLevel = requestMsgLevel;

            Wrapper.printMessage("ananode.constructor", "***", this.requestMsgLevel);
            	
            this.daofactory = daofactory;

        	this.nodeDAO = daofactory.getDAOImpl(NodeDAO.class);
        	this.thingDAO = daofactory.getDAOImpl(ThingDAO.class);
        	this.logDAO = daofactory.getDAOImpl(LogDAO.class);
        	this.versionDAO = daofactory.getDAOImpl(VersionDAO.class);
            this.componentDAO = daofactory.getDAOImpl(ComponentDAO.class);
            this.componentalternativeDAO = daofactory.getDAOImpl(ComponentAlternativeDAO.class);
            this.componentrelationshipDAO = daofactory.getDAOImpl(ComponentRelationshipDAO.class);
            this.componentorderDAO = daofactory.getDAOImpl(ComponentOrderDAO.class);
            this.componentsynonymDAO = daofactory.getDAOImpl(ComponentSynonymDAO.class);
            this.componentcommentDAO = daofactory.getDAOImpl(ComponentCommentDAO.class);
            
        	Version version = versionDAO.findMostRecent();
            this.longLOG_VERSION_FK = version.getOid();

            this.startingComponentList = new ArrayList<OBOComponent>();
            this.updatedComponentList = new ArrayList<OBOComponent>();
            this.unDeletedComponentList = new ArrayList<OBOComponent>();
            this.unModifiedComponentList = new ArrayList<OBOComponent>();

        	setProcessed( true );
    	}

        catch ( DAOException dao ) {
        	
        	setProcessed( false );
            dao.printStackTrace();
        }
        catch ( Exception ex ) {
        	
        	setProcessed( false );
            ex.printStackTrace();
        }
    }
    
    
    //  Insert new rows into ANA_NODE
    public boolean insertANA_NODE( ArrayList<OBOComponent> newTermList, String calledFrom, String strSpecies, TreeBuilder treebuilder ) throws Exception {

        Wrapper.printMessage("ananode.insertANA_NODE : " + calledFrom, "***", this.requestMsgLevel);
        	
        int intANO_OID = 0;
        
        String strANO_SPECIES_FK = "";
        String strANO_COMPONENT_NAME = "";
        
        boolean boolANO_IS_PRIMARY = true;
        boolean boolANO_IS_GROUP = false;
        
        String strANO_PUBLIC_ID = "";
        String strANO_DESCRIPTION = "";
        String strANO_DISPLAY_ID = "";

        OBOComponent component;

        this.updatedComponentList = null;
        
        try {

        	if ( !newTermList.isEmpty() ) {

        		//insert into ANA_OBJECT first
                AnaObject anaobject = new AnaObject( this.requestMsgLevel, this.daofactory);
                
                if ( !anaobject.insertANA_OBJECT(newTermList, "ANA_NODE") ) {

             	   throw new DatabaseException("ananode.insertANA_NODE : insertANA_OBJECT:ANA_NODE");
                }

                // anaobject add the OIDs to the component list
                //  need to use these in updates to ananode
                this.updatedComponentList = anaobject.getUpdatedNewTermList();
                		
                for (int i = 0; i< this.updatedComponentList.size(); i++) {
                
                	component = this.updatedComponentList.get(i);

                	//prepare values
                	intANO_OID = Integer.parseInt(component.getDBID());
                	strANO_SPECIES_FK = strSpecies;
                	strANO_COMPONENT_NAME = component.getName();
                   
                	if (component.isPrimary() ) {
                	
                		boolANO_IS_PRIMARY = true;
                		boolANO_IS_GROUP = false;
                	}
                	else {
                	
                		boolANO_IS_PRIMARY = false;
                		boolANO_IS_GROUP = true;
                	}
                   
                	if ( !anaobject.getMaxPublicId() ) {
                 	
                		throw new DatabaseException("ananode.insertANA_NODE : anaobject.getMaxPublicId()");
                	}

                	int intCurrentPublicID = anaobject.getCurrentMaxPublicId() + 1;
            	   
                	char padChar = '0';
                   
                	if (strANO_SPECIES_FK.equals("mouse")) {
               	   
                    	strANO_PUBLIC_ID = "EMAPA:" + intCurrentPublicID;
                		strANO_DISPLAY_ID = "EMAPA:" + utility.StringPad.pad(intCurrentPublicID, 7, padChar);

                		/*
                		System.out.println("intCurrentPublicID = " + intCurrentPublicID);
                    	System.out.println("strANO_PUBLIC_ID = " + strANO_PUBLIC_ID);
                    	System.out.println("strANO_DISPLAY_ID = " + strANO_DISPLAY_ID);
                    	*/
                	}
                	else if (strANO_SPECIES_FK.equals("chick")) {
                	
                		strANO_PUBLIC_ID = "ECAPA:" + intCurrentPublicID;
                		strANO_DISPLAY_ID = "ECAPA:" + utility.StringPad.pad(intCurrentPublicID, 7, padChar);
                	}
                	else if (strANO_SPECIES_FK.equals("human")) {
                	
                		strANO_PUBLIC_ID = "EHDAA:" + intCurrentPublicID;
                		strANO_DISPLAY_ID = "EHDAA:" + utility.StringPad.pad(intCurrentPublicID, 7, padChar);
                	}
                	else {
                    
                		Wrapper.printMessage("ananode.insertANA_NODE : " + calledFrom + "; " + "UNKNOWN Species Value = " + strANO_SPECIES_FK, "*", this.requestMsgLevel);
                	}

                	// Column 7
                	strANO_DESCRIPTION = "";
                   
                	Node node = new Node((long) intANO_OID, strANO_SPECIES_FK, strANO_COMPONENT_NAME, boolANO_IS_PRIMARY, boolANO_IS_GROUP, strANO_PUBLIC_ID, strANO_DESCRIPTION, strANO_DISPLAY_ID);

                	//System.out.println("node.toString() = " + node.toString());
                	
                	if ( !logANA_NODE( node, calledFrom, strSpecies ) ) {
                		
                    	throw new DatabaseException("ananode.insertANA_NODE : logANA_NODE");
                	}

                	this.nodeDAO.create(node);
                	
                	//Update Components in Memory
                	// assign generated new EMAPA id to new components replacing temp id
                	component.setNewID(strANO_PUBLIC_ID);

                	if (strANO_SPECIES_FK.equals("mouse")) {
                	   
                		treebuilder.getComponent( component.getID()).setCheckComment("New EMAPA:ID generated: " + strANO_PUBLIC_ID);
                	}
                	else if (strANO_SPECIES_FK.equals("chick")) {
                	   
                		treebuilder.getComponent( component.getID()).setCheckComment("New ECAPA:ID generated: " + strANO_PUBLIC_ID);
                	}
                	else if (strANO_SPECIES_FK.equals("human")) {
                	
                		treebuilder.getComponent( component.getID()).setCheckComment("New EHDAA:ID generated: " + strANO_PUBLIC_ID);
                	}
                	else {
                    
                		Wrapper.printMessage("ananode.insertANA_NODE : " + calledFrom + "; " + "UNKNOWN Species Value = " + strANO_SPECIES_FK, "*", this.requestMsgLevel);
                	}

                	// Update the ANA_OBO_COMPONENT tables ...
                	insertANA_OBO_COMPONENT_ALTERNATIVE( component.getID(), strANO_PUBLIC_ID);

                	// update new components with ano_oid
                	component.setDBID(Integer.toString(intANO_OID));
                   
                	// update new component with generated emapa id
                	component.setID(strANO_PUBLIC_ID);
                   	component.setDisplayId(strANO_DISPLAY_ID);                   
                }
           }
        }
        catch ( DAOException dao ) {
        	
        	setProcessed( false );
            dao.printStackTrace();
        }
        catch ( Exception ex ) {
        	
        	setProcessed( false );
            ex.printStackTrace();
        }

        return isProcessed();
    }
    

    //  Insert new rows into ANA_OBO_COMPONENT_ALTERNATIVE
    private void insertANA_OBO_COMPONENT_ALTERNATIVE( String oldPublicId, String newPublicId ) throws Exception {
      
        Wrapper.printMessage("ananode.insertANA_OBO_COMPONENT_ALTERNATIVE", "***", this.requestMsgLevel);
        
    	try {
    		
    		ComponentAlternative componentalternative = new ComponentAlternative(null, newPublicId, oldPublicId);

    		this.componentalternativeDAO.create(componentalternative);
      	
    		updateANA_OBO_COMPONENT( oldPublicId, newPublicId );
      	
    		updateANA_OBO_COMPONENT_SYNONYM( oldPublicId, newPublicId );
      	
    		updateANA_OBO_COMPONENT_COMMENT( oldPublicId, newPublicId );
      	
    		updateANA_OBO_COMPONENT_RELATIONSHIP( oldPublicId, newPublicId );
      	
    		updateANA_OBO_COMPONENT_ORDER( oldPublicId, newPublicId );
    	}
    	catch ( DAOException dao ) {

    		setProcessed( false );
    		dao.printStackTrace();
    	}
    	catch ( Exception ex ) {

    		setProcessed( false );
    		ex.printStackTrace();
    	}
    }
    

    //  Update existing rows in ANA_OBO_COMPONENT
    private void updateANA_OBO_COMPONENT( String oldPublicId, String newPublicId ) throws Exception   {

        Wrapper.printMessage("ananode.updateANA_OBO_COMPONENT", "***", this.requestMsgLevel);

        try {
        	
          	 Component component = this.componentDAO.findByOboId(oldPublicId);
           	 component.setId(newPublicId);
         	   
           	 this.componentDAO.save(component);
        }
        catch ( DAOException dao ) {

          	 setProcessed( false );
           	 dao.printStackTrace();
        }
        catch ( Exception ex ) {

          	 setProcessed( false );
           	 ex.printStackTrace();
        }
    }

    
    //  Update existing rows in ANA_OBO_COMPONENT_SYNONYM
    private void updateANA_OBO_COMPONENT_SYNONYM( String oldPublicId, String newPublicId ) throws Exception  {
        
        Wrapper.printMessage("ananode.updateANA_OBO_COMPONENT_SYNONYM", "***", this.requestMsgLevel);
        
        try {
        	
    		List<ComponentSynonym> componentsynonyms = this.componentsynonymDAO.listByOboId(oldPublicId);
        	Iterator<ComponentSynonym> iteratorComponentSynonym = componentsynonyms.iterator();

        	while (iteratorComponentSynonym.hasNext()) {
        		
        		ComponentSynonym componentsynonym = iteratorComponentSynonym.next();
        		
        		componentsynonym.setId(newPublicId);
        		
        		this.componentsynonymDAO.save(componentsynonym);
        	}
        }
        catch ( DAOException dao ) {
        	
        	setProcessed( false );
            dao.printStackTrace();
        }
        catch ( Exception ex ) {
        	
        	setProcessed( false );
            ex.printStackTrace();
        }
    }

    
    //  Update existing rows in ANA_OBO_COMPONENT_COMMENT
    private void updateANA_OBO_COMPONENT_COMMENT( String oldPublicId, String newPublicId ) throws Exception  {
        
        Wrapper.printMessage("ananode.updateANA_OBO_COMPONENT_COMMENT", "***", this.requestMsgLevel);
        	
        try {
        	
    		List<ComponentComment> componentcomments = this.componentcommentDAO.listByOboId(oldPublicId);
        	Iterator<ComponentComment> iteratorComponentComment = componentcomments.iterator();

        	while (iteratorComponentComment.hasNext()) {
        		
        		ComponentComment componentcomment = iteratorComponentComment.next();
        		
        		componentcomment.setId(newPublicId);
        		
        		this.componentcommentDAO.save(componentcomment);
        	}
        }
        catch ( DAOException dao ) {
        	
        	setProcessed( false );
            dao.printStackTrace();
        }
        catch ( Exception ex ) {
        	
        	setProcessed( false );
            ex.printStackTrace();
        }
    }
    

    //  Update existing rows in ANA_OBO_COMPONENT_RELATIONSHIP
    private void updateANA_OBO_COMPONENT_RELATIONSHIP( String oldPublicId, String newPublicId ) throws Exception  {
        
        Wrapper.printMessage("ananode.updateANA_OBO_COMPONENT_RELATIONSHIP", "***", this.requestMsgLevel);
        	
        try {
        	
    		List<ComponentRelationship> componentrelationshipchilds = this.componentrelationshipDAO.listByChild(oldPublicId);
        	Iterator<ComponentRelationship> iteratorComponentRelationshipChild = componentrelationshipchilds.iterator();

        	while (iteratorComponentRelationshipChild.hasNext()) {
        		
        		ComponentRelationship componentrelationship = iteratorComponentRelationshipChild.next();
        		
        		componentrelationship.setChild(newPublicId);
        		
        		this.componentrelationshipDAO.save(componentrelationship);
        	}

        	List<ComponentRelationship> componentrelationshipparents = this.componentrelationshipDAO.listByParent(oldPublicId);
        	Iterator<ComponentRelationship> iteratorComponentRelationshipParent = componentrelationshipparents.iterator();

        	while (iteratorComponentRelationshipParent.hasNext()) {
        		
        		ComponentRelationship componentrelationship = iteratorComponentRelationshipParent.next();
        		
        		componentrelationship.setParent(newPublicId);
        		
        		this.componentrelationshipDAO.save(componentrelationship);
        	}
        }
        catch ( DAOException dao ) {
        	
        	setProcessed( false );
            dao.printStackTrace();
        }
        catch ( Exception ex ) {
        	
        	setProcessed( false );
            ex.printStackTrace();
        }
    }
    

    //  Update existing rows in ANA_OBO_COMPONENT_ORDER
    private void updateANA_OBO_COMPONENT_ORDER( String oldPublicId, String newPublicId ) throws Exception  {
        
        Wrapper.printMessage("ananode.updateANA_OBO_COMPONENT_ORDER", "***", this.requestMsgLevel);
        	
        try {
        	
    		List<ComponentOrder> componentorderchilds = this.componentorderDAO.listByChild(oldPublicId);
        	Iterator<ComponentOrder> iteratorComponentOrderChild = componentorderchilds.iterator();

        	while (iteratorComponentOrderChild.hasNext()) {
        		
        		ComponentOrder componentorder = iteratorComponentOrderChild.next();
        		
        		componentorder.setChild(newPublicId);
        		
        		this.componentorderDAO.save(componentorder);
        	}

        	List<ComponentOrder> componentorderparents = this.componentorderDAO.listByParent(oldPublicId);
        	Iterator<ComponentOrder> iteratorComponentOrderParent = componentorderparents.iterator();

        	while (iteratorComponentOrderParent.hasNext()) {
        		
        		ComponentOrder componentorder = iteratorComponentOrderParent.next();
        		
        		componentorder.setParent(newPublicId);
        		
        		this.componentorderDAO.save(componentorder);
        	}
        }
        catch ( DAOException dao ) {
        	
        	setProcessed( false );
            dao.printStackTrace();
        }
        catch ( Exception ex ) {
        	
        	setProcessed( false );
            ex.printStackTrace();
        }
    }
    
  
    //  Insert new rows into ANA_NODE
    public boolean deleteANA_NODE( ArrayList<OBOComponent> termList, String strSpecies, String calledFrom ) throws Exception  {

        Wrapper.printMessage("ananode.deleteANA_NODE : " + calledFrom, "***", this.requestMsgLevel);
        	
        OBOComponent component;

        try {
        	
        	if ( !termList.isEmpty() ) {
                
        		for (int i = 0; i< termList.size(); i++) {
                
        			component = termList.get(i);

        			// Delete the ANA_NODE rows, if any
        			if ( nodeDAO.existOid(Long.valueOf(component.getDBID()))) {

        				Node node = nodeDAO.findByOid(Long.valueOf(component.getDBID()));

        				Thing thing = thingDAO.findByOid(node.getOid()); 

                    	if ( !logANA_NODE( node, calledFrom, strSpecies ) ) {
                    		
                        	throw new DatabaseException("ananode.deleteANA_NODE : logANA_NODE");
                    	}

                    	thingDAO.delete(thing);

        				nodeDAO.delete(node);
        			}
        		}
        	}
        }
        catch ( DAOException dao ) {
        	
        	setProcessed( false );
            dao.printStackTrace();
        }
        catch ( Exception ex ) {
        	
        	setProcessed( false );
            ex.printStackTrace();
        }

        return isProcessed();
    }
    
    
    //  Update ANA_NODE for Changed Names
    public boolean updateANA_NODE_name( ArrayList<OBOComponent> changedNameTermList, String calledFrom, String strSpecies ) throws Exception  {

        Wrapper.printMessage("ananode.updateANA_NODE : " + calledFrom, "***", this.requestMsgLevel);
        	
        try {

        	if ( !changedNameTermList.isEmpty() ) {

            	for (OBOComponent component: changedNameTermList) {
                	
                	Node node = this.nodeDAO.findByPublicId(component.getID());
                	
                	node.setComponentName(component.getName());
                	
                	if ( !logANA_NODE( node, calledFrom, strSpecies ) ) {
                		
                    	throw new DatabaseException("ananode.updateANA_NODE_name : logANA_NODE");
                	}

                	this.nodeDAO.save(node);
                }
            }
        }
        catch ( DAOException dao ) {
        	
        	setProcessed( false );
            dao.printStackTrace();
        } 
        catch ( Exception ex ) {
        	
        	setProcessed( false );
        	ex.printStackTrace();
        } 
        
        return isProcessed();
    }


    //  Update ANA_NODE for primary nodes
    public boolean updateANA_NODE_primary( ArrayList< OBOComponent > changedPrimaryTermList, String calledFrom, String strSpecies ) throws Exception  {

        Wrapper.printMessage("ananode.updateANA_NODE_primary : " + calledFrom, "***", this.requestMsgLevel);
        	
        try {

        	if ( !changedPrimaryTermList.isEmpty() ) {
            	
                for (OBOComponent component: changedPrimaryTermList) {

                	Node node = this.nodeDAO.findByPublicId(component.getID());
                    
                 	node.setGroup(!component.isPrimary());
                	node.setPrimary(component.isPrimary());

                	if ( !logANA_NODE( node, calledFrom, strSpecies ) ) {
                		
                    	throw new DatabaseException("ananode.updateANA_NODE_primary : logANA_NODE");
                	}

                	this.nodeDAO.save(node);
                }
            }
        }
        catch ( DAOException dao ) {
        	
        	setProcessed( false );
            dao.printStackTrace();
        }
        catch ( Exception ex ) {
        	
        	setProcessed( false );
            ex.printStackTrace();
        }
        
        return isProcessed();
    }

    
    //  Set Database OIDs into a list of components
    public boolean setDatabaseOIDs( ArrayList<OBOComponent> termList, String calledFrom )  throws Exception {

        Wrapper.printMessage("ananode.setDatabaseOIDs : " + calledFrom, "***", this.requestMsgLevel);
        	
        OBOComponent component = new OBOComponent();
        
        this.startingComponentList = termList;
        this.unDeletedComponentList.clear();
        this.unModifiedComponentList.clear();
        this.updatedComponentList.clear();
        
        try {
        	
            for (int i = 0; i<this.startingComponentList.size(); i++ ) {
            	
                component = this.startingComponentList.get(i);
                
                Node node = this.nodeDAO.findByPublicId(component.getID());
                
                if (node == null) {
                	
                    if ( component.getStatusChange().equals("DELETE") ) {
                    	
                        component.setCheckComment("Delete Record Warning: No " +
                            "term with the ID " + component.getID() +
                            " exists in ANA_NODE, deletion for this " +
                            "component did not proceed.");
                        termList.remove(i);
                        i--;
                        
                        this.unDeletedComponentList.add(component);
                    }
                    else if ( component.getStatusChange().equals("UPDATE") ) {
                        
                    	component.setCheckComment("Update Record Warning: No " +
                            "term with the ID " + component.getID() +
                            " exists in ANA_NODE, changes made by the user " +
                            "for this component were not update in the " +
                            "database.");
                        termList.remove(i);
                        i--;
                        
                        this.unModifiedComponentList.add(component);
                    }
                }
                else {
                	
                    component.setDBID( node.getOid().toString() );

                    this.updatedComponentList.add( component );
                }
            }
        }
        catch ( DAOException dao ) {
        	
        	setProcessed( false );
            dao.printStackTrace();
        }
        catch ( Exception ex ) {
        	
        	setProcessed( false );
            ex.printStackTrace();
        }
        
        return isProcessed();
    }

    //  Update ANA_NODE for Changed Names
    public boolean logANA_NODE( Node node, String calledFrom, String strSpecies ) throws Exception  {

        Wrapper.printMessage("ananode.logANA_NODE : " + calledFrom, "***", this.requestMsgLevel);
        	
        try {
                	
            long longLogOID = 0;
            long longLogLoggedOID = 0;

            //column values for insertion into ANA_LOG
            String strLOG_COLUMN_NAME = "";
            String strLOG_OLD_VALUE = "";
            
            //version_oid should be very first obj_oid created for easy tracing
            String strLOG_COMMENTS = "";
            String strLOG_DATETIME = MySQLDateTime.now();
            
            if ( calledFrom.equals("INSERT") ) {
            	
            	strLOG_COMMENTS = "INSERT Nodes";
            }
            else if ( calledFrom.equals("DELETE") ) {
            	
            	strLOG_COMMENTS = "DELETE Nodes";
            }
            else if ( calledFrom.equals("UPDATE") ) {
            	
            	strLOG_COMMENTS = "UPDATE Nodes";
            }
            else {
            	
            	strLOG_COMMENTS = "UNKNOWN REASON for Nodes INSERT into ANA_LOG";
            }

            //create one record
            HashMap<String, String> anoOldValues = new HashMap<String, String>();
            
            //ANA_NODE columns
            Vector<String> vANOcolumns = new Vector<String>();
            vANOcolumns.add("ANO_OID");
            vANOcolumns.add("ANO_SPECIES_FK");
            vANOcolumns.add("ANO_COMPONENT_NAME");
            vANOcolumns.add("ANO_IS_PRIMARY");
            vANOcolumns.add("ANO_IS_GROUP");
            vANOcolumns.add("ANO_PUBLIC_ID");
            vANOcolumns.add("ANO_DESCRIPTION");
            vANOcolumns.add("ANO_DISPLAY_ID");
            
        	//get max log_oid from new database
        	longLogOID = utility.ObjectConverter.convert(logDAO.maximumOid(), Long.class);
        	
            anoOldValues.clear();
            anoOldValues.put( "ANO_OID", utility.ObjectConverter.convert(node.getOid(), String.class));
            anoOldValues.put( "ANO_SPECIES_FK", node.getSpeciesFK() );
            anoOldValues.put( "ANO_COMPONENT_NAME", node.getComponentName() );
            anoOldValues.put( "ANO_IS_PRIMARY", utility.ObjectConverter.convert(node.getPrimary(), String.class));
            anoOldValues.put( "ANO_IS_GROUP", utility.ObjectConverter.convert(node.getGroup(), String.class));
            anoOldValues.put( "ANO_PUBLIC_ID", node.getPublicId());
            anoOldValues.put( "ANO_DESCRIPTION", node.getDescription() );
            anoOldValues.put( "ANO_DISPLAY_ID", node.getDisplayId() );

            longLogLoggedOID = node.getOid();
        	
            for (String columnName: vANOcolumns) {	
            	
                longLogOID++;
                strLOG_COLUMN_NAME = columnName;
                strLOG_OLD_VALUE = anoOldValues.get(columnName);

                Log log = new Log(longLogOID, longLogLoggedOID, this.longLOG_VERSION_FK, strLOG_COLUMN_NAME, strLOG_OLD_VALUE, strLOG_COMMENTS, strLOG_DATETIME, "ANA_NODE");
                
                //System.out.println("log.toString() " + log.toString());
                
                logDAO.create(log); 
            }
        }
        catch ( DAOException dao ) {
        	
        	setProcessed( false );
            dao.printStackTrace();
        } 
        catch ( Exception ex ) {
        	
        	setProcessed( false );
        	ex.printStackTrace();
        } 
        
        return isProcessed();
    }

    
    // Getters ------------------------------------------------------------------------------------
    public ArrayList<OBOComponent> getStartingComponentList() {
        return this.startingComponentList;
    }
    public ArrayList<OBOComponent> getUpdatedComponentList() {
        return this.updatedComponentList;
    }
    public ArrayList<OBOComponent> getUnDeletedComponentList() {
        return this.unDeletedComponentList;
    }
    public ArrayList<OBOComponent> getUnModifiedComponentList() {
        return this.unModifiedComponentList;
    }

    public boolean isProcessed() {
        return this.processed;
    }
    
    // Setters ------------------------------------------------------------------------------------
    public void setProcessed( boolean processed ) {
        this.processed = processed;
    }
}
