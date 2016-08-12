<%
    if (!"authenticated".equals(session.getAttribute("usertoken"))) {
        String redirectURL = "login.jsp";
        response.sendRedirect(redirectURL);
    }
%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="ingredienti" scope="page" class="beans.Ingredienti" />
<jsp:useBean id="menu" scope="page" class="beans.Menu" />

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>New pizza is coming</title>


        <!-- Latest compiled and minified CSS -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" 
              integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" 
              crossorigin="anonymous">

        <!-- Optional theme -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css" 
              integrity="sha384-fLW2N01lMqjakBkx3l/M9EahuwpSfeNvV63J5ezn3uZzapT0u7EYsXMjQV+0En5r" 
              crossorigin="anonymous">

        <!-- jQuery -->
        <script src="https://code.jquery.com/jquery-2.2.4.min.js"   
                integrity="sha256-BbhdlvQf/xTY9gja0Dq3HiwQF8LaCRTXxZKRutelT44="   
        crossorigin="anonymous"></script>

        <!-- Latest compiled and minified JavaScript -->
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" 
                integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" 
        crossorigin="anonymous"></script>

        <link rel='stylesheet' type='text/css' href='include/lib/sol/sol.css'>
        <script type='text/javascript' src='include/lib/sol/sol.js'></script>
        
        <!--jquery.growl -->
        <script type="text/javascript" src="include/lib/jquery-growl/jquery.growl.js"></script>
        <link rel="stylesheet" href="include/lib/jquery-growl/jquery.growl.css" type="text/css" />
        <!-- jQuery dropdown -->
        <link type="text/css" rel="stylesheet" href="include/lib/jquery-dropdown/jquery.dropdown.min.css" />
        <script type="text/javascript" src="include/lib/jquery-dropdown/jquery.dropdown.min.js"></script>

        <script type='text/javascript' src='include/js/gnammy-script.js'></script>
        <script type="text/javascript" src="include/js/general.js"></script>
    </head>
    <body>
        <%@include file="include/header.jsp" %>

        <div class='container'>
            <div class="col-md-6">
                <h1>Catalogo pizze</h1>
                <c:forEach var="pizza" items="${menu.pizze}">
                    <div class="row-fluid">
                        <span class="text-uppercase">
                            <a href="#0" class="edit-link"
                               data-id="${pizza.id}" data-nome="${pizza.nome}" 
                               data-prezzo="${pizza.prezzo}" data-ingredienti="1 2 3">
                                <c:out value="${pizza.nome}"/>
                            </a>
                        </span>
                        <span class="pull-right">
                            <c:out value="${pizza.prezzo}"/>&nbsp;&euro;
                        </span>
                        <p class="text-capitalize">
                            <c:out value="${pizza.listaIngredienti}"/>
                        </p>
                    </div> 
                </c:forEach>
            </div>


            <div class="col-md-6">
                
                <div id="inserimento-pizza">
                    <h1>Nuova pizza</h1>

                    <form id="form_newpi" action="Controller" method="POST">
                        <!--action-->
                        <input type="hidden" name="action" value="pizza-create"/>
                        <!-- nome pizza -->
                        <div class="form-group">
                            <label for="nome_pizza_create">Nome pizza</label>
                            <input id="nome_pizza_create" name="nome" type="text" class="form-control"
                                   placeholder="Nome della pizza"/>
                        </div>
                        <!-- prezzo -->
                        <div class="form-group">
                            <label for="prezzo_pizza_create">Prezzo pizza</label>
                            <div class="input-group">
                                <input id="prezzo_pizza_create" name="prezzo" type="number" min="0.5" step="0.1"
                                       class="form-control" placeholder="Prezzo della pizza..."/>
                                <span class="input-group-addon">&euro;</span>
                            </div>
                        </div>
                        <!-- ingredienti -->
                        <div>
                            <p><strong>Seleziona gli ingredienti da mettere sulla pizza </strong></p>
                            <select id='select-ingredienti' name='ingredientiPizza' multiple="multiple">
                                <c:forEach var="ingrediente" items="${ingredienti.listaIngredienti}">
                                    <option value="${ingrediente.id}">
                                        ${ingrediente.nome}
                                    </option>
                                </c:forEach>
                            </select>

                            <script type='text/javascript'>
                                $(function () {
                                    $('#select-ingredienti').searchableOptionList({
                                        maxHeight: '200px'
                                    });
                                });
                            </script>
                        </div>
                        <br/>
                        <button id='submit-newpi' type="submit" class="btn btn-default">
                            Inserisci pizza
                        </button>
                    </form>
                    <div id="result-container" class="alert alert-dismissible hidden" role="alert">
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <span id="result-message"></span>

                    </div>

                    Clicca <a href='newingrediente.jsp'>qui</a> per inserire un ingrediente mancante! 
                </div> 
                <div id="modifica-pizza">
                    <h1>Modifica pizza</h1>
                    <form id="editpi-form" action="Controller" method="POST">
                        <input type="hidden" name="action" value="pizza-edit"/>
                        <input type="hidden" name="id" id="id_pizza_edit" value="-1"/>

                        <div class="form-group">
                            <label for="nome_pizza_edit">Nome pizza</label>
                            <input id="nome_pizza_edit" name="nome" type="text" class="form-control"
                                   placeholder="Nome della pizza"/>
                        </div>

                        <div class="form-group">
                            <label for="prezzo_pizza_edit">Prezzo pizza</label>
                            <div class="input-group">
                                <input id="prezzo_pizza_edit" name="prezzo" type="number" min="0.5" step="0.1"
                                       class="form-control" placeholder="Prezzo della pizza..."/>
                                <span class="input-group-addon">&euro;</span>
                            </div>
                        </div>

                        <div>
                            <p><strong>Seleziona gli ingredienti da mettere sulla pizza </strong></p>
                            <select id='select-random' name='ingredientiPizza' multiple="multiple">
                                <c:forEach var="ingrediente" items="${ingredienti.listaIngredienti}">
                                    <option value="${ingrediente.id}">
                                        ${ingrediente.nome}
                                    </option>
                                </c:forEach>
                            </select>

                            <script type='text/javascript'>
                                $(function () {
                                    $('#select-random').searchableOptionList({
                                        maxHeight: '200px'
                                    });
                                });
                            </script>
                        </div>
                        <br/>
                        <button id='submit-editpi' type="submit" class="btn btn-default">
                            Conferma modifiche
                        </button>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
