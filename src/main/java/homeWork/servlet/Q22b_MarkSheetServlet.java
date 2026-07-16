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

@WebServlet("/MarkSheet")
public class Q22b_MarkSheetServlet extends HttpServlet {

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
        out.println("<html><head><title>Student Mark Sheet</title></head><body>");
        out.println("<h2>Student Mark Sheet Entry</h2>");
        out.println("<form action='MarkSheet' method='post'>");
        out.println("<label>Student Name:</label><br>");
        out.println("<input type='text' name='studentName' required><br><br>");
        out.println("<label>Roll Number:</label><br>");
        out.println("<input type='text' name='rollNo' required><br><br>");
        out.println("<label>Maths:</label><br>");
        out.println("<input type='number' name='maths' min='0' max='100' required><br><br>");
        out.println("<label>English:</label><br>");
        out.println("<input type='number' name='english' min='0' max='100' required><br><br>");
        out.println("<label>Science:</label><br>");
        out.println("<input type='number' name='science' min='0' max='100' required><br><br>");
        out.println("<label>Computer Science:</label><br>");
        out.println("<input type='number' name='cs' min='0' max='100' required><br><br>");
        out.println("<input type='submit' value='Generate Mark Sheet'>");
        out.println("</form>");
        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String studentName = request.getParameter("studentName");
        String rollNo = request.getParameter("rollNo");
        int maths = Integer.parseInt(request.getParameter("maths"));
        int english = Integer.parseInt(request.getParameter("english"));
        int science = Integer.parseInt(request.getParameter("science"));
        int cs = Integer.parseInt(request.getParameter("cs"));

        int total = maths + english + science + cs;
        double percentage = total / 4.0;
        String grade;
        if (percentage >= 90) grade = "A+";
        else if (percentage >= 80) grade = "A";
        else if (percentage >= 70) grade = "B+";
        else if (percentage >= 60) grade = "B";
        else if (percentage >= 50) grade = "C";
        else if (percentage >= 40) grade = "D";
        else grade = "F";

        String createTable = "CREATE TABLE IF NOT EXISTS marksheets ("
                + "id INT AUTO_INCREMENT PRIMARY KEY, "
                + "student_name VARCHAR(100), "
                + "roll_no VARCHAR(20), "
                + "maths INT, english INT, science INT, cs INT, "
                + "total INT, percentage DOUBLE, grade VARCHAR(5))";

        String insertSQL = "INSERT INTO marksheets (student_name, roll_no, maths, english, science, cs, total, percentage, grade) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>Mark Sheet</title></head><body>");

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate(createTable);
            }
            try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
                pstmt.setString(1, studentName);
                pstmt.setString(2, rollNo);
                pstmt.setInt(3, maths);
                pstmt.setInt(4, english);
                pstmt.setInt(5, science);
                pstmt.setInt(6, cs);
                pstmt.setInt(7, total);
                pstmt.setDouble(8, percentage);
                pstmt.setString(9, grade);
                pstmt.executeUpdate();
            }

            out.println("<h2 style='text-align:center;'>STUDENT MARK SHEET</h2>");
            out.println("<table border='1' cellpadding='10' style='margin:auto; border-collapse:collapse;'>");
            out.println("<tr><th colspan='2' style='background-color:#3F51B5; color:white;'>Student Details</th></tr>");
            out.println("<tr><td><b>Student Name</b></td><td>" + studentName + "</td></tr>");
            out.println("<tr><td><b>Roll Number</b></td><td>" + rollNo + "</td></tr>");
            out.println("<tr><th colspan='2' style='background-color:#3F51B5; color:white;'>Marks</th></tr>");
            out.println("<tr><td><b>Maths</b></td><td>" + maths + "</td></tr>");
            out.println("<tr><td><b>English</b></td><td>" + english + "</td></tr>");
            out.println("<tr><td><b>Science</b></td><td>" + science + "</td></tr>");
            out.println("<tr><td><b>Computer Science</b></td><td>" + cs + "</td></tr>");
            out.println("<tr><th colspan='2' style='background-color:#3F51B5; color:white;'>Result</th></tr>");
            out.println("<tr><td><b>Total</b></td><td>" + total + " / 400</td></tr>");
            out.println("<tr><td><b>Percentage</b></td><td>" + String.format("%.2f", percentage) + "%</td></tr>");
            out.println("<tr><td><b>Grade</b></td><td><b>" + grade + "</b></td></tr>");
            out.println("</table>");

        } catch (SQLException e) {
            out.println("<h2 style='color:red;'>Error: " + e.getMessage() + "</h2>");
        }

        out.println("<br><div style='text-align:center;'>");
        out.println("<a href='MarkSheet'>Enter New Marks</a>");
        out.println("</div>");
        out.println("</body></html>");
    }
}
