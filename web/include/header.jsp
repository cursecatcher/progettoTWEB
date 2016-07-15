<%@page contentType="text/html" pageEncoding="UTF-8"%>
<nav class="navbar navbar-default">
    <div class="collapse navbar-collapse">
        <div class="navbar-header">
            <a class="navbar-brand" href="index.jsp">PiWeb</a>
        </div>
        <ul class="nav navbar-nav navbar-right">
            <li>
                <form action="Controller" method="GET">
                    <input type="hidden" name="action" value="profilo"/>
                    <input type="submit" class="btn btn-link" value="Il tuo profilo"/>
                </form>
            </li>
            <li><a href="menu.jsp">Menu</a></li>
            <li><a href="login.jsp">Login</a></li>
            <li><a href="register.jsp">${sessionScope.usertoken}</a></li>
        </ul>
    </div>
</nav>
