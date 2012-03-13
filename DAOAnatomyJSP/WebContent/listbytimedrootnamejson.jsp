<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <title>Find TIMED Leafs of Root NODE NAME - JSON</title>
  
  <link type="text/css" rel="stylesheet" href="./css/global.css">
  
  <script type="text/javascript" src="./scripts/global.js"></script>
  <script type="text/javascript" src="./scripts/buildTimedTree.js"></script>
  
  <script type="text/javascript" src="./scripts/jsTree.v.1.0rc2/_lib/jquery.js"></script>
  <script type="text/javascript" src="./scripts/jsTree.v.1.0rc2/_lib/jquery.hotkeys.js"></script>
  <script type="text/javascript" src="./scripts/jsTree.v.1.0rc2/jquery.cookie.js"></script>
  <script type="text/javascript" src="./scripts/jsTree.v.1.0rc2/jquery.jstree.js"></script>
  <script type="text/javascript" src="./scripts/jsTree.v.1.0rc2/jquery.jstree.extra.js"></script>
  
  <script type="text/javascript">
    setHighlight('${form != null ? form.highlight : ''}');
    setFocus('${form != null ? form.focus : 'id'}');
  </script>
  
  <script type="text/javascript">
	/*
	 * Build the initial JsTree
	 *  uses one of the following Servlets: 
	 *   listbytimedrootnamejson - EMAP ID as Key
	 *   listbytimedrootdescriptionjson - Term Name as Key
	 */ 
  $(function () {
	    $("#tree").jstree({ 
		    "json_data" : {
			    "data" : [
				    {
				    	"data" : "Anatomy",
					    "attr" : { 
					    	"id" : "li.node.BASE.Timed.id0", 
			                "ext_id" : "EMAP:0",
					    	"name" : "Anatomy"
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
  		alert("Identifier: " + data.rslt.obj.attr("ext_id")); 
  	});
	    
  });

  </script>
  
</head>

<body>
  <form method="post" action="listbytimedrootnamejson">
    <fieldset>
      <legend>Find TIMED Leafs of Root NODE NAME - JSON</legend>
      <p>Enter a Root NODE NAME (EMAP ID).</p>

      <label for="rootName">ROOT NAME <span class="required">*</span></label>
      
      <input type="text" 
             id="rootName" 
             name="rootName" 
             value="${fn:escapeXml(timedleaf.rootName)}" 
             size="15" 
             maxlength="15" 
             ${form.success ? 'disabled="disabled"' : ''}>
      
      <select name="stage">
        <option value="TS01">TS01</option>
        <option value="TS02">TS02</option>
        <option value="TS03">TS03</option>
        <option value="TS04">TS04</option>
        <option value="TS05">TS05</option>
        <option value="TS06">TS06</option>
        <option value="TS07">TS07</option>
        <option value="TS08">TS08</option>
        <option value="TS09">TS09</option>
        <option value="TS10">TS10</option>
        <option value="TS11">TS11</option>
        <option value="TS12">TS12</option>
        <option value="TS13">TS13</option>
        <option value="TS14">TS14</option>
        <option value="TS15">TS15</option>
        <option value="TS16">TS16</option>
        <option value="TS17">TS17</option>
        <option value="TS18">TS18</option>
        <option value="TS19">TS19</option>
        <option value="TS20">TS20</option>
        <option value="TS21">TS21</option>
        <option value="TS22">TS22</option>
        <option value="TS23">TS23</option>
        <option value="TS24">TS24</option>
        <option value="TS25">TS25</option>
        <option value="TS26">TS26</option>
        <option value="TS27">TS27</option>
        <option value="TS28">TS28</option>
      </select>
      
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
