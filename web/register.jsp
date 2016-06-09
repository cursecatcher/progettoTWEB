<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Registrazione</title>

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
        <script type="text/javascript" src="include/js/log-reg-checks.js"></script>
    </head>
    <body>
        <jsp:include page="include/html/header.html"/>


        <div class="container">
            <h1>Registrazione</h1>

            <form id="form_reg" action="ServletController" method="POST">
                <p>
                    <label>Email</label>
                    <input type="email" name="email" required/>
                </p>
                <p>
                    <label>Password</label>
                    <input type="password" name="password" required/>
                </p>
                <p>
                    <label>Conferma password</label>
                    <input type="password" name="password2" required/>
                </p>
                <input id='submit-reg' type="submit"/>

            </form>

            <p>
                Hai gi&agrave; un account? Effettua il <a href="login.jsp">login</a>!
            </p>
        </div>
    </body>
</html>
