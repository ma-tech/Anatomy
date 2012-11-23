// ema_ts.js
// various effects for ema theiler stage and ontology pages
$(document).ready(function() {
   $("#nav_selector").change(function() { 
	if ($("#nav_selector option:selected").text()=="Abstract"){
	   window.location.href = "abstract.html";
        }
	else {
	   window.location.href = "anatomy.html?stage=" + $("#nav_selector option:selected").text();
	}
   });

   $("#navigation_toggle").toggle(function(){
        $("#navigation_container").hide('fast');
        $("#navigation_toggle span").html("show navigation");
	$("#navigation_toggle img").attr('src','/emap/ema/images/common/show.png');
   },
   function(){
        $("#navigation_container").show('slow');
        $("#navigation_toggle span").html("hide navigation");
	$("#navigation_toggle img").attr('src','/emap/ema/images/common/hide.png');
   });

   $("#search_toggle").toggle(function(){
        $("#search_container").hide('fast');
        $("#search_toggle span").html("show search");
	$("#search_toggle img").attr('src','/emap/ema/images/common/show.png');
   },
   function(){
        $("#search_container").show('slow');
        $("#search_toggle span").html("hide search");
	$("#search_toggle img").attr('src','/emap/ema/images/common/hide.png');
   });

   $("#interactive_tree_toggle").toggle(function(){
        $("#interactive_tree_container").hide('fast');
        $("#interactive_tree_toggle span").not('span.small_legend').html("show tree");
	$("#interactive_tree_toggle img").attr('src','/emap/ema/images/common/show.png');
   },
   function(){
        $("#interactive_tree_container").show('slow');
        $("#interactive_tree_toggle span").not('span.small_legend').html("hide tree");
	$("#interactive_tree_toggle img").attr('src','/emap/ema/images/common/hide.png');
   });

   $("#text_tree_toggle").toggle(function(){
        $("#text_tree_container").hide('fast');
        $("#text_tree_toggle span").html("show tree");
	$("#text_tree_toggle img").attr('src','/emap/ema/images/common/show.png');
   },
   function(){
        $("#text_tree_container").show('slow');
        $("#text_tree_toggle span").html("hide tree");
	$("#text_tree_toggle img").attr('src','/emap/ema/images/common/hide.png');
   });

   var search_data_name="none";
   var search_data_id="none";
   var current_index=0;

   $("#search_ontology_go").click(function(){
	var stage = $("#nav_selector option:selected").text();
	var search_val = $("#search_ontology_input").val();   
	var url="/DAOAnatomyJSP/listderivedpartofperspectivesfk?" + "searchTerm=" + search_val + "&stage=" + stage;

	clear_previous_results();

	$.getJSON(url,
	  function(data, textStatus, jqXHR) {
	     if (textStatus != "success") {
	        alert("Server did not respond successfully - no results");
	     }

	     search_data_name = data[0];
	     search_data_id = data[1];

	     if (search_data_name.length < 1) {
	        search_data_name[0] = "No results";
	     }

	     if (search_data_name != 'none') {
	        var el;
	        el = $('<button id="first_result" type="button" disabled="disabled">').text('First');
	        $("#search_result_nav").append(el);
	        el = $('<button id="previous_result" type="button" disabled="disabled">').text('Previous');
	        $("#search_result_nav").append(el);
	        el = $('<button id="next_result" type="button">').text('Next');
	        $("#search_result_nav").append(el);
	        el = $('<button id="last_result" type="button">').text('Last');
	        $("#search_result_nav").append(el);
	        var last_index=search_data_name.length;
	        $("#search_result_nav").
		   append('&nbsp;&nbsp;Result <span id="result_first_index">' + (current_index+1) + 
		      '</span> of <span id="result_last_index">' + last_index + '</span>');
	     }
	     $("#search_container").append($('<div id="search_result_path"><span class="search_highlight">' + search_data_name[current_index] + '</span></div>'));

             open_node_path(search_data_id[current_index]);
	  });
   });

   $('#search_result_nav').on("click",'#first_result',function(){
       current_index = 0 ;
       check_button_states();
       $("#search_result_path span").text(search_data_name[current_index]);
       $("#result_first_index").text(current_index+1);
       open_node_path(search_data_id[current_index]);
   });

   $('#search_result_nav').on("click",'#next_result',function(){
       if (current_index < search_data_name.length-1) {
          current_index++;
       }
       check_button_states();
       $("#search_result_path span").text(search_data_name[current_index]);
       $("#result_first_index").text(current_index+1);
       open_node_path(search_data_id[current_index]);
   });

   $('#search_result_nav').on("click",'#previous_result',function(){
       if (current_index > 0) {
          current_index--;
       }
       check_button_states();
       $("#search_result_path span").text(search_data_name[current_index]);
       $("#result_first_index").text(current_index+1);
       open_node_path(search_data_id[current_index]);
   });

   $('#search_result_nav').on("click",'#last_result',function(){
       current_index = search_data_name.length-1;
       check_button_states();
       $("#search_result_path span").text(search_data_name[current_index]);
       $("#result_first_index").text(current_index+1);
       open_node_path(search_data_id[current_index]);
   });

   function clear_previous_results() {
      $('#search_result_nav').empty();    
      $('#search_result_path').remove();    
      current_index = 0;
   };

   function check_button_states() {
      if (current_index == 0) {
         if ( ! $('#first_result').attr("disabled")) {
            $('#first_result').attr("disabled","disabled");
	 }
         if ( ! $('#previous_result').attr("disabled")) {
            $('#previous_result').attr("disabled","disabled");
	 }
         if ($('#next_result').attr("disabled")) {
            $('#next_result').removeAttr("disabled");
	 }
         if ($('#last_result').attr("disabled")) {
            $('#last_result').removeAttr("disabled");
	 }
      }
      if (current_index > 0 && current_index < search_data_name.length-1) {
         if ($('#first_result').attr("disabled")) {
            $('#first_result').removeAttr("disabled");
	 }
         if ($('#previous_result').attr("disabled")) {
            $('#previous_result').removeAttr("disabled");
	 }
         if ($('#next_result').attr("disabled")) {
            $('#next_result').removeAttr("disabled");
	 }
         if ($('#last_result').attr("disabled")) {
            $('#last_result').removeAttr("disabled");
	 }
      }
      if (current_index == search_data_name.length-1) {
         if ($('#first_result').attr("disabled")) {
            $('#first_result').removeAttr("disabled");
	 }
         if ($('#previous_result').attr("disabled")) {
            $('#previous_result').removeAttr("disabled");
	 }
         if ( ! $('#next_result').attr("disabled")) {
            $('#next_result').attr("disabled","disabled");
	 }
         if ( ! $('#last_result').attr("disabled")) {
            $('#last_result').attr("disabled","disabled");
	 }
      }
   };

   function open_node_path(node_path) {
      var selector;
      var nodes = node_path.split('.');

      //reset any highlighted nodes
      selector = '#tree li > a';
      $(selector).attr("class","");

      selector = "";
      var previous_node="";

      //walk down the nodes opening them and highlighting text
      for (var i=0; i< nodes.length; i++){
         selector = '#li_node_id_' + nodes[i];
         if (previous_node) {
	    //select the direct children of the previous node
	    // avoid finding multiple ids due to groups
	    this_node = previous_node.children('ul').children(selector);
	 }
	 else {
	    this_node = $(selector);
	 }
         //open the tree node
         $("#tree").jstree("open_node", this_node);
	 //highlight the tree node link
	 this_node.children('a').attr("class","search_highlight");
	 //this_node.children('a').attr("class","jstree-clicked jstree-hovered");
	 previous_node = this_node;
      }
       
   };

   function open_node(node_path) {
      var selector;
      var nodes = node_path.split('.');

      //open the tree nodes
      for (var i=0; i< nodes.length-1; i++){
         if ( nodes[i] != "mouse") {
	    selector = 'li[name="' + nodes[i] + '"]';
            $("#tree").jstree("open_node", $(selector));
	 }
      }

      //reset any highlighted nodes
      selector = '#tree li > a';
      $(selector).attr("class","");

      //highlight with css classes the nodes
      for (var i=0; i< nodes.length; i++){
         selector = 'li[name="' + nodes[i] + '"] > a';
	 $(selector).attr("class","jstree-clicked jstree-hovered");
      }
   };

});
