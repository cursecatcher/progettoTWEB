<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>


        <script src="https://code.jquery.com/jquery-2.2.4.min.js"   
                integrity="sha256-BbhdlvQf/xTY9gja0Dq3HiwQF8LaCRTXxZKRutelT44="   
        crossorigin="anonymous"></script>
        <script type="text/javascript" src="include/js/log-reg-checks.js"></script>
    </head>
    <body>
        <h1>Login</h1>

        <form id="form_login" action="ServletController" method="POST">
            <p>
                <label>Email</label>
                <input name="email" type="email" required/>
            </p>
            <p>
                <label>Password</label>
                <input name="password" type="password" required/>
            </p>

            <input id='submit-login' type="submit"/>
        </form>

        Non hai ancora un account? Registrati <a href="register.jsp">qui</a>!
    </body>
</html>
