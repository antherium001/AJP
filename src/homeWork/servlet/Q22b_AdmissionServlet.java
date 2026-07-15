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

@WebServlet("/Admission")
public class Q22b_AdmissionServlet extends HttpServlet {

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
        out.println("<html><head><title>Admission Form</title></head><body>");
        out.println("<h2>Student Admission Form</h2>");
        out.println("<form action='Admission' method='post'>");
        out.println("<label>Student Name:</label><br>");
        out.println("<input type='text' name='studentName' required><br><br>");
        out.println("<label>Father's Name:</label><br>");
        out.println("<input type='text' name='fatherName' required><br><br>");
        out.println("<label>Date of Birth:</label><br>");
        out.println("<input type='date' name='dob' required><br><br>");
        out.println("<label>Course:</label><br>");
        out.println("<select name='course' required>");
        out.println("<option value=''>--Select--</option>");
        out.println("<option value='B.Tech'>B.Tech</option>");
        out.println("<option value='BCA'>BCA</option>");
        out.println("<option value='MCA'>MCA</option>");
        out.println("<option value='M.Tech'>M.Tech</option>");
        out.println("<option value='MBA'>MBA</option>");
        out.println("</select><br><br>");
        out.println("<label>Email:</label><br>");
        out.println("<input type='email' name='email' required><br><br>");
        out.println("<label>Phone:</label><br>");
        out.println("<input type='tel' name='phone' pattern='[0-9]{10}' required><br><br>");
        out.println("<label>Address:</label><br>");
        out.println("<textarea name='address' rows='3' cols='40' required></textarea><br><br>");
        out.println("<input type='submit' value='Submit Admission'>");
        out.println("</form>");
        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String studentName = request.getParameter("studentName");
        String fatherName = request.getParameter("fatherName");
        String dob = request.getParameter("dob");
        String course = request.getParameter("course");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");

        String createTable = "CREATE TABLE IF NOT EXISTS admissions ("
                + "id INT AUTO_INCREMENT PRIMARY KEY, "
                + "student_name VARCHAR(100), "
                + "father_name VARCHAR(100), "
                + "dob DATE, "
                + "course VARCHAR(50), "
                + "email VARCHAR(100), "
                + "phone VARCHAR(15), "
                + "address TEXT)";

        String insertSQL = "INSERT INTO admissions (student_name, father_name, dob, course, email, phone, address) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>Admission Result</title></head><body>");

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate(createTable);
            }
            try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
                pstmt.setString(1, studentName);
                pstmt.setString(2, fatherName);
                pstmt.setDate(3, java.sql.Date.valueOf(dob));
                pstmt.setString(4, course);
                pstmt.setString(5, email);
                pstmt.setString(6, phone);
                pstmt.setString(7, address);
                pstmt.executeUpdate();
            }

            out.println("<h2 style='color:green;'>Admission Submitted Successfully!</h2>");
            out.println("<table border='1' cellpadding='10' style='border-collapse:collapse;'>");
            out.println("<tr><td><b>Student Name</b></td><td>" + studentName + "</td></tr>");
            out.println("<tr><td><b>Father's Name</b></td><td>" + fatherName + "</td></tr>");
            out.println("<tr><td><b>Date of Birth</b></td><td>" + dob + "</td></tr>");
            out.println("<tr><td><b>Course</b></td><td>" + course + "</td></tr>");
            out.println("<tr><td><b>Email</b></td><td>" + email + "</td></tr>");
            out.println("<tr><td><b>Phone</b></td><td>" + phone + "</td></tr>");
            out.println("<tr><td><b>Address</b></td><td>" + address + "</td></tr>");
            out.println("</table>");

        } catch (SQLException e) {
            out.println("<h2 style='color:red;'>Error: " + e.getMessage() + "</h2>");
        }

        out.println("<br><a href='Admission'>New Admission</a>");
        out.println("</body></html>");
    }
}
