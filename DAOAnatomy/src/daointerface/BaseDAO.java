/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomy
*
* Title:        BaseDAO.java
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
* Version:      1
*
* Description:  This interface represents a Base Interface which all the others then extend.
*  
*               This DAO should be used as a central point for the mapping between 
*                the ComponentAlternative DTO and a SQL database.
*
* Link:         
* 
* Maintenance:  Log changes below, with most recent at top of list.
*
* Who; When; What;
*
* Mike Wicks; 21st March 2012; Create Class
*
*----------------------------------------------------------------------------------------------
*/
package daointerface;

import daolayer.DAOFactory;

public interface BaseDAO {

	public void setDAOFactory(DAOFactory daofactory);
	
}
