jQuery(document).ready(function ($) {
    $form_ingredienti = $('#form_newingr');
    $form_pizza = $('#form_newpi');
    $edit_pizza = $('#form_editpi'); 
    

    $(".edit-link").on('click', function() {
        var nome = $(this).data('nome'); 
        var id = $(this).data('id'); 
        var prezzo = $(this).data('prezzo'); 
        var listaIngredienti = $(this).data('ingredienti'); 
        
        console.log("Nome: " + nome); 
        console.log("id: " + id); 
        console.log("prezzo:" + prezzo); 
        console.log("ingredienti: " + listaIngredienti);
        
        $('#id_pizza_edit').val(id); 
        $('#nome_pizza_edit').val(nome); 
        $('#prezzo_pizza_edit').val(prezzo);  
        
        
        
    }); 

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
                        $.growl.notice({
                            title: "OK", 
                            message: "Ingrediente <strong>" + name + "</strong> inserito con successo!"
                        });
                        /*
                        $div.removeClass();
                        $div.addClass("alert alert-success");
                        $div.text("Ingrediente inserito!");*/
                    }
                    else if (response === "ERR_DUPLICATE") {
                        $.growl.warning({
                            title: "Ingrediente gi&agrave; presente", 
                            message: "L'ingrediente <strong>" + name + "</strong> &egrave; gi&agrave; presente nel DB"
                        });
                        /*
                        $div.removeClass();
                        $div.addClass("alert alert-danger");
                        $div.text("Ingrediente gia' presente!");*/
                    }
                    else if (response === "ERR_SQL_EXCEPTION") {
                        $.growl.warning({
                            title: "WTF", 
                            message: "Macelli sul server! Contattare l'amministratore e gg"
                        });/*
                        console.log("WTF");
                        $div.removeClass();
                        $div.addClass("alert alert-danger");
                        $div.text("MACELLI SUL SERVER! SCAPPATEEEEE");*/
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
        var $div = $('#result-container');
        var $span = $('#result-message'); 

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
                    $div.removeClass("alert-success alert-warning alert-danger hidden"); 
                    
                    if (response == "OK") {
                        $.growl.notice({
                            title: "T'appost!", 
                            message: "La pizza <strong>" + nome + "</strong> &egrave; stata inserita con successo nel DB"
                        });
                        /*
                        console.log("qui");
                        $div.addClass("alert-success"); 
                        $span.html("<h4>T'appost!</h4>" + 
                                "<p>La pizza specificata &egrave; stata inserita con successo nel DB.</br>" + 
                                "'Sta senza pensier.</p>"); */

                    } else if (response == "ERR_DUPLICATE") {
                        $.growl.warning({
                            title: "Inserimento fallito", 
                            message: "La pizza <strong>" + nome + "</strong> &egrave; gi&agrave; presente nel DB"
                        });
                        /*
                        console.log("quo");
                        $div.addClass("alert-warning"); 
                        $span.html("<h4>Inserimento non andato a buon fine</h4>" + 
                                "<p>La pizza <strong>" + nome + "</strong> &egrave; gi&agrave; presente nel menu.</br>" +
                                "Scegli un altro nome!</p>");  */

                    } else if (response == "ERR_NO_INGREDIENTI") {
                        $.growl.error({
                            title: "Errore!", 
                            message: "Nessun ingrediente specificato!"
                        });
                        /*
                        console.log("qua");
                        $div.addClass("alert-danger"); 
                        $span.html("<h4>Inserimento non andato a buon fine</h4>" + 
                                "<p></p>"); */

                    } else if (response == "ERR_SQL_EXCEPTION") {
                        $.growl.error({
                            title: "Errore sul server", 
                            message: "Si &egrave; verificato un errore inatteso sul server, boh!"
                        });
                        /*
                        console.log("WTF");
                        $div.addClass("alert-danger"); 
                        $span.html("<h4>Inserimento non andato a buon fine</h4>" + 
                                "<p>Si &egrave; verificato un errore inatteso sul server.</br>" + 
                                "Misteri dell'informatica</p>");  
                        $span.text(response); */
                    }
                }
        );
        console.log("pizza: " + nome + ", " + prezzo + "€");
    });

});