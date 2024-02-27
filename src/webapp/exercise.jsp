<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="org.json.simple.JSONObject" %>
<% JSONObject data = request.getAttribute("data") %>
<!DOCTYPE html>
<html>
    <head>
        <title>Exercise - GigaCode</title>
        <link rel="stylesheet" href="https://unpkg.com/bulmaswatch/darkly/bulmaswatch.min.css">
    </head>
    <body>
        <pre><%= (String) data.get("name") %></pre>
    </body>
</html>