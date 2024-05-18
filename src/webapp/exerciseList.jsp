<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="org.json.JSONObject" %>
<%@ page import="org.json.JSONArray" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html data-theme="dark">
    <head>
        <link rel="stylesheet" href="/resources/styles/bulma.min.css" type="text/css">
        <link rel="stylesheet" href="/resources/styles/style.css" type="text/css">
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=JetBrains+Mono:ital,wght@0,100..800;1,100..800&display=swap" rel="stylesheet">
        <meta name="viewport" content="width=device-width, initial-scale=1">
    </head>
    <body style="/*transform: scale(0.7);*/ padding: 1rem;">
        <c:forEach var="ex" items='${data.iterator()}' varStatus="id">
            <div class="box has-background-success-dark">
                <div style="text-align: center; vertical-align: middle">
                    <span style="vertical-align: middle">Exercise #${id.index} </span>
                    <button class="button is-success" style="vertical-align: middle" data="chooseBtn" data-id="${id.index}"><b>Выбрать</b></button>
                </div><div class="columns">
            <c:forEach var="tile" items='${ex.iterator()}' varStatus="loop">
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
        <div id="notification" class="notification" style="display: none;">
        </div></div></c:forEach>
    </body>
</html>