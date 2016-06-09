<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Registrazione</title>


        <script src="https://code.jquery.com/jquery-2.2.4.min.js"   
                integrity="sha256-BbhdlvQf/xTY9gja0Dq3HiwQF8LaCRTXxZKRutelT44="   
        crossorigin="anonymous"></script>
        <script type="text/javascript" src="include/js/log-reg-checks.js"></script>
    </head>
    <body>
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
    </body>
</html>
