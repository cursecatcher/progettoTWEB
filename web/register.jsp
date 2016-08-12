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
        
        <script type="text/javascript" src="include/js/log-reg-checks.js"></script>
        <script type="text/javascript" src="include/js/general.js"></script>
    </head>
    <body>
        <jsp:include page="include/header.jsp"/>


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
                    <input id='submit-reg' class="btn btn-primary" type="submit" value="Iscriviti"/>

                </form>

                <p>
                    Hai gi&agrave; un account? Effettua il <a href="login.jsp">login</a>!
                </p>
                <div id="result-container" class="alert alert-dismissible hidden" role="alert">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <span id="result-message"></span>
                </div>

            </div>
        </div>
    </body>
</html>
