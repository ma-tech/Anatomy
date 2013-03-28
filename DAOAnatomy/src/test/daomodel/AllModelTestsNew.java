package test.daomodel;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
 
@RunWith (Suite.class)
@SuiteClasses (
       {
    	   ComponentTest.class,
    	   ComponentOrderTest.class,
    	   ComponentRelationshipTest.class,
    	   ComponentSynonymTest.class,
    	   EditorTest.class,
    	   ExtraTimedNodeTest.class,
    	   NodeTest.class,
    	   OBOFileTest.class,
    	   PerspectiveAmbitTest.class,
    	   RelationshipTest.class,
    	   RelationshipProjectTest.class,
    	   SourceTest.class,
    	   StageTest.class,
    	   SynonymTest.class,
    	   ThingTest.class,
    	   TimedNodeTest.class,
    	   UserTest.class,
    	   VersionTest.class,
        }
    )

public class AllModelTestsNew {
    // empty class, no properties, no methods, nothing!!
}