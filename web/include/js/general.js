function renderJSONPizza(json) {
    return "<div class='row' style='padding-left: 1em'>" +
            "<a class='remove-pizza btn btn-danger btn-xs' href='#0' " +
            "data-id-pizza='" + json.id_pizza + "'" + 
            "data-nome-pizza='" + json.nome_pizza + "'>" +
            "<span class='glyphicon glyphicon-minus'></span></a>" +
            json.quantity + "x <strong>" + json.nome_pizza + "</strong></div>";
}

function renderJSONCart(json) {
    var html = ""; 
    var i = 0; 
    
    $.each(json, function () {
        $.each(this, function (index, el) {
            console.log("Selezionata ->" + JSON.stringify(el));
            html += renderJSONPizza(el);
            i++; 
        });
    });
    
    if (i === 0) {
        html = "Carrello vuoto!"; 
    }
    
    return html; 
}


jQuery(document).ready(function ($) {
    /* aggiornamento dropdown carrello */
    var $cart = $('#cart-content'); //div 
    var $badge = $('#cart-badge'); //contatore elementi presenti nel carrello
    var $prezzo = $('#prezzo-tot'); //costo complessivo dell'ordine corrente
    var $header_tot = $('#cart-euro'); //prezzo ordine presente nell'header 
    //
    var $ordine = $('#carrello');
    
    var $form_action = $('#form-action');
    
    
    $('#disconnect').on("click", function() {
        $form_action.find("input[name=action]").val("logout");
        $form_action.submit();
        
    }); 
    
    
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
                       message: "<strong>+1x " + nomePizza + "</strong>",
                       location: "br"
                    });
                    
                    $cart.html(renderJSONCart(responseJSON.ordine));
                    $ordine.html(renderJSONCart(responseJSON.ordine));
                    $badge.text(responseJSON.tot_pizze);
                    $prezzo.text(responseJSON.prezzo_tot.toFixed(2));
                    $header_tot.text(responseJSON.prezzo_tot.toFixed(2));
                });


        console.log("Scelta pizza -  id: " + idpizza);

    });
    
    $(document).on('click', '.remove-pizza', function () {
        var idpizza = $(this).data("id-pizza");
        var nomePizza = $(this).data("nome-pizza"); 

        $.post("Controller",
                {
                    action: "remove-to-cart",
                    id_pizza: idpizza
                },
                function (responseJSON) {
                    $.growl.warning({
                       title: "Pizza rimossa dal carrello!", 
                       message: "<strong>-1x " + nomePizza + "</strong>",
                       location: "br" //basso a destra
                    });
                    
                    $cart.html(renderJSONCart(responseJSON.ordine));
                    $ordine.html(renderJSONCart(responseJSON.ordine));
                    $badge.text(responseJSON.tot_pizze);
                    $prezzo.text(responseJSON.prezzo_tot.toFixed(2));
                    $header_tot.text(responseJSON.prezzo_tot.toFixed(2));
                });
    });
    
    
}); 