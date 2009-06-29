package backend;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author attila
 */
public class Relation {
    private String id;
    private String name;
    private String transitive;
    
    public Relation() {
        this.id = "";
        this.name = "";
        this.transitive = "";
    }
    
    public void setID( String id ) {
        this.id = id;
    }
    
    public void setName( String name ) {
        this.name = name;
    }

    public void setTransitive( String transitive ) {
        this.transitive = transitive;
    }

    public String getID() {
        return this.id;
    }
    
    public String getName() {
        return this.name;
    }

    public String getTransitive() {
        return this.transitive;
    }
    
}
