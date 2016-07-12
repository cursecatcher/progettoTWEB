

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<jsp:useBean id='menu' scope="page" class="beans.Menu"/>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Menu</title>

        <!-- Latest compiled and minified CSS -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" 
              integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" 
              crossorigin="anonymous">

        <!-- Optional theme -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css" 
              integrity="sha384-fLW2N01lMqjakBkx3l/M9EahuwpSfeNvV63J5ezn3uZzapT0u7EYsXMjQV+0En5r" 
              crossorigin="anonymous">

        <script src="https://code.jquery.com/jquery-2.2.4.min.js"   
                integrity="sha256-BbhdlvQf/xTY9gja0Dq3HiwQF8LaCRTXxZKRutelT44="   
        crossorigin="anonymous"></script>

        <script type='text/javascript' src='include/js/prenotazioni.js'></script>
    </head>
    <body>
        <%@include file="include/html/header.html" %>

        <div class='container'>
            <h1>Elenco Pizze</h1>

            <c:forEach var="pizza" items="${menu.pizze}">
                <div class="menu-pizza">
                    <a id="pizza-${pizza.id}" class="choose-pizza" href="#0">
                        <span class="glyphicon glyphicon-plus"></span>
                    </a>
                    <div class="pizza-nome">
                        <c:out value="${pizza.nome}"/>
                    </div>
                    <div class="pizza-prezzo">
                        <c:out value="${pizza.prezzo}"/>
                    </div>
                </div>
            </c:forEach>

        </div>
    </body>
</html>
