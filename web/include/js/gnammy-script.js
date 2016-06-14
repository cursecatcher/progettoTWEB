jQuery(document).ready(function ($) {
    $form_ingredienti = $('#form_newingr');
    $form_pizza = $('#form_newpi');


    $('#form_newingr').submit(function(event) {
       console.log('submit!!!') ;
    });

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
    
    $('#submit-newpi').on('click', function(event) {
        event.preventDefault();
        
        var nome = $form_pizza.find("input[name=nome]").val().trim();
        var prezzo = $form_pizza.find("input[name=prezzo]").val(); 
        var ingredienti = [];
        
        $("select option:selected").each(function() {
            ingredienti.push($(this).text());
        }); 
        
        console.log("ingredienti: " + ingredienti + "!");
        /* inviare alla servlet nomi o id degli ingredienti?? 
         * - meglio mandare gli id e successivamente controllare se per ciascuno 
         * esiste l'ingrediente corrispondente - transazione?? 
         * - se tutto va bene si inserisce la pizza (dovrebbe andare bene), 
         * altrimenti annulla robe 
         */
        
        console.log("click: " + nome + ", " + prezzo);
    });
    
});