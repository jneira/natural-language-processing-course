function high_contrast_fix() {
   $("body").append("<div id='high-contrast' class='hidden' style='background-color:#878787;'>x</div>");
   var current_color = $("#high-contrast").css("background-color").toLowerCase();

   $("#high-contrast").remove();
   if (current_color != "#878787" && current_color != "rgb(135, 135, 135)") {
      $('input,a,button').removeClass('btn');
   }
}


$('document').ready(function() {
    high_contrast_fix();
});
