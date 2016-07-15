<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>New ingredients are coming! </title>

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

        <script type="text/javascript" src="include/js/gnammy-script.js"></script>
    </head>
    <body>
        <jsp:include page="include/html/header.html"/>

        <div class='container'>
            <h1>Inserimento ingredienti</h1>

            <form id="form_newingr" action='Controller' method="POST">
                <input type="hidden" name="action" value="ingrediente-add"/>
                <p>
                    <label>Nome ingrediente</label>
                    <input type="text" name="nome" maxlength="32"/>
                </p>
                <p>
                    <label>Prezzo</label>
                    <input type="number" name="prezzo" min="0.1" step="0.1"/>
                </p>
                <input type="submit" id="submit-newingr"/>
                
                <!-- div risultato operazione ? -->
            </form>
            <p>Torna all'<a href='newpizza.jsp'>inserimento delle pizze</a>!!</p>
        </div>
    </body>
</html>
