    /*
     *  Pop Up the node Details for a selected Branch node
     */
    function popUpDetails( stageSeq, obj ) {
		
     	var nodeId = obj.attr("id");
		var nodeName = obj.attr("ext_id");
		var start = obj.attr("start");
		var end = obj.attr("end");
    	var arrayStr = nodeId.split(".");
    	var treeType = arrayStr[3];
    	var emapId = "";
    	
		if ( treeType == "Timed") {
    		emapaStr = addEmapaStr(nodeName);
    		arrayEmapa = emapaStr.split(" ");
    		alert(arrayEmapa[4] + "\n(" + arrayEmapa[6] + "-" + arrayEmapa[8] + ")\n" + nodeName);
		}
		if ( treeType == "Abstract") {
    		emapId = addEmapId(stageSeq, obj);
    		alert(nodeName + "\n(" +  start + "-" + end + ")\n" + emapId);
		}

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
                //url : "http://testwww.emouseatlas.org/DAOAnatomyJSP/listleafsbyemapandstage",
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
                            	$.jstree._focused().create_node(node, "last", obj);
                                $.jstree._focused().open_node(node);
                    		}
                    	}
                	}
                },
                error: function(data) {
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
            //url : "http://testwww.emouseatlas.org/DAOAnatomyJSP/findbyemapaidandstage",
            url: "http://localhost:8080/DAOAnatomyJSP/findbyemapaidandstage",
            dataType: "text",
            data : {
                "publicEmapaId" : publicEmapaId, 
                "stageSeq" : stageSeq
            },
            success: function(data) {
            	var arrayStr = data.split(" ");
            	if (arrayStr[0] == "FAIL!") {
                	alert("AJAX Internal Failure!\n" + data); 
            	}
                else {
                	//alert("Data String = " + data); 
                  	var arrayStr = data.split(" ");
                	emapId = arrayStr[4]; 
                }
            },
            error: function(data) {
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
		
    	alert("publicEmapId = " + publicEmapId);
        
		$.ajax({
            async : false,
            type: 'GET',
            //url : "http://testwww.emouseatlas.org/DAOAnatomyJSP/findbyemapid",
            url: "http://localhost:8080/DAOAnatomyJSP/findbyemapid",
            dataType: "text",
            data : {
                "publicEmapId" : publicEmapId
            },
            success: function(data) {
            	alert("Data String = " + data); 
            	var arrayStr = data.split(" ");
            	if (arrayStr[0] == "FAIL!") {
                	alert("AJAX Internal Failure!\n" + data); 
            	}
                else {
                  	outStr = data;
                }
            },
            error: function(data) {
            	alert("call to addEmapaStr Failed!"); 
            	alert("AJAX External Failure!"); 
            }
        });
   		
   		return outStr;

    }
