<%-- 
    Document   : mie-prenotazioni
    Created on : 9-ago-2016, 16.16.24
    Author     : nico
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>

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
    </head>
    <body>
        <%@include file="include/header.jsp" %>

        <div class="container">
            <h1>Le mie prenotazioni</h1>

            <div class="col-md-3"></div>
            <div class="col-md-6">
                <div class="panel-group">
                    <c:forEach var="p" items="${user.prenotazioni}">

                        <div class="panel panel-default">
                            <div class="panel-body">
                                <p>
                                    <!--jstl format date e time plzzz -->
                                    
                                    Consegna prevista il <strong><c:out value="${p.dataConsegna}"/></strong>
                                    alle ore <strong><c:out value="${p.orarioConsegna}"/></strong>
                                </p>

                                <c:forEach var="el" items="${p.ordine}">
                                    <div>
                                        <c:out value="${el.quantity}"/>x&nbsp;
                                        <strong><c:out value="${el.nome}"/></strong>
                                    </div>
                                </c:forEach>

                                Importo: 
                                <strong>
                                    <fmt:formatNumber type="number" value="${p.prezzo}" minFractionDigits="2"/>
                                    &euro;
                                </strong>



                            </div>
                            <div class="panel-footer">
                                <a href="#0">Conferma consegna</a><br/>
                                <a href="#0">Annulla prenotazione</a>
                            </div>
                        </div>
                        <!-- 
                        ----------------------------------------------v (dropdown)
                        consegna prevista il giorno tale alle ore tali
                        Importo da pagare: tali euri
                        -->
                        <%--
                        Ordine<br/>
                        Data consegna: ${prenotazione.dataConsegna} <br/>
                        Orario consegna: ${prenotazione.orarioConsegna} <br/>
                        ${prenotazione.prezzo} &euro; 
                        <a class="btn btn-primary view-prenotazione" role="button" 
                           data-toggle="collapse" 
                           data-id="${prenotazione.id}"
                           href="#collapseExample" 
                           aria-expanded="false" 
                           aria-controls="collapseExample">
                            Vedi dettagli
                        </a>
                        --%>
                    </c:forEach>
                </div>
            </div>
            <div class="col-md-3"></div>



        </div>
    </body>
</html>
