jQuery(document).ready(function ($) {
    $form_ingredienti = $('#form_newingr');
    $form_pizza = $('#form_newpi');


    $('#submit-newingr').on('click', function (event) {
        event.preventDefault();
        
        var name = $form_ingredienti.find("input[name=nome]").val().trim();
        var price = $form_ingredienti.find("input[name=prezzo]").val();

        /*validazione lato client*/

        $.post(
                $form_ingredienti.attr('action'),
                $form_ingredienti.serialize(),
                function (response) {
                    console.log("response -> " + response); 
                    if (response === "OK") {
                        ;
                    }
                    else {
                        ;
                    }
                }
        );
    });
});