package assessment1;

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

@WebServlet("/EmployeeLogin")
public class Q5_LoginServlet extends HttpServlet {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/demo_db";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "aaditya@123";

    @Override
    public void init() throws ServletException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
                 Statement stmt = conn.createStatement()) {

                String createTable = "CREATE TABLE IF NOT EXISTS employee_logins ("
                        + "emp_id INT PRIMARY KEY, "
                        + "username VARCHAR(100), "
                        + "password VARCHAR(100))";
                stmt.executeUpdate(createTable);

                String checkEmpty = "SELECT COUNT(*) FROM employee_logins";
                ResultSet rs = stmt.executeQuery(checkEmpty);
                rs.next();
                int count = rs.getInt(1);
                rs.close();

                if (count == 0) {
                    String insertEmp = "INSERT INTO employee_logins (emp_id, username, password) VALUES (?, ?, ?)";
                    try (PreparedStatement pstmt = conn.prepareStatement(insertEmp)) {
                        pstmt.setInt(1, 1);
                        pstmt.setString(2, "admin");
                        pstmt.setString(3, "admin123");
                        pstmt.executeUpdate();

                        pstmt.setInt(1, 2);
                        pstmt.setString(2, "john");
                        pstmt.setString(3, "john456");
                        pstmt.executeUpdate();

                        pstmt.setInt(1, 3);
                        pstmt.setString(2, "priya");
                        pstmt.setString(3, "priya789");
                        pstmt.executeUpdate();

                        pstmt.setInt(1, 4);
                        pstmt.setString(2, "rahul");
                        pstmt.setString(3, "rahul321");
                        pstmt.executeUpdate();
                    }
                    System.out.println("4 employee accounts inserted into database.");
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new ServletException("Initialization failed: " + e.getMessage(), e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>Employee Login</title></head><body>");
        out.println("<h2 style='text-align:center; color:#333;'>Employee Login</h2>");
        out.println("<form action='EmployeeLogin' method='post' "
                + "style='max-width:350px; margin:auto; padding:20px; border:2px solid #4CAF50; border-radius:8px;'>");

        out.println("<label style='font-family:Arial;'><b>Username:</b></label><br>");
        out.println("<input type='text' name='username' required "
                + "style='width:100%; padding:8px; margin:8px 0; box-sizing:border-box;'><br>");

        out.println("<label style='font-family:Arial;'><b>Password:</b></label><br>");
        out.println("<input type='password' name='password' required "
                + "style='width:100%; padding:8px; margin:8px 0; box-sizing:border-box;'><br>");

        out.println("<input type='submit' value='Login' "
                + "style='width:100%; padding:10px; background-color:#4CAF50; color:white; "
                + "border:none; border-radius:4px; cursor:pointer; font-size:14px;'>");

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

        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>Login Result</title></head><body>");
        out.println("<div style='text-align:center; font-family:Arial; margin-top:50px;'>");

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {

            String sql = "SELECT * FROM employee_logins WHERE username = ? AND password = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, username);
                pstmt.setString(2, password);

                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        out.println("<h2 style='color:green;'>Login Successful!</h2>");
                        out.println("<p>Welcome, <b>" + rs.getString("username") + "</b>!</p>");
                        out.println("<p>Employee ID: " + rs.getInt("emp_id") + "</p>");
                        out.println("<p>You have been authenticated and can access the employee portal.</p>");
                    } else {
                        out.println("<h2 style='color:red;'>Login Failed!</h2>");
                        out.println("<p>Invalid username or password.</p>");
                        out.println("<p style='color:#666;'>Please check your credentials and try again.</p>");
                    }
                }
            }

        } catch (SQLException e) {
            out.println("<h2 style='color:red;'>Database Error</h2>");
            out.println("<p>" + e.getMessage() + "</p>");
        }

        out.println("<br><a href='employee_login.html'>Back to Login</a>");
        out.println("</div></body></html>");
    }
}
