package App;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import OBOModel.ComponentFile;

import OBOLayer.OBOFactory;
import OBOLayer.OBOException;

import OBOLayer.ComponentOBO;

public class RunOBOTest {
	
	public static void run () {

		try {
	        // Obtain DAOFactory.
	        OBOFactory obofactory = OBOFactory.getInstance("file");
	        //System.out.println("OBOFactory successfully obtained: " + obofactory);

	        // Obtain DAOs.
	        ComponentOBO componentOBO = obofactory.getComponentOBO();
	        //System.out.println("ComponentOBO successfully obtained: " + componentOBO);

	        // Read in Obo File
	        List<ComponentFile> obocomponents = new ArrayList<ComponentFile>();
	        obocomponents = componentOBO.listAll();
	        /*
	        if (componentOBO.debug()) {
	        	Iterator<ComponentFile> iterator = obocomponents.iterator();

	        	while (iterator.hasNext()) {
	        		ComponentFile obocomponent = iterator.next();
	                System.out.println(obocomponent.toString());
	        	}
	        }
	        */
            System.out.println("Number of File Components Read In = " + Integer.toString(obocomponents.size()));

            //System.out.println("componentOBO.inputFileRemark() = " + componentOBO.inputFileRemark());

	        // Write out Obo File
	        componentOBO.setComponentFileList((ArrayList) obocomponents);
	        componentOBO.createTemplateRelationList();
	        
	        Boolean isProcessed = componentOBO.writeAll();

	        if (isProcessed) {
	            System.out.println("Obo File SUCCESSFULLY written to " + componentOBO.outputFile());
	        }
	        else {
	            System.out.println("Obo File FAILED to write to " + componentOBO.outputFile());
	        }
	        
		}
		catch (OBOException oboexception) {
			oboexception.printStackTrace();
		}

	}
    
}
