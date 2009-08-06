package backend;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



import java.io.IOException;
import java.util.*;
import org.geneontology.oboedit.dataadapter.DefaultOBOParser;
import org.geneontology.oboedit.dataadapter.OBOParseEngine;
import org.geneontology.oboedit.dataadapter.OBOParseException;
import org.geneontology.oboedit.datamodel.OBOSession;
import org.geneontology.oboedit.datamodel.impl.OBOClassImpl;
import org.geneontology.oboedit.datamodel.impl.OBORestrictionImpl;

/**
 *
 * @author Maze Lam
 */
public class OBOParser {

    private String strFile;
    private String strRelationship;
    //private String strRootComponent;
    
    private HashMap< String, String > treeProperty; //property map to tree
    private HashMap< String, Vector<String> > treeChildren; //children map to tree
    private ArrayList< Component > termList; //instantiated components
    
    public OBOParser(String txtFileName){
        this.strFile = txtFileName.trim();
        this.strRelationship = "part_of";
        //this.strRootComponent = "EMAPA:25765";
        
        this.treeProperty = new HashMap();
        this.treeChildren = new HashMap();
        this.termList = new ArrayList< Component >();
        //mapComponents();
        createComponents();

    }
    
    public static OBOSession getSession(String path) throws IOException {
	DefaultOBOParser parser = new DefaultOBOParser();
	OBOParseEngine engine = new OBOParseEngine(parser);
	// GOBOParseEngine can parse several files at once
	// and create one munged-together ontology,
	// so we need to provide a Collection to the setPaths() method
	Collection paths = new LinkedList();
	paths.add(path);
	engine.setPaths(paths);
        try{
            engine.parse();
        }
        catch(OBOParseException e){}
        catch(java.io.FileNotFoundException io){};
	OBOSession session = parser.getSession();
	return session;
    }
    
    
    private void createComponents(){
        
        try{
            OBOSession obo_session = getSession(this.strFile);
            //all terms in a map
            Map obo_map = obo_session.getAllTermsHash();
            //System.out.println("Roots: " + obo_session.getRoots());
            //System.out.println("Relations: " + obo_session.getRelationshipTypes());
            //System.out.println("No of terfms in obo_map: " + obo_map.size());
            
            //iterate through each term in the map
            for(Iterator i = obo_map.keySet().iterator(); i.hasNext();  ){

                OBOClassImpl node = (OBOClassImpl) obo_map.get(i.next());
                
                //remove 4 default extra terms in obo_map created by obo_session.getAllTermsHash()
                if(!node.getID().startsWith("obo")){
                    //intTestComponent++;
                    Component term = new Component();

                    //set component properties (strings)
                    term.setName(node.toString());
                    term.setID(node.getID());
                    term.setNamespace(node.getNamespace().toString());
                    //get comments
                    term.setCheckComment( node.getComment() );
                    term.addUserComments( node.getComment() );
                    
                    //set component synonyms (arraylist)
                    for(Iterator k = node.getSynonyms().iterator(); k.hasNext(); ){
                        term.addSynonym(k.next().toString());
                    }

                    //set component relationships
                    //parents(arraylist), startsat(string Tsxx), endsat(TSxx)
                    //note: j is one relationship, child = node, parent = relationship postfix
                    for(Iterator j = node.getParents().iterator(); j.hasNext();  ){
                        OBORestrictionImpl rel = (OBORestrictionImpl) j.next();
                        
                                
                        if (rel.getType().getID().equals("part_of")){
                            term.addPartOf(rel.getParent().getID());
                            //System.out.println("Comp.parent: " + term.getPartOf());
                        }
                        else if (rel.getType().getID().equals("group_part_of")){
                            term.addGroupPartOf(rel.getParent().getID());
                            term.setIsPrimary(false);
                        }
                        else if (rel.getType().getID().equals("starts_at")){
                            term.setStartsAt(rel.getParent().getID());
                        }
                        else if (rel.getType().getID().equals("ends_at")){
                            term.setEndsAt(rel.getParent().getID());
                        }
                        else if (rel.getType().getID().equals("OBO_REL:is_a")){
                            term.setIsA(rel.getParent().getID());
                        }
                    }

                    //pass back to class properties
                    //if ( term.getID().startsWith("EMAPA") && term.getPartOf().isEmpty() ){
                    //    System.out.println(term.getID() + " has no parents");
                    //}
                    
                    //System.out.println("terms with undefined rels = " + counterRel);
                    this.termList.add(term);
                    
                }
                //test see whether getalltermshash include obsolete terms
                if (node.isObsolete()) System.out.println("is obsolste: " + node.getID() + node.getName());
            }
            System.out.println(termList.size() + " Components instantiated!");
            
            //get obsolete terms
            Set obsoTerms = obo_session.getObsoleteObjects();
            /*
            for( Iterator k = obsoTerms.iterator(); k.hasNext(); ){
                Object o = (Object) k.next();
                if (o instanceof OBOClassImpl){
                    OBOClassImpl obsoTerm = (OBOClassImpl) o;
                    System.out.println("is an oboclassimpl!");
                }
            }*/ 
            for( Iterator m = obsoTerms.iterator(); m.hasNext(); ){

                OBOClassImpl node = (OBOClassImpl) m.next();
                 Component term = new Component();
                 //set component properties (strings)
                 term.setName(node.toString());
                 term.setID(node.getID());
                 term.setNamespace(node.getNamespace().toString());
                 //set synonyms (not string in oboparser)
                 for(Iterator n = node.getSynonyms().iterator(); n.hasNext(); ){
                    term.addSynonym(n.next().toString());
                 }
                 term.setCheckComment("INFO: Obsolete Term");
                 term.setStrChangeStatus("DELETED");
                 this.termList.add(term);
  
            } 
            
            
 
        }
        catch(Exception e){
            e.printStackTrace();
        }
        

        /*
        System.out.println("Starting iterator for testing");
        System.out.println("Looking for component EMAPA:30624"); //EMAPA:29907//RAB:0001000//EMAPA:30759 //ID:0000000 //CS:0//EMAPA:16039 //EMAPA:16037
        for(Iterator i = this.termList.iterator(); i.hasNext(); ){
            Component comp = (Component) i.next();
            if(comp.getID().equals("EMAPA:30624")){ //EMAPA:29907//RAB:0001000//CS:0//EMAPA:16039//EMAPA:16037
                System.out.println("Term name: " + comp.getName());
                System.out.println("Term ID: " + comp.getID());
                System.out.println("Term Namespace: " + comp.getNamespace());
                System.out.println("Part of: " + comp.getPartOf());
                System.out.println("Starts at: " + comp.getStartsAt());
                System.out.println("Ends at: " + comp.getEndsAt());
                System.out.println("Synonyms: " + comp.getSynonym());
                System.out.println("Is A: " + comp.getIsA());
                System.out.println("Int starts at: " + comp.getIntStartsAt());
                System.out.println("Int ends at: " + comp.getIntEndsAt());
         
            }
        }*/
    }
    
    
    public String getFile(){
        return this.strFile;
    }
    
    public ArrayList getComponents(){
        return this.termList;
    }
    
    
    
    
    
}


