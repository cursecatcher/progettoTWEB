

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<jsp:useBean id='pizzaBean' scope="page" class="beans.Pizza"/>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Menu</title>
    </head>
    <body>
        <h1>Elenco Pizze</h1>
        
        <jsp:getProperty name="pizzaBean" property="menu"/>
    </body>
</html>
