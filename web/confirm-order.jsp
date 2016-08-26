<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>

        <script src="https://code.jquery.com/jquery-2.2.4.min.js"   
                integrity="sha256-BbhdlvQf/xTY9gja0Dq3HiwQF8LaCRTXxZKRutelT44="   
        crossorigin="anonymous"></script>

        <!-- Latest compiled and minified CSS -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" 
              integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" 
              crossorigin="anonymous">

        <!-- Optional theme -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css" 
              integrity="sha384-fLW2N01lMqjakBkx3l/M9EahuwpSfeNvV63J5ezn3uZzapT0u7EYsXMjQV+0En5r" 
              crossorigin="anonymous">

        <link rel="stylesheet" href="include/css/style.css">

        <!-- Latest compiled and minified JavaScript -->
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" 
                integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" 
        crossorigin="anonymous"></script>

        <!-- DataPicker jQuery -->
        <script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
        <link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
        <!-- TimePicker -->
        <link rel="stylesheet" href="include/lib/timepicker/jquery.timepicker.css">
        <script type="text/javascript" src="include/lib/timepicker/jquery.timepicker.min.js"></script>
        <!-- DatePicker -->
        <link rel="stylesheet" href="include/lib/datepicker/bootstrap-datepicker3.min.css">
        <script type="text/javascript" src="include/lib/datepicker/bootstrap-datepicker.min.js"></script>
        <!--growl -->
        <script type="text/javascript" src="include/lib/jquery-growl/jquery.growl.js"></script>
        <link rel="stylesheet" href="include/lib/jquery-growl/jquery.growl.css" type="text/css" />
        <!-- jQuery dropdown -->
        <link type="text/css" rel="stylesheet" href="include/lib/jquery-dropdown/jquery.dropdown.min.css" />
        <script type="text/javascript" src="include/lib/jquery-dropdown/jquery.dropdown.min.js"></script>

        <script type="text/javascript" src="include/js/prenotazioni.js"></script>
        <script type="text/javascript" src="include/js/general.js"></script>

        <script type="text/javascript">
            $(function () {
                var check = $('#__error').text().trim();

                if (check === "ERR_PARSE_DATETIME") {
                    $.growl.error({
                        title: "Errore",
                        message: "L'orario inserito non &egrave; valido!"
                    });
                }
            });
        </script>

    </head>
    <body>
        <%@include file="include/header.jsp" %>

        <div class="container">
            <div class="col-md-1"></div>
            <div class="col-md-5">

                <div class="page-header">
                    <h3>Indica l'indirizzo di consegna</h3>
                </div>
                <form action="Controller" method="POST">
                    <input type="hidden" name="action" value="add-prenotazione"/>

                    <div class="form-group">
                        <label for="nominativo">Nome completo</label>
                        <input type="text" id="nominativo" name="nominativo" 
                               class="form-control" required
                               value="${nominativo}"
                               placeholder="Il nome sul citofono"/>
                    </div>
                    <div class="form-group">
                        <label for="indirizzo">Indirizzo di consegna</label>
                        <input type="text" id="indirizzo" name="indirizzo"
                               class="form-control" required
                               value="${indirizzo}"
                               placeholder="Indirizzo di consegna"/>
                    </div>

                    <div class="page-header">
                        <h3>Quando devono essere consegnate?</h3>
                    </div>
                    <div class="form-group">
                        <label for="data">Data consegna</label>
                        <div id="datepicker" class="input-group date">
                            <input id="data" name="dataConsegna" type="text" 
                                   class="form-control" 
                                   value="${data}"
                                   required=""/>
                            <span class="input-group-addon">
                                <i class="glyphicon glyphicon-th"></i>
                            </span>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="orario">Ora di consegna</label>
                        <div id="timepicker" class="input-group">
                            <input id="orario" type="text" name="oraConsegna"
                                   placeholder="hh:mm" class="form-control" required/> 
                            <span class="input-group-addon">
                                <i class="glyphicon glyphicon-time"></i>
                            </span>
                        </div>
                    </div> 
                    <input type="submit" name="submit" value="Ordina!" class="btn btn-success btn-block"/>
                </form>
            </div>
            <div class="col-md-1"></div>
            <div class="col-md-4">
                <div>
                    <div class="page-header">
                        <h3>Riepilogo ordine</h3>
                    </div>

                    <c:forEach var="el" items="${carrello.ordine}">
                        <div class="row">
                            <div class="col-md-9">
                                <span class="quantity">
                                    <c:out value="${el.quantity}"/>
                                </span>
                                x
                                <span class="name">
                                    <c:out value="${el.nome}"/>
                                </span>
                            </div>
                            <div class="col-md-3 text-muted">
                                <fmt:formatNumber type="number" 
                                                  value="${el.prezzoParziale}" minFractionDigits="2"/>&nbsp;&euro;
                            </div>
                        </div>
                    </c:forEach>

                    <hr>
                    <div class="row">
                        <div class="col-md-9">
                            <h3>Totale</h3>
                        </div>
                        <div class="col-md-3">
                            <h3>
                                <small>
                                    <fmt:formatNumber type="number" 
                                                      value="${carrello.prezzoTotale}" minFractionDigits="2"/>&nbsp;&euro;
                                </small>
                            </h3>

                        </div>
                    </div>
                    <hr>


                    <a href="newordine.jsp" class="btn btn-primary btn-block">
                        Modifica ordine
                    </a>
                </div>
            </div>
        </div>
    </body>
</html>
