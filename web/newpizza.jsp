<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="ingredienti" scope="page" class="beans.Ingredienti" />
<jsp:useBean id="menu" scope="page" class="beans.Menu" />

<c:if test="${usertoken != 'authenticated'}">
    <c:redirect url="login.jsp"/>
</c:if>

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

        <link rel="stylesheet" href="include/css/style.css">
        <link rel="stylesheet" href="include/css/footer.css">

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

        <!-- bootbox -->
        <script type="text/javascript" 
        src="https://cdnjs.cloudflare.com/ajax/libs/bootbox.js/4.4.0/bootbox.min.js"></script>
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
                <div class="page-header">
                    <h1>Catalogo pizze</h1>
                </div>
                <div id="catalogo">
                    <c:forEach var="pizza" items="${menu.pizze}">
                        <div id='pizza-${pizza.id}' class='row-fluid'>
                            <div class='row'>
                                <div class='col-md-8'>
                                    <span class='pizza-nome text-uppercase'>
                                        <h4>
                                            <c:out value='${pizza.nome}'/>
                                        </h4>
                                    </span>
                                </div>
                            </div>
                            <div class='row'>
                                <div class='col-md-8 text-lowercase'>
                                    <c:out value='${pizza.listaIngredienti}'/>
                                </div>
                                <div class='col-md-2 text-muted'>
                                    <strong>
                                        <fmt:formatNumber type='number' value='${pizza.prezzo}' minFractionDigits='2'/>
                                        &nbsp;&euro;
                                    </strong>
                                </div>
                                <div class='col-md-2'>
                                    <a href='#0' class='edit-link btn btn-primary btn-xs' data-id='${pizza.id}'>
                                        <span class='glyphicon glyphicon-pencil'></span>
                                    </a>
                                    <a href='#0' class='delete-link btn btn-danger btn-xs' data-id='${pizza.id}'>
                                        <span class='glyphicon glyphicon-remove'></span>
                                    </a>
                                </div>
                            </div>
                            <hr>
                        </div>

                    </c:forEach>
                </div>
            </div>

            <div class="col-md-6">
                <form id="edit-req" action="Controller" method="GET">
                    <input type="hidden" name="action" value="edit-req"/>
                    <input type="hidden" name="id" value="-1"/>
                </form>

                <div id="inserimento-pizza">
                    <div class="page-header">
                        <h1>Nuova pizza</h1>
                    </div>

                    <form id="form_newpi" action="Controller" method="POST">
                        <!--action-->
                        <input type="hidden" name="action" value="pizza-create"/>
                        <!-- nome pizza -->
                        <div class="form-group">
                            <!-- <label for="nome_pizza_create">Nome pizza</label> -->
                            <input id="nome_pizza_create" name="nome" type="text" class="form-control"
                                   placeholder="Nome della pizza" required/>
                        </div>
                        <!-- prezzo -->
                        <div class="form-group">
                            <!-- <label for="prezzo_pizza_create">Prezzo pizza</label> -->
                            <div class="input-group">
                                <input id="prezzo_pizza_create" name="prezzo" type="number" min="0.1" step="0.1"
                                       class="form-control" placeholder="Prezzo della pizza..." required/>
                                <span class="input-group-addon">&euro;</span>
                            </div>
                        </div>
                        <!-- ingredienti -->
                        <div>
                            <!--<p><strong>Seleziona gli ingredienti da mettere sulla pizza </strong></p> -->
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
                                        maxHeight: '200px',
                                        showSelectAll: false,
                                        allowNullSelection: false,
                                        texts: {
                                            noItemsAvailable: "Errore nel caricamento degli ingredienti",
                                            searchplaceholder: "Seleziona gli ingredienti da mettere sulla pizza"
                                        }
                                    });
                                });
                            </script>
                        </div>
                        <br/>
                        <button id='submit-newpi' type="submit" class="btn btn-primary btn-block">
                            Inserisci pizza
                        </button>
                    </form>
                    
                    Clicca <a href='newingrediente.jsp'>qui</a> per inserire un ingrediente mancante! 
                </div> 

            </div>
        </div>

        <%@include file="include/footer.html" %>
    </body>
</html>
