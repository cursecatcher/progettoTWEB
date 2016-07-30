jQuery(document).ready(function ($) {
    var $prenform = $("#form_view_prenotazione");

    $(".view-prenotazione").on('click', function () {
        var idp = $(this).data("id");

        $.getJSON(
                "Controller",
                {
                    action: "get-prenotazione",
                    id: idp
                }, 
                function(json) {
                    console.log("response: " + json.data_consegna); 
                    console.log("json -> " + JSON.stringify(json)); 
                }
        );
    });
});