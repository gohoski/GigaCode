<%@ page contentType="text/html" pageEncoding="UTF-8"%>
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
        <p>Админ-меню</p>
        <div class="tabs is-centered">
          <ul>
            <li class="is-active"><a>Задачи</a></li>
          </ul>
        </div>
        <input class="input is-rounded" type="number" placeholder="Id" id="idInput" />
        <button class="button is-success is-dark">Список задач</button>
    </body>
</html>