<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head><title>Selected Color</title></head>
<body>
    <h2>Q33 - Session Tracking: Selected Color Details</h2>
<%
    String color = request.getParameter("color");
    if (color != null) {
        session.setAttribute("selectedColor", color);
    } else {
        Object obj = session.getAttribute("selectedColor");
        if (obj != null) color = obj.toString();
    }
    if (color != null) {
        out.println("<h3>Selected Color: <span style='color:" + color.toLowerCase() + ";'>" + color + "</span></h3>");
        out.println("<p><b>Length of color name:</b> " + color.length() + " characters</p>");
        out.println("<p><b>Session ID:</b> " + session.getId() + "</p>");
    } else {
        out.println("<h3 style='color:red;'>No color selected.</h3>");
        out.println("<a href='Q33_index.jsp'>Go back to select a color</a>");
    }
%>
    <br>
    <a href="Q33_index.jsp">Select Another Color</a>
    |
    <a href="Q33_print.jsp">Refresh This Page</a>
    <p><small>(Session preserves the selected color across pages)</small></p>
</body>
</html>
