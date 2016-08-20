<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<c:if test="${usertoken != 'authenticated'}">
    <c:redirect url="login.jsp"/>
</c:if>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Profilo</title>

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

        <script type="text/javascript" src="include/js/general.js"></script>

    </head>
    <body>
        <%@include file="include/header.jsp" %>

        <div class="container">
            <div class="col-md-2"></div>
            <div class="col-md-8">
                <h4 class="text-center text-uppercase">Il mio profilo</h4>
                <hr>
                <h5>INFORMAZIONI</h5>
                <div class="info-profile">
                    <div class="row">
                        <div class="col-md-4">
                            <strong>Nome</strong>
                        </div>
                        <div class="col-md-4 text-capitalize">
                            <c:out value="${user.nome}"/>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-4">
                            <strong>Cognome</strong>
                        </div>
                        <div class="col-md-4 text-capitalize">
                            <c:out value="${user.cognome}"/>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-4">
                            <strong>Email</strong>
                        </div>
                        <div class="col-md-4 text-lowercase">
                            <c:out value="${user.email}"/>
                        </div>
                    </div>
                </div>

                <hr>

                <h5>PASSWORD</h5>
                <div class="change-pwd">
                    <div class="form-group">       
                        <form id="form-change-pwd" method="POST" action="Controller">
                            <input type="hidden" name="action" value="user-change-pwd"/>

                            <div class="form-group">
                                <input type="password" name="old-password" class="form-control" 
                                       placeholder="Password attuale" required/>
                            </div>
                            <div class="form-group">
                                <input type="password" name="new-password" class="form-control"
                                       placeholder="Nuova password" required/>
                            </div>
                            <div class="form-group">
                                <input type="password" name="confirm-password" class="form-control"
                                       placeholder="Conferma password" required/>
                            </div>

                            <input type="submit" name="submit" value="Aggiorna password"
                                   class="btn btn-primary btn-block"/>
                        </form>
                    </div>
                </div>
                <hr>

                <h5>PRENOTAZIONI</h5>
                <div class="row">
                    <div class="col-md-4">
                        <strong>n. prenotazioni effettuate</strong>
                    </div>
                    <div class="col-md-4">
                        ${fn:length(user.prenotazioni)}
                    </div>
                    <div class="col-md-4">
                        <a href="mie-prenotazioni.jsp" class="btn btn-primary btn-block">
                            Gestisci prenotazioni
                        </a>
                    </div>
                </div>
            </div>
            <div class="col-md-2"></div>


        </div>
    </body>
</html>
