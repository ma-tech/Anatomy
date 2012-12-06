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
*                2. insertANA_RELATIONSHIP_PROJECT
*                3. deleteANA_RELATIONSHIP
*                4. updateParents
*                5. update_orderANA_RELATIONSHIP
*                6. rebuildANA_RELATIONSHIP_PROJECT
*                7. reorderANA_RELATIONSHIP
*
* Maintenance:  Log changes below, with most recent at top of list.
*
* Who; When; What;
*
* Mike Wicks; September 2010; Create Class
*
*----------------------------------------------------------------------------------------------
*/
package routines.database;

import routines.base.TreeBuilder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.HashMap;

import daolayer.ComponentOrderDAO;
import daolayer.NodeDAO;
import daolayer.RelationshipDAO;
import daolayer.JOINNodeRelationshipNodeDAO;
import daolayer.JOINNodeRelationshipRelationshipProjectDAO;
import daolayer.JOINRelationshipProjectRelationshipDAO;
import daolayer.RelationshipProjectDAO;

import daolayer.DAOException;
import daolayer.DAOFactory;

import daomodel.ComponentOrder;
import daomodel.Node;
import daomodel.Relationship;
import daomodel.JOINNodeRelationshipNode;
import daomodel.JOINNodeRelationshipRelationshipProject;
import daomodel.JOINRelationshipProjectRelationship;
import daomodel.RelationshipProject;

import obomodel.OBOComponent;

import utility.Wrapper;

public class AnaRelationship {
	// Properties ---------------------------------------------------------------------------------
    private DAOFactory daofactory; 

    private String requestMsgLevel; 
	
    //check whether was processed all the way
    private boolean processed;
    
    //Data Access Objects (DAOs)
    private RelationshipDAO relationshipDAO;
    private JOINNodeRelationshipNodeDAO joinnoderelationshipnodeDAO;
    private NodeDAO nodeDAO;
    private RelationshipProjectDAO relationshipprojectDAO;
    private ComponentOrderDAO componentorderDAO;
    private JOINNodeRelationshipRelationshipProjectDAO joinnoderelationshiprelationshipprojectDAO;
    private JOINRelationshipProjectRelationshipDAO joinrelationshipprojectrelationshipDAO;


    // Constructors -------------------------------------------------------------------------------
    public AnaRelationship() {
    	
    }

    public AnaRelationship( String requestMsgLevel, DAOFactory daofactory) {
    	
    	try {
    		
        	this.requestMsgLevel = requestMsgLevel;

            Wrapper.printMessage("anarelationship.constructor", "***", this.requestMsgLevel);

            this.daofactory = daofactory;

        	this.relationshipDAO = daofactory.getRelationshipDAO();
        	this.joinnoderelationshipnodeDAO = daofactory.getJOINNodeRelationshipNodeDAO();
        	this.nodeDAO = daofactory.getNodeDAO();
        	this.relationshipprojectDAO = daofactory.getRelationshipProjectDAO();
            this.componentorderDAO = daofactory.getComponentOrderDAO();
            this.joinnoderelationshiprelationshipprojectDAO = daofactory.getJOINNodeRelationshipRelationshipProjectDAO();
            this.joinrelationshipprojectrelationshipDAO = daofactory.getJOINRelationshipProjectRelationshipDAO();
       	
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
    	
        Wrapper.printMessage("anarelationship.insertANA_RELATIONSHIP:" + calledFrom, "***", this.requestMsgLevel);
        	
        ArrayList<OBOComponent> insertRelObjects = new ArrayList<OBOComponent>();
        
        OBOComponent component;

        String[] orders = null;
        
        boolean flagInsert;
        
        int intREL_OID = 0;
        
        String strREL_RELATIONSHIP_TYPE_FK = "";

        int intREL_CHILD_FK = 0;
        int intREL_PARENT_FK = 0;

        try {
        	
            //get max pk from referenced ana_relationship_project
        	//intMAX_PK = relationshipprojectDAO.maximumOid();

        	if ( !newTermList.isEmpty() ) {
          
                if ( !project.equals("GUDMAP") && !project.equals("EMAP") ) {
                	
                    Wrapper.printMessage("anarelationship.insertANA_RELATIONSHIP -- UNKNOWN Project Value = " + project , "*", this.requestMsgLevel);
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
                        
                        //UPDATED CODE: deleted components are now marked in proposed file as well and appear in the tree under its own root outside abstract anatomy
                        if ( parent.getStatusChange().equals("DELETED") ) {
                            
                            flagInsert = false;
                        }
                        //check whether any rules broken for each parent and print warning
                        //ignore any kind of rule violation for relationship record insertion except missing parent
                        else if ( parent.getStatusRule().equals("FAILED") ) {
                            
                        	flagInsert = true;
                        }
                        
                        //if parent is root Tmp new group don't treat as relationship
                        if ("mouse".equals(strSpecies)) {

                            if ( !parent.getNamespace().equals( abstractclassobocomponent.getNamespace() ) ) {

                                flagInsert = false;
                            }
                        }
                        if ("human".equals(strSpecies)) {

                            if ( !parent.getNamespace().equals( abstractclassobocomponent.getNamespace() ) &&
                            	 !parent.getNamespace().equals( grouptermclassobocomponent.getNamespace() ) &&
                            	 !parent.getNamespace().equals( groupclassobocomponent.getNamespace() ) ) {

                                flagInsert = false;
                            }
                        }
                        if ("chick".equals(strSpecies)) {

                            if ( !parent.getNamespace().equals( abstractclassobocomponent.getNamespace() ) &&
                                 !parent.getNamespace().equals( grouptermclassobocomponent.getNamespace() ) &&
                                 !parent.getNamespace().equals( groupclassobocomponent.getNamespace() ) ) {

                                flagInsert = false;
                            }
                        }

                        //proceed with insertion 
                        if (flagInsert) {
                            
                        	OBOComponent insertRelObject = new OBOComponent();
                            
                            insertRelObject.setID( component.getDBID() ); 
                            
                            String strParentDBID = "";
                            
                            //get DBID for parent 
                            if ( parent.getStatusChange().equals("NEW") ) {
                            	
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
                    AnaObject anaobject = new AnaObject(this.requestMsgLevel, this.daofactory);
                    
                    if ( !anaobject.insertANA_OBJECT( insertRelObjects, "ANA_RELATIONSHIP" ) ) {

                  	   throw new DatabaseException("insertANA_OBJECT for ANA_RELATIONSHIP");
                    }
                    
                    // anaobject add the OIDs to the component list
                    //  need to use these in updates to ananode
                    ArrayList<OBOComponent> updatedNewTermList = anaobject.getUpdatedNewTermList();
                    
                    //INSERT INTO ANA_RELATIONSHIP AND ANA_RELATIONSHIP_PROJECT
                    for ( OBOComponent insertRelObject : updatedNewTermList ) {
                    	
                        intREL_OID = Integer.parseInt( insertRelObject.getDBID() );

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

                        	Wrapper.printMessage("anarelationship.insertANA_RELATIONSHIP -- UNKNOWN Relationship Type = " + insertRelObject.getChildOfTypes().get(0), "*", this.requestMsgLevel);
                        }

                        intREL_CHILD_FK = Integer.parseInt( insertRelObject.getID() );

                        int intRLP_SEQUENCE = 0;
                        
                        if ( !insertRelObject.getOrderComment().equals("") ) {
                        	
                            intRLP_SEQUENCE = Integer.parseInt( insertRelObject.getOrderComment() );
                        }

                        try {
                        	
                            int intTryREL_PARENT_FK = 0;
                            intTryREL_PARENT_FK = Integer.parseInt( insertRelObject.getChildOfs().get(0) );
                            intREL_PARENT_FK = intTryREL_PARENT_FK;
                        }
                        catch(Exception e) {
                        	
                            Wrapper.printMessage("anarelationship.insertANA_RELATIONSHIP -- Exception caught for child " + 
                                    insertRelObject.getID() + " parent " +
                                    insertRelObject.getChildOfs().toString(), "*", this.requestMsgLevel);
                            e.printStackTrace();
                        }
                    	
                        if ( !insertRelObject.getOrderComment().equals("") ) {
                        	
                            intRLP_SEQUENCE = Integer.parseInt( insertRelObject.getOrderComment() );
                        }

                        Relationship relationship = new Relationship((long) intREL_OID, strREL_RELATIONSHIP_TYPE_FK, (long) intREL_CHILD_FK, (long) intREL_PARENT_FK);
                
                        this.relationshipDAO.create(relationship);
                        
                        insertANA_RELATIONSHIP_PROJECT( insertRelObject, intREL_OID, calledFrom );
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
    

    //  Insert into ANA_RELATIONSHIP_PROJECT
    private void insertANA_RELATIONSHIP_PROJECT( OBOComponent newComponent, int relationshipOid, String calledFrom) throws Exception {

        Wrapper.printMessage("anarelationship.insertANA_RELATIONSHIP_PROJECT:" + calledFrom , "***", this.requestMsgLevel);
        	
        ArrayList<String> orderParents = new ArrayList<String>();
        ArrayList<ComponentOrder> componentorders = new ArrayList<ComponentOrder>();
        
        try {
        	
            orderParents = newComponent.getChildOfs();
            
            for ( String orderParent: orderParents ) {
            	
            	componentorders = (ArrayList) this.componentorderDAO.listByChildIdAndParentID( newComponent.getID(), orderParent );

            	if ( componentorders.size() == 1) {
            		
            		ComponentOrder componentorder = componentorders.get(0);
            		
                    int intMAX_PK = this.relationshipprojectDAO.maximumOid();

                    //get max primary key for ana_relationship_project
                    intMAX_PK++; 
                    RelationshipProject relationshipproject1 = new RelationshipProject((long) intMAX_PK, (long) relationshipOid, "EMAP", componentorder.getAlphaorder());
            
                    this.relationshipprojectDAO.create(relationshipproject1);
                    
                    //get max primary key for ana_relationship_project
                    intMAX_PK++; 
                    RelationshipProject relationshipproject2 = new RelationshipProject((long) intMAX_PK, (long) relationshipOid, "GUDMAP", componentorder.getSpecialorder());
                  
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
    }
    
    
    //  Delete from ANA_RELATIONSHIP
    public boolean deleteANA_RELATIONSHIP( ArrayList<OBOComponent> deleteRelComponents, String calledFrom ) throws Exception {

        Wrapper.printMessage("anarelationship.deleteANA_RELATIONSHIP:" + calledFrom , "***", this.requestMsgLevel);
        	
        try {
        	
            if ( !deleteRelComponents.isEmpty() ) {
            	
                for ( OBOComponent deleteRelCompie: deleteRelComponents ) {
                	
                	ArrayList<Relationship> relationships = 
                			(ArrayList<Relationship>) relationshipDAO.listByParentFKAndChildFK(Long.valueOf(deleteRelCompie.getChildOfs().get(0)), Long.valueOf(deleteRelCompie.getID()));

                	Iterator<Relationship> iteratorrelationship = relationships.iterator();

                  	while (iteratorrelationship.hasNext()) {
                  		
                  		Relationship relationship = iteratorrelationship.next();
                  		
                  		relationshipDAO.delete(relationship);
                  	}

                  	ArrayList<RelationshipProject> relationshipprojects = 
                  			(ArrayList<RelationshipProject>) relationshipprojectDAO.listByRelationshipFK(Long.valueOf(deleteRelCompie.getDBID()));
                  	
                  	Iterator<RelationshipProject> iteratorrelationshipproject = relationshipprojects.iterator();

                  	while (iteratorrelationship.hasNext()) {

                  		RelationshipProject relationshipproject = iteratorrelationshipproject.next();
                  		
                  		relationshipprojectDAO.delete(relationshipproject);
                  	}
                }
                
                AnaObject anaobject = new AnaObject(this.requestMsgLevel, this.daofactory);
                
                if ( !anaobject.insertANA_OBJECT( deleteRelComponents, "ANA_RELATIONSHIP" ) ) {

              	   throw new DatabaseException("deleteANA_OBJECT for ANA_RELATIONSHIP");
                }
                
                AnaLog analog = new AnaLog( this.requestMsgLevel, this.daofactory );
                
                //insert Relationships to be deleted in ANA_LOG
                if ( !analog.insertANA_LOG_Relationships( deleteRelComponents, "DELETE" ) ) {

                	throw new DatabaseException("insertANA_LOG_TimedNodes");
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
    
      
    // updateParents
	public boolean updateParents( ArrayList<OBOComponent> changedParentsTermList,  
			String calledFrom, 
    		String project, 
    		String strSpecies, 
    		TreeBuilder treebuilder,
    		OBOComponent abstractclassobocomponent, 
    		OBOComponent stageclassobocomponent, 
    		OBOComponent groupclassobocomponent, 
    		OBOComponent grouptermclassobocomponent) throws Exception {

        Wrapper.printMessage("anarelationship.updateParents:" + calledFrom , "***", this.requestMsgLevel);
        	
        try {

            ArrayList<OBOComponent> deleteRelComponents =
                    new ArrayList<OBOComponent>();
            
            //insert relationships in ANA_RELATIONSHIP
            // 04
            if ( !insertANA_RELATIONSHIP( changedParentsTermList, calledFrom,
            		project,
            		strSpecies, 
            		treebuilder,
            		abstractclassobocomponent, 
            		stageclassobocomponent, 
            		groupclassobocomponent, 
            		grouptermclassobocomponent) ) {

         	   throw new DatabaseException("insertANA_RELATIONSHIP");
            }
            
            //insert relationships to be deleted in ANA_LOG
            AnaLog analog = new AnaLog( this.requestMsgLevel, this.daofactory );
            
            if ( !analog.insertANA_LOG_Relationships( changedParentsTermList, calledFrom ) ) {

            	throw new DatabaseException("insertANA_LOG_deletedRels");
            }
            
            //delete relationships in ANA_RELATIONSHIP
            if ( !deleteANA_RELATIONSHIP( deleteRelComponents, calledFrom) ) {

         	   throw new DatabaseException("deleteANA_RELATIONSHIP");
            }
        }
        catch ( DatabaseException dbex ) {
        	
        	setProcessed( false );
        	dbex.printStackTrace();
        } 
        catch ( Exception ex ) {
        	
        	setProcessed( false );
        	ex.printStackTrace();
        }
        
        return isProcessed();
    }

	
    // update_orderANA_RELATIONSHIP 
	public boolean update_orderANA_RELATIONSHIP( HashMap<String, ArrayList<String>> mapParentChildren, String calledFrom ) throws Exception {

        Wrapper.printMessage("anarelationship.update_orderANA_RELATIONSHIP:" + calledFrom , "***", this.requestMsgLevel);
        	
        String parentPublicId = "";
        
        long longParentDBID = -1;
        long longChildDBID = -1;
        long longREL_OID = -1;
        long longSEQ = -1;
        
        int intNumRecords = 0;

        ArrayList<String> orderedchildren = new ArrayList<String>();

        try {
        	
            if ( !mapParentChildren.isEmpty() ) {
            	
                //for each entry in the map
                for ( Iterator<String> i = mapParentChildren.keySet().iterator(); i.hasNext(); ) {
                    
                	parentPublicId = i.next();
                    //get dbid of parent
                    //parentDBID = getDatabaseIdentifier(parentPublicId);

                    Node node = null;
                    
                	if ( this.nodeDAO.existPublicId( parentPublicId ) ) {

                		node = this.nodeDAO.findByPublicId( parentPublicId );
                    	longParentDBID = node.getOid();
                	}

                    //reset order for each parent
                	longSEQ = -1; 
                    //reset number of child records for each parent
                    intNumRecords = 0; 

                    //get number of child records for each parent from database
                    ArrayList<Relationship> relationships = (ArrayList<Relationship>) relationshipDAO.listByParentFK( longParentDBID );
                    intNumRecords = relationships.size();
                    
                    //iterate through all children for each parent
                    //if children==null, no child has an order in the proposed file
                    //update all relationship entries to sequence=null
                    if ( mapParentChildren.get(parentPublicId) == null ) {
                    	
                      	Iterator<Relationship> iteratorrelationship = relationships.iterator();

                      	while (iteratorrelationship.hasNext()) {
                      		
                      		Relationship relationship = iteratorrelationship.next();

                      		longREL_OID = relationship.getOid();
                            
                            ArrayList<RelationshipProject> relationshipprojects = 
                            		(ArrayList<RelationshipProject>) relationshipprojectDAO.listByRelationshipFK( longREL_OID ); 

                          	Iterator<RelationshipProject> iteratorrelationshipproject = relationshipprojects.iterator();

                          	while (iteratorrelationshipproject.hasNext()) {
                          		
                          		RelationshipProject relationshipproject = iteratorrelationshipproject.next();

                          		relationshipproject.setSequence((long) 0);
                          		relationshipprojectDAO.create(relationshipproject);
                          	}
                      	}
                    }
                    else {
                    	
                        for (String childPublicID: mapParentChildren.get(parentPublicId)) {
                        	
                            //get dbid of child
                            //childDBID = getDatabaseIdentifier(childPublicID);
                            
                        	node = null;
                            
                        	if ( this.nodeDAO.existPublicId( childPublicID ) ) {

                        		node = this.nodeDAO.findByPublicId( childPublicID );
                        		longChildDBID = node.getOid();
                        	}

                            //get rel_oid of this relationship
                            ArrayList<Relationship> relationshipsparentchild = 
                            		(ArrayList<Relationship>) relationshipDAO.listByParentFKAndChildFK( longParentDBID, longChildDBID ); 

                          	Iterator<Relationship> iteratorrelationshipparentchild = relationshipsparentchild.iterator();

                          	while (iteratorrelationshipparentchild.hasNext()) {
                          		
                          		Relationship relationship = iteratorrelationshipparentchild.next();
                          	
                          		longREL_OID = relationship.getOid();

                                //put in ordered children rows cache for later comparison
                                orderedchildren.add( Long.toString(longREL_OID) );
                                longSEQ++;
                                
                                ArrayList<RelationshipProject> relationshipprojects = 
                                		(ArrayList<RelationshipProject>) relationshipprojectDAO.listByRelationshipFK( longREL_OID ); 

                              	Iterator<RelationshipProject> iteratorrelationshipproject = relationshipprojects.iterator();

                              	while (iteratorrelationshipproject.hasNext()) {
                              		
                              		RelationshipProject relationshipproject = iteratorrelationshipproject.next();

                              		relationshipproject.setSequence( longSEQ );
                              		relationshipprojectDAO.create(relationshipproject);
                              	}
                          	}
                        }
                    }
                    
                    //if there are more child records than the number of ordered children
                    // fill up the rest with rel_sequence = null
                    if ( orderedchildren.size()<intNumRecords ) {
                    	
                        //prepare string of ordered rel_oid
                        String strOrdered = "";
                        
                        for(String orderedchild: orderedchildren ) {
                        	
                            strOrdered = strOrdered + orderedchild + ",";
                        }
                        
                        //get all relationship entries that are unordered
                        ArrayList<Relationship> relationshipsunordered = 
                        		(ArrayList<Relationship>) relationshipDAO.listByParentFK(Long.valueOf(longParentDBID));
                        
                      	Iterator<Relationship> iteratorrelationship = relationshipsunordered.iterator();

                      	while (iteratorrelationship.hasNext()) {
                      		
                      		Relationship relationship = iteratorrelationship.next();
                      		
                      		longREL_OID = relationship.getOid().intValue();

                            ArrayList<RelationshipProject> relationshipprojects = 
                            		(ArrayList<RelationshipProject>) relationshipprojectDAO.listByRelationshipFK( longREL_OID ); 

                          	Iterator<RelationshipProject> iteratorrelationshipproject = relationshipprojects.iterator();

                          	while (iteratorrelationshipproject.hasNext()) {
                          		
                          		RelationshipProject relationshipproject = iteratorrelationshipproject.next();

                          		relationshipproject.setSequence((long) 0);
                          		relationshipprojectDAO.create(relationshipproject);
                          	}
                      	}
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


	//  rebuild ANA_RELATIONSHIP
    public boolean rebuildANA_RELATIONSHIP_PROJECT() throws Exception {

        Wrapper.printMessage("anarelationship.rebuildANA_RELATIONSHIP_PROJECT", "***", this.requestMsgLevel);
        	
        ArrayList<ComponentOrder> componentorders = new ArrayList<ComponentOrder>();
        ArrayList<RelationshipProject> relationshipprojects = new ArrayList<RelationshipProject>(); 
        int intMAX_PK = 0;
        
        try {
        	
            relationshipprojects = (ArrayList) this.relationshipprojectDAO.listAll();
        	
        	Iterator<RelationshipProject> iteratorRelationshipProject = relationshipprojects.iterator();

        	while (iteratorRelationshipProject.hasNext()) {
        		
        		RelationshipProject relationshipproject = iteratorRelationshipProject.next();
        		
        		this.relationshipprojectDAO.delete(relationshipproject);
        	}

            ArrayList<JOINNodeRelationshipNode> joinnoderelationships = new ArrayList<JOINNodeRelationshipNode>(); 
            joinnoderelationships = (ArrayList) this.joinnoderelationshipnodeDAO.listAll();
            
        	Iterator<JOINNodeRelationshipNode> iteratorJOINNodeRelationshipNode = joinnoderelationships.iterator();

        	while (iteratorJOINNodeRelationshipNode.hasNext()) {
        		
        		JOINNodeRelationshipNode joinnoderelationship = iteratorJOINNodeRelationshipNode.next();

            	componentorders = (ArrayList) this.componentorderDAO.listByChildIdAndParentID( joinnoderelationship.getAPublicId(), joinnoderelationship.getBPublicId() );

            	if ( componentorders.size() == 1) {
            		
            		ComponentOrder componentorder = componentorders.get(0);
            		
                    //get max primary key for ana_relationship_project
                    intMAX_PK++; 
                    RelationshipProject relationshipproject1 = new RelationshipProject((long) intMAX_PK, 
                    		joinnoderelationship.getOidRelationship(), 
                    		"EMAP", 
                    		componentorder.getAlphaorder());
            
                    this.relationshipprojectDAO.create(relationshipproject1);
                    
                    //get max primary key for ana_relationship_project
                    intMAX_PK++; 
                    RelationshipProject relationshipproject2 = new RelationshipProject((long) intMAX_PK, 
                    		joinnoderelationship.getOidRelationship(), 
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
    
    
    // reorderANA_RELATIONSHIP
    public boolean reorderANA_RELATIONSHIP(ArrayList <OBOComponent> validDeleteTermList, String project, String calledFrom) throws Exception {
         
         // Reorders a collection of siblings that have order sequence entries in the ANA_RELATIONSHIP
         //   when one of the siblings have been deleted, re-ordering is based on original order and closes
         //   the sequence gap left by the deleted sibling
         // NOTE: function is obsolete if editor re-orders the remaining siblings, current rules
         // checking do not allow database to be updated if the ordering has a gap anyway

        Wrapper.printMessage("anarelationship.reorderANA_RELATIONSHIP", "***", this.requestMsgLevel);

        ArrayList <String> componentParents = new ArrayList<String>();

        String skipRecords = "";
        
        long longParentDBID = -1;
        long longChildDBID = -1;
        long longSEQ = -1;
        
        try {
        	
            if ( !validDeleteTermList.isEmpty() ) {

            	//for each component to be deleted
                for (OBOComponent component: validDeleteTermList) {

                	//get all parent-child relationship entries with ordering
                    Node node = null;
                    
                	if ( this.nodeDAO.existPublicId( component.getID() ) ) {

                		node = this.nodeDAO.findByPublicId( component.getID() );
                		longChildDBID = node.getOid();
                	}

                    skipRecords = "";

                    ArrayList<JOINNodeRelationshipRelationshipProject> joinnrrps = 
                    		(ArrayList<JOINNodeRelationshipRelationshipProject>) joinnoderelationshiprelationshipprojectDAO.listAllByChildFK( longChildDBID );
                  	
                    Iterator<JOINNodeRelationshipRelationshipProject> iteratorjoinnrrps = joinnrrps.iterator();

                  	while (iteratorjoinnrrps.hasNext()) {
                  		JOINNodeRelationshipRelationshipProject joinnrrp = iteratorjoinnrrps.next();
                  		
                        if ( joinnrrp.getSequenceFK() != null ) {
                        	
                            componentParents.add( joinnrrp.getPublicId() );
                            
                            //make a list of parent-child relationship entries to be deleted
                            skipRecords = skipRecords + joinnrrp.getOidRelationshipProject().intValue() + ",";
                        }
                  	}
                }

                if (!skipRecords.equals("")) {
                	
                    skipRecords = skipRecords.substring(0, skipRecords.length() - 1);
                    skipRecords = "(" + skipRecords + ")";
                }
                
                //check for each parent whether there is ordering
                for (String parent: componentParents) {
                    
                	//get parent dbid
                    Node node = null;

                	if ( this.nodeDAO.existPublicId( parent ) ) {

                		node = this.nodeDAO.findByPublicId( parent );
                		longParentDBID = node.getOid();
                	}

                    //reset REL_SEQ
                	longSEQ = 0;
                	
                    //get all children records from ANA_RELATIONSHIP_PROJECT for this parent
                    //that has an order sequence entry, order by sequence
                    //exclude entries that are scheduled for deletion
                    
                    ArrayList<JOINRelationshipProjectRelationship> joinrprs = 
                    		(ArrayList<JOINRelationshipProjectRelationship>) joinrelationshipprojectrelationshipDAO.listAllByParentAndProjectNotIn( longParentDBID, project, skipRecords);
                    
                    Iterator<JOINRelationshipProjectRelationship> iteratorjoinrprs = joinrprs.iterator();

                  	while (iteratorjoinrprs.hasNext()) {
                  		
                  		JOINRelationshipProjectRelationship joinrpr = iteratorjoinrprs.next();
                  		
                  		RelationshipProject relationshipproject = relationshipprojectDAO.findByOid(joinrpr.getOidRelationshipProject());
                  		
                  		relationshipproject.setSequence( longSEQ );
                  		relationshipprojectDAO.create(relationshipproject);

                        //increment rel_seq for next record
                  		longSEQ++;
                  	}
                }
            }
        }
        catch ( DAOException daoex ) {
        	
        	setProcessed( false );
        	daoex.printStackTrace();
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
