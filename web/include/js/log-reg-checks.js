/* SPONSORED BY PATATA */

var email_regex = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
jQuery(document).ready(function ($) {
    var $form_login = $('#form_login');
    var $form_reg = $('#form_reg');

    $(function () {
        var $span = $('#result-message');
        var t = $span.text().trim();
        if (t !== "") {
            //         $('#result-container').removeClass("hidden"); 
            if (t === "WRONG_PASSWORD") {
                $.growl.error({
                    title: "ERRORE!",
                    message: "Password non corretta!"
                }); /*
                 $span.html("<h4>Attenzione!</h4>" + 
                 "Password incorretta. Riprova, sarai pi&ugrave; fortunato! ");*/
            } else if (t === "EMAIL_NOT_FOUND") {
                $.growl.warning({
                    title: "ATTENZIONE",
                    message: "L'email specificata non &egrave; associata a nessun account"
                }); /*
                 $span.html("<h4>Attenzione!</h4>" + 
                 "L'email specificata non &egrave; associata a nessun account. Per ora");*/
            }
        }


    });

//    $('#submit-login').on('click', function (event) {

    /*
     $form_login.submit(function (event) {
     var email = $form_login.find('input[name=email]').val().trim();
     //      var password = $form_login.find('input[name=password]').val();
     var testEmail = email_regex.test(email);
     //        var testPwd = password !== "";
     
     
     if (!testEmail) {
     $.growl.warning({
     title: "Email non valida",
     message: "nomeutente@!"
     });
     event.preventDefault();
     }*/
    /*
     if (!testEmail && !testPwd) {
     $.growl.warning({
     title: "ERRORE!!!!",
     message: "Compila tutti i campi, possibilmente bene!"
     });
     event.preventDefault();
     } else if (!testPwd) {
     $.growl.warning({
     title: "Password non valida",
     message: "Compila il campo password plz"
     });
     event.preventDefault();
     } else if (!testEmail) {
     $.growl.warning({
     title: "Email non valida",
     message: "La stringa inserita come email non &egrave; una email!"
     });
     event.preventDefault();
     }*/
    //   });


    $form_reg.submit(function (event) {
//   $('#submit-reg').on('click', function (event) {

        event.preventDefault();
        var $div = $('#result-container');
        var $span = $('#result-message');
        var email = $form_reg.find('input[name=email]').val().trim();
        var password = $form_reg.find('input[name=password]').val();
        var confirmPassword = $form_reg.find('input[name=password2]').val();
        if (email_regex.test(email)) {
            if (password === confirmPassword) {
                $.post($(this).attr('action'), $(this).serialize(), function (response) {
                    console.log("response: " + response);
                    $div.removeClass("alert-success alert-warning alert-danger hidden");
                    if (response === "OK") {
                        bootbox.alert({
                            title: "Registrazione avvenuta", 
                            message: "La registrazione &egrave; andata a buon fine!<br/>" + 
                                    "Verrai reindirizzato alla pagina di login alla chiusura di questa finestra.", 
                            callback: function() {
                                window.location.href = "login.jsp"
                            }
                        }); 
                        /*
                        $.growl.notice({
                            title: "Missione compiuta!",
                            message: "Registrazione effettuata con successo!"
                        });*/
                        /*
                         $div.addClass("alert-success");
                         $span.html("<h4>Registrazione effettuata con successo</h4>" +
                         "<a href='login.jsp'>Clicca qui</a> per accedere!");*/
                    } else if (response === "USER_ALREADY_EXISTS") {
                        $.growl.warning({
                            title: "Indirizzo email non disponibile",
                            message: "L'email " + email + " &egrave gi&agrave; registrata!"
                        });
                        /*
                         $div.addClass("alert-danger");
                         $span.html("<h4>Attenzione</h4>" +
                         "L'email " + email + " &egrave; gi&agrave; registrata!");*/
                    } else if (response === "PASSWORD_MISMATCH") {
                        $.growl.warning({
                            title: "Password mismatch",
                            message: "Le due password non coincidono!"
                        }); /*
                         $div.addClass("alert-warning");/*
                         $span.html("<h4>Attenzione</h4>" +
                         "Le password non coincidono");*/
                    }

                }
                );
            } else {
                $.growl.warning({
                    title: "Password mismatch",
                    message: "Le due password non coincidono!"
                });
                console.log("Password mismatch");
            }
        } else {
            $.growl.error({
                title: "Email non valida",
                message: "L'email inserita &egrave; boh"
            });
            console.log("not valid email address");
        }

    });
});