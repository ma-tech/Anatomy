<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <title>Find Leafs of Root NODE NAME - JSON</title>

  <script type="text/javascript" src="./scripts/jsTree.v.1.0rc2/_lib/jquery.js"></script>
  <script type="text/javascript" src="./scripts/jsTree.v.1.0rc2/_lib/jquery.hotkeys.js"></script>
  <script type="text/javascript" src="./scripts/jsTree.v.1.0rc2/jquery.cookie.js"></script>
  <script type="text/javascript" src="./scripts/jsTree.v.1.0rc2/jquery.jstree.js"></script>
  <script type="text/javascript" src="./scripts/jsTree.v.1.0rc2/jquery.jstree.extra.js"></script>

  <link type="text/css" rel="stylesheet" href="./css/global.css">

  <style type="text/css">
    #tree {
      font-family: Verdana, helvetica, arial, sans-serif;
      font-size: 100%;
    }
  </style>

  <script type="text/javascript" src="./scripts/global.js"></script>
  <script type="text/javascript" src="./scripts/buildTree.js"></script>
  
  <script type="text/javascript">
    setHighlight('${form != null ? form.highlight : ''}');
    setFocus('${form != null ? form.focus : 'id'}');
  </script>

  <script type="text/javascript">
	/*
	 * Build the initial JsTree
	 *  uses one of the following Servlets: 
	 *   listbyrootnamejson - EMAPA ID as Key
	 *   listbyrootdescriptionjson - Term Name as Key
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

${leafTree}

		                "data": "Anatomy", 
					    "state" : "closed"
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
  	.bind("select_node.jstree", function (e, data) { 
  		//alert(data.rslt.obj.attr("ext_id") + " " + data.rslt.obj.attr("name") + " " + data.rslt.obj.attr("id")); 
  		alert("Identifier: " + data.rslt.obj.attr("ext_id") + 
  				"\nStarts At Stage: " + data.rslt.obj.attr("start") + 
  				"\nEnds At Stage: " + data.rslt.obj.attr("end")); 
  	});
	    
  });

  </script>

</head>

<body>
  <form method="post" action="listbyrootnamejson">
    <fieldset>
      <legend>Find Leafs of Root NODE NAME - JSON</legend>
      <p>Enter a Root NODE NAME (EMAPA ID).</p>

      <label for="rootName">ROOT NAME<span class="required">*</span></label>
      <input type="text" 
             id="rootName" 
             name="rootName" 
             value="${fn:escapeXml(leaf.rootName)}" 
             size="15" 
             maxlength="15" 
             ${form.success ? 'disabled="disabled"' : ''}>
             
      <span class="error">${form.messages.rootName}</span>
      <br/>

      <input type="submit" 
             value="list" 
             class="withoutLabel" 
             ${form.success ? 'disabled="disabled"' : ''}>
      <br />

      <p><a href="index.html">BACK</a></p>

      <p class="${form.success ? 'success' : 'error'}">${form.messages.result}</p>

      <div id="tree">
      </div>
      <br />
    
    </fieldset>
  </form>
  
</body>

</html>
