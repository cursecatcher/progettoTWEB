<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<nav class="navbar navbar-default">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <a class="navbar-brand" href="index.jsp">PiWeb</a>
        </div>

        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav navbar-right">
                <c:choose>
                    <c:when test="${usertoken == 'authenticated'}">
                        <li class="dropdown">
                            <a href="#0" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
                                Sei loggato come 
                                <strong><c:out value="${user.email}"/></strong>
                                <span class="caret"></span>
                            </a>
                            <ul class="dropdown-menu">
                                <li><a href="profilo.jsp">Profilo</a></li>
                                <li><a href="newordine.jsp">Ordina</a></li>
                                <li><a href="#0">Qualcos'altro</a></li>
                                <li role="separator" class="divider"></li>
                                <li><a href="#0">Logout</a></li>
                            </ul>
                        </li>
                        <li>
                            <a href='#0'>
                                <span class='glyphicon glyphicon-shopping-cart'></span>
                                <span id="cart-badge" class='badge'>
                                    <c:out value="${carrello.length}"/>
                                </span>
                            </a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li><a href='login.jsp'>Accedi</a></li>
                        </c:otherwise>
                    </c:choose> 

                <li><a href="#0">Aiuto</a></li>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>
