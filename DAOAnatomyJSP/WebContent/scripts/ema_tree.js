
/*
   * Copyright (C) 2010 Medical research Council, UK.
   *
   * This program is free software; you can redistribute it and/or
   * modify it under the terms of the GNU General Public License
   * as published by the Free Software Foundation; either version 2
   * of the License, or (at your option) any later version.
   *
   * This program is distributed in the hope that it will be
   * useful but WITHOUT ANY WARRANTY; without even the implied
   * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
   * PURPOSE. See the GNU General Public License for more
   * details.
   *
   * You should have received a copy of the GNU General Public
   * License along with this program; if not, write to the Free
   * Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
   * Boston, MA 02110-1301, USA.
   *
   */
////---------------------------------------------------------
// tiledImageModel.js
// Model for high resolution tiled image from an iip server
// Using the 'module' pattern of Crockford, slightly modified by Christian Heilmann into the 'Revealing Module Pattern'
//---------------------------------------------------------
//---------------------------------------------------------
//---------------------------------------------------------
// Dependencies:
// none
//---------------------------------------------------------
//---------------------------------------------------------
// Namespace:
//---------------------------------------------------------
if(!emouseatlas) {
	var emouseatlas = {};
}
if(!emouseatlas.emap) {
	emouseatlas.emap = {};
}
//---------------------------------------------------------
// module for emaTree
// encapsulating it in a module to preserve namespace
//---------------------------------------------------------
emouseatlas.emap.emaTree = function() { 

   //---------------------------------------------------------
   //   private methods
   //---------------------------------------------------------
   function emaHashTable(obj)
   {
       this.length = 0;
       this.items = {};
       for (var p in obj) {
       	   if (obj.hasOwnProperty(p)) {
              this.items[p] = obj[p];
              this.length++;
           }
       }

       this.setItem = function(key, value) {
           var previous = undefined;
           if (this.hasItem(key)) {
              previous = this.items[key];
           }
           else {
              this.length++;
           }
           this.items[key] = value;
           return previous;
       }

       this.getItem = function(key) {
          return this.hasItem(key) ? this.items[key] : undefined;
       }

       this.hasItem = function(key) {
           return this.items.hasOwnProperty(key);
       }

       this.removeItem = function(key)
       {
           if (this.hasItem(key)) {
              previous = this.items[key];
              this.length--;
              delete this.items[key];
              return previous;
           }
           else {
              return undefined;
           }
       }

       this.keys = function() {
           var keys = [];
           for (var k in this.items) {
              if (this.hasItem(k)) {
                   keys.push(k);
               }
           }
           return keys;
       }

       this.values = function()
       {
           var values = [];
           for (var k in this.items) {
              if (this.hasItem(k)) {
                  values.push(this.items[k]);
              }
           }
           return values;
       }

       this.each = function(fn) {
           for (var k in this.items) {
              if (this.hasItem(k)) {
                  fn(k, this.items[k]);
              }
           }
       }

       this.clear = function() {
          this.items = {}
          this.length = 0;
       }
   }

   var embryoIdMap=new emaHashTable();
   embryoIdMap.setItem("TS01","EMAP:25766");
   embryoIdMap.setItem("TS02","EMAP:25767");
   embryoIdMap.setItem("TS03","EMAP:25768");
   embryoIdMap.setItem("TS04","EMAP:25769");
   embryoIdMap.setItem("TS05","EMAP:25770");
   embryoIdMap.setItem("TS06","EMAP:25771");
   embryoIdMap.setItem("TS07","EMAP:25772");
   embryoIdMap.setItem("TS08","EMAP:25773");
   embryoIdMap.setItem("TS09","EMAP:25774");
   embryoIdMap.setItem("TS10","EMAP:25775");
   embryoIdMap.setItem("TS11","EMAP:25776");
   embryoIdMap.setItem("TS12","EMAP:25777");
   embryoIdMap.setItem("TS13","EMAP:25778");
   embryoIdMap.setItem("TS14","EMAP:25779");
   embryoIdMap.setItem("TS15","EMAP:25780");
   embryoIdMap.setItem("TS16","EMAP:25781");
   embryoIdMap.setItem("TS17","EMAP:25782");
   embryoIdMap.setItem("TS18","EMAP:25783");
   embryoIdMap.setItem("TS19","EMAP:25784");
   embryoIdMap.setItem("TS20","EMAP:25785");
   embryoIdMap.setItem("TS21","EMAP:25786");
   embryoIdMap.setItem("TS22","EMAP:25787");
   embryoIdMap.setItem("TS23","EMAP:25788");
   embryoIdMap.setItem("TS24","EMAP:25789");
   embryoIdMap.setItem("TS25","EMAP:25790");
   embryoIdMap.setItem("TS26","EMAP:25791");
   embryoIdMap.setItem("TS27","EMAP:30155");
   embryoIdMap.setItem("TS28","EMAP:27551");

   //---------------------------------------------------------
   //   public methods
   //---------------------------------------------------------

   var initialise = function () {
      //alert("ema tree");
   };

   var getStage = function() {
      return "TSXXXX Anatomy";
   };

   var getEmbryoIdMap = function() {
      return embryoIdMap;
   };

   //---------------------------------------------------------
   // expose 'public' properties
   //---------------------------------------------------------
   // don't leave a trailing ',' after the last member or IE won't work.
   return {
     initialise: initialise,
     getEmbryoIdMap: getEmbryoIdMap,
     getStage: getStage
   };
}(); // end of module emaTree


// JavaScript Document
jQuery(document).ready(function(){
      var stage;
      var nvPairs = window.location.search.substring(1).split("&");
      for (var i = 0; i < nvPairs.length; i++) {
         var nvPair = nvPairs[i].split("=");
	 if (nvPair[0] === "stage") {
	    stage = nvPair[1];
	 }
      }

      //insert stage dependent html snippets
      document.getElementById("tree_title").innerHTML = "" + stage;
      document.getElementById("stage_definition").innerHTML = 
	"Stage Definition: <a href=\"../theiler_stages/StageDefinition/" + stage.toLowerCase() + "definition.html\">" + stage + "</a>";
      document.getElementById("text_tree").src = "text/" + stage + "GroupsTrailing.txt";
      document.getElementById("download").innerHTML = 
	"<a href=\"text/" + stage + "GroupsTrailing.txt\">" + stage + ".txt </a>" +
	"<a href=\"text/" + stage + "GroupsTrailing.rtf\">" + stage + ".rtf </a>" +
	"<a href=\"text/" + stage + "GroupsTrailing.xml\">" + stage + ".xml </a>";

      //set the navigation selector drop down
      $("#nav_selector").val(stage);

      var idMap = emouseatlas.emap.emaTree.getEmbryoIdMap();
      if ( ! idMap.hasItem(stage)) {
	 return;
      }
	    $("#tree").jstree({ 
		    "json_data" : {
			    "data" : [
				    {
				    	"data" : stage,
					    "attr" : { 
					    	"id" : "li_node_BASE_Timed_id0", 
			                	"ext_id" : "EMAP:0",
					    	"name" : "Anatomy",
  				    	    	"stage" : stage
					    },
					    "children": [{
                            			"attr": {
                                 			"ext_id": idMap.getItem(stage), 
                                 			"id": "li_node_BRANCH_Timed_id34", 
                                 			"name": "mouse",
		    					"title": idMap.getItem(stage),
       				    	     		"stage" : stage
                             			},
                             			"data": "mouse",
					    	"state" : "closed"
                        			}		
					    ],
					    "state" : "open"
				    }
			    ],
			    "ajax" : { 
				    async : false,
				    type : 'GET',
				    "url" : "http://testwww.emouseatlas.org/DAOAnatomyJSP/listleafsbyemapandstageaggregated",
				    dataType : "text",
				    data : function (n) { 
					    var emap_id = n.attr("ext_id").replace(/EMAP:/,"");
					    var str = {
						    "rootName" : emap_id,
						    "stage" : n.attr("stage")
					    };
					    return str; 
				    },
 				    success : function(data) {
				           var arrayStr = data.split(" ");
					   if (arrayStr[0] == "FAIL!") {
					      alert("AJAX Internal Failure!\n" + data);
					   }
					   else {
					      var obj = jQuery.parseJSON(data);
					   }
					   for (var i = 0; i < obj.length; i++) {
					      obj[i].attr.title = obj[i].attr.ext_id;
					   }
					   return obj;
	 			    },
 				    error : function(data) {
				           alert("AJAX External Failure!\n" + data);
	 			    }
			    },
			    "progressive_render" : true
		    },
		    "contextmenu" : {
		    	"items" : createDefaultMenu 
		    },
		    "themes" : {
		        "theme" : "classic",
			"dots" : "true",
			"icons" : "true" 
		    },
		    "plugins": [
		        "themes",
		        "json_data", 
		        "ui",
		        "contextmenu"
		    ]
  	})
	/*
  	.bind("select_node.jstree", function (event, data) {
		popUpDetails( "6", data.rslt.obj );
	})
	*/

});

function open_tree(){
    $("#tree").jstree("open_node","#li_node_BRANCH_Timed_id34");
}

/*
 * Create the Right Click Menu for each item in the Tree
 */
function createDefaultMenu(obj){

   var arrayStr = obj.attr("id").split(".");
   var nodeType = arrayStr[2];
   var treeType = arrayStr[3];

   var stageSeq = "0";
   var emapId = "";
		
  return {
  	"Query EMAGE" : {
   		label : "Search EMAGE",
   		action : function (obj) {
   			var emapId = obj.attr("ext_id");
   			if (emapId === "EMAP:0") {
      				return;
   			}
   			var url = 'http://www.emouseatlas.org/emagewebapp/pages/emage_general_query_result.jsf?structures=' + emapId + '&exactmatchstructures=true&includestructuresynonyms=true';
   			window.open(url);
   		}	
   	},
   	"Query GXD" : {
		label : "Search GXD",
	   	action : function (obj) {
			var emapId = obj.attr("ext_id").replace(/EMAP:/,"");
		   	if (emapId === "0") {
		      		return;
		   	}	
		   	var url = 'http://www.informatics.jax.org/searches/expression_report.cgi?edinburghKey=' + emapId + '&sort=Gene%20symbol&returnType=assay%20results&substructures=structures';
		   	window.open(url);
	   	}
   	},
   	"Query Google" : {
	        label : "Search Google",
  	   	action : function (obj) {
		   	var nodeName = obj.attr("name");
		   	var url = 'http://www.google.co.uk/search?q=' + nodeName;
		   	window.open(url);
	   	}
   	},
   	"Query Wikipedia" : {
	   	label : "Search Wikipedia",
  	   	action : function (obj) {
    		   	var nodeName = obj.attr("name");
		   	var url = 'http://en.wikipedia.org/wiki/' + nodeName;
		   	window.open(url);
	   	}
   	},
   	"ID info" : {
           	label : "ID information",
	   	action : function (obj) {
	      		if (obj.attr("ext_id") === "EMAP:0") {
	         		return;
	      		}  
	      		popUpDetails( stageSeq, obj )
	   	},
	   	"seperator_after" : false,
	   	"seperator_before" : false
   	}
    }
}
