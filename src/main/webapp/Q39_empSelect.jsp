<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*, java.util.ArrayList, java.util.List" %>
<!DOCTYPE html>
<html>
<head><title>Employee Records</title></head>
<body>
    <h2>Q39 - Select Records from Employee Database</h2>
<%
    String DB_URL = "jdbc:mysql://localhost:3306/employee_db";
    String DB_USER = "root";
    String DB_PASS = "aaditya@123";

    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;

    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        stmt = conn.createStatement();

        String createTable = "CREATE TABLE IF NOT EXISTS Employee ("
                + "id INT PRIMARY KEY, "
                + "name VARCHAR(100), "
                + "salary DOUBLE)";
        stmt.executeUpdate(createTable);

        ResultSet check = stmt.executeQuery("SELECT COUNT(*) FROM Employee");
        check.next();
        if (check.getInt(1) == 0) {
            stmt.executeUpdate("INSERT INTO Employee VALUES (101, 'Amit Verma', 55000)");
            stmt.executeUpdate("INSERT INTO Employee VALUES (102, 'Priya Sharma', 62000)");
            stmt.executeUpdate("INSERT INTO Employee VALUES (103, 'Rahul Singh', 48000)");
            stmt.executeUpdate("INSERT INTO Employee VALUES (104, 'Neha Gupta', 71000)");
            stmt.executeUpdate("INSERT INTO Employee VALUES (105, 'Sanjay Patel', 59000)");
        }
        check.close();

        rs = stmt.executeQuery("SELECT * FROM Employee");
%>
    <table border="1" cellpadding="10" cellspacing="0"
           style="border-collapse:collapse; font-family:Arial; margin:auto;">
        <tr style="background-color:#1976D2; color:white;">
            <th>Employee ID</th>
            <th>Name</th>
            <th>Salary (Rs.)</th>
        </tr>
<%
        boolean found = false;
        while (rs.next()) {
            found = true;
            out.println("<tr>");
            out.println("<td>" + rs.getInt("id") + "</td>");
            out.println("<td>" + rs.getString("name") + "</td>");
            out.println("<td>" + rs.getDouble("salary") + "</td>");
            out.println("</tr>");
        }

        if (!found) {
            out.println("<tr><td colspan='3' style='text-align:center;'>No records found</td></tr>");
        }
%>
    </table>
<%
        rs.close();
    } catch (Exception e) {
        out.println("<p style='color:red;'>Error: " + e.getMessage() + "</p>");
    } finally {
        try { if (rs != null) rs.close(); } catch (Exception e) {}
        try { if (stmt != null) stmt.close(); } catch (Exception e) {}
        try { if (conn != null) conn.close(); } catch (Exception e) {}
    }
%>
</body>
</html>
