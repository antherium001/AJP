package classWork.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/UserForm")
public class Q17_UserFormServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String userName = request.getParameter("userName");
        String userPassword = request.getParameter("userPassword");
        String userMobile = request.getParameter("userMobile");

        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>User Details</title></head><body>");
        out.println("<h2 style='color:#333;'>User Registration Details</h2>");
        out.println("<table border='1' cellpadding='10' style='border-collapse:collapse; font-family:Arial;'>");
        out.println("<tr style='background-color:#2196F3; color:white;'><th>Field</th><th>Value</th></tr>");
        out.println("<tr><td><b>User Name</b></td><td>" + userName + "</td></tr>");
        out.println("<tr><td><b>User Password</b></td><td>" + userPassword + "</td></tr>");
        out.println("<tr><td><b>User Mobile</b></td><td>" + userMobile + "</td></tr>");
        out.println("</table>");
        out.println("<br><a href='user_form.html'>Go Back</a>");
        out.println("</body></html>");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>User Form</title></head><body>");
        out.println("<h2>Please submit the form using POST method.</h2>");
        out.println("<a href='user_form.html'>Go to Form</a>");
        out.println("</body></html>");
    }
}
