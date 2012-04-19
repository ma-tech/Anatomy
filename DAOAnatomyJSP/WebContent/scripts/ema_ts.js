// ema_ts.js
// various effects for ema theiler stage and ontology pages
$(document).ready(function() {
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
});
