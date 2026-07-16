<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head><title>Color Selection</title></head>
<body>
    <h2>Q33 - Session Tracking: Select a Color</h2>
<%
    String[] colors = {"Red", "Green", "Blue", "Yellow", "Purple", "Orange"};
%>
    <form action="Q33_print.jsp" method="post">
        <label>Choose a color:</label>
        <select name="color" size="6" style="width:150px;">
<%
        for (String c : colors) {
            out.println("<option value='" + c + "'>" + c + "</option>");
        }
%>
        </select>
        <br><br>
        <input type="submit" value="Submit">
    </form>
    <hr>
    <h3>Color Array:</h3>
    <ul>
<%
        for (String c : colors) {
            out.println("<li>" + c + " (length: " + c.length() + ")</li>");
        }
%>
    </ul>
</body>
</html>
