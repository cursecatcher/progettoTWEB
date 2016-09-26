<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<c:if test="${usertoken != 'authenticated'}">
    <c:redirect url="index.jsp"/>
</c:if>

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

        <link rel="stylesheet" href="include/css/header.css">
        <link rel="stylesheet" href="include/css/footer.css">
        <link rel="stylesheet" href="include/css/style.css">

        <script src="https://code.jquery.com/jquery-2.2.4.min.js"   
                integrity="sha256-BbhdlvQf/xTY9gja0Dq3HiwQF8LaCRTXxZKRutelT44="   
        crossorigin="anonymous"></script>

        <!-- jQuery dropdown -->
        <link type="text/css" rel="stylesheet" href="include/lib/jquery-dropdown/jquery.dropdown.min.css" />
        <script type="text/javascript" src="include/lib/jquery-dropdown/jquery.dropdown.min.js"></script>
        <!--growl -->
        <script type="text/javascript" src="include/lib/jquery-growl/jquery.growl.js"></script>
        <link rel="stylesheet" href="include/lib/jquery-growl/jquery.growl.css" type="text/css" />

        <script type='text/javascript' src='include/js/prenotazioni.js'></script>
        <script type="text/javascript" src="include/js/general.js"></script>
    </head>
    <body>
        <%@include file="include/header.jsp" %>

        <div class="container">

            <div class="col-md-2"></div>
            <div class="col-md-8">
                <div class="page-header text-center">
                    <h1>Il nostro menu</h1>
                </div>

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
                            <div class="col-md-4 text-muted text-right">
                                <strong>
                                    <fmt:formatNumber type="number" value="${pizza.prezzo}" minFractionDigits="2"/>
                                    &nbsp;&euro;
                                </strong>
                            </div>
                        </div>
                    </div>
                    <hr>
                </c:forEach>
            </div>
            <div class="col-md-2"></div>

        </div>

        <%@include file="include/footer.html" %>
    </body>
</html>
