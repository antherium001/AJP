<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head><title>Visitor Counter</title></head>
<body>
    <h2>Q32 - Visitor Counter</h2>
    <p>Click the button below to increment the visitor count.</p>
    <form action="Q32_visitor.jsp" method="post">
        <input type="submit" value="Click to Visit">
    </form>
    <hr>
<%
    Integer count = (Integer) application.getAttribute("visitorCount");
    if (count == null) count = 0;
    if ("POST".equalsIgnoreCase(request.getMethod())) count++;
    application.setAttribute("visitorCount", count);
%>
    <h3>Total Visitors: <%= count %></h3>
    <p><small>(Counter resets when server restarts)</small></p>
</body>
</html>
