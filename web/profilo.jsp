<%-- 
    Document   : profilo
    Created on : 10-lug-2016, 20.57.22
    Author     : nico
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="userBean" scope="session" class="beans.Utente" />
<jsp:setProperty name="userBean" property="*"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        
        Sei loggato come: ${userBean.email}<br/>
        Ruolo utente: ${userBean.ruolo}<br/>

        <p>
            <a href="newpizza.jsp">NUOVA PIZZA</a>. 
            (solo amministratore)
        </p>
        <p>
            <a href="newordine.jsp">PRENOTAZIONE</a> 
            (cliente && amministratore)
        </p>

    </body>
</html>
