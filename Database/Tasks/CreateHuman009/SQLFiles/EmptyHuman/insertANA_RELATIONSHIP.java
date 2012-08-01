    // 04
    //  Insert into ANA_RELATIONSHIP
    private void insertANA_RELATIONSHIP( ArrayList<OBOComponent> newTermList,
            String calledFrom) {
    	/*
    	 *  ANA_RELATIONSHIP
    	 *  Columns:
         *   1. REL_OID                  - int(10) unsigned 
         *   2. REL_RELATIONSHIP_TYPE_FK - varchar(20)      
         *   3. REL_CHILD_FK             - int(10) unsigned 
         *   4. REL_PARENT_FK            - int(10) unsigned 
    	 */
        if (debug) {
        	
            System.out.println("04 - insertANA_RELATIONSHIP - called from = " + calledFrom);
        }

        System.out.println("04 - insertANA_RELATIONSHIP - called from = " + calledFrom);
        System.out.println("newTermList.size() = " + newTermList.size());
        
        ArrayList<OBOComponent> insertRelObjects = new ArrayList<OBOComponent>();
        OBOComponent component;

        String[] orders = null;
        
        int intMAX_PK = 0;
        
        boolean flagInsert;
        
        String project2 = "";
        
        int intREL_OID = 0;
        String strREL_RELATIONSHIP_TYPE_FK = "";
        int intREL_CHILD_FK = 0;
        int intREL_PARENT_FK = 0;

        int intRLP_SEQUENCE = -1;

        try {
        	
            //get max pk from referenced ana_relationship_project
        	//intMAX_PK = relationshipprojectDAO.maximumOid();

        	if ( !newTermList.isEmpty() ) {
          
                if ( project.equals("GUDMAP") ) {
                	
                    project2 = "EMAP";
                }
                else if ( project.equals("EMAP") ) {
                	
                    project2 = "GUDMAP";
                }
                else {
                	
                    System.out.println("UNKNOWN Project Value = " + project);
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
                        OBOComponent parent = (OBOComponent) tree.getComponent( parents.get(j) );

                        String strParentType = "";
                        strParentType = parentTypes.get(j);

                        //check whether parent has been deleted from obo file, do not allow insertion
                        if ( parent == null ) {
                        	
                             flagInsert = false;
                        }
                        else {
                        	
                        	ArrayList<JOINNodeRelationshipNode> joinnoderelationshipnodes = 
                        			(ArrayList<JOINNodeRelationshipNode>) joinnoderelationshipnodeDAO.listAllByChildIdAndParentId(component.getID(), parent.getID());

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
                        else if ( !parent.getNamespace().equals( abstractclassobocomponent.getNamespace() ) ) {
                            
                        	flagInsert = false;
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
                            //if component is not new 
                            else {
                            	
                            	Node node = null;
                                
                            	if ( nodeDAO.existPublicId(parent.getID()) ) {

                            		node = nodeDAO.findByPublicId(parent.getID());
                                	strParentDBID = Long.toString(node.getOid());
                            	}
                            	else {
                                	
                            		node = null;
                                	strParentDBID = "0";
                            	}
                            }

                            //get order for child based on parent
                            orders = component.getOrderCommentOnParent( parent.getID() );
                            
                            if ( orders!=null ) {
                            	
                                String[] arrayFirstWord = orders[0].split(" ");
                                insertRelObject.setOrderComment(arrayFirstWord[0]);
                            }
                            else {
                            	
                                insertRelObject.setOrderComment("");
                            }

                            insertRelObject.addChildOf( strParentDBID ); //parent dbid
                            insertRelObject.addChildOfType( strParentType ); //parent dbid

                            insertRelObjects.add( insertRelObject );
                        }
                    }
                }
                // END OF "for ( int i = 0; i< newTermList.size(); i++)"

                System.out.println("insertRelObjects.size() = " + insertRelObjects.size());

                //INSERT INTO ANA_RELATIONSHIP
                if ( !insertRelObjects.isEmpty() ) {
                	
                    //INSERT INTO ANA_OBJECT and set DBIDs
                    insertANA_OBJECT( insertRelObjects, "ANA_RELATIONSHIP" );
                    
                    //INSERT INTO ANA_RELATIONSHIP AND ANA_RELATIONSHIP_PROJECT
                    for ( OBOComponent insertRelObject : insertRelObjects ) {
                    	
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
                        /*
                        else if ( insertRelObject.getChildOfTypes().get(0).equals("SURROUNDS")) {
                        	
                            strREL_RELATIONSHIP_TYPE_FK = "surrounds";
                        }
                        */
                        else if ( insertRelObject.getChildOfTypes().get(0).equals("ATTACHED_TO")) {
                        	
                            strREL_RELATIONSHIP_TYPE_FK = "attached-to";
                        }
                        else if ( insertRelObject.getChildOfTypes().get(0).equals("HAS_PART")) {
                        	
                            strREL_RELATIONSHIP_TYPE_FK = "has-part";
                        }
                        else {
                            System.out.println("UNKNOWN Relationship Type = " + insertRelObject.getChildOfTypes().get(0));
                        }

                        intREL_CHILD_FK = Integer.parseInt( insertRelObject.getID() );
                        //intRLP_SEQUENCE = -1;
                        intRLP_SEQUENCE = 0;
                        
                        if ( !insertRelObject.getOrderComment().equals("") ) {
                        	
                            intRLP_SEQUENCE = Integer.parseInt( insertRelObject.getOrderComment() );
                        }

                        try {
                        	
                            int intTryREL_PARENT_FK = 0;
                            intTryREL_PARENT_FK = Integer.parseInt( insertRelObject.getChildOfs().get(0) );
                            intREL_PARENT_FK = intTryREL_PARENT_FK;
                        }
                        catch(Exception e) {
                        	
                            System.out.println("Exception caught for child " + 
                                insertRelObject.getID() + " parent " +
                                insertRelObject.getChildOfs().toString() );
                            e.printStackTrace();
                        }
                    	
                        if ( !insertRelObject.getOrderComment().equals("") ) {
                        	
                            intRLP_SEQUENCE = Integer.parseInt( insertRelObject.getOrderComment() );
                        }

                        Relationship relationship = new Relationship((long) intREL_OID, strREL_RELATIONSHIP_TYPE_FK, (long) intREL_CHILD_FK, (long) intREL_PARENT_FK);
                
                        System.out.println("insertANA_RELATIONSHIP = " + relationship.toString());
                        
                        relationshipDAO.create(relationship);
                        
                        //insertANA_RELATIONSHIP_PROJECT( insertRelObject, intREL_OID, calledFrom );
                    }
                }
            }
        }
        catch ( DAOException dao ) {
        	
        	setProcessed(false);
            dao.printStackTrace();
        }
        catch ( Exception ex ) {
        	
        	setProcessed(false);
            ex.printStackTrace();
        }
    }
    
