<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="org.json.simple.JSONObject" %>
<%@ page import="org.json.simple.JSONArray" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html data-theme="dark">
    <head>
        <title>Exercises - GigaCode</title>
        <link rel="stylesheet" href="/resources/styles/bulma.min.css" type="text/css">
        <link rel="stylesheet" href="/resources/styles/style.css" type="text/css">
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=JetBrains+Mono:ital,wght@0,100..800;1,100..800&display=swap" rel="stylesheet">
        <script src="/resources/scripts/ace/ace.js"></script>
        <meta name="viewport" content="width=device-width, initial-scale=1">
    </head>
    <body>
        <div class="columns">
            <c:forEach var="tile" items='${data}' varStatus="loop">
                ${!loop.first ? '<div class="inherited"></div>' : ''}
                <div class="column">
                    <article class="box notification">
                        <p class="title">${tile.get("name")}</p>
                        <div class="content">
                            <c:forEach var="var" items='${tile.get("variables").toArray()}'>
                                <div>
                                    <span class="modifier <c:out value='${var.get("modifier")}'/>"></span>
                                    <c:out value='${var.get("name")}'/>:
                                    <i><c:out value='${var.get("type")}'/></i>
                                </div>
                            </c:forEach>
                            <hr/>
                            <c:forEach var="func" items='${tile.get("functions").toArray()}'>
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
            <button class="delete"></button>
        </div>
        <div class="box is-marginless">
            <div class="tabs is-boxed is-marginless" >
              <ul>
                <c:forEach var="file" items="${data}">
                  <li onclick="setTab(this)">
                    <a><span>${file.get("name")}.java</span></a>
                  </li>
                </c:forEach>
              </ul>
              <button class="button is-primary" onclick="checkResults(this)">Отправить</button>
            </div>
            <div style="position: relative; height: 51vh"><div id="editor"></div></div>
        </div><%--
        --%>
        <script src="/resources/scripts/exercise.js"></script>
    </body>
</html>