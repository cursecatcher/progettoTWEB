<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<c:if test="${usertoken != 'authenticated'}">
    <c:redirect url="index.jsp"/>
</c:if>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ordini ricevuti</title>

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

        <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.6.3/css/font-awesome.min.css" 
              rel="stylesheet" 
              integrity="sha384-T8Gy5hrqNKT+hzMclPo118YTQO6cYprQmhrYwIiQ/3axmI1hQomh7Ud2hPOy8SP1" 
              crossorigin="anonymous">


        <link rel="stylesheet" href="include/css/header.css">
        <link rel="stylesheet" href="include/css/footer.css">
        <link rel="stylesheet" href="include/css/style.css">

        <!-- Latest compiled and minified JavaScript -->
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" 
                integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" 
        crossorigin="anonymous"></script>

        <!--growl -->
        <script type="text/javascript" src="include/lib/jquery-growl/jquery.growl.js"></script>
        <link rel="stylesheet" href="include/lib/jquery-growl/jquery.growl.css" type="text/css" />
        <!-- jQuery dropdown -->
        <link type="text/css" rel="stylesheet" href="include/lib/jquery-dropdown/jquery.dropdown.min.css" />
        <script type="text/javascript" src="include/lib/jquery-dropdown/jquery.dropdown.min.js"></script>


        <script type="text/javascript" src="include/js/general.js"></script>
    </head>
    <body>
        <%@include file="include/header.jsp" %>

        <div class="container">
            <div class="col-md-3"></div>
            <div class="col-md-6">
                <c:choose>
                    <c:when test="${user.ruolo == 'admin'}">

                        <div class="page-header text-center">
                            <h1>Ordinazioni utenti</h1>
                            <p>
                                In questa pagina sono visualizzate le ordinazioni in attesa di consegna.
                            </p>
                        </div>

                        <c:choose>
                            <c:when test="${empty user.allPrenotazioni}">
                                <p class="text-center">Non sono presenti prenotazioni </p>
                            </c:when>
                            <c:otherwise>
                                <c:forEach var="p" items="${user.allPrenotazioni}">
                                    <c:if test="${p.isConsegnato() == false}">
                                        <div id="pren-${p.id}" class="panel panel-default">
                                            <div class="panel-body">
                                                <div class="row">
                                                    <!-- colonna ordine -->
                                                    <div class="col-md-6">
                                                        <div class="page-header">
                                                            <h4>Riepilogo ordine</h4>
                                                        </div>
                                                        <c:forEach var="el" items="${p.ordine}">
                                                            <div>
                                                                <c:out value="${el.quantity}"/>x&nbsp;
                                                                <strong class="text-uppercase">
                                                                    <c:out value="${el.nome}"/>
                                                                </strong>
                                                            </div>
                                                        </c:forEach>
                                                        <hr>
                                                        <div>
                                                            <strong>Totale:</strong> 
                                                            <fmt:formatNumber type="number" value="${p.prezzo}" minFractionDigits="2"/>
                                                            &euro;
                                                        </div>
                                                    </div>
                                                    <!-- colonna dati consegna -->
                                                    <div class="col-md-6">
                                                        <div class="page-header">
                                                            <h4>Info di consegna</h4>
                                                        </div>
                                                        <address class="text-capitalize">
                                                            <c:out value="${p.citofono}"/><br/>
                                                            <c:out value="${p.indirizzo}"/><br/>
                                                        </address>
                                                        <hr>
                                                        <p>
                                                            Consegna per il
                                                            <strong>
                                                                <fmt:formatDate value="${p.dataConsegna}" 
                                                                                pattern="dd-MM-yyyy" />
                                                            </strong><br/>
                                                            alle  
                                                            <strong>
                                                                <fmt:formatDate value="${p.orarioConsegna}" 
                                                                                pattern="HH:mm" />
                                                            </strong>
                                                        </p>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="panel-footer">
                                                <div class="row">
                                                    <div class="col-md-12">
                                                        Utente: <c:out value="${p.proprietario.email}"/>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </c:if>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>

                    </c:when>
                    <c:otherwise>
                        <div class="page-header text-center">
                            <h3>Errore</h3>
                            <p>
                                Non sei autorizzato a visualizzare questa pagina
                            </p>
                        </div>
                    </c:otherwise>
                </c:choose>




            </div>
            <div class="col-md-3"></div>
        </div>

        <%@include file="include/footer.html" %>
    </body>
</html>
