package classWork.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.GenericServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/EmployeeRegistration")
public class Q11_EmployeeRegistration extends GenericServlet {

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
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String designation = request.getParameter("designation");
        String salaryStr = request.getParameter("salary");

        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>Employee Registration</title></head><body>");

        if (name == null || email == null || designation == null || salaryStr == null) {
            out.println("<h2>No data received. Please use the registration form.</h2>");
            out.println("<a href='employee_registration.html'>Go to Registration Form</a>");
        } else {
            try {
                double salary = Double.parseDouble(salaryStr);

                String createTable = "CREATE TABLE IF NOT EXISTS registered_employees ("
                        + "id INT AUTO_INCREMENT PRIMARY KEY, "
                        + "name VARCHAR(100), "
                        + "email VARCHAR(100), "
                        + "designation VARCHAR(100), "
                        + "salary DECIMAL(10,2))";

                String insertSQL = "INSERT INTO registered_employees (name, email, designation, salary) VALUES (?, ?, ?, ?)";

                try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
                    try (java.sql.Statement stmt = conn.createStatement()) {
                        stmt.executeUpdate(createTable);
                    }
                    try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
                        pstmt.setString(1, name);
                        pstmt.setString(2, email);
                        pstmt.setString(3, designation);
                        pstmt.setDouble(4, salary);
                        pstmt.executeUpdate();
                    }
                }

                out.println("<h2 style='color:green;'>Employee Registration Successful!</h2>");
                out.println("<table border='1' cellpadding='10' style='border-collapse:collapse;'>");
                out.println("<tr><th>Name</th><td>" + name + "</td></tr>");
                out.println("<tr><th>Email</th><td>" + email + "</td></tr>");
                out.println("<tr><th>Designation</th><td>" + designation + "</td></tr>");
                out.println("<tr><th>Salary</th><td>" + salary + "</td></tr>");
                out.println("</table>");
                out.println("<br><a href='employee_registration.html'>Register Another</a>");

            } catch (NumberFormatException e) {
                out.println("<h2 style='color:red;'>Invalid salary format.</h2>");
            } catch (SQLException e) {
                out.println("<h2 style='color:red;'>Database Error: " + e.getMessage() + "</h2>");
            }
        }

        out.println("</body></html>");
    }
}
