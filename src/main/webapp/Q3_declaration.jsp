<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head><title>JSP Declaration Tag Demo</title></head>
<body style="font-family:Arial; margin:40px;">
    <h2 style="text-align:center; color:#333;">Q3 - JSP Declaration Tag Demo</h2>

    <%!
        int studentId = 101;
        String studentName = "Aaditya Pratap Singh";
        String department = "Computer Science";
        double gpa = 9.5;
        int graduationYear = 2026;
    %>

    <table border="1" cellpadding="12" cellspacing="0"
           style="margin:auto; border-collapse:collapse;">
        <tr style="background-color:#2196F3; color:white;">
            <th colspan="2">Student Details (Declared using &lt;%! %&gt; tag)</th>
        </tr>
        <tr>
            <td><b>Student ID</b></td>
            <td><%= studentId %></td>
        </tr>
        <tr>
            <td><b>Name</b></td>
            <td><%= studentName %></td>
        </tr>
        <tr>
            <td><b>Department</b></td>
            <td><%= department %></td>
        </tr>
        <tr>
            <td><b>GPA</b></td>
            <td><%= gpa %></td>
        </tr>
        <tr>
            <td><b>Graduation Year</b></td>
            <td><%= graduationYear %></td>
        </tr>
    </table>

    <br>
    <h3 style="text-align:center;">How it works:</h3>
    <ul style="max-width:500px; margin:auto; font-family:Arial;">
        <li><b>&lt;%! ... %&gt;</b> — Declaration tag: declares instance variables/methods at class level</li>
        <li><b>&lt;%= ... %&gt;</b> — Expression tag: evaluates and prints the value to the page</li>
        <li>These variables are shared across all requests (instance-level, not method-level)</li>
    </ul>
</body>
</html>
