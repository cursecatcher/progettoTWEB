jQuery(document).ready(function ($) {
    
    $('.choose-pizza').on('click', function() {
        var pizza = $(this).attr("id"); 
        console.log("Scelta pizza -  id: " + pizza);  
       
        
    });
}); 