jQuery(document).ready(function ($) {
    $form_ingredienti = $('#form_newingr');
    $form_pizza = $('#form_newpi');
    

    $('#form_newingr').submit(function (event) {
        console.log('submit!!!');
    });

    $('#submit-newingr').on('click', function (event) {
        event.preventDefault();

        var name = $form_ingredienti.find("input[name=nome]").val().trim();
        var price = $form_ingredienti.find("input[name=prezzo]").val();
        var $div = $('#result-op'); 

        console.log("nome ingrediente" + name);
        console.log("serialize:" + $form_ingredienti.serialize());



        /*validazione lato client*/
        $.post(
                $form_ingredienti.attr('action'),
                $form_ingredienti.serialize(),
                function (response) {
                    console.log("response -> " + response);
                    if (response === "OK") {
                        $div.removeClass();
                        $div.addClass("alert alert-success");
                        $div.text("Ingrediente inserito!");
                    }
                    else if (response === "ERR_DUPLICATE") {
                        $div.removeClass();
                        $div.addClass("alert alert-danger");
                        $div.text("Ingrediente gia' presente!");
                    }
                    else if (response === "ERR_SQL_EXCEPTION") {
                        console.log("WTF");
                        $div.removeClass();
                        $div.addClass("alert alert-danger");
                        $div.text("MACELLI SUL SERVER! SCAPPATEEEEE");
                    }
                    else {
                        console.log("???"); 
                    }
                    
                }
        );
    });

    $('#submit-newpi').on('click', function (event) {
        event.preventDefault();

        var nome = $form_pizza.find("input[name=nome]").val().trim();
        var prezzo = $form_pizza.find("input[name=prezzo]").val();
        var ingredienti = [];
        var $div = $('#result-op');

        $("select option:selected").each(function () {
            ingredienti.push($(this).val());
        });

        console.log("ingredienti: " + ingredienti + "!");
        /* inviare alla servlet nomi o id degli ingredienti?? 
         * - meglio mandare gli id e successivamente controllare se per ciascuno 
         * esiste l'ingrediente corrispondente - transazione?? 
         * - se tutto va bene si inserisce la pizza (dovrebbe andare bene), 
         * altrimenti annulla robe 
         */
        console.log($form_pizza.serialize());
        console.log("Join: " + ingredienti.join(","));


        $.post(
                $form_pizza.attr('action'),
                {
                    action: $form_pizza.find("input[name=action]").val(),
                    nome: nome,
                    prezzo: prezzo,
                    listaIngredienti: ingredienti.join(",")
                },
                function (response) {
                    if (response == "OK") {
                        console.log("qui");
                        $div.removeClass();
                        $div.addClass("alert alert-success");
                        $div.text("Pizza inserita!");

                    } else if (response == "ERR_DUPLICATE") {
                        console.log("quo");
                        $div.removeClass();
                        $div.addClass("alert alert-warning");
                        $div.text("Pizza gia' presente!");

                    } else if (response == "ERR_NO_INGREDIENTI") {
                        console.log("qua");
                        $div.removeClass();
                        $div.addClass("alert alert-danger");
                        $div.text("E gli ingredienti?");

                    } else if (response == "ERR_SQL_EXCEPTION") {
                        console.log("WTF");
                        $div.removeClass();
                        $div.addClass("alert alert-danger");
                        $div.text("MACELLI SUL SERVER! SCAPPATEEEEE");

                    }
                }
        );
        console.log("pizza: " + nome + ", " + prezzo + "€");
    });

});