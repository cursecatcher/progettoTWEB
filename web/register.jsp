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

            <div class="col-md-5">
                <form id="form_reg" action="Controller" method="POST">
                    <div class="form-group">
                        <label for="email">Email</label>
                        <input id="email" type="email" name="email" 
                               class="form-control" placeholder="La tua email" required/>
                    </div>
                    <div class="form-group">
                        <label for="password">Password</label>
                        <input id="password" type="password" name="password" 
                               class="form-control" placeholder="Password..." required/>
                    </div>
                    <div class="form-group">
                        <label for="confirm-password">Conferma password</label>
                        <input id="confirm-password" type="password" name="password2" 
                               class="form-control" placeholder="Conferma password" required/>
                    </div>
                    <input id='submit-reg' type="submit"/>

                </form>

                <p>
                    Hai gi&agrave; un account? Effettua il <a href="login.jsp">login</a>!
                </p>
            </div>
        </div>
    </body>
</html>
