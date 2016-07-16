<%@page contentType="text/html" pageEncoding="UTF-8"%>

<c:forEach var="pizza" items="${menu.pizze}">
    <div class="row menu-pizza">
        <div class="pizza-nome">
            <c:out value="${pizza.nome}"/>
        </div>
        <div class="pizza-prezzo">
            <c:out value="${pizza.prezzo}"/> <br/>
            <c:out value="${pizza.listaIngredienti}"/>
        </div>
    </div>
</c:forEach>