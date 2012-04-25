    /*
     *  Pop Up the node Details for a selected Branch node
     */
    function popUpAbstractDetails( obj ) {
		
     	var nodeId = obj.attr("id");
		var nodeName = obj.attr("ext_id");
		var start = obj.attr("start");
		var end = obj.attr("end");

		//alert("popUpDetails: treeType = " + treeType + "\nstage = " + stageSeq + "\nnodeName = " + nodeName); 

   		alert(nodeName + "\n(" +  start + "-" + end + ")");

     }
    

    /*
     *  Pop Up the node Details for a selected Branch node
     */
    function popUpDetails( stageSeq, obj ) {
		
     	var nodeId = obj.attr("id");
		var nodeName = obj.attr("ext_id");
		var start = obj.attr("start");
		var end = obj.attr("end");
    	var arrayStr = nodeId.split("_");
    	var treeType = arrayStr[3];
    	var emapId = "";
    	
    	//alert("popUpDetails: treeType = " + treeType + "\nstage = " + stageSeq + "\nnodeName = " + nodeName); 

    	if ( treeType == "Timed") {
    		emapaStr = addEmapaStr(nodeName);
    		arrayEmapa = emapaStr.split(" ");
    		alert(nodeName + "\n(" + arrayEmapa[6] + "-" + arrayEmapa[8].replace(/\s/g,'') + ")\n" + arrayEmapa[4]);
		}

    	if ( treeType == "Abstract") {
    		emapId = addEmapId(stageSeq, obj);
    		alert(emapId + "\n(" +  start + "-" + end + ")\n" + nodeName);
		}

     }
    

    /*
     * Build the Sub Tree for a selected Branch node
     */
    function addAbstractSubTree(obj) {
    	var node = obj;
    	var rootname = obj.attr("ext_id");
		
    	//alert("rootname = " + rootname); 
        $.ajax({
                async : false,
                type: 'GET',
                //url: "http://testwww.emouseatlas.org/DAOAnatomyJSP/listbyrootnamejsononly",
                url: "http://localhost:8080/DAOAnatomyJSP/listbyrootnamejsononly",
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
     *  Build the Sub Tree for a selected Branch node
     */
    function addSubTree( stage, rootName, obj ) {
		
     	var node = obj;
    	var arrayStr = rootName.split(":");
    	var emapId = arrayStr[1];
		
    	//alert("rootName = " + emapId + "\nstage = " + stage ); 
    	
        $.ajax({
                async : false,
                type: 'GET',
                //url: "http://testwww.emouseatlas.org/DAOAnatomyJSP/listleafsbyemapandstage",
                url: "http://localhost:8080/DAOAnatomyJSP/listleafsbyemapandstage",
                dataType: "text",
                data : {
                    "rootName" : emapId,
                    "stage" : stage
                },
                success: function(data) {
                	var arrayStr = data.split(" ");
                	if (arrayStr[0] == "FAIL!") {
                    	alert("AJAX Internal Failure!\n" + data); 
                	}
                	else {
                		arrayStr = data.split(";");
                    	for (a in arrayStr) {
                    		var outStr = arrayStr[a]
                    		//alert("JSON String = " + outStr); 
                    		if ( outStr !== "\r\n"){
                        		var obj = jQuery.parseJSON(outStr);
                            		//$.jstree._focused().create_node(node, "last", obj);
                                	//$.jstree._focused().open_node(node);
                    		}
                    	}
                	}
                },
                error: function(data) {
                	//alert("call to addSubTree Failed!"); 
                	alert("AJAX External Failure!"); 
                }
            });
    }


    /*
     *  For any selected Abstract Node, get the Timed node
     */
    function addEmapId(stageSeq, obj) {

    	var publicEmapaId = obj.attr("ext_id");
    	var arrayEmapa = publicEmapaId.split(":");
    	var publicEmapaId = arrayEmapa[1];
        var emapId = "";
        
		//alert("publicEmapaId = " + publicEmapaId + "\nstageSeq = " + stageSeq);
        
		$.ajax({
            async : false,
            type: 'GET',
            //url: "http://testwww.emouseatlas.org/DAOAnatomyJSP/findbyemapaidandstage",
            url: "http://localhost:8080/DAOAnatomyJSP/findbyemapaidandstage",
            dataType: "text",
            data : {
                "publicEmapaId" : publicEmapaId, 
                "stageSeq" : stageSeq
            },
            success: function(data) {
            	//alert("Data String = " + data); 
            	var arrayStr = data.split(" ");
            	if (arrayStr[0] == "FAIL!") {
                	alert("AJAX Internal Failure!\n" + data); 
            	}
                else {
                  	var arrayStr = data.split(" ");
                	emapId = arrayStr[4]; 
                }
            },
            error: function(data) {
            	//alert("call to addEmapId Failed!"); 
            	alert("AJAX External Failure!"); 
            }
        });
   		
   		return emapId;

    }


    /*
     *  For any selected Timed Node, get the Abstract node as a String
     */
    function addEmapaStr(emapIn) {
    	
    	var arrayEmap = emapIn.split(":");
    	var publicEmapId = arrayEmap[1];
		var outStr = "";
		
    	//alert("publicEmapId = " + publicEmapId);
    	
        $.ajax({
            async : false,
            type: 'GET',
            //url: "http://testwww.emouseatlas.org/DAOAnatomyJSP/findbyemapid",
            url: "http://localhost:8080/DAOAnatomyJSP/findbyemapid",
            dataType: "text",
            data : {
                "publicEmapId" : publicEmapId
            },
            success: function(data) {
            	//alert("Data String = " + data.toString()); 
            	var arrayStr = data.toString().split(" ");
            	if (arrayStr[0] == "FAIL!") {
                	alert("AJAX Internal Failure!\n" + data.toString()); 
            	}
                else {
                  	outStr = data.toString();
                }
            },
            error: function(data) {
            	//alert("call to addEmapaStr Failed!"); 
            	alert("AJAX External Failure!"); 
            }
        });

   		return outStr;

    }
