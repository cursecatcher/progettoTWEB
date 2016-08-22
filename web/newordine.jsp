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
        
        <link rel="stylesheet" href="include/css/ordinazioni.css">

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

        <!--jquery.growl -->
        <script type="text/javascript" src="include/lib/jquery-growl/jquery.growl.js"></script>
        <link rel="stylesheet" href="include/lib/jquery-growl/jquery.growl.css" type="text/css" />
        <!-- jQuery dropdown -->
        <link type="text/css" rel="stylesheet" href="include/lib/jquery-dropdown/jquery.dropdown.min.css" />
        <script type="text/javascript" src="include/lib/jquery-dropdown/jquery.dropdown.min.js"></script>

        <script type="text/javascript" src="include/js/prenotazioni.js"></script>
        <script type="text/javascript" src="include/js/general.js"></script>
    </head>
    <body>
        <%@include file="include/header.jsp" %>

        <div class="container">
            <div class="col-md-8">
                <div class="page-header">
                    <h1>Le nostre pizze</h1>
                </div>
                <div id="elenco-pizze">
                    <c:forEach var="pizza" items="${menu.pizze}">
                        <div class="row-fluid">
                            <div class="row">
                                <div class="col-md-8">
                                    <span class="pizza-nome text-uppercase">
                                        <strong><c:out value="${pizza.nome}"/></strong>
                                    </span>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-8">
                                    <c:out value="${pizza.listaIngredienti}"/>
                                </div>
                                <div class="col-md-2 text-muted">
                                    <strong>
                                        <fmt:formatNumber type="number" value="${pizza.prezzo}" minFractionDigits="2"/>
                                        &nbsp;&euro;
                                    </strong>
                                </div>
                                <div class="col-md-2">
                                    <a class="choose-pizza" href="#0"
                                       data-id-pizza="${pizza.id}"
                                       data-nome-pizza="${pizza.nome}"
                                       data-prezzo-pizza="${pizza.prezzo}">
                                        <span class="glyphicon glyphicon-plus"></span>
                                    </a>
                                </div>
                            </div>
                        </div>
                        <hr>
                    </c:forEach>
                </div>
            </div>
            <div class="col-md-4">
                <div id="cart-fixme">
                    <!-- https://stackoverflow.com/questions/15850271/how-to-make-div-fixed-after-you-scroll-to-that-div -->
                    <div class="page-header">
                        <h1>Il tuo ordine</h1>
                    </div>
                    <div id="carrello"></div>
                    <form action="Controller" method="GET">
                        <input type="hidden" name="action" value="proceed-to-order"/>
                        <input type="submit" name="submit" value="Effettua ordine"
                               class="btn btn-success btn-block"/>   
                        <span id="error" class="hide">
                            <c:out value="${message}"/>
                        </span>
                    </form>  
                </div>
            </div>
        </div>
    </body>
</html>
