<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>


<jsp:useBean id='menu' scope="page" class="beans.Menu"/>
<html>
    <head>
        <title>Login</title>
        <link href='//fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css'>

        <c:choose>
            <c:when test="${usertoken != 'authenticated'}">
                <link rel="stylesheet" href="include/css/styndex.css">
            </c:when>
            <c:otherwise>
                <!-- Latest compiled and minified CSS -->
                <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" 
                      integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" 
                      crossorigin="anonymous">

                <!-- Optional theme -->
                <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css" 
                      integrity="sha384-fLW2N01lMqjakBkx3l/M9EahuwpSfeNvV63J5ezn3uZzapT0u7EYsXMjQV+0En5r" 
                      crossorigin="anonymous">

                <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.6.3/css/font-awesome.min.css" 
                      rel="stylesheet" 
                      integrity="sha384-T8Gy5hrqNKT+hzMclPo118YTQO6cYprQmhrYwIiQ/3axmI1hQomh7Ud2hPOy8SP1" 
                      crossorigin="anonymous">


                <link rel="stylesheet" href="include/css/header.css">
                <link rel="stylesheet" href="include/css/footer.css">
                <link rel="stylesheet" href="include/css/style.css">
            </c:otherwise>
        </c:choose>




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
        <!-- Latest compiled and minified JavaScript -->
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" 
                integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" 
        crossorigin="anonymous"></script>

        <!--growl -->
        <script type="text/javascript" src="include/lib/jquery-growl/jquery.growl.js"></script>
        <link rel="stylesheet" href="include/lib/jquery-growl/jquery.growl.css" type="text/css" />

        <script type="text/javascript" src="include/js/log-reg-checks.js"></script>
        <script type="text/javascript" src="include/js/general.js"></script>
    </head>

    <body>
        <div id="result-message" style="display: none">
            ${message}
        </div>

        <c:choose>
            <c:when test="${usertoken != 'authenticated'}">
                <div class="w3 containerz">
                    <h1 class="super-titolo">Super Pizza Goddo</h1>

                </div>
                <div class="containerz w3">

                    <h2>Accedi</h2>
                    <form id="form_login" action="Controller" method="POST">
                        <input type="hidden" name="action" value="user-login"/>
                        <div class="username">
                            <span class="username">Email</span>
                            <!--
                            <input type="text" name="name" class="name" placeholder="" required=""> 
                            -->
                            <input id="email" name="email" type="email" class="name"
                                   value="${previous_email}" required/>
                            <div class="clear"></div>
                        </div>
                        <div class="password-agileits">
                            <span class="username">Password:</span>
                            <!--
                            <input type="password" name="password" class="password" placeholder="" required="">
                            -->
                            <input id="password" name="password" class="password" type="password" 
                                   required/>
                            <div class="clear"></div>
                        </div>
                        <div class="rem-for-agile">
                            <!--   <input type="checkbox" name="remember" class="remember">Remember me<br> -->
                            Non hai un account? <br/>
                            <a href="signup.jsp"><strong>Registrati!</strong></a><br>
                        </div>
                        <div class="login-w3">
                            <input type="submit" class="login" value="Login">
                        </div>
                        <div class="clear"></div>
                    </form>
                </div>
            </c:when>
            <c:otherwise>
                <%@include file="include/header.jsp" %>
                
                <div class="container">
                    <div class="col-md-2"></div>
                    <div class="col-md-8">
                        <div class="page-header text-center">
                            <h1>Il nostro menu</h1>
                        </div>

                        <c:forEach var="pizza" items="${menu.pizze}">
                            <div class="row-fluid">
                                <div class="row">
                                    <div class="col-md-8">
                                        <span class="pizza-nome text-uppercase">
                                            <strong><c:out value="${pizza.nome}"/></strong>
                                        </span>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-8">
                                        <c:out value="${pizza.listaIngredienti}"/>
                                    </div>
                                    <div class="col-md-4 text-muted text-right">
                                        <strong>
                                            <fmt:formatNumber type="number" value="${pizza.prezzo}" minFractionDigits="2"/>
                                            &nbsp;&euro;
                                        </strong>
                                    </div>
                                </div>
                            </div>
                            <hr>
                        </c:forEach>
                    </div>
                    <div class="col-md-2"></div>
                </div>

                <%@include file="include/footer.html" %>
            </c:otherwise>
        </c:choose>


    </body>
</html>



<%--
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<jsp:useBean id='menu' scope="page" class="beans.Menu"/>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <!-- Latest compiled and minified CSS -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" 
              integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" 
              crossorigin="anonymous">

        <!-- Optional theme -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css" 
              integrity="sha384-fLW2N01lMqjakBkx3l/M9EahuwpSfeNvV63J5ezn3uZzapT0u7EYsXMjQV+0En5r" 
              crossorigin="anonymous">
        <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.6.3/css/font-awesome.min.css" 
              rel="stylesheet" 
              integrity="sha384-T8Gy5hrqNKT+hzMclPo118YTQO6cYprQmhrYwIiQ/3axmI1hQomh7Ud2hPOy8SP1" 
              crossorigin="anonymous">


        <!-- include il css corretto in base al fatto che l'utente sia autenticato -->
        <c:choose>
            <c:when test="${usertoken != 'authenticated'}">
                <link rel="stylesheet" href="include/css/cover.css">
            </c:when>
            <c:otherwise>
                <link rel="stylesheet" href="include/css/footer.css">
            </c:otherwise>
        </c:choose>

        <link rel="stylesheet" href="include/css/header.css">
        <link rel="stylesheet" href="include/css/style.css">

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


        <script type="text/javascript" src="include/js/general.js"></script>      


        <title>Home</title>

    </head>

    <body>

        <jsp:include page="include/header.jsp"/>

        <c:choose>
            <c:when test="${usertoken != 'authenticated'}">
                <div class="container">
                    <div class="site-wrapper">

                        <div class="site-wrapper-inner">
                            <div class="cover-container">
                                <div class="inner cover">
                                    <h1 class="cover-heading">PiWeb</h1>
                                    <p class="lead">
                                        Sotto quel palazzo c'&egrave; un povero cane pazzo.<br/>
                                        Date da mangiare a quel povero pazzo cane.
                                    </p>
                                    <p class="lead">
                                        <a href="login.jsp" class="btn btn-lg btn-primary">Accedi</a>
                                    </p>
                                </div>
                                <!--
                                                            <div class="mastfoot">
                                                                <div class="inner">
                                                                    <p>Cover template for <a href="http://getbootstrap.com">Bootstrap</a>, by <a href="https://twitter.com/mdo">@mdo</a>.</p>
                                                                </div>
                                                            </div> -->

                            </div>

                        </div>

                    </div>
                </div>
            </c:when>
            <c:otherwise>

                <div class="container">

                    <div class="col-md-2"></div>
                    <div class="col-md-8">
                        <div class="page-header text-center">
                            <h1>Il nostro menu</h1>
                        </div>

                        <c:forEach var="pizza" items="${menu.pizze}">
                            <div class="row-fluid">
                                <div class="row">
                                    <div class="col-md-8">
                                        <span class="pizza-nome text-uppercase">
                                            <strong><c:out value="${pizza.nome}"/></strong>
                                        </span>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-8">
                                        <c:out value="${pizza.listaIngredienti}"/>
                                    </div>
                                    <div class="col-md-4 text-muted text-right">
                                        <strong>
                                            <fmt:formatNumber type="number" value="${pizza.prezzo}" minFractionDigits="2"/>
                                            &nbsp;&euro;
                                        </strong>
                                    </div>
                                </div>
                            </div>
                            <hr>
                        </c:forEach>
                    </div>
                    <div class="col-md-2"></div>

                </div>

                <%@include file="include/footer.html" %>
            </c:otherwise>
        </c:choose>




    </body>
</html>

--%>

<%--
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<jsp:useBean id='menu' scope="page" class="beans.Menu"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Homepage</title>

        <script src="https://code.jquery.com/jquery-2.2.4.min.js"   
                integrity="sha256-BbhdlvQf/xTY9gja0Dq3HiwQF8LaCRTXxZKRutelT44="   
        crossorigin="anonymous"></script>

        <!-- Latest compiled and minified CSS -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" 
              integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" 
              crossorigin="anonymous">

        <!-- Optional theme -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css" 
              integrity="sha384-fLW2N01lMqjakBkx3l/M9EahuwpSfeNvV63J5ezn3uZzapT0u7EYsXMjQV+0En5r" 
              crossorigin="anonymous">

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


        <script type="text/javascript" src="include/js/general.js"></script>        
    </head>
    <body>
        <jsp:include page="include/header.jsp"/>

        <div class="container">
            <h1>Le nostre pizze</h1>

            <c:forEach var="pizza" items="${menu.pizze}">
                <div class="row-fluid">
                    <h4>
                        <span class="text-uppercase">
                            <c:out value="${pizza.nome}"/> 
                        </span>
                        <span class="pull-right">
                            <small>
                                <c:out value="${pizza.prezzo}"/>&nbsp;&euro;
                            </small>
                        </span>
                    </h4>
                    <p class="text-capitalize">
                        <c:out value="${pizza.listaIngredienti}"/>
                    </p>
                </div>
            </c:forEach>
        </div>
    </body>
</html> --%>
