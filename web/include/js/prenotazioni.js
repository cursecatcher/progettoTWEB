function renderJSONPizza(json) {
    return "<div class='row'>" +
            "<a class='remove-pizza' href='#0' " +
            "data-id-pizza='" + json.id_pizza + "'" + 
            "data-nome-pizza='" + json.nome_pizza + "'>" +
            "<span class='glyphicon glyphicon-minus'></span></a>" +
            json.quantity + "x <strong>" + json.nome_pizza + "</strong></div>";
}

function renderJSONCart(json) {
    var html = ""; 
    
    $.each(json, function () {
        $.each(this, function (index, el) {
            console.log("Selezionata ->" + JSON.stringify(el));
            html += renderJSONPizza(el);
        });
    });
    
    return html; 
}

jQuery(document).ready(function ($) {
    var $cart = $('#carrello');
    var $badge = $('#cart-badge');
    var $prezzo = $('#prezzo-tot');
    
    var $header_tot = $('#cart-euro'); 


    $('#datepicker').datepicker({
        format: "dd-mm-yyyy",
        weekStart: 1,
        language: "it",
        autoclose: true,
        todayHighlight: true
    });

    $('#orario').timepicker({
        minTime: '19:30',
        maxTime: '23:30',
        forceRoundTime: true,
        show2400: true,
        timeFormat: 'H:i',
        step: 15
    });
    
    $(function () {
        if ($('#error').text().trim() === "FAIL") {
            $.growl.error({
                title: "Carrello vuoto", 
                message: "Devi inserire almeno un prodotto nel carrello per effettuare l'ordine!", 
                location: "tc"
            });
        }
    }); 




/*
    $('.choose-pizza').on('click', function () {
        var idpizza = $(this).data("id-pizza");
        var nomePizza = $(this).data("nome-pizza");
        var prezzo = $(this).data("prezzo-pizza");
        

        $.post("Controller",
                {
                    action: "add-to-cart",
                    id_pizza: idpizza,
                    nome: nomePizza,
                    prezzo: prezzo
                },
                function (responseJSON) {
                    $.growl.notice({
                       title: "Pizza aggiunta al carrello!", 
                       message: "+1x " + nomePizza, 
                       location: "br"
                    });
                    
                    $cart.html(renderJSONCart(responseJSON.ordine));
                    $badge.text(responseJSON.tot_pizze);
                    $prezzo.text(responseJSON.prezzo_tot.toFixed(2));
                    $header_tot.text(responseJSON.prezzo_tot.toFixed(2));
                });


        console.log("Scelta pizza -  id: " + idpizza);

    });*/

/*
    $('#form-carrello').on('submit', function (event) {
        event.preventDefault();
        console.log("eheheh");
        /*
         console.log($(this).serialize());
         
         var data = $(this).find("input[name=dataConsegna]").val();
         var ora = $(this).find("input[name=oraConsegna]").val();
         
         
         
         if (ordine.length() > 0) {
         var inputs = [];
         //   inputs.push("<input type='number' name='num_pizze' value='" + ordine.length() + "'");
         $('#form-carrello').find('input[name=num_pizze]').val(ordine.length());
         
         for (var i = 0; i < ordine.length(); i++) {
         inputs.push("<input type='text' name='id_pizza-" + i + "' value='" + ordine.pizze[i].id_pizza + "'/>");
         inputs.push("<input type='text' name='quantity-" + i + "' value='" + ordine.pizze[i].quantity + "'/>");
         }
         
         $(this).append(inputs.join(""));
         } else {
         console.log("ordine vuoto");
         event.preventDefault();
         }
    });*/
}); 