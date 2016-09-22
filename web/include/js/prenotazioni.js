jQuery(document).ready(function ($) {
    // init datepicker
    $('#datepicker').datepicker({
        format: "dd-mm-yyyy",
        weekStart: 1,
        language: "it",
        autoclose: true,
        todayHighlight: true
    });
    // init timepicker
    $('#orario').timepicker({
        minTime: '19:30',
        maxTime: '23:30',
        forceRoundTime: true,
        show2400: true,
        timeFormat: 'H:i',
        step: 15
    });

    $(function () {
        if ($('#error').text().trim() === "FAIL") {
            $.growl.error({
                title: "Carrello vuoto",
                message: "Devi inserire almeno un prodotto nel carrello per effettuare l'ordine!",
                location: "tc"
            });
        }
    });

    $('#cancel-order').on('click', function (event) {
        bootbox.confirm({
            title: "Conferma annullamento ordine",
            message: "Sei sicuro di voler annullare l'ordine in corso?",
            buttons: {
                confirm: {
                    label: "S&igrave;"
                },
                cancel: {
                    label: "No"
                }
            }, callback: function (choice) {
                if (choice) {
                    $.post("Controller", {action: "delete-order"}, function (response) {
                        console.log(response);

                        if (response === "OK") {
                            bootbox.alert({
                                title: "Ordine annullato",
                                message: "Premi ok per tornare al profilo",
                                callback: function () {
                                    window.location.href = "profilo.jsp";
                                }
                            });

                        } else {
                            bootbox.alert({
                                title: "Si &egrave; verificato un errore",
                                message: "Sessione scaduta, si prega di rieffettuare il login",
                                callback: function () {
                                    window.location.href = "login.jsp";
                                }
                            });
                        }
                    });
                }
            }
        });



    });

}); 