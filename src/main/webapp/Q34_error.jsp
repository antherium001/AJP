<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head><title>Error Page</title></head>
<body>
    <h2 style="color:red;">Q34 - Arithmetic Exception Occurred</h2>
    <hr>
    <h3>Error Details:</h3>
    <table border="1" cellpadding="10" style="border-collapse:collapse;">
        <tr><th>Exception Type</th><td><%= exception.getClass().getName() %></td></tr>
        <tr><th>Error Message</th><td><%= exception.getMessage() %></td></tr>
    </table>
    <hr>
    <p><b>Note:</b> This error page is shown because the <code>errorPage</code> directive in <code>Q34_calculate.jsp</code> points here,
    and this page has <code>isErrorPage="true"</code> which makes the <code>exception</code> object available.</p>
    <br><a href="Q34_arith.html">Try Again</a>
</body>
</html>
