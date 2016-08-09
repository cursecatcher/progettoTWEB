<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        
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

        <!-- DataPicker jQuery -->
        <script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
        <link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
        <!-- TimePicker -->
        <link rel="stylesheet" href="include/lib/timepicker/jquery.timepicker.css">
        <script type="text/javascript" src="include/lib/timepicker/jquery.timepicker.min.js"></script>
        <!-- DatePicker -->
        <link rel="stylesheet" href="include/lib/datepicker/bootstrap-datepicker3.min.css">
        <script type="text/javascript" src="include/lib/datepicker/bootstrap-datepicker.min.js"></script>

        <script type="text/javascript" src="include/js/prenotazioni.js"></script>
        
    </head>
    <body>
        <%@include file="include/header.jsp" %>

        <div class="container">
            <div class="col-md-3"></div>
            <div class="col-md-6">
                <div>
                    <h2>Il tuo ordine</h2>
                    
                    <c:forEach var="el" items="${carrello.ordine}">
                        <c:out value="${el.quantity}"/>x&nbsp;<strong><c:out value="${el.nome}"/></strong><br/>
                    </c:forEach>
                    
                    <a href="newordine.jsp" class="btn btn-primary btn-block">
                        Modifica ordine
                    </a>
                </div>
            <div>
                <h2>Dettagli consegna</h2>
                <form action="Controller" method="POST">
                    <input type="hidden" name="action" value="add-prenotazione"/>
                    
                    <div class="form-group">
                        <label for="data">Data consegna</label>
                        <div id="datepicker" class="input-group date">
                            <input id="data" name="dataConsegna" type="text" 
                                   class="form-control"/>
                            <span class="input-group-addon">
                                <i class="glyphicon glyphicon-th"></i>
                            </span>
                        </div>
                    </div>
                    
                    <div class="form-group">
                        <label for="orario">Ora di consegna</label>
                        <div id="timepicker" class="input-group">
                            <input id="orario" type="text" name="oraConsegna"
                                   placeholder="hh:mm" class="form-control"/> 
                            <span class="input-group-addon">
                                <i class="glyphicon glyphicon-time"></i>
                            </span>
                        </div>
                    </div> 
                    
                    <input type="submit" name="submit" value="Ordina!" class="btn btn-primary btn-block"/>
                </form>
            </div>
            </div>
            <div class="col-md-3"></div>
            
            
        </div>

    </body>
</html>
