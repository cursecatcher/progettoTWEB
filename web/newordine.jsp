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
        
        <script type="text/javascript" src="include/js/prenotazioni.js"></script>
    </head>
    <body>
        <%@include file="include/header.jsp" %>
        
        <div class="container">
            <h1>EH, VOLEVI!</h1>

            <div id="elenco-pizze">
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
        </div>
    </body>
</html>
