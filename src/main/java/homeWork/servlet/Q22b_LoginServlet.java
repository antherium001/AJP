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

@WebServlet("/Login")
public class Q22b_LoginServlet extends HttpServlet {

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
        out.println("<html><head><title>Login</title></head><body>");
        out.println("<h2>Login Form</h2>");
        out.println("<form action='Login' method='post'>");
        out.println("<label>Username:</label><br>");
        out.println("<input type='text' name='username' required><br><br>");
        out.println("<label>Password:</label><br>");
        out.println("<input type='password' name='password' required><br><br>");
        out.println("<input type='submit' value='Login'>");
        out.println("</form>");
        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        String createTable = "CREATE TABLE IF NOT EXISTS login_users ("
                + "id INT AUTO_INCREMENT PRIMARY KEY, "
                + "username VARCHAR(100), "
                + "password VARCHAR(100))";

        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>Login Result</title></head><body>");

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate(createTable);

            String checkSQL = "SELECT * FROM login_users WHERE username = ? AND password = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(checkSQL)) {
                pstmt.setString(1, username);
                pstmt.setString(2, password);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    out.println("<h2 style='color:green;'>Login Successful!</h2>");
                    out.println("<p>Welcome back, " + username + "!</p>");
                } else {
                    String insertSQL = "INSERT INTO login_users (username, password) VALUES (?, ?)";
                    try (PreparedStatement insertPstmt = conn.prepareStatement(insertSQL)) {
                        insertPstmt.setString(1, username);
                        insertPstmt.setString(2, password);
                        insertPstmt.executeUpdate();
                    }
                    out.println("<h2 style='color:blue;'>New user registered!</h2>");
                    out.println("<p>User '" + username + "' has been created. Please login again.</p>");
                }
                rs.close();
            }

        } catch (SQLException e) {
            out.println("<h2 style='color:red;'>Error: " + e.getMessage() + "</h2>");
        }

        out.println("<a href='Login'>Back to Login</a>");
        out.println("</body></html>");
    }
}
