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

@WebServlet("/Feedback")
public class Q22b_FeedbackServlet extends HttpServlet {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/demo_db";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "aaditya@123";

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
        out.println("<html><head><title>Customer Feedback</title></head><body>");
        out.println("<h2>Customer Feedback Form</h2>");
        out.println("<form action='Feedback' method='post'>");
        out.println("<label>Name:</label><br>");
        out.println("<input type='text' name='name' required><br><br>");
        out.println("<label>Email:</label><br>");
        out.println("<input type='email' name='email' required><br><br>");
        out.println("<label>Rating (1-5):</label><br>");
        out.println("<input type='number' name='rating' min='1' max='5' required><br><br>");
        out.println("<label>Comments:</label><br>");
        out.println("<textarea name='comments' rows='4' cols='40' required></textarea><br><br>");
        out.println("<input type='submit' value='Submit Feedback'>");
        out.println("</form>");
        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String rating = request.getParameter("rating");
        String comments = request.getParameter("comments");

        String createTable = "CREATE TABLE IF NOT EXISTS feedback ("
                + "id INT AUTO_INCREMENT PRIMARY KEY, "
                + "name VARCHAR(100), "
                + "email VARCHAR(100), "
                + "rating INT, "
                + "comments TEXT)";

        String insertSQL = "INSERT INTO feedback (name, email, rating, comments) VALUES (?, ?, ?, ?)";

        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>Feedback Submitted</title></head><body>");

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate(createTable);
            }
            try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
                pstmt.setString(1, name);
                pstmt.setString(2, email);
                pstmt.setInt(3, Integer.parseInt(rating));
                pstmt.setString(4, comments);
                pstmt.executeUpdate();
            }

            out.println("<h2 style='color:green;'>Thank you for your feedback!</h2>");
            out.println("<table border='1' cellpadding='10' style='border-collapse:collapse;'>");
            out.println("<tr><td><b>Name</b></td><td>" + name + "</td></tr>");
            out.println("<tr><td><b>Email</b></td><td>" + email + "</td></tr>");
            out.println("<tr><td><b>Rating</b></td><td>" + rating + " / 5</td></tr>");
            out.println("<tr><td><b>Comments</b></td><td>" + comments + "</td></tr>");
            out.println("</table>");

        } catch (SQLException e) {
            out.println("<h2 style='color:red;'>Error: " + e.getMessage() + "</h2>");
        }

        out.println("<br><a href='Feedback'>Submit Another</a>");
        out.println("</body></html>");
    }
}
