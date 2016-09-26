<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<c:if test="${usertoken == 'authenticated'}">
    <c:redirect url="profilo.jsp"/>
</c:if>

<html>
    <head>
        <title>Registrazione</title>
        <link rel="stylesheet" href="include/css/styndex.css">
        <link href='//fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css'>



        <!-- For-Mobile-Apps-and-Meta-Tags -->
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta name="keywords" content="Simple Login Form Widget Responsive, Login Form Web Template, Flat Pricing Tables, Flat Drop-Downs, Sign-Up Web Templates, Flat Web Templates, Login Sign-up Responsive Web Template, Smartphone Compatible Web Template, Free Web Designs for Nokia, Samsung, LG, Sony Ericsson, Motorola Web Design" />
        <script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } </script>
        <!-- //For-Mobile-Apps-and-Meta-Tags -->



        <!-- jQuery -->
        <script src="https://code.jquery.com/jquery-2.2.4.min.js"   
                integrity="sha256-BbhdlvQf/xTY9gja0Dq3HiwQF8LaCRTXxZKRutelT44="   
        crossorigin="anonymous"></script>
        <!-- Bootstrap.js --> 
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" 
                integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" 
        crossorigin="anonymous"></script>        

        <!-- bootbox -->
        <script type="text/javascript" 
        src="https://cdnjs.cloudflare.com/ajax/libs/bootbox.js/4.4.0/bootbox.min.js"></script>
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
        <div id="result-message" style="display: none">
            ${message}
        </div>

        <div class="containerz w3">
            <h1 class="super-titolo">Super Pizza Goddo</h1>
        </div>
        <div class="containerz w3">

            <h2>Registrazione</h2>
            <form id="form_reg" action="Controller" method="POST">
                <input type="hidden" name="action" value="user-registrazione"/>


                <div>
                    <span>Nome</span>
                    <input id="nome" type="text" name="nome" class="name"
                           placeholder="Il tuo nome" required/>
                </div>
                <div class="username">
                    <span class="username">Cognome</span>
                    <input id="cognome" type="text" name="cognome" class="name"
                           placeholder="Il tuo cognome" required=""/>
                </div>
                <div class="username">
                    <span class="username">Email</span>
                    <input id="email" type="email" name="email" 
                           class="name" placeholder="La tua email" required/>
                </div>
                <div class="password-agileits">
                    <span class="username">Password:</span>
                    <!--
                    <input type="password" name="password" class="password" placeholder="" required="">
                    -->
                    <input id="password" type="password" name="password" 
                           class="password" placeholder="Password" required/>
                    <div class="clear"></div>
                </div>
                <div class="password-agileits">
                    <span class="username">Conferma password:</span>
                    <!--
                    <input type="password" name="password" class="password" placeholder="" required="">
                    -->
                    <input id="confirm-password" type="password" name="password2" 
                           class="password" placeholder="Reinserisci la password" required/>

                    <div class="clear"></div>
                </div>

                <div class="rem-for-agile">
                    <!--   <input type="checkbox" name="remember" class="remember">Remember me<br> -->
                    Hai gi&agrave; un account? <br/>
                    <a href="index.jsp"><strong>Loggati!</strong></a><br>
                </div>
                <div class="login-w3">
                    <input type="submit" class="login" value="Registrati">
                </div>
                <div class="clear"></div>
            </form>
        </div>
    </body>
</html>
