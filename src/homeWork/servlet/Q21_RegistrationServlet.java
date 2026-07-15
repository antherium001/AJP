package homeWork.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/Registration")
public class Q21_RegistrationServlet extends HttpServlet {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/demo_db";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "root";

    @Override
    public void init() throws ServletException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new ServletException("MySQL Driver not found", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>Registered Users</title></head><body>");
        out.println("<h2>All Registered Users</h2>");

        String createTable = "CREATE TABLE IF NOT EXISTS users ("
                + "id INT AUTO_INCREMENT PRIMARY KEY, "
                + "name VARCHAR(100), "
                + "password VARCHAR(100), "
                + "email VARCHAR(100), "
                + "phone VARCHAR(15))";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate(createTable);

            ResultSet rs = stmt.executeQuery("SELECT * FROM users");

            out.println("<table border='1' cellpadding='10' style='border-collapse:collapse;'>");
            out.println("<tr style='background-color:#FF9800; color:white;'>");
            out.println("<th>ID</th><th>Name</th><th>Email</th><th>Phone</th></tr>");

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

            out.println("</table>");
            out.println("<br><a href='registration.html'>Register New User</a>");

        } catch (SQLException e) {
            out.println("<p style='color:red;'>Error: " + e.getMessage() + "</p>");
        }

        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String name = request.getParameter("name");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");

        String createTable = "CREATE TABLE IF NOT EXISTS users ("
                + "id INT AUTO_INCREMENT PRIMARY KEY, "
                + "name VARCHAR(100), "
                + "password VARCHAR(100), "
                + "email VARCHAR(100), "
                + "phone VARCHAR(15))";

        String insertSQL = "INSERT INTO users (name, password, email, phone) VALUES (?, ?, ?, ?)";

        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>Registration Result</title></head><body>");

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate(createTable);
            }
            try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
                pstmt.setString(1, name);
                pstmt.setString(2, password);
                pstmt.setString(3, email);
                pstmt.setString(4, phone);
                pstmt.executeUpdate();
            }
            out.println("<h2 style='color:green;'>Registration Successful!</h2>");
            out.println("<p>Welcome, " + name + "!</p>");
        } catch (SQLException e) {
            out.println("<h2 style='color:red;'>Error: " + e.getMessage() + "</h2>");
        }

        out.println("<a href='registration.html'>Register Another</a> | ");
        out.println("<a href='Registration'>View All Users</a>");
        out.println("</body></html>");
    }
}
