    /*
     *  Build the Sub Tree for a selected Branch node
     */
    function addSubTree( emap, stage ) {
		
    	alert("emap = " + emap + "\nstage = " + stage ); 
        $.ajax({
                async : false,
                type: 'GET',
                url: "http://localhost:8080/DAOAnatomyJSP/findleafsbyemapandstage",
                dataType: "text",
                data : {
                    "emap" : emap,
                    "stage" : stage
                },
                success: function(data) {
                	if (data == "") {
                    	alert("No Data Returned!"); 
                	}
                	else {
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
		var start = obj.attr("start");
		var end = obj.attr("end");
    	var arrayStr = nodeId.split(".");
    	var nodeType = arrayStr[2];
		//alert("arrayStr[2] = " + arrayStr[2]);
    	var emapId = "";
		
    	switch (nodeType) {
    	case "LEAF":
    		{
    		    return {
    		        "Details" : {
    		        	label : "Details",
    		        	action: function (obj) {
    	            		emapId = addEmapId(obj);
    		        		alert("Leaf EMAPA Id: " + obj.attr("ext_id") + "\n" + 
      		        	  		  "Leaf EMAP Id: " + emapId + "\n" + 
    		        	  		  "Starts At Stage: " + obj.attr("start") + "\n" + 
    		        	  		  "Ends At Stage: " + obj.attr("end"));
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
    	            		emapId = addEmapId(obj);
    		        		alert("Branch EMAPA Id: " + obj.attr("ext_id") + "\n" + 
        		        	  	  "Branch EMAP Id: " + emapId + "\n" + 
      		        	  		  "Starts At Stage: " + obj.attr("start") + "\n" + 
      		        	  		  "Ends At Stage: " + obj.attr("end"));
    		        	},
		            	"seperator_after" : false,
		            	"seperator_before" : false
		            },
	                "Expand" : {
	            	    label : "Expand Branch",
    	            	action: function (obj) {
	            	    	alert('Expandable');
    	            		emapId = addEmapId(obj);
	                		addSubTree(emapId, obj);
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
    	            		emapId = addEmapId(obj);
    		        		alert("Root EMAPA Id: " + obj.attr("ext_id") + "\n" + 
          		        	  	  "Root EMAP Id: " + emapId + "\n" + 
        		        	  	  "Starts At Stage: " + obj.attr("start") + "\n" + 
        		        	  	  "Ends At Stage: " + obj.attr("end"));
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
    	            		emapId = addEmapId(obj);
    	            		alert("Base EMAPA Id: " + obj.attr("ext_id") + "\n" + 
            		        	  "Base EMAP Id: " + emapId + "\n" + 
          		        	  	  "Starts At Stage: " + obj.attr("start") + "\n" + 
          		        	  	  "Ends At Stage: " + obj.attr("end"));
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
    		        		alert("Unidentifiable Node Type: " + obj.attr("ext_id") + 
  		        	  		      "\nStarts At Stage: " + obj.attr("start") + 
    		        	  		  "\nEnds At Stage: " + obj.attr("end"));
    	            	},
		            	"seperator_after" : false,
		            	"seperator_before" : false
		            }
                }
	            break;
    	    }
    	}
    }
    