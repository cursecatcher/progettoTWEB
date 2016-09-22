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

        <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.6.3/css/font-awesome.min.css" 
              rel="stylesheet" 
              integrity="sha384-T8Gy5hrqNKT+hzMclPo118YTQO6cYprQmhrYwIiQ/3axmI1hQomh7Ud2hPOy8SP1" 
              crossorigin="anonymous">

        <link rel="stylesheet" href="include/css/profilo.css">
        <link rel="stylesheet" href="include/css/footer.css">
        <link rel="stylesheet" href="include/css/style.css">

        <script src="https://code.jquery.com/jquery-2.2.4.min.js"   
                integrity="sha256-BbhdlvQf/xTY9gja0Dq3HiwQF8LaCRTXxZKRutelT44="   
        crossorigin="anonymous"></script>

        <!-- Latest compiled and minified JavaScript -->
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" 
                integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" 
        crossorigin="anonymous"></script>

        <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.6.3/css/font-awesome.min.css" 
              rel="stylesheet" 
              integrity="sha384-T8Gy5hrqNKT+hzMclPo118YTQO6cYprQmhrYwIiQ/3axmI1hQomh7Ud2hPOy8SP1" 
              crossorigin="anonymous">

        <!--growl -->
        <script type="text/javascript" src="include/lib/jquery-growl/jquery.growl.js"></script>
        <link rel="stylesheet" href="include/lib/jquery-growl/jquery.growl.css" type="text/css" />
        <!-- jQuery dropdown -->
        <link type="text/css" rel="stylesheet" href="include/lib/jquery-dropdown/jquery.dropdown.min.css" />
        <script type="text/javascript" src="include/lib/jquery-dropdown/jquery.dropdown.min.js"></script>

        <script type="text/javascript" src="include/js/general.js"></script>
        <script type="text/javascript" src="include/js/profile.js"></script>

    </head>
    <body>
        <%@include file="include/header.jsp" %>

        <div class="container">
            <!--
                        <div class="page-header">
                            <h4 class="text-center text-uppercase">Il mio profilo</h4>
                        </div> -->

            <div class="col-md-4">
                <div class="page-header">
                    <h5 class="text-uppercase"><strong>Pannello utente</strong></h5>
                </div>

                <ul class="nav nav-pills nav-stacked">
                    <li class="active">
                        <a href="#0">Il mio profilo</a>
                    </li>
                    <li>
                        <!--  <a href="#0" data-toggle="collapse" data-target="#collapse-pwd"> -->
                        <a href="#0" id="show-pwd">
                            Modifica password
                        </a>
                    </li>
                    <li>
                        <a href="newordine.jsp">Effettua ordine</a>
                    </li>
                    <li>
                        <a href="mie-prenotazioni.jsp">Visualizza prenotazioni</a>
                    </li>
                </ul>

                <c:if test="${user.isAdmin()}">
                    <div class="page-header">
                        <h5 class="text-uppercase"><strong>Pannello admin</strong></h5>
                    </div>
                    <ul class="nav nav-pills nav-stacked">


                        <li>
                            <a href="gestione-prenotazioni.jsp">Prenotazioni utenti</a>
                        </li>
                        <li>
                            <a href="newpizza.jsp">Gestione menu</a>
                        </li>  
                    </ul>
                </c:if>

            </div>

            <div class="col-md-6">

                <div class="page-header">
                    <h4 class="text-center text-uppercase">Il mio profilo</h4>
                </div>

                <div class="row">
                    <div class="col-md-6 form-group">
                        <label for="nome">Nome</label>
                        <input id="nome" name="nome" type="text" value="${user.nome}"
                               class="form-control" readonly/>
                    </div>
                    <div class="col-md-6 form-group">
                        <label for="cognome">Cognome</label>
                        <input id="cognome" name="cognome" type="text" value="${user.cognome}"
                               class="form-control" readonly/>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12 form-group">
                        <label for="email">Email</label>
                        <input id="email" name="email" type="email" value="${user.email}"
                               class="form-control" readonly
                               placeholder="Indirizzo email"/>
                    </div>
                </div>
                <hr>
                <div id="editpwd-div" >
                    
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
                            <div class="row">
                                <div class="col-md-6">
                                    <input type="reset" name="reset" value="Annulla"
                                           id="hide-pwd"
                                           data-toggle="collapse" data-target="#demo"
                                           class="btn btn-danger btn-block"/> 
                                </div>
                                <div class="col-md-6">
                                    
                                    <input type="submit" name="submit" value="Aggiorna password"
                                           class="btn btn-primary btn-block"/>
                                </div>

                            </div>
                        </form>
                    </div>
                </div>
                <%--
                <c:if test="${user.isAdmin()}">
                    <hr>
                    <div>
                        <a href="#0" data-toggle="collapse" data-target="#test">
                            Pannello admin
                        </a>
                        <div id="test" class="collapse list-group">
                            <a href="newpizza.jsp" class="list-group-item">
                                <h4 class="list-group-item-heading text-center">
                                    Gestione men&ugrave; pizzeria
                                </h4>
                                <p class="list-group-item-text">
                                    Gestisci il catalogo delle pizze disponibili:
                                    aggiungine di nuove e sorprendi la clientela!
                                </p>
                            </a>
                            <a href="gestione-prenotazioni.jsp" class="list-group-item">
                                <h4 class="list-group-item-heading text-center">
                                    Visualizzazione prenotazioni
                                </h4>
                                <p class="list-group-item-text">
                                    Visualizza le prenotazioni effettuate dagli 
                                    utenti della pizzeria!
                                </p>
                            </a>
                        </div>
                    </div>
                </c:if> --%>
            </div>
            <div class="col-md-2"></div>
        </div>
        <%@include file="include/footer.html" %>

    </body>
</html>
