/*
function Prenotazione() {
    this.cliente = -1;
    this.data = ""; //???
    this.pizze = [];
    this.spesa = parseFloat(0);

    this.addPizza = function (id_pizza, nome_pizza, prezzo_pizza) {
        //verifica se la pizza è già stata scelta precedentemente nell'ordine
        var i = this.pizze.findIndex(function (x) {
            return x.id_pizza === id_pizza;
        });

        if (i >= 0) {
            this.pizze[i].quantity++;
        } else {
            this.pizze.push({
                id_pizza: id_pizza,
                nome: nome_pizza,
                prezzo: parseFloat(prezzo_pizza),
                quantity: 1
            });
        }
        this.spesa += parseFloat(prezzo_pizza);
    };

    this.removePizza = function (id_pizza) {
        var i = this.pizze.findIndex(function (x) {
            return x.id_pizza === id_pizza;
        });

        this.spesa -= this.pizze[i].prezzo;

        if (this.pizze[i].quantity > 1) {
            this.pizze[i].quantity--;
        } else {
            this.pizze = this.pizze.filter(function (x) {
                return x.id_pizza !== id_pizza;
            });
        }
    };

    this.length = function () {
        return this.pizze.length;
    };

    this.print = function ($div_order, $div_prezzo) {
        var content = "";

        for (var i = 0; i < this.length(); i++) {
            content += "<li>" +
                    "<a class='remove-pizza' href='#0' " +
                    "data-id-pizza='" + this.pizze[i].id_pizza + "'>" +
                    "<span class='glyphicon glyphicon-minus'></span></a>" +
                    this.pizze[i].quantity + "x " + this.pizze[i].nome +
                    "</li>";

            console.log("Prezzooo: " + this.spesa + "€");

        }

        $div_order.html("<ul>" + content + "</ul>");
        $div_prezzo.text(this.spesa.toFixed(2));
    };
}

var ordine = new Prenotazione();*/

jQuery(document).ready(function ($) {
    var $cart = $('#carrello');
    var $badge = $('#cart-badge');


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





    $('.choose-pizza').on('click', function () {
        var idpizza = $(this).data("id-pizza");
        var nomePizza = $(this).data("nome-pizza");
        var prezzo = $(this).data("prezzo-pizza");

        //ordine.addPizza(idpizza, nomePizza, prezzo);
        //    ordine.print($("#carrello"), $('#prezzo-tot'));


        $.post("Controller",
                {
                    action: "add-to-cart",
                    id_pizza: idpizza,
                    nome: nomePizza,
                    prezzo: prezzo
                },
                function (responseJSON) {
                    $badge.text(responseJSON.tot_pizze);
                    var content = ""; 

                    $.each(responseJSON.ordine, function () {
                        $.each(this, function (index, el) {
                            console.log("nome=" + index + ", value=" + JSON.stringify(el));
                            content += "<li>" + 
                                    "<a class='remove-pizza' href='#0' "  + 
                                    "data-id-pizza='" + el.id_pizza + "'>" + 
                                    "<span class='glyphicon glyphicon-minus'></span></a>" + 
                                    el.quantity + "x " + el.nome_pizza + "</li>"; 
                        });
                    });
                    
                    $cart.html(content); 

                });


        console.log("Scelta pizza -  id: " + idpizza);

    });

    $(document).on('click', '.remove-pizza', function () {
        var idpizza = $(this).data("id-pizza");
        var prezzo = $(this).data("prezzo-pizza");

        $.post("Controller",
                {
                    action: "remove-to-cart",
                    id_pizza: idpizza
                },
                function (responseJSON) {
                    $badge.text(responseJSON.tot_pizze);
                    var content = ""; 

                    $.each(responseJSON.ordine, function () {
                        $.each(this, function (index, el) {
                            console.log("nome=" + index + ", value=" + JSON.stringify(el));
                            content += "<li>" + 
                                    "<a class='remove-pizza' href='#0' "  + 
                                    "data-id-pizza='" + el.id_pizza + "'>" + 
                                    "<span class='glyphicon glyphicon-minus'></span></a>" + 
                                    el.quantity + "x " + el.nome_pizza + "</li>"; 
                        });
                    });
                    
                    $cart.html(content); 
                });
/*
        ordine.removePizza(idpizza, prezzo);
        ordine.print($("#carrello"), $('#prezzo-tot'));

        console.log("Stato ordine: " + JSON.stringify(ordine));*/
    });


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
        }*/
    });
}); 