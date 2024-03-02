<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="org.json.simple.JSONObject" %>
<% JSONObject data = (JSONObject) request.getAttribute("data"); %>
<!DOCTYPE html>
<html>
    <head>
        <title>Exercise - GigaCode</title>
        <link rel="stylesheet" href="/resources/styles/darkly.min.css" type="text/css">
        <link rel="stylesheet" href="/resources/styles/style.css" type="text/css">
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=JetBrains+Mono:ital,wght@0,100..800;1,100..800&display=swap" rel="stylesheet">
        <script src="/resources/scripts/ace/ace.js"></script>
    </head>
    <body>
        <%= (String) data.get("name") %>
        <div class="tile is-ancestor">
          <div class="tile is-parent">
            <article class="tile is-child box">
              <p class="title">One</p>
              <p class="subtitle">Subtitle</p>
            </article>
          </div>
          <div class="tile is-parent">
            <article class="tile is-child box">
              <p class="title">Two</p>
              <p class="subtitle">Subtitle</p>
            </article>
          </div>
          <div class="tile is-parent">
            <article class="tile is-child box">
              <p class="title">Three</p>
              <p class="subtitle">Subtitle</p>
            </article>
          </div>
          <div class="tile is-parent">
            <article class="tile is-child box">
              <p class="title">Four</p>
              <p class="subtitle">Subtitle</p>
            </article>
          </div>
        </div>
        <div class="box" style="position: relative; height: 51vh">
            <div id="editor">class fd {}</div>
        </div>
        <script>
            let editor = ace.edit("editor");
            editor.setTheme("ace/theme/dracula");
            editor.session.setMode("ace/mode/java");
            editor.setOption('showPrintMargin', false);
            editor.setOption('fontFamily', 'JetBrains+Mono');
            editor.setOption('fontSize', '.96em');
        </script>
    </body>
</html>