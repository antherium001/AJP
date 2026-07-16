package homeWork.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/EmployeeDetails")
public class Q10_ServletEmployee extends HttpServlet {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/employee_db";
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
        out.println("<html><head><title>Employee Details</title></head>");
        out.println("<body>");
        out.println("<h1 style='text-align:center; color:#333;'>Employee Details</h1>");
        out.println("<table border='1' cellpadding='10' cellspacing='0' "
                + "style='margin:auto; border-collapse:collapse; font-family:Arial;'>");
        out.println("<tr style='background-color:#4CAF50; color:white;'>");
        out.println("<th>Emp ID</th><th>Emp Name</th><th>Emp Address</th><th>Emp Phone</th>");
        out.println("</tr>");

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             Statement stmt = conn.createStatement()) {

            String createTable = "CREATE TABLE IF NOT EXISTS employee_details ("
                    + "emp_id INT PRIMARY KEY, "
                    + "emp_name VARCHAR(100), "
                    + "emp_address VARCHAR(200), "
                    + "emp_phone VARCHAR(15))";
            stmt.executeUpdate(createTable);

            stmt.executeUpdate("DELETE FROM employee_details");
            stmt.executeUpdate("INSERT INTO employee_details VALUES (1, 'Aaditya Pratap Singh', 'Delhi, India', '9876543210')");
            stmt.executeUpdate("INSERT INTO employee_details VALUES (2, 'Priya Singh', 'Mumbai, India', '9123456789')");
            stmt.executeUpdate("INSERT INTO employee_details VALUES (3, 'Amit Kumar', 'Bangalore, India', '9988776655')");
            stmt.executeUpdate("INSERT INTO employee_details VALUES (4, 'Neha Gupta', 'Chennai, India', '9871234567')");

            ResultSet rs = stmt.executeQuery("SELECT * FROM employee_details");

            boolean found = false;
            while (rs.next()) {
                found = true;
                out.println("<tr>");
                out.println("<td>" + rs.getInt("emp_id") + "</td>");
                out.println("<td>" + rs.getString("emp_name") + "</td>");
                out.println("<td>" + rs.getString("emp_address") + "</td>");
                out.println("<td>" + rs.getString("emp_phone") + "</td>");
                out.println("</tr>");
            }

            if (!found) {
                out.println("<tr><td colspan='4' style='text-align:center;'>No records found</td></tr>");
            }

        } catch (SQLException e) {
            out.println("<tr><td colspan='4' style='color:red;'>Error: " + e.getMessage() + "</td></tr>");
        }

        out.println("</table>");
        out.println("<p style='text-align:center; color:#666;'>Data fetched from MySQL using JDBC Servlet</p>");
        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
