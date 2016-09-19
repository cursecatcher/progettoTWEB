<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<jsp:useBean id='ingredienti' scope="page" class="beans.Ingredienti"/>

<c:if test="${usertoken != 'authenticated'}">
    <c:redirect url="login.jsp"/>
</c:if>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Catalogo ingredienti</title>

        <!-- Latest compiled and minified CSS -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" 
              integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" 
              crossorigin="anonymous">

        <!-- Optional theme -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css" 
              integrity="sha384-fLW2N01lMqjakBkx3l/M9EahuwpSfeNvV63J5ezn3uZzapT0u7EYsXMjQV+0En5r" 
              crossorigin="anonymous">
        
        <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.6.3/css/font-awesome.min.css" 
              rel="stylesheet" 
              integrity="sha384-T8Gy5hrqNKT+hzMclPo118YTQO6cYprQmhrYwIiQ/3axmI1hQomh7Ud2hPOy8SP1" 
              crossorigin="anonymous">
        
        
        <link rel="stylesheet" href="include/css/footer.css">
        <link rel="stylesheet" href="include/css/style.css">

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
        <!-- jQuery dropdown -->
        <link type="text/css" rel="stylesheet" href="include/lib/jquery-dropdown/jquery.dropdown.min.css" />
        <script type="text/javascript" src="include/lib/jquery-dropdown/jquery.dropdown.min.js"></script>

        <script type="text/javascript" src="include/js/gnammy-script.js"></script>
        <script type="text/javascript" src="include/js/general.js"></script>
    </head>
    <body>
        <%@include file="include/header.jsp" %>

        <div class='container'>
            <div class="col-md-6">
                <div class="page-header">
                    <h1>Catalogo ingredienti</h1>
                </div>

                <div id="container-ingredienti">
                    <c:forEach var="ingrediente" items="${ingredienti.listaIngredienti}">
                        <div class="row">
                            <div class="col-md-8 text-uppercase">
                                <h4>
                                    <c:out value="${ingrediente.nome}"/>
                                </h4>
                            </div>
                            <div class="col-md-4">
                                <h4>
                                    <small>
                                        <fmt:formatNumber type="number" value="${ingrediente.prezzo}" minFractionDigits="2"/>
                                        &nbsp;&euro;
                                    </small>
                                </h4>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
            <div class="col-md-6">
                <div class="page-header">
                    <h1>Inserimento ingrediente</h1>
                </div>
                <form id="form_newingr" action="Controller" method="POST">
                    <div class='form-group'>
                        <input type="hidden" name="action" value="ingrediente-add"/>
                    </div>
                    <div class="form-group">
                        <!--  <label for='nome-ingrediente'>Nome ingrediente</label> -->
                        <input id='nome-ingrediente' type="text" name="nome" class="form-control"
                               maxlength="32" placeholder="Nome ingrediente" required/>
                    </div>
                    <div class="form-group">
                        <!--  <label for='prezzo-ingrediente'>Prezzo</label> -->
                        <div class="input-group">
                            <input id="prezzo-ingrediente" name="prezzo" type="number" min="0.1" step="0.1"
                                   class="form-control" placeholder="Prezzo ingrediente" required/>
                            <span class="input-group-addon">&euro;</span>
                        </div>
                    </div>
                    <input type="submit" class="btn btn-primary btn-block" value="Inserisci"/>
                </form>
                <p class='text-muted text-center'>oppure</p>
                <a href='newpizza.jsp' class='btn btn-primary btn-block'>Torna all'inserimento pizze</a>
                
            </div>
        </div>
        
        <%@include file="include/footer.html" %>
    </body>
</html>
