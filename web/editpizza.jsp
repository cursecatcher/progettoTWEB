<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="pizza" scope="page" class="beans.Pizza"/>
<jsp:useBean id="ingredienti" scope="page" class="beans.Ingredienti" />

<c:if test="${usertoken != 'authenticated'}">
    <c:redirect url="login.jsp"/>
</c:if>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Modifica dettagli pizza</title>

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
        <div class="container">
            <div id="modifica-pizza">
                <h1>
                    Modifica pizza 
                    <strong><c:out value="${requestScope.pizza.nome}"/></strong>
                </h1>
                <form id="editpi-form" action="Controller" method="POST">
                    <input type="hidden" name="action" value="pizza-edit"/>
                    <input type="hidden" name="id" id="id_pizza_edit" value="${requestScope.pizza.id}"/>

                    <div class="form-group">
                        <label for="nome_pizza_edit">Nome pizza</label>
                        <input id="nome_pizza_edit" name="nome" type="text" class="form-control"
                               value="${requestScope.pizza.nome}" required
                               placeholder="Nome della pizza" />
                    </div>

                    <div class="form-group">
                        <label for="prezzo_pizza_edit">Prezzo pizza</label>
                        <div class="input-group">
                            <input id="prezzo_pizza_edit" name="prezzo" type="number" min="0.1" step="0.1"
                                   class="form-control" placeholder="Prezzo della pizza..." required=""
                                   value="${requestScope.pizza.prezzo}"/>
                            <span class="input-group-addon">&euro;</span>
                        </div>
                    </div>

                    <div>
                        <p>
                            <strong>Seleziona gli ingredienti da mettere sulla pizza </strong>
                        </p>
                        <select id='select-ingredienti' name='ingredientiPizza' multiple="multiple">
                            <!-- imposta gli ingredienti presenti nella pizza come selezionati -->
                            <c:forEach var="ingrediente" items="${ingredienti.listaIngredienti}">
                                <c:set var="contains" value="false"/>
                                <c:forEach var="item" items="${requestScope.pizza.ingredienti}">
                                    <c:if test="${item.id eq ingrediente.id}">
                                        <c:set var="contains" value="true"/>
                                    </c:if>
                                </c:forEach>

                                <option value="${ingrediente.id}"
                                        <c:if test="${contains eq 'true'}">selected</c:if>>
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
                    <div class="row">
                        <div class="col-md-6">
                            <input type="submit" class="btn btn-success btn-block" value="Conferma modifiche"/>
                        </div>
                        <div class="col-md-6">
                            <a href="newpizza.jsp" class="btn btn-default btn-block">
                                Annulla modifiche
                            </a>
                        </div>
                    </div>
                </form>
            </div>
        </div>

        <%@include file="include/footer.html" %>
    </body>
</html>
