package classWork.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/NamePassword")
public class Q12_NamePassword extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String name = request.getParameter("name");
        String password = request.getParameter("password");

        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>Name and Password</title></head><body>");
        out.println("<h2>Submitted Details</h2>");
        out.println("<table border='1' cellpadding='10' style='border-collapse:collapse;'>");
        out.println("<tr><th>Name</th><td>" + name + "</td></tr>");
        out.println("<tr><th>Password</th><td>" + password + "</td></tr>");
        out.println("</table>");
        out.println("<br><a href='name_password_form.html'>Go Back</a>");
        out.println("</body></html>");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}
