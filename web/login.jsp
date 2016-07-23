<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>

        <meta name="google-signin-client_id" content="495487496441-r9l7mppbotcf6i3rt3cl7fag77hl0v62.apps.googleusercontent.com"></meta>

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
        
        <meta name="google-signin-client_id" content="495487496441-r9l7mppbotcf6i3rt3cl7fag77hl0v62.apps.googleusercontent.com"></meta>
        <script type='text/javascript' src='include/js/googleplus-script.js'></script>
        <script src="https://apis.google.com/js/client:platform.js?onload=startApp" async defer></script>
    </head>
    <body>
        <jsp:include page="include/html/header.html"/>

        <div class="container">
            <h1>Login</h1>
            
            ${message}

            <div class="col-md-5">
                <form id="form_login" action="Controller" method="POST">
                    <input type="hidden" name="action" value="user-login"/>
                    <div class="form-group">
                        <label for="email">Email</label>
                        <input id="email" name="email" type="email" 
                               class="form-control" required/>
                    </div>
                    <div class="form-group">
                        <label for="password">Password</label>
                        <input id="password" name="password" type="password" 
                               class="form-control" required/>
                    </div>

                    <!--<input id='submit-login' type="submit"/>-->
                    <button id="submit-login" type="button" class="btn btn-primary">
                        Accedi
                    </button>
                </form>
                Non hai ancora un account? Registrati <a href="register.jsp">qui</a>!<br/>
            </div>
        </div>
    </body>
</html>
