<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="org.json.simple.JSONObject" %>
<%@ page import="org.json.simple.JSONArray" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Exercises - GigaCode</title>
        <link rel="stylesheet" href="/resources/styles/bulma/darkly.min.css" type="text/css">
        <link rel="stylesheet" href="/resources/styles/style.css" type="text/css">
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=JetBrains+Mono:ital,wght@0,100..800;1,100..800&display=swap" rel="stylesheet">
        <script src="/resources/scripts/ace/ace.js"></script>
        <meta name="viewport" content="width=device-width, initial-scale=1">
    </head>
    <body>
        <div class="tile is-ancestor">
            <c:forEach var="tile" items='${data}' varStatus="loop">
                <div class="tile is-parent is-marginless is-paddingless ${!loop.first ? 'inherited' : ''}">
                    <article class="tile is-child box notification">
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
                                    <c:out value='${func.get("name")}'/>(
                                    <c:forEach var="var" items="${func.get('variables')}" varStatus="loop">
                                        <c:out value="${var.get('name')}" />:
                                        <i><c:out value="${var.get('type')}" /></i><%--
                                        --%>${!loop.last ? ',' : ''}</c:forEach>
                                    )<%--
                                --%></div>
                            </c:forEach>
                        </div>
                    </article>
                </div>
            </c:forEach>
            <%-- <div class="tile is-parent">
                <article class="tile is-child box">
                    <p class="title">Two</p>
                    <p>Subtitle</p>
                </article>
            </div>
            <div class="tile is-parent">
                <article class="tile is-child box">
                    <p class="title">Three</p>
                    <p class="subtitle">Subtitle</p>
                </article>
            </div> --%>
        </div>
        <div class="box is-marginless">
            <div class="tabs is-boxed is-marginless" >
              <ul>
                <c:forEach var="file" items="${data}">
                  <li>
                    <a><span>${file.get("name")}.java</span></a>
                  </li>
                </c:forEach>
              </ul>
            </div>
            <div style="position: relative; height: 51vh"><div id="editor">class fd {}</div></div>
        </div>
        <script>
            let editor = ace.edit("editor");
            editor.setTheme("ace/theme/dracula");
            editor.session.setMode("ace/mode/java");
            editor.setOptions({
                'showPrintMargin': false,
                'fontFamily': 'JetBrains Mono',
                'fontSize': '.96em'
            });
        </script>
    </body>
</html>