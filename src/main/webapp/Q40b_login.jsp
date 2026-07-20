<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<!DOCTYPE html>
<html>
<head><title>User Login</title></head>
<body>
    <h2>Q40b - User Login</h2>
<%
    String DB_URL = "jdbc:mysql://localhost:3306/demo_db";
    String DB_USER = "root";
    String DB_PASS = "aaditya@123";

    if ("POST".equalsIgnoreCase(request.getMethod())) {
        String name = request.getParameter("name");
        String password = request.getParameter("password");

        if (name != null && password != null) {
            Connection conn = null;
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
                String sql = "SELECT * FROM registered_users WHERE name = ? AND password = ?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, name);
                pstmt.setString(2, password);
                rs = pstmt.executeQuery();

                if (rs.next()) {
                    session.setAttribute("loggedUser", name);
                    session.setAttribute("loggedEmail", rs.getString("email"));
                    response.sendRedirect("Q40_products.html");
                    return;
                } else {
                    out.println("<h3 style='color:red;'>Invalid username or password!</h3>");
                    out.println("<p>Please try again or <a href='Q40a_register.jsp'>register</a> first.</p>");
                }
            } catch (Exception e) {
                out.println("<p style='color:red;'>Error: " + e.getMessage() + "</p>");
            } finally {
                try { if (rs != null) rs.close(); } catch (Exception e) {}
                try { if (pstmt != null) pstmt.close(); } catch (Exception e) {}
                try { if (conn != null) conn.close(); } catch (Exception e) {}
            }
        }
    } else {
        out.println("<p style='color:gray;'>Please enter your credentials.</p>");
    }
%>
    <form action="Q40b_login.jsp" method="post">
        <label>Username:</label><br>
        <input type="text" name="name" required><br><br>
        <label>Password:</label><br>
        <input type="password" name="password" required><br><br>
        <input type="submit" value="Login">
    </form>
    <br>
    <a href="Q40a_register.jsp">Register New User</a>
</body>
</html>
