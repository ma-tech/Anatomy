/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyOBO
*
* Title:        AnaRelationship.java
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
* Log: 1
*
* Description:  A Wrapper Class for the Table ANA_RELATIONSHIP;
*                Constructor requires a DAOFactory Object;
*                Pass the Class Methods: a List of OBOComponents; CalledFrom String
*               
*               Methods:
*                1. insertANA_RELATIONSHIP
*                2. deleteANA_RELATIONSHIP
*                3. updateANA_RELATIONSHIP
*                4. rebuildANA_RELATIONSHIP_PROJECT
*
* Maintenance:  Log changes below, with most recent at top of list.
*
* Who; When; What;
*
* Mike Wicks; September 2010; Create Class
*
*----------------------------------------------------------------------------------------------
*/
package oboroutines.database;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.HashMap;
import java.util.Vector;

import utility.Wrapper;
import utility.ObjectConverter;
import utility.MySQLDateTime;

import daointerface.ComponentOrderDAO;
import daointerface.LogDAO;
import daointerface.NodeDAO;
import daointerface.RelationshipDAO;
import daointerface.RelationshipProjectDAO;
import daointerface.ThingDAO;
import daointerface.VersionDAO;
import daointerface.JOINNodeRelationshipNodeDAO;

import daolayer.DAOException;
import daolayer.DAOFactory;

import daomodel.ComponentOrder;
import daomodel.Log;
import daomodel.Node;
import daomodel.Relationship;
import daomodel.RelationshipProject;
import daomodel.Thing;
import daomodel.Version;
import daomodel.JOINNodeRelationshipNode;

import obomodel.OBOComponent;


import oboroutines.archive.TreeBuilder;

public class AnaRelationship {
	// Properties ---------------------------------------------------------------------------------
    private DAOFactory daofactory; 

    //check whether was processed all the way
    private boolean processed;
    
    //Data Access Objects (DAOs)
    private LogDAO logDAO;
    private ThingDAO thingDAO;
    private VersionDAO versionDAO;
    private NodeDAO nodeDAO;
    private RelationshipDAO relationshipDAO;
    private RelationshipProjectDAO relationshipprojectDAO;
    private ComponentOrderDAO componentorderDAO;
    private JOINNodeRelationshipNodeDAO joinnoderelationshipnodeDAO;

    private long longLOG_VERSION_FK;

    //input Relationship list 
    private ArrayList<Relationship> relationshipList;
    

    // Constructors -------------------------------------------------------------------------------
    public AnaRelationship() {
    	
    }

    public AnaRelationship( DAOFactory daofactory) {
    	
    	try {
    		
            this.daofactory = daofactory;

            Wrapper.printMessage("anarelationship.constructor", "***", this.daofactory.getMsgLevel());

        	this.logDAO = daofactory.getDAOImpl(LogDAO.class);
        	this.versionDAO = daofactory.getDAOImpl(VersionDAO.class);
        	this.thingDAO = daofactory.getDAOImpl(ThingDAO.class);
        	this.nodeDAO = daofactory.getDAOImpl(NodeDAO.class);
        	this.relationshipDAO = daofactory.getDAOImpl(RelationshipDAO.class);
        	this.relationshipprojectDAO = daofactory.getDAOImpl(RelationshipProjectDAO.class);
            this.componentorderDAO = daofactory.getDAOImpl(ComponentOrderDAO.class);
        	this.joinnoderelationshipnodeDAO = daofactory.getDAOImpl(JOINNodeRelationshipNodeDAO.class);
            
        	Version version = versionDAO.findMostRecent();
            this.longLOG_VERSION_FK = version.getOid();
       	
            this.relationshipList = new ArrayList<Relationship>();

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
    
    
    //  Insert new rows into ANA_RELATIONSHIP
    public boolean insertANA_RELATIONSHIP( ArrayList<OBOComponent> newTermList, 
    		String calledFrom, 
    		String project, 
    		String strSpecies, 
    		TreeBuilder treebuilder,
    		OBOComponent abstractclassobocomponent, 
    		OBOComponent stageclassobocomponent, 
    		OBOComponent groupclassobocomponent, 
    		OBOComponent grouptermclassobocomponent) throws Exception {
    	
        Wrapper.printMessage("anarelationship.insertANA_RELATIONSHIP : " + calledFrom, "***", this.daofactory.getMsgLevel());
        	
        ArrayList<OBOComponent> insertRelObjects = new ArrayList<OBOComponent>();
        
        OBOComponent component;

        String[] orders = null;
        
        boolean flagInsert;
        
        long longREL_OID = 0;
        
        String strREL_RELATIONSHIP_TYPE_FK = "";

        long longREL_CHILD_FK = 0;
        long longREL_PARENT_FK = 0;

        this.relationshipList.clear();
        
        try {
        	
            //get max pk from referenced ana_relationship_project
        	//intMAX_PK = relationshipprojectDAO.maximumOid();

        	if ( !newTermList.isEmpty() ) {
          
                if ( !project.equals("GUDMAP") && !project.equals("EMAP") ) {
                	
                    Wrapper.printMessage("anarelationship.insertANA_RELATIONSHIP : UNKNOWN Project Value = " + project , "*", this.daofactory.getMsgLevel());
                }

                for ( int i = 0; i< newTermList.size(); i++) {
                	
                    component = newTermList.get(i);

                    //reset flagInsert for each new component
                    flagInsert = true;

                    //get parents + group parents
                    ArrayList < String > parents  = new ArrayList<String>();
                    ArrayList < String > parentTypes  = new ArrayList<String>();
                    parents.addAll(component.getChildOfs());
                    parentTypes.addAll(component.getChildOfTypes());

                    //check whether component has any parents, if none issue warning, no need to proceed with insert
                    if ( parents.size() == 0 ) {
                    	
                        flagInsert = false;
                    } 

                    for ( int j = 0; j< parents.size(); j++) {
                    	
                        //reset insertflag for each parent
                        flagInsert = true;
                        OBOComponent parent = (OBOComponent) treebuilder.getComponent( parents.get(j) );

                        String strParentType = "";
                        strParentType = parentTypes.get(j);

                        //check whether parent has been deleted from obo file, do not allow insertion
                        if ( parent == null ) {

                        	flagInsert = false;
                        }
                        else {
                        	
                        	ArrayList<JOINNodeRelationshipNode> joinnoderelationshipnodes = 
                        			(ArrayList<JOINNodeRelationshipNode>) this.joinnoderelationshipnodeDAO.listAllByChildIdAndParentId(component.getID(), parent.getID());

                        	if (joinnoderelationshipnodes.size() == 0) {
                            
                        		flagInsert = true;
                        	}
                        	else {
                                
                        		flagInsert = false;
                        	}
                        }
                        
                        //MODIFIED CODE:
                        // deleted components are now marked in proposed file as well and appear in the tree under its own root outside abstract anatomy
                        if ( parent.getStatusChange().equals("DELETE") ) {
                            
                            flagInsert = false;
                        }
                        //check whether any rules broken for each parent and print warning
                        //ignore any kind of rule violation for relationship record insertion except missing parent
                        else if ( parent.getStatusRule().equals("FAILED") ) {
                            
                        	flagInsert = true;
                        }
                        
                        //if parent is root Tmp new group don't treat as relationship
                        if (strSpecies.equals("mouse")) {

                            if ( !parent.getNamespace().equals( abstractclassobocomponent.getNamespace() ) ) {

                                flagInsert = false;
                            }
                        }
                        else if (strSpecies.equals("human")) {

                            if ( !parent.getNamespace().equals( abstractclassobocomponent.getNamespace() ) &&
                            	 !parent.getNamespace().equals( grouptermclassobocomponent.getNamespace() ) &&
                            	 !parent.getNamespace().equals( groupclassobocomponent.getNamespace() ) ) {

                                flagInsert = false;
                            }
                        }
                        else if (strSpecies.equals("chick")) {

                            if ( !parent.getNamespace().equals( abstractclassobocomponent.getNamespace() ) &&
                                 !parent.getNamespace().equals( grouptermclassobocomponent.getNamespace() ) &&
                                 !parent.getNamespace().equals( groupclassobocomponent.getNamespace() ) ) {

                                flagInsert = false;
                            }
                        }
                        else {
                        	
                        	Wrapper.printMessage("anarelationship.insertANA_RELATIONSHIP : UNKNOWN Species = " + strSpecies, "*", this.daofactory.getMsgLevel());
                            flagInsert = false;
                        }

                        //proceed with insertion 
                        if (flagInsert) {
                            
                        	OBOComponent insertRelObject = new OBOComponent();
                            
                            insertRelObject.setID( component.getDBID() ); 
                            
                            String strParentDBID = "";
                            
                            //get DBID for parent 
                            if ( parent.getStatusChange().equals("INSERT") ) {
                            	
                                 strParentDBID = parent.getDBID();
                            }
                            else {
                            	
                            	Node node = null;
                                
                            	if ( this.nodeDAO.existPublicId(parent.getID()) ) {

                            		node = this.nodeDAO.findByPublicId(parent.getID());
                                	strParentDBID = Long.toString(node.getOid());
                            	}
                            	else {
                                	
                            		node = null;
                                	strParentDBID = "0";
                            	}
                            }

                            //get order for child based on parent
                            orders = component.getOrderCommentOnParent( parent.getID() );
                            
                            if ( orders != null ) {
                            	
                                String[] arrayFirstWord = orders[0].split(" ");
                                insertRelObject.setOrderComment(arrayFirstWord[0]);
                            }
                            else {
                            	
                                insertRelObject.setOrderComment("");
                            }

                            insertRelObject.addChildOf( strParentDBID ); 
                            insertRelObject.addChildOfType( strParentType ); 

                            insertRelObjects.add( insertRelObject );
                        }
                    }
                }
                // END OF "for ( int i = 0; i< newTermList.size(); i++)"

                //INSERT INTO ANA_RELATIONSHIP
                if ( !insertRelObjects.isEmpty() ) {
                	
                    //INSERT INTO ANA_OBJECT and set DBIDs
                    AnaObject anaobject = new AnaObject( this.daofactory );
                    
                    if ( !anaobject.insertANA_OBJECT( insertRelObjects, "ANA_RELATIONSHIP" ) ) {

                  	   throw new DatabaseException("anarelationship.insertANA_RELATIONSHIP : insertANA_OBJECT : ANA_RELATIONSHIP");
                    }
                    
                    // anaobject add the OIDs to the component list
                    //  need to use these in updates to ananode
                    ArrayList<OBOComponent> updatedNewTermList = anaobject.getUpdatedNewTermList();
                    
                    //INSERT INTO ANA_RELATIONSHIP AND ANA_RELATIONSHIP_PROJECT
                    for ( OBOComponent insertRelObject : updatedNewTermList ) {
                    	
                        longREL_OID = ObjectConverter.convert(insertRelObject.getDBID(), Long.class);

                        if ( insertRelObject.getChildOfTypes().get(0).equals("PART_OF")) {
                        	
                             strREL_RELATIONSHIP_TYPE_FK = "part-of";
                        }
                        else if ( insertRelObject.getChildOfTypes().get(0).equals("IS_A")) {
                        	
                            strREL_RELATIONSHIP_TYPE_FK = "is-a";
                        }
                        else if ( insertRelObject.getChildOfTypes().get(0).equals("GROUP_PART_OF")) {
                        	
                            strREL_RELATIONSHIP_TYPE_FK = "group-part-of";
                        }
                        else if ( insertRelObject.getChildOfTypes().get(0).equals("DERIVES_FROM")) {
                        	
                            strREL_RELATIONSHIP_TYPE_FK = "derives-from";
                        }
                        else if ( insertRelObject.getChildOfTypes().get(0).equals("DEVELOPS_FROM")) {
                        	
                            strREL_RELATIONSHIP_TYPE_FK = "develops-from";
                        }
                        else if ( insertRelObject.getChildOfTypes().get(0).equals("LOCATED_IN")) {
                        	
                            strREL_RELATIONSHIP_TYPE_FK = "located-in";
                        }
                        else if ( insertRelObject.getChildOfTypes().get(0).equals("DEVELOPS_IN")) {
                        	
                            strREL_RELATIONSHIP_TYPE_FK = "develops-in";
                        }
                        else if ( insertRelObject.getChildOfTypes().get(0).equals("DISJOINT_FROM")) {
                        	
                            strREL_RELATIONSHIP_TYPE_FK = "disjoint-from";
                        }
                        else if ( insertRelObject.getChildOfTypes().get(0).equals("ATTACHED_TO")) {
                        	
                            strREL_RELATIONSHIP_TYPE_FK = "attached-to";
                        }
                        else if ( insertRelObject.getChildOfTypes().get(0).equals("HAS_PART")) {
                        	
                            strREL_RELATIONSHIP_TYPE_FK = "has-part";
                        }
                        else if ( insertRelObject.getChildOfTypes().get(0).equals("CONNECTED_TO")) {
                        	
                            strREL_RELATIONSHIP_TYPE_FK = "connected-to";
                        }
                        else {

                        	Wrapper.printMessage("anarelationship.insertANA_RELATIONSHIP : UNKNOWN Relationship Type = " + insertRelObject.getChildOfTypes().get(0), "*", this.daofactory.getMsgLevel());
                        }

                        longREL_CHILD_FK = ObjectConverter.convert(insertRelObject.getID(), Long.class);

                        long longRLP_SEQUENCE = 0;
                        
                        if ( !insertRelObject.getOrderComment().equals("") ) {
                        	
                            longRLP_SEQUENCE = ObjectConverter.convert(insertRelObject.getOrderComment(), Long.class);
                        }

                        try {
                        	
                            long longTryREL_PARENT_FK = 0;
                            longTryREL_PARENT_FK = ObjectConverter.convert(insertRelObject.getChildOfs().get(0), Long.class);
                            longREL_PARENT_FK = longTryREL_PARENT_FK;
                        }
                        catch(Exception e) {
                        	
                            Wrapper.printMessage("anarelationship.insertANA_RELATIONSHIP : Exception caught for child " + 
                                    insertRelObject.getID() + " parent " +
                                    insertRelObject.getChildOfs().toString(), "*", this.daofactory.getMsgLevel());
                            e.printStackTrace();
                        }
                    	
                        if ( !insertRelObject.getOrderComment().equals("") ) {
                        	
                            longRLP_SEQUENCE = ObjectConverter.convert(insertRelObject.getOrderComment(), Long.class);
                        }

                        Relationship relationship = new Relationship( longREL_OID, strREL_RELATIONSHIP_TYPE_FK, longREL_CHILD_FK, longREL_PARENT_FK);
                
                    	if ( !logANA_RELATIONSHIP( relationship, calledFrom ) ) {
                    		
                        	throw new DatabaseException("anarelationship.insertANA_RELATIONSHIP : logANA_RELATIONSHIP");
                    	}
                    	
                    	this.relationshipList.add(relationship);

                        this.relationshipDAO.create(relationship);
                    }

                   	// Update ANA_OBJECT
                    if ( !anaobject.updateANA_OBJECTinsertANA_RELATIONSHIP(this.relationshipList) ) {

                  	   throw new DatabaseException("ananode.insertANA_RELATIONSHIP : updateANA_OBJECTinsertANA_RELATIONSHIP");
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
    

    //  Delete from ANA_RELATIONSHIP
    public boolean deleteANA_RELATIONSHIP( ArrayList<OBOComponent> deleteRelComponents, String calledFrom ) throws Exception {

        Wrapper.printMessage("anarelationship.deleteANA_RELATIONSHIP : " + calledFrom , "***", this.daofactory.getMsgLevel());
        	
        try {
        	
        	if ( !deleteRelComponents.isEmpty() ) {
            	
                for ( OBOComponent deleteRelCompie: deleteRelComponents ) {

                	ArrayList<Relationship> relationships = 
                			(ArrayList<Relationship>) relationshipDAO.listByChildFK( ObjectConverter.convert(deleteRelCompie.getDBID(), Long.class));

                	Iterator<Relationship> iteratorrelationship = relationships.iterator();

                  	while (iteratorrelationship.hasNext()) {
                  		
                  		Relationship relationship = iteratorrelationship.next();
                  		
                    	Thing thing = thingDAO.findByOid(relationship.getOid()); 

                    	if ( !logANA_RELATIONSHIP( relationship, calledFrom ) ) {
                    		
                        	throw new DatabaseException("anarelationship.deleteANA_RELATIONSHIP : logANA_RELATIONSHIP");
                    	}

                    	thingDAO.delete(thing);

                  		relationshipDAO.delete(relationship);
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
    
      
	//  rebuild ANA_RELATIONSHIP_PROJECT
    public boolean rebuildANA_RELATIONSHIP_PROJECT() throws Exception {

        Wrapper.printMessage("anarelationship.rebuildANA_RELATIONSHIP_PROJECT", "***", this.daofactory.getMsgLevel());
        	
        ArrayList<ComponentOrder> componentorders = new ArrayList<ComponentOrder>();
        ArrayList<RelationshipProject> relationshipprojects = new ArrayList<RelationshipProject>(); 

        long intMAX_PK = 0;
        
        try {
        	
            relationshipprojects = (ArrayList) this.relationshipprojectDAO.listAll();
        	
        	Iterator<RelationshipProject> iteratorRelationshipProject = relationshipprojects.iterator();

        	while (iteratorRelationshipProject.hasNext()) {
        		
        		RelationshipProject relationshipproject = iteratorRelationshipProject.next();
        		
        		this.relationshipprojectDAO.delete(relationshipproject);
        	}

            ArrayList<JOINNodeRelationshipNode> joinnoderelationships = new ArrayList<JOINNodeRelationshipNode>(); 
            joinnoderelationships = (ArrayList) this.joinnoderelationshipnodeDAO.listAllPartOfs();
            
        	Iterator<JOINNodeRelationshipNode> iteratorJOINNodeRelationshipNode = joinnoderelationships.iterator();

        	while (iteratorJOINNodeRelationshipNode.hasNext()) {
        		
        		JOINNodeRelationshipNode joinnoderelationshipnode = iteratorJOINNodeRelationshipNode.next();

            	componentorders = (ArrayList) this.componentorderDAO.listByChildIdAndParentID( joinnoderelationshipnode.getAPublicId(), joinnoderelationshipnode.getBPublicId() );

            	if ( componentorders.size() == 1) {
            		
            		ComponentOrder componentorder = componentorders.get(0);
            		
                    //get max primary key for ana_relationship_project
                    intMAX_PK++; 
                    RelationshipProject relationshipproject1 = new RelationshipProject( intMAX_PK, 
                    		joinnoderelationshipnode.getOidRelationship(), 
                    		"EMAP", 
                    		componentorder.getAlphaorder());
            
                    this.relationshipprojectDAO.create(relationshipproject1);
                    
                    //get max primary key for ana_relationship_project
                    intMAX_PK++; 
                    RelationshipProject relationshipproject2 = new RelationshipProject( intMAX_PK, 
                    		joinnoderelationshipnode.getOidRelationship(), 
                    		"GUDMAP", 
                    		componentorder.getSpecialorder());
                  
                    this.relationshipprojectDAO.create(relationshipproject2);
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
    
    
    //  Insert into ANA_LOG for ANA_RELATIONSHIP Insertions or Deletions
    public boolean logANA_RELATIONSHIP( Relationship relationship, String calledFrom ) throws Exception {

        Wrapper.printMessage("anarelationship.logANA_RELATIONSHIP : " + calledFrom, "***", this.daofactory.getMsgLevel());
        	
        try {
        	
            long longLogLoggedOID = 0;
            long longLogOID = 0;
            
            HashMap<String, String> relOldValues = new HashMap<String, String>(); 

            //ANA_RELATIONSHIP columns
            Vector<String> vRELcolumns = new Vector<String>();
            vRELcolumns.add("REL_OID");
            vRELcolumns.add("REL_RELATIONSHIP_TYPE_FK");
            vRELcolumns.add("REL_PARENT_FK");
            vRELcolumns.add("REL_CHILD_FK");
            
            //column values for selection from ANA_RELATIONSHIP
            long longREL_OID = 0;
            
            //column values for insertion into ANA_LOG
            String strLOG_COLUMN_NAME = "";
            String strLOG_OLD_VALUE = "";
            String strLOG_DATETIME = MySQLDateTime.now();
            
            //version_oid should be very first obj_oid created for easy tracing
            String strLOG_COMMENTS = "";

            if ( calledFrom.equals("INSERT") ) {
            	
            	strLOG_COMMENTS = "INSERT Relationships";
            }
            else if ( calledFrom.equals("UPDATE") ) {
            	
            	strLOG_COMMENTS = "UPDATE Relationships";
            }
            else if ( calledFrom.equals("DELETE") ) {
            	
            	strLOG_COMMENTS = "DELETE Relationships";
            }
            else {
            	
            	strLOG_COMMENTS = "UNKNOWN REASON for Relationship INSERT into ANA_LOG";
            }
            
            //get max log_oid from new database
        	longLogOID = logDAO.maximumOid(); 

            //clear HashMap relOldValues
            relOldValues.clear();
            relOldValues.put( "REL_OID", utility.ObjectConverter.convert(relationship.getOid(), String.class ));
            relOldValues.put( "REL_RELATIONSHIP_TYPE_FK", relationship.getTypeFK() );
            relOldValues.put( "REL_PARENT_FK", utility.ObjectConverter.convert(relationship.getParentFK(), String.class ));
            relOldValues.put( "REL_CHILD_FK", utility.ObjectConverter.convert(relationship.getChildFK(), String.class ));

            longLogLoggedOID = relationship.getOid();
            
            for (String columnName: vRELcolumns) {
          	  
                longLogOID++;
                strLOG_COLUMN_NAME = columnName;
                strLOG_OLD_VALUE = relOldValues.get(columnName);

                Log log = new Log(longLogOID, longLogLoggedOID, this.longLOG_VERSION_FK, strLOG_COLUMN_NAME, strLOG_OLD_VALUE, strLOG_COMMENTS, strLOG_DATETIME, "ANA_RELATIONSHIP");
                
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
    public boolean isProcessed() {
        return this.processed;
    }
    // Setters ------------------------------------------------------------------------------------
    public void setProcessed( boolean processed ) {
        this.processed = processed;
    }
}
