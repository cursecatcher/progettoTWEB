function Prenotazione() {
    this.cliente = -1;
    this.data = ""; //???
    this.pizze = [];

    this.addPizza = function (id_pizza, nome_pizza) {
        var i = this.pizze.findIndex(function (x) {
            return x.id_pizza === id_pizza;
        });

        if (i >= 0) {
            this.pizze[i].quantity++;
        } else {
            this.pizze.push({
                id_pizza: id_pizza,
                nome: nome_pizza,
                quantity: 1
            });
        }
    };

    this.removePizza = function (id_pizza) {
        var i = this.pizze.findIndex(function (x) {
            return x.id_pizza === id_pizza;
        });

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

    this.print = function ($div) {
        var content = "";

        for (var i = 0; i < this.pizze.length; i++) {
            content += "<li>" +
                    "<a class='remove-pizza' data-id-pizza='" + this.pizze[i].id_pizza + "' href='#0'>" +
                    "<span class='glyphicon glyphicon-minus'></span></a>" +
                    this.pizze[i].quantity + "x " + this.pizze[i].nome +
                    "</li>";

        }

        $div.html("<ul>" + content + "</ul>");
    };
}

var ordine = new Prenotazione();

jQuery(document).ready(function ($) {
    $datapicker = $('#datapicker');
    $timepicker = $('#timepicker');

    $datapicker.datepicker({
        showOn: "both", /*
         buttonImage: "images/calendario.png",
         buttonImageOnly: true,*/
        minDate: 0,
        monthNames: ["Gennaio", "Febbraio", "Marzo", "Aprile", "Maggio", "Giugno", "Luglio", "Agosto", "Settembre", "Ottobre", "Novembre", "Dicembre"],
        dayNamesMin: ["Do", "Lu", "Ma", "Me", "Gi", "Ve", "Sa"],
        dateFormat: "dd-mm-yy"
    });

    $timepicker.timepicker({
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

        ordine.addPizza(idpizza, nomePizza);
        console.log("Scelta pizza -  id: " + idpizza);
        console.log("Stato ordine: " + JSON.stringify(ordine));
        ordine.print($("#carrello"));
    });

    $(document).on('click', '.remove-pizza', function () {
        var idpizza = $(this).data("id-pizza");
//        var idpizza = $(this).attr("id").replace("del-pizza-", "");

        ordine.removePizza(idpizza);
        ordine.print($("#carrello"));
        console.log("Stato ordine: " + JSON.stringify(ordine));
    });


    $('#form-carrello').on('submit', function () {
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
            return false;
        }
    });
}); 