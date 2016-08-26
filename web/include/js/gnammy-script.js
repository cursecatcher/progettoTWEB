jQuery(document).ready(function ($) {
    var $form_ingredienti = $('#form_newingr');
    var $form_pizza = $('#form_newpi');
    var $edit_pizza = $('#editpi-form');
    var $error = $('#__error');


    $form_pizza.submit(function (event) {
        event.preventDefault(); //uso ajax
        var nome = $(this).find("input[name=nome]").val().trim();

        if (!$("#select-ingredienti").val()) {
            $.growl.error({
                title: "Errore!",
                message: "Nessun ingrediente specificato!"
            });
        } else {
            $.post("Controller", $(this).serialize(), function (response) {
                console.log("Response: " + response);
                if (response === "OK") {
                    $.growl.notice({
                        title: "Operazione eseguita con successo",
                        message: "La pizza <strong>" + nome + "</strong> &egrave; aggiunta al menu",
                        location: "br"
                    });
                    $('#catalogo').load("Controller?action=ajax-update-admin-pizzas");

                    $('#nome_pizza_create').val("");
                    $('#prezzo_pizza_create').val("");
                    $('#select-ingredienti').searchableOptionList().deselectAll();

                } else if (response === "ERR_MISSING_NAME") {
                    $.growl.error({
                        title: "Errore nei dati inseriti",
                        message: "Inserisci un nome da dare alla pizza!"
                    });
                } else if (response === "ERR_PARSE_FLOAT") {
                    $.growl.error({
                        title: "Errore nel campo prezzo",
                        message: "Il prezzo dev'essere un valore numerico maggiore di zero!"
                    });
                } else if (response === "ERR_DUPLICATE") {
                    $.growl.warning({
                        title: "Inserimento fallito",
                        message: "La pizza <strong>" + nome + "</strong> &egrave; gi&agrave; presente nel DB"
                    });
                } else if (response === "ERR_NO_INGREDIENTI") {
                    $.growl.error({
                        title: "Errore!",
                        message: "Nessun ingrediente specificato!"
                    });
                } else if (response === "ERR_SQL_EXCEPTION") {
                    $.growl.error({
                        title: "Errore sul server",
                        message: "Si &egrave; verificato un errore inatteso sul server, boh!"
                    });
                }
            });
        }
    });


    $edit_pizza.submit(function (event) {
        event.preventDefault(); //ajax

        var nome = $(this).find("input[name=nome]").val();
        var prezzo = $(this).find("input[name=prezzo]").val();
        var flag = true;

        if (nome.trim() === "") {
            $.growl.error({
                title: "Errore!",
                message: "Inserisci un nome da dare alla pizza!"
            });
            flag = false;

        } else if (prezzo.trim() === "") {
            $.growl.error({
                title: "Errore!",
                message: "Inserisci il campo prezzo!"
            });
            flag = false;

        } else if (!$("#select-ingredienti").val()) {
            $.growl.error({
                title: "Errore!",
                message: "Inserisci almeno un ingrediente!"
            });
            flag = false;
        }

        if (flag) {
            $.post("Controller", $(this).serialize(), function (response) {
                if (response === "OK") {
                    bootbox.alert({
                        title: "Pizza <strong>" + nome + "</strong> aggiornata",
                        message: "I dati della pizza sono stati aggiornati<br/>" +
                                "Verrai reindirizzato al catalogo della pizzeria alla chiusura di questa finestra",
                        callback: function () {
                            window.location.href = "newpizza.jsp"
                        }
                    });
                } else if (response === "ERR_UNAVAIABLE_NAME") {
                    $.growl.error({
                        title: "ERRORE",
                        message: "Il nome inserito non &egrave; disponibile!"
                    });
                }
            });
        }
    });

    $form_ingredienti.submit(function (event) {
        event.preventDefault(); //ajax
        var name = $(this).find("input[name=nome]").val().trim();

        $.post("Controller", $(this).serialize(), function (response) {
            console.log("response -> " + response);
            if (response === "OK") {
                $.growl.notice({
                    title: "OK",
                    message: "Ingrediente <strong>" + name + "</strong> inserito con successo!"
                });
                $('#container-ingredienti').load("Controller?action=ajax-html-ingr");
                
                $('#nome-ingrediente').val(""); 
                $('#prezzo-ingrediente').val("");

            } else if (response === "ERR_DUPLICATE") {
                $.growl.warning({
                    title: "Ingrediente gi&agrave; presente",
                    message: "&Egrave; gi&agrave; presente un ingrediente con lo stesso nome"
                });
            } else if (response === "ERR_SQL_EXCEPTION") {
                $.growl.warning({
                    title: "WTF",
                    message: "Macelli sul server! Contattare l'amministratore e gg"
                });
            } else {
                console.log("???");
            }
        });
    });

    $(document).on('click', '.edit-link', function () {
        var $edit_form = $('#edit-req');
        var id = $(this).data('id');
        $edit_form.find("input[name=id]").val(id);
        $edit_form.submit();
    });

    $(document).on('click', '.delete-link', function () {
        var id = $(this).data('id');
        $error.empty();

        bootbox.confirm({
            title: "Conferma cancellazione pizza",
            message: "Sei sicuro di voler rimuovere la pizza " + id + "?",
            buttons: {
                confirm: {
                    label: "Conferma cancellazione"
                },
                cancel: {
                    label: "Annulla operazione"
                }
            },
            callback: function (choice) {
                if (choice) {
                    var data = {
                        action: "pizza-remove",
                        id_pizza: id
                    };

                    $.post("Controller", data, function (response) {
                        console.log("response ->" + response);
                        if (response === "OK") {
                            $.growl.notice({
                                title: "OK",
                                message: "Pizza rimossa dal menu"
                            });

                            $('#pizza-' + id).addClass('hide');

                        } else if (response === "OK_PIZZA_UNAVAILABLE") {
                            $.growl.notice({
                                title: "OK",
                                message: "Pizza rimossa dal menu"
                            });

                            $('#pizza-' + id).addClass('hide');

                        } else if (response === "ERR_NO_RECORD") {
                            $.growl.error({
                                title: "ERRORE",
                                message: "Errore nell'elaborazione della richiesta"
                            });

                        } else if (response === "ERR_SQL_EXCEPTION") {
                            $.growl.error({
                                title: "ERRORE",
                                message: "Errore nell'esecuzione della query"
                            });
                        }
                    });
                }
            }
        });
    });
});