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
                });/*
                 $span.html("<h4>Attenzione!</h4>" + 
                 "Password incorretta. Riprova, sarai pi&ugrave; fortunato! ");*/
            } else if (t === "EMAIL_NOT_FOUND") {
                $.growl.warning({
                    title: "ATTENZIONE",
                    message: "L'email specificata non &egrave; associata a nessun account"
                });/*
                 $span.html("<h4>Attenzione!</h4>" + 
                 "L'email specificata non &egrave; associata a nessun account. Per ora");*/
            }
        }


    });

    $('#submit-login').on('click', function (event) {
        var email = $form_login.find('input[name=email]').val().trim();
        var password = $form_login.find('input[name=password]').val();
        var testEmail = email_regex.test(email);
        var testPwd = password !== "";

        if (testEmail && testPwd) {
            $form_login.submit();
        } else if (!testEmail && !testPwd) {
            $.growl.warning({
                title: "ERRORE!!!!",
                message: "Compila tutti i campi, possibilmente bene!"
            });
        } else if (!testPwd) {
            $.growl.warning({
                title: "Password non valida",
                message: "Compila il campo password plz"
            });
        } else {
            $.growl.warning({
                title: "Email non valida",
                message: "La stringa inserita come email non &egrave; una email!"
            });

        }
    });



    $('#submit-reg').on('click', function (event) {
        event.preventDefault();

        var $div = $('#result-container');
        var $span = $('#result-message');

        var email = $form_reg.find('input[name=email]').val().trim();
        var password = $form_reg.find('input[name=password]').val();
        var confirmPassword = $form_reg.find('input[name=password2]').val();

        if (email_regex.test(email)) {
            if (password === confirmPassword) {
                $.post(
                        $form_reg.attr('action'),
                        {
                            action: 'user-registrazione',
                            email: email,
                            password: password,
                            password2: confirmPassword
                        },
                        function (response) {
                            console.log("response: " + response);
                            $div.removeClass("alert-success alert-warning alert-danger hidden");

                            if (response === "OK") {
                                $.growl.notice({
                                    title: "Missione compiuta!",
                                    message: "Registrazione effettuata con successo!"
                                });
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
                                });/*
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