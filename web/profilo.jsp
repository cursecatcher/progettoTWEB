<%
    if (!"authenticated".equals(session.getAttribute("usertoken"))) {
        String redirectURL = "login.jsp";
        response.sendRedirect(redirectURL);
    }
%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>

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
        <!--growl -->
        <script type="text/javascript" src="include/lib/jquery-growl/jquery.growl.js"></script>
        <link rel="stylesheet" href="include/lib/jquery-growl/jquery.growl.css" type="text/css" />
        <!-- jQuery dropdown -->
        <link type="text/css" rel="stylesheet" href="include/lib/jquery-dropdown/jquery.dropdown.min.css" />
        <script type="text/javascript" src="include/lib/jquery-dropdown/jquery.dropdown.min.js"></script>

        
        <script type="text/javascript" src="include/js/profilo.js"></script>
        <script type="text/javascript" src="include/js/general.js"></script>

    </head>
    <body>

        <%@include file="include/header.jsp" %>

        <div class="container">
            <div class="col-md-6">
                <h1>Profilo utente </h1>

                ${user.id} <br/>
                Sei loggato come: ${user.email}<br/>
                Ruolo utente: ${user.ruolo}<br/>

                <p>
                <form action="Controller" method="GET">
                    <input type="hidden" name="action" value="newpizza"/>
                    <input type="submit" class="btn btn-link" value="NUOVA PIZZA"/>  
                </form>
                </p>

                <p>
                    <a href="newpizza.jsp">NUOVA PIZZA</a>. 
                    (solo amministratore)
                </p>
                <p>
                    <a href="newordine.jsp">ORDINA</a> 
                    (cliente && amministratore)
                </p>
                <p>
                    <a href="mie-prenotazioni.jsp"> MIE PRENOTAZIONE</a> 
                    (cliente && amministratore)
                </p>
                <p>
                    <a href="gestione-prenotazioni.jsp">Archivio Prenotazioni</a>
                </p>
            </div>
                <%--
            <div class="col-md-6">
                <h1>Le tue prenotazioni</h1>

                <c:forEach var="prenotazione" items="${user.prenotazioni}">
                    <div class="row">
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
                    </div>
                </c:forEach>
            </div> --%>
        </div>
    </body>
</html>
