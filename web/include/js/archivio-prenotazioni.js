jQuery(document).ready(function ($) {
    $(".confirm-deliver").on("click", function () {
        var idp = $(this).data("id");
        console.log("Confirm deliver n. " + idp);
        bootbox.confirm({
            title: "Conferma ricezione ordine",
            message: "Sei sicuro di voler contrassegnare l'ordine come ricevuto?",
            buttons: {
                confirm: {
                    label: "Conferma ricezione"
                }, 
                cancel: {
                    label: "Annulla operazione"
                }
                
            }, 
            callback: function (choice) {
                if (choice) {
                    $.post(
                            "Controller",
                            {
                                action: "confirm-deliver",
                                idp: idp
                            },
                            function (response) {
                                if (response === "OK") {
                                    $.growl.notice({
                                        title: "Ricezione confermata!",
                                        message: "La tua prenotazione &egrave; stata confermata!<br/>" +
                                                "Sta senza pensieri!"
                                    });
                                } else {
                                    $.growl.error({
                                        title: "Si &egrave; verificato un errore :(",
                                        message: "Ops, qualcosa e' andato storto!"
                                    });
                                }
                            }); //end $.post
                }//end if
            }}); //end bootbox
    }); //end confirm-deliver event listener

    $(".delete-deliver").on("click", function () {
        var idp = $(this).data("id");
        console.log("Delete deliver n. " + idp);
        bootbox.confirm({
            title: "Cancellazione prenotazione",
            message: "Sei sicuro di voler cancellare la prenotazione?",
            buttons: {
                confirm: {
                    label: "Cancella prenotazione"
                }, 
                cancel: {
                    label: "Annulla operazione"
                }
                
            },
            callback: function (choice) {
                if (choice) {
                    $.post(
                            "Controller",
                            {
                                action: "delete-deliver",
                                idp: idp
                            },
                            function (response) {
                                console.log("Response: " + response);
                                if (response === "OK") {
                                    $("#pren-" + idp).addClass("hide");
                                    $.growl.notice({
                                        title: "Prenotazione annullata!",
                                        message: "La tua prenotazione &egrave; stata annullata!<br/>" +
                                                "Sta senza pensieri!"
                                    });
                                } else {
                                    $.growl.error({
                                        title: "Si &egrave; verificato un errore :(",
                                        message: "Ops, qualcosa e' andato storto!"
                                    });
                                }
                            }); //end $.post
                }//end if
            }}); //end bootbox
    }); //end delete-deliver event listener


});
