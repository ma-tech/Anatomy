<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<f:view>
  <html xmlns="http://www.w3.org/1999/xhtml">


<head>
  <title>ANAD_PART_OF_PERSPECTIVES with FOREIGN KEYS</title>
 
  <link type="text/css" rel="stylesheet" href="./css/global.css">

  <script type="text/javascript" src="./scripts/jsTree.v.1.0rc2/_lib/jquery.js"></script>
  <script type="text/javascript" src="./scripts/jsTree.v.1.0rc2/_lib/jquery.hotkeys.js"></script>
  <script type="text/javascript" src="./scripts/jsTree.v.1.0rc2/jquery.cookie.js"></script>
  <script type="text/javascript" src="./scripts/jsTree.v.1.0rc2/jquery.jstree.js"></script>
  <script type="text/javascript" src="./scripts/jsTree.v.1.0rc2/jquery.jstree.extra.js"></script>

  <script type="text/javascript" src="./scripts/global.js"></script>
  <script type="text/javascript" src="./scripts/buildPaths.js"></script>
  
</head>

<body>

  <h:form id="form">

    <h1>Anatomy - ANAD_PART_OF_PERSPECTIVES, JSON Tree Only</h1>
   
    <fieldset>
  
    <p><a href="index.jsf">BACK</a></p>
  
    <legend>Browse Derived Part Of Perspectives ( plus Foreign Keys)</legend>
  
    <h2>STAGE
        <h:outputText value="#{DerivedPartOfPerspectivesFKBackingBean.theilerStage}" />
    </h2>
    

    <%-- The sortable datatable 
                             ANAD_PART_OF
                             
         Fields:     
         1.  CONCAT(a.APO_FULL_PATH_JSON_HEAD, a.APO_FULL_PATH_JSON_TAIL) AS FULL_PATH_JSON
                   => fullPathJson
    --%>
    
    <h:outputLabel for="searchTerm" 
                   value="Enter a Full or Partial Node Name or a Full or Partial Node Name or EMAP/EMAPA Id Number:" />
    <%-- Set Search Term --%>
    <h:inputText id="searchTerm" 
                 value="#{DerivedPartOfPerspectivesFKBackingBean.searchTerm}" 
                 size="50"
                 maxlength="50" />
    <br />
    <br />
    <br />
    <br />
    <br />

    <h:commandButton value="Search" 
                     action="#{DerivedPartOfPerspectivesFKBackingBean.pageFirst}">
    </h:commandButton>
                         
    <h:messages infoStyle="color: green;" 
                errorStyle="color: red;" />
    <br />

    <h:dataTable value="#{DerivedPartOfPerspectivesFKBackingBean.dataList}" 
                 var="item">
      
      <h:column>

    <%-- 
        Horrible Kludge for wrapping the returned JSON data contained in the FULL_PATH_JSON field
         with Javascript Head and Tail to build the complete JsTree required for the JsTree JQuery Plug-In
          
        Yuck!!!
        
      --%>
<script type="text/javascript">
	/*
	 * Build the initial JsTree
	 */ 
  $(function () {
	    $("#tree").jstree({ 
		    "json_data" : {
			    "data" : [
				    {
				    	"data" : "Anatomy",
					    "attr" : { 
					    	"id" : "li.node.BASE.Abstract.id0", 
			                "ext_id" : "EMAPA:0",
					    	"name" : "Anatomy",
  				    	    "start" : "TS01",
							"end" : "TS28"
					    },
					    "children": [

<h:outputText value="#{item.fullPathJson}" escape="false"/>

				        ],
		                "data": "Anatomy", 
					    "state" : "open"
				    }
			    ],
			    
			    "progressive_render" : true,
		    },
		    "contextmenu" : {
		    	"items" : createDefaultMenu 
		    },
		    "plugins": [
		        "themes", 
		        "json_data", 
		        "ui",
		        "contextmenu"
		    ]
  	})
  	.bind("select_node.jstree", function (event, data) {
		popUpDetails( "27", data.rslt.obj );
    })
  });
  
    /*
     * Create the Right Click Menu for each item in the Tree
     */
    function createDefaultMenu(obj){
		
		var arrayStr = obj.attr("id").split(".");
    	var nodeType = arrayStr[2];
    	var treeType = arrayStr[3];

    	var stageSeq = "27";
    	var emapId = "";
		
    	if ( nodeType == "BRANCH") {
    	    return {
                "Expand" : {
            	    label : "Expand Branch",
	            	action: function (obj) {
	                	if ( treeType == "Abstract" ) {
	                		emapId = addEmapId(stageSeq, obj);
		                 	//alert("Tree Type = " + treeType + "; emapId = " + emapId); 
	                		addSubTree("TS28", emapId, obj);
	                	}
	                	if ( treeType == "Timed" ) {
	                    	var emapId = obj.attr("ext_id");
	                    	//var arrayEmap = publicEmapId.split(":");
	                    	//var publicEmapId = arrayEmap[1];
		                 	//alert("Tree Type = " + treeType + "; emapId = " + emapId); 
	                		addSubTree("TS28", emapId, obj);
	                	}
		            },
                	"seperator_after" : false,
            	    "seperator_before" : false
                }
    	    }
    	}
    	
    }
    
</script>

      </h:column>
      
    </h:dataTable>

    <%-- HERE --%>
    <%-- The paging buttons --%>
    <h:commandButton value="first" 
                     action="#{DerivedPartOfPerspectivesFKBackingBean.pageFirst}"
                     disabled="#{DerivedPartOfPerspectivesFKBackingBean.firstRow == 0}" >
    </h:commandButton>
                      
    <h:commandButton value="prev" 
                     action="#{DerivedPartOfPerspectivesFKBackingBean.pagePrevious}"
                     disabled="#{DerivedPartOfPerspectivesFKBackingBean.firstRow == 0}" >
    </h:commandButton>    

    <h:commandButton value="next" 
                     action="#{DerivedPartOfPerspectivesFKBackingBean.pageNext}" 
                     disabled="#{DerivedPartOfPerspectivesFKBackingBean.firstRow + DerivedPartOfPerspectivesFKBackingBean.rowsPerPage >= DerivedPartOfPerspectivesFKBackingBean.totalRows}" >
    </h:commandButton>

    <h:commandButton value="last" 
                     action="#{DerivedPartOfPerspectivesFKBackingBean.pageLast}"
                     disabled="#{DerivedPartOfPerspectivesFKBackingBean.firstRow + DerivedPartOfPerspectivesFKBackingBean.rowsPerPage >= DerivedPartOfPerspectivesFKBackingBean.totalRows}" >
    </h:commandButton>
      
    <h:outputText value="Tree No. #{DerivedPartOfPerspectivesFKBackingBean.currentPage} From #{DerivedPartOfPerspectivesFKBackingBean.totalPages} Possible Tree Paths" >
    </h:outputText>
    
    <br />

    <%-- DIV Locator for populating the Tree passed back by the JSF call --%>
    <br />
    <br />
    <div id="tree"></div>
    
    
  </h:form>

  </fieldset>

</body>
</html>

</f:view>