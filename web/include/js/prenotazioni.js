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

        if (this.pizze[i] > 1) {
            this.pizze[i]--;
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
            content += "<div>" +
                    this.pizze[i].quantity + "x " + this.pizze[i].nome +
                    "</div>";

        }

        $div.html(content);
    };
}

var ordine = new Prenotazione();

jQuery(document).ready(function ($) {

    $('.choose-pizza').on('click', function () {
        var idpizza = $(this).attr("id").replace("pizza-", "");
        var nomePizza = $(this).next().text().trim();

        ordine.addPizza(idpizza, nomePizza);
        console.log("Scelta pizza -  id: " + idpizza);
        console.log("Stato ordine: " + JSON.stringify(ordine));
        ordine.print($("#carrello"));



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