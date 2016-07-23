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

        <script src="https://code.jquery.com/jquery-2.2.4.min.js"   
                integrity="sha256-BbhdlvQf/xTY9gja0Dq3HiwQF8LaCRTXxZKRutelT44="   
        crossorigin="anonymous"></script>

        <link rel='stylesheet' type='text/css' href='include/lib/sol/sol.css'>
        <script type='text/javascript' src='include/lib/sol/sol.js'></script>

        <script type='text/javascript' src='include/js/gnammy-script.js'></script>
    </head>
    <body>
        <jsp:include page="include/html/header.html"/>

        <div class='container'>
            <div class="col-md-6">
                <h1>Catalogo pizze</h1>
                <c:forEach var="pizza" items="${menu.pizze}">
                    <div class="row-fluid">
                        <span class="text-uppercase">
                            <c:out value="${pizza.nome}"/>
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
                <h1>Nuova pizza</h1>

                <form id="form_newpi" action="Controller" method="POST">
                    <input type="hidden" name="action" value="pizza-create"/>
                    <div class="form-group">
                        <label for="nome_pizza">Nome pizza</label>
                        <input id="nome_pizza" name="nome" type="text" class="form-control"
                               placeholder="Nome della pizza"/>
                    </div>

                    <div class="form-group">
                        <label for="prezzo_pizza">Prezzo pizza</label>
                        <input id="prezzo_pizza" name="prezzo" type="number" min="0.5" step="0.1"
                               class="form-control" placeholder="Prezzo della pizza..."/>
                    </div>

                    <div>
                        <p><strong>Scegli gli ingredienti da mettere sulla pizza: </strong></p>
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
                    <button id='submit-newpi' type="submit" class="btn btn-default">
                        Inserisci pizza
                    </button>
                </form>
                <div id="result-op" class="alert" role="alert"><!--jquery--></div>
                
                Clicca <a href='newingrediente.jsp'>qui</a> per inserire un ingrediente mancante! 
            </div>
        </div>
    </body>
</html>
