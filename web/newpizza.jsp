<%-- 
    inserire scriptlet che controlli il login (o qualcosa del genere)
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="ingredientiBean" scope="page" class="beans.Ingredienti" />
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>New pizza is coming</title>


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

        <link rel='stylesheet' type='text/css' href='include/lib/sol/sol.css'>
        <script type='text/javascript' src='include/lib/sol/sol.js'></script>
    </head>
    <body>
        <jsp:include page="include/html/header.html"/>
        
        <div class='container'>
            <h1>Nuova pizza</h1>

            <div class="form insert">
                <form id="form_newpi" action="ServletController" method="POST">
                    <p>
                        <label>Nome pizza</label>
                        <input name="name" type="text" 
                               placeholder="Nome della pizza (es. margherita)"/>
                    </p>
                    <p>
                        <label>Prezzo</label>
                        <input name="price" type="number" min="0.5" step="0.1"
                               placeholder="Prezzo della pizza..."/>
                    </p>
                    <p>
                        Da quali ingredienti &egrave; composta la pizza?<br/>

                        <select id='select-random' name='culo' multiple="multiple">
                            <jsp:getProperty name="ingredientiBean" property="listaIngredienti"/>
                        </select>
                        
                        <script type='text/javascript'>
                            $(function() {
                               $('#select-random').searchableOptionList(); 
                            });
                        </script>
                    </p>
                    <input id='submit-newpi' type="submit"/>
                </form>
            </div>
            
            Clicca <a href='newingrediente.jsp'>qui</a> per inserire un ingrediente mancante! 
        </div>
    </body>
</html>
