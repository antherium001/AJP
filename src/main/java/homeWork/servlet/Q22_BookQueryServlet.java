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

@WebServlet("/BookQuery")
public class Q22_BookQueryServlet extends HttpServlet {

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
        out.println("<html><head><title>Book Query</title></head><body>");
        out.println("<h2 style='text-align:center;'>Book Management System</h2>");

        String createTable = "CREATE TABLE IF NOT EXISTS books ("
                + "book_id INT PRIMARY KEY, "
                + "book_name VARCHAR(200), "
                + "book_author VARCHAR(100), "
                + "published_date DATE)";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate(createTable);

            stmt.executeUpdate("DELETE FROM books");
            stmt.executeUpdate("INSERT INTO books VALUES (1, 'Java Programming', 'James Gosling', '2020-01-15')");
            stmt.executeUpdate("INSERT INTO books VALUES (2, 'Head First Design Patterns', 'Eric Freeman', '2019-06-20')");
            stmt.executeUpdate("INSERT INTO books VALUES (3, 'Effective Java', 'Joshua Bloch', '2021-03-10')");
            stmt.executeUpdate("INSERT INTO books VALUES (4, 'Spring in Action', 'Craig Walls', '2018-11-05')");
            stmt.executeUpdate("INSERT INTO books VALUES (5, 'Clean Code', 'Robert Martin', '2017-08-12')");

            String searchId = request.getParameter("book_id");
            ResultSet rs;

            if (searchId != null && !searchId.isEmpty()) {
                String searchSQL = "SELECT * FROM books WHERE book_id = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(searchSQL)) {
                    pstmt.setInt(1, Integer.parseInt(searchId));
                    rs = pstmt.executeQuery();
                    out.println("<h3>Search Result for Book ID: " + searchId + "</h3>");
                }
            } else {
                rs = stmt.executeQuery("SELECT * FROM books");
                out.println("<h3>All Books</h3>");
            }

            out.println("<table border='1' cellpadding='10' style='border-collapse:collapse;'>");
            out.println("<tr style='background-color:#795548; color:white;'>");
            out.println("<th>Book ID</th><th>Book Name</th><th>Author</th><th>Published Date</th></tr>");

            boolean found = false;
            while (rs.next()) {
                found = true;
                out.println("<tr>");
                out.println("<td>" + rs.getInt("book_id") + "</td>");
                out.println("<td>" + rs.getString("book_name") + "</td>");
                out.println("<td>" + rs.getString("book_author") + "</td>");
                out.println("<td>" + rs.getDate("published_date") + "</td>");
                out.println("</tr>");
            }

            if (!found) {
                out.println("<tr><td colspan='4' style='text-align:center;'>No book found with ID: " + searchId + "</td></tr>");
            }

            out.println("</table>");
            rs.close();

        } catch (SQLException e) {
            out.println("<p style='color:red;'>Error: " + e.getMessage() + "</p>");
        }

        out.println("<br><h3>Search by Book ID:</h3>");
        out.println("<form action='BookQuery' method='get'>");
        out.println("<input type='number' name='book_id' placeholder='Enter Book ID'>");
        out.println("<input type='submit' value='Search'>");
        out.println("<a href='BookQuery' style='margin-left:10px;'>Show All</a>");
        out.println("</form>");
        out.println("</body></html>");
    }
}
