<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="Q34_error.jsp" %>
<!DOCTYPE html>
<html>
<head><title>Calculation Result</title></head>
<body>
    <h2>Q34 - Division Result</h2>
<%
    String num1Str = request.getParameter("num1");
    String num2Str = request.getParameter("num2");
    if (num1Str != null && num2Str != null) {
        int num1 = Integer.parseInt(num1Str);
        int num2 = Integer.parseInt(num2Str);
        if (num2 == 0) throw new ArithmeticException("Division by zero is not allowed!");
        int result = num1 / num2;
%>
    <h3>Result</h3>
    <table border="1" cellpadding="10" style="border-collapse:collapse;">
        <tr><th>Dividend</th><td><%= num1 %></td></tr>
        <tr><th>Divisor</th><td><%= num2 %></td></tr>
        <tr><th>Quotient</th><td><%= result %></td></tr>
    </table>
<%
    } else {
        out.println("<p style='color:red;'>No input received.</p>");
    }
%>
    <br><a href="Q34_arith.html">Try Again</a>
</body>
</html>
