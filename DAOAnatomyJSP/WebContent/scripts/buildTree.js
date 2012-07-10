    /*
     * Build the Sub Tree for a selected Branch node
     */
    function addSubTree(obj) {
    	var node = obj;
    	var rootname = obj.attr("ext_id");
		//alert("rootname = " + rootname); 
        $.ajax({
                async : false,
                type: 'GET',
                url : "http://localhost:8080/DAOAnatomyJSP/listbyrootnamejsononly",
                //url: "http://testwww.emouseatlas.org/DAOAnatomyJSP/listbyrootnamejsononly",
                //url: "http://www.emouseatlas.org/DAOAnatomyJSP/listbyrootnamejsononly",
                dataType: "text",
                data : {
                    "rootName" : rootname
                },
                success: function(data) {
                	var arrayStr = data.split(";");
                	for (a in arrayStr) {
                		var outStr = arrayStr[a]
                		//alert("JSON String = " + outStr); 
                		if ( outStr !== "\r\n"){
                    		var obj = jQuery.parseJSON(outStr);
                        	$.jstree._focused().create_node(node, "last", obj);
                            $.jstree._focused().open_node(node);
                		}
                	}
                },
                error: function(data) {
                	alert("AJAX Call for Sub Tree Failed!"); 
                }
                });
    }

    /*
     * Create the Right Click Menu for each item in the Tree
     */
    function createDefaultMenu(obj){
		var nodeId = obj.attr("id");
		var nodeName = obj.attr("ext_id");
		//alert("nodeId = " + nodeId); 
		//alert("nodeName = " + nodeName); 
    	var arrayStr = nodeId.split(".");
    	var nodeType = arrayStr[2];

    	switch (nodeType) {
    	case "LEAF":
    		{
    		    return {
    		        "Details" : {
    		        	label : "Details",
    		        	action: function (obj) {
    		        		alert("Leaf Name = " + obj.attr("ext_id"));
    		        	},
		            	"seperator_after" : false,
		            	"seperator_before" : false
    		        }
    	        }
    		    break;
    		}
    	case "BRANCH":
     		{
	    	    return {
    		        "Details" : {
    		        	label : "Details",
    		        	action: function (obj) {
    		        		alert("Branch Name = " + obj.attr("ext_id"));
    		        	},
		            	"seperator_after" : false,
		            	"seperator_before" : false
		            },
	                "Expand" : {
	            	    label : "Expand Branch",
    	            	action: function (obj) {
	            	    	//alert('Expandable');
	                		addSubTree(obj);
 		            	},
	                	"seperator_after" : false,
	            	    "seperator_before" : false
	                }
	            }
		        break;
		    }
    	case "ROOT":
    		{
	    	    return {
    		        "Details" : {
    		        	label : "Details",
    		        	action: function (obj) {
    		        		alert("Root Name = " + obj.attr("ext_id"));
    		        	},
		            	"seperator_after" : false,
		            	"seperator_before" : false
		            }
	            }
		        break;
		    }
    	case "BASE":
		    {
		        return {
    		        "Details" : {
    		        	label : "Details",
    		        	action: function (obj) {
    		        		alert("Base Name = " + obj.attr("ext_id"));
    		        	},
		            	"seperator_after" : false,
		            	"seperator_before" : false
    		        }
	            }
		        break;
		    }
    	default:
    	    {
	            return {
	                "Unidentifiable Node Type" : {
	            	    label : "Unidentifiable Node Type",
		            	action: function (obj) {
        	        		alert('Unidentifiable Node Type')
    	            	},
		            	"seperator_after" : false,
		            	"seperator_before" : false
		            }
                }
	            break;
    	    }
    	}
    }
    