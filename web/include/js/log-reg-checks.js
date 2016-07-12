/* SPONSORED BY PATATA */

var email_regex = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;

jQuery(document).ready(function ($) {
    var $form_login = $('#form_login');
    var $form_reg = $('#form_reg');

    $('#submit-login').on('click', function (event) {
        var email = $form_login.find('input[name=email]').val().trim();
        var password = $form_login.find('input[name=password]').val();

        if (email_regex.test(email) && password !== "") {
            $form_login.submit();
        }
    });

/*
    $('#submit-login').on('click', function (event) {
        event.preventDefault();

        var email = $form_login.find('input[name=email]').val().trim();
        var password = $form_login.find('input[name=password]').val();

        if (email_regex.test(email)) {
            $.post(
                    //url 
                    $form_login.attr("action"),
                    //data
                            {
                                action: 'user-login',
                                email: email,
                                password: password
                            },
                    //callback
                            function (response) {
                                if (response === "OK") {

                                } else {
                                    console.log("response: " + response);
                                }
                            });
                } else {
            console.log("not valid email address");
        }

        console.log(email, password);



    });*/

    $('#submit-reg').on('click', function (event) {
        event.preventDefault();

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
                        }
                );
            } else {
                console.log("Password mismatch");
            }
        } else {
            console.log("not valid email address");
        }

    });
});