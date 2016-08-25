<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="col-md-1"></div>
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="col-md-10">
        <div class="navbar-header">
            <a class="navbar-brand" href="index.jsp">PiWeb</a>
        </div>
            <div id="__error" style="display:none">
                <c:out value="${error}"/>
            </div>

        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav navbar-right">
                <c:choose>
                    <c:when test="${usertoken == 'authenticated'}">
                        <li class="dropdown">
                            <a href="#0" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
                                Ciao,&nbsp;
                                <strong><c:out value="${user.nome}"/></strong>
                                <span class="caret"></span>
                            </a>
                            <ul class="dropdown-menu">                                
                                <li><a href="profilo.jsp">Profilo</a></li>
                                <li role="separator" class="divider"></li>
                                <li><a href="newordine.jsp">Ordina</a></li>
                                <li role="separator" class="divider"></li>
                                <li><a href="mie-prenotazioni.jsp">Le mie prenotazioni</a></li>
                                <li role="separator" class="divider"></li>
                                <li><a href="#0" id="disconnect">Logout</a></li>
                            </ul>
                        </li>
                        <li>
                            <a href="newordine.jsp">
                                <span class='glyphicon glyphicon-shopping-cart'></span>
                                <span id="cart-badge" class='badge'>
                                    <c:out value="${carrello.length}"/>
                                </span>
                                <span id="cart-euro">
                                    <fmt:formatNumber type="number" value="${carrello.prezzoTotale}" minFractionDigits="2"/>
                                </span>&euro;
                            </a>
                        </li> 
                    </c:when>
                    <c:otherwise>
                        <li><a href='login.jsp'>Accedi</a></li>
                        </c:otherwise>
                    </c:choose> 
            </ul>
        </div><!-- /.navbar-collapse -->
        </div>
        <div class="col-md-1"></div>
    </div><!-- /.container-fluid -->
</nav>

<%--
<c:if test="${usertoken == 'authenticated'}">
    <div id="jq-dropdown-1" class="jq-dropdown jq-dropdown-tip">
        <div class="jq-dropdown-panel">
            <h4>Carrello</h4>
            <div id="cart-content">
                <c:choose>
                    <c:when test="${carrello.isEmpty()}">
                        Carrello vuoto
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="e" items="${carrello.ordine}">
                            <div class="row" style="padding-left: 1em">
                                <a class="remove-pizza" href="#0" 
                                   data-id-pizza="${e.id}" data-nome-pizza="${e.nome}">
                                    <span class="glyphicon glyphicon-minus"></span>
                                </a>
                                <c:out value="${e.quantity}"/>x <strong><c:out value="${e.nome}"/></strong>
                            </div>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>   
            </div>
        </div>
    </div>
</c:if> --%>

<form action="Controller" method="GET" id="form-action">
    <input type="hidden" name="action" value="random"/>
</form>