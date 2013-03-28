/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyRebuild
*
* Title:        RunExportToXML.java
*
* Date:         2012
*
* Author:       Mike Wicks
*
* Copyright:    2012
*               Medical Research Council, UK.
*               All rights reserved.
*
* Address:      MRC Human Genetics Unit,
*               Western General Hospital,
*               Edinburgh, EH4 2XU, UK.
*
* Version: 1
*
* Description:  A Main Class that exports the anatomy database to GraphML Format (XML)
*
* Maintenance:  Log changes below, with most recent at top of list.
*
* Who; When; What;
*
* Mike Wicks; November 2012; Create Class
*
*----------------------------------------------------------------------------------------------
*/
package app;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import obolayer.OBOFactory;

import daolayer.DAOException;
import daolayer.DAOFactory;

import daointerface.NodeDAO;
import daointerface.RelationshipDAO;

import daomodel.Node;
import daomodel.Relationship;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
 
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import utility.Wrapper;

public class RunExportToXML {

	public static void run ( String requestMsgLevel, OBOFactory obofactory, DAOFactory daofactory ) {
    	
    	try {
            // Obtain DAOs.
            NodeDAO nodeDAO = daofactory.getDAOImpl(NodeDAO.class);
            RelationshipDAO relationshipDAO = daofactory.getDAOImpl(RelationshipDAO.class);

            int i = 0;

            List<Node> nodes = new ArrayList<Node>();
            nodes = nodeDAO.listAll();
            Iterator<Node> iteratorNode = nodes.iterator();

            List<Relationship> relationships = new ArrayList<Relationship>();
            relationships = relationshipDAO.listAll();
            Iterator<Relationship> iteratorRelationship = relationships.iterator();
          	
    		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
     
    		// root element
    		Document doc = docBuilder.newDocument();
    		Element rootElement = doc.createElement("graphml");
    		doc.appendChild(rootElement);
     
    		// key elements
    		Element nodeKey1 = doc.createElement("key");
    		rootElement.appendChild(nodeKey1);
    		// set nodeAttribute to key1 element
    		Attr nodeAttr1 = doc.createAttribute("for");
    		nodeAttr1.setValue("node");
    		nodeKey1.setAttributeNode(nodeAttr1);
    		nodeAttr1 = doc.createAttribute("id");
    		nodeAttr1.setValue("ANO_OID");
    		nodeKey1.setAttributeNode(nodeAttr1);
    		nodeAttr1 = doc.createAttribute("attr.name");
    		nodeAttr1.setValue("ANO_OID");
    		nodeKey1.setAttributeNode(nodeAttr1);
    		nodeAttr1 = doc.createAttribute("attr.type");
    		nodeAttr1.setValue("int");
    		nodeKey1.setAttributeNode(nodeAttr1);
    		// key elements
    		Element nodeKey2 = doc.createElement("key");
    		rootElement.appendChild(nodeKey2);
    		// set nodeAttribute to key2 element
    		Attr nodeAttr2 = doc.createAttribute("for");
    		nodeAttr2.setValue("node");
    		nodeKey2.setAttributeNode(nodeAttr2);
    		nodeAttr2 = doc.createAttribute("id");
    		nodeAttr2.setValue("ANO_SPECIES_FK");
    		nodeKey2.setAttributeNode(nodeAttr2);
    		nodeAttr2 = doc.createAttribute("attr.name");
    		nodeAttr2.setValue("ANO_SPECIES_FK");
    		nodeKey2.setAttributeNode(nodeAttr2);
    		nodeAttr2 = doc.createAttribute("attr.type");
    		nodeAttr2.setValue("string");
    		nodeKey2.setAttributeNode(nodeAttr2);
    		// key elements
    		Element nodeKey3 = doc.createElement("key");
    		rootElement.appendChild(nodeKey3 );
    		// set nodeAttribute to key1 element
    		Attr nodeAttr3 = doc.createAttribute("for");
    		nodeAttr3.setValue("node");
    		nodeKey3.setAttributeNode(nodeAttr3);
    		nodeAttr3 = doc.createAttribute("id");
    		nodeAttr3.setValue("ANO_COMPONENT_NAME");
    		nodeKey3.setAttributeNode(nodeAttr3);
    		nodeAttr3 = doc.createAttribute("attr.name");
    		nodeAttr3.setValue("ANO_COMPONENT_NAME");
    		nodeKey3.setAttributeNode(nodeAttr3);
    		nodeAttr3 = doc.createAttribute("attr.type");
    		nodeAttr3.setValue("string");
    		nodeKey3.setAttributeNode(nodeAttr3);
    		// key elements
    		Element nodeKey4 = doc.createElement("key");
    		rootElement.appendChild(nodeKey4);
    		// set nodeAttribute to key1 element
    		Attr nodeAttr4 = doc.createAttribute("for");
    		nodeAttr4.setValue("node");
    		nodeKey4.setAttributeNode(nodeAttr4);
    		nodeAttr4 = doc.createAttribute("id");
    		nodeAttr4.setValue("ANO_IS_PRIMARY");
    		nodeKey4.setAttributeNode(nodeAttr4);
    		nodeAttr4 = doc.createAttribute("attr.name");
    		nodeAttr4.setValue("ANO_IS_PRIMARY");
    		nodeKey4.setAttributeNode(nodeAttr4);
    		nodeAttr4 = doc.createAttribute("attr.type");
    		nodeAttr4.setValue("int");
    		nodeKey4.setAttributeNode(nodeAttr4);
    		// key elements
    		Element nodeKey5 = doc.createElement("key");
    		rootElement.appendChild(nodeKey5);
    		// set nodeAttribute to key1 element
    		Attr nodeAttr5 = doc.createAttribute("for");
    		nodeAttr5.setValue("node");
    		nodeKey5.setAttributeNode(nodeAttr5);
    		nodeAttr5 = doc.createAttribute("id");
    		nodeAttr5.setValue("ANO_IS_GROUP");
    		nodeKey5.setAttributeNode(nodeAttr5);
    		nodeAttr5 = doc.createAttribute("attr.name");
    		nodeAttr5.setValue("ANO_IS_GROUP");
    		nodeKey5.setAttributeNode(nodeAttr5);
    		nodeAttr5 = doc.createAttribute("attr.type");
    		nodeAttr5.setValue("int");
    		nodeKey5.setAttributeNode(nodeAttr5);
    		// key elements
    		Element nodeKey6 = doc.createElement("key");
    		rootElement.appendChild(nodeKey6);
    		// set nodeAttribute to key1 element
    		Attr nodeAttr6 = doc.createAttribute("for");
    		nodeAttr6.setValue("node");
    		nodeKey6.setAttributeNode(nodeAttr6);
    		nodeAttr6 = doc.createAttribute("id");
    		nodeAttr6.setValue("ANO_PUBLIC_ID");
    		nodeKey6.setAttributeNode(nodeAttr6);
    		nodeAttr6 = doc.createAttribute("attr.name");
    		nodeAttr6.setValue("ANO_PUBLIC_ID");
    		nodeKey6.setAttributeNode(nodeAttr6);
    		nodeAttr6 = doc.createAttribute("attr.type");
    		nodeAttr6.setValue("string");
    		nodeKey6.setAttributeNode(nodeAttr6);
    		// key elements
    		Element nodeKey7 = doc.createElement("key");
    		rootElement.appendChild(nodeKey7);
    		// set nodeAttribute to key1 element
    		Attr nodeAttr7 = doc.createAttribute("for");
    		nodeAttr7.setValue("node");
    		nodeKey7.setAttributeNode(nodeAttr7);
    		nodeAttr7 = doc.createAttribute("id");
    		nodeAttr7.setValue("ANO_DESCRIPTION");
    		nodeKey7.setAttributeNode(nodeAttr7);
    		nodeAttr7 = doc.createAttribute("attr.name");
    		nodeAttr7.setValue("ANO_DESCRIPTION");
    		nodeKey7.setAttributeNode(nodeAttr7);
    		nodeAttr7 = doc.createAttribute("attr.type");
    		nodeAttr7.setValue("string");
    		nodeKey7.setAttributeNode(nodeAttr7);

    		// graph element
    		Element graphElement = doc.createElement("graph");
    		rootElement.appendChild(graphElement);
    		// set graphAttr1 to graph element
    		Attr graphAttr1 = doc.createAttribute("edgedefault");
    		graphAttr1.setValue("directed");
    		graphElement.setAttributeNode(graphAttr1);
    		graphAttr1 = doc.createAttribute("id");
    		graphAttr1.setValue("G");
    		graphElement.setAttributeNode(graphAttr1);

            while (iteratorNode.hasNext()) {
            	
            	Node node = iteratorNode.next();

        		// node element
        		Element nodeElement = doc.createElement("node");
        		graphElement.appendChild(nodeElement);

        		//Attr nodeElementAttr1 = doc.createAttribute("ANO_OID");
        		Attr nodeElementAttr1 = doc.createAttribute("id");
        		nodeElementAttr1.setValue(node.getOid().toString());
        		nodeElement.setAttributeNode(nodeElementAttr1);
        		
        		// data element
        		Element dataElement1 = doc.createElement("data");
        		dataElement1.setTextContent(node.getSpeciesFK());
        		nodeElement.appendChild(dataElement1);
        		
        		Attr dataElementAttr = doc.createAttribute("key");
        		dataElementAttr.setValue("ANO_SPECIES_FK");
        		dataElement1.setAttributeNode(dataElementAttr);

        		// data element
        		Element dataElement2 = doc.createElement("data");
        		dataElement2.setTextContent(node.getComponentName());
        		nodeElement.appendChild(dataElement2);
        		
        		dataElementAttr = doc.createAttribute("key");
        		dataElementAttr.setValue("ANO_COMPONENT_NAME");
        		dataElement2.setAttributeNode(dataElementAttr);
        		
        		// data element
        		Element dataElement3 = doc.createElement("data");
        		dataElement3.setTextContent(((Integer) node.getPrimary()).toString());
        		nodeElement.appendChild(dataElement3);
        		
        		dataElementAttr = doc.createAttribute("key");
        		dataElementAttr.setValue("ANO_IS_PRIMARY");
        		dataElement3.setAttributeNode(dataElementAttr);
        		
        		// data element
        		Element dataElement4 = doc.createElement("data");
        		dataElement4.setTextContent(((Integer) node.getGroup()).toString());
        		nodeElement.appendChild(dataElement4);
        		
        		dataElementAttr = doc.createAttribute("key");
        		dataElementAttr.setValue("ANO_IS_GROUP");
        		dataElement4.setAttributeNode(dataElementAttr);

        		// data element
        		Element dataElement5 = doc.createElement("data");
        		dataElement5.setTextContent(node.getPublicId());
        		nodeElement.appendChild(dataElement5);
        		
        		dataElementAttr = doc.createAttribute("key");
        		dataElementAttr.setValue("ANO_PUBLIC_ID");
        		dataElement5.setAttributeNode(dataElementAttr);

        		// data element
        		Element dataElement6 = doc.createElement("data");
        		dataElement6.setTextContent(node.getDescription());
        		nodeElement.appendChild(dataElement6);
        		
        		dataElementAttr = doc.createAttribute("key");
        		dataElementAttr.setValue("ANO_DESCRIPTION");
        		dataElement6.setAttributeNode(dataElementAttr);
        		
            }

            while (iteratorRelationship.hasNext()) {
            	
            	Relationship relationship = iteratorRelationship.next();

        		// edge element
        		Element edgeElement = doc.createElement("edge");
        		graphElement.appendChild(edgeElement);

        		Attr edgeElementAttr1 = doc.createAttribute("id");
        		edgeElementAttr1.setValue(relationship.getOid().toString());
        		edgeElement.setAttributeNode(edgeElementAttr1);
        		
        		edgeElementAttr1 = doc.createAttribute("source");
        		edgeElementAttr1.setValue(Long.toString(relationship.getParentFK()));
        		edgeElement.setAttributeNode(edgeElementAttr1);
        		
        		edgeElementAttr1 = doc.createAttribute("target");
        		edgeElementAttr1.setValue(Long.toString(relationship.getChildFK()));
        		edgeElement.setAttributeNode(edgeElementAttr1);
        		
        		edgeElementAttr1 = doc.createAttribute("label");
        		edgeElementAttr1.setValue(relationship.getTypeFK());
        		edgeElement.setAttributeNode(edgeElementAttr1);
        		
            }

    		// write the content into xml file
    		TransformerFactory transformerFactory = TransformerFactory.newInstance();
    		Transformer transformer = transformerFactory.newTransformer();
    		DOMSource source = new DOMSource(doc);
    		StreamResult result = new StreamResult(new File("/Users/mwicks/Desktop/mouse010.xml"));
     
    		// Output to console for testing
    		// StreamResult result = new StreamResult(System.out);
     
    		transformer.transform(source, result);
     
    	    Wrapper.printMessage("File saved from RunExportToXML!", "***", requestMsgLevel);
    	}
		catch (DAOException daoexception) {
			
			daoexception.printStackTrace();
		}
    	catch (ParserConfigurationException pce) {
    		
    		pce.printStackTrace();
    	}
    	catch (TransformerException tfe) {
    		
    		tfe.printStackTrace();
    	}
    	catch (Exception ex) {
    		
    		ex.printStackTrace();
    	}
    }
}
