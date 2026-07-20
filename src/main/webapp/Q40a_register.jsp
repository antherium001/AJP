<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<!DOCTYPE html>
<html>
<head><title>User Registration</title></head>
<body>
    <h2>Q40a - User Registration Form</h2>
<%
    String DB_URL = "jdbc:mysql://localhost:3306/demo_db";
    String DB_USER = "root";
    String DB_PASS = "aaditya@123";

    Connection conn = null;
    Statement stmt = null;

    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        stmt = conn.createStatement();

        String createTable = "CREATE TABLE IF NOT EXISTS registered_users ("
                + "id INT AUTO_INCREMENT PRIMARY KEY, "
                + "name VARCHAR(100), "
                + "password VARCHAR(100), "
                + "email VARCHAR(100), "
                + "phone VARCHAR(15))";
        stmt.executeUpdate(createTable);

        ResultSet check = stmt.executeQuery("SELECT COUNT(*) FROM registered_users");
        check.next();
        if (check.getInt(1) == 0) {
            stmt.executeUpdate("INSERT INTO registered_users (name, password, email, phone) VALUES ('Amit', 'amit123', 'amit@gmail.com', '9876543210')");
            stmt.executeUpdate("INSERT INTO registered_users (name, password, email, phone) VALUES ('Priya', 'priya456', 'priya@gmail.com', '9123456789')");
            stmt.executeUpdate("INSERT INTO registered_users (name, password, email, phone) VALUES ('Rahul', 'rahul789', 'rahul@gmail.com', '9988776655')");
            stmt.executeUpdate("INSERT INTO registered_users (name, password, email, phone) VALUES ('Neha', 'neha321', 'neha@gmail.com', '9871234567')");
        }
        check.close();
    } catch (Exception e) {
        out.println("<p style='color:red;'>Error: " + e.getMessage() + "</p>");
    } finally {
        try { if (stmt != null) stmt.close(); } catch (Exception e) {}
        try { if (conn != null) conn.close(); } catch (Exception e) {}
    }
%>
    <h3>Register New User</h3>
    <form action="Q40a_register.jsp" method="post">
        <label>Name:</label><br>
        <input type="text" name="name" required><br><br>
        <label>Password:</label><br>
        <input type="password" name="password" required><br><br>
        <label>Email:</label><br>
        <input type="email" name="email" required><br><br>
        <label>Phone:</label><br>
        <input type="tel" name="phone" pattern="[0-9]{10}" required><br><br>
        <input type="submit" value="Register">
    </form>
    <hr>
<%
    if ("POST".equalsIgnoreCase(request.getMethod())) {
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");

        if (name != null && password != null && email != null && phone != null) {
            Connection conn2 = null;
            PreparedStatement pstmt = null;
            try {
                conn2 = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
                String insertSQL = "INSERT INTO registered_users (name, password, email, phone) VALUES (?, ?, ?, ?)";
                pstmt = conn2.prepareStatement(insertSQL);
                pstmt.setString(1, name);
                pstmt.setString(2, password);
                pstmt.setString(3, email);
                pstmt.setString(4, phone);
                pstmt.executeUpdate();
                out.println("<h3 style='color:green;'>Registration Successful! Welcome, " + name + "</h3>");
            } catch (Exception e) {
                out.println("<h3 style='color:red;'>Error: " + e.getMessage() + "</h3>");
            } finally {
                try { if (pstmt != null) pstmt.close(); } catch (Exception e) {}
                try { if (conn2 != null) conn2.close(); } catch (Exception e) {}
            }
        }
    }
%>
    <hr>
    <h3>All Registered Users</h3>
<%
    Connection conn3 = null;
    Statement stmt3 = null;
    ResultSet rs = null;
    try {
        conn3 = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        stmt3 = conn3.createStatement();
        rs = stmt3.executeQuery("SELECT * FROM registered_users");
%>
    <table border="1" cellpadding="10" cellspacing="0"
           style="border-collapse:collapse; font-family:Arial;">
        <tr style="background-color:#FF9800; color:white;">
            <th>ID</th>
            <th>Name</th>
            <th>Email</th>
            <th>Phone</th>
        </tr>
<%
        boolean found = false;
        while (rs.next()) {
            found = true;
            out.println("<tr>");
            out.println("<td>" + rs.getInt("id") + "</td>");
            out.println("<td>" + rs.getString("name") + "</td>");
            out.println("<td>" + rs.getString("email") + "</td>");
            out.println("<td>" + rs.getString("phone") + "</td>");
            out.println("</tr>");
        }
        if (!found) {
            out.println("<tr><td colspan='4' style='text-align:center;'>No users registered yet.</td></tr>");
        }
%>
    </table>
<%
    } catch (Exception e) {
        out.println("<p style='color:red;'>Error: " + e.getMessage() + "</p>");
    } finally {
        try { if (rs != null) rs.close(); } catch (Exception e) {}
        try { if (stmt3 != null) stmt3.close(); } catch (Exception e) {}
        try { if (conn3 != null) conn3.close(); } catch (Exception e) {}
    }
%>
    <br>
    <a href="Q40b_login.jsp">Go to Login</a>
</body>
</html>
