
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
   embryoIdMap.setItem("Abstract","EMAPA:25765");

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
      var stage="Abstract";

      //insert stage dependent html snippets
      document.getElementById("tree_title").innerHTML = "" + stage;
      document.getElementById("stage_definition").innerHTML = 
	"Stage Definition: <a href=\"../theiler_stages/StageDefinition/" + stage.toLowerCase() + "definition.html\">" + stage + "</a>";
      document.getElementById("text_tree").src = "text/" + stage + "GroupsTrailing.txt";
      document.getElementById("download").innerHTML = 
	"<a href=\"text/" + stage + "GroupsTrailing.txt\">" + stage + ".txt </a>" +
	"<a href=\"text/" + stage + "GroupsTrailing.rtf\">" + stage + ".rtf </a>" +
	"<a href=\"text/" + stage + "GroupsTrailing.xml\">" + stage + ".xml </a>" +
	"<a href=\"text/AbstractVersion008.obo\">AbstractVersion008.obo </a>";

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
					    	"id" : "li_node_BASE_Abstract_id0", 
			                	"ext_id" : "EMAPA:0",
					    	"name" : "Anatomy",
  				    	    	"start" : "TS01",
  				    	    	"end" : "TS28"
					    },
					    "children": [{
                            			"attr": {
                                 			"ext_id": idMap.getItem(stage), 
                                 			"id": "li_node_ROOT_Abstract_id33", 
                                 			"name": "mouse",
		    					"title": idMap.getItem(stage),
  				    	    	        "start" : "TS01",
  				    	    	        "end" : "TS28"
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
				    url : "http://localhost:8080/DAOAnatomyJSP/listbyrootnamejsononlyaggregated",
				    //url : "http://testwww.emouseatlas.org/DAOAnatomyJSP/listbyrootnamejsononlyaggregated",
				    //url : "http://www.emouseatlas.org/DAOAnatomyJSP/listbyrootnamejsononlyaggregated",
				    dataType : "text",
				    data : function (n) { 
					    var emap_id = n.attr("ext_id");
					    var str = {
						    "rootName" : emap_id
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
    $("#tree").jstree("open_node","#li_node_ROOT_Abstract_id33");
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
			popUpAbstractDetails(obj)
	   	},
	   	"seperator_after" : false,
	   	"seperator_before" : false
   	}
    }
}
