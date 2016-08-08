<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<jsp:useBean id='menu' scope="page" class="beans.Menu"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ordina!!!</title>

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

        <script type="text/javascript" src="include/js/prenotazioni.js"></script>
    </head>
    <body>
        <%@include file="include/header.jsp" %>

        <div class="container">
            <div class="col-md-6">
                <h1>Prenotazione</h1>
                <div id="elenco-pizze">
                    <c:forEach var="pizza" items="${menu.pizze}">
                        <p>
                        <div class="row">
                            <div class="col-md-6">
                                <span class="pizza-nome text-uppercase">
                                    <strong><c:out value="${pizza.nome}"/></strong>
                                </span>
                            </div>
                            <div class="col-md-6">
                                <span class="pizza-prezzo pull-right">
                                    <strong>&euro;&nbsp;<c:out value="${pizza.prezzo}"/></strong>
                                    <a class="btn btn-success choose-pizza" href="#0"
                                       data-id-pizza="${pizza.id}"
                                       data-nome-pizza="${pizza.nome}"
                                       data-prezzo-pizza="${pizza.prezzo}">
                                        <span class="glyphicon glyphicon-plus"></span>
                                    </a>

                                </span>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-11">
                                <span class="text-capitalize">
                                    <c:out value="${pizza.listaIngredienti}"/>
                                </span>
                            </div>
                        </div>
                        </p>
                    </c:forEach>

                    <%--
                    <c:forEach var="pizza" items="${menu.pizze}">
                        <div class="menu-pizza row">
                            <!-- link scelta pizza -->
                            
                            
                            <a class="choose-pizza" href="#0"
                               data-id-pizza="${pizza.id}"
                               data-nome-pizza="${pizza.nome}"
                               data-prezzo-pizza="${pizza.prezzo}">
                                <span class="glyphicon glyphicon-plus"></span>
                            </a>
                                
                                
                            <span class="pizza-nome text-uppercase">
                                <strong><c:out value="${pizza.nome}"/></strong>
                            </span>
                            <span class="pizza-prezzo pull-right">
                                <strong><c:out value="${pizza.prezzo}"/>&nbsp;&euro;</strong>
                            </span>
                        </div> 
                    </c:forEach> --%>
                </div>
            </div>
            <div class="col-md-6">
                <h1>Il tuo ordine</h1>
                <div id="carrello">
                    <!-- jquery -->
                </div>

                <form id="form-carrello" action="Controller" method="POST">
                    <input type="hidden" name="action" value="add-prenotazione"/>
                    <input type="hidden" name="num_pizze" value='0'/>
                    <!--
                    <div id="datepicker" class="form-group">
                        <label for="datepicker">Data consegna</label>
                        <div class="input-group">
                            <input name="dataConsegna" type="text" 
                                   class="form-control" placeholder="gg-mm-yyyy">
                            <span class="input-group-addon">
                                <i class="glyphicon glyphicon-th"></i>
                            </span>
                        </div>
                    </div> -->

                    <div class="form-group">
                        <label for="data">Data consegna</label>
                        <div id="datepicker" class="input-group date">
                            <input id="data" name="dataConsegna" type="text" 
                                   class="form-control"/>
                            <span class="input-group-addon">
                                <i class="glyphicon glyphicon-th"></i>
                            </span>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="orario">Ora di consegna</label>
                        <div id="timepicker" class="input-group">
                            <input id="orario" type="text" name="oraConsegna"
                                   placeholder="hh:mm" class="form-control"/> 
                            <span class="input-group-addon">
                                <i class="glyphicon glyphicon-time"></i>
                            </span>
                        </div>
                    </div> 
                    <!-- input create dinamicamente by jquery -->

                    <input type="submit" name="submit" value="Ordina!" class="btn btn-primary"/>
                    <div>
                        PAGAH:
                        <span id="prezzo-tot">0.00</span>&euro;
                    </div>
                </form> 

                <div id="result-container" class="alert alert-dismissible hidden" role="alert">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <span id="result-message"></span>     
                </div>



            </div>
        </div>
    </body>
</html>
