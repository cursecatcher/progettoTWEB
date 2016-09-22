jQuery(document).ready(function ($) {
    $div_edit_pwd = $('#editpwd-div');

    $('#show-pwd').on('click', function (event) {
        $div_edit_pwd.css('visibility', 'visible');
    });

    $('#hide-pwd').on('click', function (event) {
        $div_edit_pwd.css('visibility', 'hidden');
    });

    $('#form-change-pwd').submit(function (event) {
        event.preventDefault(); //ajax 

        $vecchia = $(this).find("input[name=old-password]");
        $nuova = $(this).find("input[name=new-password]");
        $nuova2 = $(this).find("input[name=confirm-password]");


        var p1 = $(this).find("input[name=new-password]").val();
        var p2 = $(this).find("input[name=confirm-password]").val();

        if ($nuova.val() === $nuova2.val()) {
            $.post("Controller", $(this).serialize(), function (response) {
                if (response === "OK") {
                    $.growl.notice({
                        title: "Password modificata",
                        message: "La password &egrave; stata aggiornata con successo!"
                    });

                    $div_edit_pwd.css('visibility', 'hidden');
                    $vecchia.val("");
                    $nuova.val("");
                    $nuova2.val("");

                } else if (response === "ERR_OLD_PWD") {
                    $.growl.error({
                        title: "Errore",
                        message: "La vecchia password &egrave; incorretta!"
                    });

                    $vecchia.val("");

                } else if (response === "ERR_MISMATCH") {
                    $.growl.error({
                        title: "Errore",
                        message: "Le password inserite non coincidono!"
                    });

                    $nuova.val("");
                    $nuova2.val("");

                } else if (response === "ERR_UPDATE_FAILED") {
                    $.growl.error({
                        title: "Errore",
                        message: "Si &egrave; verificato un errore random!"
                    });
                }
            });
        } else {
            $.growl.error({
                title: "Errore",
                message: "Le password inserite non coincidono!"
            });

            $nuova.val("");
            $nuova2.val("");
        }
    });


});
