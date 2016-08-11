<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<jsp:useBean id='ingredienti' scope="page" class="beans.Ingredienti"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>New ingredients are coming! </title>

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
        
        <!-- Latest compiled and minified JavaScript -->
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" 
                integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" 
        crossorigin="anonymous"></script>
        
        <!--jquery.growl -->
        <script type="text/javascript" src="include/lib/jquery-growl/jquery.growl.js"></script>
        <link rel="stylesheet" href="include/lib/jquery-growl/jquery.growl.css" type="text/css" />

        <script type="text/javascript" src="include/js/gnammy-script.js"></script>
        <script type="text/javascript" src="include/js/general.js"></script>
    </head>
    <body>
        <%@include file="include/header.jsp" %>

        <div class='container'>
            <div class="col-md-6">
                <h1>Catalogo ingredienti</h1>
                <ul>
                    <c:forEach var="ingrediente" items="${ingredienti.listaIngredienti}">
                        <li>
                            <span class="text-capitalize">
                                <c:out value="${ingrediente.nome}"/>
                            </span>
                        </li>
                    </c:forEach>
                </ul>
            </div>
            <div class="col-md-6">
                <h1>Inserimento ingredienti</h1>
                <form id="form_newingr" action="Controller" method="POST">
                    <div class='form-group'>
                         <input type="hidden" name="action" value="ingrediente-add"/>
                    </div>
                    <div class="form-group">
                        <label for='nome-ingrediente'>Nome ingrediente</label>
                        <input id='nome-ingrediente' type="text" name="nome" class="form-control"
                               maxlength="32" placeholder="Nome ingrediente..."/>
                    </div>
                    <div class="form-group">
                        <label for='prezzo-ingrediente'>Prezzo</label>
                        <input id='prezzo-ingrediente' type="number" name="prezzo" class="form-control"
                               min="0.1" step="0.1"/>
                    </div>
                    <button type="submit" class='btn btn-default' 
                            id="submit-newingr">Inserisci</button>
                </form>
                <p>Torna all'<a href='newpizza.jsp'>inserimento delle pizze</a>!!</p>
                <div id="result-op" class="alert" role="alert"><!--jquery--></div>
            </div>
        </div>
    </body>
</html>
