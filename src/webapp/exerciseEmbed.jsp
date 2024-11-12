<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<div class="columns">
    <c:forEach var="tile" items='${data.iterator()}' varStatus="loop">
        ${!loop.first ? '<div class="inherited"></div>' : ''}
        <div class="column">
            <article class="box notification">
                <p class="title">${tile.get("name")}</p>
                <div class="content">
                    <c:forEach var="var" items='${tile.get("variables").toList()}'>
                        <div>
                            <span class="modifier <c:out value='${var.get("modifier")}'/>"></span>
                            <c:out value='${var.get("name")}'/>:
                            <i><c:out value='${var.get("type")}'/></i>
                        </div>
                    </c:forEach>
                    <hr/>
                    <c:forEach var="func" items='${tile.get("functions").toList()}'>
                        <div>
                            <span class="modifier <c:out value='${func.get("modifier")}'/>"></span>
                            <c:out value='${func.get("name")}'/> (<c:forEach var="var" items="${func.get('variables')}" varStatus="loop"><c:out value="${var.get('name')}" />:
                                <i><c:out value="${var.get('type')}" /></i><%--
                                --%>${!loop.last ? ', ' : ''}</c:forEach>)<%--
                        --%></div>
                    </c:forEach>
                </div>
            </article>
        </div>
    </c:forEach>
</div>