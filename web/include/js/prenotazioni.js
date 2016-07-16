function Prenotazione() {
    this.cliente = -1;
    this.data = ""; //???
    this.pizze = [];
    
    this.addPizza = function (pizza, quantity) {
        var i = this.pizze.findIndex(function (x) { 
            return x.id_pizza === pizza
        });

        if (i >= 0) {
            this.pizze[i].quantity = quantity;
        } else {
            this.pizze.push({
                id_pizza: pizza,
                quantity: quantity
            });
        }
    }

    this.removePizza = function (id_pizza) {
        this.pizze = this.pizze.filter(function (x) {
            return x.id_pizza != id_pizza;
        });
    }

    this.editPizza = function (id_pizza, quantity) {
        this.removePizza(id_pizza);
        this.addPizza(id_pizza, quantity);
    }
}

var ordine = new Prenotazione();

jQuery(document).ready(function ($) {

    $('.choose-pizza').on('click', function () {
        var pizza = $(this).attr("id").replace("pizza-", "");
        ordine.addPizza(pizza, pizza % 3 + 1);
        console.log("Scelta pizza -  id: " + pizza);
        console.log("Stato ordine: " + JSON.stringify(ordine));



    });
}); 